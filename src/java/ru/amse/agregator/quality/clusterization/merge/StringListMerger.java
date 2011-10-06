package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.HashSet;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;

/**
 *
 * @author pavel
 */
public class StringListMerger extends AttributeMerger {

    @Override
    @SuppressWarnings("unchecked")
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        ArrayList<ArrayList<String>> stringLists = new ArrayList<ArrayList<String>>();
        // get all the lists from all of the objects in the cluster
        for (UniqueId id : cluster.getObjectList()) {
            DBWrapper obj = Database.getByUniqueId(id);
            stringLists.add((ArrayList<String>)obj.getValue(attributeName));
        }

        resultingObject.setListAttribute(attributeName, mergeStringLists(stringLists));

    }

    public ArrayList<String> mergeStringLists
            (final ArrayList<ArrayList<String>> stringLists) {

        // hash set is used to eliminate duplicate strings
        HashSet<String> hash = new HashSet<String>();
        // add everystring to hashset
        for (ArrayList<String> stringList : stringLists) {
            if (stringList != null) {
                for (String string : stringList) {
                    if (string != null) {
                        hash.add(string);
                    }
                }
            }
        }
        // return unique strings
        return new ArrayList<String>(hash);
    }

}
