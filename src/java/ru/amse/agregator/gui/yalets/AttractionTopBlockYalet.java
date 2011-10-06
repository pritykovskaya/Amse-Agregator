package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class AttractionTopBlockYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res) {
        Database.connectToMainBase();
        ArrayList<DBWrapper> attractions;
        final String category = req.getParameter("rankingValue");
//        System.out.println(category);
        if (requestFromWorld(req)) {
            attractions = Database.getTopNWithTypeAndCategory(5, DBWrapper.TYPE_ARCH_ATTRACTION, category);
        } else {
            attractions = getTopAttractions(req, category);
        }

        ArrayList<Record> webRes = new ArrayList<Record>();

		for (DBWrapper attraction : attractions) {
			Record attrRec = new Record();
			attrRec.addCell("name", attraction.getName());
			attrRec.addCell("id", attraction.getId().toString());
			ArrayList<String> desc = attraction.getDescriptionArray();
			if(desc != null && desc.size() != 0){
				attrRec.addCell("mini-desc", desc.get(0).substring(0,desc.get(0).length()>150 ? 150: desc.get(0).length()));
			} else {
				attrRec.addCell("mini-desc", "...");
			}
			ArrayList<String> images = attraction.getImagesArray();
			if(images != null && images.size() != 0){
				attrRec.addCell("image", images.get(0));
			} else {
				attrRec.addCell("image", "images/not_image.gif");
			}

			webRes.add(attrRec);
		}
        res.add(webRes);
	}

    private ArrayList<DBWrapper> getTopAttractions(final InternalRequest req, final String category) {
        if (req == null) {
            return Database.getTopNWithTypeAndCategory(5, DBWrapper.TYPE_ARCH_ATTRACTION, category) ;
        }

        ObjectId id = new ObjectId(req.getParameter("id"));
        if (id == null) {
            return Database.getTopNWithTypeAndCategory(5, DBWrapper.TYPE_ARCH_ATTRACTION, category);
        } else if (req.getParameter("type").equals("Continent")) {
            return Database.getTopNWithKeyValueAndCategory(5,
                    DBWrapper.TYPE_ARCH_ATTRACTION,
                    DBWrapper.FIELD_CONTINENT_ID, id, category);
        } else if (req.getParameter("type").equals("Country")) {
            System.out.println("tro-lo-lo");
            return Database.getTopNWithKeyValueAndCategory(5,
                    DBWrapper.TYPE_ARCH_ATTRACTION,
                    DBWrapper.FIELD_COUNTRY_ID, id, category);
        } else if (req.getParameter("type").equals("City")) {
            return Database.getTopNWithKeyValueAndCategory(5,
                    DBWrapper.TYPE_ARCH_ATTRACTION,
                    DBWrapper.FIELD_CITY_ID, id, category);
        } else {
            Object cityId = Database.getDBObjectByIdAndType(id, DBWrapper.TYPE_ARCH_ATTRACTION).getCityId();
            return Database.getTopNWithKeyValueAndCategory(5,
                    DBWrapper.TYPE_ARCH_ATTRACTION,
                    DBWrapper.FIELD_CITY_ID, cityId, category);
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
