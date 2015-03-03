package toolsPackage;

import java.security.MessageDigest;

/**
 * Convert Strings to Hashes
 * 
 * @author John O Riordan
 */
public class Hash {
    /**
     * Return hash for the given string and hash type
     * 
     * @param message The string to be hashed
     * @param hashType The type of hash to be performed to the string
     * @return The hashed string
     */
    public static String getHash(String message, String hashType) 
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashType);
            // hash the message and store the hash in an array
            byte[] messageHash = messageDigest.digest(message.getBytes());
            
            StringBuilder builder = new StringBuilder();
            // Start of solution from fitorec
            // http://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash
            for (int i = 0; i < messageHash.length; ++i) {
                // convert the array containing the hash to its hex form
                builder.append(Integer.toHexString((messageHash[i] & 0xFF) | 0x100).substring(1, 3));
            }
            // End of solution from fitorec
            // http://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash
            return builder.toString();
        } catch (Exception exception) {
            // error action
        }
        return null;
    }
    
    /**
     * Returns the MD5 hash of the string
     * 
     * @param message
     * @return The hash of the string
     */
    public static String md5(String message) 
    {
        return Hash.getHash(message, "MD5");
    }
    
    /**
     * Returns the SHA1 hash of the string
     * 
     * @param message
     * @return The hash of the string
     */
    public static String sha1(String message) 
    {
        return Hash.getHash(message, "SHA1");
    }

}
