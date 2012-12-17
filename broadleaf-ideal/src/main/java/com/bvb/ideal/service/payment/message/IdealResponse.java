package com.bvb.ideal.service.payment.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.bvb.ideal.service.payment.message.details.IdealError;

public class IdealResponse implements ErrorCheckable {

	protected List<IdealError> errors = new ArrayList<IdealError>();

	@XmlTransient
	public List<IdealError> getErrors() {
		return errors;
	}

	public void setErrors(List<IdealError> errors) {
		this.errors = errors;
	}

	
}
