package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.storage.DBWrapper;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class MergeProcess {

    static private int MINIMUM_DESCRIPTION_LENGTH = 20;

    static public void perform(ClusterMerger merger, ClusterStorage storage) {

        merger.preprocess(storage);

        storage.startIterating();
        while (storage.hasNext()) {
            //use merging algorithm to create single object out of cluster
            Database.connectToDirtyBase();
            Cluster cluster = storage.getNextCluster();

            //logging an error
            if (cluster.size() == 0) {
                //not critical
                System.out.println("Encountered empty cluster while merging");
                continue;
            }

            DBWrapper obj = merger.mergeCluster(cluster);
            if (checkObjectConsistency(obj)) {
                if(obj.getName() != null && !obj.getName().isEmpty()){
                    // add or update an object
                    UniqueId objectToUpdateId = cluster.objectFromMainDB();
                    if (objectToUpdateId != null) {
                        // update the correct object
                        Database.connectToMainBase();
                        obj.setId(objectToUpdateId.getId());
                        Database.update(obj);
                    } else {
                        // add a new object
                        Database.connectToMainBase();
                        Database.add(obj);
                    }
                }
            }
            

        }
        storage.finishIterating();

        merger.postprocess(storage);
    }

    // checks if the object is consistent to add to the base
    static private boolean checkObjectConsistency(DBWrapper object) {
        ArrayList<String> descriptions = object.getDescriptionArray();
        if (descriptions == null) {
            return false;
        }

        for (String description : descriptions) {
            if (description.length() > MINIMUM_DESCRIPTION_LENGTH) {
                return true;
            }
        }

        // all descriptions are too short
        return false;

    }
}
