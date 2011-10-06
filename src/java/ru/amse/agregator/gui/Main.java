package ru.amse.agregator.gui;

import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.searcher.UserQuery;
import ru.amse.agregator.storage.Database;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Searcher.setIndexDir(new File("build/index"));
        System.out.println("result - " + Searcher.search(new UserQuery("о")));
        AttractionManager.connectToDatabase();
        System.out.println("sdfsfdsf");
        Database.printAll();
//        Database.getAttractionById();
    }
}

