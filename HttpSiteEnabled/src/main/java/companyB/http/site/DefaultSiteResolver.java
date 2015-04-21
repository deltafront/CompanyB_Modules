package companyB.http.site;

import org.apache.commons.lang3.Validate;

/**
 * Default implementation of SiteResolver.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class DefaultSiteResolver implements SiteResolver
{

    @Override
    public Site resolveSite(String host, Integer port)
    {
        Validate.notEmpty(host);
        String strPort = (null == port) ? "" : String.valueOf(port);
        return new Site(String.format("%s%s",host,strPort),"0",IsoLang.English,null,IsoLocale.United_States);
    }
}
