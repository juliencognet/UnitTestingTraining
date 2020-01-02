import { element, by, ElementFinder } from 'protractor';

export class DiscountCodeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-discount-code div table .btn-danger'));
  title = element.all(by.css('jhi-discount-code div h2#page-heading span')).first();

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

export class DiscountCodeUpdatePage {
  pageTitle = element(by.id('jhi-discount-code-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  discountInput = element(by.id('field_discount'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setDiscountInput(discount: string): Promise<void> {
    await this.discountInput.sendKeys(discount);
  }

  async getDiscountInput(): Promise<string> {
    return await this.discountInput.getAttribute('value');
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

export class DiscountCodeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-discountCode-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-discountCode'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
