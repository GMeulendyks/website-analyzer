package WebsiteAnalyzer;

import java.util.Random;

/*
 * THIS CLASS IS USED FOR TESTING PURPOSES
 * this class is responsible for generating a large dataset of randomized baked data
 * this is NOT used during normal program operation
 * useful for developers for testing purposes
 */
public class BakedData {
    
	/*
	 * random number generator
	 * used by the various functions in this class to generate random data
	 */
	static Random random = new Random();

	/*
	 * generate a baked dataset
	 * manually called by developers if needed for test data
	 */
	public static void generateBakedData()
	{


		/*
		 * fill the objects with random data
		 */
		for (int i = 0; i < 100; i++)
		{
			/*
			 * some of the random values need to be referenced multiple times
			 * they get stored temporarily here
			 */
			String pagePath = generateBakedFileNames();
			String imagePath = generateBakedFileNames();
			String CSSPath = generateBakedFileNames();
			String JSPath = generateBakedFileNames();

			/*
			 * generate random pages
			 */
			Website.Pages.addPage(pagePath, random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000), random.nextInt(100000));

			/*
			 * generate random images
			 */
			Website.Images.addElement(imagePath, random.nextInt(100000));

			/*
			 * generate random CSS
			 */
			Website.CSS.addElement(CSSPath, random.nextInt(100000));

			/*
			 * generate random JS
			 */
			Website.JS.addElement(JSPath, random.nextInt(100000));

			/*
			 * generate random archives
			 */
			Website.Archives.addElement(generateBakedFileNames(), random.nextInt(100000));

			/*
			 * generate random videos
			 */
			Website.Videos.addElement(generateBakedFileNames(), random.nextInt(100000));

			/*
			 * generate random videos
			 */
			Website.Audios.addElement(generateBakedFileNames(), random.nextInt(100000));

			/*
			 * generate random uncategorized
			 */
			Website.Uncategorized.addElement(generateBakedFileNames(), random.nextInt(100000));

			/*
			 * link the new random items together
			 */
			Website.Images.pageLinkToElement(pagePath, imagePath);
			Website.Images.pageLinkToElement(generateBakedFileNames(), imagePath);
			Website.Images.pageLinkToElement(generateBakedFileNames(), imagePath);

			Website.JS.pageLinkToElement(pagePath, JSPath);
			Website.JS.pageLinkToElement(generateBakedFileNames(), JSPath);
			Website.JS.pageLinkToElement(generateBakedFileNames(), JSPath);

			Website.CSS.pageLinkToElement(pagePath, CSSPath);
			Website.CSS.pageLinkToElement(generateBakedFileNames(), CSSPath);
			Website.CSS.pageLinkToElement(generateBakedFileNames(), CSSPath);

			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "Image");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "Image");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "Image");

			Website.Pages.pageLinkToElement(pagePath, JSPath, "JS");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "JS");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "JS");

			Website.Pages.pageLinkToElement(pagePath, CSSPath, "CSS");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "CSS");
			Website.Pages.pageLinkToElement(pagePath, generateBakedFileNames(), "CSS");
		}
	}


	/*
	 * this function will generate baked filenames for program testing
	 * gets called by the generateBakedData function, returns randomized file names as strings
	 */
	public static String generateBakedFileNames()
	{
		/*
		 * store the directory + filename of agenerated item
		 */
		String fileName = "/";

		/*
		 * generate a random directory
		 */
		for (int i = 0; i < 10; i++)
		{
			/*
			 * grab a random character from the lettersNumbers object, add it to the string
			 */
			fileName += CLI.allowableCharacters[random.nextInt(CLI.allowableCharacters.length)];
		}

		/*
		 * generate a random filename inside of the directory
		 */
		fileName += '/';

		for (int i = 0; i < 10; i++)
		{
			/*
			 * grab a random character from the lettersNumbers object, add it to the string
			 */
			fileName += CLI.allowableCharacters[random.nextInt(CLI.allowableCharacters.length)];
		}

		return fileName;
	}
}
