package WebsiteAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * this class will handle the creation and filling of an excel output file
 */
public class ExcelFileWriter extends FileWriting{
    
    /**
     * this function will handle creating the output of a Excel file
     * takes in a ListOfPages object, which is then printed to the excel file
     * returns the Excel output filename, which is later printed and saved
     * @param PageSet (ListOfPages)
     */
    public static String outputExcelFile(ListOfPages PageSet)
    {
       /*
        * get the current date/time
        * add the extra bit of text and file extension
        */
        String outputFileName = (getDate() + "-summary.xlsx");
        
        /*
         * convert the page hashset to a arraylist
         */
        ArrayList<SinglePage> Pages = new ArrayList<>(PageSet.List);
        
        /*
         * create a worksheet object
         */
        XSSFWorkbook workbook = new XSSFWorkbook();

        /*
         * create a single sheet named summary
         */
        XSSFSheet sheet = workbook.createSheet("summary");

        /*
         * make the Excel output file
         */
        try
        {
            FileOutputStream outFile = new FileOutputStream(new File(outputFileName));

            /*
             * build the header of the Excel file
             */
            Row headerRow = sheet.createRow(0);

            Cell pageCellHeader = headerRow.createCell(0);
            pageCellHeader.setCellValue("Page");

            Cell imageCellHeader = headerRow.createCell(1);
            imageCellHeader.setCellValue("# Images");

            Cell cssCellHeader = headerRow.createCell(2);
            cssCellHeader.setCellValue("# CSS");

            Cell scriptsCellHeader = headerRow.createCell(3);
            scriptsCellHeader.setCellValue("# Scripts");

            Cell intraPageCellHeader = headerRow.createCell(4);
            intraPageCellHeader.setCellValue("# Links (Intra-Page)");

            Cell interPageCellHeader = headerRow.createCell(5);
            interPageCellHeader.setCellValue("# Links (Internal)");

            Cell externalPageCellHeader = headerRow.createCell(6);
            externalPageCellHeader.setCellValue("# Links (External)");

            /*
             * loop through all the rows of the excel sheet
             * (one page at a time)
             */
            for (int y = 0; y < Pages.size(); y++)
            {
                Row currentRow = sheet.createRow(y + 1);
                
                /*
                 * loop through all the columns of a single row
                 * (the contents of a single page at a time))
                 */
                Cell pageCell = currentRow.createCell(0);
                pageCell.setCellValue(Pages.get(y).getRelativePath());

                Cell imageCell = currentRow.createCell(1);
                imageCell.setCellValue(Pages.get(y).getnumInternalImages() + Pages.get(y).getnumExternalImages());

                Cell cssCell = currentRow.createCell(2);
                cssCell.setCellValue(Pages.get(y).getnumInternalCascadingStyleSheets() + Pages.get(y).getnumExternalCascadingStyleSheets());

                Cell scriptsCell = currentRow.createCell(3);
                scriptsCell.setCellValue(Pages.get(y).getnumInternalJavaScripts() + Pages.get(y).getnumExternalJavaScripts());

                Cell intraPageCell = currentRow.createCell(4);
                intraPageCell.setCellValue(Pages.get(y).getnumIntraPageLinks());

                Cell interPageCell = currentRow.createCell(5);
                interPageCell.setCellValue(Pages.get(y).getnumIntraSiteLinks());

                Cell externalPageCell = currentRow.createCell(6);
                externalPageCell.setCellValue(Pages.get(y).getnumExternalLinks());
            }

            /*
             * set the width nicely on the sheet
             */
            for (int i = 0; i < 7; i++)
            {
                sheet.autoSizeColumn(i, false);
            }

            /*
             * write the workbook to a file, and close the file
             */
            workbook.write(outFile);
            workbook.close();
            outFile.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputFileName;
    }
}
