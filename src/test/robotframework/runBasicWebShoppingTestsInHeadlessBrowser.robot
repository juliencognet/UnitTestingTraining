*** Settings ***
Documentation     Simple product basket example using Selenium run inside Headless Chrome
Library           SeleniumLibrary
Resource          basicWebShoppingTestKeywords.robot

*** Variables ***
${LOGIN URL}      http://localhost:9000
${BROWSER}        HeadlessFirefox

*** Test Cases ***
Check basic features of online web shop
    Open Browser To Home page
    Login to web application
    Select customer Delmer Goldner
    Add Epson Projector to basket
    Go to basket and check total price
    Remove Projector and check total price
    [Teardown]    Close Browser


