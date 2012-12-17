package com.bvb.payment.service.module;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItemImpl;
import org.broadleafcommerce.core.payment.service.PaymentContext;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.module.PaymentModule;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;

import com.bvb.ideal.service.payment.IdealPaymentService;
import com.bvb.ideal.service.payment.MessageConstants;
import com.bvb.ideal.service.payment.message.IdealResponse;
import com.bvb.ideal.service.payment.message.details.IdealIssuer;
import com.bvb.ideal.service.payment.message.details.IdealStatusRequest;
import com.bvb.ideal.service.payment.message.details.IdealStatusResponse;
import com.bvb.ideal.service.payment.message.details.IdealTrx;
import com.bvb.ideal.service.payment.message.details.IdealTrxRequest;
import com.bvb.ideal.service.payment.message.details.IdealTrxResponse;
import com.bvb.ideal.service.payment.type.IdealMethodType;

public class IdealPaymentModule implements PaymentModule {
	
	private static final Log LOG = LogFactory.getLog(IdealPaymentModule.class);
	protected IdealPaymentService idealPaymentService;
	protected String issuerId;
	protected PaymentContext authorizeAndDebitpaymentContext = null;

	@Override
	public PaymentResponseItem authorize(PaymentContext paymentContext)
			throws PaymentException {
		
        throw new PaymentException("The authorize method is not supported by this module");
	}

	@Override
	public PaymentResponseItem reverseAuthorize(PaymentContext paymentContext)
			throws PaymentException {

        throw new PaymentException("The reverseAuthorize method is not supported by this module");
	}

	@Override
	public PaymentResponseItem debit(PaymentContext paymentContext)
			throws PaymentException {

        throw new PaymentException("The debit method is not supported by this module");

	}

	@Override
	public PaymentResponseItem authorizeAndDebit(PaymentContext paymentContext)
			throws PaymentException {
		PaymentResponseItem responseItem = null;
		if (paymentContext.getPaymentInfo().getAdditionalFields().containsKey(MessageConstants.TRANSACTIONID)) {
			responseItem = checkIdealStatus(paymentContext);
		} else {
			responseItem = createIdealTransaction(paymentContext);
		}
        return responseItem;
	}

	@Override
	public PaymentResponseItem credit(PaymentContext paymentContext)
			throws PaymentException {
        throw new PaymentException("The credit method is not supported by this module");
	}

	@Override
	public PaymentResponseItem voidPayment(PaymentContext paymentContext)
			throws PaymentException {
        throw new PaymentException("The voidPayment method is not supported by this module");
	}

	@Override
	public PaymentResponseItem balance(PaymentContext paymentContext)
			throws PaymentException {
        throw new PaymentException("The balance method is not supported by this module");
	}

