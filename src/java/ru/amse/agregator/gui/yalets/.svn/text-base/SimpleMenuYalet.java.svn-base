package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.apache.log4j.Logger;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.storage.*;

public class SimpleMenuYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);


    public void process(InternalRequest req, InternalResponse res) {

        AttractionManager.connectToDatabase();
        ArrayList<DBWrapper> continents = Database.getAllContinents();

        for (DBWrapper continent : continents) {

            ArrayList<DBWrapper> countrytmp = Database.getAllCountriesByContinent(continent.getId());

//            ArrayList<MenuItem> listOfContinents = new ArrayList<MenuItem>();
//            listOfContinents.add(new MenuItem(continent.getName(), continent.getId().toString()));
            ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
            menuItems.add(new MenuItem(continent.getName(), continent.getId().toString()));
            for (DBWrapper country : countrytmp) {
                menuItems.add(new MenuItem(country.getName(), country.getId().toString()));
            }
            res.add(menuItems);
        }
    }
}