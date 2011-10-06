package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class CountryTopBlockYalet implements Yalet {

	public void process(InternalRequest req, InternalResponse res) {
        if (requestFromWorld(req)) {
            res = buildWorldResponse(req, res);
        } else {
            res = buildContinentResponse(req, res);
        }
	}

    /**
     * use it only once in @process() method!
     * @param req
     * @param res
     * @return proper response for the "continent page",
     *              "country page",
     *              "city page",
     *              "attraction page"
     */
    private InternalResponse buildContinentResponse(InternalRequest req, InternalResponse res) {
        Database.connectToMainBase();

        ArrayList<DBWrapper> countries = getTopCountries(req);
		ArrayList<Record> webRes = new ArrayList<Record>();

		for (DBWrapper country : countries) {

			ArrayList<DBWrapper> cities = Database.getTopNWithKeyValue(3, DBWrapper.TYPE_CITY, DBWrapper.FIELD_COUNTRY_ID, country.getId());
			Record countryRec = new Record();
			countryRec.addCell("name", country.getName());
			countryRec.addCell("id", country.getId().toString());
			//System.out.println(country.getName());
			ArrayList<Record> citiesRecArray = new ArrayList<Record>();
			for(DBWrapper city : cities){
				Record cityRecord = new Record();
				cityRecord.addCell("name", city.getName());
				//System.out.println(city.getName());
				cityRecord.addCell("id", city.getId().toString());
				citiesRecArray.add(cityRecord);
			}
			countryRec.addCell("cities", citiesRecArray);
			webRes.add(countryRec);
		}
		res.add(webRes);
        return res;
    }

    private ArrayList<DBWrapper> getTopCountries(InternalRequest req) {
        ObjectId id = new ObjectId(req.getParameter("id"));
        if (req.getParameter("type").equals("Continent")) {
            return Database.getTopNWithKeyValue(5, DBWrapper.TYPE_COUNTRY, DBWrapper.FIELD_CONTINENT_ID, id);
        } else if (req.getParameter("type").equals("Country")) {
            ObjectId continentId = Database.getDBObjectByIdAndType(id, DBWrapper.TYPE_COUNTRY).getContinentId();
            return Database.getTopNWithKeyValue(5, DBWrapper.TYPE_COUNTRY, DBWrapper.FIELD_CONTINENT_ID, continentId);
        } else if (req.getParameter("type").equals("City")) {
            ObjectId continentId = Database.getDBObjectByIdAndType(id, DBWrapper.TYPE_CITY).getContinentId();
            return Database.getTopNWithKeyValue(5, DBWrapper.TYPE_COUNTRY, DBWrapper.FIELD_CONTINENT_ID, continentId);
        } else {
            ObjectId continentId = Database.getDBObjectByIdAndType(id, DBWrapper.TYPE_ATTRACTION).getContinentId();
            return Database.getTopNWithKeyValue(5, DBWrapper.TYPE_COUNTRY, DBWrapper.FIELD_CONTINENT_ID, continentId);
        }
    }

    /**
     * use it only once in @process() method!
     * @param req
     * @param res
     * @return proper response for the "index page"
     */
    private InternalResponse buildWorldResponse(InternalRequest req, InternalResponse res) {
        Database.connectToMainBase();

        ArrayList<DBWrapper> countries = Database.getTopNWithType(5, DBWrapper.TYPE_COUNTRY);
		ArrayList<Record> webRes = new ArrayList<Record>();

		for (DBWrapper country : countries) {

			ArrayList<DBWrapper> cities = Database.getTopNWithKeyValue(3, DBWrapper.TYPE_CITY, DBWrapper.FIELD_COUNTRY_ID, country.getId());

			Record countryRec = new Record();
			countryRec.addCell("name", country.getName());
			countryRec.addCell("id", country.getId().toString());
			//System.out.println(country.getName());
			ArrayList<Record> citiesRecArray = new ArrayList<Record>();
			for(DBWrapper city : cities){
				Record cityRecord = new Record();
				cityRecord.addCell("name", city.getName());
				//System.out.println(city.getName());
				cityRecord.addCell("id", city.getId().toString());
				citiesRecArray.add(cityRecord);
			}
			countryRec.addCell("cities", citiesRecArray);
			webRes.add(countryRec);
		}
		res.add(webRes);
        return res;
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
