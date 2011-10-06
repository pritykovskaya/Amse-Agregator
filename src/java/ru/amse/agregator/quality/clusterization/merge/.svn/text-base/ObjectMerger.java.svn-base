package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.amse.agregator.quality.clusterization.InternalException;
import ru.amse.agregator.storage.UniqueId;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.utils.Tools;
import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;


/**
 *
 * @author pavel
 */
public class ObjectMerger extends ClusterMerger {

    final private AttributeMerger defaultMerger = new StringMerger();
    private DescriptionFingerprinter fingerprinter;

    final private Map<String, AttributeMerger> attMergers;
    public ObjectMerger() throws InternalException {

        
        final AttributeMerger mergeStringLists = new StringListMerger();
        final AttributeMerger doNothing = new DoNothingMerger();

        fingerprinter = new DescriptionFingerprinter();
        attMergers = new TreeMap<String, AttributeMerger>();

        // set appropriate mergers for corresponding fields
        // note that setting wrong mergers may cause runtime errors due type conversion

        attMergers.put(DBWrapper.FIELD_CONTINENT_ID, new ContinentiIdMerger());
        attMergers.put(DBWrapper.FIELD_COUNTRY_ID, new CountryIdMerger());
        attMergers.put(DBWrapper.FIELD_CITY_ID, new CityIdMerger());
        attMergers.put(DBWrapper.FIELD_KEYWORDS, mergeStringLists);
        attMergers.put(DBWrapper.FIELD_IMAGES, mergeStringLists);
        attMergers.put(DBWrapper.FIELD_DESC, new CombinedDescriptionMerger(fingerprinter));
        attMergers.put(DBWrapper.FIELD_SOURCE_URL, mergeStringLists);
        attMergers.put(DBWrapper.FIELD_UNIQUE_ID, doNothing);
        attMergers.put(DBWrapper.FIELD_ID, doNothing);     
        attMergers.put(DBWrapper.FIELD_COORDS, new CoordinatesMerger());
        attMergers.put(DBWrapper.FIELD_RATING, new RatingMerger());
    }


    @Override
    public DBWrapper mergeCluster(Cluster cluster) {
        DBWrapper resultingObject = new DBWrapper();

        // retrieve a list of attributeNames that are possesed
        // by at least one object of the cluster
        ArrayList<String> attributeNames = getAttributeNames(cluster);

        //merge all attributes using corresponding attribute mergers
        for (String attributeName : attributeNames) {
            AttributeMerger merger = attMergers.get(attributeName);
            if (merger == null) {
                merger = defaultMerger;
            }
            merger.mergeAttributes(attributeName, cluster, resultingObject);
        }

        return resultingObject;
        
    }

    ArrayList<String> getAttributeNames(Cluster cluster) {
        // hash set is used to eliminate duplicates
        ArrayList<String> attributeNames = new ArrayList<String>();

        for (UniqueId id : cluster.getObjectList()) {
            DBWrapper obj =  Database.getByUniqueId(id);
            attributeNames.addAll(obj.getKeySetWithoutWrapperFields());
        }

        Tools.eliminateDuplicates(attributeNames);
        return new ArrayList<String>(attributeNames);
    }

    @Override
    public void preprocess(ClusterStorage clusters) {
        fingerprinter.startBuildingFilter();
        clusters.startIterating();
        while (clusters.hasNext()) {
            Cluster cluster = clusters.getNextCluster();
            for (UniqueId id : cluster.getObjectList()) {
                DBWrapper object = Database.getByUniqueId(id);
                List<String> descriptionList = object.getDescriptionArray();
                if (descriptionList != null) {
                    for (String description : object.getDescriptionArray()) {
                        fingerprinter.addSampleForFilter(description);
                    }
                }
            }
        }
        clusters.finishIterating();
        fingerprinter.finalizeFilter();
    }

    @Override
    public void postprocess(ClusterStorage clusters) {

    }
}
