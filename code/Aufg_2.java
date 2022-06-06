package code;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;


public class Aufg_2 {
    public static void main(String[] args) {
        int a = 0;
        Scanner mode = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        System.out.println("Wählen sie einen Operationsmodus: ");
        System.out.println("    1. Mit AES-Verschlüsseln");
        System.out.println("    2. Mit AES-Verschlüsseln, aber mit einem zufällig generierten Schlüssel");
        System.out.println("    3. Mit AES-Entschlüsseln");
        System.out.println("Geben Sie ihre Auswahl als Nummer ein: ");
        try{
            a = mode.nextInt();
        }
        catch (Exception e){
            System.out.println("Ungültige Eingabe! Geben sie eine Zahl, von den oben gezeigten ein!");
            return;
        }
        switch (a) {
            case 1 -> {
                System.out.println("Bitte geben Sie einen Klartext ein:");
                String message = in.nextLine();
                System.out.println("Bitte geben Sie Ihren Schlüssel ein:");
                String key = in.nextLine();
                System.out.println("Nachricht: " + message);
                try {
                    System.out.println("Verschlüsselte Nachricht: " + AESen(message, key));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("Bitte geben Sie einen Text ein:");
                String tovalidate = in.nextLine();
                System.out.println("Geben Sie nun die Signatur ein:");
                String sigin = in.nextLine();
                String sigcalc = "";
                try {
                    //sigcalc = sha256(tovalidate);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                if (sigin.equals(sigcalc)) {
                    System.out.println("Die Signatur zu der Nachricht: " + tovalidate);
                    System.out.println("ist korrekt!");
                } else {
                    System.out.println("Ihre übergebene Signatur ist ungültig.");
                    System.out.println("Ihre Signatur zu der Nachricht: " + tovalidate);
                    System.out.println("lautete: " + sigin);
                    System.out.println("Die korrekte Signatur lautet: " + sigcalc);
                }
            }
            default -> System.out.println("Ungültige Eingabe!");
        }
    }

    public static String AESen(String message, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        SecureRandom prng = new SecureRandom();
        byte[] iv = new byte[16];
        prng.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE , keySpec , ivSpec);
        byte[] cipherText = cipher.doFinal(message.getBytes ());
        ByteArrayOutputStream os = new ByteArrayOutputStream ();
        os.write(iv);
        os.write(cipherText);
        Base64.Encoder encoder = Base64.getEncoder ();
        return encoder.encodeToString(os.toByteArray ());
    }
}
