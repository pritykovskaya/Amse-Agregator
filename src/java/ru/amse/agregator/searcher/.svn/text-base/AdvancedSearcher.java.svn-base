package ru.amse.agregator.searcher;

import ru.amse.agregator.storage.DBWrapper;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Bondarev Timofey
 * Date: 07.03.11
 * Time: 10:45 AM
 */

public class AdvancedSearcher extends Searcher {
    public static ArrayList<DBWrapper> search(UserQuery query) { //throws IOException, ParseException {
        ObjectId cityId = null;
        try {
            cityId = getCityId(query.getQueryExpression());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (cityId == null) {
            return Searcher.search(query);
        }

        query.setQueryExpression("" + cityId);

        try {
            return searchInField(INDEX_DIR, query, DBWrapper.FIELD_CITY_ID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static ArrayList<DBWrapper> searchInField(File indexDir, UserQuery q, String fieldForSearch) throws IOException, ParseException {
        Directory fsDirectory = new NIOFSDirectory(indexDir);
        IndexSearcher is = new IndexSearcher(fsDirectory);
        QueryParser qParser = new QueryParser(Version.LUCENE_30,
                                  fieldForSearch,
                                  new StandardAnalyzer(Version.LUCENE_30));
        Query query = qParser.parse(q.getQueryExpression());

        TopFieldCollector tfc = TopFieldCollector.create(Sort.RELEVANCE, 2000, true, true, true, false);
        is.search(query, tfc);
        TopDocs docs = tfc.topDocs();

        ScoreDoc[] sDocs = docs.scoreDocs;
        return getResultOfSearch(q, sDocs, is);
    }

    protected static ArrayList<DBWrapper> getResultOfSearch(UserQuery query, ScoreDoc[] sDocs, IndexSearcher is) throws IOException {
        ArrayList<DBWrapper> listOfWrapper = new ArrayList<DBWrapper>();
        ArrayList<String> labels = query.getLabels();
        for (ScoreDoc currentScoreDoc : sDocs) {
            String type = is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_TYPE).stringValue();
            if (labels != null && labels.size() != 0 && !labels.contains(type)) {
                continue;
            }
            DBWrapper wrapper = new DBWrapper();
            wrapper.setId(new ObjectId(is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_ID).stringValue()));
            if (is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_NAME) != null) {
                wrapper.setName(is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_NAME).stringValue());
            }
            if (is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_TYPE) != null) {
                wrapper.setType(is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_TYPE).stringValue());

            }
            if (is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_DESC) != null) {
                wrapper.setDescription(is.doc(currentScoreDoc.doc).getField(DBWrapper.FIELD_DESC).stringValue());
            }
            listOfWrapper.add(wrapper);
        }
        return listOfWrapper;
    }

    private static ObjectId getCityId(String cityName) throws IOException, ParseException {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(DBWrapper.TYPE_CITY);
        ArrayList<DBWrapper> cities = Searcher.search(new UserQuery(cityName, labels));
        if (cities.size() < 1) {
            UserQuery query = addTilda(new UserQuery(cityName));
            cities = Searcher.search(new UserQuery(query.getQueryExpression(), labels));
            if (cities.size() < 1) {
                return null;
            }
        }
        return cities.get(0).getId();
    }

    /*private static String getCityById(String id) throws IOException, ParseException {
        ArrayList<DBWrapper> cityNames = searchInField(INDEX_DIR, new UserQuery(id), DBWrapper.FIELD_CITY_ID);
        if (cityNames.size() == 0) {
            return null;
        }
        return cityNames.get(0).getName();
    }*/
}
