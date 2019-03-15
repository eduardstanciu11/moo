package com.moo.cart.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyCartTest {

    @Test
    void shouldStoreCartId() {
        // Given

        // When
        DummyCart cart = new DummyCart("ABC");

        // Then
        assertEquals("ABC", cart.getId());
    }
}