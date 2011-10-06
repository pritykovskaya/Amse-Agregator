package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.List;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

public class FingerprintDescriptionMerger extends AttributeMerger {

    // parameter that adjusts how similar descriptions should be to be merged in one
    static final double DEFAULT_MIN_SIMILARITY = 0.5;
    
    private DescriptionFingerprinter fingerprinter;
    private double minSimilarity;

    public FingerprintDescriptionMerger(DescriptionFingerprinter fingerprinter, double minSimilarity) {
        assert(fingerprinter != null);
        this.fingerprinter = fingerprinter;
        this.minSimilarity = minSimilarity;
    }

    public FingerprintDescriptionMerger(DescriptionFingerprinter fingerprinter) {
        this(fingerprinter, DEFAULT_MIN_SIMILARITY);
    }

    public List<String> filterWithFingerprinter
            (List<String> descriptionList) {
        // use fingerprinter to create prints for all descriptions
        List<Fingerprint> fingerprintList = new ArrayList<Fingerprint>();
        // this list represents indexes from descriptionList
        // we'll delete those of similar descriptions and add others
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i != descriptionList.size(); ++i) {
            String description = descriptionList.get(i);
            fingerprintList.add(fingerprinter.getTextFingerprint(description));
            indexes.add(i);
        }

        assert(fingerprintList.size() == descriptionList.size());
        assert(indexes.size() == fingerprintList.size());

        for (int i = 0; i != fingerprintList.size(); ++i) {
            for (int j = 0; j != fingerprintList.size(); ++j) {
                if (j <= i) {
                    continue;
                }

                //compare fingerprints
                Fingerprint fingerprintI = fingerprintList.get(i);
                Fingerprint fingerprintJ = fingerprintList.get(j);
                if (Fingerprint.distance(fingerprintI, fingerprintJ) < minSimilarity) {

                    //discard one of the descriptions
                    //we discard that which comes last in the list
                    if (j > i) {
                        // discard ith description
                        indexes.remove((Integer)i);
                    } else {
                        // discard jth description
                        indexes.remove((Integer)j);
                    }
                }
            }
        }

        //now indexes contain indexes from descriptionList we want to add to resulting object
        ArrayList<String> resultingList = new ArrayList<String>();
        for (int index : indexes) {
            resultingList.add(descriptionList.get(index));
        }

        return resultingList;
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
        ArrayList<String> filteredDescriptions = new ArrayList<String>(filterWithFingerprinter(descriptionList));
        resultingObject.setDescriptionArray(filteredDescriptions);
    }

}
