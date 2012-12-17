package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")

public class IdealError {

	protected String errorCode;
	protected String errorMessage;
	protected String errorDetail;
	protected String consumerMessage;

	@XmlElement(name = "errorCode")
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@XmlElement(name = "errorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@XmlElement(name = "errorDetail")
	public String getErrorDetail() {
		return errorDetail;
	}
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	@XmlElement(name = "consumerMessage")
	public String getConsumerMessage() {
		return consumerMessage;
	}
	public void setConsumerMessage(String consumerMessage) {
		this.consumerMessage = consumerMessage;
	}

	
}
