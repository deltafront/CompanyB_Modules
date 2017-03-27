package companyB.workspace;

import java.io.Closeable;
import java.io.File;
import java.nio.file.Path;

/**
 * This interface defines the contract for classes that create a temporary workspace in which to contain files needed for
 * application operations.
 * @author C.A. Burrell deltafront@gmail.com
 */
public interface TemporaryWorkspace extends Closeable
{
    /**
     * Gets the root directory of this temporary workspace.
     * @return root directory of this temporary workspace.
     */
    Path getRoot();

    /**
     * Copies a file into the temporary workspace.
     * @param file File to be copied.
     * @param directory Directory in which to copy this file. If this is null, then the root of the workspace should be assumed.
     *                  The directory should have been created using the 'createDirectory' method.
     * @return Path to copied file.
     */
    Path copyFile(File file, String directory);

    /**
     * Creates a new directory within the workspace.
     * @param directory Directory to be created.
     * @return Path to the created directory.
     */
    Path createDirectory(String directory);

    /**
     *
     * Moves a file into the temporary workspace, deleting the original at the source.
     * @param file File to be moved.
     * @param directory Directory in which to move this file. If this is null, then the root of the workspace should be assumed.
     *                  The directory should have been created using the 'createDirectory' method.
     * @return Path to moved file.
     */
    Path moveFile(File file, String directory);

    /**
     * Writes content to a file within the workspace.
     * @param filename Name of the file to write this content to. If the file does not exist, it should be created.
     * @param content Content to write.
     * @return Path to the file written to.
     */
    Path writeFile(String filename, byte[]content);

    /**
     * Gets a file that is in the workspace.
     * @param filename Name of file to get.
     * @return File from workspace.
     */
    File getFile(String filename);

    /**
     * Removes a file from the workspace.
     * @param filename File to be removed.
     */
    void removeFile(String filename);
}
