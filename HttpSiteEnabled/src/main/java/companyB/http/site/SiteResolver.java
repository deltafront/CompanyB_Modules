package companyB.http.site;

/**
 * Definition for resolving a site, via either local filesystem, database lookup or other means.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public interface SiteResolver
{
    /**
     * Classes that implement this method will have some sort of way of performing a lookup of a site by its' host
     * and port and fetching all of the required site information from an external source.
     * @param host Host / cluster on which the site is being run.
     * @param port Port that the WebApp container for this. This can be a null value.
     * @return Valid site object.
     * @since 2.0.0
     */
    @SuppressWarnings("PMD.UnusedModifier")
    public Site resolveSite(String host,Integer port);
}
