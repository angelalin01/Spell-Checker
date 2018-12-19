import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * A SpellChecker uses a Dictionary, a Corrector, and I/O to interactively spell check an input
 * stream. It writes the corrected output to a specified output stream.
 * <p>
 * Note:
 * <ul>
 * <li> The provided partial implementation includes some I/O methods useful for getting user input
 * from a Scanner.
 * <li> All user prompts and messages should be output on System.out
 * </ul>
 * <p>
 * The SpellChecker object is used by SpellCheckerRunner; see the provided code there.
 * @see SpellCheckerRunner
 */
public class SpellChecker {
    private Corrector corr;
    private Dictionary dict;
  
    /**
     * Constructs a SpellChecker
     * 
     * @param c A Corrector
     * @param d A Dictionary
     */
    public SpellChecker(Corrector c, Dictionary d) {
        corr = c;
        dict = d;
    }

    /**
     * Returns the next integer from the argued scanner in the range [min, max]. Will re-prompt the
     * user until a valid integer is provided.
     *
     * @param min Mimimum accepted input
     * @param max Maximum accepted input
     * @param sc A Scanner
     * @return The next integer from the argued Scanner (guaranteed to be between the argued min and
     *           max)
     */
    private int getNextInt(int min, int max, Scanner sc) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.next());

                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ex) {
                // Was not a number. Ignore and prompt again.
            }
            System.out.println("Invalid input. Please try again!");
        }
    }

    /**
     * Returns the next String input from the Scanner.
     *
     * @param sc A Scanner
     * @return The next String from the argued Scanner
     */
    private String getNextString(Scanner sc) {
        return sc.next();
    }

    
    /**
     * Interactively spell checks a given document. Internally, it should use a TokenScanner to parse
     * the document. Word tokens that are not in the dictionary should be corrected; non-word tokens
     * and words that are in the dictionary should be output verbatim. This SpellChecker's dictionary
     * and corrector (dict and corr) should be used in this method.
     * <p>
     * You may assume all of the inputs to this method are non-null.
     *
     * @param in The source document to spell check
     * @param input An InputStream from which user input is obtained
     * @param out The target document to which the corrected output is written
     * @throws IOException if error while reading
     */
    public void checkDocument(Reader in, InputStream input, Writer out) throws IOException {
        Scanner sc = new Scanner(input);
        TokenScanner doc = new TokenScanner(in);
        while(doc.hasNext()) {
        	String wordToBeRead = doc.next();
        	//non-word tokens ouputted verbatim
        	if(!TokenScanner.isWord(wordToBeRead)) {
        		out.write(wordToBeRead);
        	//words in the dictionary ouputted verbatim
        	}else if(dict.isWord(wordToBeRead)) {
        		out.write(wordToBeRead);
        	}else {
        		Set<String> correct = corr.getCorrections(wordToBeRead);
        		String[] correctionsArray = correct.toArray(new String[correct.size()]);
        		System.out.println("The word: " +  wordToBeRead + " is not in the dictionary. Please enter the number "
        				+ "corresponding with the appropriate action: ");
        		System.out.println("0: Ignore and continue");
        		System.out.println("1: Replace with another word");
        		for(int i =0; i< correctionsArray.length; i++) {
        			System.out.println("Replace with " + correctionsArray[i]);
        		}
        		int userSelection = getNextInt(0, correctionsArray.length+1, sc);
        		//Output depending on user selection
        		if(userSelection == 0) {
        			out.write(wordToBeRead);
        		} else if(userSelection == 1){
        			out.write(getNextString(sc));;
        		}else {
        			out.write(correctionsArray[userSelection-2]);
        		}
        	}
        }

        // TODO
    }
}
