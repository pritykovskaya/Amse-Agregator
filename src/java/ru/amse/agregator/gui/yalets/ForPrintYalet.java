package ru.amse.agregator.gui.yalets;

import com.mongodb.DBObject;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.*;

import java.util.ArrayList;

public class ForPrintYalet extends AbstractAgregatorYalet {
    public void process(InternalRequest req, InternalResponse res) {
        if (req.getParameter("user") != null && !req.getParameter("user").equals("")) {
            Database.connectToMainBase();
            ArrayList<Record> webRes = new ArrayList<Record>();
            DBObject object = Database.findInCollection(Database.COLLECTION_USERS, StorageObject.FIELD_ID, new ObjectId(req.getParameter("user")));
            if (object != null) {
                System.out.println(object.toString());
                User user = new User(object);
                System.out.println(user.getId() + "        " + user.getLogin() + "   " + user.getTour());
                if (user.getTour() != null && user.getTour().size() > 0) {
                    ObjectId idTour = user.getTour().get(0);
                    DBObject tourObject = Database.findInCollection(Database.COLLECTION_TOURS, StorageObject.FIELD_ID, idTour);
                    if (tourObject != null) {
                        Tour tour = new Tour(tourObject);

                        ArrayList<ObjectId> allArch = tour.getAttraction();
                        if (req.getParameter("id_attr") != null && !req.getParameter("id_attr").equals("")) {
                            ObjectId idAttr = new ObjectId(req.getParameter("id_attr"));
                            allArch.remove(allArch.indexOf(idAttr));
                            tour.setAttraction(allArch);
                            Database.addTour(tour);

                        }
                        for (ObjectId id : allArch) {
                            DBWrapper atch = null;
                            DBObject objectAttr = Database.findInCollection(Database.COLLECTION_ATTRACTIONS,
                                    StorageObject.FIELD_ID, id);
                            if (objectAttr != null)
                                atch = new DBWrapper(objectAttr);
                            else {
                                DBObject objectCity = Database.findInCollection(Database.COLLECTION_CITIES, StorageObject.FIELD_ID, id);
                                if (objectCity != null) {
                                    atch = new DBWrapper(objectCity);
                                } else {
                                    DBObject objectCountry = Database.findInCollection(Database.COLLECTION_COUNTRIES, StorageObject.FIELD_ID, id);
                                    if (objectCountry != null) {
                                        atch = new DBWrapper(objectCountry);
                                    }
                                }
                            }

                            if (atch != null) {
                                Record newRecord = new Record();
                                newRecord.addCell("name", atch.getName());
                                if (atch.getCoordsArray() != null
                                        && atch.getCoordsArray().size() > 0) {
                                    if (atch.getCoordsArray().get(0) != null) {
                                        System.out.println(atch.getCoordsArray().get(0).x);
                                        newRecord.addCell("lng", atch.getCoordsArray().get(0).x);
                                        newRecord.addCell("y", atch.getCoordsArray().get(0).y);
                                    } else {
                                        newRecord.addCell("lng", " ");
                                        newRecord.addCell("y", " ");
                                    }
                                } else {
                                    newRecord.addCell("lng", " ");
                                    newRecord.addCell("y", " ");
                                }
                                if (atch.getInfo() != null) {
                                    newRecord.addCell("info", atch.getAddress());
                                } else {
                                    newRecord.addCell("info", " ");
                                }
                                newRecord.addCell("id_attr", id.toString());
                                newRecord.addCell("id_user", req.getParameter("user"));
                                webRes.add(newRecord);

                            }
                        }
                    }
                }
            }

            res.add(webRes);
        }
    }
}