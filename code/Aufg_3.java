package code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class Aufg_3 {
    public static void main(String[] args) throws Exception{
        System.out.println("Es wird ein Schlüsselpaar generiert...");
        KeyPair keypair = generateKeyPair();
        System.out.println("Schlüsselpaar generiert!");
        int i = 0;
        while(i==0){
            System.out.println("Geben Sie einen zu verschlüsselnden Text ein:");
		    Scanner Text = new Scanner(System.in);
		    String text=Text.nextLine();
		    System.out.println("Der Text wird verschlüsselt...");

            String CipherText=encrypt(text,keypair);
            System.out.println("Verschlüssselte Nachricht:");
		    System.out.println(CipherText);

        
		    Scanner decission = new Scanner(System.in);
		
        
            int k=0;
            while(k==0){ 
                System.out.println("Soll der veschlüsselte Text auch wieder entschlüsselt werden? (j/n)");
                String answer=decission.nextLine();   
		        switch (answer) {
		    	    case "j":
                        System.out.println("Der Text wird nun wieder entschlüsselt...");
			    	    String decipheredText=decrypt(CipherText,keypair);
                        System.out.println("Entschlüssselte Nachricht:");
                        System.out.println(decipheredText);
                        k=1;
                        break;
			        case "n":
                        k=1;
                        break;
			        default:
				        System.out.println("Falsche Eingabe! Benutzung: j/n");
		        }
            }
            int j = 0;
            while(j==0){
                System.out.println("Wollen sie noch einen Text verschlüsseln? (j/n)");
                String answer=decission.nextLine();

                switch (answer) {
                    case "j":
                        i=0;
                        j=1;
                        break;
                    case "n":
                        i=1;
                        j=1;
                        break;
                    default:
                        i=1;
                        System.out.println("Falsche Eingabe! Benutzung: j/n");
                        j=0;
                }
            }
        }
        System.out.println("Das Programm wird beendet.");
        System.exit(0);
    }
    

    public static KeyPair generateKeyPair() throws Exception{
		int keysize=2000;
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keysize);
		KeyPair rsaKeyPair = keyPairGen.generateKeyPair ();
        return rsaKeyPair;
    }

    public static String encrypt(String text, KeyPair keypair) throws Exception{
        String plainText = text;
		PublicKey publicKey = keypair.getPublic ();
		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.ENCRYPT_MODE , publicKey);
		byte[] cipherText = rsa.doFinal(plainText.getBytes ());
		Base64.Encoder encoder = Base64.getEncoder ();
		String message = encoder.encodeToString(cipherText);
        return message;
    }

    public static String decrypt(String cipherText, KeyPair keypair) throws Exception{
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] a = decoder.decode(cipherText);
        Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.DECRYPT_MODE, keypair.getPrivate());
		byte[] decipheredText = rsa.doFinal(a);
		String b = new String(decipheredText);
        return b;
    }
}
