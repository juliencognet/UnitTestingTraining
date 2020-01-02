import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiscountCodeComponentsPage, DiscountCodeDeleteDialog, DiscountCodeUpdatePage } from './discount-code.page-object';

const expect = chai.expect;

describe('DiscountCode e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let discountCodeComponentsPage: DiscountCodeComponentsPage;
  let discountCodeUpdatePage: DiscountCodeUpdatePage;
  let discountCodeDeleteDialog: DiscountCodeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DiscountCodes', async () => {
    await navBarPage.goToEntity('discount-code');
    discountCodeComponentsPage = new DiscountCodeComponentsPage();
    await browser.wait(ec.visibilityOf(discountCodeComponentsPage.title), 5000);
    expect(await discountCodeComponentsPage.getTitle()).to.eq('Discount Codes');
  });

  it('should load create DiscountCode page', async () => {
    await discountCodeComponentsPage.clickOnCreateButton();
    discountCodeUpdatePage = new DiscountCodeUpdatePage();
    expect(await discountCodeUpdatePage.getPageTitle()).to.eq('Create or edit a Discount Code');
    await discountCodeUpdatePage.cancel();
  });

  it('should create and save DiscountCodes', async () => {
    const nbButtonsBeforeCreate = await discountCodeComponentsPage.countDeleteButtons();

    await discountCodeComponentsPage.clickOnCreateButton();
    await promise.all([discountCodeUpdatePage.setCodeInput('code'), discountCodeUpdatePage.setDiscountInput('5')]);
    expect(await discountCodeUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await discountCodeUpdatePage.getDiscountInput()).to.eq('5', 'Expected discount value to be equals to 5');
    await discountCodeUpdatePage.save();
    expect(await discountCodeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await discountCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DiscountCode', async () => {
    const nbButtonsBeforeDelete = await discountCodeComponentsPage.countDeleteButtons();
    await discountCodeComponentsPage.clickOnLastDeleteButton();

    discountCodeDeleteDialog = new DiscountCodeDeleteDialog();
    expect(await discountCodeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Discount Code?');
    await discountCodeDeleteDialog.clickOnConfirmButton();

    expect(await discountCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
