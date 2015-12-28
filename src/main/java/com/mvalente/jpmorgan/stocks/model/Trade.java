package com.mvalente.jpmorgan.stocks.model;

import java.util.Date;

public class Trade {

	private Stock stock;
	private Date date;
	private int quantity;
	private double price;
	private TradeType tradeType;

	public Trade(Stock stock, Date date, int quantity, double price,
			TradeType tradeType) {
		super();
		this.stock = stock;
		this.date = date;
		this.quantity = quantity;
		this.price = price;
		this.setTradeType(tradeType);
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	@Override
	public String toString() {
		return "Trade [stock=" + stock + ", date=" + date + ", quantity="
				+ quantity + ", price=" + price + ", tradeType=" + tradeType
				+ "]";
	}
}
