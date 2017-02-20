package companyB.context;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Container for configuration-based DefaultApplicationContext instantiation.
 * @author Charles Burrell
 * @since 1.0.0
 */
public class ClassArgsContainer
{
    private Class c;
    private Object[]args = new Object[0];
    private String id;
    private final Logger LOGGER = LoggerFactory.getLogger(ClassArgsContainer.class);

    /**
     * Default constructor.
     * @param fqcn Fully Qualified Class Name.
     * @param args List of arguments to be provided to the constructor for instantiation.
     * @param id ID that this instance is to be keyed to.
     * @since 1.0.0
     */
    public ClassArgsContainer(String fqcn, List<Object> args, String id) throws ClassNotFoundException
    {
       this(fqcn,args.toArray(),id);
    }
    /**
     * Default constructor.
     * @param fqcn Fully Qualified Class Name.
     * @param args List of arguments to be provided to the constructor for instantiation.
     * @param id ID that this instance is to be keyed to.
     * @since 1.0.0
     */
    public ClassArgsContainer(String fqcn, Object[] args, String id) throws ClassNotFoundException
    {
        try
        {
            this.c = Class.forName(fqcn);
            this.id = id;
            this.args = args;
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.error("Class '{}' could not be found!\n{}",fqcn,e);
            throw e;
        }
    }

    /**
     * @return Class associated with this container.
     * @since 1.0.0
     */
    public Class get_Class()
    {
        return c;
    }
    /**
     * @return Args needed to instantiate class.
     * @since 1.0.0
     */
    public Object[] getArgs()
    {
        return args;
    }
    /**
     * @return ID of this instance.
     * @since 1.0.0
     */
    public String getId()
    {
        return id;
    }
}
