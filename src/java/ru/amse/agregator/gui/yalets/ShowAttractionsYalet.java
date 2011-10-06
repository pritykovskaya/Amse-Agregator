package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowAttractionsYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        System.err.println("sldkfjsdlkfjsldkfjslfj dkfjl skdfj lskdjflsk f");
        String tmp = req.getParameter(String.valueOf("findTextBox"));
        if (tmp != null && !"".equals(tmp)) {
            ArrayList<String> labels = setLabels(req);

//            log.error(req.getAllParameters());

            List<Attraction> result = manager.getSearchResult(tmp, labels);
            log.error(result + "RESULT");
            if (result.size() == 0) {
                Attraction attraction = new Attraction();
                attraction.setType("Error");
                result.add(attraction);
            }
            res.add(result);
//            }
        } else {
            tmp = req.getParameter(String.valueOf("findTextBox1"));
            if (tmp != null && !"".equals(tmp)) {
                ArrayList<String> labels = setLabels(req);

//                log.error(req.getAllParameters());

                List<Attraction> result = manager.getSearchResult(tmp, labels);
                System.err.println("ssss" + result);
                if (result.size() == 0) {
                    Attraction attraction = new Attraction();
                    attraction.setType("Error");
                    result.add(attraction);
                }
                res.add(result);
//            }
            } else {
                ArrayList<String> labels = setLabels(req);

//            log.error(req.getAllParameters());

                List<Attraction> result = manager.getSearchResult("Рим", labels);
                log.error(result + "RESULT");
                if (result.size() == 0) {
                    Attraction attraction = new Attraction();
                    attraction.setType("Error");
                    result.add(attraction);
                }
                res.add(result);
            }

        }
    }

    private ArrayList<String> setLabels
            (InternalRequest
                     req) {
        ArrayList<String> labels = new ArrayList<String>();
        if (req.getParameter("countryCheckbox") != null) {
            labels.add("Country");
        }
        if (req.getParameter(String.valueOf("cityCheckbox")) != null) {
            labels.add("City");
        }
        if (req.getParameter(String.valueOf("archAttractionCheckbox")) != null) {
            labels.add("ArchAttraction");
        }
        if (req.getParameter(String.valueOf("naturalAttractionCheckbox")) != null) {
            labels.add("NaturalAttraction");
        }
        if (req.getParameter(String.valueOf("museumCheckbox")) != null) {
            labels.add("Museum");
        }
        if (req.getParameter(String.valueOf("entertainmentCheckbox")) != null) {
            labels.add("Entertainment");
        }
        if (req.getParameter(String.valueOf("shoppingCheckbox")) != null) {
            labels.add("Shopping");
        }
        if (req.getParameter(String.valueOf("hotelCheckbox")) != null) {
            labels.add("Hotel");
        }
        if (req.getParameter(String.valueOf("cafeCheckbox")) != null) {
            labels.add("Cafe");
        }

        return labels;
    }
}
