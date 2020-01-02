import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
  title = element.all(by.css('jhi-product div h2#page-heading span')).first();

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

export class ProductUpdatePage {
  pageTitle = element(by.id('jhi-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productNameInput = element(by.id('field_productName'));
  unitPriceInput = element(by.id('field_unitPrice'));
  ean13BarCodeInput = element(by.id('field_ean13BarCode'));
  brandInput = element(by.id('field_brand'));
  categoriesInput = element(by.id('field_categories'));
  imageUrlInput = element(by.id('field_imageUrl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setProductNameInput(productName: string): Promise<void> {
    await this.productNameInput.sendKeys(productName);
  }

  async getProductNameInput(): Promise<string> {
    return await this.productNameInput.getAttribute('value');
  }

  async setUnitPriceInput(unitPrice: string): Promise<void> {
    await this.unitPriceInput.sendKeys(unitPrice);
  }

  async getUnitPriceInput(): Promise<string> {
    return await this.unitPriceInput.getAttribute('value');
  }

  async setEan13BarCodeInput(ean13BarCode: string): Promise<void> {
    await this.ean13BarCodeInput.sendKeys(ean13BarCode);
  }

  async getEan13BarCodeInput(): Promise<string> {
    return await this.ean13BarCodeInput.getAttribute('value');
  }

  async setBrandInput(brand: string): Promise<void> {
    await this.brandInput.sendKeys(brand);
  }

  async getBrandInput(): Promise<string> {
    return await this.brandInput.getAttribute('value');
  }

  async setCategoriesInput(categories: string): Promise<void> {
    await this.categoriesInput.sendKeys(categories);
  }

  async getCategoriesInput(): Promise<string> {
    return await this.categoriesInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
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

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-product-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-product'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
