package com.bvb.ideal.service.payment;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.payment.domain.AmountItem;
import org.broadleafcommerce.core.payment.domain.AmountItemImpl;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentInfoImpl;
import org.broadleafcommerce.core.payment.service.PaymentInfoFactory;

import com.bvb.ideal.service.payment.type.IdealPaymentType;

public class IdealPaymentInfoFactoryImpl implements PaymentInfoFactory {

	@Override
	public PaymentInfo constructPaymentInfo(Order order) {
        PaymentInfoImpl paymentInfo = new PaymentInfoImpl();
        paymentInfo.setOrder(order);
        paymentInfo.setType(IdealPaymentType.IDEAL);
        paymentInfo.setReferenceNumber(String.valueOf(order.getId()));
        paymentInfo.setAmount(order.getTotal());
        paymentInfo.getAdditionalFields().put(MessageConstants.SUBTOTAL, order.getSubTotal().toString());
        paymentInfo.getAdditionalFields().put(MessageConstants.TOTALSHIPPING, order.getTotalShipping().toString());
        paymentInfo.getAdditionalFields().put(MessageConstants.TOTALTAX, order.getTotalTax().toString());
        for (OrderItem orderItem : order.getOrderItems()) {
            AmountItem amountItem = new AmountItemImpl();
            if (DiscreteOrderItem.class.isAssignableFrom(orderItem.getClass())) {
                amountItem.setDescription(((DiscreteOrderItem)orderItem).getSku().getDescription());
                amountItem.setSystemId(String.valueOf(((DiscreteOrderItem) orderItem).getSku().getId()));
            } else if (BundleOrderItem.class.isAssignableFrom(orderItem.getClass())) {
                amountItem.setDescription(((BundleOrderItem)orderItem).getSku().getDescription());
                amountItem.setSystemId(String.valueOf(((BundleOrderItem) orderItem).getSku().getId()));
            }
            amountItem.setShortDescription(orderItem.getName());
            amountItem.setPaymentInfo(paymentInfo);
            amountItem.setQuantity((long) orderItem.getQuantity());
            amountItem.setUnitPrice(orderItem.getPrice().getAmount());
            paymentInfo.getAmountItems().add(amountItem);
        }

        return paymentInfo;
	}

}
