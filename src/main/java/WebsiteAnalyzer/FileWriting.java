package WebsiteAnalyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

/*
 * this class is the driver function for producing the output files .txt, .json, and .xlsx
 * also contains some helpers for formatting things correctly, such as date/time, file sizes
 */
public class FileWriting {

    /*
     * list of file size modifiers that will be referenced when file sizes are populated into files
     */
    final private static String[] sizeNames = {"B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB"};
    
    /**
     * call all of the output functions to generate the text, excel, and JSON files
     * @param Pages
     * @param Images
     * @param Archives
     * @param Videos
     * @param Audios
     * @param CSS
     * @param JS
     * @param Uncategorized
     * takes in the various page list and element lists as inputs,
     * sends those inputs to helper functions to be output
     * returns nothing
     */
    public static void outputAllFiles(ListOfPages Pages, ListOfPageElements Images,
    ListOfPageElements Archives, ListOfPageElements Videos, ListOfPageElements Audios,
    ListOfPageElements CSS, ListOfPageElements JS, ListOfPageElements Uncategorized)
    {
		/*
		 * before generating output files, first ensure that a minimum of 1 page exists to actually be printed
		 */
		if (Pages.List.size() > 0)
		{
            /*
             * keep track of the output files that the various data goes in
             */
            String outputFileName = "";

			/*
			 * generate an output text file
             * store the resulting output file name
             * send the output file name to the user console
			 */
			outputFileName = TextFileWriter.outputTextFile(Website.Pages);
            CLI.throwMessage("Text file output file at: " + outputFileName);

			/*
			 * generate an output excel file
			 */
			outputFileName = ExcelFileWriter.outputExcelFile(Website.Pages);
            CLI.throwMessage("Excel file output file at: " + outputFileName);

			/*
			 * generate an output JSON file
			 */
			outputFileName = JSONFileWriter.outputJsonFile(Website.Pages, Website.Images, Website.Archives, Website.Videos, Website.Audios, Website.Uncategorized);
            CLI.throwMessage("JSON file output file at: " + outputFileName);
		}
		else
		{
			/*
			 * the list of pages contains nothing, generate an error
			 */
			CLI.throwError("No pages were found!");
		}
    }


    /**
     * this function will get the current date/time, which is used for naming output files
     * takes nothing as input, returns a date/time as a string
     * @return dateTime
     */
    public static String getDate()
    {
        /*
         * define how the date will be formatted
         */
        DateTimeFormatter formatDateCorrectly = DateTimeFormatter.ofPattern("uuuuMMdd-HHmmss");

        /*
         * store the now formatted date as a string
         */
        String dateTime = (formatDateCorrectly.format(LocalDateTime.now()).toString());        

        return dateTime;
    }

    
    /**
     * this function will take in a filesize as a double of bytes
     * the bytes will be converted into string of B/KiB/MiB/GiB etc...
     * takes in a double of bytes, returns the size normalized as a string
     * @param inputFileSize
     * @return file size in string format
     */
    public static String convertFileSizeToString(double inputFileSize)
    {
        /*
         * store the final output that is converted
         */
        String outputFileSize = "";

        /*
         * rounding logic
         */
        DecimalFormat rounding = new DecimalFormat("0.00");

        /*
         * loop until we figure out what the filesize should be
         */
        for (int i = 0; i < 7; i++)
        {
            if (inputFileSize < 1024)
            {
                /*
                 * the file size has been determined, add a modifier on the end
                 */
                outputFileSize = String.valueOf(rounding.format(inputFileSize)) + sizeNames[i];

                /*
                 * output the new string of B, KB, MB, etc
                 */
                return outputFileSize; 
            }
            else
            {
                /*
                 * divide the fileSize by another increment of 1024, and try the loop again
                 */
                inputFileSize /= 1024;
                if (i == 6)
                {
                    /*
                     * oops... looks like the program encountered a file too large to be processed
                     * save an error instead of a file size
                     * realistically this should never actually happen
                     */
                    outputFileSize = "You overflowed my unit converion function!";
                }
            }
        }

        /*
         * output the new string of B, KB, MB, etc
         */
        return outputFileSize; 
    }
}
