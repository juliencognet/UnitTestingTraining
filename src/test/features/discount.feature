Feature: Add and apply discount to the basket

    Scenario Outline: Check that a discount is applied to the basket
        Given a discount code name "<DiscountCodeName>" which provides a discount of <DiscountPercent> per cent
        And an empty product basket
        When the user adds a product with a unit price of <unitPrice> dollars
        Then the total price of the basket must be <totalPrice>

        Examples:
        | DiscountCodeName | DiscountPercent | unitPrice | totalPrice |
        | 10POURCENT       | 0.1             | 100       | 90         |
        | 10POURCENT       | 0.1             | 200       | 180        |
        | 20POURCENT       | 0.2             | 100       | 80         |
