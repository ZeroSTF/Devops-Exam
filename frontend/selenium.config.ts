export const config = {
  seleniumAddress: 'http://localhost:4444/wd/hub',
  baseUrl: 'http://localhost:80',
  capabilities: {
    browserName: 'chrome',
    chromeOptions: {
      args: ['--headless', '--no-sandbox', '--disable-dev-shm-usage'],
    },
  },
  framework: 'mocha',
  specs: ['e2e-tests/**/*.ts'],
  mochaOpts: {
    reporter: 'spec',
    timeout: 30000,
  },
};

// "@types/jasmine": "latest",
// "@types/selenium-webdriver": "^4.1.20",
// "selenium-webdriver": "^4.18.1",
// "chromedriver": "latest",
// "geckodriver": "latest",
// "mocha": "^10.2.0",
// "ts-mocha": "^10.0.0",
// "@types/mocha": "^10.0.1",
// "mocha-junit-reporter": "^2.2.0",
