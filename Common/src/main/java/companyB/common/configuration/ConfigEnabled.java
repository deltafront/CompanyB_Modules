package companyB.common.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates that a ConfigEnabler field should be decorated with the ClassLevelConfigEnabler.
 * @see companyB.common.configuration.ConfigEnabler
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEnabled
{
    /**
     * @return Filename of the configuration properties file.
     * @since 1.0.0
     */
    public String filename();

    /**
     * @return Contextual family.
     * @since 1.0.0
     */
    public String family();
}
