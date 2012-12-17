package com.bvb.ideal.service.payment;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.vendor.service.exception.PaymentException;
import org.broadleafcommerce.common.vendor.service.type.ServiceStatusType;

import com.bvb.ideal.security.util.EasyHttpClient;
import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.IdealResponse;

public class IdealPaymentServiceImpl implements IdealPaymentService {

	private static final Log LOG = LogFactory.getLog(IdealPaymentServiceImpl.class);

    protected String serverUrl;
    protected Integer failureReportingThreshold;
    protected Integer failureCount = 0;
    protected Boolean isUp = true;
    protected IdealRequestGenerator requestGenerator;
    protected IdealResponseGenerator responseGenerator;

    protected synchronized void clearStatus() {
        isUp = true;
        failureCount = 0;
    }

    protected synchronized void incrementFailure() {
        if (failureCount >= failureReportingThreshold) {
            isUp = false;
        } else {
            failureCount++;
        }
    }

	@Override
	public IdealResponse process(IdealRequest idealRequest) throws PaymentException {
        InputStream response;
        IdealResponse idealResponse = null;
        
        try {
            response = communicateWithVendor(idealRequest);
        } catch (Exception e) {
            incrementFailure();
            throw new PaymentException(e);
        }
        clearStatus();
        
        idealResponse = responseGenerator.buildResponse(response, idealRequest);
        return idealResponse;
	}

    protected InputStream communicateWithVendor(IdealRequest paymentRequest) {
    	EasyHttpClient client = new EasyHttpClient();
        String request = requestGenerator.buildRequest(paymentRequest);
    	return client.getStream(getServerUrl(), request);
    }

	@Override
	public ServiceStatusType getServiceStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getFailureReportingThreshold() {
		return failureReportingThreshold;
	}

	@Override
	public void setFailureReportingThreshold(Integer failureReportingThreshold) {
		this.failureReportingThreshold = failureReportingThreshold;
		
	}

	@Override
	public String getServerUrl() {
		return serverUrl;
	}

	@Override
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public IdealRequestGenerator getRequestGenerator() {
		return requestGenerator;
	}

	public void setRequestGenerator(IdealRequestGenerator requestGenerator) {
		this.requestGenerator = requestGenerator;
	}

	public IdealResponseGenerator getResponseGenerator() {
		return responseGenerator;
	}

	public void setResponseGenerator(IdealResponseGenerator responseGenerator) {
		this.responseGenerator = responseGenerator;
	}

	
}
