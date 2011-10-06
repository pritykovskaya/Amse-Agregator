package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: nepank
 * Date: 03.04.11
 * Time: 17:25
 */
public class RankingBlockYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res) {
        try {
            Database.connectToMainBase();
            final ArrayList<Record> webRes = new ArrayList<Record>();
            ArrayList<String> rankings = new ArrayList<String>();
            if (requestFromWorld(req)) {
                rankings = Database.getTopNCategories(10);
            } else {
                rankings = getRankings(req);
            }

            for (String rank : rankings) {
                final Record record = new Record();
                record.addCell("category", rank);
                webRes.add(record);
            }

            res.add(webRes);
        } catch (Exception e) {
            //
        }

    }

    private ArrayList<String> getRankings(InternalRequest req) {
        if (req == null) {
            return Database.getTopNCategories(10);
        }
        ObjectId id = new ObjectId(req.getParameter("id"));
        if (id == null) {
            return Database.getTopNCategories(10);
        } else if (req.getParameter("type").equals("Continent")) {
            return Database.getTopNCategoriesInContinent(10, id);
        } else if (req.getParameter("type").equals("Country")) {
            return Database.getTopNCategoriesInCountry(10, id);
        } else if (req.getParameter("type").equals("City")) {
            return Database.getTopNCategoriesInCity(10, id);
        } else {
            ObjectId cityId = Database.getDBObjectByIdAndType(id, DBWrapper.TYPE_ARCH_ATTRACTION).getCityId();
            return Database.getTopNCategoriesInCity(10, cityId);
        }
    }


    private boolean requestFromCountry(InternalRequest req) {
        if (req.getParameter("type") != null) {
            return (req.getParameter("type").equals("Country"));
        } else return false;
    }

    private boolean requestFromCity(InternalRequest req) {
        if (req.getParameter("type") != null) {
            return (req.getParameter("type").equals("City"));
        } else return false;
    }

    private boolean requestFromArrtaction(InternalRequest req) {
        if (req.getParameter("type") != null) {
            return (req.getParameter("type").equals("Attraction"));
        } else return false;

    }

    private boolean requestFromContinent(InternalRequest req) {
        if (req.getParameter("type") != null) {
            return (req.getParameter("type").equals("Continent"));
        } else return false;
    }

    private boolean requestFromWorld(InternalRequest req) {
        return !(requestFromArrtaction (req)
                || requestFromCity     (req)
                || requestFromContinent(req)
                || requestFromCountry  (req));
    }

}
