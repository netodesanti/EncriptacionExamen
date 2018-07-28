package com.cenfotec.encrypt.utils.encryption.encryption;

import com.cenfotec.encrypt.utils.enums.EncryptionType;

public class EncryptionFactory {
	public static Encryption create(EncryptionType pType) {
		switch(pType) {
		case SYMETRIC: 
			return new SymetricEncryption();
		case ASYMETRIC:
			return new AsymetricEncryption();
		default:
			return null;
		}
	}
}
