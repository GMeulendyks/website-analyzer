package WebsiteAnalyzer;

/*
 * these are used for performing unit tests
 */
import org.junit.jupiter.api.Test;
import com.google.gson.JsonArray;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * this class will test the JSON file writer class
 */
public class JSONFileWriterTest {

    /*
     * verify that the URL list creation function is working
     */
    @Test void makeJSONURLTest()
    {
        /*
         * create a url list containing a few URLs
         */
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("https://cs.odu.edu");
        urls.add("https://example.odu.edu");

        /*
         * send the URL list to the function to be tested
         * store the results in a JSON array to be examined later
         */
        JsonArray listURL = JSONFileWriter.makeJSONURL(urls);

        /*
         * examine the JSON array, make sure the expected results are in there
         */
        assertEquals(listURL.toString(), "[\"https://cs.odu.edu\",\"https://example.odu.edu\"]");
    }


    /*
     * verify that the image JSON creation function is working
     */
    @Test void makeImageJSONTest()
    {
        /*
         * create a list of Image objects
         */
        ArrayList<SinglePageElement> Images = new ArrayList<SinglePageElement>();

        /*
         * make up some data to store in the image list
         */
        SinglePageElement image1 = new SinglePageElement();
        image1.setRelativePath("/var/path1/image1.jpg");
        image1.setFileSizeBytes(45000);
        SinglePageElement image2 = new SinglePageElement();
        image2.setRelativePath("/var/path2/image2.jpg");
        image2.setFileSizeBytes(1500000);

        /*
         * put the images in the image list
         */
        Images.add(image1);
        Images.add(image2);

        /*
         * send the image list to the function to be tested
         * store the returned information
         */
        JsonArray imageList = JSONFileWriter.makeImageJSON(Images);

        /*
         * verify that the output of the function matches what would be expected
         */
        assertEquals(imageList.toString(), "[{\"path\":\"/var/path1/image1.jpg\",\"pageCount\":0,\"usedOn\":[]},{\"path\":\"/var/path2/image2.jpg\",\"pageCount\":0,\"usedOn\":[]}]");
    }


    /*
     * test the JSON function that makes a list of uncategorized files
     */
    @Test void makeOtherJSON()
    {
        /*
         * create a list of videos
         */
        ArrayList<SinglePageElement> Videos = new ArrayList<SinglePageElement>();

        /*
         * create some video objects
         * populate them with some data
         */
        SinglePageElement video1 = new SinglePageElement();
        video1.setRelativePath("/var/path1/video1.jpg");
        video1.setFileSizeBytes(45000);
        SinglePageElement video2 = new SinglePageElement();
        video2.setRelativePath("/var/path2/video2.jpg");
        video2.setFileSizeBytes(1500000);

        /*
         * add the videos to the list
         */
        Videos.add(video1);
        Videos.add(video2);

        /*
         * send the video data to the function to test it
         * save the returned JSON array
         */
        JsonArray video = JSONFileWriter.makeOtherJSON(Videos);

        /*
         * verify that the JSON array is formatted as expected
         */
        assertEquals(video.toString(), "[{\"path\":\"/var/path1/video1.jpg\",\"size\":\"43.95KiB\"},{\"path\":\"/var/path2/video2.jpg\",\"size\":\"1.43MiB\"}]");
    }


    /*
     * generate a small JSON file and verify the output is formatted as desired
     */
    @Test void JSONOutputTest()
    {
        /*
         * create instances of the various objects
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
         * create and store the paths to various objects
         */
        String pagePath = "/directory/image1";
        String imagePath = "/directory/image1";
        String CSSPath = "/directory/CSS1";
        String JSPath = "/directory/JS1";
        String archivePath = "/directory/archive1";
        String videoPath = "/directory/video1";
        String audioPath = "/directory/audio1";
        String UncategorizedPath = "/directory/uncategorized1";

		/*
		 * clear the object structure
		 */
		Website.reset();
        
        /*
         * generate some information
         */
        Website.inputURLS.add("https://www.example.odu.edu");
        Website.inputURLS.add("https://cs.example.odu.edu");
        Website.baseDirectory = "/var/www/";

        /*
         * generate a page
         */
        Pages.addPage(pagePath, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * generate a image
         */
        Images.addElement(imagePath, 10);

        /*
         * generate a CSS
         */
        CSS.addElement(CSSPath, 20);

        /*
         * generate a JS
         */
        JS.addElement(JSPath, 30);

        /*
         * generate a archive
         */
        Archives.addElement(archivePath, 40);

        /*
         * generate video
         */
        Videos.addElement(videoPath, 50);

        /*
         * generate video
         */
        Audios.addElement(audioPath, 60);

        /*
         * generate a uncategorized
         */
        Uncategorized.addElement(UncategorizedPath, 70);

        /*
         * link the new items together
         */
        Images.pageLinkToElement(pagePath, imagePath);
        JS.pageLinkToElement(pagePath, JSPath);
        CSS.pageLinkToElement(pagePath, CSSPath);
        Pages.pageLinkToElement(pagePath, imagePath, "Image");
        Pages.pageLinkToElement(pagePath, JSPath, "JS");
        Pages.pageLinkToElement(pagePath, CSSPath, "CSS");

        /*
         * generate the output JSON file
         * store the path of the new test file, and the known good file
         */
        String outputFileName = JSONFileWriter.outputJsonFile(Pages, Images, Archives, Videos, Audios, Uncategorized);
        String knownGoodJSONFile = "src/test/resources/knownGoodJSON.json";

        try {
             /*
              * open the new JSON file, and the known good JSON file
              */
            BufferedReader testingJSONFile = new BufferedReader(new FileReader(outputFileName));
            BufferedReader knownJSONFile = new BufferedReader(new FileReader(knownGoodJSONFile));

            /*
             * loop through both files at the same time, compare them line by line
             */
            for (int i = 0; i < 73; i++)
            {
                /*
                 * compare line 'i' from both files, assertTrue if they are the same
                 */
                assertTrue(testingJSONFile.readLine().equals(knownJSONFile.readLine()), "Found a mismatch between the two files at line: " + i);
            }

            /*
             * close the input files when done
             */
            testingJSONFile.close();
            knownJSONFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
