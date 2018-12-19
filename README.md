# Spell-Checker

Spell check program based on a dictionary and a list of corrections for misspelled words.

## Class Overview:

- TokenScanner: parses a text file and breaks it up into tokens (words and nonwords)
- Dictionary: parses a text file and creates a collection of words in the dictionary
- FileCorrector and SwapCorrector: given a misspelled word, suggests possible corrections from a dictionary or collection of corrections.
- SpellChecker: links the TokenScanner, Dictionary and Corrector to interactively spellcheck a document.

In addition to the provided files described above, we also provide several test classes (DictionaryTest, TokenScannerTest, etc.) and sample text files with which to test the code.

