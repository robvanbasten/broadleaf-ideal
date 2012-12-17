package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Signature")
public class IdealSignature {
	
	protected String signatureValue;
	protected String fingerPrint;
	@XmlElement(name = "signatureValue")
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
	@XmlElement(name = "fingerprint")
	public String getFingerPrint() {
		return fingerPrint;
	}
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	
}
