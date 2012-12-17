package com.bvb.ideal.service.payment;

import java.util.List;

import com.bvb.ideal.service.payment.message.details.IdealIssuer;

public interface IdealIssuerService {

	public List<IdealIssuer> getIssuers();
	
	public String getIssuerId(String bankName);
	
}
