package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bvb.ideal.service.payment.message.IdealRequest;

@XmlRootElement(name = "DirectoryReq", namespace="##default")
public class IdealIssuerRequest extends IdealRequest {

	protected String createDateTimeStamp;
	protected IdealMerchant merchant;

	@XmlElement(name = "createDateTimeStamp")
	public String getCreateDateTimeStamp() {
		return createDateTimeStamp;
	}
	public void setCreateDateTimeStamp(String createDateTimeStamp) {
		this.createDateTimeStamp = createDateTimeStamp;
	}

	@XmlElement(name = "Merchant")
	public IdealMerchant getMerchant() {
		return merchant;
	}
	public void setMerchant(IdealMerchant merchant) {
		this.merchant = merchant;
	}

	
}
