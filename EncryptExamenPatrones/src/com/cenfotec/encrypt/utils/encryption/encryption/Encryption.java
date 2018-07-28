package com.cenfotec.encrypt.utils.encryption.encryption;

public interface Encryption {

	public void createKey(String pKeyName);
	public void encryptMessage(String pKey, String pName, String pMessage); 
	public void decryptMessage(String pKey, String pName);
}
