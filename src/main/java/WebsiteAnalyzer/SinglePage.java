package WebsiteAnalyzer;

import java.util.ArrayList;

/*
 * this class is intended to be an object that will contain the attributes of a single page from a website
 * if there are N pages in a website, we will create N of these objects to represent the pages
 */
public class SinglePage implements Comparable<SinglePage>{
    /* Variables */
 
    /*
     * store the relative path of this page
     */
    private String relativePath;
    
    /*
     * counters for images on this page
     */
    private int numInternalImages;
    private int numExternalImages;
    
    /*
     * counters for scripting on this page
     */
    private int numInternalJavaScripts;
    private int numExternalJavaScripts;
    private int numInternalCascadingStyleSheets;
    private int numExternalCascadingStyleSheets;
    
    /*
     * counters for links on this page
     */
    private int numIntraPageLinks;
    private int numIntraSiteLinks;
    private int numExternalLinks;

    /*
     * arrayLists to track the elements this page links to.
     */
    ArrayList<String> referencingImages = new ArrayList<String>();
    ArrayList<String> referencingCSS = new ArrayList<String>();
    ArrayList<String> referencingJS = new ArrayList<String>();
    ArrayList<String> referencingArchive = new ArrayList<String>();
    ArrayList<String> referencingVideo = new ArrayList<String>();
    ArrayList<String> referencingAudio = new ArrayList<String>();
    ArrayList<String> referencingUncategorized = new ArrayList<String>();
    
    /*
     * size of all the images on this page combined
     * this will not get populated until the program has finished discovering everything
     * rounding will be performed once the program is ready to generate an output
     * this double will not be populated manually, the program will fill this at the end
     */
    double PageSize;

    /* Constructors */
    /**
     * Default (empty) constructor SinglePage
     */
    public SinglePage()
    {
        this.relativePath = "";
        this.numInternalImages = 0;
        this.numExternalImages = 0;
        this.numInternalJavaScripts = 0;
        this.numExternalJavaScripts = 0;
        this.numInternalCascadingStyleSheets = 0;
        this.numExternalCascadingStyleSheets = 0;
        this.numIntraPageLinks = 0;
        this.numIntraSiteLinks = 0;
        this.numExternalLinks = 0;
        this.PageSize = 0.0;
    }

    /**
     * constructor with specified relativePath
     * @param relativePathInput
     */
    public SinglePage(String relativePathInput)
    {
    this.relativePath = relativePathInput;
    numInternalImages = 0;
    numExternalImages = 0;
    numInternalJavaScripts = 0;
    numExternalJavaScripts = 0;
    numInternalCascadingStyleSheets = 0;
    numExternalCascadingStyleSheets = 0;
    numIntraPageLinks = 0;
    numIntraSiteLinks = 0;
    numExternalLinks = 0;
    PageSize = 0.0;
    }
    

    /* Setters */
    /* 
     * Set relativePath  
     */
    public void setRelativePath(String s)
    {
        this.relativePath = s;
    }

    /* 
     * Set numInternalImages  
     */
    public void setnumInternalImages(int x)
    {
        this.numInternalImages = x;
    }

    /* 
     * Set numExternalImages  
     */
    public void setnumExternalImages(int x)
    {
        this.numExternalImages = x;
    }

    /* 
     * Set numInternalJavaScripts  
     */
    public void setnumInternalJavaScripts(int x)
    {
        this.numInternalJavaScripts = x;
    }

    /* 
     * Set numExternalJavaScripts  
     */
    public void setnumExternalJavaScripts(int x)
    {
        this.numExternalJavaScripts = x;
    }

    /* 
     * Set numInternalCascadingStyleSheets  
     */
    public void setnumInternalCascadingStyleSheets(int x)
    {
        this.numInternalCascadingStyleSheets = x;
    }

    /* 
     * Set numExternalCascadingStyleSheets  
     */
    public void setnumExternalCascadingStyleSheets(int x)
    {
        this.numExternalCascadingStyleSheets = x;
    }

    /* 
     * Set numIntraPageLinks  
     */
    public void setnumIntraPageLinks(int x)
    {
        this.numIntraPageLinks = x;
    }

    /* 
     * Set numInternalImages  
     */
    public void setnumIntraSiteLinks(int x)
    {
        this.numIntraSiteLinks = x;
    }

    /* 
     * Set numExternalLinks  
     */
    public void setnumExternalLinks(int x)
    {
        this.numExternalLinks = x;
    }

    /* 
     * Set PageSize  
     */
    public void setPageSize(Double x)
    {
        this.PageSize = x;
    }

    /* Getters */
    /* 
    * Get relativePath
    */
    public String getRelativePath()
    {
        return this.relativePath;
    }

    /* 
     * get numInternalImages  
     */
    public int getnumInternalImages()
    {
        return this.numInternalImages;
    }

    /* 
     * get numExternalImages  
     */
    public int getnumExternalImages()
    {
        return this.numExternalImages;
    }

    /* 
     * get numInternalJavaScripts  
     */
    public int getnumInternalJavaScripts()
    {
        return this.numInternalJavaScripts;
    }

