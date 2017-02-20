package companyB.workspace.test;

import companyB.workspace.TemporaryWorkspace;
import companyB.workspace.TemporaryWorkspaceDefaultImpl;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static junit.framework.TestCase.*;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
@Test(groups = {"unit", "workspace"})
public class WorkspaceTests
{
    private TemporaryWorkspace temporaryWorkspace;
    private String pathSep = File.separator;

    @BeforeMethod
    public void before()
    {
        temporaryWorkspace = new TemporaryWorkspaceDefaultImpl();
        System.out.println(temporaryWorkspace.getRoot());
    }
    @AfterMethod
    public void after() throws IOException
    {
        try
        {
            temporaryWorkspace.close();
        }
        catch (IOException | IllegalArgumentException e)
        {
            //swallow
        }

    }

    public void getRoot()
    {
        String path = FileUtils.getTempDirectoryPath();
        String root = temporaryWorkspace.getRoot().toAbsolutePath().toString();
        assertTrue(String.format("Path '%s' does not contain root '%s'.",path,root),root.contains(path));
    }
    public void createDirectory()
    {
        String directory = "foo";
        String expected = String.format("%s%s%s", temporaryWorkspace.getRoot(), pathSep,directory);
        String actual = temporaryWorkspace.createDirectory(directory).toAbsolutePath().toString();
        assertEquals(expected,actual);
    }
    public void copyFileAtRoot()
    {
        copyOrMoveFile(null,false);
    }
    public void copyFileInExistingDir()
    {
        temporaryWorkspace.createDirectory("baz");
        copyOrMoveFile("baz",false);
    }
    public void copyFileInNonExistingDir()
    {
        copyOrMoveFile("baz",false);
    }
    public void moveFileAtRoot()
    {
        copyOrMoveFile(null,true);
    }
    public void moveFileInExistingDir()
    {
        temporaryWorkspace.createDirectory("baz");
        copyOrMoveFile("baz",true);
    }
    public void moveFileInNonExistingDir()
    {
        copyOrMoveFile("baz",true);
    }
    public void writeFile()
    {
        writeGetDeleteFile(false,false);
    }
    public void getFile()
    {
        writeGetDeleteFile(true,false);
    }
    public void deleteFile()
    {
        writeGetDeleteFile(false,true);
    }
    private void copyOrMoveFile(String dir, Boolean move)
    {
        try
        {
            final File expected = File.createTempFile("foo",".bar");
            expected.deleteOnExit();
            final File actual = (move) ?
                    temporaryWorkspace.moveFile(expected,dir).toFile() :
                    temporaryWorkspace.copyFile(expected,dir).toFile();
            assertTrue(null != actual);
            assertEquals(!move, expected.exists());
        }
        catch (IOException e)
        {
            fail("Unexpected exception while performing file IO." + e.getMessage());
        }

    }
    private void writeGetDeleteFile(Boolean get, Boolean delete)
    {
        final String content = "This is some content.";
        final byte[]bytes = content.getBytes();
        final Path expected = temporaryWorkspace.writeFile("temp.txt",bytes);
        assertNotNull(expected);
        if(get)
        {
            final File actual  = temporaryWorkspace.getFile(expected.toFile().getName());
            assertNotNull(actual);
            assertTrue(actual.getName().contains("temp.txt"));
            try
            {
                final String actualContent = FileUtils.readFileToString(actual, "UTF-8");
                assertNotNull(actualContent);
                assertEquals(content,actualContent);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(delete)
            {
                temporaryWorkspace.removeFile(actual.getName());
            }
        }
        if(delete)
        {
            temporaryWorkspace.removeFile("temp.txt");
            final File actual  = temporaryWorkspace.getFile(expected.toFile().getName());
            assertNull(actual);
        }
    }
}
