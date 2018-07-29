package com.cenfotec.encrypt.pruebas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.cenfotec.encrypt.controllers.EncryptController;
import com.cenfotec.encrypt.utils.encryption.encryption.AsymetricEncryption;
import com.cenfotec.encrypt.utils.encryption.encryption.DESEncryption;
import com.cenfotec.encrypt.utils.encryption.encryption.EncryptionFactory;
import com.cenfotec.encrypt.utils.encryption.encryption.SymetricEncryption;
import com.cenfotec.encrypt.utils.enums.EncryptionType;

public class EncryptApplicationTest {
	private EncryptController mainController = new EncryptController();
	
	private String keyName = "test";
	private String msgName = "prueba";
	private String message = "esto es una prueba";
	
	@Test
	public void factoryTest() {
		assertEquals(SymetricEncryption.class, EncryptionFactory.create(EncryptionType.SYMETRIC).getClass());
		assertEquals(AsymetricEncryption.class, EncryptionFactory.create(EncryptionType.ASYMETRIC).getClass());
		assertEquals(DESEncryption.class, EncryptionFactory.create(EncryptionType.DES).getClass());
	}

	@Test
	public void decryptSymetricTest() {
		mainController.createKey(keyName, EncryptionType.SYMETRIC);
		mainController.encryptMessage(msgName, message, keyName, EncryptionType.SYMETRIC);
		assertEquals(message, mainController.decryptMessage(msgName, keyName, EncryptionType.SYMETRIC));
	}
	
	@Test
	public void decryptAsymetricTest() {
		mainController.createKey(keyName, EncryptionType.ASYMETRIC);
		mainController.encryptMessage(msgName, message, keyName, EncryptionType.ASYMETRIC);
		assertEquals(message, mainController.decryptMessage(msgName, keyName, EncryptionType.ASYMETRIC));
	}
	
	@Test
	public void decryptDESTest() {
		mainController.createKey(keyName, EncryptionType.DES);
		mainController.encryptMessage(msgName, message, keyName, EncryptionType.DES);
		assertEquals(message, mainController.decryptMessage(msgName, keyName, EncryptionType.DES));
	}
}
