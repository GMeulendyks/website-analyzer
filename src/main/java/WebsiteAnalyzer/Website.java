package WebsiteAnalyzer;

import java.util.ArrayList;

/*
 * this class will contain all the objects that comprise the searched website
 */
public class Website
{
    /*
     * this will dictate the maximum number of pages that the program is willing to process
     * the assignment requirements set this to 1000, but we can easily refactor the program in the future with this
     * anytime you are referring to the maximum number of pages (1000) use this reference: Website.maxNumPages
     */
    final static int maxNumPages = 1000;

    /*
     * this will store the base directory of the website
     * will be populated based on the input from the user at program start
     */
    static String baseDirectory = "";

    /*
     * this will store the URL(s) provided by the user at program start
     * the URLs are used to examine if a http(s) link is internal or external
     * when the JSON file is created, these URLs will be printed near the top of the file
     */
    static ArrayList<String> inputURLS = new ArrayList<String>();

    /*
     * initialize the object that will store all the pages
     */
    static ListOfPages Pages = new ListOfPages();

    /*
     * initialize an instance of ListOfPageElements for each type of page element that the program will track
     */
    static ListOfPageElements Images = new ListOfPageElements();
    static ListOfPageElements Archives = new ListOfPageElements();
    static ListOfPageElements Videos = new ListOfPageElements();
    static ListOfPageElements Audios = new ListOfPageElements();
    static ListOfPageElements CSS = new ListOfPageElements();
    static ListOfPageElements JS = new ListOfPageElements();
    static ListOfPageElements Uncategorized = new ListOfPageElements();

    /**
     * this function will clear the object structure
     * primarily used by unit tests to get clean test data
     * takes nothing as input, returns nothing as output, clears object structure
     */
    public static void reset() {
        baseDirectory = "";
        inputURLS.clear();
        Pages = new ListOfPages();
        Images = new ListOfPageElements();
        Archives = new ListOfPageElements();
        Videos = new ListOfPageElements();
        Audios = new ListOfPageElements();
        CSS = new ListOfPageElements();
        JS = new ListOfPageElements();
        Uncategorized = new ListOfPageElements();
    }
}
