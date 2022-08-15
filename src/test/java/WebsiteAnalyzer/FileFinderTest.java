package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/*
 * this class will store the unit tests for the FileFinder functions
 */
public class FileFinderTest {
    
    /*
     * test the containsHTML() function to check if it is working correctly
     */
    @Test void containsHTML()
    {
        /*
         * grab some of the test file directories and test them
         */
        File file1 = new File("src/test/resources/exampleWebsiteDirectory/HTMLFileWithWrongExtension.txt");
        assertTrue(FileFinder.containsHTML(file1));

        File file2 = new File("src/test/resources/exampleWebsiteDirectory/normalHTMLFile.html");
        assertTrue(FileFinder.containsHTML(file2));

        File file3 = new File("src/test/resources/exampleWebsiteDirectory/NotHTMLFile.txt");
        assertFalse(FileFinder.containsHTML(file3));
    }


    /*
     * test that the searchFiles function is grabbing test files and inserting them into the page list
     */
    @Test void searchFilesTest()
    {
		/*
		 * clear the object structure
		 */
		Website.reset();

        Website.baseDirectory = "src/test/resources/exampleWebsiteDirectory/";
        /*
         * run the function being tested
         */
        FileFinder.searchFiles(Website.baseDirectory);

        /*
         * temporary page object to store search results
         */
        SinglePage testPage = new SinglePage();

        /*
         * search for some expected pages in the list, and ensure that they have showed up correctly
         */
        testPage = Website.Pages.searchForPage("normalHTMLFile.html");
        assertTrue(testPage.getRelativePath().equals("normalHTMLFile.html"));

        testPage = Website.Pages.searchForPage("subDir1/HTMLFileWithWrongExtension.txt");
        assertTrue(testPage.getRelativePath().equals("subDir1/HTMLFileWithWrongExtension.txt"));

        testPage = Website.Pages.searchForPage("NotHTMLFile.txt");
        assertFalse(testPage.getRelativePath().equals("NotHTMLFile.txt"));
    }


    /*
     * test that the pageContainsErrors function is correctly ignoring error pages
     */
    @Test void pageContainsErrorsTest()
    {
        /*
         * feed a 403 error page, a 404 error page, and a normal page to the function
         * verify that the error pages return true (contains error)
         * verify that the normal pages return false (does not contain error)
         */
        File errorFile1 = new File("src/test/resources/exampleWebsiteDirectory/HTML403Error.html");
        assertTrue(FileFinder.pageContainsError(errorFile1));

        File errorFile2 = new File("src/test/resources/exampleWebsiteDirectory/HTML404Error.html");
        assertTrue(FileFinder.pageContainsError(errorFile2));

        File normalFile1 = new File("src/test/resources/exampleWebsiteDirectory/normalHTMLFile.html");
        assertFalse(FileFinder.pageContainsError(normalFile1));
    }
}
