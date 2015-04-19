package util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Encrypt {
	
	public static String key = "zMbDdG4D02GKm2IxOWQp99==";
	public static Key aesKey;
	public static Cipher cipher;
	
	 public static String encrypt(String text) {
			byte[] raw;
			String encryptedString;
			SecretKeySpec skeySpec;
			byte[] encryptText = text.getBytes();
			Cipher cipher;
			try {
				raw = Base64.getDecoder().decode(key);
				skeySpec = new SecretKeySpec(raw, "AES");
				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(encryptText));
			} 
			catch (Exception e) {
				e.printStackTrace();
				return "Error";
			}
			return encryptedString;
		}

		public static String decrypt(String text) {
			Cipher cipher;
			String encryptedString;
			byte[] encryptText = null;
			byte[] raw;
			SecretKeySpec skeySpec;
			try {
				raw = Base64.getDecoder().decode(key);
				skeySpec = new SecretKeySpec(raw, "AES");
				encryptText = Base64.getDecoder().decode(text);
				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				encryptedString = new String(cipher.doFinal(encryptText));
			} catch (Exception e) {
				e.printStackTrace();
				return "Error";
			}
			return encryptedString;
		}

}
