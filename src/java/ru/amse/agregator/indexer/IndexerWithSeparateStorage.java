package ru.amse.agregator.indexer;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Nov 22, 2010
 * Time: 9:20:19 PM
 */

public class IndexerWithSeparateStorage extends OriginalIndexer {
    protected static void index(File indexDir) throws IOException{
        ArrayList<String> types = DBWrapper.getTypeNames();
        for (String currentType: types) {
            File dirForCurrentType = getAndCreateDirForType(indexDir, currentType);
            indexType(dirForCurrentType, currentType);
        }
    }

    private static File getAndCreateDirForType(File rootDirectory, String nameOfType) throws IOException {
        File dirForType = new File(rootDirectory.getCanonicalPath() + "/" + nameOfType);
        dirForType.mkdirs();
        return null;
    }

    private static void indexType(File dirForType, String type) {
        ArrayList<DBWrapper> allObjectOfCurrentType = Database.getAllWithType(type);
    }
}
