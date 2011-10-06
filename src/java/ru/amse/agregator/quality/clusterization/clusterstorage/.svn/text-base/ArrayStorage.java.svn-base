package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author pavel
 */ 
final public class ArrayStorage extends ClusterStorage {

    public ArrayList<Cluster> clusters = new ArrayList<Cluster>();
    
    @SuppressWarnings("rawtypes")
	private Iterator iterator = null;

    @Override
	public void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    @Override
	public void startIterating() {
        iterator = clusters.iterator();
    }

    @Override
	public Cluster getNextCluster() {
        return (Cluster)iterator.next();
    }

    @Override
	public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
	public int getClusterCount() {
        return clusters.size();
    }

    @Override
    public void finishIterating() {
        iterator = null;
    }
}
