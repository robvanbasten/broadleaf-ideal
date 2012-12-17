package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Merchant")
public class IdealMerchant {
	
	protected String merchantId;
	protected String subId;
	protected String authentication;
	protected String token;
	protected String tokenCode;
	protected String merchantReturnURL;
	
	@XmlElement(name = "merchantID")
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	@XmlElement(name = "subID")
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	@XmlElement(name = "authentication")
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	@XmlElement(name = "token")
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@XmlElement(name = "tokenCode")
	public String getTokenCode() {
		return tokenCode;
	}
	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}
	@XmlElement(name = "merchantReturnURL")
	public String getMerchantReturnURL() {
		return merchantReturnURL;
	}
	public void setMerchantReturnURL(String merchantReturnURL) {
		this.merchantReturnURL = merchantReturnURL;
	}
	
}
