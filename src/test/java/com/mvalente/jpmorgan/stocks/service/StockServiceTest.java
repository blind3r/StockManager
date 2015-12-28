package com.mvalente.jpmorgan.stocks.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvalente.jpmorgan.stocks.model.Trade;
import com.mvalente.jpmorgan.stocks.model.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-config.xml" })
public class StockServiceTest {

	static final Logger logger = LogManager.getLogger(StockServiceTest.class
			.getName());

	static final String[] stockSymbols = { "TEA", "POP", "ALE", "GIN", "JOE" };

	@Autowired
	private StockService stockService;

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	@Test
	public void calculateDividendYieldTest() {
		logger.info("Begin test calculateDividendYield for price 50");

		try {
			for (String symbol : stockSymbols) {
				double dividend = stockService
						.calculateYeldDividend(symbol, 50);

				logger.info(symbol + " yeld dividend: " + dividend);

				switch (symbol) {
				case "TEA":
					Assert.assertTrue(dividend == 0);
					break;
				case "POP":
					Assert.assertTrue(dividend == 0.16);
					break;
				case "ALE":
					Assert.assertTrue(dividend == 0.46);
					break;
				case "GIN":
					Assert.assertTrue(dividend == 0.4);
					break;
				case "JOE":
					Assert.assertTrue(dividend == 0.26);
					break;
				}
			}

		} catch (Exception exception) {
			Assert.assertTrue(false);
		}

		logger.info("CalculateDividendYieldTest test OK");
		logger.info("-----------------------------------------------------------");
	}

	@Test
	public void calculatePriceEarningsRatioTest() {
		logger.info("Begin test calculatePriceEarningsRatio for price 50");

		try {
			for (String symbol : stockSymbols) {
				double priceEarnings = stockService
						.calculatePriceEarningsRatio(symbol, 50);

				logger.info(symbol + " price earnings ratio: " + priceEarnings);

				switch (symbol) {
				case "TEA":
					Assert.assertTrue(Double.isInfinite(priceEarnings));
					break;
				case "POP":
					Assert.assertTrue(priceEarnings == 6.25);
					break;
				case "ALE":
					Assert.assertTrue(priceEarnings == 2.1739130434782608);
					break;
				case "GIN":
					Assert.assertTrue(priceEarnings == 6.25);
					break;
				case "JOE":
					Assert.assertTrue(priceEarnings == 3.8461538461538463);
					break;
				}

			}

		} catch (Exception exception) {
			Assert.assertTrue(false);
		}

		logger.info("CalculatePriceEarningsRatio test OK");
		logger.info("-----------------------------------------------------------");
	}

	@Test
	public void recordTradeTest() {
		logger.info("Begin test recordTrade");
		boolean find = false;
		
		try {
			Date date = new Date();
			stockService.recordTrade("GIN", date, 10, 50, TradeType.BUY);

			List<Trade> trades = stockService.loadTrades();
			for (Trade trade : trades) {
				if ("GIN".equals(trade.getStock().getSymbol())
						&& date.equals(trade.getDate())
						&& trade.getQuantity() == 10 && trade.getPrice() == 50
						&& trade.getTradeType() == TradeType.BUY) {
					find = true;
					break;
				}
			}
			Assert.assertTrue(find);

		} catch (Exception exception) {
			Assert.assertTrue(false);
		}

		logger.info("RecordTradeTest test OK");
		logger.info("-----------------------------------------------------------");
	}

	@Test
	public void calculateStockPriceTest() {
		logger.info("Begin test calculateStockPrice");

		stockService.clearTrades();
		
		Date date = new Date();
		stockService.recordTrade("TEA", date, 30, 50, TradeType.BUY);
		stockService.recordTrade("POP", date, 10, 60, TradeType.BUY);
		stockService.recordTrade("ALE", date, 5, 20, TradeType.SELL);
		stockService.recordTrade("POP", date, 10, 55, TradeType.BUY);
		
		// make sure it doesnt add trades from more than the interval provided (15 minutes)
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -30);
		stockService.recordTrade("POP", cal.getTime(), 10, 60, TradeType.BUY);
		
		double price = stockService.calculateStockPrice("POP", 15);
		// (600 + 550) / 20 = 57.5
		
		Assert.assertTrue(price == 57.5);
		
		logger.info("calculateStockPrice test OK");
		logger.info("-----------------------------------------------------------");
	}

	@Test
	public void calculateGBCETest() {
		logger.info("-----------------------------------------------------------");
		logger.info("Begin test calculateGBCE");

		stockService.clearTrades();
		
		Date date = new Date();
		stockService.recordTrade("TEA", date, 30, 50, TradeType.BUY);
		stockService.recordTrade("POP", date, 10, 60, TradeType.BUY);
		stockService.recordTrade("ALE", date, 5, 20, TradeType.SELL);
		stockService.recordTrade("POP", date, 10, 30, TradeType.BUY);

		double gbce = stockService.calculateGBCE();
		
		Assert.assertTrue(gbce == 36.628415014847064);
		
		logger.info("calculateGBCE test OK: "+gbce);
		logger.info("-----------------------------------------------------------");
	}
}
