package com.mvalente.jpmorgan.stocks.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mvalente.jpmorgan.stocks.dao.StocksDAO;
import com.mvalente.jpmorgan.stocks.model.Stock;
import com.mvalente.jpmorgan.stocks.model.StockType;
import com.mvalente.jpmorgan.stocks.model.Trade;
import com.mvalente.jpmorgan.stocks.model.TradeType;
import com.mvalente.jpmorgan.stocks.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	static final Logger logger = LogManager.getLogger(StockServiceImpl.class);

	private StocksDAO stocksDAO;

	public void setStocksDAO(StocksDAO stocksDAO) {
		this.stocksDAO = stocksDAO;
	}
	
	private Stock loadStock(String symbol) throws Exception {
		Stock stock = stocksDAO.getStock(symbol);
		if (stock == null) {
			throw new InvalidParameterException("Invalid stock symbol:  "
					+ symbol);
		}

		return stock;
	}
	
	@Override
	public double calculateYeldDividend(String symbol, double tickerPrice) {
		double dividendYield = 0;

		logger.debug("calculateYeldDividend: symbol-" + symbol
				+ " tickerPrice-" + tickerPrice);

		try {
			
			if (tickerPrice <= 0) {
				throw new InvalidParameterException("Invalid tickerPrice: "
						+ tickerPrice);
			}
			Stock stock = loadStock(symbol);

			if (stock.getType() == StockType.COMMON) {
				dividendYield = stock.getLastDividend() / tickerPrice;
			} else {
				dividendYield = (stock.getFixedDividend() * stock.getParValue())
						/ tickerPrice;
			}

			logger.debug(symbol + " yield dividend: " + dividendYield);

		} catch (Exception e) {
			logger.error("Error calculating dividend for symbol: " + symbol, e);
		}
		return dividendYield;
	}

	@Override
	public double calculatePriceEarningsRatio(String symbol, double tickerPrice) {
		double earningsRatio = 0;

		try {
			if (tickerPrice <= 0) {
				throw new InvalidParameterException("Invalid tickerPrice: "
						+ tickerPrice);
			}

			Stock stock = loadStock(symbol);

			earningsRatio = tickerPrice / stock.getLastDividend();

		} catch (Exception e) {
			logger.error("Error calculating price earnings ratio for symbol: "
					+ symbol, e);
		}
		return earningsRatio;
	}

	@Override
	public Trade recordTrade(String stockSymbol, Date date, int quantity,
			double price, TradeType tradeType) {
		Trade trade = null;
		try {
			Stock stock = loadStock(stockSymbol);

			trade = new Trade(stock, date, quantity, price, tradeType);
			stocksDAO.saveTrade(trade);
		} catch (Exception e) {
			logger.error("Error saving trade" + trade.toString(), e);
		}

		return trade;
	}

	@Override
	public double calculateStockPrice(String symbol, int interval) {
		double stockPrice = 0;
		List<Trade> targetTrades = new ArrayList<Trade>();

		double tradeSum = 0;
		int quantitySum = 0;

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, -interval);

			for (Trade trade : stocksDAO.getTrades()) {
				if (symbol.equals(trade.getStock().getSymbol())
						&& trade.getDate().after(cal.getTime())) {
					targetTrades.add(trade);
				}
			}

			for (Trade trade : targetTrades) {
				tradeSum += (trade.getPrice() * trade.getQuantity());
				quantitySum += trade.getQuantity();
			}

			stockPrice = tradeSum / quantitySum;
		} catch (Exception e) {
			logger.error("Error calculating stock price for stock " + symbol, e);
		}

		return stockPrice;
	}

	@Override
	public double calculateGBCE() {
		double GBCEindex = 0;
		double total = 1;

		List<Trade> trades = stocksDAO.getTrades();
		for (Trade trade : trades) {
			total *= trade.getPrice();
		}
		GBCEindex = Math.pow(total, 1.0 / trades.size());

		return GBCEindex;
	}

	@Override
	public List<Trade> loadTrades() {
		return stocksDAO.getTrades();
	}

	@Override
	public void clearTrades() {
		stocksDAO.clearTrades();
	}
}
