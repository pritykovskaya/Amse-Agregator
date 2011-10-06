/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.amse.agregator.quality.clusterization.textquality;

import ru.amse.agregator.quality.clusterization.textquality.ahocorasick.*;

import java.io.BufferedReader;
import java.util.Iterator;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author pavel
 *
 * A class for assessing the quality of text
 */
public class TextQuality {

    private AhoCorasick goodVocabulary;

    public TextQuality() {
        goodVocabulary = new AhoCorasick();
    }

    private void readVocabularyFromFile(String fileName, AhoCorasick vocabulary) throws IOException {
        
        BufferedReader inputStream = null;

        try {
            inputStream =
                new BufferedReader(new FileReader(fileName));

            String word;
            while (true) {
                word = inputStream.readLine();
                if (word == null) {
                    break;
                }
                // "1" magic number means we treat all the words equally
                vocabulary.add(word.getBytes(), 1);

            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    public void readGoodVocabularyFromFile(String fileName) throws IOException {

        readVocabularyFromFile(fileName, goodVocabulary);

    }

    public void prepareForSearch() {

        goodVocabulary.prepare();
        
    }

    private int countMatches(String example, AhoCorasick vocabulary) {
        if (example == null) {
            return 0;
        }
        int matchesCount = 0;
        Iterator<SearchResult> searcher = vocabulary.search(example.getBytes());
        int[] results;
        while (searcher.hasNext()) {
            results = searcher.next().getOutputs();
            for (int i = 0; i < results.length; ++i) {
                matchesCount += results[i];
            }
        }
        return matchesCount;

    }

    public double rateQuality(String example) {
        return countMatches(example, goodVocabulary);
    }


}
