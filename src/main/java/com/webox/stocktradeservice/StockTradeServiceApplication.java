package com.webox.stocktradeservice;

public class StockTradeServiceApplication {

    public static void main(String[] args) {

        System.out.println("---Share Market Trade ----");

        // int[] stockPricesYesterday = {21,20,18,17,13,10,9,8,7,6,1};

        // int[] stockPricesYesterday = {21,21,19,19,17,19,10,9,8,7,6,1};

        int[] stockPricesYesterday = { 3, 20, 19, 18, 25, 13, 10, 9, 8, 7, 6 };

        // int[] stockPricesYesterday = {1,13,1,3,19,7,2};
        
        StockPriceProfitCalculator stockPriceProfitCalculator = new StockPriceProfitCalculator();
        StockPriceProfitCalculator.TradeInfo maxProiftTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        if (maxProiftTradeDetail.isProfitableTrade()) {
            System.out.println("The max profit is $"
                    + (maxProiftTradeDetail.getSellPrice() - maxProiftTradeDetail.getBuyPrice())
                    + " per share if you bought the stock at " + maxProiftTradeDetail.getBuyTime() + " with price $"
                    + maxProiftTradeDetail.getBuyPrice() + " and sold the stock at "
                    + maxProiftTradeDetail.getSellTime() + " with price $" + maxProiftTradeDetail.getSellPrice());
        } else {
            System.out.println("No profits you could make as per stock records");
        }
    }

}
