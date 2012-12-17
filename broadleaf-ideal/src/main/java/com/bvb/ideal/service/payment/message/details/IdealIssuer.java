package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Issuer")
public class IdealIssuer {

	protected String issuerId;
	protected String issuerName;
	protected String issuerList;
	protected String issuerAuthenticationURL;
	
	@XmlElement(name = "issuerID")
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	@XmlElement(name = "issuerName")
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	@XmlElement(name = "issuerList")
	public String getIssuerList() {
		return issuerList;
	}
	public void setIssuerList(String issuerList) {
		this.issuerList = issuerList;
	}
	@XmlElement(name = "issuerAuthenticationURL")
	public String getIssuerAuthenticationURL() {
		return issuerAuthenticationURL;
	}
	public void setIssuerAuthenticationURL(String issuerAuthenticationURL) {
		this.issuerAuthenticationURL = issuerAuthenticationURL;
	}
	
}
