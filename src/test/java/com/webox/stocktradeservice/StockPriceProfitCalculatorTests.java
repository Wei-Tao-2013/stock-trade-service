package com.webox.stocktradeservice;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StockPriceProfitCalculatorTests {
    private StockPriceProfitCalculator stockPriceProfitCalculator;

    @Before
    public void setUp() {
        stockPriceProfitCalculator = new StockPriceProfitCalculator();
    }

    @Test
    public void shouldReturnProfitPriceAt22WhenStockPriceUpAndDown() {
        List<String> stockPriceList = Arrays.asList("3", "20", "19.1", "18", "25", "13", "10", "9", "8", "7", "6");
        StockPriceProfitCalculator.TradeInfo maxProfitTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPriceList);
        assertEquals(0, maxProfitTradeDetail.getSellPrice().subtract(maxProfitTradeDetail.getBuyPrice())
                .compareTo(new BigDecimal(22)));
        assertEquals("10:00", String.valueOf(maxProfitTradeDetail.getBuyTime()));
        assertEquals("10:04", String.valueOf(maxProfitTradeDetail.getSellTime()));
    }

    @Test
    public void shouldReturnProfitPriceAt8WhenStockPriceKeepUp() {
        List<String> stockPricesYesterday = Arrays.asList("3", "4", "4", "5.1", "6", "7", "7", "9", "9.8", "10", "11");
        StockPriceProfitCalculator.TradeInfo maxProfitTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertEquals(0, maxProfitTradeDetail.getSellPrice().subtract(maxProfitTradeDetail.getBuyPrice())
                .compareTo(new BigDecimal(8)));
        assertEquals("10:00", String.valueOf(maxProfitTradeDetail.getBuyTime()));
        assertEquals("10:10", String.valueOf(maxProfitTradeDetail.getSellTime()));
    }

    @Test
    public void shouldReturnNonProfitTradeWhenStockPriceKeepDown() {
        List<String> stockPricesYesterday = Arrays.asList("21", "20", "18", "17.13", "13", "10.00", "9", "8", "7.00",
                "6.00", "1.1");
        StockPriceProfitCalculator.TradeInfo maxProfitTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertFalse(maxProfitTradeDetail.isProfitableTrade());
    }

    @Test
    public void shouldReturnNonProfitTradeWhenStockPriceKeepStill() {
        List<String> stockPricesYesterday = Arrays.asList("5", "5", "5", "5", "5", "5", "5", "5", "5.00", "5.00", "5");
        StockPriceProfitCalculator.TradeInfo maxProfitTradeDetail = stockPriceProfitCalculator
                .getMaxProfitTradeInfo(stockPricesYesterday);
        assertFalse(maxProfitTradeDetail.isProfitableTrade());
    }

    @Test
    public void shouldThrowNumberFromatExceptionWhenStockPriceDataHasNonDigitalNumber() {
        List<String> stockPricesYesterday = Arrays.asList("2.3", "non-number", "2");
        try {
            stockPriceProfitCalculator.getMaxProfitTradeInfo(stockPricesYesterday);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "NumberFormatException");
        }

    }

    @Test
    public void shouldThrowNullPointerExceptionWhenStockPriceDataHasNull() {
        List<String> stockPricesYesterday = Arrays.asList("2.3", null, "2");
        try {
            stockPriceProfitCalculator.getMaxProfitTradeInfo(stockPricesYesterday);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "NullPointerException");
        }
    }

    @Test
    public void shouldThrowExceptionWhenStockInfoIsNull() {
        try {
            stockPriceProfitCalculator.getMaxProfitTradeInfo(null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Stock info can't be null.");
        }

    }

    @Test
    public void shouldThrowExceptionWhenStockInfoIsEmpty() {
        List<String> stockPricesYesterday = Collections.emptyList();
        try {
            stockPriceProfitCalculator.getMaxProfitTradeInfo(stockPricesYesterday);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Stock info can't be empty.");

        }

    }

}
