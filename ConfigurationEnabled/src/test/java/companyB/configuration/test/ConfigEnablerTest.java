package companyB.configuration.test;

import companyB.configuration.ConfigEnabler;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chburrell on 2/13/15.
 */
@SuppressWarnings("ConstantConditions")
public class ConfigEnablerTest extends ConfigurationEnabledTestBase
{
    private String family;
    private Map<String,Object> mapping;
    private String key;
    private Object value;
    private String path;

    @Before
    public void before()
    {
        family = "foo1";
        mapping = getValues();
    }


    @Test
    public void integerNoDefaultValidFamily()
    {
        key = String.format("%s.intValue",family);
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Integer out = configEnabler.getInteger("intValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void integerNoDefaultInValidFamily()
    {
        key = String.format("%s.intValue",family);
        Integer def = 0;
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Integer out = configEnabler.getInteger("intValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void integerNoDefaultInValidName()
    {
        key = String.format("%s.intValue",family);
        Integer def = 0;
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Integer out = configEnabler.getInteger("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void integerDefaultValidFamily()
    {
        key = String.format("%s.intValue",family);
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Integer out = configEnabler.getInteger("intValue", 100);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void integerDefaultInValidFamily()
    {
        key = String.format("%s.intValue",family);
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"foo2");
        Integer out = configEnabler.getInteger("intValue", 100);
        assertNotNull(out);
        assertEquals(new Integer(100),out);
    }

    @Test
    public void bigIntegerNoDefaultValidFamily()
    {
        key = String.format("%s.bigIntValue",family);
        value = new BigInteger("42");
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigInteger out = configEnabler.getBigInteger("bigIntValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void bigIntegerNoDefaultInValidFamily()
    {
        key = String.format("%s.bigIntValue",family);
        BigInteger def = new BigInteger("0");
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        BigInteger out = configEnabler.getBigInteger("bigIntValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void bigIntegerNoDefaultInValidName()
    {
        key = String.format("%s.bigIntValue",family);
        BigInteger def = new BigInteger("0");
        value = 42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigInteger out = configEnabler.getBigInteger("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void bigIntegerDefaultValidFamily()
    {
        BigInteger def = new BigInteger("100");
        key = String.format("%s.bigIntValue",family);
        value = new BigInteger("42");
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigInteger out = configEnabler.getBigInteger("bigIntValue",def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void bigIntegerDefaultInValidFamily()
    {
        BigInteger def = new BigInteger("100");
        key = String.format("%s.bigIntValue",family);
        value = new BigInteger("42");
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        BigInteger out = configEnabler.getBigInteger("bigIntValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }

    @Test
    public void longNoDefaultValidFamily()
    {
        key = String.format("%s.longValue",family);
        value = 42L;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Long out = configEnabler.getLong("longValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void longNoDefaultInValidFamily()
    {
        Long def = 0L;
        key = String.format("%s.longValue",family);
        value = 42L;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Long out = configEnabler.getLong("longValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void longNoDefaultInValidName()
    {
        Long def = 0L;
        key = String.format("%s.longValue",family);
        value = 42L;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Long out = configEnabler.getLong("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void longDefaultValidFamily()
    {
        key = String.format("%s.longValue",family);
        Long def = 100L;
        value = 42L;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Long out = configEnabler.getLong("longValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void longDefaultInValidFamily()
    {
        key = String.format("%s.longValue",family);
        value = 42L;
        Long def = 100L;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"foo2");
        Long out = configEnabler.getLong("longValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }

    @Test
    public void shortNoDefaultValidFamily()
    {
        key = String.format("%s.shortValue",family);
        value = Short.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Short out = configEnabler.getShort("shortValue");
        assertNotNull(out);
        assertEquals(value,out);
    }

    @Test
    public void shortNoDefaultInValidFamily()
    {
        Short def = 0;
        key = String.format("%s.shortValue",family);
        value = Short.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"FOO2");
        Short out = configEnabler.getShort("shortValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void shortNoDefaultInValidName()
    {
        Short def = 0;
        key = String.format("%s.shortValue",family);
        value = Short.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Short out = configEnabler.getShort("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void shortDefaultValidFamily()
    {
        short def = 100;
        key = String.format("%s.shortValue",family);
        value = Short.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Short out = configEnabler.getShort("shortValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void shortDefaultInValidFamily()
    {
        short def = 100;
        key = String.format("%s.shortValue",family);
        value = Short.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Short out = configEnabler.getShort("shortValue", def);
        assertNotNull(out);
        assertEquals(new Short(def),out);
    }

    @Test
    public void stringNoDefaultValidFamily()
    {
        key = String.format("%s.stringValue",family);
        value = "This is a string";
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        String out = configEnabler.getString("stringValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void stringNoDefaultInValidFamily()
    {
        key = String.format("%s.stringValue",family);
        value = "This is a string";
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"");
        String out = configEnabler.getString("stringValue");
        assertNull(out);
    }
    @Test
    public void stringNoDefaultInValidName()
    {
        key = String.format("%s.stringValue",family);
        value = "This is a string";
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        String out = configEnabler.getString("");
        assertNull(out);
    }
    @Test
    public void stringDefaultValidFamily()
    {
        String def = "default value";
        key = String.format("%s.stringValue",family);
        value = "This is a string";
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        String out = configEnabler.getString("stringValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void stringDefaultInValidFamily()
    {
        String def = "default value";
        key = String.format("%s.stringValue",family);
        value = "This is a string";
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        String out = configEnabler.getString("stringValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }

    @Test
    public void floatNoDefaultValidFamily()
    {
        key = String.format("%s.floatValue",family);
        value = Float.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Float out = configEnabler.getFloat("floatValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void floatNoDefaultInValidFamily()
    {
        Float def = 0.0F;
        key = String.format("%s.floatValue",family);
        value = Float.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"");
        Float out = configEnabler.getFloat("floatValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void floatNoDefaultInValidName()
    {
        Float def = 0.0F;
        key = String.format("%s.floatValue",family);
        value = Float.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Float out = configEnabler.getFloat("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void floatDefaultValidFamily()
    {
        Float def = Float.intBitsToFloat(42);
        key = String.format("%s.floatValue",family);
        value = Float.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Float out = configEnabler.getFloat("floatValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void floatDefaultInValidFamily()
    {
        Float def = Float.intBitsToFloat(42);
        key = String.format("%s.floatValue",family);
        value = Float.MAX_VALUE;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Float out = configEnabler.getFloat("floatValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }

    @Test
    public void doubleNoDefaultValidFamily()
    {
        key = String.format("%s.doubleValue",family);
        value = 42.42D;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Double out = configEnabler.getDouble("doubleValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void doubleNoDefaultInValidFamily()
    {
        Double def = 0.0D;
        key = String.format("%s.doubleValue",family);
        value = 42.42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"");
        Double out = configEnabler.getDouble("doubleValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void doubleNoDefaultInValidName()
    {
        Double def = 0.0D;
        key = String.format("%s.doubleValue",family);
        value = 42.42;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Double out = configEnabler.getDouble("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void doubleDefaultValidFamily()
    {
        Double def = 100.00D;
        key = String.format("%s.doubleValue",family);
        value = 42.42D;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Double out = configEnabler.getDouble("doubleValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void doubleDefaultInValidFamily()
    {
        Double def = 100.00D;
        key = String.format("%s.doubleValue",family);
        value = 42.42D;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Double out = configEnabler.getDouble("doubleValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }

    @Test
    public void bigDecimalNoDefaultValidFamily()
    {
        key = String.format("%s.bigDecimalValue",family);
        value = new BigDecimal(BigDecimal.ONE.doubleValue());
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigDecimal out = configEnabler.getBigDecimal("bigDecimalValue");
        assertNotNull(out);
        assertEquals(((BigDecimal)value).doubleValue(),out.doubleValue(),0);
    }
    @Test
    public void bigDecimalNoDefaultInValidFamily()
    {
        BigDecimal def = new BigDecimal(0.0);
        key = String.format("%s.bigDecimalValue",family);
        value = new BigDecimal(BigDecimal.ONE.doubleValue());
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"");
        BigDecimal out = configEnabler.getBigDecimal("bigDecimalValue");
        assertNotNull(out);
        assertEquals(def.doubleValue(),out.doubleValue(),0);
    }
    @Test
    public void bigDecimalNoDefaultInValidName()
    {
        BigDecimal def = new BigDecimal(0.0);
        key = String.format("%s.bigDecimalValue",family);
        value = new BigDecimal(BigDecimal.ONE.doubleValue());
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigDecimal out = configEnabler.getBigDecimal("");
        assertNotNull(out);
        assertEquals(def.doubleValue(),out.doubleValue(),0);
    }
    @Test
    public void bigDecimalDefaultValidFamily()
    {
        BigDecimal def = new BigDecimal(BigDecimal.TEN.doubleValue());
        key = String.format("%s.bigDecimalValue",family);
        value = new BigDecimal(BigDecimal.ONE.doubleValue());
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        BigDecimal out = configEnabler.getBigDecimal("bigDecimalValue",def);
        assertNotNull(out);
        assertEquals(((BigDecimal)value).doubleValue(),out.doubleValue(),0);
    }
    @Test
    public void bigDecimalDefaultInValidFamily()
    {
        BigDecimal def = new BigDecimal(BigDecimal.ROUND_DOWN);
        key = String.format("%s.bigDecimalValue",family);
        value = new BigDecimal(BigDecimal.ROUND_CEILING);
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        BigDecimal out = configEnabler.getBigDecimal("bigDecimalValue",def);
        assertNotNull(out);
        assertEquals(def.doubleValue(),out.doubleValue(),0);
    }


    @Test
    public void booleanNoDefaultValidFamily()
    {
        key = String.format("%s.booleanValue",family);
        value = true;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Boolean out = configEnabler.getBoolean("booleanValue");
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void booleanNoDefaultInValidFamily()
    {
        Boolean def = false;
        key = String.format("%s.booleanValue",family);
        value = true;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"");
        Boolean out = configEnabler.getBoolean("booleanValue");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void booleanNoDefaultInValidName()
    {
        Boolean def = false;
        key = String.format("%s.booleanValue",family);
        value = true;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Boolean out = configEnabler.getBoolean("");
        assertNotNull(out);
        assertEquals(def,out);
    }
    @Test
    public void booleanDefaultValidFamily()
    {
        Boolean def = false;
        key = String.format("%s.booleanValue",family);
        value = true;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,family);
        Boolean out = configEnabler.getBoolean("booleanValue", def);
        assertNotNull(out);
        assertEquals(value,out);
    }
    @Test
    public void booleanDefaultInValidFamily()
    {
        Boolean def = false;
        key = String.format("%s.booleanValue",family);
        value = true;
        mapping.put(key,value);
        path = createTempConfigFile(mapping);
        configEnabler = new ConfigEnabler(path,"Foo2");
        Boolean out = configEnabler.getBoolean("booleanValue", def);
        assertNotNull(out);
        assertEquals(def,out);
    }
}
