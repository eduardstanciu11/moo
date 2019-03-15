Feature: get all products from the cart
  Scenario: client adds a product and makes call to GET /cart/id/getProducts
    When the client calls /cart/id/add
    And the client calls /cart/id/getProducts
    Then the number of products returned is 1