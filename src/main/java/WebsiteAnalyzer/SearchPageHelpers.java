package WebsiteAnalyzer;

import java.io.File;
import java.nio.file.FileSystems;

/*
 * this class will store the helpers for the SearchPage class
 * handles things like finding specific page element types, adding things to objects, etc
 */
public class SearchPageHelpers extends SearchPage {

    /**
     * this function will get the absolute directory of a file, but not the file itself
     * @param pagePath
     * @return absolute directory path
     */
    private static String getPageAbsoluteFolderPath(String pagePath) {
        String absPagePath = URLToPathMapping.normalizePathSlash(FileSystems.getDefault().getPath(Website.baseDirectory + "/" + pagePath).normalize().toAbsolutePath().toString());
        int endIdx = absPagePath.lastIndexOf("/");

        if(endIdx == -1) {
            return "";
        }
        return absPagePath.substring(0, endIdx);
    }


    /**
     * this function will get the absolute path of a specified file
     * @param currFolder
     * @param line
     * @param prefixPattern
     * @return absolute file path
     */
    private static String getAbsoluteFilePath(String currFolder, String line, String prefixPattern) {
        int startIdx = line.indexOf(prefixPattern) + prefixPattern.length();
        int endIdx = line.indexOf('"', startIdx);
        
        if(startIdx == -1 | endIdx == -1) {
            return "";
        }
        String relativePathToFile = line.substring(startIdx, endIdx);
        return URLToPathMapping.normalizePathSlash(FileSystems.getDefault().getPath(currFolder + "/" + relativePathToFile).normalize().toAbsolutePath().toString());
    }


    /**
     * this function will determine if a specified file is internal, or external
     * @param absFilePath
     * @return INTERNAL or EXTERNAL classification
     */
    private static SinglePageElement.Classification getFileClassification(String absFilePath) {
        if (absFilePath.contains(Website.baseDirectory)) {
            return SinglePageElement.Classification.INTERNAL;
        } else {
            return SinglePageElement.Classification.EXTERNAL;
        }
    }


    /**
     * this function will find the absolute path of a specified external file
     * @param absFilePath
     * @return absolute external path
     */
    private static String findExternalPath(String absFilePath) {
        final String updir = ".." + "/";
        String upMovingDir = Website.baseDirectory;
        
        String relativePath = "";
        int startIdx = absFilePath.length();
        while(startIdx > 0) {
            if(!absFilePath.contains(upMovingDir)) {
                int nextDirectoryIdx = upMovingDir.lastIndexOf("/");
                if(nextDirectoryIdx == -1) {
                    return absFilePath;
                }
                upMovingDir = upMovingDir.substring(0, nextDirectoryIdx);
                relativePath += updir;
            } else {
                relativePath += absFilePath.substring(absFilePath.indexOf(upMovingDir) + upMovingDir.length() + "/".length());
                return relativePath;
            }
        }

        return absFilePath;
    }


    /**
     * this function will get the information of a specified file
     * @param line
     * @param page
     * @param prefixPattern
     * @return SinglePageElement from data found
     */
    private static SinglePageElement getFileInfo(String line, SinglePage page, String prefixPattern) {
        SinglePageElement file = new SinglePageElement();

        String currFolder = getPageAbsoluteFolderPath(page.getRelativePath());
        String absFilePath = getAbsoluteFilePath(currFolder, line, prefixPattern);
        SinglePageElement.Classification classification = getFileClassification(absFilePath);

        String relativePath;
        if (classification == SinglePageElement.Classification.INTERNAL) {
            relativePath = URLToPathMapping.stripSiteRoot(absFilePath);
        } else {
            relativePath = findExternalPath(absFilePath);
        }

        file.setRelativePath(relativePath);
        file.setFileSizeBytes(determineFileSizeBytes(absFilePath));
        file.setClassification(classification);
        file.addReferencePage(page.getRelativePath());

        return file;
    }


    /**
     * this function will search the provided string for links to images
     * @param line
     * @param currentPage
     * @param imagePrefixPattern
     * @return SinglePageElement for an Image
     */
    public static SinglePageElement searchImage(String line, SinglePage currentPage, String imagePrefixPattern) {
        SinglePageElement thisImage = getFileInfo(line, currentPage, imagePrefixPattern);
        
        if (Website.Images.duplicateCheck(thisImage.getRelativePath())){
            Website.Images.List.add(thisImage);
        }
        else {
            Website.Images.pageLinkToElement(currentPage.getRelativePath(), thisImage.getRelativePath());
        }
        
        return thisImage;
    }


