package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bvb.ideal.service.payment.message.IdealResponse;

@XmlRootElement(name = "AcquirerStatusRes", namespace = "##default")
public class IdealStatusResponse extends IdealResponse {

	protected String createDateTimeStamp;
	protected IdealAcquirer acquirer;
	protected IdealTrx transaction;
	protected IdealSignature signature;
	
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
	@XmlElement(name = "Transaction")
	public IdealTrx getTransaction() {
		return transaction;
	}
	public void setTransaction(IdealTrx transaction) {
		this.transaction = transaction;
	}
	@XmlElement(name = "Signature")
	public IdealSignature getSignature() {
		return signature;
	}
	public void setSignature(IdealSignature signature) {
		this.signature = signature;
	}
	
}
