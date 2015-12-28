package com.mvalente.jpmorgan.stocks.service;

import java.util.Date;
import java.util.List;

import com.mvalente.jpmorgan.stocks.model.Trade;
import com.mvalente.jpmorgan.stocks.model.TradeType;

/**
 * Service support for the Global Beverage Corporation Exchange stocks manager
 * 
 * @author Miguel Valente
 */
public interface StockService {

	/**
	 * a) i. For a given stock, calculate the dividend yield
	 * 
	 * @param symbol
	 * @param tickerPrice
	 * @return dividends yeld
	 */
	public double calculateYeldDividend(String symbol, double tickerPrice);

	/**
	 * a) ii. For a given stock, calculate the P/E Ratio
	 * 
	 * @param symbol
	 * @return P/E Ratio
	 */
	public double calculatePriceEarningsRatio(String symbol, double tickerPrice);

	/**
	 * a) iii. For a given stock record a trade, with timestamp, quantity of
	 * shares, buy or sell indicator and price
	 * 
	 * @param symbol
	 * @param date
	 * @param quantity
	 * @param price
	 * @param tradeType
	 * @return trade
	 */
	public Trade recordTrade(String symbol, Date date, int quantity,
			double price, TradeType tradeType);

	public List<Trade> loadTrades();
	
	public void clearTrades();

	/**
	 * a) iv: Calculate Stock Price based on trades recorded in past 15 minutes
	 * 
	 * @param symbol
	 * @param interval
	 *            in minutes
	 * @return price
	 */
	public double calculateStockPrice(String symbol, int interval);

	/**
	 * b) Calculate the GBCE All Share Index using the geometric mean of prices
	 * for all stocks
	 */
	public double calculateGBCE();

}
