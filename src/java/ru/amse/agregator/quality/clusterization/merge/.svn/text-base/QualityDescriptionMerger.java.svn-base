/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.amse.agregator.quality.clusterization.merge;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.amse.agregator.quality.clusterization.InternalException;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.quality.clusterization.textquality.TextQuality;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;
import ru.amse.agregator.utils.Tools;

/**
 *
 * @author pavel
 */
public class QualityDescriptionMerger extends AttributeMerger {

    private TextQuality qualityMeter = null;
    // path to <project_directory>/resources/clusterizer/myGoodVocabulary.txt
    static private final String GOOD_VOCABULARY_PATH = "resources/clusterizer/myGoodVocabulary.txt";

    public QualityDescriptionMerger() throws InternalException {

        qualityMeter = new TextQuality();
        try {
            qualityMeter.readGoodVocabularyFromFile(GOOD_VOCABULARY_PATH);
        } catch (IOException e) {
            throw new InternalException("Couldn't read vocabulary from file");
        }
        qualityMeter.prepareForSearch();

    }

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
            //form a list of all descriptions for this cluster
            List<String> descriptionList = new ArrayList<String>();

            for (UniqueId id : cluster.getObjectList()) {
                DBWrapper obj = Database.getByUniqueId(id);

                ArrayList<String> descriptions = obj.getDescriptionArray();
                if (descriptions != null) {
                    descriptionList.addAll(obj.getDescriptionArray());
                }
            }

            ArrayList<String> sortedDescriptions 
                    = new ArrayList<String>(sortByQuality(descriptionList));

            // reverse the list so the best description will be the first
            Collections.reverse(sortedDescriptions);
            resultingObject.setDescriptionArray(sortedDescriptions);
    }

    public List<String> sortByQuality(List<String> descriptions) {

        Map<String, Double> descriptionsToRating = new HashMap<String, Double>();
        for (String description : descriptions) {
            descriptionsToRating.put(description, qualityMeter.rateQuality(description));
        }
        // sort descriptions by rating
        List<String> sortedDescriptions = Tools.getKeysSortedByValue(descriptionsToRating);
        return  sortedDescriptions;       
    }


}
