package companyB.common.test;

import companyB.common.guid.GUID;
import org.testng.annotations.Test;

import java.io.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

@Test(groups = {"unit","common","guid","utils"})
public class GuidTest
{
    public void noArgs()
    {
        final GUID guid = new GUID();
        serDeser(guid);
    }
    public void withArgs()
    {
        final GUID guid = new GUID(42L);
        serDeser(guid);
    }

    private void serDeser(GUID guid)
    {
        try
        {
            final File file = File.createTempFile("guid","ser");
            final OutputStream outputStream = new FileOutputStream(file);
            final ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(guid);
            objectOutputStream.close();
            outputStream.close();
            final InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            final GUID fromStream = (GUID)objectInputStream.readObject();
            assertThat(fromStream,is(not(nullValue())));
            assertThat(guid.getGuid(), is(equalTo(fromStream.getGuid())));
            file.deleteOnExit();
        }
        catch (IOException| ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void getHashedGuid()
    {
        GUID guid = new GUID();
        IntStream.range(0,1000).forEach((i)-> assertThat(guid.getHashedGuid(),is(not(nullValue()))));
    }

}
