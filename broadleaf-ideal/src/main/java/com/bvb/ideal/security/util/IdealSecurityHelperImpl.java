package com.bvb.ideal.security.util;

import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

/**
 * Sign XML file.
 */
public class IdealSecurityHelperImpl implements IdealSecurityHelper {

    private KeyStore keyStore = null;
    private Signature signatureInstance = null;
    private PrivateKey privateKey = null;
    private X509Certificate certificate = null;
    

    public IdealSecurityHelperImpl(String algorithm, String keyStoreType, String keyStoreName, String keyStorePass, String keyAlias, String privateKeyPass, String certAlias) {
    	init(algorithm, keyStoreType, keyStoreName, keyStorePass, keyAlias, privateKeyPass, certAlias);
    }
    
	private void init(String algorithm, String keyStoreType, String keyStoreName, String keyStorePass, String keyAlias, String privateKeyPass, String certAlias) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
	       	signatureInstance = Signature.getInstance(algorithm, "BC");
			keyStore = KeyStore.getInstance(keyStoreType);
				keyStore.load(
						Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStoreName),  
						keyStorePass.toCharArray()
						);
			Assert.notNull(keyStore, "Unable to load keystore: "+keyStoreName);
				
			privateKey = (PrivateKey) keyStore.getKey(
			    keyAlias,
			    privateKeyPass.toCharArray()
			);
			Assert.notNull(privateKey, "Unable to find privatekey in the keystore: "+keyAlias);
			certificate = (X509Certificate) keyStore.getCertificate(certAlias);
			Assert.notNull(certificate, "Unable to find certificate in the keystore: "+certAlias + " keystore: "+keyStoreName);
        } catch (Exception e) {
        	e.printStackTrace();
        }

	}

	@Override
	public String getCertificate(String alias) throws IdealSecurityException {
		X509Certificate certf = null;
		String encoded = null;
		try {
			certf = (X509Certificate) keyStore.getCertificate(alias);
		} catch (Exception e) {
        	e.printStackTrace();
			
		}
		try {
			encoded = sha(certf.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new IdealSecurityException(e);
		} catch (CertificateEncodingException e) {
			throw new IdealSecurityException(e);
		}
		return encoded;
/*		try {
			return sha(this.certificate.getEncoded());
		} catch (CertificateEncodingException e) {
			throw new IdealSecurityException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IdealSecurityException(e);
		}
*/	}
	
	@Override
	public String sign(byte[] message) throws IdealSecurityException {
		try {
			signatureInstance.initSign(this.privateKey);
			signatureInstance.update(message);
			return Base64.encodeBase64String(signatureInstance.sign());
		} catch (Exception e) {
			throw new IdealSecurityException(e);
		}
	}

	@Override
	public boolean verify(byte[] message, byte[] sigValue) throws IdealSecurityException {
		try {
			signatureInstance.initVerify(certificate.getPublicKey());
			signatureInstance.update(message);
			return signatureInstance.verify(Base64.decodeBase64(sigValue));
		} catch (InvalidKeyException e) {
        	e.printStackTrace();
//			throw new IdealSecurityException(e);
		} catch (SignatureException e) {
        	e.printStackTrace();
//			throw new IdealSecurityException(e);
		}
		return false;
	}

	@Override
	public String getPrivateKey() throws IdealSecurityException {
		String privateKeyAsString = null;
		try {
			privateKeyAsString = sha(privateKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new IdealSecurityException(e);
		}
		return privateKeyAsString;
	}

	private String sha (byte[] message) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(message);
 
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString().toUpperCase();
	}

	@Override
	public String getEntranceCode() throws IdealSecurityException {
		// sha1(rand(1000000, 9999999))
		// 1644244164343548
		Random rand = new Random();
		int num = 1000000 + rand.nextInt(9999999);
		byte[] b = Integer.toString(num).getBytes();
		try {
			return sha(Integer.toString(num).getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new IdealSecurityException(e);
		}
//		long num = 1000000000000000L + rand.nextLong();
//		if (num < 0L) num = num * -1;
//		MessageDigest md = MessageDigest.getInstance("SHA1");
//        md.up.update(num);
//
//		//		return Long.toString(num).substring(0, 16);
////		return "0179180624982125";
//		return "d966f5d12737f3c85685e3911931e3781231d846";
	}

}