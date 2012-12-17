package com.bvb.ideal.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.checkout.BroadleafCheckoutController;
import org.broadleafcommerce.core.web.order.CartState;
import org.springframework.ui.Model;

import com.bvb.ideal.service.payment.IdealCheckoutService;
import com.bvb.ideal.service.payment.MessageConstants;
import com.bvb.ideal.service.payment.type.IdealPaymentType;

public class BroadleafIdealController extends BroadleafCheckoutController {
    private static final Log LOG = LogFactory.getLog(BroadleafIdealController.class);

    @Resource(name="blIdealCheckoutService")
    protected IdealCheckoutService idealCheckoutService;

    /**
     * The default endpoint to initiate a Ideal Checkout.
     * To use: Create a controller in your application and extend this class.
     *
     * @param request - The Http request
     * @return ModelAndView
     */
    public String idealCheckout(HttpServletRequest request, String selectedBank) throws PaymentException {
        Order cart = CartState.getCart();
        if (!(cart instanceof NullOrderImpl)) {
            CompositePaymentResponse compositePaymentResponse = idealCheckoutService.initiateIdealCheckout(cart, selectedBank);

            for (PaymentInfo paymentInfo : compositePaymentResponse.getPaymentResponse().getResponseItems().keySet()) {
                if (IdealPaymentType.IDEAL.equals(paymentInfo.getType())) {
                    PaymentResponseItem responseItem = compositePaymentResponse.getPaymentResponse().getResponseItems().get(paymentInfo);
                    if (responseItem.getTransactionSuccess()) {
                        return "redirect:" + responseItem.getAdditionalFields().get(MessageConstants.REDIRECTURL);
                    }
                }
            }

            if (LOG.isDebugEnabled()) {
                if (compositePaymentResponse.getPaymentResponse().getResponseItems().size() == 0) {
                    LOG.debug("Payment Response is empty. Please see BLC_PAYMENT_LOG and BLC_PAYMENT_RESPONSE_ITEM for further details.");
                }
            }

        }

        return getCartPageRedirect();
    }
    
    public String idealNogeen(HttpServletRequest request, String selectedBank) throws PaymentException {
    	return null;
    }

    /**
     * The default endpoint that Ideal redirects to on callback.
     * To use: Create a controller in your application and extend this class.
     *
     * Note: assumes there is already a Payment Info of Type IDEAL on the order.
     * This should have already been created before redirecting to Ideal (i.e. initiateIdealCheckout())
     *
     * @param request - The Http request
     * @param transactionId - An Ideal variable sent back as a request parameter
     * @param entrance code - An Ideal variable sent back as a request parameter
     * @return ModelAndView
     */
    public String idealProcess(HttpServletRequest request, HttpServletResponse response, Model model,
    								String transactionId, String entranceCode) throws CheckoutException, PricingException {
        Order cart = CartState.getCart();
        if (!(cart instanceof NullOrderImpl)) {
            //save the transaction id and entrance code on the payment info
            PaymentInfo idealPaymentInfo = null;
            for (PaymentInfo paymentInfo : cart.getPaymentInfos()) {
                if (IdealPaymentType.IDEAL.equals(paymentInfo.getType())) {
                    //There should only be one payment info of type ideal in the order
                    idealPaymentInfo = paymentInfo;
                    paymentInfo.getAdditionalFields().put(MessageConstants.TRANSACTIONID, transactionId);
                    paymentInfo.getAdditionalFields().put(MessageConstants.ENTRANCECODE, entranceCode);
                    break;
                }
            }

            if (idealPaymentInfo != null) {
                orderService.save(cart, false);

                initializeOrderForCheckout(cart);

                CheckoutResponse checkoutResponse = idealCheckoutService.completeIdealCheckout(transactionId, entranceCode, cart);
                if (checkoutResponse == null || !checkoutResponse.getPaymentResponse().getResponseItems().get(idealPaymentInfo).getTransactionSuccess()) {
                    processFailedOrderCheckout(cart);
                    populateModelWithShippingReferenceData(request, model);
                    model.addAttribute("paymentException", true);
                    return getCheckoutView();
                }

                return getConfirmationView(checkoutResponse.getOrder().getOrderNumber());

            }
        }

        return getCartPageRedirect();
    }

}
