package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.gui.model.LeftMenuItem;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.util.ArrayList;

public class LeftMenuYalet extends AbstractAgregatorYalet{
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    public void process(InternalRequest req, InternalResponse res) {
        if (AbstractAgregatorYalet.leftMenuItemArrayList.size() == 0) {
            AttractionManager.connectToDatabase();
            ArrayList<DBWrapper> continents = Database.getAllContinents();

            for (DBWrapper continent : continents) {
                AbstractAgregatorYalet.leftMenuItemArrayList.add(new LeftMenuItem(continent.getName(), continent.getId().toString(), DBWrapper.TYPE_CONTINENT));
            }
        }
        res.add(AbstractAgregatorYalet.leftMenuItemArrayList);
    }
}