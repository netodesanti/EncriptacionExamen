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

public class MethodsHelper {
	private final String MESSAGE_EXT = ".encript";
	
	public void writeBytesFile(String name, byte[] content, String type, String path) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(path + name + type);
		fos.write(content);
		fos.close();
	}
	
	public byte[] generatedSequenceOfBytes(int keysize) throws Exception {
		StringBuilder randomkey = new StringBuilder();
		for (int i = 0; i < keysize; i++) {
			randomkey.append(Integer.parseInt(Double.toString((Math.random() + 0.1) * 1000).substring(0, 2)));
		}
		
		return randomkey.toString().getBytes("UTF-8");
	}
	
	public byte[] readMessageFile(String messageName, String path) throws Exception{
		String substring = path.substring(Math.max(path.length() - 2, 0));
		File file = new File(path + messageName + MESSAGE_EXT);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        //Revisa si es de tipo DES
        if (substring.equals("S/")) {
			return bytes;
		} else {
			Decoder oneDecoder = Base64.getDecoder();
			return oneDecoder.decode(bytes);
		}
	}

	public byte[] readKeyFile(String keyName, String path, String keyExt) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(path + keyName + keyExt));
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

}
