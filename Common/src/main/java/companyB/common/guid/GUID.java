package companyB.common.guid;

import java.io.Serializable;
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

}
