package com.bvb.ideal.service.payment.message;

import java.util.List;

import com.bvb.ideal.service.payment.message.details.IdealError;

public interface ErrorCheckable {

    public List<IdealError> getErrors();

    public void setErrors(List<IdealError> errorResponses);

}
