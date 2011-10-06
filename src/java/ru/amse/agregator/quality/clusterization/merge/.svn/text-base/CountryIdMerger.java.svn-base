package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class CountryIdMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        //
        String countryName = null;
        for (UniqueId id : cluster.getObjectList())
        {
            DBWrapper obj = Database.getByUniqueId(id);
            countryName = obj.getStaticCountryName();
            if ((countryName != null) && (!countryName.isEmpty())) {
                break;
            }
        }

        //@todo care about switching databases here, may cause errors     
        if ((countryName != null) && (!countryName.isEmpty())) {
            Database.connectToMainBase();
        	resultingObject.setCountryByName(countryName);
        }
    }
    
}
