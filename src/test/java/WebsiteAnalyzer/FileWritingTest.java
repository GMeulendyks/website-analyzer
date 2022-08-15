package WebsiteAnalyzer;

/*
 * these are used for performing unit tests
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will handle verifying everything in the FileWriting.java section of the codebase
 */
public class FileWritingTest {

    /*
     * verify that the helper function that converts double fileSizes to strings is working
     * should take in a double like 5.2, and convert that to a string like "5.2B"
     */
    @Test void convertFileSizeToStringTest()
    {

        /*
         * try a few different file size samples, and ensure the appropriate responses come back
         * check for some things that would be expected to be true
         */
        assertTrue(FileWriting.convertFileSizeToString(5).equals("5.00B"), "Wrong filesize returned!");
        assertTrue(FileWriting.convertFileSizeToString(1024).equals("1.00KiB"), "Wrong filesize returned!");
        assertTrue(FileWriting.convertFileSizeToString(100000).equals("97.66KiB"), "Wrong filesize returned!");
        assertTrue(FileWriting.convertFileSizeToString(10000000).equals("9.54MiB"), "Wrong filesize returned!");

        /*
         * check for some things that would be expected to be false
         */
        assertFalse(FileWriting.convertFileSizeToString(100).equals("9.54MiB"), "Wrong filesize returned!");
        assertFalse(FileWriting.convertFileSizeToString(10000).equals("9.54MiB"), "Wrong filesize returned!");
        assertFalse(FileWriting.convertFileSizeToString(1000000).equals("9.54MiB"), "Wrong filesize returned!");
        assertFalse(FileWriting.convertFileSizeToString(1000000000).equals("9.54MiB"), "Wrong filesize returned!");
    }
}
