package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Transaction")
public class IdealTrx  {
	
	protected String purchaseId;
	protected String amount;
	protected String currency;
	protected String expirationPeriod;
	protected String language;
	protected String description;
	protected String entranceCode;
	protected String transactionId;
	protected String consumerName;
	protected String consumerAccountNumber;
	protected String consumerCity;
	protected String status;
	
	@XmlElement(name = "purchaseID")
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	@XmlElement(name = "amount")
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@XmlElement(name = "currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@XmlElement(name = "expirationPeriod")
	public String getExpirationPeriod() {
		return expirationPeriod;
	}
	public void setExpirationPeriod(String expirationPeriod) {
		this.expirationPeriod = expirationPeriod;
	}
	@XmlElement(name = "language")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement(name = "entranceCode")
	public String getEntranceCode() {
		return entranceCode;
	}
	public void setEntranceCode(String entranceCode) {
		this.entranceCode = entranceCode;
	}
	@XmlElement(name = "transactionID")
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@XmlElement(name = "consumerName")
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	@XmlElement(name = "consumerAccountNumber")
	public String getConsumerAccountNumber() {
		return consumerAccountNumber;
	}
	public void setConsumerAccountNumber(String consumerAccountNumber) {
		this.consumerAccountNumber = consumerAccountNumber;
	}
	@XmlElement(name = "consumerCity")
	public String getConsumerCity() {
		return consumerCity;
	}
	public void setConsumerCity(String consumerCity) {
		this.consumerCity = consumerCity;
	}
	@XmlElement(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
