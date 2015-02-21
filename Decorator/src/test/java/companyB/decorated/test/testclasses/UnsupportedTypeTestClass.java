package companyB.decorated.test.testclasses;

import companyB.decorated.Decorated;

import java.util.List;

/**
 * Created by chburrell on 2/16/15.
 */
public class UnsupportedTypeTestClass
{
    @Decorated
    public List<String> stringListVal;
}
