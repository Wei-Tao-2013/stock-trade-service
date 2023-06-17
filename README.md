# Application Description

### Suppose we could access yesterday’s stock prices as a list, where:

* The indices are the time in minutes past trade opening time, which was 10:00am local time.
* The values are the price in dollars of the Latitude Financial stock at that time.
* So if the stock cost $5 at 11:00am, stock_prices_yesterday[60] = 5.
* You must buy before you sell. You may not buy and sell in the same time step (at least 1 minute must pass).

### For example:

Given stock price as below 

[3, 20, 19.15, 18, 25.23, 13, 10, 9, 8, 7, 6]

The application will give the below detail trade transaction that demonstrates how did you make the maximum profit by buy & sell. 

* The max profit price was $22.23 per share.

* Trade Transaction: Bought the stock at 10:00 with price $3 and sold the stock at 10:04 with price $25.23 .

  
# Getting Started
## Application Setup

### Running on local (develop mode, Review code by IDE intellij or VSCode IDE ) 
* Clone repository from GitHub 
```
git clone https://github.com/Wei-Tao-2013/stock-trade-service.git
``` 
* Check local Java version in terminal, make sure java version is 11, otherwise set JAVA_HOME to JAVA 11. 
``` 
java -version
```  
* Build the project
```
cd stock-trade-service
```
```
./mvnw clean package
```  
* Run unit tests
```
./mvnw -Dtest=StockPriceProfitCalculatorTests test
```
* Run the app
```
java -jar target/stock-trade-service-1.0.jar
```
### There are 5 scenarios demonstrated the use cases in terminal

  ![description](app-screen-shot.jpg?raw=true)


### Running application in Docker 
* Login jfrog as guest

```
docker login webox.jfrog.io
```
* enter username and password as below

username:
```
guest
```
password:
```
###############
```
* run docker image

```
docker run webox.jfrog.io/default-docker-local/stock-trade:1.0.1
```
* docker logout
```
docker logout webox.jfrog.io
```
