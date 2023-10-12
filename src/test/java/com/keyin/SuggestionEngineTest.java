package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SuggestionEngineTest {
    private final SuggestionEngine suggestionEngine = new SuggestionEngine();

    @BeforeEach
    public void setup() {
        try {
            Path dictionaryFilePath =
                    Paths.get( ClassLoader.getSystemResource("words.txt").toURI());

            if (Files.exists(dictionaryFilePath)) {
                suggestionEngine.loadDictionaryData(dictionaryFilePath);
            } else {
                System.err.println("Dictionary file doesn't exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testLoadDictionaryData() {
        Assertions.assertFalse(suggestionEngine.getWordSuggestionDB().isEmpty(),
                "Expected dictionary file to be loaded");
    }

    @Test
    public void testGenerateSuggestionsPass() {
        String mispelledWord = "scienct";
        Assertions.assertTrue(suggestionEngine.generateSuggestions(mispelledWord).contains("science"),
                "Expected 'science' suggested for 'scienct'");
    }

    @Test
    public void testGenerateSuggestionsFail() {
        String correctlySpelledWord = "science";
        Assertions.assertTrue(suggestionEngine.generateSuggestions(correctlySpelledWord).isEmpty(),
                "Expected no result for correctly spelled word.");
    }
}
