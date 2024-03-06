package com.exercise_1.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class OrganizationIdCodec {

    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public static String encode(Long organizationId) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(organizationId.toString().getBytes());
            return Base64.getUrlEncoder().encodeToString(encVal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Long decode(String encodedOrganizationId) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getUrlDecoder().decode(encodedOrganizationId);
            byte[] decValue = c.doFinal(decodedValue);
            return Long.parseLong(new String(decValue));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGO);
    }
}