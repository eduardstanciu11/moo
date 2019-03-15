package com.moo.cart.integration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moo.cart.ApplicationMain;
import cucumber.api.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetProducts implements En {
    private int port;
    private HttpResponse<String> getProducts;

    public GetProducts() {
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

        And("^the client calls /cart/id/getProducts", () -> {
            try {
                getProducts = Unirest.get("http://localhost:" + port + "/cart/1/getProducts").asString();
            } catch (UnirestException e) {
                getProducts = null;
            }
        });

        Then("^the number of products returned is (\\d+)$", (Integer numberOfProducts) -> {
            assertNotNull(getProducts);
            assertEquals(200, getProducts.getStatus());
            assertNotNull(getProducts.getBody());
            assertEquals(1, getProducts.getBody().split(", ").length, "Product list size is supposed to be 1");
        });
    }
}
