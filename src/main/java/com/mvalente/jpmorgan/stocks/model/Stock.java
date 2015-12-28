package com.mvalente.jpmorgan.stocks.model;

/**
 * Representation of a simple stock
 * 
 * @author Miguel Valente
 */
public class Stock {

	private String symbol;
	private StockType type;

	private double lastDividend = 0.0;
	private double fixedDividend = 0.0;
	private double parValue = 0.0;

	public Stock(String symbol, StockType type, double lastDividend,
			double fixedDividend, double parValue) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public double getParValue() {
		return parValue;
	}

	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", lastDividend="
				+ lastDividend + ", fixedDividend=" + fixedDividend
				+ ", parValue=" + parValue + "]";
	}
}
