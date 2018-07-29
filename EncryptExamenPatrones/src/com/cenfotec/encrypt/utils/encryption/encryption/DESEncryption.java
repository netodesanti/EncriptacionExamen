package com.cenfotec.encrypt.utils.encryption.encryption;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESEncryption implements Encryption {

	private final String PATH = "/Users/netodesanti/Documents/encrypt/DES/";
	private final String KEY_EXTENSION = ".key";
	private final String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final int KEYSIZE = 4;
	private final MethodsHelper helper = new MethodsHelper();
	
	@Override
	public void createKey(String pKeyName) {
		try {
			byte[] myKey = helper.generatedSequenceOfBytes(KEYSIZE);
			helper.writeBytesFile(pKeyName, myKey, KEY_EXTENSION, PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void encryptMessage(String pKey, String pName, String pMessage) {
		byte[] key;
		try {
			key = helper.readKeyFile(pKey, PATH, KEY_EXTENSION);
			Cipher cipher = Cipher.getInstance("DES");
			SecretKey myKey = new SecretKeySpec(key, "DES");
			
			cipher.init(Cipher.ENCRYPT_MODE, myKey);
			byte[] texto = pMessage.getBytes();
		    byte[] textEncrypted = cipher.doFinal(texto);
			
		    helper.writeBytesFile(pName, textEncrypted, MESSAGE_ENCRYPT_EXTENSION, PATH);
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
			Cipher cipher = Cipher.getInstance("DES");
			SecretKey myKey = new SecretKeySpec(key,"DES");
			
			byte[] mensajeEncriptado = helper.readMessageFile(pName, PATH);
			cipher.init(Cipher.DECRYPT_MODE, myKey);
			byte[] decryptedData = cipher.doFinal(mensajeEncriptado);
			
			message = new String(decryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return message;
	}
		
}
