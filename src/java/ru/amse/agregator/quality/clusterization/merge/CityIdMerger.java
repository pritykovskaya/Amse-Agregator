package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class CityIdMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        //
        String cityName = null;
        for (UniqueId id : cluster.getObjectList())
        {
            DBWrapper obj = Database.getByUniqueId(id);
            cityName = obj.getStaticCityName();
            if ((cityName != null) && (!cityName.isEmpty())) {
                break;
            }
        }

        //@todo care about switching databases here, may cause errors 
        if ((cityName != null) && (!cityName.isEmpty())) {
            Database.connectToMainBase();
        	resultingObject.setCityByName(cityName);
        }
    }

}
