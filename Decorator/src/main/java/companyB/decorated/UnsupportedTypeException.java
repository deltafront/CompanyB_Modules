package companyB.decorated;

/**
 * Exception thrown when a decorated field is of a type that is not supported.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class UnsupportedTypeException extends Throwable
{
    /**
     * Default constructor.
     * @param c Class that is not supported.
     * @since 1.0
     */
    public UnsupportedTypeException(Class c)
    {
        super(String.format("'%s' is not a supported type!\nSupported types are %s.",c.getCanonicalName(),SupportedClasses.getSupportedClassesList()));
    }
}
