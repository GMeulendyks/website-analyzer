package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will test the single page element class
 */
public class SinglePageElementTest {

    /*
     * this function will test the constructor
     */
    @Test void constructorTest() 
    {
        SinglePageElement testElement = new SinglePageElement();
        assertTrue(testElement.getRelativePath() == "");
        assertTrue(testElement.getFileSizeBytes() == 0);
        assertTrue(testElement.getClassification() == SinglePageElement.Classification.NONE);
        assertTrue(testElement.referencingPages.isEmpty());

    }


    /*
     * this function will test the set relative path function
     */
    @Test void setRelativePathTest()
    {
        SinglePageElement testElement = new SinglePageElement();
        String testS = "this/is/path";
        testElement.setRelativePath(testS);
        assertTrue(testElement.getRelativePath() == testS);
        assertTrue(testElement.getFileSizeBytes() == 0);
        assertTrue(testElement.getClassification() == SinglePageElement.Classification.NONE);
        assertTrue(testElement.referencingPages.isEmpty());
    }


    /*
     * this function will test the set file size function
     */
    @Test void setFileSizeTest()
    {
        SinglePageElement testElement = new SinglePageElement();
        double testD = 19.4;
        testElement.setFileSizeBytes(testD);
        assertTrue(testElement.getRelativePath() == "");
        assertTrue(testElement.getFileSizeBytes() == testD);
        assertTrue(testElement.getClassification() == SinglePageElement.Classification.NONE);
        assertTrue(testElement.referencingPages.isEmpty());
    }
    

    /*
     * this funtion will test the file classification function
     */
    @Test void setClassificationTest()
    {
        SinglePageElement testElement = new SinglePageElement();
        SinglePageElement.Classification testS = SinglePageElement.Classification.INTERNAL;
        testElement.setClassification(testS);
        assertTrue(testElement.getRelativePath() == "");
        assertTrue(testElement.getFileSizeBytes() == 0);
        assertTrue(testElement.getClassification() == testS);
        assertTrue(testElement.referencingPages.isEmpty());
    }
    
    
    /*
     * Test of the addReferencePage function
     */
    @Test void addReferencePageTest()
    {
        // Make Element, add new page, check that counter increases, check that page was added to list
        SinglePageElement testElement = new SinglePageElement();
        String page1 = "/path1/page1";
        String page2 = "/path1/page2";

        // Test 1 adding a page
        testElement.addReferencePage(page1);
        assertTrue(testElement.referencingPages.contains(page1), "Unable to add page1 to list!");
        assertTrue(testElement.referencingPages.size() == 1);
        
        // Test 2 adding a second page
        testElement.addReferencePage(page2);
        assertTrue(testElement.referencingPages.contains(page2), "Unable to add page2 to list!");
        assertTrue(testElement.referencingPages.contains(page1), "page1 no longer in list!");
        assertTrue(testElement.referencingPages.size() == 2);
    }


    /*
     * test all the getters and setters for a single page element
     */
    @Test void gettersetterTest()
    {
        /*
         * create a local page element to test with
         */
        SinglePageElement element = new SinglePageElement();

        /*
         * loop through the various getters and setters
         * ensure that they are all working correctly
         */
        for (int i = 0; i < 10; i++)
        {
            /*
             * set the relative path
             */
            element.setRelativePath(String.valueOf(i * 1));
            assertTrue(element.getRelativePath().equals(String.valueOf(i * 1)));

            /*
             * set the file size
             */
            element.setFileSizeBytes(i * 2);
            assertTrue(element.getFileSizeBytes() == (i * 2));

            /*
             * set the classification
             */
            element.setClassification(SinglePageElement.Classification.INTERNAL);
            assertTrue(element.getClassification() == SinglePageElement.Classification.INTERNAL);

            /*
             * test comparision
             */
            assertTrue(element.equals(element));
        }
    }
}
