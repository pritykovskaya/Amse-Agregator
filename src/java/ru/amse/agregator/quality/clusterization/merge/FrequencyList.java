package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.util.List;
import ru.amse.agregator.utils.Tools;

/**
 *
 * @author pavel
 *
 * FrequencyList is a wrapper
 * 
 */
final public class FrequencyList {

    // a map representing a list of words and their frequency
    private Map<String, Integer> dictionaryMap = null;

    private final String nonCharacterSymbols = " \n\t<>'\"{}[],;:%&*^#@!?.()\\-«»1234567890„“”–—";

    public FrequencyList() {
        reset();
    }

    public FrequencyList(String text) {
        this();
        addText(text);
    }

    // sets to empty
    public void reset() {
        dictionaryMap = new TreeMap<String, Integer>();
    }

    // adds a word to dictionary or increases it's count in the list
    public void addWord(String word) {
        String normalizedWord = word.toLowerCase();
        Integer wordCount = dictionaryMap.get(normalizedWord);
        if (wordCount == null) {
            // add a word to dictionary with a 1 count
            dictionaryMap.put(normalizedWord, 1);
        } else {
            // increase count
            ++wordCount;
            dictionaryMap.put(normalizedWord, wordCount);
        }
    }

    // removes the word from dictionary regardless of count
    public void removeWord(String word) {
        String normalizedWord = word.toLowerCase();
        dictionaryMap.remove(normalizedWord);
    }

    // returns the word's count
    public int getCount(String word) {
        Integer wordCount = dictionaryMap.get(word.toLowerCase());
        if (wordCount == null) {
            return 0;
        } else {
            return wordCount;
        }
    }

    public int size() {
        return dictionaryMap.size();
    }

    // add text to list
    public void addText(String text) {
        
        if (text == null) {
            return;
        }

        // use tokenizer to parse the text
        StringTokenizer tokenizer = new StringTokenizer(text, nonCharacterSymbols);
        while (tokenizer.hasMoreTokens()) {
            addWord(tokenizer.nextToken());
        }
    }

    // return vocabulary(list of words) sorted by the frequency of the elements
    public List<String> getSortedVocabulary() {
        return Tools.getKeysSortedByValue(dictionaryMap);
        
    }

    // return the list of words that has frequency equal or greater than specified value
    // the output is sorted by the frequency
    public List<String> getSortedVocabularyOfMinimumFrequency(int minimumFrequency) {
        List<String> sortedVocabulary = Tools.getKeysSortedByValue(dictionaryMap);
        List<String> resultingList = new ArrayList<String>();
        for (String word : sortedVocabulary) {
            if (getCount(word) >= minimumFrequency) {
                resultingList.add(word);
            }
        }
        // the list is sorted because the initial list was sorted
        return resultingList;
    }
}
