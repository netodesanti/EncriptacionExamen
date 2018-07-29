package com.cenfotec.encrypt.controllers;

import com.cenfotec.encrypt.utils.encryption.EncryptionManager;
import com.cenfotec.encrypt.utils.enums.EncryptionType;

public class EncryptController {
	
	private EncryptionManager encryptManager = new EncryptionManager();

	public void createKey(String name, EncryptionType pType) {
		encryptManager.createKey(name, pType);
	}

	public void encryptMessage(String messageName, String message, String name, EncryptionType pType) {
		encryptManager.encryptMessage(messageName, message, name, pType);
	}

	public String decryptMessage(String messageName, String keyName, EncryptionType pType) {
		return encryptManager.decryptMessage(keyName, messageName, pType);
	}

}