    /* 
     * get numExternalJavaScripts  
     */
    public int getnumExternalJavaScripts()
    {
        return this.numExternalJavaScripts;
    }

    /* 
     * get numInternalCascadingStyleSheets  
     */
    public int getnumInternalCascadingStyleSheets()
    {
        return this.numInternalCascadingStyleSheets;
    }

    /* 
     * get numExternalCascadingStyleSheets  
     */
    public int getnumExternalCascadingStyleSheets()
    {
        return this.numExternalCascadingStyleSheets;
    }

    public int getNumArchives() {
        return this.referencingArchive.size();
    }

    public int getNumVideo() {
        return this.referencingVideo.size();
    }

    public int getNumAudio() {
        return this.referencingAudio.size();
    }

    public int getNumUncategorized() {
        return this.referencingUncategorized.size();
    }

    /* 
     * get numIntraPageLinks  
     */
    public int getnumIntraPageLinks()
    {
        return this.numIntraPageLinks;
    }

    /* 
     * get numIntraSiteLinks  
     */
    public int getnumIntraSiteLinks()
    {
        return this.numIntraSiteLinks;
    }

    /* 
     * get numExternalLinks  
     */
    public int getnumExternalLinks()
    {
        return this.numExternalLinks;
    }

    /* 
     * get PageSize  
     */
    public double getPageSize()
    {
        return this.PageSize;
    }

    /* Overrides */
    /**
     * Set the hashing method for SinglePage
     * @return hashcode
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
     * Set the comparison method for SinglePage
     * @param rhs
     * @return true if object paths match, false otherwise
     */
    @Override
    public boolean equals(Object rhs)
    {
        /*
         * make sure that the object being compared is of the correct type
         */
        if (!(rhs instanceof SinglePage))
        {
            return false;
        }

        /*
         * return true if there is a match
         */
        return this.relativePath.equals(((SinglePage) rhs).relativePath);
    }


    /**
     * @return int of compared paths
     */
    @Override
    public int compareTo(SinglePage RHS)
    {
        return this.relativePath.compareTo(RHS.relativePath);
    }

    /* functions */
    /**
     * function to add image to page
     * URI & size 
     * (if size=0 -> external, else size > 0 -> internal)
     * @param image
     */
    public void addImage(SinglePageElement image)
    {
        // add image URI to list of images
        referencingImages.add(image.getRelativePath());
        // increment image count for internal or external
        // if internal then add size
        if (image.getClassification() == SinglePageElement.Classification.INTERNAL) {
            this.PageSize += image.getFileSizeBytes();
            this.numInternalImages++;
        }
        else if (image.getClassification() == SinglePageElement.Classification.EXTERNAL) {this.numExternalImages++;}   
    }


    /**
     * this function will add one JS entry to this page
     * @param js
     */
    public void addJs(SinglePageElement js) {
        if (referencingJS.contains(js.getRelativePath())) {
            return;
        }

        referencingJS.add(js.getRelativePath());
        if (js.getClassification() == SinglePageElement.Classification.INTERNAL) {
            this.numInternalJavaScripts++;
        }
        else {
            this.numExternalJavaScripts++;
        }
    }


    /**
     * this function will add one CSS entry to this page
     * @param css
     */
    public void addCss(SinglePageElement css) {
        if (referencingCSS.contains(css.getRelativePath())) {
            return;
        }

        referencingCSS.add(css.getRelativePath());
        if (css.getClassification() == SinglePageElement.Classification.INTERNAL) {
            this.numInternalCascadingStyleSheets++;
        }
        else {
            this.numExternalCascadingStyleSheets++;
        }
    }


    /**
     * this function will add one uncategorized entry to this page
     * @param other
     */
    public void addOther(SinglePageElement other) {
        FileAnalyzer.FileType fileType = FileAnalyzer.parseFileTypeFromName(other.getRelativePath());

        switch (fileType) {
            case ERROR:
                return;

            case ARCHIVE:
                if (!referencingArchive.contains(other.getRelativePath())) {
                    referencingArchive.add(other.getRelativePath());
                }
                break;

            case VIDEO:
                if (!referencingVideo.contains(other.getRelativePath())) {
                    referencingVideo.add(other.getRelativePath());
                }
                break;

            case AUDIO:
                if (!referencingAudio.contains(other.getRelativePath())) {
                    referencingAudio.add(other.getRelativePath());
                }
                break;

            case UNCATEGORIZED:
                if (!referencingUncategorized.contains(other.getRelativePath())) {
                    referencingUncategorized.add(other.getRelativePath());
                }
                break;
        }
    }

    /**
     * this function will increment number of Intra-Page links on the page by 1
     */
    public void addIntraPageLink() { numIntraPageLinks++; } 

    /**
     * this function will increment number of Intra-Site links on the page by 1
     */
    public void addIntraSiteLink() { numIntraSiteLinks++; } 

    /**
     * this function will increment number of external links on the page by 1
     */
    public void addExternalLink() { numExternalLinks++; } 
}
