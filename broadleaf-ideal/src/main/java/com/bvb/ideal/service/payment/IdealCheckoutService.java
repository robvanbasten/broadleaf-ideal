package com.bvb.ideal.service.payment;

import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;

public interface IdealCheckoutService {

	CompositePaymentResponse initiateIdealCheckout(Order cart, String selectedBank) throws PaymentException;

	CheckoutResponse completeIdealCheckout(String transactionId, String entranceCode,
			Order cart) throws CheckoutException;

}
