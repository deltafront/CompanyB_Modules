package companyB.decorated.test.testclasses;

import companyB.decorated.Decorated;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestClass
{
    @Decorated()
    public String stringValNoDefault;
    @Decorated(defaultValue = "this")
    public String stringValDefault;
    @Decorated(alternateName = "stringAlt")
    public String stringValAltName;
    @Decorated(defaultValue = "alt", alternateName = "stringAltNameDefault")
    public String stringValAltNameDefault;
    @Decorated()
    public Long longVal;
    @Decorated()
    public Integer intVal;
    @Decorated()
    public Double doubleVal;
    @Decorated()
    public Short shortVal;
    @Decorated()
    public  Boolean boolVal;
    @Decorated
    public final String canNotSet = "Can not set";
    @Decorated
    public Character charVal;
    @Decorated
    public Byte byteVal;
    @Decorated
    public BigInteger bigIntVal;
    @Decorated
    public BigDecimal bigDecimalVal;
    @Decorated(defaultValue = "Found!")
    public String notFoundField;

    public static class PublicInnerClass
    {
        @Decorated
        public String stringVal;
    }
}
