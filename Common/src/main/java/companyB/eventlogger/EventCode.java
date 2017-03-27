package companyB.eventlogger;

import org.apache.commons.lang3.Validate;

/**
 * Represents a single event code.
 * @author Charles Burrell (deltafront@gmail.com)
 *
 */
public class EventCode
{
    private final String name;
    private final Integer code;

    /**
     * Default constructor.
     * @param name Name of this event.
     * @param code Code assigned to this event
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
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Code for this event.
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
