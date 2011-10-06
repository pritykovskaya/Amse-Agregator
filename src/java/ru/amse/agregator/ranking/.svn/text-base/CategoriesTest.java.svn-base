package ru.amse.agregator.ranking;

import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: nepank
 * Date: 23.03.11
 * Time: 13:23
 */

public class CategoriesTest {
    public void test() {
        Database.connectToMainBase();
//        DBWrapper redFort = Database.getDBObjectByIdAndType(new ObjectId("4db5f37b2420b0864ee35ec0"), DBWrapper.TYPE_ARCH_ATTRACTION);
//        System.out.println(redFort.getKeyWordsArray());
//        System.out.println("redFort in ATTRACTION category : " + redFort.isInCategory(Categories.ATTRACTION));
//        System.out.println("redFort in BUILDING category : " + redFort.isInCategory(Categories.BUILDING));
//        System.out.println("redFort in BRIDGE category : " + redFort.isInCategory(Categories.BRIDGE));

//        ArrayList<String> tops = Database.getTopNCategoriesInCountry(10, new ObjectId("4db5f4a72420b08697e45ec0"));
//        for (final String top : tops) {
//            System.out.println(top);
//        }
//        System.out.println("--");
//        tops = Database.getTopNCategoriesInContinent(10, new ObjectId("4db5f38f2420b08675e35ec0"));
//        for (final String top : tops) {
//            System.out.println(top);
//        }
//        System.out.println("--");
//        tops = Database.getTopNCategoriesInCity(10, new ObjectId("4db5f4a82420b08698e45ec0"));
//        for (final String top : tops) {
//            System.out.println(top);
//        }
//        System.out.println("--");
//        tops = Database.getTopNCategories(10);
//        for (final String top : tops) {
//            System.out.println(top);
//        }
        ArrayList<DBWrapper> topWithCat = Database.getTopNWithKeyValueAndCategory(10, DBWrapper.TYPE_ARCH_ATTRACTION,
                                                    DBWrapper.FIELD_CONTINENT_ID,
                                                    new ObjectId("4db6f548b980b08631926585"),
                                                    "достопримечательности");

        System.out.println(Database.getTopNCategories(10));
//        for (DBWrapper top : topWithCat) {
//            System.out.println(top.getName());
//        }
    }

    public static void main(String[] args) throws IOException {
        CategoriesTest test = new CategoriesTest();
        test.test();
    }
}
