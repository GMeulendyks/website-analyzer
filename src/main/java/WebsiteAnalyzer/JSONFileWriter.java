package WebsiteAnalyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * this class is responsible for producing a JSON output file
 */
public class JSONFileWriter extends FileWriting{

    /**
     * this function will create a JSON array of pages
     * takes in a list of pages, returns a JSON object containing the pages
     * @param Pages
     * @return the JSON for the pages
     */
    public static JsonArray makeJSONPageList(ArrayList<SinglePage> Pages)
    {
        /*
         * JSON array that will store the entire list of pages
         */
        JsonArray PageList = new JsonArray();

        /*
         * fill the PageList JSON array with all the pages that the website has
         */
        for (int i = 0; i < Pages.size(); i++)
        {
            /*
             * create temporary JSON objects/arrays to populate values into
             */
            JsonObject Page = new JsonObject();
            JsonObject imageCount = new JsonObject();
            JsonObject jsCount = new JsonObject();
            JsonObject cssCount = new JsonObject();
            JsonArray imagePaths = new JsonArray();
            JsonArray scriptPaths = new JsonArray();
            JsonArray cssPaths = new JsonArray();
            JsonObject linkCount = new JsonObject();

            /*
             * populate the image count JSON object
             */
            imageCount.addProperty("local", Pages.get(i).getnumInternalImages());
            imageCount.addProperty("external", Pages.get(i).getnumExternalImages());

            /*
             * populate the JS count JSONO object
             */
            jsCount.addProperty("local", Pages.get(i).getnumInternalJavaScripts());
            jsCount.addProperty("external", Pages.get(i).getnumExternalJavaScripts());

            /*
             * populate the CSS count JSON object
             */
            cssCount.addProperty("local", Pages.get(i).getnumInternalCascadingStyleSheets());
            cssCount.addProperty("external", Pages.get(i).getnumExternalCascadingStyleSheets());

            /*
             * populate the imagePaths JSON array
             */
            for (int k = 0; k < Pages.get(i).referencingImages.size(); k++)
            {
                imagePaths.add(Pages.get(i).referencingImages.get(k));
            }

            /*
             * populate the scriptPaths JSON array
             */
            for (int k = 0; k < Pages.get(i).referencingJS.size(); k++)
            {
                scriptPaths.add(Pages.get(i).referencingJS.get(k));
            }

            /*
             * populate the cssPaths JSON array
             */
            for (int k = 0; k < Pages.get(i).referencingCSS.size(); k++)
            {
                cssPaths.add(Pages.get(i).referencingCSS.get(k));
            }

            /*
             * populate the linkCount JSON object
             */
            linkCount.addProperty("intra-page", Pages.get(i).getnumIntraPageLinks());
            linkCount.addProperty("intra-site", Pages.get(i).getnumIntraSiteLinks());
            linkCount.addProperty("external", Pages.get(i).getnumExternalLinks());

            /*
             * add all the temporary local JSON objects to the Page JSON object
             */
            Page.addProperty("path", Pages.get(i).getRelativePath());
            Page.add("imageCount", imageCount);
            Page.add("jsCount", jsCount);
            Page.add("cssCount", cssCount);
            Page.add("imagePaths", imagePaths);
            Page.add("scriptPaths", scriptPaths);
            Page.add("cssPaths", cssPaths);
            Page.add("linkCount", linkCount);

            /*
             * add the JSON page object to the JSON page Array
             */
            PageList.add(Page);
        }
        return PageList;
    }


    /**
     * this function will create a JSON array with all of the website URLs
     * tajes in a list of URLs, returns a JSOn object containing the URLs
     * @param inputURLS
     * @return JSON array of URLs
     */
    public static JsonArray makeJSONURL(ArrayList<String> inputURLS)
    {
        /*
         * JSON array that will store all the URLs of the website
         */
        JsonArray listURL = new JsonArray();

        /*
         * fill the listURL JSON array with all the URLs that the website has
         */
        for (int i = 0; i < inputURLS.size(); i++)
        {
            listURL.add(inputURLS.get(i));
        }

        return listURL;
    }


    /**
     * this function will create a JSON array with all of the images
     * takes in a list of images, returns a JSON object containing the images
     * @param Images
     * @return JSON array of Images
     */
    public static JsonArray makeImageJSON(ArrayList<SinglePageElement> Images)
    {
        /*
         * JSON array to store all the images
         */
        JsonArray ImageList = new JsonArray();

        /*
         * fill the image JSON array with image JSON objects
         */
        for (int i = 0; i < Images.size(); i++)
        {
            /*
             * JSON object & array that one image will be comprised of
             */
            JsonObject image = new JsonObject();
            JsonArray usedOn = new JsonArray();

            /*
             * populate values into the JSON objects
             */
            image.addProperty("path", Images.get(i).getRelativePath());
            image.addProperty("pageCount", Images.get(i).getNumLinkedPages());

            /*
             * populate the usedON JSON array
             */
            for (int k = 0; k < Images.get(i).referencingPages.size(); k++)
            {
                usedOn.add(Images.get(i).referencingPages.get(k));
            }

            /*
             * add the usedOn array to the image
             */
            image.add("usedOn",usedOn);

            /*
             * add the new image JSON object to the image JSON array
             */
            ImageList.add(image);
        }

        return ImageList;
    }


