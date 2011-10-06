package ru.amse.agregator.gui.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.searcher.UserQuery;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.utils.HtmlTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttractionManager {
    Logger log = Logger.getLogger(AttractionManager.class);

    public static enum DatabaseEnum {
        mainDB,
        dirtyDB
    }

    public static DatabaseEnum databaseName = DatabaseEnum.mainDB;

    public static void connectToDatabase() {

        if (AttractionManager.databaseName == DatabaseEnum.dirtyDB) {
            Database.connectToDirtyBase();
        } else {
            Database.connectToMainBase();
        }
    }

    //Получение данных из базы
    public List<Attraction> getSearchResult(String param, ArrayList<String> vector) {
        Searcher.setIndexDir(new File("index"));
        log.error("vector - " + vector);
        ArrayList<DBWrapper> dbwr;

        if (vector.size() > 0) {
            dbwr = Searcher.search(new UserQuery(param, vector));
        } else {
            dbwr = Searcher.search(new UserQuery(param));
        }
        List<Attraction> result = new ArrayList<Attraction>();
        if (dbwr.size() == 0) {
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        String tmp;
        log.error("length " + dbwr.size());
        for (int i = 0; i < dbwr.size(); ++i) {
            log.error("number " + i);
            log.error("name " + dbwr.get(i).getName());
            log.error("id " + dbwr.get(i).getId());
            log.error("type " + dbwr.get(i).getType());

            Attraction attraction = new Attraction();

            tmp = String.valueOf(dbwr.get(i).getId());
            if (tmp == null) {
                continue;
            }
            attraction.setId(tmp);

            tmp = dbwr.get(i).getType();
            if (tmp == null) {
                log.error("CONTINUE");
                continue;
            }
            attraction.setType(tmp);

            tmp = dbwr.get(i).getName();
            if (tmp == null) {
                continue;
            }
            attraction.setName(tmp);

            if (dbwr.get(i).getDescriptionArray() == null) {
                continue;
            }

            tmp = (String) dbwr.get(i).getDescriptionArray().get(0);

            if (tmp != null) {
                String withoutTags = HtmlTools.clearString(tmp);
                withoutTags = withoutTags.replaceAll("<tr.*>", "<br/>");
                withoutTags = withoutTags.replaceAll("<td.*>", "<br/>");
                withoutTags = withoutTags.replaceAll("<br/>", "~~~~~~~~~");
                withoutTags = withoutTags.replaceAll("</.*>", "");
                withoutTags = withoutTags.replaceAll("<.*>", "");
                withoutTags = withoutTags.replaceAll( "~~~~~~~~~","<br/>");
                if (withoutTags.length() > 300) {
                    attraction.setDescription(new String(withoutTags.substring(0, 300) + " ..."));
                } else {
                    attraction.setDescription(tmp);
                }
            }

            result.add(attraction);
        }
        return result;
    }

    // For next description on main attaraction page
    public List<Attraction> getSomeAttractionByIdMoreDescription(String id, String type, String tab, String descriptionId) {
        AttractionManager.connectToDatabase();
        ObjectId selectedItem = new ObjectId(id);

        DBWrapper dbwr = Database.getDBObjectByIdAndTypeAndIncRating(selectedItem, type);

        List<Attraction> result = new ArrayList<Attraction>();

        if (dbwr == null) {
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }
        Attraction attraction = new Attraction();
        HashMap<String, String> tabMap = new HashMap<String, String>();
        try {
            tabMap = getTabsArray(dbwr);
            attraction.setFieldMap(tabMap);
            attraction.setId(dbwr.getId().toString());
            attraction.setType(type);
            attraction.setName(dbwr.getName());
        } catch (NullPointerException e) {
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        if (isEmptyTabMap(tabMap)) {
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        ArrayList<String> arrayList = new ArrayList<String>();

        if (tab.equals("moredescription") && tabMap.get("description").equals("true")) {
            ArrayList<String> ar = new ArrayList<String>();
            ar = dbwr.getDescriptionArray();
            for (String a : ar) {
                StringBuffer sb = new StringBuffer();
                sb.append(dbwr.getDescriptionArray().get(Integer.parseInt(descriptionId)));
                sb.append("<hr>");
                sb.append("<small>©");
                sb.append(dbwr.getSourceUrlArray().get(Integer.parseInt(descriptionId)));
                sb.append("</small><br/><br/>");
                arrayList.add(sb.toString());
            }
        }
        result.add(attraction);
        return result;
    }

    // Get Attraction - main method
    public List<Attraction> getSomeAttractionById(String id, String type, String tab) {
        List<Attraction> result = new ArrayList<Attraction>();
        try {
            AttractionManager.connectToDatabase();
            ObjectId selectedItem = new ObjectId(id);

            DBWrapper dbwr = Database.getDBObjectByIdAndTypeAndIncRating(selectedItem, type);

            if (dbwr == null) {
                Attraction attraction = new Attraction();
                attraction.setType("Error");
                result.add(attraction);
                return result;
            }
            Attraction attraction = setParents(dbwr, type);
            HashMap<String, String> tabMap;
            try {
                tabMap = getTabsArray(dbwr);
                attraction.setFieldMap(tabMap);
                attraction.setId(dbwr.getId().toString());
                attraction.setType(type);
                attraction.setName(dbwr.getName());
            } catch (NullPointerException e) {
                attraction.setType("Error");
                result.add(attraction);
                return result;
            }

            if (isEmptyTabMap(tabMap)) {
                attraction.setType("Error");
                result.add(attraction);
                return result;
            }

            if (tab.equals("images") && tabMap.get("images").equals("true")) {
                attraction.setImagesArray(dbwr.getImagesArray());
            } else if (tab.equals("description") && tabMap.get("description").equals("true")) {
                StringBuffer sb = new StringBuffer();
                ArrayList<String> descArray = dbwr.getDescriptionArray();
                ArrayList<String> srcArray = dbwr.getSourceUrlArray();

                for (int i = 0; i < descArray.size(); ++i) {
                    sb.append(descArray.get(i));
                    sb.append("<hr>");
                    if(srcArray.size() > i){
                        sb.append("<small>©");
                        sb.append(srcArray.get(i));
                        sb.append("</small><br/><br/>");
                    }

                }
                attraction.setDescription(sb.toString());

                sb = new StringBuffer();

            } else if (tab.equals("list")) {
                attraction.setAttractionList(addListOfAttractions(attraction));
            } else if (tab.equals("all")) {
                if (tabMap.get("images").equals("true")) {
                    attraction.setImagesArray(dbwr.getImagesArray());
                }

                ArrayList<String> arrayList = new ArrayList<String>();

                if (tabMap.get("description").equals("true")) {
                    ArrayList<String> ar = dbwr.getDescriptionArray();
                    ArrayList<String> sr = dbwr.getSourceUrlArray();
                    for (String str : ar) {
                        StringBuilder sb = new StringBuilder();
                        if (str.length() > 400) {
                            final String subString = str.substring(0, 350);
                        	 str = str.replaceAll("<tr.*>", "<br/>");
                        	 str = str.replaceAll("<td.*>", "<br/>");
                        	 str = str.replaceAll("<br/>", "~~~~~~~~~");
                        	 str = str.replaceAll("</.*>", "");
                        	 str = str.replaceAll("<.*>", "");
                        	 str = str.replaceAll( "~~~~~~~~~","<br/>");
                            sb.append(subString);
                            sb.append("...");
                        } else {
                            sb.append(str);
                        }
                        sb.append("<hr>");
                        if(!sr.isEmpty()){
                            sb.append("<small>©");
                            sb.append(sr.get(0));
                            sb.append("</small><br/><br/>");
                        }

                        arrayList.add(sb.toString());
                    }
                    attraction.setDescriptionArray(arrayList);
                }
            } else if (tab.equals("zoom")) {
                attraction.setZoom("10");
            }

            attraction.getParents();
            result.add(attraction);
            return result;
        } catch (Exception e) {
            System.err.println("Exeption in method getSomeAttractionById");
            e.printStackTrace();
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }
    }


    private Attraction setParents(DBWrapper dbWrapper, String type) {
        try {
            Attraction attraction = new Attraction();
            ArrayList<Cell> arrayList = new ArrayList<Cell>();
            if (type.equals("Continent")) {
                return attraction;
            } else if (type.equals("Country")) {
                ObjectId id = dbWrapper.getContinentId();
                String name = dbWrapper.getStaticContinentName();
                if ((id != null)&& (!name.equals(""))) {
                    arrayList.add(new Cell(name, id.toString()));
                    arrayList.add(new Cell("type", "Continent"));
                }
                if (arrayList != null) attraction.setParents(arrayList);
                return attraction;
            } else if (type.equals("City")) {
                ObjectId id = dbWrapper.getCountryId();
                String name = dbWrapper.getStaticCountryName();
                if ((id != null)&& (!name.equals(""))) {
                    arrayList.add(new Cell(name, id.toString()));
                    arrayList.add(new Cell("type", "Country"));
                }
                if (arrayList != null) attraction.setParents(arrayList);
                return attraction;
            } else {
                ObjectId id = dbWrapper.getCityId();
                String name = dbWrapper.getStaticCityName();
                if ((id != null)&& (!name.equals(""))) {
                    arrayList.add(new Cell(name, id.toString()));
                    arrayList.add(new Cell("type", "City"));
                }
                if (arrayList != null) attraction.setParents(arrayList);
                return attraction;
            }
        } catch (Exception e) {
            System.err.println("Exeption in method setParents");
            e.printStackTrace();
            return new Attraction();
        }

    }

    // Hash Map of tabs <name of tab, True/False>
    private HashMap<String, String> getTabsArray
    (DBWrapper
             attraction) {

        HashMap<String, String> map = new HashMap<String, String>();
        String type = attraction.getType();
        String parameter;
        if (!attraction.getName().equals("")) {
            map.put("name", "true");
        } else {
            map.put("name", "false");
        }
        if (!type.equals("Continent")) {
            try {
                if (attraction.getDescriptionArray().size() > 0) {
                    map.put("description", "true");
                } else {
                    map.put("description", "false");
                }
            } catch (NullPointerException e) {
                map.put("description", "false");
            }
            if ((attraction.getImagesArray() != null) && (attraction.getImagesArray().size() > 0)) {

                if (!attraction.getImagesArray().get(0).equals("")) {
                    map.put("images", "true");
                } else {
                    map.put("images", "false");
                }
            } else {
                map.put("images", "false");
            }
        }

        if (type.equals("City") || type.equals("Country") || type.equals("Continent")) {
            Attraction a = new Attraction();
            a.setId(attraction.getId().toString());
            a.setType(type);
            ArrayList<MenuItem> links = addListOfAttractions(a);
            if ((links != null) && (links.size() > 0)) {
                 if (!links.get(0).getId().equals("")) {
                    map.put("list", "true");
                } else {
                    map.put("list", "false");
                }

            } else {
                map.put("list", "false");
            }

        } else {
            map.put("list", "false");
        }
        return map;
    }

    // Check tab for absent content
    private boolean isEmptyTabMap(HashMap<String, String> map) {
        /*if (map.get("images").equals("true")) {
            return false;
        } else if (map.get("description").equals("true")) {
            return false;
        } else if (map.get("list").equals("true")) {
            return false;
        } else {
            return true;
        }*/
        return false;
    }

    //Get list of attractions
    private ArrayList<MenuItem> addListOfAttractions(Attraction attraction) {
        ArrayList<MenuItem> links = new ArrayList<MenuItem>();

        if (attraction.getType().equals("City")) {
            ArrayList<String> list = Database.getAllTypesOfObjectByCity(new ObjectId(attraction.getId()));
            for (String tmp : list) {
                ArrayList<DBWrapper> array = Database.getAllObjectOfSelectedTypeInCity(new ObjectId(attraction.getId()), tmp);
                for (DBWrapper dbwrArray : array) {
                    links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), dbwrArray.getType()));
                }
            }
        }

        if (attraction.getType().equals("Country")) {
            ArrayList<DBWrapper> array = Database.getAllCitiesByCountry(new ObjectId(attraction.getId()));
            for (DBWrapper dbwrArray : array) {
                links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), DBWrapper.TYPE_CITY));
            }
        }

        if (attraction.getType().equals("Continent")) {
            ArrayList<DBWrapper> array = Database.getAllCountriesByContinent(new ObjectId(attraction.getId()));
            for (DBWrapper dbwrArray : array) {
                links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), DBWrapper.TYPE_COUNTRY));
            }
        }

        return links;

    }


}
