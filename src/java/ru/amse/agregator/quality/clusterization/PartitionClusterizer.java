package ru.amse.agregator.quality.clusterization;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;
import ru.amse.agregator.utils.Tools;

import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.Graph;

/**
 *
 * @author pavel
 */
public class PartitionClusterizer extends Clusterizer {

    //simple implementation of partition algorithm
    @Override
    public void clusterize(final ArrayList<DBWrapper> objects,
        final Graph similarityGraph, ClusterStorage clusterStorage) {

        Map<UniqueId, Cluster> clusterMap
                = new HashMap<UniqueId, Cluster>();

        //init map with single element clusters
        //System.out.println("Before:");
        for (DBWrapper obj : objects) {
            UniqueId id = obj.getUniqueId();
            clusterMap.put(id, new Cluster(id));
            //System.out.println(clusterMap.get(obj).getObjectList().size());
        }

        //iterate through the edges of graph
        similarityGraph.startIterating();
        while (similarityGraph.hasNext()) {
            Graph.Edge e = similarityGraph.getNextEdge();

            //get objects
            UniqueId obj1 = e.obj1;
            UniqueId obj2 = e.obj2;

            //retrieve corresponding clusters from map
            Cluster cluster1 = clusterMap.get(obj1);
            Cluster cluster2 = clusterMap.get(obj2);

            //merge clusters
            Cluster mergedCluster
                    = Cluster.mergeClusters(cluster1, cluster2);
            clusterMap.put(obj1, mergedCluster);
            clusterMap.put(obj2, mergedCluster);
        }

        //eleminate duplicates and get unique clusters from our map
        ArrayList<Cluster> uniqueClusters = new ArrayList<Cluster>(clusterMap.values());
        Tools.eliminateDuplicates(uniqueClusters);

        // add resulting clusters to storage
        for (Cluster cluster : uniqueClusters) {
            clusterStorage.addCluster(cluster);
        }
    }
}
