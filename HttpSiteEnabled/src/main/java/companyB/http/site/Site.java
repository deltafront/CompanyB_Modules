package companyB.http.site;

import com.google.gson.Gson;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 * Holds information for the site as a whole.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
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
     * @since 1.0.0
     */
    public Site(String siteName, String siteId, IsoLang primaryLang, IsoLang[] supportedLangs, IsoLocale locale)
    {
        Validate.notNull(primaryLang,"Primary Lang is required.");
        Validate.notNull(locale,"Locale is required.");
        Validate.notBlank(siteName,"Site name is required.");
        Validate.notBlank(siteId,"Site ID is required.");
        this.siteName = siteName;
        this.siteId = siteId;
        this.primaryLang = primaryLang;
        this.locale = locale;
        SiteUtils siteUtils = new SiteUtils();
        this.hostName = siteUtils.getHostName();
        this.hostIpAddress = siteUtils.getLocalIpAddress();
        this.supportedLangs = (null == supportedLangs) ? new IsoLang[0] : supportedLangs;
        Validate.notNull(this.supportedLangs,"Supported languages are null after processing!");
        Validate.notBlank(this.hostName,"Host name is null after processing!");
        Validate.notBlank(this.hostIpAddress,"Host IP Address is null after processing!");
    }

    /**
     * @return Name of the site.
     * @since 1.0.0
     */
    public String getSiteName()
    {
        return siteName;
    }

    /**
     * @return Unique Id for the site.
     * @since 1.0.0
     */
    public String getSiteId()
    {
        return siteId;
    }

    /**
     * @return Primary Language supported by this site.
     * @since 1.0.0
     */
    public IsoLang getPrimaryLang()
    {
        return primaryLang;
    }

    /**
     * @return Locale of this site.
     * @since 1.0.0
     */
    public IsoLocale getLocale()
    {
        return locale;
    }

    /**
     * @return Host name of site.
     * @since 1.0.0
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * @return IP Address of site.
     * @since 1.0.0
     */
    public String getHostIpAddress()
    {
        return hostIpAddress;
    }

    /**
     * @return All languages supported by this site, including the primary.
     * @since 1.0.0
     */
    public IsoLang[] getSupportedLangs()
    {
        if(!ArrayUtils.contains(this.supportedLangs,primaryLang))
        {
            IsoLang[]isoLangs = new IsoLang[this.supportedLangs.length +1];
            System.arraycopy(this.supportedLangs, 0, isoLangs, 0, this.supportedLangs.length);
            isoLangs[this.supportedLangs.length] = primaryLang;
            return isoLangs;
        }
        else return this.supportedLangs;
    }

    /**
     * Returns the path to the desired resource file. This is assuming that the resource file's name follows a format
     * such as 'foo/test_EN_US.properties'.
     * @param resourceDir Directory in which resource is located.
     * @param prefix Prefix for the resource file.
     * @param language Language for the resource file.
     * @param locale Locale for the resource file.
     * @return Path to the desired properties file.
     * @since 2.0.0
     */
    public String getResourcePropertiesFileName(String resourceDir,String prefix,IsoLang language, IsoLocale locale)
    {
        Validate.notNull(resourceDir);
        Validate.notBlank(prefix);
        Validate.notNull(language);
        Validate.notNull(locale);
        if('/' !=resourceDir.charAt(resourceDir.length() -1))resourceDir += "/";
        if(!ArrayUtils.contains(supportedLangs,language))return null;
        if(!this.locale.equals(locale))return null;
        return String.format("%s%s_%s_%s.properties",resourceDir,prefix,language.name(),locale.name());
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
