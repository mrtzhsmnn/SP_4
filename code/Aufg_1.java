package code;
import java.security.*;
import javax.crypto.*;
import java.lang.*;
import java.util.*;


public class Aufg_1 {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();
        System.out.println("Message: " + message);
        System.out.println("Digest.: " + sha256(message));
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