package companyB.context.test;


import companyB.common.utils.ToStringUtils;

public class TestObject
{
    public int intValue;
    public String stringValue;
    public boolean booleanValue;
    public Iterable<Boolean> booleanIterable;

    public TestObject()
    {
        this(42,"foo",false);
    }
    public TestObject(Iterable<Boolean> booleanIterable)
    {
       this.booleanIterable = booleanIterable;
    }
    public TestObject(String stringValue)
    {
        this(42,stringValue,false);
    }
    public TestObject(int intValue)
    {
        this(intValue,"foo",false);
    }
    public TestObject(boolean booleanValue)
    {
        this(42,"foo",booleanValue);
    }
    public TestObject(boolean booleanValue, String stringValue)
    {
        this(42,stringValue,booleanValue);
    }
    public TestObject(int intValue, String stringValue)
    {
        this(intValue,stringValue,false);
    }
    public TestObject(int intValue, boolean booleanValue)
    {
        this(intValue,"foo",booleanValue);
    }
    public TestObject(int intValue, String stringValue, boolean booleanValue)
    {
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.intValue = intValue;
    }

    public String toString()
    {
        return String.format("%s %s %s %s",intValue,booleanValue,stringValue, ToStringUtils.iterableToString(booleanIterable));
    }

}
