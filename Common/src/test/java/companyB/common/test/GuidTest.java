package companyB.common.test;

import companyB.common.guid.GUID;
import org.testng.annotations.Test;

import java.io.*;
import java.util.stream.IntStream;

@Test(groups = {"unit","common","guid","utils"})
public class GuidTest extends TestBase
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
            validateNotNull(fromStream);
            validateEquality(guid.getGuid(),fromStream.getGuid());
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
        IntStream.range(0,1000).forEach((i)-> validateNotNull(guid.getHashedGuid()));
    }

}
