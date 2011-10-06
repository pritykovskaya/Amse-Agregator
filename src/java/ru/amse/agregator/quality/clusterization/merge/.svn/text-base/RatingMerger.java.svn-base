package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class RatingMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        //
        Integer resultingRating = 0;;
        for (UniqueId id : cluster.getObjectList())
        {
            DBWrapper obj = Database.getByUniqueId(id);
            Integer objRating = obj.getRating();
            if (objRating != null) {
                resultingRating += objRating;
            }

        }

        resultingObject.setRating(resultingRating);
    }
}
