Feature: add a product to a cart
  Scenario: client makes call to POST /cart/id/add
    When the client calls /cart/id/add
    Then the client receives success code 200