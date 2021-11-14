package com.webox.stocktradeservice;

import java.util.Arrays;
import java.util.List;

public class StockTradeServiceApplication {

    public static void main(String[] args) {
        System.out.println("#############################################");
        System.out.println("#                                           #");
        System.out.println("#      Share Market Trade Simulator         #");
        System.out.println("#        (Yesterday Market Trade)           #");
        System.out.println("#                                           #");
        System.out.println("#############################################\r\n");

        System.out.println("#Show Case Of Price Up And Down.");
        System.out.println("#Share Market Trade Price List:");
        List<String> stockPricesUpAndDown = Arrays.asList("3", "20", "19.15", "18", "25.23", "13", "10", "9", "8", "7","6");
        System.out.println(stockPricesUpAndDown);
        printMaxProfitTradeDetail(stockPricesUpAndDown);

        System.out.println("#Show Case Of Price Keep Up.");
        System.out.println("#Share Market Trade Price List:");
        List<String> stockPricesKeepUp = Arrays.asList("3.00", "3.11", "3.15", "4.00", "4.23", "4.24", "4.55", "5", "5.8", "5.99", "6");
        System.out.println(stockPricesKeepUp);
        printMaxProfitTradeDetail(stockPricesKeepUp);

        System.out.println("#Show Case Of Price Keep Down.");
        System.out.println("#Share Market Trade Price List:");
        List<String> stockPricesKeepDown = Arrays.asList("6.00", "5.99", "5.15", "5.00", "4.99", "4.24", "4.03", "4", "3.8", "3.79", "3.76");
        System.out.println(stockPricesKeepDown);
        printMaxProfitTradeDetail(stockPricesKeepDown);

        System.out.println("#Show Case Of Price Stay Still.");
        System.out.println("#Share Market Trade Price List:");
        List<String> stockPricesKeepStill = Arrays.asList("4.00", "4.00", "4.00", "4.00", "4", "4", "4", "4", "4", "4",
                "4");
        System.out.println(stockPricesKeepStill);
        printMaxProfitTradeDetail(stockPricesKeepStill);

        System.out.println("#Show Case Of Data Broken issue.");
        System.out.println("#Share Market Trade Price List (Broken Data):");
        List<String> stockPricesDataBroken = Arrays.asList("4.00", "4.00", "4.00", "4.00", "brokenData", "4", "4", "4", "4", "4", "4");
        System.out.println(stockPricesDataBroken);
        printMaxProfitTradeDetail(stockPricesDataBroken);
    }

    private static void printMaxProfitTradeDetail(List<String> stockPrices) {
        StockPriceProfitCalculator stockPriceProfitCalculator = new StockPriceProfitCalculator();
        try {
            StockPriceProfitCalculator.TradeInfo maxProfitTradeDetail = stockPriceProfitCalculator
                    .getMaxProfitTradeInfo(stockPrices);

            System.out.println(".......................");
            if (maxProfitTradeDetail.isProfitableTrade()) {
                System.out.println("The max profit price was $"
                        + (maxProfitTradeDetail.getSellPrice().subtract(maxProfitTradeDetail.getBuyPrice()))
                        + " per share.");
                System.out.println("Trade Transaction: Bought the stock at " + maxProfitTradeDetail.getBuyTime()
                        + " with price $" + maxProfitTradeDetail.getBuyPrice() + " and sold the stock at "
                        + maxProfitTradeDetail.getSellTime() + " with price $" + maxProfitTradeDetail.getSellPrice()
                        + " .");
            } else {
                System.out.println("No chance you could make profits based on stock records");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Sorry, the stock data are broken. You may provide validate stock data to calculate the profits.");
        }

        System.out.println("\r\n");
    }

}
