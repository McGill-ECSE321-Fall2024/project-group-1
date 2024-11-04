package ca.mcgill.ecse321group1.gamestore.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PersonServiceTestHelper {
    static String hash_password(String password) {
        StringBuilder hashed = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((password + "__^^..rainbow table this").getBytes());

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hashed.append('0');
                hashed.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {}
        return hashed.toString();
    }
}
