package com.cenfotec.encrypt.utils.encryption.encryption;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SymetricEncryption implements Encryption {

	private final int KEYSIZE = 8;
	private final String KEY_EXTENSION = ".key";
	private final String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final String PATH = "/Users/netodesanti/Documents/encrypt/asymetric/";

	@Override
	public void createKey(String pKeyName) {
		byte[] key;
		try {
			key = generatedSequenceOfBytes();
			writeBytesFile(pKeyName, key, KEY_EXTENSION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void encryptMessage(String pKey, String pName, String pMessage) {
		byte[] key;
		try {
			key = readKeyFile(pKey);
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] encryptedData = cipher.doFinal(pMessage.getBytes(StandardCharsets.UTF_8));
			Encoder oneEncoder = Base64.getEncoder();
			encryptedData = oneEncoder.encode(encryptedData);
			writeBytesFile(pName, encryptedData, MESSAGE_ENCRYPT_EXTENSION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decryptMessage(String pKey, String pName) {
		byte[] key;
		try {
			key = readKeyFile(pKey);
			byte[] encryptedMessage = readMessageFile(pName);
			System.out.println(encryptedMessage.length);
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key,"AES");
			cipher.init(Cipher.DECRYPT_MODE, k);
			byte[] DecryptedData = cipher.doFinal(encryptedMessage);
			String message = new String(DecryptedData, StandardCharsets.UTF_8);
			System.out.println("El mensaje era: ");
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeBytesFile(String name, byte[] content, String type) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(PATH + name + type);
		fos.write(content);
		fos.close();
	}

	private byte[] generatedSequenceOfBytes() throws Exception {
		StringBuilder randomkey = new StringBuilder();
		for (int i = 0; i < KEYSIZE; i++) {
			randomkey.append(Integer.parseInt(Double.toString((Math.random() + 0.1) * 1000).substring(0, 2)));
		}
		return randomkey.toString().getBytes("UTF-8");
	}

	private byte[] readKeyFile(String keyName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(PATH + keyName + KEY_EXTENSION));
		String everything = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		return everything.getBytes(StandardCharsets.UTF_8);
	}
	
	private byte[] readMessageFile(String messageName) throws Exception{
		File file = new File(PATH + messageName + MESSAGE_ENCRYPT_EXTENSION);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        Decoder oneDecoder = Base64.getDecoder();
	    reader.close();
		return oneDecoder.decode(bytes);
	}
}
