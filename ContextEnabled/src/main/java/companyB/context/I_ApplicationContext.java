package companyB.context;

import java.util.Set;

/**
 * Base contract for Application Context.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 */
@SuppressWarnings({"PMD.UnusedCode","PMD.UnusedModifier"})
public interface I_ApplicationContext
{

    /**
     * Associates a key with a value. If the key is already present, the value is *not* replaced.
     * @param key Key to associate value to.
     * @param value Value to associate with key.
     * @param <Value> Type parameter.
     * @return True if value has been set.
     * @since 1.0.0
     */
    public <Value> boolean associate(String key, Value value);

    /**
     * Gets the value associated with key in the present context.
     * @param key Key to get value of.
     * @param <Value> Type parameter
     * @return Value associated with key in the present context.
     * @since 1.0.0
     */
    public <Value> Value get(String key);

    /**
     * Gets all of the keys associated with this application context.
     * @return All of the keys associated with this application context.
     * @since 1.0.0
     */
    public Set<String> getKeys();

    /**
     * Gets an instance of Class and associates it with the context.
     * @param c Class to get instance of.
     * @param args Array of arguments to provide via the constructor for the instantiation of the class.
     * @param id Name to associate this instance with in the context.
     * @param <T> Type parameter.
     * @return Instance of Class from the context
     * @since 1.0.0
     */
    public <T>T getInstance(Class<T> c, Object[] args, String id);

    /**
     * Clears the existing application context's mapping. This is a very dangerous method to use in production, but
     * has been included for testability.
     * @since 1.0.0
     */
    public void clear();
}
