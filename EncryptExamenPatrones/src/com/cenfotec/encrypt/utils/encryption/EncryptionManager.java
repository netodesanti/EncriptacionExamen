package com.cenfotec.encrypt.utils.encryption;

import com.cenfotec.encrypt.utils.encryption.encryption.Encryption;
import com.cenfotec.encrypt.utils.encryption.encryption.EncryptionFactory;
import com.cenfotec.encrypt.utils.enums.EncryptionType;

public class EncryptionManager {

	Encryption encryption;
	
	public void createKey(String pKeyName, EncryptionType pType) {
		encryption = EncryptionFactory.create(pType);
		encryption.createKey(pKeyName);
	}
	
	public void encryptMessage(String pMessageName, String pMessage, String pKeyName, EncryptionType pType) {
		encryption = EncryptionFactory.create(pType);
		encryption.encryptMessage(pKeyName, pMessageName, pMessage);
	}
	
	public String decryptMessage(String pKeyName, String pMessageName, EncryptionType pType) {
		encryption = EncryptionFactory.create(pType);
		return encryption.decryptMessage(pKeyName, pMessageName);
	}
}
