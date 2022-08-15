package WebsiteAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * this class is intended to store the logic for searching a page
 * items to search for would include links, images, CSS, JS, etc
 */
public class SearchPage {
    static String javascriptIndicator = "<script type=\"text/javascript\" src=\"";
    static String cssIndicator = "<link rel=\"stylesheet\" href=\"";
    static String imageIndicator = "<img src=\"";
    static String linkIndicator = "<a href=\"";

    /**
     * this function will iterate through all of the discovered pages, and search them for elements
     * one page is searched at a time, and then passed to helper functions for deeper analysis
     * @param baseDirectory
     */
    public static void searchPages(String baseDirectory) {
        for (SinglePage page : Website.Pages.List) {

            try {
                BufferedReader file = new BufferedReader(new FileReader(Website.baseDirectory + "/" + page.getRelativePath()));

                /*
                 * Loop over the file one line at a time looking for relavant elements.
                 */
                String newLine = "";
                while ((newLine = file.readLine()) != null) {
                    if (newLine.contains(SearchPageHelpers.imageIndicator)) {
                        /*
                         * found image
                         */
                        SinglePageElement image = SearchPageHelpers.searchImage(newLine, page, SearchPageHelpers.imageIndicator);
                        if (image != null) {
                            page.addImage(image);
                        }
                    } 
                    else if (newLine.contains(SearchPageHelpers.javascriptIndicator)) {
                        /*
                         * found JavaScript
                         */
                        SinglePageElement element = SearchPageHelpers.searchJS(newLine, page, SearchPageHelpers.javascriptIndicator);
                        if (element != null) {
                            page.addJs(element);
                        }
                    } 
                    else if (newLine.contains(cssIndicator)) {
                        /*
                         * found CSS
                         */
                        SinglePageElement element = SearchPageHelpers.searchCSS(newLine, page, SearchPageHelpers.cssIndicator);
                        if (element != null) {
                            page.addCss(element);
                        }
                    } 
                    else if (newLine.contains(FileAnalyzer.htmlFileLinkIndicator)) {
                        /*
                         * found other files (uncategorized)
                         */
                        SinglePageElement element = SearchPageHelpers.searchOther(newLine, page);
                        if (element != null) {
                            page.addOther(element);
                        }
                    }
                    else if (newLine.contains(SearchPageHelpers.linkIndicator)) {
                        /*
                         * found link
                         */
                        SearchPageHelpers.searchLinks(newLine, page, linkIndicator);
                    } 
                }
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
