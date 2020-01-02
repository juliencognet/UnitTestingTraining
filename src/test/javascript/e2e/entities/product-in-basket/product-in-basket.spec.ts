import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductInBasketComponentsPage, ProductInBasketDeleteDialog, ProductInBasketUpdatePage } from './product-in-basket.page-object';

const expect = chai.expect;

describe('ProductInBasket e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productInBasketComponentsPage: ProductInBasketComponentsPage;
  let productInBasketUpdatePage: ProductInBasketUpdatePage;
  let productInBasketDeleteDialog: ProductInBasketDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductInBaskets', async () => {
    await navBarPage.goToEntity('product-in-basket');
    productInBasketComponentsPage = new ProductInBasketComponentsPage();
    await browser.wait(ec.visibilityOf(productInBasketComponentsPage.title), 5000);
    expect(await productInBasketComponentsPage.getTitle()).to.eq('Product In Baskets');
  });

  it('should load create ProductInBasket page', async () => {
    await productInBasketComponentsPage.clickOnCreateButton();
    productInBasketUpdatePage = new ProductInBasketUpdatePage();
    expect(await productInBasketUpdatePage.getPageTitle()).to.eq('Create or edit a Product In Basket');
    await productInBasketUpdatePage.cancel();
  });

  it('should create and save ProductInBaskets', async () => {
    const nbButtonsBeforeCreate = await productInBasketComponentsPage.countDeleteButtons();

    await productInBasketComponentsPage.clickOnCreateButton();
    await promise.all([
      productInBasketUpdatePage.setQuantityInput('5'),
      productInBasketUpdatePage.productSelectLastOption(),
      productInBasketUpdatePage.basketSelectLastOption()
    ]);
    expect(await productInBasketUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    await productInBasketUpdatePage.save();
    expect(await productInBasketUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productInBasketComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductInBasket', async () => {
    const nbButtonsBeforeDelete = await productInBasketComponentsPage.countDeleteButtons();
    await productInBasketComponentsPage.clickOnLastDeleteButton();

    productInBasketDeleteDialog = new ProductInBasketDeleteDialog();
    expect(await productInBasketDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Product In Basket?');
    await productInBasketDeleteDialog.clickOnConfirmButton();

    expect(await productInBasketComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
