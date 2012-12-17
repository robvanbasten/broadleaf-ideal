package com.bvb.ideal.service.payment;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bvb.ideal.security.util.IdealSecurityException;
import com.bvb.ideal.security.util.IdealSecurityHelperImpl;
import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.message.details.IdealIssuer;
import com.bvb.ideal.service.payment.message.details.IdealIssuerRequest;
import com.bvb.ideal.service.payment.message.details.IdealMerchant;
import com.bvb.ideal.service.payment.message.details.IdealStatusRequest;
import com.bvb.ideal.service.payment.message.details.IdealTrx;
import com.bvb.ideal.service.payment.message.details.IdealTrxRequest;
import com.bvb.ideal.service.payment.type.IdealMethodType;

public class IdealRequestGeneratorImpl implements IdealRequestGenerator {

	private static final Log LOG = LogFactory.getLog(IdealRequestGeneratorImpl.class);

    protected String merchantId;
    protected String subId;
    protected String libVersion;
    protected String returnUrl;
    protected String cancelUrl;
    protected String authentication;
    protected String createDateTimeStamp;
	protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");

    protected String token;
    protected String tokenCode;
    protected String entranceCode;
    
    
    @Resource
    private IdealSecurityHelperImpl securityHelper;

    protected Map<String, String> additionalConfig;

    public IdealRequestGeneratorImpl() {
    }
    @Override
	public String buildRequest(IdealRequest idealRequest) {
		String requestBody = null;
		
		if (IdealMethodType.CHECKOUT.equals(idealRequest.getMethodType())) {
        	
        } else if (IdealMethodType.ISSUER.equals(idealRequest.getMethodType())) {
        	requestBody = processIssuerRequest((IdealIssuerRequest) idealRequest);
        } else if (IdealMethodType.PROCESS.equals(idealRequest.getMethodType())) {
        	
        } else if (IdealMethodType.STATUS.equals(idealRequest.getMethodType())) {
        	requestBody = processStatusRequest((IdealStatusRequest) idealRequest);
        } else if (IdealMethodType.TRANSACTION.equals(idealRequest.getMethodType())) {
        	requestBody = processTrxRequest((IdealTrxRequest) idealRequest);
        }

		return requestBody;
	}

    private String processIssuerRequest(IdealIssuerRequest idealRequest) {
		String frmtDateTime = formatter.format(Calendar.getInstance().getTime());
		idealRequest.setCreateDateTimeStamp(frmtDateTime);
    	String trustedMessage = frmtDateTime +
    			getMerchantId() +
    			getSubId();
    	
    	trustedMessage = trustedMessage.replaceAll("\\s", "");
    	System.out.println("trusted: "+trustedMessage);
    	try {
			setTokenCode(securityHelper.sign(trustedMessage.getBytes()));
        	setToken(securityHelper.getCertificate("com.bvb.ideal"));
		} catch (IdealSecurityException e) {
			e.printStackTrace();
		}
    	
    	IdealMerchant merchant = new IdealMerchant();
    	merchant.setAuthentication(getAuthentication());
    	merchant.setMerchantId(getMerchantId());
    	merchant.setSubId(getSubId());
    	merchant.setToken(getToken().toString());
    	merchant.setTokenCode(getTokenCode().toString());
    	idealRequest.setMerchant(merchant);

    	return marchallRequest(idealRequest);

    }
    
