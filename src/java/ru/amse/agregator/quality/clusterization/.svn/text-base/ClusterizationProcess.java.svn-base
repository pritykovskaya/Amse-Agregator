package ru.amse.agregator.quality.clusterization;

import java.util.ArrayList;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.quality.clusterization.merge.*;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class ClusterizationProcess {

    // indicates whether mainbase should be deleted
    static private boolean shouldDeleteMainDB;
    // dirty base name, should be 'dirty' by default
    static private String baseToAddName;

    static public void perform() {

        // hard-coded threshold, we may choose to adjust this parameter later
        final double threshold = 0.0;

        Database.connectToMainBase();
        if (shouldDeleteMainDB) {
            System.out.println("Deleting main base");
            Database.removeAllCollections();
        }

        ArrayList<String> types = DBWrapper.getTypeNames();

        for (String type : types) {

            // instantiate all algorithms and structures used
            Clusterizer clusterizer = new PartitionClusterizer();
            Metric metric = new StandardMetric();
            Graph similatityGraph = new ArrayGraph(metric, threshold);
            ClusterStorage storage = new ArrayStorage();

            System.out.println("**************\n\nProcessing " + type + "s");
            System.out.println("Retrieving data from " + baseToAddName + " base");

            // list of all objects to be processed
            ArrayList<DBWrapper>  allObjects = new ArrayList<DBWrapper>();
            
            // prepare new objects
            {

                Database.connectToBase(baseToAddName);
                ArrayList<DBWrapper> allOfTypeNew = Database.getAllWithType(type);
                setUniqueIdsForObjects(allOfTypeNew, baseToAddName);
                System.out.println("There are " + allOfTypeNew.size() + " " + type + "s in " + baseToAddName + " base");
                allObjects.addAll(allOfTypeNew);
            }

            // prepare existing in main database objects objects
            if (!shouldDeleteMainDB) {
                //should merge with existing in main db objects
                Database.connectToMainBase();
                ArrayList<DBWrapper> allOfTypeOld = Database.getAllWithType(type);
                setUniqueIdsForObjects(allOfTypeOld, Database.MAIN_DB_NAME);
                System.out.println("There are " + allOfTypeOld.size() + " " + type + "'s in main base");
                allObjects.addAll(allOfTypeOld);
            }

        
            System.out.println("Building similarity graph\nObjects processed:");
            similatityGraph.build(allObjects);

            System.out.println(String.valueOf(allObjects.size()));

            System.out.println("Resulting graph has "
                    + String.valueOf(similatityGraph.getEdgeCount()) + " edges");

            System.out.println("Performing clusterization process");

            clusterizer.clusterize(allObjects, similatityGraph, storage);
            
            //print non single object clusters
//
//            storage.startIterating();
//            while (storage.hasNext()) {
//                Cluster cluster = storage.getNextCluster();
//                if (cluster.size() > 1) {
//                    ClusterMerger merger = new ObjectMerger();
//                    System.out.println("Cluster");
//                    cluster.print();
//                    System.out.println("Merged object:");
//
//                    DBWrapper resultingObject = merger.mergeCluster(cluster);
//                    System.out.println(resultingObject);
//                }
//            }
//            storage.finishIterating();

            System.out.println(String.valueOf(allObjects.size()));
            
            System.out.println("Clusterisation process created "
                    + String.valueOf(storage.getClusterCount()) + " clusters out of "
                    + String.valueOf(allObjects.size()) + " objects");

            System.out.println("Merging and adding to main base");

            try {
                MergeProcess.perform(new ObjectMerger(), storage);
            } catch (InternalException e) {
                System.out.println("Execution aborted! Following error occured!:");
                System.out.println(e);
            }
        }
  
       System.out.println("Finished clusterization process successfully");
    }

    public static void setUniqueIdsForObjects(ArrayList<DBWrapper> objects,
            final String databaseName) {
        for (DBWrapper obj : objects) {
            obj.setUniqueId(new UniqueId(obj, databaseName));
        }
        
    }

    public static void main(String[] args) {
    	
    	Database.connectToDirtyBase();
    	Database.unificationNames();
    	
        //set default parameter values
        baseToAddName = Database.DIRTY_DB_NAME;
        shouldDeleteMainDB = false;

        if (args.length > 2) {
            System.out.println("Error: Unexpected number of arguments");
            return;
        }
        if (args.length >= 1) {
            baseToAddName = args[0];           
        }

        if (args.length == 2) {
            if (args[1].equals("-r")) {
                shouldDeleteMainDB = true;
            } else if (args[1].equals("-u")) {
                shouldDeleteMainDB = false;
            } else {
                System.out.println("Error: Unexpected second argument");
                return;
            }
        }

        System.out.println("Adding " + baseToAddName + " database");
        if (shouldDeleteMainDB) {
            System.out.println("Renewing database. Main database will be deleted");
        } else {
            System.out.println("Updating database. New objects will be added");
        }

        perform();
    }
}
