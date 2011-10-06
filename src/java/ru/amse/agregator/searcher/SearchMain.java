package ru.amse.agregator.searcher;

import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Nov 15, 2010
 * Time: 9:37:48 PM
 */

public class SearchMain {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        Searcher.setIndexDir(new File("index"));
        long timeForSearch = System.currentTimeMillis() - begin;
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(DBWrapper.TYPE_CAFE);
//        labels.add(DBWrapper.TYPE_ARCH_ATTRACTION);
        labels.add(DBWrapper.TYPE_NATURAL_ATTRACTION);
        //ArrayList<DBWrapper> output = AdvancedSearcher.search(new UserQuery("Санкт-Петербург", labels));
        ArrayList<DBWrapper> output = null;
        output = AdvancedSearcher.search(new UserQuery("Санкт-Петербург", labels));
        if (output == null) {
            System.out.println("Ничего не найдено");
            return;
        }
        for (DBWrapper currentObject: output) {
            System.out.println(currentObject);
        }
        System.out.println("Searching " + output.size() + " objects by " + timeForSearch + " millis") ;
    }
}