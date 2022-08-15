package WebsiteAnalyzer;

import java.util.HashSet;

/*
 * this class is intended to store a list of all the page elements that the program has discovered
 * this class will also store functions intended to manipulate the list of page elements,
 * such as reading, writing, modifiying
 * one instance of this object will be created for each type of element (image, video, etc)
 */
public class ListOfPageElements {

    /*
     * create a set for the elements that will be stored in this instance
     */
    HashSet<SinglePageElement> List = new HashSet<SinglePageElement>();
 
    /**
     * this function will take the relative path of a new pageElement as an input
     * the relative path will be checked against the Set of pageElement
     * if the relative path is found in the Set, a false will be returned
     * if the relative path is not found in the set, a true will be returned
     * @param relativePath
     * @return false if item in collection, true if does not
     */
    public Boolean duplicateCheck(String relativePath)
    {
        relativePath = URLToPathMapping.normalizePathSlash(relativePath);

        /*
         * search the Set with the created page
         */
        return !List.contains(new SinglePageElement(relativePath));
    }


    /**
     * this function will take the attributes of a page element as an input
     * will add a new page element to the set based on the provided inputs
     * @param elementRelativePath
     * @param fileSizeBytes
     */
    public void addElement(String elementRelativePath, double fileSizeBytes)
    {
        elementRelativePath = URLToPathMapping.normalizePathSlash(elementRelativePath);

        /*
         * do a duplicate check first
         */
        if (duplicateCheck(elementRelativePath))
        /*
         * if the duplicate check passes, add the new page element to the set
         */
        {
            /*
             * create a temporary new element instance
             */
            SinglePageElement newElement = new SinglePageElement();

            /*
             * populate the new element with the provided attributes
             */
            newElement.setRelativePath(elementRelativePath);
            newElement.setFileSizeBytes(fileSizeBytes);

            /*
             * add the new element to the set
             */
            List.add(newElement);
        }
    }

    
    /**
     * function to bond a page to a element
     * if you find a page that links to an element, this will tie the element to the index of a page
     * @param pageRelativePath
     * @param elementRelativePath
     */
    public void pageLinkToElement(String pageRelativePath, String elementRelativePath)
    {
        // pageRelativePath = URLToPathMapping.normalizePathSlash(pageRelativePath);
        // elementRelativePath = URLToPathMapping.normalizePathSlash(elementRelativePath);
        /*
         * only execute the linking process if the element exists in the set
         */
        if (List.contains(new SinglePageElement(elementRelativePath)))
		{
            /*
             * retrieve both the specified page and the specified page element from their sets
             */
            SinglePage pageLink = Website.Pages.searchForPage(pageRelativePath);
            SinglePageElement elementLink = searchForPageElement(elementRelativePath);

            /*
             * add the page that is linked to the element to the set
             */
            elementLink.addReferencePage(pageRelativePath);

            /*
             * check that both objects actually contain something
             */
            if (elementLink.getRelativePath().equals("") && pageLink.getRelativePath().equals(""))
            {
                /*
                 * re-insert both objects back into their respective sets
                 */
                replacePageElement(elementLink);
            }
        }
    }
    

    /**
     * search the set for a specified path, and return the instance of that object
     * @param relativePath
     * @return SinglePageElement from the collection
     */
    public SinglePageElement searchForPageElement(String relativePath)
    {
        relativePath = URLToPathMapping.normalizePathSlash(relativePath);
        
        if (List.contains(new SinglePageElement(relativePath)))
		{
            for (SinglePageElement PageElement : List)
			{
                if (PageElement.equals(new SinglePageElement(relativePath)))
				{
                    return PageElement;
				}
            }
        }

        /*
         * nothing was found, just return an empty page
         */
        return new SinglePageElement();
    }


    /**
     * replace an item in the set with a new instance of that ojbect
     * @param modifiedElement
     */
    public void replacePageElement(SinglePageElement modifiedElement)
    {
        /*
         * remove the old instance of the object
         * raplace the old instance of the object with a new object
         */
        List.remove(modifiedElement);
        List.add(modifiedElement);
    }
}
