package ru.amse.agregator.quality.clusterization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import net.sf.saxon.functions.Collection;
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
 * Only some code for testing and creating some temporary files
 */
public class ClusterizationHelper {

    static public void main(String[] args) throws Exception {
//        Database.connectToMainBase();
//        String fileName1 = "/home/pavel/myDescriptions.txt";
//        String fileName = "/home/pavel/myVocabulary.txt";
//        FrequencyList list = new FrequencyList();
//
//        ArrayList<DBWrapper> cities = Database.getAllWithType(DBWrapper.TYPE_CITY);
//        for (DBWrapper city : cities) {
//            List<String> descriptions = city.getDescriptionArray();
//            if (descriptions != null) {
//                for (String description : descriptions) {
//                    list.addText(description);
//                }
//            }
//
//        }
//
//        BufferedWriter outputStream = null;
//        try {
//            outputStream =
//                new BufferedWriter(new FileWriter(fileName));
//
//            List<String> sortedVocabulary = list.getSortedVocabularyOfMinimumFrequency(100);
//
//            for (String word : sortedVocabulary) {
//                outputStream.write(word + "\n");
//            }
//
//
//        } finally {
//            if (outputStream != null) {
//                outputStream.close();
//            }
//        }

        QualityDescriptionMerger qualityMerger = new QualityDescriptionMerger();
        FingerprintDescriptionMerger fingerprintMerger = new FingerprintDescriptionMerger(new DescriptionFingerprinter());
        ArrayList<String> desc = new ArrayList<String>();
        desc.add("прекрасный город прекрасный");
        desc.add("плохой город прекрасный прекрасный прекрасный");
        desc.add("троолололол трололо троололо");
        ArrayList<String> sortedDescriptions
                = new ArrayList<String>(qualityMerger.sortByQuality(desc));
        List<String> resultingList = fingerprintMerger.filterWithFingerprinter(sortedDescriptions);

        for (String res : resultingList) {
            System.out.println(res);
        }
        Collections.reverse(resultingList);


    }

}
