package companyB.common.atomic;

import java.util.Optional;

/**
 * Created by chburrell on 2/22/17.
 */
public class AtomicOptional<T>
{

    private volatile T optionalValue;
    public AtomicOptional(T optionalValue)
    {
        this.optionalValue = optionalValue;
    }
    public Optional<T>get()
    {
        return (null == this.optionalValue) ?
                Optional.empty() :
                Optional.of(this.optionalValue);
    }
    public void set(T optionalValue)
    {
        this.optionalValue = optionalValue;
    }
    public Optional<T>getAndSet(T optionalValue)
    {
        final Optional<T>result = (null == this.optionalValue) ?
                Optional.empty() :
                Optional.of(this.optionalValue);
        this.optionalValue = optionalValue;
        return result;
    }

}
