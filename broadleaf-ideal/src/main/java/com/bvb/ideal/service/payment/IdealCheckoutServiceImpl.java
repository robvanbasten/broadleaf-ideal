package com.bvb.ideal.service.payment;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.CompositePaymentService;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;

import com.bvb.ideal.service.payment.type.IdealPaymentType;
import com.bvb.payment.service.module.IdealPaymentModule;

public class IdealCheckoutServiceImpl implements IdealCheckoutService {

    @Resource(name="blCheckoutService")
    protected CheckoutService checkoutService;

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    @Resource(name="blCompositePaymentService")
    protected CompositePaymentService compositePaymentService;

    @Resource(name="blIdealModule")
    protected IdealPaymentModule idealPaymentModule;
    
    @Resource(name = "blIdealIssuerService")
    protected IdealIssuerService issuerService;

	@Override
	public CompositePaymentResponse initiateIdealCheckout(Order order, String selectedBank) throws PaymentException {

		idealPaymentModule.setIssuerId(issuerService.getIssuerId(selectedBank));
        return compositePaymentService.executePaymentForGateway(order, new IdealPaymentInfoFactoryImpl());
	}

	@Override
	public CheckoutResponse completeIdealCheckout(String transactionId,	String entranceCode, Order order) throws CheckoutException {
        PaymentInfo idealPaymentInfo = null;
		Map<PaymentInfo, Referenced> payments = new HashMap<PaymentInfo, Referenced>();
        for (PaymentInfo paymentInfo : order.getPaymentInfos()) {
            if (IdealPaymentType.IDEAL.equals(paymentInfo.getType())) {
                paymentInfo.getAdditionalFields().put(MessageConstants.TRANSACTIONID, transactionId);
                paymentInfo.getAdditionalFields().put(MessageConstants.ENTRANCECODE, entranceCode);
                payments.put(paymentInfo, paymentInfo.createEmptyReferenced());
                idealPaymentInfo = paymentInfo;
                break;
            }
        }
        
        CheckoutResponse checkoutResponse = checkoutService.performCheckout(order, payments);

        PaymentResponseItem responseItem = checkoutResponse.getPaymentResponse().getResponseItems().get(idealPaymentInfo);
        if (responseItem.getTransactionSuccess()) {
            //Fill out a few customer values for anonymous customers
            Customer customer = order.getCustomer();
            if (customer == null) {
            	customer = customerService.createCustomer();
            	customer.setFirstName(responseItem.getCustomer().getFirstName());
            	customer.setLastName(responseItem.getCustomer().getLastName());
            	customer.setEmailAddress(responseItem.getCustomer().getEmailAddress());
            }
//            if (customer.getFirstName().isEmpty() && responseItem.getCustomer() != null && responseItem.getCustomer().getFirstName() != null) {
//                customer.setFirstName(responseItem.getCustomer().getFirstName());
//            }
//            if (customer.getLastName().isEmpty() && responseItem.getCustomer() != null && responseItem.getCustomer().getLastName() != null) {
//                customer.setLastName(responseItem.getCustomer().getLastName());
//            }
//            if (customer.getEmailAddress().isEmpty() && responseItem.getCustomer() != null && responseItem.getCustomer().getEmailAddress() != null) {
//                customer.setEmailAddress(responseItem.getCustomer().getEmailAddress());
//            }
        	order.setCustomer(customer);
            customerService.saveCustomer(customer, false);
        }


        return checkoutResponse;

	}

}
