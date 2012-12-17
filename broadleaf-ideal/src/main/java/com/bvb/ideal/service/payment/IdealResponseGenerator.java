package com.bvb.ideal.service.payment;

import java.io.InputStream;

import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.IdealResponse;

public interface IdealResponseGenerator {

    public IdealResponse buildResponse(InputStream response, IdealRequest idealRequest);

}
