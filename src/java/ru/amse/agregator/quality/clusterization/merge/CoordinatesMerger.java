package ru.amse.agregator.quality.clusterization.merge;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class CoordinatesMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {

        assert(attributeName.equals(DBWrapper.FIELD_COORDS));

        ArrayList<Point2D.Double> coordArray = null;
        for (UniqueId id : cluster.getObjectList())
        {
            DBWrapper obj = Database.getByUniqueId(id);
            coordArray = obj.getCoordsArray();
            if ((coordArray != null) && (!coordArray.isEmpty())) {
                break;
            }
        }

        //@todo care about switching databases here, may cause errors
        if ((coordArray != null) && (!coordArray.isEmpty())) {
            Database.connectToMainBase();
        	resultingObject.setCoordsArray(coordArray);
        }
    }

}