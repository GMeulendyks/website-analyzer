package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/*
 * this class will handle verifying everything in the ListOfPages.java section of the codebase
 */
class ListOfPagesTest {

    /*
     * verify that the function responsible for adding pages to the list is working
     * test will add a page to the list, and then determine if the list contains the new page
     * being able to find a created page is a pass
     * being unable to find a created page is a fail
     */
    @Test void addPageToList()
    {
        /*
         * create an instance of the pagelist
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * path of the test page being created
         */
        String testPagePath = "/path1/page1";

        /*
         * add a test page to the list
         */
        Pages.addPage(testPagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    
        /*
         * check if the new test page is in the list
         * will throw an error if the new image is not in the list
         */
        assertTrue(Pages.List.contains(new SinglePage(testPagePath)), "Unable to create a new page in the page list!");
    }


    /*
     * verify that the duplicateCheck function is correctly when handling non-duplicate pages
     * allowing non-duplicates is a pass
     * blocking non-duplicates is a fail
     */
    @Test void addNonDuplicatePage()
    {
        /*
         * create an instance of the pagelist
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * path of the test pages being created
         */
        String testPagePath1 = "/path1/page1";
        String testPagePath2 = "/path1/page2";

        /*
         * call the duplicate checking function, suggest a fresh (non duplicate) page
         * if the duplicate checking function works correctly, it should return a true
         * if the duplicate checking function is broken, it will return false
         */
        assertTrue(Pages.duplicateCheck(testPagePath1), "The duplicateCheck function is incorrectly marking a valid page as a duplicate!");

        /*
         * add the first test page to the list
         */
        Pages.addPage(testPagePath1, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * call the duplicate checking function a second time, suggest another fresh (non duplicate) page
         * if the duplicate checking function works correctly, it should return a true
         * if the duplicate checking function is broken, it will return false
         */
        assertTrue(Pages.duplicateCheck(testPagePath2), "The duplicateCheck function is incorrectly marking a valid page as a duplicate!");
    }


    /*
     * verify that the duplicateCheck function is correctly when handling duplicate pages
     * blocking duplicates is a pass
     * allowing duplicates is a fail
     */
    @Test void addDuplicatePage()
    {
        /*
         * create an instance of the pagelist
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * path of the test page being created
         */
        String testPagePath = "/path1/page1";

        /*
         * add a test page to the list
         * add the same page again, which should not succeed
         */
        Pages.addPage(testPagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Pages.addPage(testPagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * assert that the page list size is 1, and not 2
         */
        assertTrue(Pages.List.size() == 1);

        /*
         * call the duplicate checking function, suggest a duplicate page
         * if the duplicate checking function works correctly, it will return a false
         * if the duplicate checking function is broken, it will return true
         */
        assertFalse(Pages.duplicateCheck(testPagePath), "The duplicateCheck function is failing to find duplicates!");
    }


    /*
     * verify that the searchForPage function is correctly returning the desired page
     */
    @Test void searchForPageTest()
    {
        /*
         * create an instance of a page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * create a path for the page that will be searched for
         */
        String pagePath = "path1/image1";

        /*
         * add a page to the set to be searched for later
         */
        Pages.addPage(pagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * search for the desired page
         * store the page if it is found
         */
        SinglePage searched = Pages.searchForPage(pagePath);

        /*
         * verify that the desired attributes are found in the returned page element
         */
        assertTrue(searched.getRelativePath().contains(pagePath), "The returned page has the wrong relativePath!");
        assertTrue(searched.getnumInternalJavaScripts() == 3, "The returned page has the wrong number of JS!");
    }


    /*
     * verify that the replacePage function is correctly replacing the desired page
     */
    @Test void replacePageTest()
    {
        /*
         * create an instance of a page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * create a path for the page that will be searched for
         */
        String pagePath = "path1/image1";

        /*
         * add a page to the set to be searched for later
         */
        Pages.addPage(pagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * search for the desired page
         * store the page if it is found
         */
        SinglePage searched = Pages.searchForPage(pagePath);

        /*
         * modify the page element and then re-insert it into the hashset
         */
        searched.setnumInternalJavaScripts(500);
        Pages.replacePage(searched);

        /*
         * verify that the desired attributes are found in the returned page element
         */
        assertTrue(searched.getRelativePath().contains(pagePath), "The returned page has the wrong relativePath!");
        assertTrue(searched.getnumInternalJavaScripts() == 500, "The returned page has the wrong number of JS!");
    }


    /*
     * verify that only an acceptable number of pages can be created
     */
    @Test void tooManyPages()
    {
        /*
         * create an instance of the page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * declare objects that can store the error stream
         */
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;

        /*
         * setup the error stream
         */
        System.setErr(new PrintStream(err));

        /*
         * create a bunch of pages
         * this should generate an error since the number of pages will be too large
         */
        for (int i = 1; i <= 1001; i++)
        {
            Pages.addPage(String.valueOf(i), (i* 100), (i * 200), (i * 300), (i * 400), (i * 500), (i * 600), (i * 700), (i * 800), (i * 900));
        }

        /*
         * if the function is working correctly, the size should stay within an acceptable threshold
         */
        assertTrue(Pages.List.size() <= 1000);

        /*
         * read the error stream and store it
         */
        System.setErr(originalErr);

        assertTrue(err.toString().contains("Too many pages in the list!"), "Was expecting a specific error message, but did not find it!");
    }


    /*
     * verify that a minimum of one page must be generated
     */
    @Test void noPages()
    {
        /*
         * create an instance of the all the various objects
         */
        ListOfPages Pages = new ListOfPages();
        ListOfPageElements Images = new ListOfPageElements();
        ListOfPageElements Archives = new ListOfPageElements();
        ListOfPageElements Videos = new ListOfPageElements();
        ListOfPageElements Audios = new ListOfPageElements();
        ListOfPageElements CSS = new ListOfPageElements();
        ListOfPageElements JS = new ListOfPageElements();
        ListOfPageElements Uncategorized = new ListOfPageElements();

        /*
         * declare objects that can store the error stream
         */
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;

        /*
         * setup the error stream
         */
        System.setErr(new PrintStream(err));

        /*
         * attempt to generate output files despite not having any pages stored
         * this is supposed to generate an error
         */
        FileWriting.outputAllFiles(Pages, Images, Archives, Videos, Audios, CSS, JS, Uncategorized);

        /*
         * read the error stream and store it
         */
        System.setErr(originalErr);

        assertTrue(err.toString().contains("No pages were found!"), "Was expecting a specific error message, but did not find it!");
    }
}
