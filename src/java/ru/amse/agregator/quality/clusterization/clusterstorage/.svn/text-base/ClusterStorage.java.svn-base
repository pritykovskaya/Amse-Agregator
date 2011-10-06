package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;

import org.bson.types.ObjectId;

/**
 *
 * @author pavel
 *
 * An absract class representing a collection of clusters
 */
abstract public class ClusterStorage {

    abstract public void addCluster(Cluster cluster);

    abstract public void startIterating();

    abstract public Cluster getNextCluster();

    abstract public void finishIterating();

    abstract public boolean hasNext();

    abstract public int getClusterCount();

    public void printAllClusters() {

        startIterating();
        while (hasNext()) {
            Cluster cluster = getNextCluster();
            cluster.print();
        }
        finishIterating();
    }

    public void printNonSingleObjectClusters() {

        startIterating();
        while (hasNext()) {
            Cluster cluster = getNextCluster();
            if (cluster.size() > 1) {
                cluster.print();
                System.out.println();
            }
        }
        finishIterating();

    }
}
