import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productComponentsPage: ProductComponentsPage;
  let productUpdatePage: ProductUpdatePage;
  let productDeleteDialog: ProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Products', async () => {
    await navBarPage.goToEntity('product');
    productComponentsPage = new ProductComponentsPage();
    await browser.wait(ec.visibilityOf(productComponentsPage.title), 5000);
    expect(await productComponentsPage.getTitle()).to.eq('Products');
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle()).to.eq('Create or edit a Product');
    await productUpdatePage.cancel();
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

    await productComponentsPage.clickOnCreateButton();
    await promise.all([
      productUpdatePage.setProductNameInput('productName'),
      productUpdatePage.setUnitPriceInput('5'),
      productUpdatePage.setEan13BarCodeInput('ean13BarCode'),
      productUpdatePage.setBrandInput('brand'),
      productUpdatePage.setCategoriesInput('categories'),
      productUpdatePage.setImageUrlInput('imageUrl')
    ]);
    expect(await productUpdatePage.getProductNameInput()).to.eq('productName', 'Expected ProductName value to be equals to productName');
    expect(await productUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await productUpdatePage.getEan13BarCodeInput()).to.eq(
      'ean13BarCode',
      'Expected Ean13BarCode value to be equals to ean13BarCode'
    );
    expect(await productUpdatePage.getBrandInput()).to.eq('brand', 'Expected Brand value to be equals to brand');
    expect(await productUpdatePage.getCategoriesInput()).to.eq('categories', 'Expected Categories value to be equals to categories');
    expect(await productUpdatePage.getImageUrlInput()).to.eq('imageUrl', 'Expected ImageUrl value to be equals to imageUrl');
    await productUpdatePage.save();
    expect(await productUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Product', async () => {
    const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
    await productComponentsPage.clickOnLastDeleteButton();

    productDeleteDialog = new ProductDeleteDialog();
    expect(await productDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Product?');
    await productDeleteDialog.clickOnConfirmButton();

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
