// e2e-tests/specs/student.test.ts
import { WebDriver, Builder, By, until } from 'selenium-webdriver';
import 'chromedriver';
import { expect } from 'chai';

describe('Student Management E2E Tests', function () {
  let driver: WebDriver;

  this.timeout(50000); // Set timeout for all tests in this suite

  before(async function () {
    driver = await new Builder().forBrowser('chrome').build();
  });

  after(async function () {
    if (driver) {
      await driver.quit();
    }
  });

  it('should list students', async function () {
    await driver.get('http://localhost:4200/students');
    const tableElement = await driver.wait(
      until.elementLocated(By.css('table')),
      5000
    );
    expect(await tableElement.isDisplayed()).to.be.true;
  });

  it('should create new student', async function () {
    await driver.get('http://localhost:4200/students/new');
    await driver.findElement(By.id('prenomEtudiant')).sendKeys('Test');
    await driver.findElement(By.id('nomEtudiant')).sendKeys('Student');
    await driver.findElement(By.id('cinEtudiant')).sendKeys('12345678');
    await driver.findElement(By.id('dateNaissance')).sendKeys('2000-01-01');
    await driver.findElement(By.css('.submit-btn')).click();
    await driver.wait(until.urlIs('http://localhost:4200/students'), 5000);
  });
});
