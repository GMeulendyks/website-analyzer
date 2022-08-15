package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * this class will run general system tests
 * this will test large portions of functionality as they work together as a system,
 * as opposed to testing individual functions
 */
public class SystemTest {
    private final String baseDirectory = URLToPathMapping.normalizePathSlash(FileSystems.getDefault().getPath("src/test/resources/systemTestWebsite").normalize().toAbsolutePath().toString());

    private final int numIntraPageLinks = 3;
    private final int numIntraSiteLinks = 4;
    private final int numExternalSiteLinks = 4;

    private final ArrayList<SinglePageElement> internalJsList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("test.js")
    ));
    private final ArrayList<SinglePageElement> externalJsList = new ArrayList<>(Arrays.asList(
        new SinglePageElement(".." + "/" + "systemTestExternalWebsite" + "/" + "test.js")
    ));
    private final ArrayList<SinglePageElement> internalCssList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("test.css")
    ));
    private final ArrayList<SinglePageElement> externalCssList = new ArrayList<>(Arrays.asList(
        new SinglePageElement(".." + "/" + "systemTestExternalWebsite" + "/" + "test.css")
    ));
    private final ArrayList<SinglePageElement> internalImgList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("beans.jpg")
    ));
    private final ArrayList<SinglePageElement> externalImgList = new ArrayList<>(Arrays.asList(
        new SinglePageElement(".." + "/" + "systemTestExternalWebsite" + "/" + "beans.jpg")
    ));
    private final ArrayList<SinglePageElement> archiveList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("beans.zip")
    ));
    private final ArrayList<SinglePageElement> videoList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("supa-hot-fire.gif")
    ));
    private final ArrayList<SinglePageElement> audioList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("punch.m4a")
    ));
    private final ArrayList<SinglePageElement> uncategorizedList = new ArrayList<>(Arrays.asList(
        new SinglePageElement("fun.fun")
    ));

    private final ArrayList<SinglePage> pagesList = new ArrayList<>(Arrays.asList(
        new SinglePage("home.html"),
        new SinglePage("page1.html"),
        new SinglePage("page2.html"),
        new SinglePage("otherPages" + "/" + "page3.html")
    ));


    /*
     * Run the website analysis.
     */
    void setup() {
        Website.baseDirectory = baseDirectory;
        FileFinder.searchFiles(baseDirectory);
        SearchPage.searchPages(baseDirectory);
    }


    /*
     * Clear the results of the last website analysis.
     */
    void tearDown() {
        Website.reset();
    }

    /*
     * this function confirms that pages were found in the page list
     */
    boolean confirmPagesFound() {
        if (Website.Pages.List.size() != pagesList.size()) {
            return false;
        }

        for(SinglePage page : pagesList) {
            if (!Website.Pages.List.contains(page)) {
                return false;
            }
        }

        return true;
    }


    /*
     * this function ensures that the unit tests can create an object structure for testing, and then find it
     */
    @Test void testPageAnalysis() {
        /*
         * create the object structure
         */
        setup();

        /*
         * confirm the object structure has stuff in it
         */
        assertTrue(confirmPagesFound());

        /*
         * purge the object structure
         */
        tearDown();
    }


    /*
     * Ensure the analysis locates the correct page elements.
     */
    @Test void testPageElementAnalysis() {
        setup();

        // JS
        for(SinglePageElement element : internalJsList) {
            assertTrue(Website.JS.List.contains(element));
        }
        for(SinglePageElement element : externalJsList) {
            assertTrue(Website.JS.List.contains(element));
        }

        // CSS
        for(SinglePageElement element : internalCssList) {
            assertTrue(Website.CSS.List.contains(element));
        }
        for(SinglePageElement element : externalCssList) {
            assertTrue(Website.CSS.List.contains(element));
        }

        // Images
        for(SinglePageElement element : internalImgList) {
            assertTrue(Website.Images.List.contains(element));
        }
        for(SinglePageElement element : externalImgList) {
            assertTrue(Website.Images.List.contains(element));
        }

        // Specialty
        for(SinglePageElement element : archiveList) {
            assertTrue(Website.Archives.List.contains(element));
        }
        for(SinglePageElement element : videoList) {
            assertTrue(Website.Videos.List.contains(element));
        }
        for(SinglePageElement element : audioList) {
            assertTrue(Website.Audios.List.contains(element));
        }

        tearDown();
    }


    /*
     * Ensure the analysis setup the correct page to element relationship.
     */
    @Test void testPageToElementRelationship() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.getnumInternalJavaScripts() == internalJsList.size());
            assertTrue(page.getnumExternalJavaScripts() == externalJsList.size());
            assertTrue(page.getnumInternalCascadingStyleSheets() == internalCssList.size());
            assertTrue(page.getnumExternalCascadingStyleSheets() == externalCssList.size());
            assertTrue(page.getnumInternalImages() == internalImgList.size());
            assertTrue(page.getnumExternalImages() == externalImgList.size());
            assertTrue(page.getnumIntraPageLinks() == numIntraPageLinks);
            assertTrue(page.getnumIntraSiteLinks() == numIntraSiteLinks);
            assertTrue(page.getnumExternalLinks() == numExternalSiteLinks);
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct images.
     */
    @Test void testPageImagesReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingImages.size() == internalImgList.size() + externalImgList.size());
            for(String img : page.referencingImages) {
                SinglePageElement element = new SinglePageElement(img);
                assertTrue(internalImgList.contains(element) || externalImgList.contains(element));
            }
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct CSS.
     */
    @Test void testPageCssReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingCSS.size() == internalCssList.size() + externalCssList.size());
            for(String css : page.referencingCSS) {
                SinglePageElement element = new SinglePageElement(css);
                assertTrue(internalCssList.contains(element) || externalCssList.contains(element));
            }
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct javascript.
     */
    @Test void testPageJsReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingJS.size() == internalJsList.size() + externalJsList.size());
            for(String js : page.referencingJS) {
                SinglePageElement element = new SinglePageElement(js);
                assertTrue(internalJsList.contains(element) || externalJsList.contains(element));
            }
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct archives.
     */
    @Test void testPageArchivesReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingArchive.size() == archiveList.size());
            for(String archive : page.referencingArchive) {
                SinglePageElement element = new SinglePageElement(archive);
                assertTrue(archiveList.contains(element));
            }
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct videos.
     */
    @Test void testPageVideosReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingVideo.size() == videoList.size());
            for(String video : page.referencingVideo) {
                SinglePageElement element = new SinglePageElement(video);
                assertTrue(videoList.contains(element));
            }
        }

        tearDown();
    }


    /*
     * Ensure all pages reference the correct audio.
     */
    @Test void testPageAudiosReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingAudio.size() == audioList.size());
            for(String audio : page.referencingAudio) {
                SinglePageElement element = new SinglePageElement(audio);
                assertTrue(audioList.contains(element));
            }
        }

        tearDown();
    }

    
    /*
     * Ensure all pages reference the correct uncategorized files.
     */
    @Test void testPageUncategorizedReferenced() {
        setup();

        for (SinglePage page : Website.Pages.List) {
            assertTrue(page.referencingUncategorized.size() == uncategorizedList.size());
            for(String uncategorized : page.referencingUncategorized) {
                SinglePageElement element = new SinglePageElement(uncategorized);
                assertTrue(uncategorizedList.contains(element));
            }
        }

        tearDown();
    }
}
