package code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

public class Aufg_3 {

	public static void main(String[] args) throws Exception{
		
		int keysize=2000;
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keysize);
		KeyPair rsaKeyPair = keyPairGen.generateKeyPair ();
		String plainText = "Kryptographie macht immer noch Spass!!!";
		PublicKey publicKey = rsaKeyPair.getPublic ();
		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.ENCRYPT_MODE , publicKey);
		byte[] cipherText = rsa.doFinal(plainText.getBytes ());
		Base64.Encoder encoder = Base64.getEncoder ();
		String message = encoder.encodeToString(cipherText);
		System.out.println("Verschlüssselte Nachricht:");
		System.out.println(message);
		
		
		Base64.Decoder decoder= Base64.getDecoder();
		byte[] a= decoder.decode(message);
		rsa.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPrivate());
		byte[] decipheredText = rsa.doFinal(a);
		String b = new String(decipheredText);
		System.out.println("Entschlüssselte Nachricht:");
		System.out.println(b);
	}
}
