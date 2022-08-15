package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will test the functionality of the single page class
 */
class SinglePageTest {
   /*
    * test the add image function
    */
   @Test void addImageTest()
   {
      /*
       * create a test page
       */
      SinglePage pageTest = new SinglePage();
      
      /*
       * create some test page elements
       */
      SinglePageElement imageInternal1 = new SinglePageElement("image/internal1", SinglePageElement.Classification.INTERNAL, 5.6);
      SinglePageElement imageInternal2 = new SinglePageElement("image/internal2", SinglePageElement.Classification.INTERNAL, 6.8);
      SinglePageElement imageExternal = new SinglePageElement("image/external", SinglePageElement.Classification.EXTERNAL, 10.5);

      pageTest.addImage(imageInternal1);
      assertTrue(pageTest.getnumInternalImages() == 1);
      assertTrue(pageTest.getnumExternalImages() == 0);
      assertTrue(pageTest.getPageSize() == imageInternal1.getFileSizeBytes());
      assertTrue(pageTest.referencingImages.contains(imageInternal1.getRelativePath()));   

      pageTest.addImage(imageInternal2);
      assertTrue(pageTest.getnumInternalImages() == 2);
      assertTrue(pageTest.getnumExternalImages() == 0);
      assertTrue(pageTest.getPageSize() == imageInternal1.getFileSizeBytes() + imageInternal2.getFileSizeBytes());
      assertTrue(pageTest.referencingImages.contains(imageInternal1.getRelativePath()));
      assertTrue(pageTest.referencingImages.contains(imageInternal2.getRelativePath())); 

      pageTest.addImage(imageExternal);
      assertTrue(pageTest.getnumInternalImages() == 2);
      assertTrue(pageTest.getnumExternalImages() == 1);
      assertTrue(pageTest.getPageSize() == imageInternal1.getFileSizeBytes() + imageInternal2.getFileSizeBytes());
      assertTrue(pageTest.referencingImages.contains(imageInternal1.getRelativePath())); 
      assertTrue(pageTest.referencingImages.contains(imageInternal2.getRelativePath())); 
      assertTrue(pageTest.referencingImages.contains(imageExternal.getRelativePath())); 
   }  


   /*
    * test all the getters and setters for a single page
    */
   @Test void gettersSettersTest()
   {
      /*
       * clean the object structure
       */
      Website.reset();

      /*
       * create a single page to test with
       */
      SinglePage page = new SinglePage();

      /*
       * generate some random single pages
       * ensure the getters and setters perform as intended
       */
      for (int i = 0; i < 10; i++)
      {
         /*
          * set and get the relative path
          */
         page.setRelativePath(String.valueOf(i * 1));
         assertTrue(page.getRelativePath().equals(String.valueOf(i * 1)));

         /*
          * set and get numInternalImages
          */
         page.setnumInternalImages(i * 2);
         assertTrue(page.getnumInternalImages() == (i * 2));

         /*
          * set and get numExternalImages
          */
         page.setnumExternalImages(i * 3);
         assertTrue(page.getnumExternalImages() == (i * 3));

         /*
          * set and get numInternalJavaScripts
          */
         page.setnumInternalJavaScripts(i * 4);
         assertTrue(page.getnumInternalJavaScripts() == (i * 4));

         /*
          * set and get numExternalJavaScripts
          */
         page.setnumExternalJavaScripts(i * 5);
         assertTrue(page.getnumExternalJavaScripts() == (i * 5));

         /*
          * set and get numInternalCascadingStyleSheets
          */
         page.setnumInternalCascadingStyleSheets(i * 6);
         assertTrue(page.getnumInternalCascadingStyleSheets() == (i * 6));

         /*
          * set and get numExternalCascadingStyleSheets
          */
         page.setnumExternalCascadingStyleSheets(i * 7);
         assertTrue(page.getnumExternalCascadingStyleSheets() == (i * 7));

         /*
          * set and get numIntraPageLinks
          */
         page.setnumIntraPageLinks(i * 8);
         assertTrue(page.getnumIntraPageLinks() == (i * 8));

         /*
          * set and get numIntraSiteLinks
          */
         page.setnumIntraSiteLinks(i * 9);
         assertTrue(page.getnumIntraSiteLinks() == (i * 9));

         /*
          * set and get numExternalLinks
          */
         page.setnumExternalLinks(i * 10);
         assertTrue(page.getnumExternalLinks() == (i * 10));    
         
         /*
          * set and get the page size
          */
         page.setPageSize(i * 2.1);
         assertTrue(page.getPageSize() == (i * 2.1));

         /*
          * link some elements
          */
         page.addOther(new SinglePageElement(String.valueOf("file.mp4")));
         page.addOther(new SinglePageElement(String.valueOf("file.zip")));
         page.addOther(new SinglePageElement(String.valueOf("file.wav")));
         page.addOther(new SinglePageElement(String.valueOf("file.xyz")));

         /*
          * get counts
          */
         assertTrue(page.getNumArchives() > 0);
         assertTrue(page.getNumVideo() > 0);
         assertTrue(page.getNumAudio() > 0);
         assertTrue(page.getNumUncategorized() > 0);
      }
   }
}