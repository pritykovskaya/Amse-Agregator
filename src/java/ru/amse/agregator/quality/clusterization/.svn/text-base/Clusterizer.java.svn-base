package ru.amse.agregator.quality.clusterization;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.quality.clusterization.simgraph.Graph;

import java.util.ArrayList;
import ru.amse.agregator.storage.DBWrapper;

import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
abstract public class Clusterizer {

    //abstract algorithm that takes similarity graph and forms the list of resulting clusters
    abstract public void clusterize(final ArrayList<DBWrapper> objects,
            final Graph similarityGraph, ClusterStorage clusterStorage);
}
