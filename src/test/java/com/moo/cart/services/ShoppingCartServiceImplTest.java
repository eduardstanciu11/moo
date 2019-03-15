package com.moo.cart.services;

import com.moo.cart.models.DummyCart;
import com.moo.cart.models.DummyProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceImplTest {

    ShoppingCartService shoppingCartService;
    DummyCart dummyCart;
    DummyProduct dummyProduct;

    @Before
    public void setup() {
        dummyCart = mock(DummyCart.class);
        dummyCart.setId("1");

        dummyProduct = mock(DummyProduct.class);
        dummyProduct.setId("1");
        dummyProduct.setName("Product 1");
    }

    @Test
    public void noProductsTest() throws Exception{
        shoppingCartService = new ShoppingCartServiceImpl();
        ArrayList<DummyProduct> allProducts = shoppingCartService.getProducts(dummyCart);

        assertEquals(null, allProducts);
    }

    @Test
    public void addProductTest() throws Exception{
        shoppingCartService = new ShoppingCartServiceImpl();
        shoppingCartService.addProduct(dummyCart, dummyProduct);

        ArrayList<DummyProduct> allProducts = shoppingCartService.getProducts(dummyCart);

        assertEquals(1, allProducts.size());
    }

    @Test
    public void addProductAndClearCartTest() throws Exception{
        shoppingCartService = new ShoppingCartServiceImpl();
        shoppingCartService.addProduct(dummyCart, dummyProduct);

        ArrayList<DummyProduct> allProducts = shoppingCartService.getProducts(dummyCart);

        assertEquals(1, allProducts.size());

        shoppingCartService.clearProducts(dummyCart);

        assertEquals(0, allProducts.size());
    }
}
