package com.moo.cart.services;

import com.moo.cart.models.DummyCart;
import com.moo.cart.models.DummyProduct;

import java.util.ArrayList;

public interface ShoppingCartService {
    public void addProduct(DummyCart cart, DummyProduct product);

    public ArrayList<DummyProduct> getProducts(DummyCart cart);

    public void clearProducts(DummyCart cart);
}
