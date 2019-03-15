package com.moo.cart.services;

import com.moo.cart.models.DummyCart;
import com.moo.cart.models.DummyProduct;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartServiceImpl implements ShoppingCartService{
    private HashMap<DummyCart, ArrayList<DummyProduct>> cartsAndProducts;

    public ShoppingCartServiceImpl() {
        cartsAndProducts = new HashMap<>();
    }

    @Override
    public void addProduct(DummyCart cart, DummyProduct product) {
        // Check if the cart exists. If it doesn't, create it
        if(cartsAndProducts.get(cart) == null) {
            cartsAndProducts.put(cart, new ArrayList<>());
        }

        // Getting the cart to which we need to add
        ArrayList<DummyProduct> requestedCartProducts = cartsAndProducts.get(cart);
        requestedCartProducts.add(product);
    }

    @Override
    public ArrayList<DummyProduct> getProducts(DummyCart cart) {
        return cartsAndProducts.get(cart);
    }

    @Override
    public void clearProducts(DummyCart cart) {
        cartsAndProducts.get(cart).clear();
    }
}
