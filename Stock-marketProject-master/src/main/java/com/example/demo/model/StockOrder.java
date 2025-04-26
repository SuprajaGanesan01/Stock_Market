package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "stock_orders")
public class StockOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id", nullable = false)
	private Portfolio portfolio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	
	private double stockPrice;
	private String stockSymbol;
	private double investAmount;
	private int quantity;
	private String orderType; // BUY or SELL
	
	// Risk management fields
	private Double stopLoss;
	private Double takeProfit;
	private Double riskRewardRatio;
	private String riskLevel; // LOW, MEDIUM, HIGH
	
	// Payment information
	private String cardType;
	private String cardNumber;
	private String userEmail;
	private String address;
	private String comment;
	
	public StockOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StockOrder(Portfolio portfolio, Date orderDate, double stockPrice, String stockSymbol, 
					 double investAmount, int quantity, String orderType) {
		this.portfolio = portfolio;
		this.orderDate = orderDate;
		this.stockPrice = stockPrice;
		this.stockSymbol = stockSymbol;
		this.investAmount = investAmount;
		this.quantity = quantity;
		this.orderType = orderType;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Portfolio getPortfolio() {
		return portfolio;
	}
	
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public Double getStopLoss() {
		return stopLoss;
	}
	
	public void setStopLoss(Double stopLoss) {
		this.stopLoss = stopLoss;
	}
	
	public Double getTakeProfit() {
		return takeProfit;
	}
	
	public void setTakeProfit(Double takeProfit) {
		this.takeProfit = takeProfit;
	}
	
	public Double getRiskRewardRatio() {
		return riskRewardRatio;
	}
	
	public void setRiskRewardRatio(Double riskRewardRatio) {
		this.riskRewardRatio = riskRewardRatio;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
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
