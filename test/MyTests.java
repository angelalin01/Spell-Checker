import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

/** Put your OWN test cases in this file, for all classes in the assignment. */
public class MyTests {
	
	@Test
	public void nullInput() throws IOException {
        try {
        	TokenScanner ts = new TokenScanner(null);
          } catch (IllegalArgumentException e) {
            return;
          }
          fail();
	}
	
	
	@Test
	public void testIsEmpty() throws IOException {
		Reader in = new StringReader(""); 
        TokenScanner d = new TokenScanner(in);
        
		assertFalse(TokenScanner.isWord(""));
		assertFalse(d.hasNext());
        
	}
	
	@Test
	public void testSingleWord() throws IOException {
		Reader in = new StringReader("one"); 
        TokenScanner d2 = new TokenScanner(in);
        assertTrue(d2.hasNext());
        assertEquals("one", d2.next());
		
	}
	
	@Test
	public void testSingleWordWithSpace() throws IOException {
		Reader in = new StringReader("one "); 
        TokenScanner d2 = new TokenScanner(in);
        assertTrue(d2.hasNext());
        assertEquals("one", d2.next());
		assertTrue(d2.hasNext());
		assertEquals(" ", d2.next());
	}
	
	@Test
	public void testSingleNonWord() throws IOException {
		 Reader in = new StringReader("$#%"); 
		 TokenScanner d2 = new TokenScanner(in);
		 assertFalse(TokenScanner.isWord("$#%"));
		 assertTrue(d2.hasNext());
		 assertEquals("$#%", d2.next());
		 
		// Reader in2 = new StringReader(".$#%"); 
		// TokenScanner d3 = new TokenScanner(in2);
		// assertFalse("reached end of stream", d3.hasNext());
		
	}
	
	@Test
	public void testEndsWithWord() throws IOException {
		Reader in = new StringReader("one $#% three"); 
		TokenScanner d2 = new TokenScanner(in);
		assertTrue(d2.hasNext());
		assertEquals("one", d2.next());
		assertTrue(d2.hasNext());
		assertEquals(" $#% ", d2.next());
		assertTrue(d2.hasNext());
		assertEquals("three", d2.next());
		assertFalse("reached end of stream", d2.hasNext());
		
	}
	
	@Test
	public void testEndsWithNonWord() throws IOException {
		Reader in = new StringReader("don't ^&*"); 
		TokenScanner d2 = new TokenScanner(in);
		assertTrue(d2.hasNext());
		assertEquals("don't", d2.next());
		assertTrue(d2.hasNext());
		assertEquals(" ^&*", d2.next());
		assertFalse("reached end of stream", d2.hasNext());
	}
	
	//Tests for Dictionary
	
	@Test
	public void testDictionaryContainsWord() throws IOException {
        Dictionary d = Dictionary.make("files/fruitDictionary.txt");
        assertTrue("'apple' -> should be true ('apple' in file)", d.isWord("apple"));
	}
	@Test
	public void testDictionaryContainsMixedCasing() throws IOException{
		Dictionary d = Dictionary.make("files/fruitDictionary.txt");
		assertTrue("'Pear' -> should be true ('pear' in file)", d.isWord("Pear"));
		assertTrue("'Get' -> should be true ('Get' in file)", d.isWord("Get"));
        assertTrue("'get' -> should be true ('Get' in file)", d.isWord("get"));
	}
	
	@Test
	public void testEmptyString() throws IOException{
		Dictionary d = Dictionary.make("files/fruitDictionary.txt");
		assertFalse("'' -> should be false (empty string not a word)", d.isWord(""));
	}
	
	@Test
	public void testWordNotInDictionary() throws IOException{
		Dictionary d = Dictionary.make("files/fruitDictionary.txt");
		assertFalse("'pineapple' -> should be false", d.isWord("pineapple"));
	}
	
	@Test
	public void testWordAllUppercase() throws IOException{
		Dictionary d = Dictionary.make("files/fruitDictionary.txt");
		assertTrue("'PEAR' -> should be true ('pear' in file)", d.isWord("PEAR"));
	}
	
	@Test
	public void testWordWithWhiteSpace() throws IOException{
		Dictionary d = Dictionary.make("files/fruitDictionary.txt");
		assertTrue("'strawberry' -> should be true ('strawberry' in file)", 
				d.isWord("strawberry"));
	}
        
