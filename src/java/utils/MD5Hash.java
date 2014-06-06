package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Goys
 */
public abstract class MD5Hash {

    /**
     * Crypte le <String> entré en paramètre en MD5 et le retourne sous forme de
     * <String>.
     * Cette fonction a été récupérée sur StackOverflow, mais le lien a été
     * perdu.
     *
     * @param str La chaîne de caractères à crypter
     * @return La chaîne de caractères cryptée
     */
    public static String encrypt(String str) {
        String generatedPassword = null;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(str.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }
}
