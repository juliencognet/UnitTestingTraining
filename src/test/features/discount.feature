Feature: Add and apply discount to the basket

Scenario: Add discount to the basket
    Given a valid Basket and a discount code  "TenPourcent"
     When I call save the discount code into the basket
     Then the discount code should be saved into the Basket

Scenario: Check that a discount is applied to the basket
    Given a BasketDTO that contains the DiscountCode "TenPourcent" and a Product with id 1 with a price of 50
    When the user save the basket
    Then the total price of the basket must be 45

