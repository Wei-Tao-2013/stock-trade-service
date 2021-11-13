package com.webox.stocktradeservice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;

public class StockPriceProfitCalculator {
    private static final LocalTime tradeOpenTime = LocalTime.of(10, 0);
    private static final LocalTime tradeCloseTime = LocalTime.of(16, 0);
    private int soldIndex;
    private int boughtIndex;
    private StockPriceProfitCalculator.TradeInfo tradeInfo;

    public int getMaxProfit(int stockPrices[]) {
        int indexLen = stockPrices.length;
        // calculate difference between adjacent stock prices
        int[] diff = getDiffArray(stockPrices);
        // find out the max sum of the difference in subarray from the stock prices
        int maxProfit = getMaxSubArry(diff, indexLen - 1);
        return maxProfit;
    }

    public TradeInfo getMaxProfitTradeInfo(int stockPrices[]) {
        if (stockPrices == null || stockPrices.length == 0) {
            raiseError("Stock info can't be empty or null");
          }

        int indexLen = stockPrices.length;
        // calculate difference between adjacent stock prices
        int[] diff = getDiffArray(stockPrices);
        // find out the max sum of the difference in subarray from the stock prices
        int maxProfit = getMaxSubArry(diff, indexLen - 1);

        // find out the buying index
        int boughtPrice = stockPrices[this.soldIndex] - maxProfit;
        this.boughtIndex = findIndex(stockPrices, boughtPrice);

        if (this.boughtIndex < 0 || maxProfit <= 0) {
            this.tradeInfo = TradeInfo.builder().profitableTrade(false).build();
        } else {
            this.tradeInfo = TradeInfo.builder().buyPrice(stockPrices[this.boughtIndex])
                    .buyTime(getTradeTime(this.boughtIndex)).sellPrice(stockPrices[this.soldIndex])
                    .sellTime(getTradeTime(this.soldIndex)).profitableTrade(true).build();
        }

        return tradeInfo;
    }

    private int[] getDiffArray(int stockPrices[]) {
        int indexLen = stockPrices.length;
        int[] diff = new int[indexLen - 1];
        for (int i = 0; i < indexLen - 1; i++) {
            diff[i] = stockPrices[i + 1] - stockPrices[i];
        }
        return diff;
    }

    private int getMaxSubArry(int diff[], int size) {
        int maxSum = diff[0];
        int maxSumEnding = diff[0];
        for (int i = 1; i < size; i++) {
            if (maxSumEnding + diff[i] > diff[i]) {
                maxSumEnding = maxSumEnding + diff[i];
            } else {
                maxSumEnding = diff[i];
            }
            if (maxSum < maxSumEnding) {
                maxSum = maxSumEnding;
                this.soldIndex = i + 1;
            }
        }
        return maxSum;
    }

    private static int findIndex(int arr[], int t) {
        ArrayList<Integer> clist = new ArrayList<>();
        for (int i : arr)
            clist.add(i);
        // returning index of the element
        return clist.indexOf(t);
    }

    private static LocalTime getTradeTime(int index) {
        return tradeOpenTime.plusMinutes(index);

    }
    private static void raiseError(String error, Object ...args) {
        String message = String.format(error, args);
        throw new IllegalArgumentException(message);
    }

    @Data
    @Builder
    static public class TradeInfo {
        private LocalTime buyTime;
        private LocalTime sellTime;
        private int buyPrice;
        private int sellPrice;
        private boolean profitableTrade;

    }
}
