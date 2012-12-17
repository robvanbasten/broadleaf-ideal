package com.bvb.ideal.service.payment.type;

import org.broadleafcommerce.common.BroadleafEnumerationType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An extendible enumeration of status types.
 * 
 * @author rob
 */
public class IdealPaymentStatusType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, IdealPaymentStatusType> TYPES = new HashMap<String, IdealPaymentStatusType>();
    public static final IdealPaymentStatusType NONE  = new IdealPaymentStatusType("None", "None");
    public static final IdealPaymentStatusType OPEN = new IdealPaymentStatusType("Open", "Open");
    public static final IdealPaymentStatusType SUCCESS = new IdealPaymentStatusType("Success", "Success");
    public static final IdealPaymentStatusType FAILURE = new IdealPaymentStatusType("Failure", "Failure");
    public static final IdealPaymentStatusType EXPIRED = new IdealPaymentStatusType("Expired", "Expired");
    public static final IdealPaymentStatusType CANCELED = new IdealPaymentStatusType("Canceled", "Canceled");

    public static IdealPaymentStatusType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public IdealPaymentStatusType() {
        //do nothing
    }

    public IdealPaymentStatusType(final String type, final String friendlyType) {
    	this.friendlyType = friendlyType;
        setType(type);
    }

    public String getType() {
        return type;
    }

    public String getFriendlyType() {
		return friendlyType;
	}

	private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdealPaymentStatusType other = (IdealPaymentStatusType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
