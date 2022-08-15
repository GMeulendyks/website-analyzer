package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * this class will handle verifying everything in the CLI.java section of the codebase
 */
public class CLITest {

	/*
	 * this will test the various input validation operations
	 */
	@Test void testInputValidation()
	{
		/*
		 * setup the error stream sampling
		 */
		 final ByteArrayOutputStream errs = new ByteArrayOutputStream();
		 final PrintStream saveErrs = System.err;
		 final ByteArrayOutputStream outs = new ByteArrayOutputStream();
		 
		/*
		 * Test empty arguments
		 */
		System.setErr(new PrintStream(errs));
		
		String expected0 = "Error encountered: " + "No arguments provided" + " Program must terminate!";
		String temp0[] = new String[0];
		CLI.inputValidation(temp0);
		System.setErr(saveErrs);
		assertTrue(errs.toString().contains(expected0));

		/*
		 * Test existing directory with pages
		 */
		System.setOut(new PrintStream(outs));
		String expected1 = "";
		Path testDir1 = Paths.get("./src/test/resources/data/testDirectory1/");
		testDir1 = testDir1.toAbsolutePath();
		testDir1 = testDir1.normalize();
		String temp1[] = {testDir1.toString()};
		CLI.inputValidation(temp1);
		assertTrue(outs.toString().equals(expected1));
		
		/*
		 * Test empty directory
		 * this test fails if the destination directory does contain any pages
		 * 
		 */
		System.setErr(new PrintStream(errs));
		
		String expected2 = "Error encountered: " + "Directory does not contain any pages" + " Program must terminate!";
		Path testDir2 = Paths.get("./src/test/resources/data/testDirectory2");
		try {
			Files.createDirectories(testDir2);
		} catch (IOException e) {
		
			System.err.println("Failed to make test directory.");

		}
		testDir2 = testDir2.toAbsolutePath();
		String temp2[] = {testDir2.toString()};
		CLI.inputValidation(temp2);
		System.setErr(saveErrs);
		assertTrue(errs.toString().contains(expected2));
		try {
			Files.deleteIfExists(testDir2);
		} catch (IOException e) {
			System.err.println("Failed to delete test directory.");

		}
		/*
		 * Test nonexistent/readable directory
		 */		
		System.setErr(new PrintStream(errs));
		
		String expected3 = "Error encountered: " + "Directory does not exist" + " Program must terminate!";
		Path testDir3 = Paths.get("./src/test/resources/data/testDirectory5000/");
		String temp3[] = {testDir3.toString()};
		CLI.inputValidation(temp3);
		System.setErr(saveErrs);
		assertTrue(errs.toString().contains(expected3));		
		
		/*
		 * Test valid directory followed by malformed URL provided as argument
		 */		
		System.setErr(new PrintStream(errs));
	
		Path testRootDir0 = Paths.get("./src/test/resources/data/testDirectory1/");
		String testURL = "htt://www.cs.odu.edu/~tkennedy/cs350/sum19/Directory/outline/index.html";
		
		String expected4 = "Error encountered: " + "Malformed URL in arguments" + " Program must terminate!";

		testRootDir0 = testRootDir0.toAbsolutePath();
		testRootDir0 = testRootDir0.normalize();
		String tempURLs1[] = {testRootDir0.toString(), testURL};
		CLI.inputValidation(tempURLs1);
		assertTrue(errs.toString().contains(expected4));

	
	}
	

	/*
	 * this will test the program ability to store a user specified input
	 */
	@Test void storeUserInputTest()
	{
		/*
		 * create and store some strings for user inputs
		 */
		String args[] = {"/var/example/dir/", "https://odu.edu", "https://cs.odu.edu", "http://odu.edu"};

		/*
		 * clear the object structure
		 */
		Website.reset();

		/*
		 * feed the user inputs in
		 */
		CLI.storeUserInput(args);

		/*
		 * verify that the user inputs have been correctly parsed into the appropriate locations
		 */
		assertEquals(Website.baseDirectory, args[0]);
		assertEquals(Website.inputURLS.get(0), args[1]);
		assertEquals(Website.inputURLS.get(1), args[2]);
		assertEquals(Website.inputURLS.get(2), args[3]);
	}


	/*
	 * try running the entire program from the start with a valid input
	 */
	@Test void entireProgramTest()
	{
		/*
		 * clean the object structure first
		 */
      	Website.reset();

		/*
		 * call the main function with a user input
		 */
		String[] args = {"src/test/resources/systemTestWebsite", "https://whatever.com"};
		CLI.main(args);

		assertTrue(true);
	}
}
