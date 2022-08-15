package WebsiteAnalyzer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * this class is intended to handle interactions with the user
 * other functions here will handle input related actions, such as input validation
 * this will also trigger error messages back to the user if an unacceptable condition is encountered
 */
public class CLI {

	/*
	 * pre-determined set of characters that should appear in a filename
	 */
	final static char allowableCharacters[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
	'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
	'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
	'7', '8', '9', '-', '_', '.', '+'};

	/**
	 * this function will validate input from the user as an array of strings
	 * if an input is invalid, the input will be sent back to the user, and an error will be displayed
	 * to err out.
	 * the program will automatically terminate upon an input validation failure
	 * @param arguments given from command line
	 * @return true if inputs match those given in requirements definition(directory, url1, url2,...),
	 * false otherwise
	 */
	final public static boolean inputValidation(final String[] userInput) 
	{
		/*
		 * check if no arguements are provided by the user
		 */
		if (userInput.length == 0)
		{
			throwError("No arguments provided");
			return false;
		}
		else
		{
			/*
			 * validate the provided inputs
			 */
			for(int i = 0; i < userInput.length; i++)
			{
				/*
				 * check if URL is malformed
				 */
				if(i > 0)
				{
					try
					{
						URL testURL = new URL(userInput[i]);
						/*
						 *  double-check if URL is malformed
						 */
						try
						{
							testURL.toURI();	
						}
						catch (URISyntaxException e)
						{
							throwError("Malformed URI in arguments");
							return false;
						}

					}
					catch (MalformedURLException e)
					{
						throwError("Malformed URL in arguments");
						return false;
					}
				}

				final Path tempPath = Paths.get(userInput[0]);
				
				/*
				 * Check if directory exists
				 */
				if(Files.exists(tempPath) != true)
				{
					throwError("Directory does not exist");
					return false;
				};

				/*
				 * Check if directory contains less than 1 page
				 */
				try(DirectoryStream<Path> tempDir = Files.newDirectoryStream(tempPath))
				{
					/*
					 * IO Exception
					 */
					if(tempDir.iterator().hasNext() != true)
					{
						throwError("Directory does not contain any pages");
						return false;
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		/*
		 * no errors found during the input validation, return true
		 */
		return true;
	}

	
	/**
	 * this function will store the inputs from the user
	 * takes in an array of strings as the user input, returns nothing
	 * @param args
	 */
	public static void storeUserInput(String args[])
	{
		/*
		 * store the now validated user provided base directory
		 * only do this if the args array has something in it
		 */
		if (args.length > 0)
		{
			Website.baseDirectory = URLToPathMapping.normalizePathSlash(args[0]);
		}
		
		/*
		 * loop through the input URLs and store them
		 * only do this is the args array has any URLs to store
		 */
		if (args.length > 1)
		{
			for (int i = 1; i < args.length; i++)
			{
				Website.inputURLS.add(args[i]);
			}
		}
	}


	/**
	 * this function will throw a specified error
	 * takes in an error as a string, sends the error to the user, returns nothing
	 * @param errorMessage
	 */
	public static void throwError(String errorMessage)
	{
		/*
		 * print the specified error to where-ever it is supposed to go
		 */
		System.err.println("Error encountered: " + errorMessage + " Program must terminate!");
	}


	/**
	 * this function will send information to the user
	 * takes a message in as a string, sends it to the user, returns nothing
	 * @param message
	 */
	public static void throwMessage(String message)
	{
		/*
		 * send the message to the user
		 */
		System.out.println(message);
	}

	/**
	 * Main Function
	 * takes in user arguements as an array of strings, returns nothing
	 * @param args
	 */
	public static void main(String[] args)
	{
		/* 
		 * send the user input to inputValidation
		 * this will generate errors based on the user specified data
		 * do not continue the program if input validation fails
		 */
		if (inputValidation(args))
		{
			/*
			 * store the now validated user input(s)
			 */
			storeUserInput(args);

			/*
			 * search the user specified directory for HTML files recursively
			 */
			FileFinder.searchFiles(Website.baseDirectory);

			/*
			 * parse throught the above list of HTML pages
			 * search each page for various elements such as images, CSS, etc
			 */
			SearchPage.searchPages(Website.baseDirectory);

			/*
			 * now that all the data has been harvested, send it all to be output
			 * TXT, XLSX, JSON output files
			 */
			FileWriting.outputAllFiles(Website.Pages, Website.Images, Website.Archives, Website.Videos, Website.Audios, Website.CSS, Website.JS, Website.Uncategorized);
		}
	}
}
