*** Keywords ***
Open Browser To Home page
    Open Browser    ${LOGIN URL}    ${BROWSER}
    Title Should Be    FIC Testing

Login to web application
    Click Link                  account-menu
    Click Link                  login
    Input Text                  username  admin
    Input Password              password  admin
    Click Button                Sign in

Select customer Delmer Goldner
    Wait Until Page Contains Element    btnSelectCustomer_8  10
    Click Button                        btnSelectCustomer_8

Add Epson Projector to basket
    Wait Until Page Contains Element    btnAddProduct_1  10
    Click Button                        btnAddProduct_1
    Wait Until Page Contains            A new productInBasket is created
    Wait Until Element Contains         badgeNbProductsInBasket  1 product(s)  3

Go to basket and check total price
    Click Button    btnBasket
    Wait Until Page Contains Element   spanTotalPrice
    Element Should Contain  spanTotalPrice  599

Remove Projector and check total price
    Click Button    xpath:(//button[contains(text(),"Remove")])[1]
    Wait Until Element Contains         spanTotalPrice  0

