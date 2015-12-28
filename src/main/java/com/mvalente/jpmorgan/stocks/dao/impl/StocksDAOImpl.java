package com.mvalente.jpmorgan.stocks.dao.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mvalente.jpmorgan.stocks.dao.StocksDAO;
import com.mvalente.jpmorgan.stocks.model.Stock;
import com.mvalente.jpmorgan.stocks.model.StockType;
import com.mvalente.jpmorgan.stocks.model.Trade;

@Repository
public class StocksDAOImpl implements StocksDAO {

	static final Logger logger = LogManager.getLogger(StocksDAOImpl.class);

	private Map<String, Stock> stocks = null;
	private List<Trade> trades = null;

	public StocksDAOImpl() {
		stocks = fillDummyStocks();
		trades = new ArrayList<Trade>();
	}

	private Map<String, Stock> fillDummyStocks() {
		Map<String, Stock> stocks = new HashMap<String, Stock>();

		stocks.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0));
		stocks.put("POP", new Stock("POP", StockType.COMMON, 8, 0.0, 100.0));
		stocks.put("ALE", new Stock("ALE", StockType.COMMON, 23, 0.0, 60.0));
		stocks.put("GIN", new Stock("GIN", StockType.PREFERRED, 8, 0.2, 100));
		stocks.put("JOE", new Stock("JOE", StockType.COMMON, 13, 0.0, 250.0));

		return stocks;
	}

	@Override
	public Stock getStock(String symbol) throws Exception {
		Stock stock = null;

		if (symbol != null) {
			stock = stocks.get(symbol);
		} else {
			throw new InvalidParameterException("The symbol cannot be null");
		}

		return stock;
	}

	@Override
	public Map<String, Stock> getStocks() {
		return stocks;
	}

	@Override
	public boolean saveTrade(Trade trade) throws Exception {
		boolean result = false;

		result = trades.add(trade);
		return result;
	}

	@Override
	public List<Trade> getTrades() {
		return trades;
	}

	@Override
	public void clearTrades() {
		trades = new ArrayList<Trade>();
	}

}
