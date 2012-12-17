package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bvb.ideal.service.payment.message.IdealRequest;

@XmlRootElement(name = "AcquirerStatusReq", namespace="##default")
public class IdealStatusRequest extends IdealRequest {

	protected IdealMerchant merchant;
	protected IdealTrx transaction;
	
	@XmlElement(name = "Merchant")
	public IdealMerchant getMerchant() {
		return merchant;
	}
	public void setMerchant(IdealMerchant merchant) {
		this.merchant = merchant;
	}
	@XmlElement(name = "Transaction")
	public IdealTrx getTransaction() {
		return transaction;
	}
	public void setTransaction(IdealTrx transaction) {
		this.transaction = transaction;
	}
	
}
