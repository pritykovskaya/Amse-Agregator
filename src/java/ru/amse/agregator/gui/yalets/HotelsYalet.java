package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.gui.model.RightMenuItem;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.util.ArrayList;

public class HotelsYalet extends AbstractAgregatorYalet{
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    public void process(InternalRequest req, InternalResponse res) {

            AttractionManager.connectToDatabase();
            ArrayList<DBWrapper> dbhotels = Database.getAllWithType(DBWrapper.TYPE_HOTEL);

            ArrayList<MenuItem> hotels = new ArrayList<MenuItem>();
           if (dbhotels.size() > 0) {
               for (DBWrapper hotel : dbhotels) {
                hotels.add(new MenuItem(hotel.getName(), hotel.getId().toString(), DBWrapper.TYPE_HOTEL));
            }
           }   else {
               hotels.add(new MenuItem("error", "0"));
           }


        res.add(hotels);
    }
}