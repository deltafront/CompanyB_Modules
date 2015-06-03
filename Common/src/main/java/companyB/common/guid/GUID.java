package companyB.common.guid;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * Class for creating simple Globally unique identifiers.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since  1.2.1
 */
public class GUID implements Serializable
{
    private static final long serialVersionUID = 42L;
    private Long guid;

    /**
     * @param guid Value of Guid.
     * @since 1.2.1
     */
    public GUID(Long guid)
    {
      this.guid = guid;
    }

    /**
     * No-args constructor needed for Serialization.
     * @since 1.2.1
     */
    public GUID()
    {
        this.guid = UUID.randomUUID().getMostSignificantBits();
    }

    /**
     * @return Read-only copy value of GUID.
     * @since 1.2.1
     */
    public Long getGuid()
    {
       return Long.valueOf(String.valueOf(guid));
    }

    public String getStringGuid()
    {
        return String.valueOf(guid);
    }

    public String getHashedGuid()
    {
        int upper = 6;
        int random = new Random(System.currentTimeMillis()).nextInt(upper);
        if (0 == random)return DigestUtils.md2Hex(getStringGuid());
        if (1 == random)return DigestUtils.md5Hex(getStringGuid());
        if (2 == random)return DigestUtils.sha1Hex(getStringGuid());
        if (3 == random)return DigestUtils.sha256Hex(getStringGuid());
        if (4 == random) return DigestUtils.sha384Hex(getStringGuid());
        return DigestUtils.sha512Hex( getStringGuid());
    }

}
