package com.maojie.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: Utility class for generating and verifying MD5 hash values for strings.
 */
public class Md5Util {

    /**
     * Default hex digits for converting bytes to hexadecimal representation, commonly used to verify file integrity.
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(Md5Util.class.getName() + " initialization failed, MD5 is not supported.");
            nsaex.printStackTrace();
        }
    }

    /**
     * @Description: Generates an MD5 hash value for a given string.
     * @Param: s - Input string to hash
     * @return: String - MD5 hash of the input string
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * @Description: Checks if the MD5 hash of a given string matches a known MD5 hash.
     * @Param: password - The input string to hash and compare; md5PwdStr - Known MD5 hash to compare against
     * @return: boolean - True if the hashes match, false otherwise
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * @Description: Generates an MD5 hash for a byte array.
     * @Param: bytes - Input byte array to hash
     * @return: String - MD5 hash of the input byte array
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * @Description: Converts a byte array to a hexadecimal string.
     * @Param: bytes - Input byte array
     * @return: String - Hexadecimal representation of the byte array
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    /**
     * @Description: Converts a section of a byte array to a hexadecimal string.
     * @Param: bytes - Input byte array; m - Starting index; n - Number of bytes to process
     * @return: String - Hexadecimal representation of the specified section of the byte array
     */
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * @Description: Appends the hexadecimal representation of a byte to a StringBuffer.
     * @Param: bt - Byte to convert; stringbuffer - StringBuffer to append the hex representation to
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4]; // Convert high 4 bits
        char c1 = hexDigits[bt & 0xf];         // Convert low 4 bits
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
