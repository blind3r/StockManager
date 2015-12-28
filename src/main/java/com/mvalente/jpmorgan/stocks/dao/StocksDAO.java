package com.mvalente.jpmorgan.stocks.dao;

import java.util.List;
import java.util.Map;

import com.mvalente.jpmorgan.stocks.model.Stock;
import com.mvalente.jpmorgan.stocks.model.Trade;

/**
 * This is the interface for this application data storage
 * 
 * @author Miguel Valente
 */
public interface StocksDAO {

	/**
	 * Obtain the stock for a given symbol
	 * 
	 * @param stockSymbol
	 * @return stock
	 */
	public Stock getStock(String symbol) throws Exception ;

	/**
	 * Obtain all the stocks mapped by their symbol
	 * 
	 * @return the map containing all the stocks
	 */
	public Map<String, Stock> getStocks();

	/**
	 * Persist a trade
	 * 
	 * @param trade
	 *            the trade to save
	 * @return True when the trade was created successfully
	 * 
	 * @throws Exception
	 */
	public boolean saveTrade(Trade trade) throws Exception;

	/**
	 * Obtain the map that contains all trades mapped by the stock symbol
	 * 
	 * @return the trade list
	 */
	public List<Trade> getTrades();
	
	/**
	 * Clear the trade list
	 */
	public void clearTrades();
}
