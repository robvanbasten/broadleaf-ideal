package com.bvb.ideal.service.payment.message.details;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorRes", namespace = "##default")
public class IdealErrorResponse {

	protected String createDateTimeStamp;
	protected List<IdealError> error = new ArrayList<IdealError>();

	@XmlElement(name = "createDateTimeStamp")
	public String getCreateDateTimeStamp() {
		return createDateTimeStamp;
	}
	public void setCreateDateTimeStamp(String createDateTimeStamp) {
		this.createDateTimeStamp = createDateTimeStamp;
	}
	
	@XmlElement(name = "Error")
	public List<IdealError> getError() {
		return error;
	}
	public void setError(List<IdealError> error) {
		this.error = error;
	}

	
}
