package companyB.eventlogger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark an EventLogger field as being eligible for decoration.
 * @author Charles Burrell (deltafront@gmail.com)
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventLog
{
    /* Optional name of this logger. Default name is the class name. */
    String name() default "";
    Class logMessageFormatter() default DefaultLogMessageFormatter.class;
}
