package ru.amse.agregator.quality.clusterization;

import java.util.ArrayList;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.quality.clusterization.merge.*;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.quality.clusterization.merge.Fingerprint;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 *
 */
public class ClusterizationTester {


    public static void Test() throws Exception {
        double threshold = 0.0;

        System.out.println("Deleting main base");

        Fingerprint f1 = new Fingerprint("Ликийские гробницы - главная достопримечательность курорта. Гробницы датируются IV веком до н.э. Это совершенно особый вид надгробного искусства – массивнейшие каменные изваяния оригинальной формы. Наиболее знаменита – гробница Аминтаса, представляющая собой площадку с вырубленным в скале портиком и двумя ионическими колоннами. Подняться к ней можно по ступенькам. На стене гробницы вырублена надпись на греческом \"Аминтас, сын Хермагиоса\". Ещё одна гробница имеет довольно необычное оформление. Прямоугольная в плане, с деревянными опорами, она имеет крышку в готическом стиле и арки с обеих сторон. Крышка украшена рельефами с изображениями войны, которые, как полагают, рассказывают о жизни захороненного в этой гробнице человека.");
        Fingerprint f2 = new Fingerprint("Одна из главных достопримечательностей Фетхие – ликийские каменные гробницы, датируемые IV веком до н.э. Самая известная и великолепная из них – гробница Аминтаса. Она прекрасно видна из долины, но если у Вас хватит сил Вы взобраться к ней по многочисленным ступеням, Вы сможете восхититься её величием в полной мере. Ещё одна гробница ликийского периода имеет довольно необычное оформление. Прямоугольная в плане, с деревянными опорами, она имеет крышку в готическом стиле и арки с обеих сторон. Крышка украшена рельефами с изображениями войны, которые, как полагают, рассказывают о жизни захороненного в этой гробнице человека.");
        double dist = Fingerprint.distance(f1, f2);
        if (dist <= 0.5) {
            System.out.println("Distance is " + dist);
//            System.out.println(desc1);
//            System.out.println(desc2);
            System.out.println(f1);
            System.out.println(f2);
        }

        ArrayList<String> types = DBWrapper.getTypeNames();

        for (String type : DBWrapper.getTypeNames()) {


            Clusterizer clusterizer = new PartitionClusterizer();
            Metric metric = new NameMetric();
            Graph similatityGraph = new ArrayGraph(metric, threshold);
            ClusterStorage storage = new ArrayStorage();

            System.out.println("**************\n\nProcessing " + type + "s");
            System.out.println("Retrieving data from dirty base");

            Database.connectToDirtyBase();

            ArrayList<DBWrapper> allOfType = Database.
                    getAllWithType(type);
            ClusterizationProcess.setUniqueIdsForObjects(allOfType, Database.DIRTY_DB_NAME);

            System.out.println("There are " + allOfType.size() + " " + type + "s in dirty base");

            System.out.println("Building similarity graph\nObjects processed:");
            similatityGraph.build(allOfType);

            System.out.println(String.valueOf(allOfType.size()));

            System.out.println("Resulting graph has "
                    + String.valueOf(similatityGraph.getEdgeCount()) + " edges");

            System.out.println("Performing clusterization process");

            clusterizer.clusterize(allOfType, similatityGraph, storage);
            System.out.println("Printing non single object clusters:");

    //        storage.startIterating();
    //        while (storage.hasNext()) {
    //            Cluster cluster = storage.getNextCluster();
    //            if (cluster.size() > 1) {
    //                ClusterMerger merger = new ObjectMerger();
    //                System.out.println("Merged object:");
    //                DBWrapper resultingObject = merger.mergeCluster(cluster);
    //                for (String desc : resultingObject.getDescriptionArray()) {
    //                    System.out.println("Description:");
    //                    System.out.println(desc);
    //                    System.out.println("Fingerprint:");
    //                    Fingerprint f = new Fingerprint(desc);
    //                    System.out.println(f);
    //                }
    //
    //                System.out.println(resultingObject);
    //            }
    //        }
    //        storage.finishIterating();

//            System.out.println("Testing comparison:");
//            storage.startIterating();
//            while (storage.hasNext()) {
//                Cluster cluster = storage.getNextCluster();
//                if (cluster.size() > 1) {
//                    ClusterMerger merger = new ObjectMerger();
////                    System.out.println("OBject:");
//                    DBWrapper resultingObject = merger.mergeCluster(cluster);
//                    ArrayList<String> descArray = resultingObject.getDescriptionArray();
//                    for (String desc1 : descArray) {
//                        for (String desc2 : descArray) {
//                            if (desc1 == desc2) {
//                                continue;
//                            }
//                            Fingerprint f1 = new Fingerprint(desc1);
//                            Fingerprint f2 = new Fingerprint(desc2);
//                            double dist = Fingerprint.distance(f1, f2);
//                            if (dist <= 0.5) {
//                                System.out.println("Distance is " + dist);
//                                System.out.println(desc1);
//                                System.out.println(desc2);
//                                System.out.println(f1);
//                                System.out.println(f2);
//                            }
//                        }
//                    }
//
//                    System.out.println(resultingObject);
//                }
//            }
//            storage.finishIterating();

            //ClusterMerger merger = new ObjectMerger();
            //MergeProcess.perform(merger, storage);

            System.out.println(String.valueOf(allOfType.size()));

            System.out.println("Clusterisation process created "
                    + String.valueOf(storage.getClusterCount()) + " clusters out of "
                    + String.valueOf(allOfType.size()) + " objects");


            MergeProcess.perform(new ObjectMerger(), storage);
        }
    }

    public static void main(String[] args) throws Exception {
        Test();
    }

}
