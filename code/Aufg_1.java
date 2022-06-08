package code;
import java.security.*;
import java.lang.*;
import java.util.*;


public class Aufg_1 {
    public static void main(String[] args) {
        int a;
        Scanner mode = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        System.out.println("Wählen sie einen Operationsmodus: ");
        System.out.println("    1. SHA-Signatur erstellen");
        System.out.println("    2. SHA-Signatur validieren");
        System.out.println("Geben Sie ihre Auswahl als Nummer ein: ");
        try {
            a = mode.nextInt();
        } catch (Exception e) {
            System.out.println("Ungültige Eingabe! Geben sie eine Zahl, von den oben gezeigten ein!");
            return;
        }
        switch (a) {
            case 1 -> {
                System.out.println("Bitte geben Sie einen Text ein:");
                String message = in.nextLine();
                System.out.println("Message: " + message);
                try {
                    System.out.println("Digest.: " + sha256(message));
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
                    sigcalc = sha256(tovalidate);
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

    public static String sha256(String message) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(message.getBytes ());
        return toHexString(digest, 2);
    }
    protected static String toHexString(byte[] data, int offset) {
        if (offset <0) {
            offset =0;
        }
        StringBuilder sb = new StringBuilder ();
        for (int i=0; i<data.length; i++) {
            sb.append(String.format("%02X", data[i]));
            if ((offset >0) && (i+1<data.length) && ((i+1) % offset == 0)) {
                sb.append(" ");
            }
        }
        return sb.toString ();
    }
}