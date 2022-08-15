package WebsiteAnalyzer;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/*
 * this class contains various helper functions that will manipulate file paths, and URLs
 * also helps normalize URLs/URIs
 */
public class URLToPathMapping {

	/**
     * checks if a URL is correct without visiting the site
	 * takes a URL as an input, returns true/false based on findings
     * @param url
     * @return true if valid, false if invalid
     */
	static boolean validateURL(String url)
	{
		/*
		 * checks if malformed/contains syntax errors/is readable, throws
		 * URISyntaxException and returns false if it is
		 */
         try
		 {
			URI urlToCheck = new URI(url);
			/*
			 * convert to relative path, check if internal
			 */
			String convertedURLToCheck = URLToPathMapping.stripSiteRootURL(urlToCheck.toString());
			/*
			 * check if file pointed to exists somewhere in the website 
			 * duplicateCheck returns true if a relative path does not already exist 
			 * and false if it does exist;
			 * the return value of duplicateCheck is inverted for clarity, such that 
			 * existsInWebsite will be true if it exists in the website under analysis
			 * and false otherwise
			 */
			Boolean existsInWebsite = !(Website.Pages.duplicateCheck(convertedURLToCheck));

			/*
			 * internal and valid
			 */
			if(existsInWebsite)
			{
				return true;
			}

			/*
			 * internal and invalid
			 */
			else
			{
				/*
				 * external and invalid
				 */
				int colon = urlToCheck.toString().indexOf(':');

				if (colon < 3)
				{
					return false;
				}   
				     
				String protocol = urlToCheck.toString().substring(0, colon);

				if (!protocol.equals("http") && !protocol.equals("https") && !protocol.equals("ftp") && !protocol.equals("file"))
				{
					return false;
				}
				else if (urlToCheck.getHost() == null)
				{
					return false;
				}
				else
				{
					/**
					 * external and valid
					 */
					return true;
				}
			}

		/**
		 * internal/external and invalid
		 */
        } 
        catch (URISyntaxException e) 
        {
            return false;            
        }
    }


	/**
	 * this function strips double slashes, as well as converts all slashes to
	 * forward slashes
	 * this helps normalize file paths and directories
	 * takes a path string as an input, returns a normalized path as a string for output
	 * @param path
	 * @return string for the path with /
	 */
	public static String normalizePathSlash(String path) {
		/*
		 * find and replace all back slashes with forward slashes
		 */
		path = path.replace("\\", "/");

		/*
		 * find and replace all instances of double forward slashes
		 * continue doing this as many times as needed to get a single slash in each
		 * position
		 */
		while (path.contains("//"))
		{
			path = path.replace("//", "/");
		}
		return path;
	}


	/**
	 * @param checkPath
	 * @return true if checkPath contains the directory or URLs specified in the
	 *         CLI, false otherwise.
	 */
	public static Boolean pathContainsSiteRoot(String checkPath) {

		if (checkPath.contains(Website.baseDirectory))
		{
			return true;
		}
		return false;
	}


	/**
	 * @param checkPath
	 * @return true if checkPath contains the URLs specified in the CLI, false
	 *         otherwise.
	 */
	public static Boolean urlContainsSiteRoot(String checkPath)
	{

		for (int i = 0; i < Website.inputURLS.size(); i++)
		{

			if (checkPath.contains(Website.inputURLS.get(i)))
			{
				return true;
			}
		}

		return false;
	}


	/**
	 * this function will strip the site root directory from the absolute path of a file
	 * @param toConvert
	 * @return string of path without root dir
	 */
	public static String stripSiteRoot(String toConvert) {

		Path absToConvert = FileSystems.getDefault().getPath(toConvert);

		absToConvert = absToConvert.toAbsolutePath();

		Path absBaseDir = FileSystems.getDefault().getPath(Website.baseDirectory);

		absBaseDir = absBaseDir.toAbsolutePath();

		if (absToConvert.toString().contains(absBaseDir.toString())) {
			/*
			 * Strip site root from a directory
			 */

			int endOfStrip = absBaseDir.toString().length();

			toConvert = absToConvert.toString().substring(endOfStrip);

			toConvert = toConvert.substring(1);
			
			if(toConvert.contains("\\")){
				toConvert = toConvert.replaceAll("\\\\", "/");
				}
			return toConvert;
		}
		return toConvert;
	}


	/**
	 * this function will strip the site root URL from a specified URL by 
	 * @param toConvert
	 * @return string URL without site root
	 */
	public static String stripSiteRootURL(String toConvert)
	{
		try
		{
			URI urlToConvert = new URI(toConvert);
			
			Path absUrlToConvert = FileSystems.getDefault().getPath(urlToConvert.getRawPath()).normalize();

			absUrlToConvert = absUrlToConvert.toAbsolutePath();

			for (int i = 0; i < Website.inputURLS.size(); i++)
			{

				URI givenURL = new URI(Website.inputURLS.get(i));
				
				Path absURL = FileSystems.getDefault().getPath(givenURL.getRawPath()).normalize();

				absURL = absURL.toAbsolutePath();

				if (absUrlToConvert.toString().contains(absURL.toString()))
				{
					/*
					 * Strip site root from a URL
					 */

					int endOfStrip = absURL.toString().length();

					toConvert = absUrlToConvert.toString().substring(endOfStrip);

					toConvert = toConvert.substring(1);

					toConvert = FileSystems.getDefault().getPath(toConvert).normalize().toString();

					if(toConvert.contains("\\"))
					{
						toConvert = toConvert.replaceAll("\\\\", "/");
					}
					return toConvert;
				}
			}
		}
		catch (URISyntaxException e)
		{
			System.err.println("invalid url passed to StripSiteRootURL");
		}

		return toConvert;
	}
}
