package com.moo.cart;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.moo.cart.models.DummyCart;
import com.moo.cart.models.DummyProduct;
import com.moo.cart.resources.StandardResponse;
import com.moo.cart.resources.StatusResponse;
import com.moo.cart.services.ShoppingCartService;
import com.moo.cart.services.ShoppingCartServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class ApplicationMain {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    private static final Gson GSON = new Gson();
    private static final ResponseTransformer JSON_TRANSFORMER = GSON::toJson;
    private static final String JSON = "application/json";

    public static void main(String[] Args) {
        startServer();
    }

    public static int startServer() {
        Spark.init();

        initializeRoutes();

        exception(JsonSyntaxException.class, ApplicationMain::handleInvalidInput);
        LOG.debug("Created exception handlers");

        Spark.awaitInitialization();
        LOG.debug("Ready");
        return Spark.port();
    }

    private static void initializeRoutes() {
        final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

        // Set response type to always be JSON
        before((request, response) -> response.type(JSON));

        path("/health", () -> get("", (req, res) -> "healthy"));

        path("/cart", () -> {
            post("/:id/add", (request, response) -> {
                response.type("application/json");

                DummyCart cart = new DummyCart(request.params(":id"));
                DummyProduct product = new DummyProduct(request.queryParams("productId"), request.queryParams("productName"));

                shoppingCartService.addProduct(cart, product);

                if(shoppingCartService.getProducts(cart).contains(product)) {
                    return new Gson()
                            .toJson(new StandardResponse(StatusResponse.SUCCESS));
                } else {
                    return new Gson()
                            .toJson(new StandardResponse(StatusResponse.ERROR));
                }
            });
        });

        path("/cart", () -> {
            get("/:id/getProducts", (request, response) -> {
                response.type("application/json");

                DummyCart cart = new DummyCart(request.params(":id"));

                ArrayList<DummyProduct> products = shoppingCartService.getProducts(cart);

                return products.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
            });
        });

        path("/cart", () -> {
            post("/:id/clear", (request, response) -> {
                response.type("application/json");

                DummyCart cart = new DummyCart(request.params(":id"));

                shoppingCartService.clearProducts(cart);

                if(shoppingCartService.getProducts(cart) == null) {
                    return new Gson()
                            .toJson(new StandardResponse(StatusResponse.SUCCESS));
                } else {
                    return new Gson()
                            .toJson(new StandardResponse(StatusResponse.ERROR));
                }
            });
        });

        LOG.debug("Initialised routes");
    }

    private static void handleInvalidInput(Exception e, Request request, Response response) {
        response.status(400);
        errorResponse(e, request, response);
    }

    private static void errorResponse(Exception e, Request request, Response response) {
        response.type(JSON);
        response.body(GSON.toJson(Collections.singletonMap("error", e.getMessage())));
    }

    /**
     * For testing, as we want to start and stop the server.
     */
    public static void stopServer() {
        LOG.debug("Asking server to stop");
        Spark.stop();
    }

}
