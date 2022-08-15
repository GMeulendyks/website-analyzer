package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will test the search page helpers class
 */
public class SearchPageHelpersTest {

    /*
     *  verify that validateBoundaries function is working
     *  functions will take two directory paths and compare them based on the
     *  directory the file is in
     *  if both directories are the same returns true
     *  if the two directories are different return false
     */
    @Test void validateBoundaries()
    {
		/*
		 * clear the object structure
		 */
		Website.reset();

        String baseDirectory =  "src/test/resources/systemTestWebsite";

        String internalPage = "src/test/resources/systemTestWebsite/home.html";
        String externalPage = "src/test/resources/systemTestExternalWebsite/page2.html";

        /*
         *  call validateBoundaries
         *  page1 should match and return true
         *  page2 should not match and return false
         */
        assertTrue(SearchPageHelpers.validateBoundaries(internalPage, baseDirectory),"validateBoundaries is marking an exact match as false");
        assertFalse(SearchPageHelpers.validateBoundaries(externalPage, baseDirectory),"validateBoundaries is marking two different paths as true");
    }

    /*
     * this function will test that the program is able to identify the correct file size of a specified file
     */
    @Test void testDetermineFileSizeBytes()
    {
        String testfilePath = "src/test/resources/systemTestWebsite/beans.jpg";
        double size = SearchPageHelpers.determineFileSizeBytes(testfilePath); 
        assertTrue(size == 96673.00);
    }
}
