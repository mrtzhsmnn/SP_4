package code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Scanner;

public class Aufg_4 {

    public static void main(String[] args) throws Exception {
        int i = 0;
        while (i == 0) {
            Scanner input = new Scanner(System.in);
            System.out.println
                ("Geben Sie einen zu signierenden Text ein: ");
            String text = input.nextLine();
            System.out.println
                ("Neues Schlüsselpaar wird erzeugt ...");
            KeyPair keypair = generateKeyPair();
            System.out.println
                ("Schlüsselpaar erzeugt!");
            System.out.println("---------------------------");
            System.out.println("Public Key:");
            System.out.println(keypair.getPublic());
            System.out.println("Private Key:");
            System.out.println(keypair.getPrivate());
            System.out.println("---------------------------");
            System.out.println("Text wird signiert ...");
            String signature = sign(text, keypair);
            System.out.println("Text signiert!");
            System.out.println("Signatur: ");
            System.out.println(signature);

            int j = 0;
            while (j == 0) {
                System.out.println("---------------------------");
                System.out.println
                        ("Signatur verifizieren? (j/n)");
                String answerA = input.nextLine();
                switch (answerA) {
                    case "j":
                        System.out.println
                                ("Der Text wird verifiziert ...");
                        boolean verified = verify(text,
                                signature, keypair.getPublic());
                        if (verified) {
                            System.out.println("Verifiziert!");
                        } else {
                            System.out.println("Ungültig!");
                        }
                        j = 1;
                        break;
                    case "n":
                        j = 1;
                        break;
                    default:
                        System.out.println
                            ("Falsche Eingabe! Benutzung: j/n");
                        break;
                }
            }

            int k = 0;
            while (k == 0) {
                System.out.println("---------------------------");
                System.out.println
                    ("Weiteren Text signieren? (j/n)");
                String answerB = input.nextLine();
                switch (answerB) {
                    case "j":
                        k = 1;
                        break;
                    case "n":
                        k = 1;
                        i = 1;
                        break;
                    default:
                        System.out.println
                            ("Falsche Eingabe! Benutzung: j/n");
                        break;
                }
            }
        }

        System.out.println("Das Programm wird beendet.");
        System.exit(0);
    }

    public static KeyPair generateKeyPair() throws Exception {
        int keysize = 2000;
        KeyPairGenerator keyPairGen =
                KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keysize);
        return keyPairGen.generateKeyPair();
    }

    public static String sign
    (String text, KeyPair keypair) throws Exception {
        java.security.PrivateKey privKey = keypair.getPrivate();
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privKey);
        rsa.update(text.getBytes());
        byte[] signature = rsa.sign();
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(signature);
    }

    public static boolean verify
    (String text, String sig, PublicKey pubKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initVerify(pubKey);
        rsa.update(text.getBytes());
        byte[] sBytes = Base64.getDecoder().decode(sig);
        return rsa.verify(sBytes);
    }
}