	@Test(timeout=500)
    public void testDictionarySize() throws IOException {
        Dictionary d = Dictionary.make("files/fruitDictionary.txt");
        assertEquals("Dictionary should be of size 7", 7, d.getNumWords());
    }    
        
        
    //FileCorrector Tests
	
	  private Set<String> makeSet(String[] strings) {
	        Set<String> mySet = new TreeSet<String>();
	        for (String s : strings) {
	            mySet.add(s);
	        }

	        return mySet;
	    }

	  @Test
	    public void testNoCorrections() throws IOException, FileCorrector.FormatException  {
	        Corrector c = FileCorrector.make("files/fileCorrectorTestFile.txt");
	        assertEquals("lkjlkjlkj -> no corrections", makeSet(new String[]{}), 
	        		c.getCorrections("lkjlkjlkj"));
	    }
    

	  @Test
	    public void testMultipleCorrectionsLowerCase() 
	    		throws IOException, FileCorrector.FormatException  {
	        Corrector c = FileCorrector.make("files/fileCorrectorTestFile.txt");
	        assertEquals("suup -> {soup, sup, supper}", 
	        		makeSet(new String[]{"soup","sup","supper"}),
	                c.getCorrections("suup"));
	    }
	  
	  @Test
	  public void testMultipleCorrectionsAllUpperCasing() 
			  throws IOException, FileCorrector.FormatException  {
	        Corrector c = FileCorrector.make("files/fileCorrectorTestFile.txt");
	        assertEquals("SUUP -> {Soup, Sup, Supper}", 
	        		makeSet(new String[]{"Soup","Sup","Supper"}),
	                c.getCorrections("SUUP"));
	    }
	  
	  @Test
	  public void testMultipleCorrectionsMixedCasing() 
			  throws IOException, FileCorrector.FormatException  {
	        Corrector c = FileCorrector.make("files/fileCorrectorTestFile.txt");
	        assertEquals("Suup -> {Soup, Sup, Supper}",
	        		makeSet(new String[]{"Soup","Sup","Supper"}),
	                c.getCorrections("Suup"));
	    }
	  
	  @Test
	  public void testWhiteSpaces() throws IOException, 
	  FileCorrector.FormatException  {
	        Corrector c = FileCorrector.make("files/fileCorrectorTestFile.txt");
	        assertEquals("kat -> {cat}", makeSet(new String[]{"cat"}),
	                c.getCorrections("kat"));
	    }
	  

	  //Swap Tests
	  @Test
	    public void testSwapCorrectionSimple() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("tne -> {ten}", makeSet(new String[]{"ten"}), 
	            		swap.getCorrections("tne"));
	            

	        } finally {
	            reader.close();
	        }
	    }
	  
	  @Test
	    public void testSwapCorrectionWordInDictionary() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("ten -> {}", makeSet(new String[]{}), swap.getCorrections("ten"));
	            

	        } finally {
	            reader.close();
	        }
	    }
	  
	  @Test
	    public void testSwapCorrectionsSuggestionsNotInDictionary() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("asdfjkl -> {}", makeSet(new String[]{}), 
	            		swap.getCorrections("asdfjkl"));

	        } finally {
	            reader.close();
	        }
	    }
	  
	  @Test
	    public void testSwapCorrectionsMixedCasing() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("Ha -> {Ah}", makeSet(new String[]{"Ah"}), 
	            		swap.getCorrections("Ha"));

	        } finally {
	            reader.close();
	        }
	    }
	  
	  @Test
	    public void testSwapCorrectionsContraction() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("its' -> {it's}", makeSet(new String[]{"it's"}), 
	            		swap.getCorrections("its'"));

	        } finally {
	            reader.close();
	        }
	    }


	    @Test
	    public void testSwapCorrectionsWithABCDE() throws IOException {
	        Reader reader = new FileReader("files/smallDictionary.txt");
	        try {
	            Dictionary d = new Dictionary(new TokenScanner(reader));
	            SwapCorrector swap = new SwapCorrector(d);
	            assertEquals("abcd -> {abdc, bacd, acbd}", 
	            		makeSet(new String[]{"abdc", "bacd", "acbd"}), 
	            		swap.getCorrections("abcd"));

	        } finally {
	            reader.close();
	        }
	    }

	    @Test
	    public void testSwapCorrectionNullDictionary() throws IOException {
	        
	        try {
	        	new SwapCorrector(null);
	            fail("Expected an Illegal Argument - "
	            		+ "cannot create SwapCoorector with null dictionary");

	        } catch (IllegalArgumentException f) {
	        	
	        }
	        
	    }
	}


