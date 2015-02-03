package toolsPackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Convert Strings to Hashes
 * 
 * @author fitorec
 * @url https://github.com/fitorec/java-hashes
 */
public class Hash {
    /**
     * Return hash for the given string and hash type
     * 
     * @param txt The string to be hashed
     * @param hashType The type of hash to be performed to the string
     */
    public static String getHash(String txt, String hashType) 
    {
        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (NoSuchAlgorithmException e) {
            //error action
        }
        return null;
    }
    
    /**
     * Returns the MD2 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String md2(String txt) 
    {
        return Hash.getHash(txt, "MD2");
    }
    
    /**
     * Returns the MD5 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String md5(String txt) 
    {
        return Hash.getHash(txt, "MD5");
    }
    
    /**
     * Returns the SHA1 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String sha1(String txt) 
    {
        return Hash.getHash(txt, "SHA1");
    }
    
    /**
     * Returns the SHA-224 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String sha224(String txt) 
    {
        return Hash.getHash(txt, "SHA-224");
    }
    
    /**
     * Returns the SHA-256 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String sha256(String txt) 
    {
        return Hash.getHash(txt, "SHA-256");
    }
    
    /**
     * Returns the SHA-384 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String sha384(String txt) 
    {
        return Hash.getHash(txt, "SHA-384");
    }
    
    /**
     * Returns the SHA-512 hash of the string
     * 
     * @param txt
     * @return The hash of the string
     */
    public static String sha512(String txt) 
    {
        return Hash.getHash(txt, "SHA-512");
    }
}
