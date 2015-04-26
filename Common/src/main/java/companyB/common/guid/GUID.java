package companyB.common.guid;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * Class for creating simple Globally unique identifiers.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 2.0
 */
public class GUID implements Serializable
{
    private static final long serialVersionUID = 42L;
    private Long guid;

    /**
     * @param guid Value of Guid.
     * @since 2.0
     */
    public GUID(Long guid)
    {
      this.guid = guid;
    }

    /**
     * No-args constructor needed for Serialization.
     * @since 2.0
     */
    public GUID()
    {
        this.guid = new Random().nextBoolean() ?
                UUID.randomUUID().getLeastSignificantBits():
                UUID.randomUUID().getMostSignificantBits();
    }

    /**
     * @return Read-only copy value of GUID.
     * @since 2.0
     */
    public Long getGuid()
    {
       return Long.valueOf(String.valueOf(guid));
    }

}
