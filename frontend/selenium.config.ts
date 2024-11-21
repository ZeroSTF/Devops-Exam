export const config = {
  selenium: {
    hub: 'http://selenium-hub:4444/wd/hub',
    capabilities: {
      browserName: 'chrome',
      'goog:chromeOptions': {
        args: ['--headless', '--no-sandbox', '--disable-dev-shm-usage'],
      },
    },
  },
  baseUrl: 'http://frontend',
  waitTimeout: 10000,
};
