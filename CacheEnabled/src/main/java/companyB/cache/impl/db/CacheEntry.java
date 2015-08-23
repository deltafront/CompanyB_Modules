package companyB.cache.impl.db;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "cache_entry")
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

    public CacheEntry()
    {}
    public CacheEntry(String key, String value)
    {
        Validate.notBlank(key,"Not-null key must be present.");
        this.key = key;
        this.value = value;
    }
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
