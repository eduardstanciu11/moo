package com.moo.cart.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DummyProductTest {
    @Test
    void shouldStoreCartId() {
        // Given

        // When
        DummyProduct product = new DummyProduct("1", "Product 1");

        // Then
        assertEquals("1", product.getId());
        assertEquals("Product 1", product.getName());
    }
}
