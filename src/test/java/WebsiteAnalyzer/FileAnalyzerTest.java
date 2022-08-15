package WebsiteAnalyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * this class will test everything associated with the file analyzer class
 */
public class FileAnalyzerTest {

    /*
     * Ensure that malformated html file links result in an error.
     */
    @Test void parseFileTypeTest() {
        /*
         * Error File Types
         */
        String badLink = "<a href=\"\">vid</a>"; // Missing file:///
        assertTrue(FileAnalyzer.parseFileType(badLink) == FileAnalyzer.FileType.ERROR);

        badLink = "<a href=\"file:///\">vid</a>"; // No file specified.
        assertTrue(FileAnalyzer.parseFileType(badLink) == FileAnalyzer.FileType.ERROR);

        badLink = "<a href=\"flie:///\">vid</a>"; // Spelling mistake.
        assertTrue(FileAnalyzer.parseFileType(badLink) == FileAnalyzer.FileType.ERROR);

        badLink = "<a href=\"file:///\"vid</a>"; // Missing closing link brace.
        assertTrue(FileAnalyzer.parseFileType(badLink) == FileAnalyzer.FileType.ERROR);

        badLink = "<a href=\"file:///.....\">vid</a>"; // We can't have a file named that.
        assertTrue(FileAnalyzer.parseFileType(badLink) == FileAnalyzer.FileType.ERROR);

        /*
         * Archive File Types
         */
        for (String extension : FileAnalyzer.archiveFileExtensions) {
            String fileLink = "<a href=\"file:///test" + extension + "\">file</a>";
            assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.ARCHIVE);
        }

        /*
         * Video File Types
         */
        for (String extension : FileAnalyzer.videoFileExtensions) {
            String fileLink = "<a href=\"file:///test" + extension + "\">file</a>";
            assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.VIDEO);
        }

        /*
         * Audio File Types
         */
        for (String extension : FileAnalyzer.audioFileExtensions) {
            String fileLink = "<a href=\"file:///test" + extension + "\">file</a>";
            assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.AUDIO);
        }

        /*
         * Uncategorized File Types
         */
        String fileLink = "<a href=\"file:///test.cpp\">file</a>";
        assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.UNCATEGORIZED);

        fileLink = "<a href=\"file:///test.deb\">file</a>";
        assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.UNCATEGORIZED);

        fileLink = "<a href=\"file:///test.exe\">file</a>";
        assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.UNCATEGORIZED);

        fileLink = "<a href=\"file:///test.myfunfiletype\">file</a>";
        assertTrue(FileAnalyzer.parseFileType(fileLink) == FileAnalyzer.FileType.UNCATEGORIZED);
    }

    
    /*
     * Ensure parsing a file name from an HTML link works.
     */
    @Test void parseFileNameTest() {
        /*
         * Invalid File Names
         */
        String badLink = "<a href=\"\">vid</a>"; // Missing file:///
        assertTrue(FileAnalyzer.parseFileName(badLink).equals(""));

        badLink = "<a href=\"file:///\">vid</a>"; // No file specified.
        assertTrue(FileAnalyzer.parseFileName(badLink).equals(""));

        badLink = "<a href=\"flie:///\">vid</a>"; // Spelling mistake.
        assertTrue(FileAnalyzer.parseFileName(badLink).equals(""));

        badLink = "<a href=\"file:///\"vid</a>"; // Missing closing link brace.
        assertTrue(FileAnalyzer.parseFileName(badLink).equals(""));

        /*
         * Valid File Names
         */
        String fileLink = "<a href=\"file:///src/dev/temp/test.cpp\">file</a>";
        assertTrue(FileAnalyzer.parseFileName(fileLink).equals("src/dev/temp/test.cpp"));

        fileLink = "<a href=\"file:///../../test.deb\">file</a>";
        assertTrue(FileAnalyzer.parseFileName(fileLink).equals("../../test.deb"));

        fileLink = "<a href=\"file:////home/test/test.exe\">file</a>";
        assertTrue(FileAnalyzer.parseFileName(fileLink).equals("/home/test/test.exe"));

        fileLink = "<a href=\"file:///test.myfunfiletype\">file</a>";
        assertTrue(FileAnalyzer.parseFileName(fileLink).equals("test.myfunfiletype"));
    }
}
