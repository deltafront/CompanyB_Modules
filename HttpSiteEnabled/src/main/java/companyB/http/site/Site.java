package companyB.http.site;

import com.google.gson.Gson;
import org.apache.commons.lang3.Validate;

/**
 * Holds information for the site as a whole.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class Site
{
    private final String siteName;
    private final String siteId;
    private final IsoLang primaryLang;
    private final IsoLang[] supportedLangs;
    private final IsoLocale locale;
    private final String hostName;
    private final String hostIpAddress;

    /**
     * Default constructor.
     * @param siteName Name of the site.
     * @param siteId Unique Id for the site.
     * @param primaryLang Primary Language that is supported by this site.
     * @param supportedLangs All languages that are supported by this site.
     * @param locale Primary Locale for this site.
     * @since 1.0
     */
    public Site(String siteName, String siteId, IsoLang primaryLang, IsoLang[] supportedLangs, IsoLocale locale)
    {
        Validate.notNull(primaryLang);
        Validate.notNull(locale);
        Validate.notBlank(siteName);
        Validate.notBlank(siteId);
        this.siteName = siteName;
        this.siteId = siteId;
        this.primaryLang = primaryLang;
        this.locale = locale;
        this.hostName = SiteUtils.getHostName();
        this.hostIpAddress = SiteUtils.getLocalIpAddress();
        this.supportedLangs = (null == supportedLangs) ? new IsoLang[0] : supportedLangs;
        Validate.notNull(this.supportedLangs);
        Validate.notBlank(hostName);
        Validate.notBlank(hostIpAddress);
    }

    /**
     * @return Name of the site.
     * @since 1.0
     */
    public String getSiteName()
    {
        return siteName;
    }

    /**
     * @return Unique Id for the site.
     * @since 1.0
     */
    public String getSiteId()
    {
        return siteId;
    }

    /**
     * @return Primary Language supported by this site.
     * @since 1.0
     */
    public IsoLang getPrimaryLang()
    {
        return primaryLang;
    }

    /**
     * @return Locale of this site.
     * @since 1.0
     */
    public IsoLocale getLocale()
    {
        return locale;
    }

    /**
     * @return Host name of site.
     * @since 1.0
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * @return IP Address of site.
     * @since 1.0
     */
    public String getHostIpAddress()
    {
        return hostIpAddress;
    }

    /**
     * @return All languages supported by this site, including the primary.
     * @since 1.0
     */
    public IsoLang[] getSupportedLangs()
    {
        IsoLang[]isoLangs = new IsoLang[this.supportedLangs.length +1];
        for(int i = 0; i < this.supportedLangs.length; i++)
        {
            isoLangs[i] = this.supportedLangs[i];
        }
        isoLangs[this.supportedLangs.length] = primaryLang;
        return isoLangs;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
