package code;
import java.security.*;
import java.lang.*;
import java.util.*;


public class Aufg_1 {
    public static void main(String[] args) {
        // neue int Variable für die Eingabe erzeugen
        int a;
        // Scanner Objekte für die Eingabe erzeugen
        Scanner mode = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        // Benutzer Erklärungen
        System.out.println("Wählen sie einen Operationsmodus: ");
        System.out.println("    1. SHA-Signatur erstellen");
        System.out.println("    2. SHA-Signatur validieren");
        System.out.println("Geben Sie ihre Auswahl als Nummer " +
                "ein: ");
        // try-catch-Block für die Eingabe
        try {
            // eingabe des modus in die Variable a schreiben
            a = mode.nextInt();
        } catch (Exception e) {
            // Fehlermeldung ausgeben
            System.out.println("Ungültige Eingabe! Geben sie " +
                    "eine Zahl, von den oben gezeigten ein!");
            return;
        }
        // switch-case-Block für die verschiedenen Modi
        switch (a) {
            // Modus 1: SHA-Signatur erstellen
            case 1 -> {
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie einen Text" +
                        " ein:");
                // Nachricht einlesen ...
                String message = in.nextLine();
                // ... und ausgeben
                System.out.println("Message: " + message);
                // try catch Block für die SHA-Signatur
                try {
                    // SHA-Signatur erstellen
                    System.out.println("Digest.: "
                            + sha256(message));
                } catch (Exception e) {
                    // Fehlermeldung ausgeben
                    System.out.println("Error: "
                            + e.getMessage());
                }
            }
            // Modus 2: SHA-Signatur validieren
            case 2 -> {
                // Ausgaben für den Benutzer
                System.out.println("Bitte geben Sie einen Text" +
                        " ein:");
                // Die zu signierende Nachricht einlesen
                String tovalidate = in.nextLine();
                System.out.println("Geben Sie nun die Signatur" +
                        " ein:");
                // Die alte Signatur einlesen
                String sigin = in.nextLine();
                String sigcalc = "";
                // try catch Block für die SHA-Signatur
                try {
                    // SHA-Signatur erstellen, für den zu validierenden String
                    sigcalc = sha256(tovalidate);
                } catch (Exception e) {
                    // Fehlermeldung ausgeben
                    System.out.println("Error: " + e.getMessage());
                }
                // entspricht die neu generierte Signatur der alten?
                if (sigin.equals(sigcalc)) {
                    // Ausgabe für den Benutzer, dass Signatur gültig ist
                    System.out.println("Die Signatur zu der " +
                            "Nachricht: " + tovalidate);
                    System.out.println("ist korrekt!");
                } else {
                    // Ausgabe für den Benutzer, dass Signatur ungültig ist
                    System.out.println("Ihre übergebene " +
                            "Signatur ist ungültig.");
                    System.out.println("Ihre Signatur zu der " +
                            "Nachricht: " + tovalidate);
                    System.out.println("lautete: " + sigin);
                    System.out.println("Die korrekte Signatur" +
                            " lautet: " + sigcalc);
                }
            }
            // Fehlermeldung ausgeben, wenn der Modus nicht existiert
            default -> System.out.println("Ungültige Eingabe!");
        }
    }
    // Methode zur SHA-Signatur erstellen
    public static String sha256(String message) throws Exception{
        // neue MessageDigest-Objekt erzeugen
        MessageDigest md = MessageDigest.
                getInstance("SHA-256");
        // Nachricht in Bytes umwandeln
        byte[] digest = md.digest(message.getBytes ());
        // Bytes in Hex-String umwandeln
        return toHexString(digest, 2);
    }
    // Methode zur Umwandlung von Bytes in Hex-Strings
    protected static String toHexString(byte[] data, int offset) {
        // offset prüfen
        if (offset <0) {
            offset =0;
        }
        // neues Stringbuilder Objekt erzeugen
        StringBuilder sb = new StringBuilder ();
        // alle Bytes in Hex-Strings umwandeln
        for (int i=0; i<data.length; i++) {
            // Hex-String erzeugen
            sb.append(String.format("%02X", data[i]));
            if ((offset >0) && (i+1<data.length)
                    && ((i+1) % offset == 0)) {
                sb.append(" ");
            }
        }
        // Hex-String zurückgeben
        return sb.toString ();
    }
}