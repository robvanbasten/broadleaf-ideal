package com.bvb.ideal.service.payment;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import com.bvb.ideal.security.util.IdealSecurityException;
import com.bvb.ideal.security.util.IdealSecurityHelperImpl;
import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.IdealResponse;
import com.bvb.ideal.service.payment.message.details.IdealError;
import com.bvb.ideal.service.payment.message.details.IdealErrorResponse;
import com.bvb.ideal.service.payment.message.details.IdealIssuerResponse;
import com.bvb.ideal.service.payment.message.details.IdealStatusResponse;
import com.bvb.ideal.service.payment.message.details.IdealTrxRequest;
import com.bvb.ideal.service.payment.message.details.IdealTrxResponse;
import com.bvb.ideal.service.payment.type.IdealMethodType;

public class IdealResponseGeneratorImpl implements IdealResponseGenerator {
	
    @Resource
    private IdealSecurityHelperImpl securityHelper;

	class CustomValidationEventHandler implements ValidationEventHandler {

	    public boolean handleEvent(ValidationEvent evt) {
	        System.out.println("Event Info: "+evt);
	        if(evt.getMessage().contains("unexpected element"))
	            return true;
	        return true;
	    }

	}

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public IdealResponse buildResponse(InputStream response, IdealRequest idealRequest) {
		IdealResponse idealResponse = null;
		if (IdealMethodType.CHECKOUT.equals(idealRequest.getMethodType())) {
        	
        } else if (IdealMethodType.ISSUER.equals(idealRequest.getMethodType())) {
        	idealResponse = buildIssuerResponse(response);
        } else if (IdealMethodType.PROCESS.equals(idealRequest.getMethodType())) {
        	
        } else if (IdealMethodType.STATUS.equals(idealRequest.getMethodType())) {
        	idealResponse = buildStatusResponse(response);
        } else if (IdealMethodType.TRANSACTION.equals(idealRequest.getMethodType())) {
        	idealResponse = buildTrxResponse(response, (IdealTrxRequest) idealRequest);
        }
		return idealResponse;
	}

	private IdealIssuerResponse buildIssuerResponse(InputStream response) {
		IdealIssuerResponse issuerResponse = new IdealIssuerResponse();
		Object responseObject = unMarchall(response, new IdealIssuerResponse());
		if (responseObject instanceof IdealIssuerResponse) {
			issuerResponse = (IdealIssuerResponse) responseObject;
		}
		if (responseObject instanceof IdealErrorResponse) {
			issuerResponse.setErrors(((IdealErrorResponse) responseObject).getError());
		}
		return issuerResponse;
	}
	
	private IdealTrxResponse buildTrxResponse(InputStream response, IdealTrxRequest request) {
		IdealTrxResponse trxResponse = new IdealTrxResponse();
		Object responseObject = unMarchall(response, new IdealTrxResponse());
		if (responseObject instanceof IdealTrxResponse) {
			trxResponse = (IdealTrxResponse) responseObject;
			trxResponse.setCancelUrl(request.getCancelUrl());
			trxResponse.setReturnUrl(request.getReturnUrl());
		}
		if (responseObject instanceof IdealErrorResponse) {
			trxResponse.setErrors(((IdealErrorResponse) responseObject).getError());
		}
		return trxResponse;
	}

	private IdealStatusResponse buildStatusResponse(InputStream response) {
		IdealStatusResponse statusResponse = new IdealStatusResponse();
		Object responseObject = unMarchall(response, new IdealStatusResponse());
		if (responseObject instanceof IdealStatusResponse) {
			statusResponse = (IdealStatusResponse) responseObject;
		}
		if (responseObject instanceof IdealErrorResponse) {
			statusResponse.setErrors(((IdealErrorResponse) responseObject).getError());
		}
		try {
			verifyStatusResponse(statusResponse);
		} catch (IdealSecurityException e) {
			IdealError error = new IdealError();
			error.setConsumerMessage(e.getMessage());
			error.setErrorCode("XXXX");
			error.setErrorDetail(e.getMessage());
			error.setErrorMessage(e.getMessage());
			statusResponse.getErrors().add(error);
		}
		return statusResponse;
	}

	private void verifyStatusResponse(IdealStatusResponse statusResponse) throws IdealSecurityException {
		String trustedMessage = statusResponse.getCreateDateTimeStamp() +
				statusResponse.getTransaction().getTransactionId() +
				statusResponse.getTransaction().getStatus() +
				statusResponse.getTransaction().getConsumerAccountNumber();
		if (!securityHelper.verify(trustedMessage.getBytes(), statusResponse.getSignature().getSignatureValue().getBytes())) {
			throw new IdealSecurityException("Bad signature.");
		}
	}
	
	private Object unMarchall(InputStream idealResponse, Object responseObject) {
		// create JAXB context and instantiate marshaller
		Object errorObject = new IdealErrorResponse();
	    JAXBContext context;

		try {
			context = JAXBContext.newInstance(responseObject.getClass(), errorObject.getClass());
		    Unmarshaller um = context.createUnmarshaller();
		    um.setEventHandler(new CustomValidationEventHandler());
		    responseObject = um.unmarshal(idealResponse);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return responseObject;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}


}