    /**
     * this function will search the provided string for links to JS
     * @param line
     * @param page
     * @param jsPrefixPattern
     * @return SinglePageElement for JS
     */
    public static SinglePageElement searchJS(String line, SinglePage page, String jsPrefixPattern) {
        SinglePageElement element = getFileInfo(line, page, jsPrefixPattern);

        if (Website.JS.duplicateCheck(element.getRelativePath())) {
            Website.JS.List.add(element);
        }

        return element;
    }


    /**
     * this function will search the provided string for links to CSS
     * @param line
     * @param page
     * @param cssPrefixPattern
     * @return SinglePageElement for an CSS
     */
    public static SinglePageElement searchCSS(String line, SinglePage page, String cssPrefixPattern) {
        SinglePageElement element = getFileInfo(line, page, cssPrefixPattern);

        if (Website.CSS.duplicateCheck(element.getRelativePath())) {
            Website.CSS.List.add(element);
        }

        return element;
    }


    /**
     * this function will search the provided string for links to other pages
     * @param line
     * @param currentPage
     * @param prefixPattern
     */
    public static void searchLinks(String line, SinglePage currentPage, String prefixPattern) {
        /*
         * check if the link is a 404, ignore 404 links
         * check if the link already exists in the page list
         * do not create a new page if the link already exists
         * still need to record the type of link,
         * and store that attribute in the page that the link was found in
         */
        String currFolder = getPageAbsoluteFolderPath(currentPage.getRelativePath());
        String absFilePath = getAbsoluteFilePath(currFolder, line, prefixPattern);

        String link = line.trim().replace(prefixPattern, "");

        if (link.startsWith("#")) {
            currentPage.addIntraPageLink();
        }
        else if (absFilePath.contains(Website.baseDirectory)) {
            File file = new File(absFilePath);
            if(file.exists()) {
                currentPage.addIntraSiteLink();
            } else {
                CLI.throwMessage(currentPage.getRelativePath() + " contains an erroneous link to " + absFilePath);
            }
        }
        else {
            currentPage.addExternalLink();
        }
    }


    /**
     * Categorize the HTML link according to FileAnalyzer.FileTypes
     * @param line
     * @param page
     * @return SinglePageElement for an other data file
     */
    public static SinglePageElement searchOther(String line, SinglePage page) {
        FileAnalyzer.FileType fileType = FileAnalyzer.parseFileType(line);
        if (fileType == FileAnalyzer.FileType.ERROR) {
            return null;
        }

        String fileName = FileAnalyzer.parseFileName(line);
        if (fileName.equals("")) {
            return null;
        }

        SinglePageElement element = getFileInfo(line, page, "<a href=\"" + FileAnalyzer.htmlFileLinkIndicator);

        /*
         * Add the file to the appropriate website elements list.
         */
        switch (fileType) {
            case ERROR:
                return null;

            case ARCHIVE:
                if (Website.Archives.duplicateCheck(element.getRelativePath())) {
                    Website.Archives.List.add(element);
                }
                break;

            case VIDEO:
                if (Website.Videos.duplicateCheck(element.getRelativePath())) {
                    Website.Videos.List.add(element);
                }
                break;

            case AUDIO:
                if (Website.Audios.duplicateCheck(element.getRelativePath())) {
                    Website.Audios.List.add(element);
                }
                break;

            case UNCATEGORIZED:
                if (Website.Uncategorized.duplicateCheck(element.getRelativePath())) {
                    Website.Uncategorized.List.add(element);
                }
                break;
        }

        return element;
    }


    /**
     * this function will examine a specified file,
     * and return the file size of that file in bytes
     * @param filePath
     * @return file size in bytes
     */
    public static double determineFileSizeBytes(String filePath) {
        File file = new File(filePath);
        return file.length();
    }
    

    /**
     *  determines if the page is within the boundaries of the directory specified by user
     *  takes input of a string and returns true if the directory of the page is the same 
     *  as the directory specified by the user and false if it is not
     * @param relativePath
     * @param baseDirectory
     * @return true if within the boundaries, false otherwise
     */
    public static boolean validateBoundaries (String relativePath, String baseDirectory) {

        /*
         * convert the provided file and directory into absolute paths
         */
        String absBasePath = FileSystems.getDefault().getPath(baseDirectory).normalize().toAbsolutePath().toString();
        String absPagePath = FileSystems.getDefault().getPath(relativePath).normalize().toAbsolutePath().toString();

        /*
         * return the result saying that the file is contained inside of the website or not
         */
        return absPagePath.contains(absBasePath);
    }
}
