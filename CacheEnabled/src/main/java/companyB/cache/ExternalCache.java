package companyB.cache;

/**
 * Contract that defines basic Caching operations. Classes that implement this shall be responsible for their own ejection strategies, initial
 * load, etc.
 * @param <Key> Type of key.
 * @param <Value> Type of value.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.2.0
 */
@SuppressWarnings({"PMD.UnusedModifier"})
public interface ExternalCache<Key,Value>
{
    /**
     * Associates key with value in the cache.
     * @param key Key to associate value with.
     * @param value Value to be associated with key.
     * @since 2.2
     */
    public void insert(Key key, Value value);
    /**
     * Associates key with value in the cache.
     * @param key Key associated with value.
     * @return Value associated with key, or null if not present.
     * @since 2.2.0
     */
    public Value retrieve(Key key);

    /**
     * Clears all of the entries from the underlying cache.
     * @since 2.2.0
     */
    public void clear();

    /**
     * Removes value associated with key.
     * @param key Key associated with value that is to be removed.
     * @return Value associated with key, or null of not present.
     * @since 2.2.0
     */
    public Value remove(Key key);

    /**
     * @return Name of this particular cache.
     * @since 2.2.0
     */
    public String getName();

}
