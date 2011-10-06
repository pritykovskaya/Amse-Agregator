package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

/**
 *
 * @author pavel
 */
public class StringMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {

        // pick the first object and use it's value for this attribute
        DBWrapper obj = Database.getByUniqueId(cluster.getObjectList().get(0));
        resultingObject.setKeyValue(attributeName, (String)obj.getValue(attributeName));

    }

}
