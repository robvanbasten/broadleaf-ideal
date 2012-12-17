package com.bvb.ideal.service.payment.message.details;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bvb.ideal.service.payment.message.IdealResponse;

@XmlRootElement(name = "DirectoryRes", namespace = "##default")
public class IdealIssuerResponse extends IdealResponse {

	protected String createDateTimeStamp;
	protected IdealAcquirer acquirer;
	protected IdealDirectory directory;

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
	
	@XmlElement(name = "Directory")
	public IdealDirectory getDirectory() {
		return directory;
	}
	public void setDirectory(IdealDirectory directory) {
		this.directory = directory;
	}
    
}
