package companyB.encrypted;

import org.apache.commons.codec.digest.DigestUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * Annotation used to designate a String that is to be encrypted.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
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

        MD2(DigestUtils::md2Hex),
        MD5(DigestUtils::md5Hex),
        SHA1(DigestUtils::sha1Hex),
        SHA256(DigestUtils::sha256Hex),
        SHA348(DigestUtils::sha384Hex),
        SHA512(DigestUtils::sha512Hex);
        public Function<String,String>encrypt;
        private algorithms(Function<String,String> encrypt)
        {
            this.encrypt = encrypt;
        }
    }
}