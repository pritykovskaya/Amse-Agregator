package ru.amse.agregator.quality.clusterization.merge;

import java.util.Set;
import java.util.TreeSet;

import java.util.List;

/**
 *
 * @author pavel
 */
final public class DescriptionFingerprinter extends TextFingerprinter {

    // percentage of most common words
    // we want to filter from out fingerprints
    private static final double percentage = 0.02;

    // this frequency list is created to build the filter for fingerprinter
    private FrequencyList filterList;

    public DescriptionFingerprinter() {
        super();
        filterList = new FrequencyList();
    }

    public void startBuildingFilter() {
        filterList = new FrequencyList();
    }

    public void addSampleForFilter(String text) {
        filterList.addText(text);
    }

    public void finalizeFilter() {
        Set<String> filter = new TreeSet<String>();
        List<String> vocabulary =
            filterList.getSortedVocabulary();
        int vocabularySize = vocabulary.size();
        int lookedThroughCount = 0;
        //get just the part of the vocabulary
        //@todo can be optimized
        for (String word : vocabulary) {
            ++lookedThroughCount;
            //get the last percentage of the list
            if (vocabularySize * (1.0 - percentage) <= lookedThroughCount) {
                // output 
//                System.out.print(lookedThroughCount);
//                System.out.print(" ");
//                System.out.println(word);
                filter.add(word);
            }
        }
        addFilter(filter);
    }

}
