package companyB.workspace;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Default implementation of TemporaryWorkspace.
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0.0
 */
public class TemporaryWorkspaceDefaultImpl implements TemporaryWorkspace
{
    private Path root;
    private final String pathSep;
    private final Logger LOGGER = LoggerFactory.getLogger(TemporaryWorkspaceDefaultImpl.class);

    /**
     * Default constructor for class.
     */
    public TemporaryWorkspaceDefaultImpl()
    {
        final File finalRoot = FileUtils.getTempDirectory();
        this.pathSep = File.separator;
        final String suffix = String.valueOf(System.currentTimeMillis());
        this.root = new File(String.format("%s%s%s%s",finalRoot.getAbsolutePath(), this.pathSep,suffix, this.pathSep)).toPath();

    }
    @Override
    public Path getRoot()
    {
        return root;
    }


    @Override
    public Path copyFile(File file, String directory)
    {
        return copyAndMaybeDelete(file.toPath(),directory,false);
    }

    @Override
    public Path createDirectory(String directory)
    {
        final File newDir = new File(String.format("%s%s",prependedFile(directory),pathSep));
        Path path = newDir.toPath();
        if(!newDir.exists()) try
        {
            FileUtils.forceMkdir(newDir);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
            path = null;
        }
        return path;
    }


    @Override
    public Path moveFile(File file,String directory)
    {
        return copyAndMaybeDelete(file.toPath(),directory,true);
    }

    @Override
    public Path writeFile(String filename, byte[] content)
    {
        final File newFile = new File(prependedFile(filename));
        Path path = newFile.toPath();
        try
        {
            FileUtils.writeByteArrayToFile(newFile,content);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return path;
    }


    @Override
    public File getFile(String filename)
    {
        final File file = Paths.get(prependedFile(filename)).toFile();
        LOGGER.debug("Returning file {}.",file.getAbsolutePath());
        return file.exists() ? file : null;
    }

    @Override
    public void removeFile(String filename)
    {
        final File file = getFile(prependedFile(filename));System.out.println("Attempting to delete " + prependedFile(filename));
        final Boolean deleted= file.delete();
        LOGGER.debug("File {} deleted? {}", filename, deleted);
    }


    @Override
    public void close() throws IOException
    {
        FileUtils.cleanDirectory(root.toFile());
    }

    private Path copyAndMaybeDelete(Path sourcePath, String directory, Boolean delete)
    {
        Path out = null;
        final String dir = (null == directory) ? pathSep : String.format("%s%s", pathSep, directory);
        final String newFileName = String.format("%s%s%s", prependedFile(dir), pathSep,sourcePath.toFile().getName());
        try
        {
            final File source = sourcePath.toFile();
            final File dest = new File(newFileName);
            final String copyOrMove = delete ? "move" : "copy";
            final String message = String.format("Attempting to %s '%s' to '%s'.",copyOrMove,source.getAbsolutePath(), dest.getAbsolutePath());
            LOGGER.debug(message);
            FileUtils.copyFile(source,dest);
            if(delete)source.delete();
            out = dest.toPath();

        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
    private String getRootAsString()
    {
        return String.format("%s%s", root.toFile().getAbsolutePath(), pathSep);
    }
    private String prependedFile(String filename)
    {
        return filename.contains(getRootAsString()) ?
                filename : String.format("%s%s",getRootAsString(),filename);
    }

}
