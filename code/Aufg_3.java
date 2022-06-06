package code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;

public class Aufg_3 {

	public static void main(String[] args) throws Exception{
		System.out.println("Es wird ein Schlüsselpaar generiert...");
		int keysize=2000;
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keysize);
		KeyPair rsaKeyPair = keyPairGen.generateKeyPair ();
		System.out.println("Schlüsselpaar generiert!");
		System.out.println("Geben Sie einen zu verschlüsselnden Text ein:");
		Scanner sc = new Scanner(System.in);
		String text=sc.nextLine();
		System.out.println("Der Text wird verschlüsselt...");
		String plainText = text;
		PublicKey publicKey = rsaKeyPair.getPublic ();
		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.ENCRYPT_MODE , publicKey);
		byte[] cipherText = rsa.doFinal(plainText.getBytes ());
		Base64.Encoder encoder = Base64.getEncoder ();
		String message = encoder.encodeToString(cipherText);
		System.out.println("Verschlüssselte Nachricht:");
		System.out.println(message);
		
		System.out.println("Soll der veschlüsselte Text auch wieder entschlüsselt werden? (j/n)");
		Scanner sc2 = new Scanner(System.in);
		String answer=sc2.nextLine();
		if(answer.equals("j")){
			System.out.println("Der Text wird nun wieder entschlüsselt...");
			Base64.Decoder decoder= Base64.getDecoder();
			byte[] a= decoder.decode(message);
			rsa.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPrivate());
			byte[] decipheredText = rsa.doFinal(a);
			String b = new String(decipheredText);
			System.out.println("Entschlüssselte Nachricht:");
			System.out.println(b);
			System.exit(0);
		}
		if(answer.equals("n")){
			System.out.println("Das Programm wird beendet.");
			System.exit(0);
		}
		else{
			System.out.println("Falsche Eingabe. Das Programm wird beendet.");
			System.exit(0);
		}
	}
}
