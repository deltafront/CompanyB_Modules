package companyB.decorated.test;

import companyB.decorated.BeanDecorator;
import companyB.decorated.UnsupportedTypeException;
import companyB.decorated.test.testclasses.AbstractFileLoadedTestClass;
import companyB.decorated.test.testclasses.FileLoadedTestClass;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileLoadedTestClassTest
{
    private FileLoadedTestClass fileLoadedTestClass;
    private AbstractFileLoadedTestClass abstractFileLoadedTestClass;
    private String key = "stringVal";
    private String value = "value";
    private BeanDecorator beanDecorator;
    @BeforeMethod
    public void before()
    {
        beanDecorator = new BeanDecorator();
    }

    @Test
    public void validPropertiesFile() throws UnsupportedTypeException
    {
        generateFile(true,false);
        assertNotNull(fileLoadedTestClass);
        assertEquals(value,fileLoadedTestClass.stringVal);
    }
    @Test
    public void validPropertiesFileInvalidClass() throws UnsupportedTypeException
    {
        generateFile(true,false);
        assertNull(abstractFileLoadedTestClass);
    }

    @Test
    public void inValidPropertiesXmlFile() throws UnsupportedTypeException
    {
        generateFile(false, true);
        assertNull(fileLoadedTestClass);
    }
    @Test
    public void inValidPropertiesFile() throws UnsupportedTypeException
    {
        generateFile(false, false);
        assertNull(fileLoadedTestClass);
    }

    @Test
    public void validPropertiesXmlFile() throws UnsupportedTypeException
    {
        generateFile(true,true);
        assertNotNull(fileLoadedTestClass);
        assertEquals(value,fileLoadedTestClass.stringVal);
    }
    private void generateFile(boolean loadCorrectFile, boolean isXML) throws UnsupportedTypeException
    {
        String prolog = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">";
        String property = (isXML) ? String.format("%s<properties><entry key=\"%s\">%s</entry></properties>",prolog,key,value) : String.format("%s=%s",key,value);
        String filename = (isXML) ? "temp.xml" : "temp.properties";
        File file = new File(filename);
        try
        {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(property);
            fileWriter.close();
            fileLoadedTestClass = (loadCorrectFile) ?
                    beanDecorator.decorate(FileLoadedTestClass.class,file.getAbsolutePath()) :
                    beanDecorator.decorate(FileLoadedTestClass.class, "test.properties");
            abstractFileLoadedTestClass = beanDecorator.decorate(AbstractFileLoadedTestClass.class,file.getAbsolutePath());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(file.exists())
            {
                file.deleteOnExit();
            }
        }

    }
}