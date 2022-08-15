package WebsiteAnalyzer;

import java.util.ArrayList;
import java.util.List;

/*
 * this class will analyze a specified file, and pull attributes from it
 */
public class FileAnalyzer {
    final static String htmlFileLinkIndicator = "file:///";

    final static ArrayList<String> archiveFileExtensions = new ArrayList<>(
        List.of(".zip", ".tar", ".gz", ".7z"));

    final static ArrayList<String> videoFileExtensions = new ArrayList<>(
        List.of(".mkv", ".gif", ".gifv", ".avi", ".mov", ".wmv", ".mp4"));

    final static ArrayList<String> audioFileExtensions = new ArrayList<>(
        List.of(".m4a", ".mka", ".ogg", ".wav"));

    final private static int htmlFileLinkIndicatorSize = htmlFileLinkIndicator.length();
    
    /*
     * An enum specifying the different types of file categories.
     */
    public enum FileType {
        ERROR,
        ARCHIVE,
        VIDEO,
        AUDIO,
        UNCATEGORIZED
    }

    /**
     * Given an HTML link tag, return the file name.
     * The returned string will be empty if the file name could not be parsed.
     * @param htmlLink (String)
     * @return string of the parsed file name
     */
    public static String parseFileName(String htmlLink) {
        if (!isValidHtmlFileLink(htmlLink)) {
            return "";
        }

        String fileName = parseFileName_(htmlLink);

        return fileName;
    }

    /**
     * Given a file name, return the category of file referenced or error.
     * @param name (String)
     * @return FileType
     */
    public static FileType parseFileTypeFromName(String name) {
        String fileExtension = parseFileExtension(name);
        if (fileExtension.length() == 0) {
            return FileType.ERROR;
        }

        if (archiveFileExtensions.contains(fileExtension)) {
            return FileType.ARCHIVE;
        } else if (videoFileExtensions.contains(fileExtension)) {
            return FileType.VIDEO;
        } else if (audioFileExtensions.contains(fileExtension)) {
            return FileType.AUDIO;
        } else {
            return FileType.UNCATEGORIZED;
        }
    }

    /**
     * Given an HTML link tag, return the category of file referenced or error.
     * @param htmlLink (String)
     * @return the FileType
     */
    public static FileType parseFileType(String htmlLink) {
        if (!isValidHtmlFileLink(htmlLink)) {
            return FileType.ERROR;
        }

        String fileName = parseFileName_(htmlLink);
        if (fileName.length() == 0) {
            return FileType.ERROR;
        }

        return parseFileTypeFromName(fileName);
    }

    /**
     * Check if a given html link is actually html
     * @param htmlLink (String)
     * @return true if valid html, false otherwise 
     */
    private static boolean isValidHtmlFileLink(String htmlLink) {
        return htmlLink.toLowerCase().matches(".*<a href=\"" + htmlFileLinkIndicator + ".*\">.*");
    }

    /**
     * Pare the file name
     * @param htmlLink (String)
     * @return the html link
     */
    private static String parseFileName_(String htmlLink) {
        int fileIndicatorStart = htmlLink.indexOf(htmlFileLinkIndicator);
        int fileIndicatorEnd = htmlLink.indexOf("\"", fileIndicatorStart + htmlFileLinkIndicatorSize);
        if (fileIndicatorStart == -1 || fileIndicatorEnd == -1) {
            return "";
        }

        return htmlLink.substring(fileIndicatorStart + htmlFileLinkIndicatorSize, fileIndicatorEnd);
    }

    /**
     * return the file extension of a specified file name
     * @param filename (String)
     * @return file extension
     */
    private static String parseFileExtension(String fileName) {
        if (fileName.matches("[.]*")) {
            return "";
        }

        int extensionStart = fileName.lastIndexOf('.');
        if (extensionStart == -1) {
            return "";
        }
        return fileName.substring(extensionStart);
    }
}
