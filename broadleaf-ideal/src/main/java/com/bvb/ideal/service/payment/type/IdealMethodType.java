package com.bvb.ideal.service.payment.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;

public class IdealMethodType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, IdealMethodType> TYPES = new HashMap<String, IdealMethodType>();

    public static final IdealMethodType CHECKOUT  = new IdealMethodType("Checkout", "Checkout");
    public static final IdealMethodType PROCESS = new IdealMethodType("Process", "Process");
    public static final IdealMethodType ISSUER = new IdealMethodType("Issuer", "Issuer");
    public static final IdealMethodType STATUS = new IdealMethodType("Status", "Status");
    public static final IdealMethodType TRANSACTION = new IdealMethodType("Transaction", "Transaction");

    public static IdealMethodType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public IdealMethodType() {
        //do nothing
    }

    public IdealMethodType(final String type, final String friendlyType) {
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
		result = prime * result
				+ ((friendlyType == null) ? 0 : friendlyType.hashCode());
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
		IdealMethodType other = (IdealMethodType) obj;
		if (friendlyType == null) {
			if (other.friendlyType != null)
				return false;
		} else if (!friendlyType.equals(other.friendlyType))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
