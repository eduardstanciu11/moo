package com.moo.cart.integration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moo.cart.ApplicationMain;
import cucumber.api.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClearCart implements En {
    private int port;
    private HttpResponse<String> getProducts;

    public ClearCart() {
        Before(() -> {
            port = ApplicationMain.startServer();
        });

        After(() -> {
            ApplicationMain.stopServer();
            // This is terrible.
            // Spark has a problem where it doesn't clear the running state flag until shutdown has completed, and
            // has no blocking stop method.
            // See https://github.com/perwendel/spark/issues/705
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        And("^the client calls /cart/id/clear", () -> {
            try {
                getProducts = Unirest.post("http://localhost:" + port + "/cart/1/clear").asString();
            } catch (UnirestException e) {
                getProducts = null;
            }
        });
    }
}
