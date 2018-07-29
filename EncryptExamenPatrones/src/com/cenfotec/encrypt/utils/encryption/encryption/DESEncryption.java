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
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESEncryption implements Encryption {

	private final String PATH = "/Users/netodesanti/Documents/encrypt/DES/";
	
	@Override
	public void createKey(String pKeyName) {
		try {
			byte[] myKey = generatedSequenceOfBytes();
			writeBytesFile(pKeyName, myKey, ".key");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void encryptMessage(String pKey, String pName, String pMessage) {
		byte[] key;
		try {
			key = readKeyFile(pKey);
			Cipher cipher = Cipher.getInstance("DES");
			SecretKey myKey = new SecretKeySpec(key, "DES");
			
			cipher.init(Cipher.ENCRYPT_MODE, myKey);
			byte[] texto = pMessage.getBytes();
		    byte[] textEncrypted = cipher.doFinal(texto);
			
		    writeBytesFile(pName, textEncrypted, ".encript");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void decryptMessage(String pKey, String pName) {
		byte[] key;
		try {
			key = readKeyFile(pKey);
			Cipher cipher = Cipher.getInstance("DES");
			SecretKey myKey = new SecretKeySpec(key,"DES");
			
			byte[] mensajeEncriptado = readMessageFile(pName);
			cipher.init(Cipher.DECRYPT_MODE, myKey);
			byte[] decryptedData = cipher.doFinal(mensajeEncriptado);
			
			String message = new String(decryptedData, StandardCharsets.UTF_8);
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
	
	private byte[] readKeyFile(String keyName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(PATH + keyName + ".key"));
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
		File file = new File(PATH + messageName + ".encript");
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
		return bytes;
	}
	
	private byte[] generatedSequenceOfBytes() throws Exception {
		StringBuilder randomkey = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			randomkey.append(Integer.parseInt(Double.toString((Math.random() + 0.1) * 1000).substring(0, 2)));
		}
		
		return randomkey.toString().getBytes("UTF-8");
	}
	
}
