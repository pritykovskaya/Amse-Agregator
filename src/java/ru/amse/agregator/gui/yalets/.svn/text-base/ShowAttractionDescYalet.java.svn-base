package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

public class ShowAttractionDescYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter("tab");
        if (tmp != null && !"".equals(tmp)) {
            if (tmp.equals("moredescription")) {
                System.out.println(req.getParameter("descriptionId"));
                res.add(manager.getSomeAttractionByIdMoreDescription(req.getParameter("id"), req.getParameter("type"), req.getParameter("tab"), req.getParameter("descriptionId")));
            } else {
                res.add(manager.getSomeAttractionById(req.getParameter("id"), req.getParameter("type"), req.getParameter("tab")));
            }
        } else {
            if (req.getParameter("type").equals("Continent")) {
                res.add(manager.getSomeAttractionById(req.getParameter("id"), req.getParameter("type"), "list"));
            } else {
                res.add(manager.getSomeAttractionById(req.getParameter("id"), req.getParameter("type"), "all"));
            }
        }
    }
}
