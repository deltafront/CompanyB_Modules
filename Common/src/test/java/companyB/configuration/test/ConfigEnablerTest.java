package companyB.configuration.test;

import companyB.configuration.ConfigEnabler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","config.enabler","configuration.enabled"}, dataProvider = "default")
public class ConfigEnablerTest extends ConfigurationEnabledTestBase
{
    private static String family = "foo1";
    private static String INVALID_FAMILY = "INVALID_FAMILY";
    private Map<String,Object> mapping;
    private final static Boolean USE_DEFAULT_VALUE = true;
    private final static Boolean USE_VALID_KEY = true;
    private final static Boolean USE_DEFAULT_PARAM = true;
    private Map<Class,Object>expectedNullValues;

    private class SimplePair<T>
    {
        String key;
        T defaultValue;
    }
    @BeforeMethod
    public void before()
    {
        mapping = getValues();
        expectedNullValues = new HashMap<>();
        expectedNullValues.put(Short.class,Short.valueOf("0"));
        expectedNullValues.put(Long.class,Long.valueOf("0"));
        expectedNullValues.put(Integer.class, 0);
        expectedNullValues.put(Double.class,0.0D);
        expectedNullValues.put(Float.class,0.0F);
        expectedNullValues.put(BigInteger.class,BigInteger.valueOf(0L));
        expectedNullValues.put(BigDecimal.class,BigDecimal.valueOf(0.0D));
        expectedNullValues.put(String.class,"");
        expectedNullValues.put(Boolean.class,false);
    }


    @DataProvider(name = "default")
    public static Object[][] parameters()
    {
        return new Object[][]{
                {family,!USE_DEFAULT_VALUE,USE_VALID_KEY,!USE_DEFAULT_PARAM},
                {INVALID_FAMILY,USE_DEFAULT_VALUE,USE_VALID_KEY,!USE_DEFAULT_PARAM},
                {family,USE_DEFAULT_VALUE,!USE_VALID_KEY,!USE_DEFAULT_PARAM},
                {family,!USE_DEFAULT_VALUE,USE_VALID_KEY,USE_DEFAULT_PARAM},
                {INVALID_FAMILY,USE_DEFAULT_VALUE,USE_VALID_KEY,USE_DEFAULT_PARAM}
        };
    }

    public void testString(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<String>pair = simplePair("stringValue","100");
        final BiFunction<SimplePair<String>,ConfigEnabler,String>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getString(simplePair.key) :configEnabler.getString(simplePair.key,simplePair.defaultValue);
        runTest("42",pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }

    public void testFloat(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Float>pair = simplePair("floatValue",Float.valueOf("100"));
        final BiFunction<SimplePair<Float>,ConfigEnabler,Float>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getFloat(simplePair.key) :configEnabler.getFloat(simplePair.key,simplePair.defaultValue);
        runTest(Float.valueOf("42"),pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }

    public void testShort(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Short>pair = simplePair("shortValue",Short.valueOf("100"));
        final BiFunction<SimplePair<Short>,ConfigEnabler,Short>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getShort(simplePair.key) :configEnabler.getShort(simplePair.key,simplePair.defaultValue);
        runTest(Short.valueOf("42"),pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }
    public void testBigDecimal(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<BigDecimal>pair = simplePair("bigDecimalValue",BigDecimal.valueOf(1.0D));
        final BiFunction<SimplePair<BigDecimal>,ConfigEnabler,BigDecimal> function =(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getBigDecimal(simplePair.key) :configEnabler.getBigDecimal(simplePair.key,simplePair.defaultValue);
        runTest(BigDecimal.valueOf(10.0D),pair,family,useDefaultValue,useValidKey,useDefaultParam, function);
    }
    public void testBigInteger(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<BigInteger>pair = simplePair("bigIntValue",BigInteger.ONE);
        final BiFunction<SimplePair<BigInteger>,ConfigEnabler,BigInteger>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getBigInteger(simplePair.key) :configEnabler.getBigInteger(simplePair.key,simplePair.defaultValue);
        runTest(BigInteger.TEN,pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }

    public void testInteger(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Integer>pair = simplePair("intValue",100);
        final BiFunction<SimplePair<Integer>,ConfigEnabler,Integer>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getInteger(simplePair.key) :configEnabler.getInteger(simplePair.key,simplePair.defaultValue);
        runTest(42,pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }

    public void testLong(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Long>pair = simplePair("longValue",100L);
        final BiFunction<SimplePair<Long>,ConfigEnabler,Long>function=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getLong(simplePair.key) :configEnabler.getLong(simplePair.key,simplePair.defaultValue);
        runTest(42L,pair,family,useDefaultValue,useValidKey,useDefaultParam,function);
    }

    public void testDouble(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Double>pair = simplePair("doubleValue",100.00);
        final BiFunction<SimplePair<Double>,ConfigEnabler,Double>getDouble=(simplePair, configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getDouble(simplePair.key) :configEnabler.getDouble(simplePair.key,simplePair.defaultValue);
        runTest(42.0,pair,family,useDefaultValue,useValidKey,useDefaultParam,getDouble);
    }
    public void testBoolean(String family, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam)
    {
        final SimplePair<Boolean>pair = simplePair("booleanValue",false);
        final  BiFunction<SimplePair<Boolean>,ConfigEnabler,Boolean>getBoolean=(simplePair,configEnabler)-> (null == simplePair.defaultValue) ?
                configEnabler.getBoolean(simplePair.key) : configEnabler.getBoolean(simplePair.key,simplePair.defaultValue);
        runTest(true,pair,family,useDefaultValue,useValidKey,useDefaultParam,getBoolean);
    }

    private<T> void runTest(T valueToInsert, SimplePair<T>simplePair, String configEnablerFamily, Boolean useDefaultValue, Boolean useValidKey, Boolean useDefaultParam,BiFunction<SimplePair<T>,ConfigEnabler,T> function)
    {
        System.out.println(String.format("Family: %s\tUse default value? %b\tUse Valid Key? %b\tUse Default Param? %b",
                configEnablerFamily,useDefaultValue, useValidKey, useDefaultParam));
        final String key = String.format("foo1.%s",simplePair.key);
        mapping.put(key,valueToInsert);
        final String path = createTempConfigFile(mapping);
        final ConfigEnabler configEnabler = new ConfigEnabler(path,configEnablerFamily);
        final T expected = (useDefaultValue) ? simplePair.defaultValue : valueToInsert;
        if(!useValidKey)simplePair.key = "";
        if(!useDefaultParam)simplePair.defaultValue = null;
        final T actual = function.apply(simplePair,configEnabler);
        validateNotNull(actual);
        if(INVALID_FAMILY.equals(configEnablerFamily) &&
                (!useDefaultValue || !useDefaultParam))validateEquality(actual,(T)expectedNullValues.get(valueToInsert.getClass()));
        else if(!useValidKey)validateEquality(actual,((T)expectedNullValues.get(valueToInsert.getClass())));
        else validateEquality(actual,expected);
    }
    private<T> SimplePair<T> simplePair(String key, T defaultValue)
    {
        final SimplePair<T>simplePair = new SimplePair<T>();
        simplePair.key = key;
        simplePair.defaultValue = defaultValue;
        return simplePair;
    }

}
