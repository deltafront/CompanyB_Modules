# SpringEnabled

## Purpose
By importing this module, you get a pre-built set of dependencies suitable for building most Spring-based applications.
Also included are the following:

### Annotations
Included are facilities to decorate the following:
*   Slf4J logger fields with a @Log Annotation for autowiring
*   EventLogger field with an @EventLogger annotation for autowiring
*   ConfigEnabler field with @ConfigEnabled field for autowiring

### Default Bean Definitions
All of the classes in `companyB.common.utils` and `companyB.common.conversions` have default bean definitions in ~./xml/common-beans.xml.