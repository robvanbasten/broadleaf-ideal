package com.bvb.ideal.security.util;

public interface IdealSecurityHelper {
	
	public String sign(byte[] message) throws IdealSecurityException;
	
	public String getCertificate(String alias)  throws IdealSecurityException;
	
	public String getPrivateKey() throws IdealSecurityException;
	
	public boolean verify(byte[] message, byte[] sigValue) throws IdealSecurityException;
	
	public String getEntranceCode() throws IdealSecurityException;

}
