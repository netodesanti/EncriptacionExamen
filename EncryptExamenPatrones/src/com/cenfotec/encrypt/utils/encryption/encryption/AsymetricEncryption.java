package com.cenfotec.encrypt.utils.encryption.encryption;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AsymetricEncryption implements Encryption {

	private final String PUBLIC = "public";
	private final String PRIVATE = "private";
	private final String KEY_EXTENSION = ".key";
	private final String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final String PATH = "/Users/netodesanti/Documents/encrypt/asymetric/";
	private final MethodsHelper helper = new MethodsHelper();
	
	@Override
	public void createKey(String name) {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			KeyFactory fact = KeyFactory.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
			RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

			saveToFile(PATH + name + "public.key", pub.getModulus(), pub.getPublicExponent());
			saveToFile(PATH + name + "private.key", priv.getModulus(), priv.getPrivateExponent());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void encryptMessage(String pKey, String pName, String pMessage) {
		PublicKey pubKey;
		try {
			pubKey = (PublicKey) readKeyFromFile(pKey, PUBLIC);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptedData = cipher.doFinal(pMessage.getBytes(StandardCharsets.UTF_8));
			Encoder oneEncoder = Base64.getEncoder();
			encryptedData = oneEncoder.encode(encryptedData);
			helper.writeBytesFile(pName, encryptedData, MESSAGE_ENCRYPT_EXTENSION, PATH);
		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void decryptMessage(String pKey, String pName) {
		PrivateKey privKey;
		try {
			privKey = (PrivateKey)readKeyFromFile(pKey, PRIVATE);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] encryptedMessage = helper.readMessageFile(pName, PATH);
			byte[] decryptedData = cipher.doFinal(encryptedMessage);
		    String message = new String(decryptedData,StandardCharsets.UTF_8);
		    System.out.println("El mensaje era: ");
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}

	private Key readKeyFromFile(String keyFileName, String type) throws IOException {
		InputStream in = new FileInputStream(PATH + keyFileName + type + KEY_EXTENSION);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			if (type.equals("public")) {
				RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PublicKey pubKey = fact.generatePublic(keySpec);
				return pubKey;
			} else {
				RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PrivateKey pubKey = fact.generatePrivate(keySpec);
				return pubKey;
			}
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();
		}
	}
		
}
