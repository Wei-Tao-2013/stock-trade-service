package com.webox.stocktradeservice;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StockPriceProfitCalculatorTests {

    private StockPriceProfitCalculator stockPriceProfitCalculator;

    @Before
    public void setUp() {
        stockPriceProfitCalculator = new StockPriceProfitCalculator();
    }

    @Test
    public void shouldReturnProfitPriceAt22WhenStockPriceUpAndDown() {
        int[] stockPricesYesterday = { 3, 20, 19, 18, 25, 13, 10, 9, 8, 7, 6 };
        StockPriceProfitCalculator.TradeInfo maxProiftTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertEquals(22, maxProiftTradeDetail.getSellPrice() - maxProiftTradeDetail.getBuyPrice());
    }

    @Test
    public void shouldReturnProfitPriceAt8WhenStockPriceKeepUp() {
        int[] stockPricesYesterday = { 3, 4, 4, 5, 6, 7, 7, 9, 10, 11, 11 };
        StockPriceProfitCalculator.TradeInfo maxProiftTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertEquals(8, maxProiftTradeDetail.getSellPrice() - maxProiftTradeDetail.getBuyPrice());
    }

    @Test
    public void shouldReturnNonProfitTradeWhenStockPriceKeepDown() {
        int[] stockPricesYesterday = { 21, 20, 18, 17, 13, 10, 9, 8, 7, 6, 1 };
        StockPriceProfitCalculator.TradeInfo maxProiftTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertEquals(false, maxProiftTradeDetail.isProfitableTrade());
    }

    @Test
    public void shouldReturnNonProfitTradeWhenStockPriceKeepStill() {
        int[] stockPricesYesterday = { 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21 };
        StockPriceProfitCalculator.TradeInfo maxProiftTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertEquals(false, maxProiftTradeDetail.isProfitableTrade());
    }

    @Test
    public void shouldThrowExceptionWhenStockInfoIsNull() {
        try {
            stockPriceProfitCalculator.getMaxProfitTradeInfo(null);
            fail("expecting IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Stock info can't be empty or null");
        }

    }

    @Test
    public void shouldThrowExceptionWhenStockInfoIsEmpty() {
        try {
            int[] stockPricesYesterday = {};
            stockPriceProfitCalculator.getMaxProfitTradeInfo(stockPricesYesterday);
            fail("expecting IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Stock info can't be empty or null");

        }

    }

}
