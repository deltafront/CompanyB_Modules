package companyB.common.guid;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * Class for creating simple Globally unique identifiers.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class GUID implements Serializable
{
    private static final long serialVersionUID = 42L;
    private Long guid;
    private final Map<Integer, Function<String,String>> functionMap;

    /**
     * @param guid Value of Guid.
     */
    public GUID(Long guid)
    {
        this();
        this.guid = guid;
    }

    /**
     * No-args constructor needed for Serialization.
     */
    public GUID()
    {
        this.guid = UUID.randomUUID().getMostSignificantBits();
        functionMap = new HashMap<>();
        functionMap.put(0, DigestUtils::md2Hex);
        functionMap.put(1, DigestUtils::md5Hex);
        functionMap.put(2, DigestUtils::sha1Hex);
        functionMap.put(3, DigestUtils::sha256Hex);
        functionMap.put(4, DigestUtils::sha384Hex);
    }

    /**
     * @return Read-only copy value of GUID.
     */
    public Long getGuid()
    {
       return Long.valueOf(String.valueOf(guid));
    }

    /**
     * @return String representation of this GUID.
     */
    public String getStringGuid()
    {
        return String.valueOf(guid);
    }

    /**
     * @return GUID that has been hashed using MD2, MD5, SHA1, SHA256, SHA384 or SHA512.
     */
    public String getHashedGuid()
    {
        final Integer random = new Random(System.currentTimeMillis()).nextInt(functionMap.size()+1);
        return functionMap.containsKey(random) ?
               functionMap.get(random).apply(getStringGuid()) :
                DigestUtils.sha512Hex(getStringGuid());
    }

}
