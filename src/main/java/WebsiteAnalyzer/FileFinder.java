package WebsiteAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * this class is responsible for recursively searching the user specified directory
 * will open every file and directory, searching for HTML content
 */
public class FileFinder {
    
    /**
     * search everything in a directory
     * if a file is found, check if it contains HTML, if HTML is found, add the file to the pageList
     * if a directory is found, recursively call the function again with that new directory
     * takes in a file directory to be searched, returns nothing
     * @param specifiedDirectory
     */
    private static void recurvieFileSearcher(File specifiedDirectory)
    {
        /*
         * interate through everything in THIS directory
         */
        for (File fileName : specifiedDirectory.listFiles())
        {
            /*
             * if a sub-directory is found, recursively search it
             */
            if (fileName.isDirectory())
            {
                recurvieFileSearcher(fileName);
            }

            /*
             * if a non-directory file object is found, check if it contains HTML
             */
            else if (containsHTML(fileName))
            {
                /*
                 * check to make sure this is not an error page
                 * if this is NOT an error page, continue
                 */
                if (!pageContainsError(fileName))
                {
                    /*
                     * create a temporary page object with the specified filename
                     */
                    SinglePage newPage = new SinglePage();
                    newPage.setRelativePath(URLToPathMapping.normalizePathSlash(URLToPathMapping.stripSiteRoot(fileName.toString())));

                    /*
                     * add the new filename to the page list, and then continue recursively searching
                     */
                    Website.Pages.List.add(newPage);
                }
            }
            else
            {
                /*
                 * this is not a directory or an HTML contianing file
                 * additional handling may be added here later if desired/needed
                 */
            }
        }
    }


    /**
     * will setup and kick-off the recursive search for pages
     * starts in the user specified directory, moves on from there
     * takes the base directory of the website as a input, returns nothing
     * @param baseDirectoryString
     */
    public static void searchFiles(String baseDirectoryString)
    {
        /*
         * convert the user provided base directory into a file object
         */
        File baseDirectory = new File(URLToPathMapping.normalizePathSlash(baseDirectoryString));

        /*
         * send the user specified base directory for searching
         */
        recurvieFileSearcher(baseDirectory);
    }


    /**
     * search a specified file to determine if the file contains HTML data
     * currently this is a naive function that only cares about finding "<!DOCTYPE html>"
     * will likely add additional attriubtes to search for in the future
     * @param fileName
     * @return true if html content in file, false otherwise
     */
    public static Boolean containsHTML(File fileName)
    {
        /*
         * open the specified file and see if it contains anything that looks like HTML
         */
        String line1 = "";
        try {
            BufferedReader inTxt = new BufferedReader(new FileReader(fileName));
            /*
             * verify something useful can actually be read from the file
             * otherwise just return false straight away
             */
            if ((line1 = inTxt.readLine()) == null)
            {
                inTxt.close();
                return false;
            }

            /*
             * close the input file when done
             */
            inTxt.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        /*
         * make a determination if the read data from the file looks like HTML
         */
        if (line1.equals("<!DOCTYPE html>"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * this function will handle searching each new page for errors
     * if a HTTP error code 403/404 is found on the page, do not store the page
     * @param fileName
     * @return true if page contains http error, false otherwise
     */
    public static Boolean pageContainsError(File fileName)
    {
        /*
         * open the specified file
         * examine the file for error titles
         */
        try {
            BufferedReader inTxt = new BufferedReader(new FileReader(fileName));

            /*
             * loop through every line until either the end of the file, or an error is found
             */
            String searchLine = "";
            while ((searchLine = inTxt.readLine()) != null)
            {
                if (searchLine.contains("403 Access Denied"))
                {
                    inTxt.close();
                    return true;
                }
                else if (searchLine.contains("404 Not Found"))
                {
                    inTxt.close();
                    return true;
                }
            }
            /*
             * close the input file when done
             */
            inTxt.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        /*
         * the loop got to the end of the file without finding an error
         * return false, the file must not be an error page
         */
        return false;
    }
}
