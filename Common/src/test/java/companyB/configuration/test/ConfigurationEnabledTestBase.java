package companyB.configuration.test;

import companyB.configuration.ConfigEnabler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class ConfigurationEnabledTestBase
{
    protected ConfigEnabler configEnabler;

    protected Map<String,Object> getValues()
    {
        final Map<String,Object> values = new HashMap<>();
        final String[] properties =
                {"stringVal","booleanVal","intVal","longVal","shortVal","doubleVal","bigIntVal","bigDoubleVal","floatVal"};
        final Object[] vals =
                {"this is a string",true,42,42L,42,42.00D,42,42.00,42.00F};
        final String[] families  = {"family1", "family2"};
        for (String family : families)
            for(int i = 0; i < properties.length; i++) values.put(String.format("%s.%s",family,properties[i]),vals[i]);
        return values;
    }

    protected String createTempConfigFile(Map<String,Object>configs)
    {
        String path = null;
        try
        {
            File file = File.createTempFile("config","properties");
            Writer writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            Set<String> keys = configs.keySet();
            for (String key : keys)
            {
                Object value = configs.get(key);
                String line = String.format("%s=%s\n",key,String.valueOf(value));
                bufferedWriter.write(line);
            }
            bufferedWriter.close();
            path = file.getAbsolutePath();
            file.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        return path;
    }
    protected void validateEquality(Object expected, Object actual)
    {
        assertThat(expected,is(equalTo(actual)));
    }
    void validateNull(Object instance)
    {
        assertThat(instance,is(nullValue()));
    }
    protected void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
    protected void validateTrue(Boolean condition)
    {
        assertThat(condition,is(true));
    }
    protected void validateFalse(Boolean condition)
    {
        assertThat(condition,is(false));
    }
}
