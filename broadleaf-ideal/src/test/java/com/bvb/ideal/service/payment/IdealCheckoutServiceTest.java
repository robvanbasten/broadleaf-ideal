package com.bvb.ideal.service.payment;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.type.IdealMethodType;

import org.easymock.EasyMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="IdealCheckoutServiceTest-context.xml")
public class IdealCheckoutServiceTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testMethod() {
		IdealCheckoutService checkoutService = applicationContext.getBean(IdealCheckoutService.class);
		IdealRequest idealRequest = new IdealRequest();
		idealRequest.setMethodType(IdealMethodType.TRANSACTION);
		Order order = EasyMock.createMock(Order.class);
		CompositePaymentResponse response = null;
		try {
			response = checkoutService.initiateIdealCheckout(order, "Bank 1");
//			response = checkoutService.initiateExpressCheckout(order);
		} catch (org.broadleafcommerce.core.payment.service.exception.PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response: " + response);
	}
	

}
