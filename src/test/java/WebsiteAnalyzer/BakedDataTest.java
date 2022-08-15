package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


/*
 * this class exists to perform unit tests against the BakedData class
 * this is not important to the main codebase, however it does assist developers for testing
 */
public class BakedDataTest {
    
    @Test void generateBakedFileNamesTest()
    {        
        /*
         * array to store some of the random generated filenames in
         */
        ArrayList<String> randomNames = new ArrayList<String>();

        /*
         * call the random filename function, see if it gives some random file names
         */
        for (int i = 0; i < 10; i++)
        {
            /*
             * generate a bunch of random file names and store them
             */
            randomNames.add(BakedData.generateBakedFileNames());
        }

        /*
         * examine the new array of random file names,
         * ensure that the array contains random entries
         */
        for (int i = 0; i < randomNames.size(); i++)
        {
            for (int j = 0; j < randomNames.size(); j++)
            {
                /*
                 * don't accidentally check the same entry against itself
                 */
                if (i != j)
                {
                    assertFalse(randomNames.get(i).equals(randomNames.get(j)));
                }
            }
        }
    }


    /*
     * test the baked data function
     * ensure that it creates some random content
     */
    @Test void generateBakedDataTest()
    {
        /*
         * call the generate baked data function and have it create some data
         */
        BakedData.generateBakedData();

        /*
         * check that all the file structures contain a bunch of data
         */
        assertTrue(Website.Pages.List.size() > 50);
        assertTrue(Website.Images.List.size() > 50);
        assertTrue(Website.Archives.List.size() > 50);
        assertTrue(Website.Videos.List.size() > 50);
        assertTrue(Website.Audios.List.size() > 50);
        assertTrue(Website.CSS.List.size() > 50);
        assertTrue(Website.JS.List.size() > 50);
        assertTrue(Website.Uncategorized.List.size() > 50);

        /*
         * clear the data structures when done
         */
        Website.reset();
    }
}
