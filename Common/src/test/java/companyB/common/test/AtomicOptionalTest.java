package companyB.common.test;


import companyB.common.atomic.AtomicOptional;
import org.testng.annotations.Test;

import java.util.Optional;

@Test(groups = {"unit","common","utils","atomic"})
public class AtomicOptionalTest extends TestBase
{

    private String expected = "Foo";

    public void getOriginalValue()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.get();
        validateTrue(actual.isPresent());
        validateEquality(expected,actual.get());
    }
    public void getEmptyValue()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(null);
        final Optional<String>actual = atomicOptional.get();
        validateFalse(actual.isPresent());
        validateEquality(Optional.empty(),actual);
    }
    public void getSetValue()
    {
        final String newValue = "Bar";
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        atomicOptional.set(newValue);
        final Optional<String>actual = atomicOptional.get();
        validateTrue(actual.isPresent());
        validateEquality(newValue,actual.get());
    }
    public void setToNull()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        atomicOptional.set(null);
        final Optional<String>actual = atomicOptional.get();
        validateFalse(actual.isPresent());
        validateEquality(Optional.empty(),actual);
    }
    public void getUpdatedValue()
    {
        final String newValue = "Bar";
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.getAndSet(newValue);
        validateTrue(actual.isPresent());
        validateEquality(expected,actual.get());
        final Optional<String>newActual = atomicOptional.get();
        validateTrue(actual.isPresent());
        validateEquality(newActual.get(),newValue);
    }
    public void setUpdatedToNull()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.getAndSet(null);
        validateTrue(actual.isPresent());
        validateEquality(expected,actual.get());
        final Optional<String>newActual = atomicOptional.get();
        validateEquality(Optional.empty(),newActual);
    }
    public void getAndSetUpdatedToNull()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(null);
        final Optional<String>actual = atomicOptional.getAndSet("42");
        validateFalse(actual.isPresent());
    }
}
