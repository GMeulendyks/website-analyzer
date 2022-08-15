package WebsiteAnalyzer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TextFileWriter extends FileWriting{

    /**
     * this function will sort a ListOfPage object Lexicographically
     * needed for formatting the textfile output
     * @param Pages
     * @return sorted ArrayList of pages
     */
    public static ArrayList<SinglePage> sortListOfPages(ListOfPages Pages)
    {
        ArrayList<SinglePage> tempSortArray = new ArrayList<>(Pages.List);

        Collections.sort(tempSortArray);
        return tempSortArray;
    }


    /**
     * this function will handle creating the output of a text file
     * takes a list of pages as an input, returns the text file output name as a string as an output
     * @param Pages
     * @return String for Text File output
     */
    public static String outputTextFile(ListOfPages Pages)
    {
       /*
        * get the current date/time
        * add the extra bit of text and file extension
        */
        String outputFileName = (getDate() + "-summary.txt");

        /*
         * counter to track the total size of all the pages in the list
         */
        double sizeOfAllPages = 0;

        /*
         * have the Image list sorted before sending it to the output file
         */
        ArrayList<SinglePage> sortedPages = sortListOfPages(Pages);
        
        /*
         * create an output file
         */
        try {
            BufferedWriter outTxt = new BufferedWriter(new FileWriter(outputFileName));

            /*
             * fill the output file with individual entries
             */
            for (int i = 0; i < sortedPages.size(); i++)
            {
                /*
                 * convert the double to a string that can be output
                 * write the new filesize string to the output file
                 */
                outTxt.write(convertFileSizeToString(sortedPages.get(i).PageSize));
                sizeOfAllPages += sortedPages.get(i).PageSize;

                /*
                 * add a space between the image size and the image path
                 */
                outTxt.write(" ");

                /*
                 * write the image relativePath to the file
                 */
                outTxt.write(sortedPages.get(i).getRelativePath());

                /*
                 * add a new line for the next loop to utilize
                 */
                outTxt.write("\n");
            }


            /*
             * write the total size of the entire image list to the file
             */
            outTxt.write(convertFileSizeToString(sizeOfAllPages) + " .");

            /*
             * close the output file when done
             */
            outTxt.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFileName;
    }
}
