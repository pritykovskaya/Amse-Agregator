package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.StorageObject;
import ru.amse.agregator.storage.Tour;
import ru.amse.agregator.storage.User;

public class AddYalet  extends AbstractAgregatorYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
    	if (req.getParameter("flag")!=null && req.getParameter("flag").equals("1")&&req.getParameter("user")!=null &&!req.getParameter("user").equals("")){
    		Database.connectToMainBase();
        	User user = new User(Database.findInCollection(Database.COLLECTION_USERS,StorageObject.FIELD_ID, new ObjectId(req.getParameter("user"))));
        	if(user.getTour().size()==0){
        		Tour newTour = new Tour();
        		Database.addTour(newTour);
        		user.addTour(newTour.getId());
        		Database.addUser(user);
        	}
        	ObjectId idTour =user.getTour().get(0);
        	Tour tour = new Tour(Database.findInCollection(Database.COLLECTION_TOURS,StorageObject.FIELD_ID, idTour));
        	tour.addAttraction(new ObjectId(req.getParameter("id")));
        	Database.addTour(tour);

    	}
    }
}
