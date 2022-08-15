package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will handle verifying everything in the ListOfPageElements.java section of the codebase
 */
class ListOfPagesElementsTest {

    /*
     * verify that a page element can be added to the list of page elements
     */
    @Test void addPageElementToListTest()
    {
        /*
         * create an instance of a element list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * path of the test element being created
         */
        String testElementPath = "/path1/element";

        /*
         * add a test element to the list
         */
        Images.addElement(testElementPath, 5);

        /*
         * check if the new element is in the list
         */
        assertTrue(Images.List.contains(new SinglePageElement(testElementPath)), "Unable to find the new element that was created!");
    }

    
   /*
     * verify that the duplicateCheck function is correctly when handling non-duplicate page elements
     * allowing non-duplicates is a pass
     * blocking non-duplicates is a fail
     */
    @Test void addNonDuplicatePageElement()
    {
        /*
         * create an instance of the image list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * path of the test images being created
         */
        String testElementPath1 = "/path1/image1";
        String testElementPath2 = "/path1/image2";

        /*
         * call the duplicate checking function, suggest a fresh (non duplicate) image
         * if the duplicate checking function works correctly, it should return a true
         * if the duplicate checking function is broken, it will return false
         */
        assertTrue(Images.duplicateCheck(testElementPath1), "The duplicateCheck function is incorrectly marking a valid page element as a duplicate!");

        /*
         * add the first test page to the list
         */
        Images.addElement(testElementPath1, 20);

        /*
         * call the duplicate checking function a second time, suggest another fresh (non duplicate) page
         * if the duplicate checking function works correctly, it should return a true
         * if the duplicate checking function is broken, it will return false
         */
        assertTrue(Images.duplicateCheck(testElementPath2), "The duplicateCheck function is incorrectly marking a valid page element as a duplicate!");
    }


    /*
     * verify that the duplicateCheck function is correctly when handling duplicate Images
     * blocking duplicates is a pass
     * allowing duplicates is a fail
     */
    @Test void addDuplicatePage()
    {
        /*
         * create an instance of the image list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * path of the test image being created
         */
        String testElementPath1 = "/path1/image1";

        /*
         * add a test image to the list
         */
        Images.addElement(testElementPath1, 20);

        /*
         * call the duplicate checking function, suggest a duplicate page
         * if the duplicate checking function works correctly, it will return a false
         * if the duplicate checking function is broken, it will return true
         */
        assertFalse(Images.duplicateCheck(testElementPath1), "The duplicateCheck function is failing to find duplicates!");
    }


    /*
     * verify that a page and page element can be correctly linked together correctly
     */
    @Test void pageLinkToElement()
    {
        /*
         * create an instance of a page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * create an instance of a element list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * make up some page paths
         */
        String testPagePath1 = "/path1/page1";
        String testPagePath2 = "/path1/page2";

        /*
         * make up some element paths
         */
        String testElementPath1 = "/path1/element1";
        String testElementPath2 = "/path1/element2";

        /*
         * create some new pages
         */
        Pages.addPage(testPagePath1, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Pages.addPage(testPagePath2, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        /*
         * create some new elements
         */
        Images.addElement(testElementPath1, 5);
        Images.addElement(testElementPath2, 8);

        /*
         * tie some pages to some elements
         */
        Images.pageLinkToElement(testPagePath1, testElementPath1);
        Images.pageLinkToElement(testPagePath2, testElementPath2);

        /*
         * verify that all the expected items are in their respective lists and linked together as expected
         */
        assertTrue(Images.searchForPageElement(testElementPath1).referencingPages.get(0).equals(testPagePath1));
        assertFalse(Images.searchForPageElement(testElementPath1).referencingPages.get(0).equals(testPagePath2));
        assertTrue(Images.searchForPageElement(testElementPath2).referencingPages.get(0).equals(testPagePath2));
        assertFalse(Images.searchForPageElement(testElementPath2).referencingPages.get(0).equals(testPagePath1));     
    }


    /*
     * verify that the searchForPageElement function is correctly returning the desired page element
     */
    @Test void searchForPageElementTest()
    {
        /*
         * create an instance of a element list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * create a path for the element that will be searched for
         */
        String elementPath = "path1/image1";
        /*
         * add an image to the set to be searched for later
         */
        Images.addElement(elementPath, 200);

        /*
         * search for the desired image
         * store the page element if it is found
         */
        SinglePageElement searched = Images.searchForPageElement(elementPath);

        /*
         * verify that the desired attributes are found in the returned page element
         */
        assertTrue(searched.getRelativePath().contains(elementPath), "The returned page element has the wrong relativePath!");
        assertTrue(searched.getFileSizeBytes() == 200, "The returned page element has the wrong filesize!");
    }


    /*
     * verify that the replacePageElement function is correctly replacing the desired page element
     */
    @Test void replacePageElementTest()
    {
        /*
         * create an instance of a element list
         */
        ListOfPageElements Images = new ListOfPageElements();

        /*
         * create a path for the element that will be searched for
         */
        String elementPath = "path1/image1";
        /*
         * add an image to the set to be searched for later
         */
        Images.addElement(elementPath, 200);

        /*
         * search for the desired image
         * store the page element if it is found
         */
        SinglePageElement searched = Images.searchForPageElement(elementPath);

        /*
         * modify the page element and then re-insert it into the hashset
         */
        searched.setFileSizeBytes(500);
        Images.replacePageElement(searched);

        /*
         * verify that the desired attributes are found in the returned page element
         */
        assertTrue(searched.getRelativePath().contains(elementPath), "The returned page element has the wrong relativePath!");
        assertTrue(searched.getFileSizeBytes() == 500, "The returned page element has the wrong filesize!");
    }
}
