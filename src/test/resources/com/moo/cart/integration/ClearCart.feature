Feature: add a product to a cart and clear it
  Scenario: client adds a product and clears the cart the client gets an empty list of products
    When the client calls /cart/id/add
    And the client calls /cart/id/clear
    And the client calls /cart/id/getProducts
    Then the number of products returned is 0