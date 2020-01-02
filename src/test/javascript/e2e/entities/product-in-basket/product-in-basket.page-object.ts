import { element, by, ElementFinder } from 'protractor';

export class ProductInBasketComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-in-basket div table .btn-danger'));
  title = element.all(by.css('jhi-product-in-basket div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class ProductInBasketUpdatePage {
  pageTitle = element(by.id('jhi-product-in-basket-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantityInput = element(by.id('field_quantity'));
  productSelect = element(by.id('field_product'));
  basketSelect = element(by.id('field_basket'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async productSelectLastOption(): Promise<void> {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option: string): Promise<void> {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption(): Promise<string> {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async basketSelectLastOption(): Promise<void> {
    await this.basketSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async basketSelectOption(option: string): Promise<void> {
    await this.basketSelect.sendKeys(option);
  }

  getBasketSelect(): ElementFinder {
    return this.basketSelect;
  }

  async getBasketSelectedOption(): Promise<string> {
    return await this.basketSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProductInBasketDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productInBasket-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productInBasket'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
