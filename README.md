# alpha-test-task

The app tracks YOUR_CURRENCY daily exchnage rate (to USD only!) change to and returns a gif.
URL: http://localhost:8080/v1/currency/{YOUR_CURRENCY} 
Supported currencies: https://openexchangerates.org/api/currencies.json

To run the app with docker: 
1) Get YOUR_API_KEY at https://developers.giphy.com and YOUR_APP_ID at https://docs.openexchangerates.org
2) Download this repository
3) docker container run -dp 8080:8080 --env GIPHY_APIKEY={YOUR_API_KEY} --env OPENEXCHANGE_APIID={YOUR_APP_ID} richbroken:0.0.1-SNAPSHOT 
