package WebsiteAnalyzer;

import java.util.ArrayList;


/*
 * this class is intended to store attributes about a single attribute found on a page
 * an example of an attribute might be an image, video, CSS, JavaScript
 * essentially anything found on a page that is interesting (excluding a link to a page) would be stored here
 * one instance of this object would be created for each page element that is found
 * if the program finds N page elements, it will have N instances of this object
 */
public class SinglePageElement implements Comparable<SinglePageElement>{
    /*
     * An identifier for where the element is located.
     */
    public enum Classification {
        NONE,
        INTERNAL,
        INTRAPAGE,
        INTRASITE,
        EXTERNAL
    }

    /* Variables */
    /*
     * the relative path of this element
     */
    private String relativePath;
    
    /*
     * the file size of this element in bytes
     */
    private double fileSizeBytes;
    
    /* 
     * classification of location: internal, external, intra-page, intra-site
     */
    private Classification classifcation;   
    
    /*
     * list of page indexes that reference this element
     */
    public ArrayList<String> referencingPages = new ArrayList<String>();
    
    
    /* Constructors */
    /*
     * Default (empty) constructor for SinglePageElement
     */
    public SinglePageElement()
    {
        this.fileSizeBytes = 0.0;
        this.relativePath = "";
        this.classifcation = Classification.NONE;
    }


    /**
     * constructor with only specified relativePath
     * @param relativePathInput
     */
    public SinglePageElement(String relativePathInput)
    {
        this.relativePath = relativePathInput;
        // rest is empty
        classifcation = Classification.NONE;
        fileSizeBytes = 0.0;
    }


    /**
     * constructor with all specified inputs
     * @param relativePathInput
     */
    public SinglePageElement(String relativePathInput, Classification classification, Double fileSize)
    {
        this.relativePath = relativePathInput;
        classifcation = classification;
        fileSizeBytes = fileSize;
    }


    /* Setter Methods */
    /** 
     * Set relativePath 
     * @param s (Relative Path)
     */
    public void setRelativePath(String s)
    {
        this.relativePath = s;
    }


    /** 
     * Set classification
     * @param c (Classification)
     */
    public void setClassification(Classification c)
    {
        this.classifcation = c;
    }


    /** 
     * Set fileSizeBytes
     * @param num (File Size Bytes)
     */
    public void setFileSizeBytes(double num)
    {
        this.fileSizeBytes = num;
    }


    /* Getter Methods */
    /** 
     * Get relativePath
     * @return relativePath
     */
    public String getRelativePath()
    {
        return this.relativePath;
    }
    

    /**
     * Get classifcation
     * @return classifcation
     */
    public Classification getClassification()
    {
        return this.classifcation;
    }


    /**
     * Get getFileSizeBytes
     * @return fileSizeBytes
     */
    public double getFileSizeBytes()
    {
        return this.fileSizeBytes;
    }


    /** 
     * Get numLinkedPages
     * @return size of referencingPages
     */
    public int getNumLinkedPages()
    {
        return this.referencingPages.size();
    }


    /* Functions */
    /**
     * add a page to the list of pages that reference this element
     * @param pageURI
     */
    public void addReferencePage(String pageURI) 
    {
        this.referencingPages.add(pageURI);
    }

    
    /* Overrides */  
    /**
     * Set the hashing method for SinglePageElement
     * @return int: hashCode
     */
    @Override
    public int hashCode()
    {
        if (relativePath.isEmpty())
        {
            return 0;
        }
        else
        {
            return relativePath.toLowerCase().hashCode();
        }
    }


    /**
     * Set the comparison method for SinglePageElement
     * @return boolean: true if paths match, false otherwise
     */
    @Override
    public boolean equals(Object rhs)
    {
        /*
         * make sure that the object being compared is of the correct type
         */
        if (!(rhs instanceof SinglePageElement))
        {
            return false;
        }

        /*
         * return true if there is a match
         */
        return this.relativePath.equals(((SinglePageElement) rhs).relativePath);
    }

    
    /**
     * Set the comparison of relativePaths
     * @return int
     */
    @Override
    public int compareTo(SinglePageElement RHS)
    {
        return this.relativePath.compareTo(RHS.relativePath);
    }
}
