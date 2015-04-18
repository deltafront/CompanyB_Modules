package companyB.eventlogger;

import org.apache.commons.lang3.Validate;

/**
 * Represents a single event code.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class EventCode
{
    private final String name;
    private final Integer code;

    /**
     * Default constructor.
     * @param name Name of this event.
     * @param code Code assigned to this event
     * @since 1.0
     */
    public EventCode(String name, Integer code)
    {
        Validate.notBlank(name,"Event Name is required.");
        Validate.notNull(code,"Event Code is required.");
        this.name = name;
        this.code = code;
    }

    /**
     * @return Name of this event.
     * @since 1.0
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Code for this event.
     * @since 1.0
     */
    public Integer getCode()
    {
        return code;
    }

    @Override
    public String toString()
    {
        return String.format("Event=%s (%d)",name,code);
    }
}
