package companyB.cache;

import java.io.Serializable;

/**
 * Contract that defines basic Caching operations. Classes that implement this shall be responsible for their own ejection strategies, initial
 * load, etc.
 * @param <Key> Type of key. The class used for this <strong>must</strong> implement Serializable.
 * @param <Value> Type of value. The class used for this <strong>must</strong> implement Serializable.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public interface ExternalCache<Key extends Serializable,Value extends Serializable>
{
    /**
     * Associates key with value in the cache.
     * @param key Key to associate value with.
     * @param value Value to be associated with key.
     */
    void insert(Key key, Value value);
    /**
     * Associates key with value in the cache.
     * @param key Key associated with value.
     * @return Value associated with key, or null if not present.
     */
    Value retrieve(Key key);

    /**
     * Clears all of the entries from the underlying cache.
     */
    void clear();

    /**
     * Removes value associated with key.
     * @param key Key associated with value that is to be removed.
     * @return Value associated with key, or null of not present.
     */
    Value remove(Key key);

    /**
     * @return Name of this particular cache.
     */
    String getName();

}
