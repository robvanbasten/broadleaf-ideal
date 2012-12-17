package com.bvb.ideal.service.payment;

import java.util.Map;

import com.bvb.ideal.service.payment.message.IdealRequest;

public interface IdealRequestGenerator {

    String buildRequest(IdealRequest paymentRequest);

    Map<String, String> getAdditionalConfig();

    void setAdditionalConfig(Map<String, String> additionalConfig);

    String getCancelUrl();

    void setCancelUrl(String cancelUrl);

    String getLibVersion();

    void setLibVersion(String libVersion);

    String getReturnUrl();

    void setReturnUrl(String returnUrl);

    String getMerchantId();

    void setMerchantId(String merchantId);

    String getSubId();

    void setSubId(String subId);

    void setAuthentication(String authentication);

    String getAuthentication();

}
