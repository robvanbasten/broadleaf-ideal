package com.bvb.ideal.service.payment;

import org.broadleafcommerce.common.vendor.service.exception.PaymentException;
import org.broadleafcommerce.common.vendor.service.type.ServiceStatusType;

import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.IdealResponse;

public interface IdealPaymentService {

    public IdealResponse process(IdealRequest paymentRequest) throws PaymentException;

    public ServiceStatusType getServiceStatus();

    public Integer getFailureReportingThreshold();

    public void setFailureReportingThreshold(Integer failureReportingThreshold);

    public String getServerUrl();

    public void setServerUrl(String serverUrl);

}
