package WebsiteAnalyzer;

/*
 * importing the HashSet package to store Sets of items
 */
import java.util.HashSet;

/*
 * this class is intended to store a list of all the pages that the program has discovered
 * this class will also store functions intended to manipulate the list of pages
 * such as reading, writing, modifiying
 * one instance of thie page list will exist for the entire program
 */
public class ListOfPages {

    /*
     * create an ArrayList that will store the Pages in the listOfPages
     */
    HashSet<SinglePage> List = new HashSet<SinglePage>();


    /**
     * this function will take the relative path of a new page as an input
     * the relative path will be checked against the Set of pages
     * if the relative path is found in the Set, a false will be returned
     * if the relative path is not found in the array, a true will be returned
     * @param relativePath
     * @return false if found, true otherwise
     */
    public Boolean duplicateCheck(String relativePath)
    {
        relativePath = URLToPathMapping.normalizePathSlash(relativePath);

        /*
         * search the Set with the created page
         */
        return !List.contains(new SinglePage(relativePath));
    }


    /**
     * this function will take the attributes of a page as an input
     * will add a new page to the array based on the provided inputs
     * will also do a duplication check to ensure that the provided page is not a duplicate
     * @param relativePath
     * @param numInternalImages
     * @param numExternalImages
     * @param numInternalJavaScripts
     * @param numExternalJavaScripts
     * @param numInternalCascadingStyleSheets
     * @param numExternalCascadingStyleSheets
     * @param numIntraPageLinks
     * @param numIntraSiteLinks
     * @param numExternalLinks
     */
    public void addPage(
        String relativePath, int numInternalImages, int numExternalImages,
        int numInternalJavaScripts, int numExternalJavaScripts,
        int numInternalCascadingStyleSheets, int numExternalCascadingStyleSheets,
        int numIntraPageLinks, int numIntraSiteLinks, int numExternalLinks)
    {
        relativePath = URLToPathMapping.normalizePathSlash(relativePath);

        /*
         * do a duplicate check first
         */
        if (duplicateCheck(relativePath))
        {
            /*
             * verify that the list of pages is still small enough to allow another page
             */
            if (List.size() < Website.maxNumPages)
            {
                /*
                 * create a temporary newPage object
                 */
                SinglePage newPage = new SinglePage();
    
                /*
                 * populate the newPage object with the provided attributes
                 */
                newPage.setRelativePath(relativePath);
                newPage.setnumInternalImages(numInternalImages);
                newPage.setnumExternalImages(numExternalImages);
                newPage.setnumInternalJavaScripts(numInternalJavaScripts);
                newPage.setnumExternalJavaScripts(numExternalJavaScripts);
                newPage.setnumInternalCascadingStyleSheets(numInternalCascadingStyleSheets);
                newPage.setnumExternalCascadingStyleSheets(numExternalCascadingStyleSheets);
                newPage.setnumIntraPageLinks(numIntraPageLinks);
                newPage.setnumIntraSiteLinks(numIntraSiteLinks);
                newPage.setnumExternalLinks(numExternalLinks);
    
                /*
                 * insert the new page into the Set
                 */
                List.add(newPage);
            }
            else
            {
                /*
                 * the page list is too large to be able to accept another page
                 * send an error
                 */
                CLI.throwError("Too many pages in the list!");
            }
        }
    }


    /**
     * function to bond a page to a element
     * if you find a page that links to an element, this will tie the element to the page
     * @param pageRelativePath
     * @param elementRelativePath
     * @param elementType
     */
    public void pageLinkToElement(String pageRelativePath, String elementRelativePath, String elementType)
    {
        pageRelativePath = URLToPathMapping.normalizePathSlash(pageRelativePath);
        elementRelativePath = URLToPathMapping.normalizePathSlash(elementRelativePath);
        
        /*
         * only execute the linking process if the page exists
         */
        if (List.contains(new SinglePage(pageRelativePath)))
		{
            /*
             * retrieve the specified page from the set
             */
            SinglePage tempPage = searchForPage(pageRelativePath);

            /*
             * check that something was returned
             */
            if (!tempPage.getRelativePath().equals(""))
            {
                /*
                 * add the page that is linked to the element to the set
                 */
                if (elementType.equals("Image"))
                {
                    tempPage.referencingImages.add(elementRelativePath);
                }
                else if (elementType.equals("CSS"))
                {
                    tempPage.referencingCSS.add(elementRelativePath);
                }
                else if (elementType.equals("JS"))
                {
                    tempPage.referencingJS.add(elementRelativePath);
                }

                /*
                 * re-insert the page in the set
                 */
                replacePage(tempPage);
            }
        }
    }


    /**
     * search the set for a specified path, and return the instance of that object
     * @param relativePath
     * @return SinglePage from collection
     */
    public SinglePage searchForPage(String relativePath)
    {
        relativePath = URLToPathMapping.normalizePathSlash(relativePath);
        
        if (List.contains(new SinglePage(relativePath)))
		{
            for (SinglePage Page : List)
			{
                if (Page.equals(new SinglePage(relativePath)))
				{
                    return Page;
				}
            }
        }

        /*
         * nothing was found, just return an empty page
         */
        return new SinglePage();
    }


    /**
     * replace an item in the set with a new instance of that ojbect
     * @param modifiedPage
     */
    public void replacePage(SinglePage modifiedPage)
    {
        /*
         * remove the old instance of the object
         * raplace the old instance of the object with a new object
         */
        List.remove(modifiedPage);
        List.add(modifiedPage);
    }
}