    /**
     * this function will generate the various JSON arrays for the generic file types
     * such as archive, video, audio, and other (uncategorized)
     * takes in a list of elements, returns a JSON object with those elements
     * @param Elements
     * @return JSON array of Data Elements
     */
    public static JsonArray makeOtherJSON(ArrayList<SinglePageElement> Elements)
    {
        /*
         * JSON array to store everything in
         */
        JsonArray FileList = new JsonArray();

        /*
         * loop through the element list and build it
         */
        for (int i = 0; i < Elements.size(); i++)
        {
            /*
             * make a temporary file JSON object
             */
            JsonObject tempFile = new JsonObject();

            /*
             * populate the temporary JSON object with the attributes of one file
             */
            tempFile.addProperty("path", Elements.get(i).getRelativePath());
            tempFile.addProperty("size", convertFileSizeToString(Elements.get(i).getFileSizeBytes()));

            /*
             * add the newly created JSON object to the array of JSON objects
             */
            FileList.add(tempFile);
        }

        return FileList;
    }


    /**
     * this function will handle creating the output of a JSON file
     * first creates some basic objects, then calls other functions to populate them
     * once everything is populated, the objects get dumped into the output file
     * takes in the various page list and element lists, sends the data to helpers,
     * returns the output JSON file name, which is later printed and saved
     * @param PageSet
     * @param ImageSet
     * @param ArchiveSet
     * @param VideoSet
     * @param AudioSet
     * @param UncategorizedSet
     * @return creates JSON output file
     */
    public static String outputJsonFile(ListOfPages PageSet, ListOfPageElements ImageSet,
    ListOfPageElements ArchiveSet, ListOfPageElements VideoSet,
    ListOfPageElements AudioSet, ListOfPageElements UncategorizedSet)
    {
       /*
        * get the current date/time
        * add the extra bit of text and file extension
        */
        String outputFileName = (getDate() + "-summary.json");

        /*
         * convert everything into arrays to make iterating easier
         */
        ArrayList<SinglePage> Pages = new ArrayList<>(PageSet.List);
        ArrayList<SinglePageElement> Images = new ArrayList<>(ImageSet.List);
        ArrayList<SinglePageElement> Archives = new ArrayList<>(ArchiveSet.List);
        ArrayList<SinglePageElement> Videos = new ArrayList<>(VideoSet.List);
        ArrayList<SinglePageElement> Audios = new ArrayList<>(AudioSet.List);
        ArrayList<SinglePageElement> Uncategorized = new ArrayList<>(UncategorizedSet.List);

        /*
         * JSON object that will store the entire JSON document
         */
        JsonObject entireDocument = new JsonObject();

        /*
         * JSON object that will store the entire array of pages
         * call a helper function to build the JSON array
         */
        JsonArray pageList = makeJSONPageList(Pages);

        /*
         * JSON object that will store the entire array of images
         * call a helper function to build the JSON array
         */
        JsonArray imageList = makeImageJSON(Images);

        /*
         * JSON object that will store all the URLs of the website
         * call a helper function to build the JSON array
         */
        JsonArray listURL = makeJSONURL(Website.inputURLS);

        /*
         * JSON arrays that will store the generic file data
         * call a helper function to build the various JSON arrays
         * send archive, video, audio, other/uncategorized
         */
        JsonArray archive = makeOtherJSON(Archives);
        JsonArray video = makeOtherJSON(Videos);
        JsonArray audio = makeOtherJSON(Audios);
        JsonArray other = makeOtherJSON(Uncategorized);

        /*
         * package the file types into another object
         */
        JsonObject files = new JsonObject();
        files.add("archive",archive);
        files.add("video",video);
        files.add("audio",audio);
        files.add("other",other);

        /*
         * insert all of the generated JSON objects until the entireDocument
         * which will aggregate all the objects into a single large JSON structure
         * which will then be dumped into an output JSON file
         */
        entireDocument.addProperty("basePath",Website.baseDirectory);
        entireDocument.add("urls",listURL);
        entireDocument.add("pages",pageList);
        entireDocument.add("images",imageList);
        entireDocument.add("files",files);

        /*
         * touch up the formatting to be a "pretty" human readable format
         */
        Gson prettyOutput = new GsonBuilder().setPrettyPrinting().create();
        String output = prettyOutput.toJson(entireDocument);

        /*
         * dump the entire JSON document into the output file as a string
         */
        try (FileWriter outTxt = new FileWriter(outputFileName))
        {
            outTxt.write(output);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return outputFileName;
    }
}