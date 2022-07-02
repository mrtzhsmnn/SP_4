package code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class Aufg_3 {
    public static void main(String[] args) throws Exception{ 
        System.out.println("Es wird ein Schlüsselpaar "
		+"generiert..."); // Hinweis ausgeben
        KeyPair keypair = generateKeyPair(); // Schlüsselpaar generieren
        System.out.println("Schlüsselpaar generiert!"); // Hinweis ausgeben
        int i = 0; // Zähler initialisieren
        while(i==0){ // solange i==0 ist
            System.out.println("Geben Sie einen zu"  
			+ " verschlüsselnden Text ein:"); //Hinweis ausgeben
		    Scanner Text = new Scanner(System.in); // Scanner initialisieren
		    String text=Text.nextLine(); // Text einlesen
		    System.out.println("Der Text wird verschlüsselt..."); // Hinweis ausgeben

            String CipherText=encrypt(text,keypair); //Methode für das Verschlüsseln aufrufen
            System.out.println("Verschlüssselte Nachricht:"); // Hinweis ausgeben	
		    System.out.println(CipherText); // Verschlüsselte Nachricht ausgeben

		    Scanner decission = new Scanner(System.in); //erneut scanner initialisieren

            int k=0; //Zähler für erste Schleife initialisieren
            while(k==0){  //solange k==0 ist
                System.out.println("Soll der veschlüsselte Text" 
				+ " auch wieder entschlüsselt werden? (j/n)"); //Hinweis ausgeben
                String answer=decission.nextLine();    //Antwort einlesen
		        switch (answer) { 
		    	    case "j": //wenn Antwort "j" ist
                        System.out.println("Der Text wird nun"
						+ " wieder entschlüsselt..."); //Hinweis ausgeben
			    	    String decipheredText=
						decrypt(CipherText,keypair); //Methode für das Entschlüsseln aufrufen
                        System.out.println("Entschlüssselte"
						+ " Nachricht:"); //Hinweis ausgeben
                        System.out.println(decipheredText); //Entschlüsselte Nachricht ausgeben
                        k=1; //Zähler auf 1 setzen
                        break; //Schleife beenden
			        case "n": //wenn Antwort "n" ist
                        k=1; //Zähler auf 1 setzen
                        break; //Schleife beenden
			        default: 
				        System.out.println("Falsche Eingabe!" 
						+ " Benutzung: j/n"); //Hinweis ausgeben
		        }
            }
            int j = 0; //Zähler für zweite Schleife initialisieren
            while(j==0){ //solange j==0 ist
                System.out.println("Wollen sie noch einen Text"
				+ " verschlüsseln? (j/n)"); //Hinweis ausgeben
                String answer=decission.nextLine(); //Antwort einlesen

                switch (answer) { 
                    case "j": //wenn Antwort "j" ist
                        i=0; //Zähler i auf 0 setzen um wieder i-Schleife zu durchlaufen
                        j=1; //Zähler j auf 1 setzen um j-Schleife zu beenden
                        break; //Schleife beenden
                    case "n": //wenn Antwort "n" ist
                        i=1; //Zähler i auf 1 setzen um i-Schleife zu beenden
                        j=1; //Zähler j auf 1 setzen um j-Schleife zu beenden
                        break; //Schleife beenden
                    default: 
                        i=1; //Zähler i auf 1 setzen um i-Schleife zu beenden
                        System.out.println("Falsche Eingabe!"
						+ " Benutzung: j/n"); //Hinweis ausgeben
                        j=0; //Zähler j auf 0 setzen um j-Schleife zu durchlaufen
                }
            }
        }
        System.out.println("Das Programm wird beendet."); //Hinweis ausgeben
        System.exit(0); //Programm beenden
    }
    
    public static KeyPair generateKeyPair() throws Exception{ 
		int keysize=2000; //Schlüsselgröße 2000
		KeyPairGenerator keyPairGen = 
		KeyPairGenerator.getInstance("RSA"); //KeyPairGenerator initialisieren mit RSA-Algorithmus
		keyPairGen.initialize(keysize); //Schlüsselgröße setzen
		KeyPair rsaKeyPair = keyPairGen.generateKeyPair (); //KeyPair erzeugen
        return rsaKeyPair; //KeyPair zurückgeben
    }

    public static String encrypt(String text, KeyPair keypair) 
	throws Exception{
        String plainText = text; //Text setzen
		PublicKey publicKey = keypair.getPublic (); //PublicKey holen und in publicKey speichern
		Cipher rsa = Cipher.getInstance("RSA"); //rsa initialisieren mit RSA-Algorithmus
		rsa.init(Cipher.ENCRYPT_MODE , publicKey); //rsa initialisieren mit publicKey und im Verschlüsseln-Modus
		byte[] cipherText = rsa.doFinal(plainText.getBytes ()); //Verschlüsselung durchführen
		Base64.Encoder encoder = Base64.getEncoder (); //Base64-Encoder initialisieren
		String message = encoder.encodeToString(cipherText); //Verschlüsselte Nachricht in String umwandeln und speichern
        return message; //Verschlüsselte Nachricht zurückgeben
    }

    public static String decrypt 
	(String cipherText, KeyPair keypair) throws Exception{ 
		Base64.Decoder decoder = Base64.getDecoder(); //Base64-Decoder initialisieren
		byte[] a = decoder.decode(cipherText); //Verschlüsselte Nachricht in Byte-Array umwandeln und speichern
        Cipher rsa = Cipher.getInstance("RSA"); //rsa initialisieren mit RSA-Algorithmus
		rsa.init(Cipher.DECRYPT_MODE, keypair.getPrivate()); //rsa initialisieren mit privateKey und im Entschlüsseln-Modus
		byte[] decipheredText = rsa.doFinal(a); //Entschlüsselung durchführen
		String b = new String(decipheredText); //Entschlüsselte Nachricht in String umwandeln und in neuem String Objekt speichern
        return b; //Entschlüsselte Nachricht zurückgeben
    }
}
