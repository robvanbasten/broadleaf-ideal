package com.bvb.ideal.service.payment.message;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.bvb.ideal.service.payment.type.IdealMethodType;

public class IdealRequest implements Serializable {

    protected IdealMethodType secondaryMethodType;
    protected IdealMethodType methodType;
    
	protected String securePath;
	protected String cachePath;
	protected String privateKeyPass;
	protected String privateKeyFile;
	protected String privateCertificateFile;
	protected String publicCertificateFile;

	// Account settings
	protected boolean AbnAmro = false; // ABN has some issues
	protected String aquirerName;
	protected String aquirerUrl;
	protected boolean testMode = false;

	@XmlTransient
	public IdealMethodType getSecondaryMethodType() {
		return secondaryMethodType;
	}
	public void setSecondaryMethodType(IdealMethodType secondaryMethodType) {
		this.secondaryMethodType = secondaryMethodType;
	}
	@XmlTransient
	public IdealMethodType getMethodType() {
		return methodType;
	}
	public void setMethodType(IdealMethodType methodType) {
		this.methodType = methodType;
	}
	@XmlTransient
	public String getSecurePath() {
		return securePath;
	}
	public void setSecurePath(String securePath) {
		this.securePath = securePath;
	}
	@XmlTransient
	public String getCachePath() {
		return cachePath;
	}
	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	@XmlTransient
	public String getPrivateKeyPass() {
		return privateKeyPass;
	}
	public void setPrivateKeyPass(String privateKeyPass) {
		this.privateKeyPass = privateKeyPass;
	}
	@XmlTransient
	public String getPrivateKeyFile() {
		return privateKeyFile;
	}
	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}
	@XmlTransient
	public String getPrivateCertificateFile() {
		return privateCertificateFile;
	}
	public void setPrivateCertificateFile(String privateCertificateFile) {
		this.privateCertificateFile = privateCertificateFile;
	}
	@XmlTransient
	public String getPublicCertificateFile() {
		return publicCertificateFile;
	}
	public void setPublicCertificateFile(String publicCertificateFile) {
		this.publicCertificateFile = publicCertificateFile;
	}
	@XmlTransient
	public boolean isAbnAmro() {
		return AbnAmro;
	}
	public void setAbnAmro(boolean abnAmro) {
		AbnAmro = abnAmro;
	}
	@XmlTransient
	public String getAquirerName() {
		return aquirerName;
	}
	public void setAquirerName(String aquirerName) {
		this.aquirerName = aquirerName;
	}
	@XmlTransient
	public String getAquirerUrl() {
		return aquirerUrl;
	}
	public void setAquirerUrl(String aquirerUrl) {
		this.aquirerUrl = aquirerUrl;
	}
	@XmlTransient
	public boolean isTestMode() {
		return testMode;
	}
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
}