	@Override
	public Boolean isValidCandidate(PaymentInfoType paymentType) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isValidCandidate: paymenttype: "+paymentType.getType());
		}
        //return IdealPaymentType.IDEAL.equals(paymentType);
		return Boolean.TRUE;
	}

	private PaymentResponseItem createIdealTransaction(PaymentContext paymentContext) throws PaymentException {
		System.out.println("createTransaction");
		
		IdealTrxRequest request = new IdealTrxRequest();
        request.setMethodType(IdealMethodType.TRANSACTION);
       
		IdealTrx transaction = new IdealTrx();

		IdealIssuer issuer = new IdealIssuer();
		issuer.setIssuerId(getIssuerId());
		request.setIdealIssuer(issuer);
		transaction.setAmount(paymentContext.getPaymentInfo().getAmount().toString().replaceAll("\\.", ""));
		transaction.setCurrency(paymentContext.getPaymentInfo().getAmount().getCurrency().getCurrencyCode());
		transaction.setDescription("Uw Order: "+paymentContext.getPaymentInfo().getReferenceNumber());
		transaction.setPurchaseId(paymentContext.getPaymentInfo().getReferenceNumber());
		transaction.setExpirationPeriod("PT1H");
		transaction.setLanguage("nl");
		request.setIdealTrx(transaction);

        IdealTrxResponse response;
        try {
            response = (IdealTrxResponse) idealPaymentService.process(request);
        } catch (org.broadleafcommerce.common.vendor.service.exception.PaymentException e) {
            throw new PaymentException(e);
        }


        PaymentResponseItem responseItem = buildBasicResponse(response);
        responseItem.setReferenceNumber(response.getTransaction().getPurchaseId());
        responseItem.setTransactionSuccess(true);
        responseItem.setAuthorizationCode(response.getTransaction().getTransactionId());
        responseItem.setTransactionId(response.getTransaction().getTransactionId());

 //       setDecisionInformation(response, responseItem);
        responseItem.getAdditionalFields().put(MessageConstants.ENTRANCECODE, response.getTransaction().getEntranceCode());
        responseItem.getAdditionalFields().put(MessageConstants.TRANSACTIONID, response.getTransaction().getTransactionId());
        responseItem.getAdditionalFields().put(MessageConstants.REDIRECTURL, response.getIssuer().getIssuerAuthenticationURL());// +
//        		response.getTransaction().getEntranceCode());
//        responseItem.getAdditionalFields().put(MessageConstants.RETURNURL, "http://localhost:8080/ideal/process");
//        responseItem.getAdditionalFields().put(MessageConstants.CANCELURL, "http://localhost:8080/cart");
        responseItem.getAdditionalFields().put(MessageConstants.RETURNURL, response.getReturnUrl());
        responseItem.getAdditionalFields().put(MessageConstants.CANCELURL, response.getCancelUrl());

        responseItem.setAmountPaid(paymentContext.getPaymentInfo().getAmount());

        return responseItem;
		
	}
	
	private PaymentResponseItem checkIdealStatus(PaymentContext paymentContext) throws PaymentException {
		System.out.println("checkStatus");
    	
		IdealStatusRequest request = new IdealStatusRequest();
		request.setMethodType(IdealMethodType.STATUS);
		IdealTrx transaction = new IdealTrx();
		transaction.setTransactionId(paymentContext.getPaymentInfo().getAdditionalFields().get(MessageConstants.TRANSACTIONID));
/*        for (PaymentInfo paymentInfo : order.getPaymentInfos()) {
            if (IdealPaymentType.IDEAL.equals(paymentInfo.getType())) {
                //There should only be one payment info of type ideal in the order
        		transaction.setTransactionId(paymentInfo.getAdditionalFields().get(MessageConstants.TRANSACTIONID));
                break;
            }
        }
*/		request.setTransaction(transaction);
		IdealStatusResponse response = null;
        try {
            response = (IdealStatusResponse) idealPaymentService.process(request);
        } catch (org.broadleafcommerce.common.vendor.service.exception.PaymentException e) {
            throw new PaymentException(e);
        }

        PaymentResponseItem responseItem = buildBasicResponse(response);
        responseItem.setReferenceNumber(response.getTransaction().getPurchaseId());
        responseItem.setTransactionSuccess(response.getTransaction().getStatus().equals("Success"));
        responseItem.setAuthorizationCode(response.getTransaction().getTransactionId());

 //       setDecisionInformation(response, responseItem);
//        responseItem.getAdditionalFields().put(MessageConstants.ENTRANCECODE, response.getTransaction().getEntranceCode());
//        responseItem.getAdditionalFields().put(MessageConstants.TRANSACTIONID, response.getTransaction().getTransactionId());
//        responseItem.getAdditionalFields().put(MessageConstants.REDIRECTURL, response.getIssuer().getIssuerAuthenticationURL());// +
//        		response.getTransaction().getEntranceCode());
//        responseItem.getAdditionalFields().put(MessageConstants.RETURNURL, "http://localhost:8080/ideal/process");
//        responseItem.getAdditionalFields().put(MessageConstants.RETURNURL, "http://localhost:8080/cart");
//        responseItem.getAdditionalFields().put(MessageConstants.CANCELURL, "http://localhost:8080/cart");
//        responseItem.getAdditionalFields().put(MessageConstants.REDIRECTURL, "http://localhost:8080/cart");
        responseItem.setAmountPaid(paymentContext.getPaymentInfo().getAmount());

        return responseItem;
    }

    protected PaymentResponseItem buildBasicResponse(IdealResponse response) {
        PaymentResponseItem responseItem = new PaymentResponseItemImpl();
        responseItem.setTransactionTimestamp(SystemTime.asDate());
//        responseItem.setMiddlewareResponseCode(response.getAck());
//        responseItem.setMiddlewareResponseText(response.getAck());

        
        
        int counter = 0;
//        for (IdealError errorResponse : response.getErrors()) {
//            String errorCode = errorResponse.getErrorCode();
//            if (counter == 0) {
//                responseItem.setMiddlewareResponseCode(errorCode);
//                responseItem.setMiddlewareResponseText(errorResponse.getErrorDetail());
//            }
//            counter++;
//            responseItem.getAdditionalFields().put(MessageConstants.MODULEERRORCODE, errorCode);
////            responseItem.getAdditionalFields().put(MessageConstants.MODULEERRORSEVERITYCODE + "_" + errorCode, errorResponse.getSeverityCode());
//            responseItem.getAdditionalFields().put(MessageConstants.MODULEERRORLONGMESSAGE + "_" + errorCode, errorResponse.getConsumerMessage());
//            responseItem.getAdditionalFields().put(MessageConstants.MODULEERRORSHORTMESSAGE + "_" + errorCode, errorResponse.getErrorDetail());
//        }

        
        //        responseItem.getAdditionalFields().putAll(response.getPassThroughErrors());

        return responseItem;
    }

	public IdealPaymentService getIdealPaymentService() {
		return idealPaymentService;
	}

	public void setIdealPaymentService(IdealPaymentService idealPaymentService) {
		this.idealPaymentService = idealPaymentService;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	
}
