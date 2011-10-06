package ru.amse.agregator.quality.clusterization.merge;

import java.util.List;
import java.util.TreeSet;
import java.util.Set;
import ru.amse.agregator.utils.Tools;

/**
 *
 * @author pavel
 *
 * Fingerprint represents a part of the text's frequency dictionary used to compare
 * pieces of text. This helps (faster computation of metric between object's
 * descriptions)(not implemented) and to determine near-duplicate descriptions during merge process.
 *
 * Current implementation basically takes the middle part of the text frequency list
 * 
 */
final public class Fingerprint {

    // these values indicate what part of vocabulary is actually considered
    // "the middle"
    private static final double middleStart = 0.0;
    private static final double middleLength = 1.0;
    // fingerprint should be constructed
    private static final int minimumFrequency = 1;

    // fingerprint representation
    private Set<String> words;

    // constructs a fingerprint for textList with dictionaty List
    Fingerprint(FrequencyList list) {

        words = new TreeSet<String>();
        
        List<String> vocabulary =
                list.getSortedVocabularyOfMinimumFrequency(minimumFrequency);
        int vocabularySize = vocabulary.size();
        int lookedThroughCount = 0;
        //get just the part of the vocabulary
        //@todo can be optimized
        for (String word : vocabulary) {
            ++lookedThroughCount;
            // the word is in the middle of the dictionary
            if ((vocabularySize * middleStart <= lookedThroughCount) &&
                (vocabularySize * (middleStart + middleLength) >= lookedThroughCount)) {
                words.add(word);
            }
        }
    }

    public Fingerprint(String text) {
        this(new FrequencyList(text));
    }

    /*
     * returns a value between 0.0 and 1.0 representing distance
     * between two fingerprints. 0.0 represents equal fingerprints
     */
    public static double distance(Fingerprint fingerprint1, Fingerprint fingerprint2) {
        if (fingerprint1.words.isEmpty() || fingerprint2.words.isEmpty()) {
            return 1.0;
        }
        
        Set<String> allWords  = new TreeSet<String>();
        allWords.addAll(fingerprint1.words);
        allWords.addAll(fingerprint2.words);
        double similarity = (double)(fingerprint1.words.size() + fingerprint2.words.size() - allWords.size())
                / (double)Math.min(fingerprint1.words.size(), fingerprint2.words.size());

        return 1.0 - similarity;
    }

    @Override
    public String toString() {
        String result = new String();
        for (String word : words) {
            result += word + "; ";
        }
        return result;
    }

}
