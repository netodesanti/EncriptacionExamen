package com.cenfotec.encrypt.utils.encryption.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SymetricEncryption implements Encryption {

	private final int KEYSIZE = 8;
	private final String KEY_EXTENSION = ".key";
	private final String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final String PATH = "/Users/netodesanti/Documents/encrypt/symetric/";
	private final MethodsHelper helper = new MethodsHelper();
	
	@Override
	public void createKey(String pKeyName) {
		byte[] key;
		try {
			key = helper.generatedSequenceOfBytes(KEYSIZE);
			helper.writeBytesFile(pKeyName, key, KEY_EXTENSION, PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void encryptMessage(String pKey, String pName, String pMessage) {
		byte[] key;
		try {
			key = helper.readKeyFile(pKey, PATH, KEY_EXTENSION);
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] encryptedData = cipher.doFinal(pMessage.getBytes(StandardCharsets.UTF_8));
			Encoder oneEncoder = Base64.getEncoder();
			encryptedData = oneEncoder.encode(encryptedData);
			helper.writeBytesFile(pName, encryptedData, MESSAGE_ENCRYPT_EXTENSION, PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String decryptMessage(String pKey, String pName) {
		byte[] key;
		String message = null;
		try {
			key = helper.readKeyFile(pKey, PATH, KEY_EXTENSION);
			byte[] encryptedMessage = helper.readMessageFile(pName, PATH);
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key,"AES");
			cipher.init(Cipher.DECRYPT_MODE, k);
			byte[] DecryptedData = cipher.doFinal(encryptedMessage);
			message = new String(DecryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return message;
	}

}
