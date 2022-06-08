package code;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
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
                System.out.println("Bitte geben Sie einen Klartext ein:");
                String message = in.nextLine();
                System.out.println("Dieser Text wird nun mit einem zufällig generierten Schlüssel verschlüsselt.");
                SecureRandom random = new SecureRandom();
                String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                StringBuilder sb = new StringBuilder(16);
                for(int i = 0; i < 16; i++)
                    sb.append(AB.charAt(random.nextInt(AB.length())));
                String key = sb.toString();
                try {
                    System.out.println("Verschlüsselte Nachricht: " + AESen(message, key));
                    System.out.println("Schlüssel: " + key);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                System.out.println("Bitte geben Sie einen verschl. Text ein:");
                String message = in.nextLine();
                System.out.println("Bitte geben Sie Ihren Schlüssel ein:");
                String key = in.nextLine();
                System.out.println("Nachricht: " + message);
                try {
                    System.out.println("Entschlüsselte Nachricht: " + AESde(message, key));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
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
    public static String AESde(String message, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        SecureRandom prng = new SecureRandom();
        byte[] iv = new byte[16];
        prng.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        System.out.println(message.length());
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] a = decoder.decode(message);
        System.out.println(a.length);
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decipheredText = aes.doFinal(a);
        String b = new String(decipheredText);
        b = b.substring(15);
        return b;
    }
}
