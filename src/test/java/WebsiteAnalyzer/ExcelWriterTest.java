package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * this class will test everything associated with ExcelFileWriter.java
 */
public class ExcelWriterTest {

    /*
     * verify that the Excel file output function is operating as intended
     */
    @Test void outputExcelFileTest()
    {
        /*
         * create a new instance of the page list
         */
        ListOfPages Pages = new ListOfPages();

        /*
         * add a few pages to the list of pages
         */
        Pages.addPage("/var/path/page1", 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000);
        Pages.addPage("/var/path/page2", 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 1000);

        /*
         * send the page list to the output function
         * grab the path of the created output file and store it
         */
        String outputFileName = ExcelFileWriter.outputExcelFile(Pages);

        /*
         * open the output file so that the contents can be examined
         */
        try
        {
            /*
             * setup the Excel file to be read from
             */
            FileInputStream readFile = new FileInputStream(new File(outputFileName));
            XSSFWorkbook workbook = new XSSFWorkbook(readFile);
            XSSFSheet sheet = workbook.getSheetAt(0);

            /*
             * sample the header row and ensure that it is correct
             */
            assertEquals(sheet.getRow(0).getCell(0).toString(), "Page");
            assertEquals(sheet.getRow(0).getCell(1).toString(), "# Images");
            assertEquals(sheet.getRow(0).getCell(2).toString(), "# CSS");
            assertEquals(sheet.getRow(0).getCell(3).toString(), "# Scripts");
            assertEquals(sheet.getRow(0).getCell(4).toString(), "# Links (Intra-Page)");
            assertEquals(sheet.getRow(0).getCell(5).toString(), "# Links (Internal)");
            assertEquals(sheet.getRow(0).getCell(6).toString(), "# Links (External)");

            /*
             * sample a few page values to ensure that they are correct as well
             */
            assertEquals(sheet.getRow(1).getCell(0).toString(), "/var/path/page1");
            assertEquals(sheet.getRow(1).getCell(2).toString(), "11000.0");
            assertEquals(sheet.getRow(2).getCell(0).toString(), "/var/path/page2");
            assertEquals(sheet.getRow(2).getCell(4).toString(), "8000.0");

            /*
             * close everything when done
             */
            workbook.close();
            readFile.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
        }
        
    }
}
