package companyB.common.test;


import companyB.common.atomic.AtomicOptional;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chburrell on 2/22/17.
 */
@Test(groups = {"unit","atomic"})
public class AtomicOptionalTest
{

    private String expected = "Foo";

    public void getOriginalValue()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.get();
        assertThat(actual.isPresent(),is(true));
        assertThat(expected,is(equalTo(actual.get())));
    }
    public void getEmptyValue()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(null);
        final Optional<String>actual = atomicOptional.get();
        assertThat(actual.isPresent(),is(false));
        assertThat(Optional.empty(),is(equalTo(actual)));
    }
    public void getSetValue()
    {
        final String newValue = "Bar";
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        atomicOptional.set(newValue);
        final Optional<String>actual = atomicOptional.get();
        assertThat(actual.isPresent(),is(true));
        assertThat(actual.get(),is(equalTo(newValue)));
    }
    public void setToNull()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        atomicOptional.set(null);
        final Optional<String>actual = atomicOptional.get();
        assertThat(actual.isPresent(),is(false));
        assertThat(Optional.empty(),is(equalTo(actual)));
    }
    public void getUpdatedValue()
    {
        final String newValue = "Bar";
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.getAndSet(newValue);
        assertThat(actual.isPresent(),is(true));
        assertThat(actual.get(),is(equalTo(expected)));
        final Optional<String>newActual = atomicOptional.get();
        assertThat(newActual.isPresent(),is(true));
        assertThat(newActual.get(),is(equalTo(newValue)));
    }
    public void setUpdatedToNull()
    {
        final AtomicOptional<String>atomicOptional = new AtomicOptional<>(expected);
        final Optional<String>actual = atomicOptional.getAndSet(null);
        assertThat(actual.isPresent(),is(true));
        assertThat(actual.get(),is(equalTo(expected)));
        final Optional<String>newActual = atomicOptional.get();
        assertThat(newActual.isPresent(),is(false));
        assertThat(Optional.empty(),is(equalTo(newActual)));
    }
}
