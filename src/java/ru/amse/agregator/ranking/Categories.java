package ru.amse.agregator.ranking;

import ru.amse.agregator.storage.DBWrapper;

/**
 * User: nepank
 * Date: 23.03.11
 * Time: 13:03
 */
public class Categories {

    public static final String ATTRACTION   = "достопримечательности";
    public static final String BUILDING     = "здания";
    public static final String BRIDGE       = "мосты";
    public static final String CHURCH       = "церкви";
    public static final String ARCHITECTURE = "архитектура";
    public static final String ZOO          = "зоопарки";
    public static final String SQUARE       = "площади";
    public static final String MONASTERY    = "монастыри";
    public static final String MUSEUM       = "музеи";
    public static final String CHILDREN     = "дети";

    private DBWrapper dbObj = null;

    public static boolean isInCategory(DBWrapper dbObj, String category) {
        return (dbObj != null && dbObj.getKeyWordsArray()!= null && dbObj.getKeyWordsArray().contains(category));
    }

}
