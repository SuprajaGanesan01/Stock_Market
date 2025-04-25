package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;
import  java.sql.Date;
import jakarta.persistence.*;


@Entity
public class StockOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	
	private double stockPrice;
	private String stockSymbol;
	private double investAmount;
	private String cardType;
	private String cardNumber;
	private String userEmail;
	private String address;
	private String comment;
	public StockOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockOrder(long id, Date orderDate, double stockPrice, String stockSymbol, double investAmount,
			String cardType, String cardNumber, String userEmail, String address, String comment) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.stockPrice = stockPrice;
		this.stockSymbol = stockSymbol;
		this.investAmount = investAmount;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.userEmail = userEmail;
		this.address = address;
		this.comment = comment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public double getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(double investAmount) {
		this.investAmount = investAmount;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	

	
	

}
