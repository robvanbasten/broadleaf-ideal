package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.bvb.ideal.service.payment.message.IdealRequest;

@XmlRootElement(name = "AcquirerTrxReq", namespace = "##default")
public class IdealTrxRequest extends IdealRequest {
	
	protected String createDateTimeStamp;
	protected IdealIssuer idealIssuer;
	protected IdealMerchant idealMerchant;
	protected IdealTrx IdealTrx;
	protected String cancelUrl;
	protected String returnUrl;

	@XmlElement(name = "createDateTimeStamp")
	public String getCreateDateTimeStamp() {
		return createDateTimeStamp;
	}
	public void setCreateDateTimeStamp(String createDateTimeStamp) {
		this.createDateTimeStamp = createDateTimeStamp;
	}
	@XmlElement(name = "Issuer")
	public IdealIssuer getIdealIssuer() {
		return idealIssuer;
	}
	public void setIdealIssuer(IdealIssuer idealIssuer) {
		this.idealIssuer = idealIssuer;
	}
	@XmlElement(name = "Merchant")
	public IdealMerchant getIdealMerchant() {
		return idealMerchant;
	}
	public void setIdealMerchant(IdealMerchant idealMerchant) {
		this.idealMerchant = idealMerchant;
	}
	@XmlElement(name = "Transaction")
	public IdealTrx getIdealTrx() {
		return IdealTrx;
	}
	public void setIdealTrx(IdealTrx idealTrx) {
		IdealTrx = idealTrx;
	}
	@XmlTransient
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	@XmlTransient
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}


}
