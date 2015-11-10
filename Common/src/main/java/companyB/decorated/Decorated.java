package companyB.decorated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates that a particular field is to be decorated at runtime.
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Decorated
{
    /**
     * @return Default value if value is not found in the properties.
     * @since 1.0.0
     */
    String defaultValue() default "";

    /**
     * @return Name of field as it appears in the properties file or object.
     * @since 1.0.0
     */
    String alternateName() default "";
}
