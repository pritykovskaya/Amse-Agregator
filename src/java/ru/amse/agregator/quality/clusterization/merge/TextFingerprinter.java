package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 *
 * @author pavel
 */
public class TextFingerprinter {

    //a list of filters which are
    //represented by sets of strings
    private List<Set<String>> filterList;

    public TextFingerprinter() {
        filterList = new ArrayList<Set<String>>();
    }

    public void addFilter(Set<String> filter) {
        if (filter != null) {
            filterList.add(filter);
        }
    }

    public void removeLastFilter() {
        if (!filterList.isEmpty()) {
            filterList.remove(filterList.size() - 1);
        }
    }

    // gets a fingerprint for this text with the current filters
    public Fingerprint getTextFingerprint(String text) {
        FrequencyList frequencyList = new FrequencyList(text);
        //remove filtered words
        for (Set<String> filter : filterList) {
            for (String word : filter) {
                frequencyList.removeWord(word);
            }
        }
        return new Fingerprint(frequencyList);
    }
}
