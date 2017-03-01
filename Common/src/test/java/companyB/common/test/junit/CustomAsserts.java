package companyB.common.test.junit;

import org.junit.Assert;

import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Custom Junit Assert methods
 * @author C.A. Burrell (deltafront@gmail.com)
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "UnusedDeclaration"})
public final class CustomAsserts
{
    CustomAsserts()
    {}

    /**
     * Fails if the supplied class does not implement the interface
     *
     * @param interface_class          Class representing the interface
     * @param interface_implementation Class representing the class that is implementing the interface
     * @since 1.0
     */
    public static void assertInstanceOf(Class interface_class, Class interface_implementation)
    {
        if (!interface_class.isInterface())
        {
            Assert.fail("Class '" + interface_class.getCanonicalName() + "' is not an interface!");
        }
        else
        {
            final Class[] interfaces = interface_implementation.getInterfaces();
            boolean found = false;
            for (final Class _interface : interfaces)
            {
                if (_interface.getCanonicalName().equals(interface_class.getCanonicalName()))
                {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                Assert.fail("" + interface_implementation.getCanonicalName() + "' does not implement '" + interface_class.getCanonicalName() + "'");
            }
        }
    }

    /**
     * Fails if the supplied class implements the interface
     *
     * @param interface_class          Class representing the interface
     * @param interface_implementation Class representing the class that is NOT implementing the interface
     *  @since 1.0
     */
    public static void assertNotInstanceOf(Class interface_class, Class interface_implementation)
    {
        if (!interface_class.isInterface())
        {
            Assert.fail("Class '" + interface_class.getCanonicalName() + "' is not an interface!");
        }
        else
        {
            final Class[] interfaces = interface_implementation.getInterfaces();
            boolean found = false;
            for (final Class _interface : interfaces)
            {
                if (_interface.getCanonicalName().equals(interface_class.getCanonicalName()))
                {
                    found = true;
                    break;
                }
            }
            if (found)
            {
                Assert.fail("" + interface_implementation.getCanonicalName() + "' implements '" + interface_class.getCanonicalName() + "'");
            }
        }
    }

    /**
     * Compares the string name against the canonical name of the class
     *
     * @param class_name Expected String name
     * @param clasz      Class for comparison
     * @since 1.0
     */
    public static void assertClassNameEquals(String class_name, Class clasz)
    {
        Assert.assertEquals(class_name, clasz.getCanonicalName());
    }

    /**
     * Determines if this string is null or a set of empty quotes
     * @param string String to be evaluated.
     * @since 1.0
     */
    public static void assertEmptyString(String string)
    {
        if (!isEmpty(string))
        {
            Assert.fail("String is neither null nor empty. Content is as follows:\n" + string);
        }
    }

    /**
     * Determines if this string is neither null nor a set of empty quotes
     * @param string String to be evaluated.
     * @since 1.0
     */
    public static void assertNotEmptyString(String string)
    {
        if (isEmpty(string))
        {
            Assert.fail("String is either null or empty.");
        }
    }
}