package companyB.common;

import companyB.common.guid.GUID;
import org.testng.annotations.Test;

import java.io.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","guid"})
public class GuidTest
{
    public void noArgs()
    {
        GUID guid = new GUID();
        serDeser(guid);
    }
    public void withArgs()
    {
        GUID guid = new GUID(42L);
        serDeser(guid);
    }

    private void serDeser(GUID guid)
    {
        try
        {
            File file = File.createTempFile("guid","ser");
            OutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(guid);
            objectOutputStream.close();
            outputStream.close();
            InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            GUID fromStream = (GUID)objectInputStream.readObject();
            assertNotNull(fromStream);
            assertEquals(guid.getGuid(), fromStream.getGuid());
            file.deleteOnExit();
        }
        catch (IOException| ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
