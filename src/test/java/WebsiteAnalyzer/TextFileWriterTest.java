package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * this class will test the text file writer class
 */
public class TextFileWriterTest {

    /*
     * verify that the text output file can be correctly populated with some pages
     * verify that the output file has all the names and file sizes correct
     */
    @Test void verifyTextFileOutput()
    {
        /*
         * create an instance of the page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * create some pages
         */
        Pages.addPage("/Zpages/page1", 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Pages.addPage("/Fpages/page2", 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Pages.addPage("/Apages/page3", 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         * convert the Page list to an array for searching and manipulation
         */
        ArrayList<SinglePage> tempSearchArray = new ArrayList<>(Pages.List);

        /*
         * populate a few random image sizes in the pages
         */
        SinglePage temp = new SinglePage();
        for (int i = 1; i < tempSearchArray.size() + 1; i++)
        {
            /*
             * pull each element out of the array, set a value, insert it back into the array
             */
            temp = tempSearchArray.get(i - 1);
            temp.PageSize += (i * 1000);
            tempSearchArray.set(i - 1, temp);
        }
        
        /*
         * print the pages to an output file
         * save the directory that the output file was dumped into for later analysis
         */
        String OutputFileName = TextFileWriter.outputTextFile(Pages);

        /*
         * open the file, read the image values, and read the total value at the end
         */
        try {
            BufferedReader inTxt = new BufferedReader(new FileReader(OutputFileName));

            String line1 = inTxt.readLine();
            String line2 = inTxt.readLine();
            String line3 = inTxt.readLine();
            String endTotalValue = inTxt.readLine();

            /*
             * close the input file when done
             */
            inTxt.close();

            /*
             * verify that the output file has all 3 images with the correct file sizes
             * verify that the files are in the epxected order to ensure sorting was correct
             */
            assertTrue(line1.equals("1000.00B /Apages/page3"));
            assertTrue(line2.equals("2.93KiB /Fpages/page2"));
            assertTrue(line3.equals("1.95KiB /Zpages/page1"));

            /*
             * verify that the end of the output file has the sum of the file sizes
             */
            assertTrue(endTotalValue.equals("5.86KiB ."));

            /*
             * verify that the output file follows the expected naming convention
             * file naming convention is:
             * 8 digits, follow by '-' followed by 6 digits, followed by "-summary.txt"
             * currently not bothering to validate the correct actual date, may or may not add that later
             */
            assertTrue(OutputFileName.matches("^[0-9]{8}-[0-9]{6}-summary.txt$"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
