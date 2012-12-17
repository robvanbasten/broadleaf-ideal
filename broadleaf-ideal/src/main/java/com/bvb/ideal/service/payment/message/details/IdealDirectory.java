package com.bvb.ideal.service.payment.message.details;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Directory")
public class IdealDirectory {
	protected String directoryDateTimeStamp;
	protected List<IdealIssuer> issuers = new ArrayList<IdealIssuer>();

	@XmlElement(name = "directoryDateTimeStamp")
	public String getDirectoryDateTimeStamp() {
		return directoryDateTimeStamp;
	}
	public void setDirectoryDateTimeStamp(String directoryDateTimeStamp) {
		this.directoryDateTimeStamp = directoryDateTimeStamp;
	}

	@XmlElement(name = "Issuer")
	public List<IdealIssuer> getIssuers() {
		return issuers;
	}
	public void setIssuers(List<IdealIssuer> issuers) {
		this.issuers = issuers;
	}
	
	
}
