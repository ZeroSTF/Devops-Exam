import { Builder, WebDriver } from 'selenium-webdriver';
import 'chromedriver';

export async function setupDriver(): Promise<WebDriver> {
  // Configure Chrome options
  const chrome = require('selenium-webdriver/chrome');
  const options = new chrome.Options();

  // Add options for CI/CD environments if needed
  options.addArguments('--headless');
  options.addArguments('--no-sandbox');
  options.addArguments('--disable-dev-shm-usage');

  return new Builder().forBrowser('chrome').setChromeOptions(options).build();
}
