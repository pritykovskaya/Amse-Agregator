package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class Cluster {

    private ArrayList<UniqueId> objects = new ArrayList<UniqueId>();

    public Cluster() {
    }

    public Cluster(UniqueId obj) {
        objects.add(obj);
    }

    public Cluster(ArrayList<UniqueId> objs) {
        objects.addAll(objs);
    }

    public void addObject(UniqueId obj) {
        objects.add(obj);
    }

    public void addObjects(ArrayList<UniqueId> objs) {
        objects.addAll(objs);
    }

    public ArrayList<UniqueId> getObjectList() {
        return objects;
    }

    public int size() {
        return objects.size();
    }

    // indicates whether cluster contains an object from main database
    // returns an UniqueId of that object or null otherwise
    public UniqueId objectFromMainDB() {
        for (UniqueId id : objects) {
            if (id.fromMainDB()) {
                return id;
            }
        }
        return null;
    }

    // prints and object to system output for testing and logging
    public void print() {
        for (UniqueId id : objects) {
            DBWrapper obj = Database.getByUniqueId(id);
            System.out.println(obj);
        }
    }

    static public Cluster mergeClusters(Cluster cluster1, Cluster cluster2) {
        if (cluster1 != cluster2) {
            cluster1.addObjects(cluster2.objects);
        }
        return cluster1;
    }
}
