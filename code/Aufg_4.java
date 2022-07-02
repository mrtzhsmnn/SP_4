// importiere Paket
package code;
// Importiere Klassen
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Scanner;

public class Aufg_4 {

    public static void main(String[] args) throws Exception {
        // initialisiere Variable i mit dem Wert 0
        int i = 0;
        // SOlange i = 0
        while (i == 0) {
            // Warte auf Eingabe
            Scanner input = new Scanner(System.in);
            // Ausgabe für Bedienbarkeit
            System.out.println
                ("Geben Sie einen zu signierenden Text ein: ");
            // Speichere Benutzereingabe in String text
            String text = input.nextLine();
            System.out.println
                ("Neues Schlüsselpaar wird erzeugt ...");
            // Generiere neues Schlüsselpaar mit Methode generateKeyPair()
            KeyPair keypair = generateKeyPair();
            // Ausgabe der Keys zur Bestätigung
            System.out.println
                ("Schlüsselpaar erzeugt!");
            System.out.println("---------------------------");
            System.out.println("Public Key:");
            System.out.println(keypair.getPublic());
            System.out.println("Private Key:");
            System.out.println(keypair.getPrivate());
            System.out.println("---------------------------");
            System.out.println("Text wird signiert ...");
            // Signiere String in "text" mit Methode sign
            String signature = sign(text, keypair);
            // Ausgabe der Signatur zur Bestätigung
            System.out.println("Text signiert!");
            System.out.println("Signatur: ");
            System.out.println(signature);

            // initialisiere Variable j mit dem Wert 0
            int j = 0;
            // Solange j = 0
            while (j == 0) {
                // Ausgabe zur Bedienbarkeit
                System.out.println("---------------------------");
                System.out.println
                        ("Signatur verifizieren? (j/n)");
                // Speichere Eingabe in String answerA
                String answerA = input.nextLine();
                switch (answerA) {
                    // Wenn Inhalt von "answerA" = j
                    case "j":
                        System.out.println
                                ("Der Text wird verifiziert ...");
                        // verifiziere Signatur mit Methode verifiy
                        boolean verified = verify(text,
                                signature, keypair.getPublic());
                        // Sofern Signatur gültig
                        if (verified) {
                            // Bestätigung
                            System.out.println("Verifiziert!");
                        } else {
                            // Ungültig
                            System.out.println("Ungültig!");
                        }
                        // Setze j auf den Wert 1, um aus der Schleife zu springen
                        j = 1;
                        break;
                    // Wenn Inhalt von "answerA" = n
                    case "n":
                        // Setze j auf den Wert 1, um aus der Schleife zu springen
                        j = 1;
                        break;
                    // Bei anderen Eingaben
                    default:
                        // Fehler ausgeben und Schleife neu starten
                        System.out.println
                            ("Falsche Eingabe! Benutzung: j/n");
                        break;
                }
            }
            // initialisiere Variable k mit dem Wert 0
            int k = 0;
            // Solange k = 0
            while (k == 0) {
                // Ausgabe für Bedienbarkeit
                System.out.println("---------------------------");
                System.out.println
                    ("Weiteren Text signieren? (j/n)");
                // Speichere Eingabe in String answerB
                String answerB = input.nextLine();
                switch (answerB) {
                    // Wenn Inhalt von "answerB" = j
                    case "j":
                        // k auf den Wert 1 setzen, um aus Schleife zu springen und zurück zu Schleife mit i
                        k = 1;
                        break;
                    case "n":
                        // k und i auf den Wert 1 setzen, um aus beiden Schleifen zu springen
                        k = 1;
                        i = 1;
                        break;
                    // Bei anderen Eingaben
                    default:
                        // Fehlerausgabe
                        System.out.println
                            ("Falsche Eingabe! Benutzung: j/n");
                        break;
                }
            }
        }
        // Nach Ende der Schleife, Programm beenden
        System.out.println("Das Programm wird beendet.");
        System.exit(0);
    }
    // Methode generateKeyPair
    public static KeyPair generateKeyPair() throws Exception {
        // initialisiere Variable keysize mit Wert 2000
        int keysize = 2000;
        // initialisiere neues KeyPairGenerator Objekt
        KeyPairGenerator keyPairGen =
                // Wähle RSA Verfahren mit getInstance Methode
                KeyPairGenerator.getInstance("RSA");
        // initialisiere Schlüssel mit Größe von keysize (2000)
        keyPairGen.initialize(keysize);
        // Gib mit generateKeyPair erzeugtes Schlüsselobjekt zurück
        return keyPairGen.generateKeyPair();
    }
    // Methode sign
    public static String sign
    (String text, KeyPair keypair) throws Exception {
        // Lese privaten Key aus in keypair gespeichertem Schlüsselpaar aus und speichere das Ergebnis in privKey
        java.security.PrivateKey privKey = keypair.getPrivate();
        // Wähle das gewünschte Signaturverfahren und speichere es in Signature Objekt rsa
        Signature rsa = Signature.getInstance("SHA256withRSA");
        // initialisiere rsa Objekt mit privatem Schlüssel über Methode initSign
        rsa.initSign(privKey);
        // Verarbeite zu signierenden Text mit Methode getBytes
        rsa.update(text.getBytes());
        // Speichere Signatur in byte-Array signature
        byte[] signature = rsa.sign();
        // Initialisiere Base 64 encoder
        Base64.Encoder encoder = Base64.getEncoder();
        // Kodiere Signatur als Base-64-String und gib das Ergebnis zurück
        return encoder.encodeToString(signature);
    }

    // Methode verify
    public static boolean verify
    (String text, String sig, PublicKey pubKey) throws Exception {
        // Wähle das gewünschte Signaturverfahren und speichere es in Signature Objekt rsa
        Signature rsa = Signature.getInstance("SHA256withRSA");
        // initialisiere rsa Objekt mit öffentlichem Schlüssel über Methode initVerify
        rsa.initVerify(pubKey);
        // Verarbeite zu verifizierenden Text mit Methode getBytes
        rsa.update(text.getBytes());
        // Initialisiere Base 64 decoder und speichere bytes in sBytes
        byte[] sBytes = Base64.getDecoder().decode(sig);
        // Gib Ergebnis der Verifikation mit in sBytes dekodierter Signatur zurück
        return rsa.verify(sBytes);
    }
}