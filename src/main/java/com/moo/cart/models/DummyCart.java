package com.moo.cart.models;

import java.util.Objects;

public class DummyCart {

    private String id;

    public DummyCart(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        // null check
        if (o == null) {
            return false;
        }

        // this instance check
        if (this == o) {
            return true;
        }

        // instanceof check and actual value check
        if ((o instanceof DummyCart) && (((DummyCart) o).getId().equals(this.id))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
