package companyB.common.cache.impl.db;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "cache_entry")
/**
 * Contains a single cache entry.
 * @since 2.1.0
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class CacheEntry
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    /**
     * Needed for GSON compatibility
     * @since 2.1.0
     */
    public CacheEntry()
    {}

    /**
     * @param key Cache key.
     * @param value Cache value.
     * @since 2.1.0
     */
    public CacheEntry(String key, String value)
    {
        Validate.notBlank(key,"Not-null key must be present.");
        this.key = key;
        this.value = value;
    }

    /**
     * @return id of this entry if stored in a database.
     * @since 2.1.0
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the id of this entry if this is stored in a database.
     * @param id ID for this entry.
     * @since 2.1.0
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return Key for this entry.
     * @since 2.1.0
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Sets the key for this entry.
     * @param key key for this entry.
     * @since 2.1.0
     */

    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @return Value assigned to this entry.
     * @since 2.1.0
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value for this entry.
     * @param value Cache value.
     * @since 2.1.0
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
