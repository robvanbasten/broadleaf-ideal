package com.bvb.ideal.security.util;

public class IdealSecurityException extends Exception {
		  public IdealSecurityException() {
		  }

		  public IdealSecurityException(String msg) {
		    super(msg);
		  }
		  
		  public IdealSecurityException(Exception e) {
			  super(e);
		  }
}
