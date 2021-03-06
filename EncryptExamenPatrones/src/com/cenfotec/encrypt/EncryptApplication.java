package com.cenfotec.encrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cenfotec.encrypt.controllers.EncryptController;
import com.cenfotec.encrypt.utils.enums.EncryptionType;

public class EncryptApplication {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static EncryptController mainController = new EncryptController();

	public static void main(String[] args) throws Exception {
		int option = 0;
		do {
			System.out.println("-------------------------------");
			System.out.println("1.Create Symetric Key");
			System.out.println("2.Encrypt Symetric Message");
			System.out.println("3.Decrypt Symetric Message");
			System.out.println("============================");
			System.out.println("4.Create Asymetric Key");
			System.out.println("5.Encrypt Asymetric Message");
			System.out.println("6.Decrypt Asymetric Message");
			System.out.println("============================");
			System.out.println("7.Create DES Key");
			System.out.println("8.Encrypt DES Message");
			System.out.println("9.Decrypt DES Message");
			System.out.println("0.Exit ");
			System.out.println("-------------------------------");
			option = Integer.parseInt(br.readLine());
			if (option >= 1 && option <= 9) {
				executeAction(option);
			}
		} while (option != 0);

	}

	private static void executeAction(int option) throws Exception {
		// Symetric encryption
		if (option == 1) {
			System.out.println("Symetric key name: ");
			String keyName = br.readLine();
			mainController.createKey(keyName, EncryptionType.SYMETRIC);
		}
		if (option == 2) {
			System.out.println("Symetric key name: ");
			String keyName = br.readLine();
			System.out.println("Symetric message name: ");
			String messageName = br.readLine();
			System.out.println("Symetric message: ");
			String message = br.readLine();
			mainController.encryptMessage(messageName, message, keyName, EncryptionType.SYMETRIC);
		}
		if (option == 3) {
			System.out.println("Symetric key name: ");
			String keyName = br.readLine();
			System.out.println("Symetric message name: ");
			String messageName = br.readLine();
			System.out.println("El mensaje era: ");
			System.out.println(mainController.decryptMessage(messageName, keyName, EncryptionType.SYMETRIC));
		}

		// Asymetric encryption
		if (option == 4) {
			System.out.println("Asymetric key name: ");
			String keyName = br.readLine();
			mainController.createKey(keyName, EncryptionType.ASYMETRIC);
		}
		if (option == 5) {
			System.out.println("Asymetric key name: ");
			String keyName = br.readLine();
			System.out.println("Asymetric message name: ");
			String messageName = br.readLine();
			System.out.println("Asymetric message: ");
			String message = br.readLine();
			mainController.encryptMessage(messageName, message, keyName, EncryptionType.ASYMETRIC);
		}
		if (option == 6) {
			System.out.println("Asymetric key name: ");
			String keyName = br.readLine();
			System.out.println("Asymetric message name: ");
			String messageName = br.readLine();
			System.out.println("El mensaje era: ");
			System.out.println(mainController.decryptMessage(messageName, keyName, EncryptionType.ASYMETRIC));
		}

		// DES Symetric encryption
		if (option == 7) {
			System.out.println("DES key name: ");
			String keyName = br.readLine();
			mainController.createKey(keyName, EncryptionType.DES);
		}
		if (option == 8) {
			System.out.println("DES key name: ");
			String keyName = br.readLine();
			System.out.println("DES message name: ");
			String messageName = br.readLine();
			System.out.println("DES message: ");
			String message = br.readLine();
			mainController.encryptMessage(messageName, message, keyName, EncryptionType.DES);
		}
		if (option == 9) {
			System.out.println("DES key name: ");
			String keyName = br.readLine();
			System.out.println("DES message name: ");
			String messageName = br.readLine();
			System.out.println("El mensaje era: ");
			System.out.println(mainController.decryptMessage(messageName, keyName, EncryptionType.DES));
		}

	}
}
