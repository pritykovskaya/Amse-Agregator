package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.InternalException;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.saxon.functions.Collection;

/**
 * @author: Pavel
 *
 * This class combines description merging techniques of qualityDescription merger
 * which ranges descriptions depending on the quality of descriptions and
 * fingerprintMerger which deletes similar descriptions
 */
public class CombinedDescriptionMerger extends AttributeMerger {

    //private DescriptionFingerprinter fingerprinter;
    private QualityDescriptionMerger qualityMerger;
    private FingerprintDescriptionMerger fingerprintMerger;

    public CombinedDescriptionMerger(DescriptionFingerprinter fingerprinter) throws InternalException {
        //this.fingerprinter = fingerprinter;
        qualityMerger = new QualityDescriptionMerger();
        fingerprintMerger = new FingerprintDescriptionMerger(fingerprinter);
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

        ArrayList<String> sortedDescriptions;
        sortedDescriptions = new ArrayList<String>(qualityMerger.sortByQuality(descriptionList));
        List<String> resultingList = fingerprintMerger.filterWithFingerprinter(sortedDescriptions);

        // reverse the list so the best description will be the first
        Collections.reverse(resultingList);
        resultingObject.setDescriptionArray
                (new ArrayList<String>(resultingList));
    }

}
