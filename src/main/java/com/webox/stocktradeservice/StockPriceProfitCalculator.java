package com.webox.stocktradeservice;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class StockPriceProfitCalculator {
    private static final LocalTime tradeOpenTime = LocalTime.of(10, 0);
    private int soldIndex;

    /**
     * Get the MaxProfit trade info detail based on stock prices array include:
     * bought time and price, sold time and price
     * 
     * @param stockPricesList as type of int array
     * @return TradeInfo
     */
    public TradeInfo getMaxProfitTradeInfo(List<String> stockPricesList) {
        BigDecimal[] stockPrices = convertPriceListToBigDecimalArray(stockPricesList);

        int indexLen = stockPrices.length;
        // calculate difference between adjacent stock prices
        BigDecimal[] diff = getDiffArray(stockPrices);
        // find out the max sum of the difference in subarray from the stock prices
        BigDecimal maxProfit = getMaxSubDiffArr(diff, indexLen - 1);

        // find out the buying index
        BigDecimal boughtPrice = stockPrices[this.soldIndex].subtract(maxProfit);
        int boughtIndex = findIndex(stockPrices, boughtPrice);
        StockPriceProfitCalculator.TradeInfo tradeInfo;
        if (boughtIndex < 0 || maxProfit.compareTo(BigDecimal.valueOf(0)) <= 0) {
            tradeInfo = TradeInfo.builder().profitableTrade(false).build();
        } else {
            tradeInfo = TradeInfo.builder().buyPrice(stockPrices[boughtIndex])
                    .buyTime(getTradeTime(boughtIndex)).sellPrice(stockPrices[this.soldIndex])
                    .sellTime(getTradeTime(this.soldIndex)).profitableTrade(true).build();
        }
        return tradeInfo;
    }

    private BigDecimal[] getDiffArray(BigDecimal[] stockPrices) {
        int indexLen = stockPrices.length;
        BigDecimal[] diff = new BigDecimal[indexLen - 1];
        // store the difference in the array
        for (int i = 0; i < indexLen - 1; i++) {
            diff[i] = stockPrices[i + 1].subtract(stockPrices[i]);
        }
        return diff;
    }

    // calculate the max sum of difference
    private BigDecimal getMaxSubDiffArr(BigDecimal[] diff, int size) {
        BigDecimal maxSum = diff[0];
        BigDecimal maxSumEnding = diff[0];
        for (int i = 1; i < size; i++) {
            if (maxSumEnding.add(diff[i]).compareTo(diff[i]) > 0) {
                maxSumEnding = maxSumEnding.add(diff[i]);
            } else {
                maxSumEnding = diff[i];
            }
            if (maxSum.compareTo(maxSumEnding) < 0) {
                maxSum = maxSumEnding;
                //locate the highest price index
                this.soldIndex = i + 1;
            }
        }
        return maxSum;
    }

    // find the index in array by value, here is for searching the when the stock
    // should be bought for maximum profit
    private static int findIndex(BigDecimal[] arr, BigDecimal t) {
        int index = -1;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(t) == 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static BigDecimal[] convertPriceListToBigDecimalArray(List<String> stockPriceList) {
        if (stockPriceList == null) {
            raiseError("Stock info can't be null.");
        }
        int stockSize = stockPriceList.size();
        if (stockSize == 0) {
            raiseError("Stock info can't be empty.");
        }

        BigDecimal[] stockPrices = new BigDecimal[stockSize];
        int count = 0;
        try {
            for (String value : stockPriceList) {
                stockPrices[count] = new BigDecimal(value);
                count++;
            }
        } catch (NumberFormatException e) {
            raiseError("NumberFormatException");
        } catch (NullPointerException e) {
            raiseError("NullPointerException");
        } catch (Exception e) {
            raiseError("Exception");
        }
        return stockPrices;
    }

    private static LocalTime getTradeTime(int index) {
        return tradeOpenTime.plusMinutes(index);
    }

    private static void raiseError(String error, Object... args) {
        String message = String.format(error, args);
        throw new IllegalArgumentException(message);
    }

    @Data
    @Builder
    static public class TradeInfo {
        private LocalTime buyTime;
        private LocalTime sellTime;
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;
        private boolean profitableTrade;

    }
}
