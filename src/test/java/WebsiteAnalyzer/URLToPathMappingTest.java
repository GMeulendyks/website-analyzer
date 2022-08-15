package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will test the URL to path mapping class
 */
public class URLToPathMappingTest {

	/*
	 *
	 */
	@Test public void testURLToPathMapping()
	{
		/*
		 * clear the object structure
		 */
		Website.reset();
		
		/*
		 * Strip site root of directory given arguments like in Kennedy's example
		 */
		Website.baseDirectory = "/home/tkennedy/cs350/sum19/";
		
		Website.inputURLS.add("https://www.cs.odu.edu/~tkennedy/cs350/sum19/");
		Website.inputURLS.add("https://www.cs.odu.edu/~tkennedy/cs350/sum19/");	
		
		String testString1 = "https://www.cs.odu.edu/~tkennedy/cs350/sum19/Directory/outline/index.html";
		String testString2 = "https://www.cs.odu.edu/~tkennedy/cs350/sum19/Directory/outline/index.html";
		
		String expected ="Directory/outline/index.html";
		
		testString1 = URLToPathMapping.stripSiteRootURL(testString1);
		testString2 = URLToPathMapping.stripSiteRootURL(testString2);
		
		assertTrue(testString1.equals(expected));
	    assertTrue(testString2.equals(expected));
		
		Website.baseDirectory = "./src/test/resources/exampleWebsiteDirectory/";

		String testString3 = "./src/test/resources/exampleWebsiteDirectory/subDir1/normalHTMLFile.txt";
		
		String expected1 = "subDir1/normalHTMLFile.txt";
		testString3= URLToPathMapping.stripSiteRoot(testString3);

		assertTrue(testString3.equals(expected1));

	}

	@Test void validateURL()
    {
        /**
         * valid url passed
         */

         boolean isValid = false;
         String testURL1 = "https://www.wikipedia.org/";
         isValid = URLToPathMapping.validateURL(testURL1);
         assertTrue(isValid);
        

         /**
         * misspelled url passed
         */
        String testURL2 = "http//google";
        isValid = URLToPathMapping.validateURL(testURL2);
        assertFalse(isValid);

        String testURL3 = "htp://google";
        isValid = URLToPathMapping.validateURL(testURL3);
        assertFalse(isValid);

    }
}