    private String processTrxRequest(IdealTrxRequest idealRequest) {
		String frmtDateTime = formatter.format(Calendar.getInstance().getTime());
       	try {
			setEntranceCode(securityHelper.getEntranceCode());
			String trustedMessage = frmtDateTime +
    			idealRequest.getIdealIssuer().getIssuerId() +
    			getMerchantId() +
    			getSubId() +
    			getReturnUrl() +
    			idealRequest.getIdealTrx().getPurchaseId() +
    			idealRequest.getIdealTrx().getAmount() +
    			"EURnl" +
    			idealRequest.getIdealTrx().getDescription() +
    			getEntranceCode();
			trustedMessage = trustedMessage.replaceAll("\\s", "");
			setTokenCode(securityHelper.sign(trustedMessage.getBytes()));
        	setToken(securityHelper.getCertificate("com.bvb.ideal"));
		} catch (IdealSecurityException e) {
			e.printStackTrace();
		}

		// from request
		
		// from generator
		IdealMerchant merchant = new IdealMerchant();
		merchant.setAuthentication(getAuthentication());
		merchant.setMerchantId(getMerchantId());
		merchant.setSubId(getSubId());
		merchant.setToken(getToken());
		merchant.setTokenCode(getTokenCode());
		merchant.setMerchantReturnURL(getReturnUrl());
		idealRequest.setIdealMerchant(merchant);

		// from request
		IdealIssuer issuer = new IdealIssuer();
		issuer.setIssuerId(idealRequest.getIdealIssuer().getIssuerId());
		idealRequest.setIdealIssuer(issuer);

		IdealTrx trx = new IdealTrx();
		trx.setAmount(idealRequest.getIdealTrx().getAmount());
		trx.setCurrency(idealRequest.getIdealTrx().getCurrency());
		trx.setDescription(idealRequest.getIdealTrx().getDescription());
		
		//from generator
		trx.setEntranceCode(getEntranceCode());
		
		// from request
		trx.setExpirationPeriod(idealRequest.getIdealTrx().getExpirationPeriod());
		trx.setLanguage(idealRequest.getIdealTrx().getLanguage());
		trx.setPurchaseId(idealRequest.getIdealTrx().getPurchaseId());
		idealRequest.setIdealTrx(trx);

		// from generator
		idealRequest.setCancelUrl(getCancelUrl());
		idealRequest.setReturnUrl(getReturnUrl());
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("TRXREQUEST: "+marchallRequest(idealRequest));
		}
		return marchallRequest(idealRequest);

    }

    private String processStatusRequest(IdealStatusRequest idealRequest) {
		String frmtDateTime = formatter.format(Calendar.getInstance().getTime());
    	String trustedMessage = frmtDateTime +
    			getMerchantId() +
    			getSubId() +
    			idealRequest.getTransaction().getTransactionId();
    	trustedMessage = trustedMessage.replaceAll("\\s", "");
    	try {
			setTokenCode(securityHelper.sign(trustedMessage.getBytes()));
        	setToken(securityHelper.getCertificate("com.bvb.ideal"));
		} catch (IdealSecurityException e) {
			e.printStackTrace();
		}
    
		// from generator
		IdealMerchant merchant = new IdealMerchant();
		merchant.setAuthentication(getAuthentication());
		merchant.setMerchantId(getMerchantId());
		merchant.setSubId(getSubId());
		merchant.setToken(getToken());
		merchant.setTokenCode(getTokenCode());
		idealRequest.setMerchant(merchant);

		// from request
		IdealTrx trx = new IdealTrx();
		trx.setTransactionId(idealRequest.getTransaction().getTransactionId());
		idealRequest.setTransaction(trx);
		if (LOG.isDebugEnabled()) {
			LOG.debug("STATUSREQUEST: "+marchallRequest(idealRequest));
		}
    	return marchallRequest(idealRequest);
    }


    private String marchallRequest(Object idealRequest) {
		// create JAXB context and instantiate marshaller
	    JAXBContext context;
	 // Create a stringWriter to hold the XML
        final StringWriter stringWriter = new StringWriter();
		try {
			context = JAXBContext.newInstance(idealRequest.getClass());
		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.idealdesk.com/Message");
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    m.marshal(idealRequest, stringWriter);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
	

	public String getLibVersion() {
		return libVersion;
	}
	public void setLibVersion(String libVersion) {
		this.libVersion = libVersion;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public Map<String, String> getAdditionalConfig() {
		return additionalConfig;
	}
	public void setAdditionalConfig(Map<String, String> additionalConfig) {
		this.additionalConfig = additionalConfig;
	}
	
	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getCreateDateTimeStamp() {
		return createDateTimeStamp;
	}

	public void setCreateDateTimeStamp(String createDateTimeStamp) {
		this.createDateTimeStamp = createDateTimeStamp;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}
	
	public String getEntranceCode() {
		return entranceCode;
	}
	public void setEntranceCode(String entranceCode) {
		this.entranceCode = entranceCode;
	}
}
