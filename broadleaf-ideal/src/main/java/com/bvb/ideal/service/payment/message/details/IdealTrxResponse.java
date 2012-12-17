package com.bvb.ideal.service.payment.message.details;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.bvb.ideal.service.payment.message.IdealResponse;

@XmlRootElement(name = "AcquirerTrxRes", namespace = "##default")
public class IdealTrxResponse extends IdealResponse {

	protected String createDateTimeStamp;
	protected IdealAcquirer acquirer;
	protected IdealIssuer issuer;
	protected IdealTrx transaction;
	protected String returnUrl;
	protected String cancelUrl;
	

	@XmlElement(name = "createDateTimeStamp")
	public String getCreateDateTimeStamp() {
		return createDateTimeStamp;
	}
	public void setCreateDateTimeStamp(String createDateTimeStamp) {
		this.createDateTimeStamp = createDateTimeStamp;
	}
	@XmlElement(name = "Acquirer")
	public IdealAcquirer getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(IdealAcquirer acquirer) {
		this.acquirer = acquirer;
	}
	@XmlElement(name = "Issuer")
	public IdealIssuer getIssuer() {
		return issuer;
	}
	public void setIssuer(IdealIssuer issuer) {
		this.issuer = issuer;
	}
	@XmlElement(name = "Transaction")
	public IdealTrx getTransaction() {
		return transaction;
	}
	public void setTransaction(IdealTrx transaction) {
		this.transaction = transaction;
	}
	@XmlTransient
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	@XmlTransient
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	
}
