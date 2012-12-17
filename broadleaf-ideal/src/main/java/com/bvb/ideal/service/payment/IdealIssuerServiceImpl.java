package com.bvb.ideal.service.payment;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.vendor.service.exception.PaymentException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.bvb.ideal.service.payment.message.details.IdealIssuer;
import com.bvb.ideal.service.payment.message.details.IdealIssuerRequest;
import com.bvb.ideal.service.payment.message.details.IdealIssuerResponse;
import com.bvb.ideal.service.payment.type.IdealMethodType;

public class IdealIssuerServiceImpl implements IdealIssuerService {

	private static final Log LOG = LogFactory.getLog(IdealIssuerServiceImpl.class);
	private List<IdealIssuer> idealIssuers;

    @Resource(name="blIdealVendorOrientedPaymentService")
    protected IdealPaymentService paymentService;
    
	@Override
	@Cacheable("issuerList")
	public List<IdealIssuer> getIssuers() {
		return getIssuersNoCache();
	}

	@CacheEvict(value="issuerList")
	private List<IdealIssuer> getIssuersNoCache() {
        IdealIssuerRequest request = new IdealIssuerRequest();
        
        request.setMethodType(IdealMethodType.ISSUER);
        IdealIssuerResponse response = null;
		try {
			response = (IdealIssuerResponse) paymentService.process(request);
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idealIssuers = response.getDirectory().getIssuers();
		return response.getDirectory().getIssuers();

	}
	
	@Override
	public String getIssuerId(String bankName) {
		for (IdealIssuer issuer : idealIssuers) {
			if (issuer.getIssuerName().equals(bankName)) {
				return issuer.getIssuerId();
			}
		}
		return null;
	}

}
