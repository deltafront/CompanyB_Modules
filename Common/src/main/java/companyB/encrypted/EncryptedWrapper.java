package companyB.encrypted;

import org.apache.commons.lang3.Validate;

/**
 * This class allows you to process most Java objects into encrypted strings.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class EncryptedWrapper
{
    private final Object value;

    /**
     * Default constructor for class.
     * @param value Value whose string equivalent is to be decrypted.
     */
    public EncryptedWrapper(Object value)
    {
        Validate.notNull(value, "Value must be set.");
        this.value = value;
    }

    /**
     * @return String.valueOf(value)
     */
    public String getValue()
    {
        return String.valueOf(value);
    }
}
