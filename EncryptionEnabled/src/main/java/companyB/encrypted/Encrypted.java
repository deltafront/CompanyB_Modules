package companyB.encrypted;

import org.apache.commons.codec.digest.DigestUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to designate a String that is to be encrypted.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Encrypted
{
    /**
     *  Algorithm to be used. Allowable values are:
     *   - MD2
     *   - MD5 (default)
     *   - SHA1
     *   - SHA256
     *   - SHA348
     *   - SHA512
     */
    public algorithms algorithm() default algorithms.MD5;
    public enum algorithms
    {

        MD2
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.md2Hex(in);
                    }
                },
        MD5
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.md5Hex(in);
                    }
                },
        SHA1
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.sha1Hex(in);
                    }
                },
        SHA256
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.sha256Hex(in);
                    }
                },
        SHA348
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.sha384Hex(in);
                    }
                },
        SHA512
                {
                    @Override
                    public String encrypt(String in)
                    {
                        return DigestUtils.sha512Hex(in);
                    }
                };

        /**
         * Encrypts string via command pattern.
         * @param in String to be encrypted.
         * @return Encrypted String.
         */
        public abstract String encrypt(String in);
    }
}