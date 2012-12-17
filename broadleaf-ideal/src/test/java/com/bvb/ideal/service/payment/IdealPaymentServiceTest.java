package com.bvb.ideal.service.payment;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.vendor.service.exception.PaymentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.IdealResponse;
import com.bvb.ideal.service.payment.message.details.IdealError;
import com.bvb.ideal.service.payment.message.details.IdealIssuer;
import com.bvb.ideal.service.payment.message.details.IdealIssuerRequest;
import com.bvb.ideal.service.payment.message.details.IdealIssuerResponse;
import com.bvb.ideal.service.payment.message.details.IdealMerchant;
import com.bvb.ideal.service.payment.message.details.IdealStatusRequest;
import com.bvb.ideal.service.payment.message.details.IdealStatusResponse;
import com.bvb.ideal.service.payment.message.details.IdealTrx;
import com.bvb.ideal.service.payment.message.details.IdealTrxRequest;
import com.bvb.ideal.service.payment.message.details.IdealTrxResponse;
import com.bvb.ideal.service.payment.type.IdealMethodType;
import com.smartgwt.client.docs.Errors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="IdealPaymentServiceTest-context.xml")
public class IdealPaymentServiceTest {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testMethod() {
		IdealPaymentService paymentService = applicationContext.getBean(IdealPaymentService.class);
		IdealIssuerRequest idealRequest = new IdealIssuerRequest();
		idealRequest.setMethodType(IdealMethodType.ISSUER);
		IdealIssuerResponse response = null;
		try {
			response = (IdealIssuerResponse) paymentService.process(idealRequest);
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response: " + response);
		if (response.getErrors() != null) {
			for (IdealError error : response.getErrors()) {
				System.out.println("error: "+ error.getErrorMessage());
			}
		}

		IdealTrxRequest req = new IdealTrxRequest();
		req.setMethodType(IdealMethodType.TRANSACTION);

		IdealIssuer issuer = new IdealIssuer();
		System.out.println("acquirer: " + response.getDirectory());
		System.out.println("issuer1: " + response.getDirectory().getIssuers());
		
		issuer.setIssuerId(response.getDirectory().getIssuers().get(0).getIssuerId());
		req.setIdealIssuer(issuer);
		
		IdealTrx trx = new IdealTrx();
//		Money amount = new Money(100);
		trx.setAmount("10000");
		trx.setCurrency("EUR");
		trx.setDescription("TEST order");
//		trx.setEntranceCode("100");
		trx.setExpirationPeriod("PT1H");
		trx.setLanguage("nl");
		trx.setPurchaseId("12345");
		req.setIdealTrx(trx);
		
		IdealTrxResponse trxresponse = null;
		try {
			trxresponse = (IdealTrxResponse) paymentService.process(req);
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tResponse: " + trxresponse);
		if (trxresponse.getErrors() != null) {
			for (IdealError error : trxresponse.getErrors()) {
				System.out.println("error: "+ error.getErrorMessage());
			}
		}
		System.out.println("trx: "+trxresponse.getTransaction().getTransactionId() + "url: "+trxresponse.getIssuer().getIssuerAuthenticationURL());

		IdealStatusRequest sreq = new IdealStatusRequest();
		sreq.setMethodType(IdealMethodType.STATUS);

		trx = new IdealTrx();
		trx.setTransactionId(trxresponse.getTransaction().getTransactionId());
		sreq.setTransaction(trx);
		IdealStatusResponse sr = null;
		try {
			sr = (IdealStatusResponse) paymentService.process(sreq);
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("sResponse: " + sr + " status: "+sr.getTransaction().getStatus());
		System.out.println("sResponse: " + sr);
		if (sr.getErrors() != null) {
			for (IdealError error : sr.getErrors()) {
				System.out.println("error: "+ error.getErrorMessage());
			}
		}
		System.out.println("sResponse status: " + sr.getTransaction().getStatus());

	}
	
}
