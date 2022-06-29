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
        // int a als Speicher für modus
        int a;
        // neue Scanner Objekte für Eingabe
        Scanner mode = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        // Ausgaben für den Benutzer
        System.out.println("Wählen sie einen Operationsmodus: ");
        System.out.println("    1. Mit AES-Verschlüsseln");
        System.out.println("    2. Mit AES-Verschlüsseln, aber " +
                "mit einem zufällig generierten Schlüssel");
        System.out.println("    3. Mit AES-Entschlüsseln");
        System.out.println("Geben Sie ihre Auswahl als Nummer " +
                "ein: ");
        // try-catch-Block für die Eingabe
        try {
            // eingabe des modus in die Variable a schreiben
            a = mode.nextInt();
        } catch (Exception e) {
            // Fehlermeldung ausgeben
            System.out.println("Ungültige Eingabe! Geben sie" +
                    " eine Zahl, von den oben gezeigten ein!");
            return;
        }
        // switch-case-Block für die verschiedenen Modi
        switch (a) {
            // Modus 1: AES-Verschlüsseln
            case 1 -> {
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie einen " +
                        "Klartext ein:");
                // Klartext einlesen
                String message = in.nextLine();
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie Ihren " +
                        "Schlüssel ein:");
                // Schlüssel einlesen
                String key = in.nextLine();
                // Ausgaben für den Benutzer
                System.out.println("Nachricht: " + message);
                // try-catch-Block für die AES-Verschlüsselung
                try {
                    // AES-Verschlüsselung
                    System.out.println("Verschlüsselte " +
                            "Nachricht: " + AESen(message, key));
                } catch (Exception e) {
                    // Fehlermeldung ausgeben
                    System.out.println("Error: " + e.getMessage());
                }
            }
            // Modus 2: AES-Verschlüsseln, aber mit einem zufälligen Schlüssel
            case 2 -> {
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie einen " +
                        "Klartext ein:");
                // Klartext einlesen
                String message = in.nextLine();
                // Ausgaben für den Benutzer
                System.out.println("Ihre Eingabe wird nun m. " +
                        "einem zufälligen Schl. verschl..");
                // SecureRandom-Objekt erstellen
                SecureRandom random = new SecureRandom();
                // String für die zulässigen buchstaben erstellen
                String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWX" +
                        "YZabcdefghijklmnopqrstuvwxyz";
                // String für den zufälligen Schlüssel erstellen
                StringBuilder sb = new StringBuilder(16);
                // Schlüssel generieren
                for (int i = 0; i < 16; i++)
                    sb.append(AB.charAt(
                            random.nextInt(AB.length())));
                String key = sb.toString();
                // try-catch-Block für die AES-Verschlüsselung
                try {
                    // AES-Verschlüsselung
                    System.out.println("Verschlüsselte " +
                            "Nachricht: " + AESen(message, key));
                    // Schlüssel ausgeben
                    System.out.println("Schlüssel: " + key);
                } catch (Exception e) {
                    // Fehlermeldung ausgeben
                    System.out.println("Error: " + e.getMessage());
                }
            }
            // Modus 3: AES-Entschlüsseln
            case 3 -> {
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie einen " +
                        "verschl. Text ein:");
                // verschl. Text einlesen
                String message = in.nextLine();
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie Ihren" +
                        " Schlüssel ein:");
                // Schlüssel einlesen
                String key = in.nextLine();
                // Nachricht ausgeben
                System.out.println("Nachricht: " + message);
                // try-catch-Block für die AES-Entschlüsselung
                try {
                    // AES-Entschlüsselung
                    System.out.println("Entschlüsselte " +
                            "Nachricht: " + AESde(message, key));
                } catch (Exception e) {
                    // Fehlermeldung ausgeben
                    System.out.println("Error: " + e.getMessage());
                }
            }
            // Fehlermeldung ausgeben, wenn der Modus nicht existiert
            default -> System.out.println("Ungültige Eingabe!");
        }
    }

    public static String AESen(String message, String key)
            throws Exception {
        // schlüsselobjekt erstellen
        SecretKeySpec keySpec = new SecretKeySpec
                (key.getBytes(), "AES");
        // neues SecureRandom-Objekt erstellen
        SecureRandom prng = new SecureRandom();
        // byte-Array für den IV erstellen
        byte[] iv = new byte[16];
        // IV generieren
        prng.nextBytes(iv);
        // iv-Objekt erstellen
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        // cipher-Objekt erstellen
        Cipher cipher = Cipher.getInstance
                ("AES/CBC/PKCS5Padding");
        // cipher-Objekt initialisieren
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        // byte-Array für den verschlüsselten Text erstellen
        byte[] cipherText = cipher.doFinal(message.getBytes());
        // byte-Array für den output-String erstellen
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // IV in den bytestream schreiben
        os.write(iv);
        // verschlüsselten Text in den bytestream schreiben
        os.write(cipherText);
        // output-String encoden und zurückgeben
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(os.toByteArray());
    }

    public static String AESde(String message, String key)
            throws Exception {
        // schlüsselobjekt erstellen
        SecretKeySpec keySpec = new SecretKeySpec
                (key.getBytes(), "AES");
        // byte-Array für IV erstellen
        byte[] iv = new byte[16];
        // neues Decoder-Objekt erstellen
        Base64.Decoder decoder = Base64.getDecoder();
        // byte-Array für den verschlüsselten Text erstellen
        byte[] a = decoder.decode(message);
        // IV aus byte array kopieren ...
        System.arraycopy(a, 0, iv, 0, 16);
        // und in das neue iv-Objekt schreiben
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        // neuen byte array für den bereinigten verschlüsselten Text erstellen
        byte[] newa = new byte[a.length - 16];
        // bereinigten verschlüsselten Text aus byte array kopieren ...
        System.arraycopy(a, 16, newa, 0, a.length - 16);
        a = newa;
        // cipher-Objekt erstellen
        Cipher aes = Cipher.getInstance
                ("AES/CBC/PKCS5Padding");
        // cipher-Objekt initialisieren
        aes.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        // byte-Array für den entschlüsselten Text erstellen
        byte[] decipheredText = aes.doFinal(a);
        // output-String encoden und zurückgeben
        return new String(decipheredText);
    }
}
