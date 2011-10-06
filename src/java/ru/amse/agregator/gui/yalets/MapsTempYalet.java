package ru.amse.agregator.gui.yalets;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

public class MapsTempYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    //@Override
    public void process(InternalRequest req, InternalResponse res) {
    	Attraction attraction = new Attraction();
    	attraction.setId(req.getParameter("id"));
    	Database.connectToMainBase();	
    	ArrayList<Record> webRes = new ArrayList<Record>(); 
    	ObjectId selectedItem = new ObjectId(req.getParameter("id"));
    	String type = req.getParameter("type");
    	if ( type.equals(DBWrapper.TYPE_COUNTRY)){
    		ArrayList<DBWrapper> allCities = Database.getAllCitiesByCountry(selectedItem);
    		if (allCities != null){
    			ArrayList<DBWrapper> allAttr = new ArrayList<DBWrapper>();
    			for (DBWrapper obj : allCities){
    				ArrayList<DBWrapper> allAttrByCity = Database.getAllAttrByCity(obj.getId());
    				for ( DBWrapper obj1 : allAttrByCity){
    					allAttr.add(obj1);
    				}
    			}
    			if (allAttr != null){
    				double minlng = 300;
    				double minlat = 300;
    				double maxlng = -300;
    				double maxlat = -300;
    				for ( DBWrapper obj : allAttr){
    					if (obj.getCoordsArray() != null){
    						ArrayList<Point2D.Double> coords = obj.getCoordsArray();
    						for ( Point2D.Double coord : coords){
    							if (minlng > coord.x) minlng = coord.x;
    							if (minlat > coord.y) minlat = coord.y;
    							if (maxlng < coord.x) maxlng = coord.x;
    							if (maxlat < coord.y) maxlat = coord.y;  							
    						}
    					}
    					
    				}
    				if (minlng < 300) {
    					Record newRecord = new Record();
    					newRecord.addCell("minlng", minlng);
    					newRecord.addCell("minlat", minlat);
    					newRecord.addCell("maxlng", maxlng);
    					newRecord.addCell("maxlat", maxlat);
    					webRes.add(newRecord);
    				}
    				
    			}
    		}
    	}
    	else if ( type.equals(DBWrapper.TYPE_CITY)){
        		ArrayList<DBWrapper> allAttrByCity = Database.getAllAttrByCity(selectedItem);
        		ArrayList<DBWrapper> allAttr = new ArrayList<DBWrapper>();
        		for ( DBWrapper obj1 : allAttrByCity){
        			allAttr.add(obj1);
        		}
        		if (allAttr != null){
        			double minlng = 300;
    				double minlat = 300;
    				double maxlng = -300;
    				double maxlat = -300;
        			for ( DBWrapper obj : allAttr){	
        				if (obj.getCoordsArray() != null){
        					ArrayList<Point2D.Double> coords = obj.getCoordsArray();
        					for ( Point2D.Double coord : coords){
        						if (minlng > coord.x) minlng = coord.x;
    							if (minlat > coord.y) minlat = coord.y;
    							if (maxlng < coord.x) maxlng = coord.x;
    							if (maxlat < coord.y) maxlat = coord.y;
            				    
        					}
        				}
        				
        			}
        			if (minlng < 300) {
    					Record newRecord = new Record();
    					newRecord.addCell("minlng", minlng);
    					newRecord.addCell("minlat", minlat);
    					newRecord.addCell("maxlng", maxlng);
    					newRecord.addCell("maxlat", maxlat);
    					webRes.add(newRecord);
    				}
        		}
    	}
    	else if (!type.equals(DBWrapper.TYPE_CONTINENT)){
    		DBWrapper obj = Database.getDBObjectByIdAndType(selectedItem, type);
    		
    		if (obj.getCoordsArray() != null){
    			double minlng = 300;
				double minlat = 300;
				double maxlng = -300;
				double maxlat = -300;
				ArrayList<Point2D.Double> coords = obj.getCoordsArray();
				for ( Point2D.Double coord : coords){
					if (minlng > coord.x) minlng = coord.x;
					if (minlat > coord.y) minlat = coord.y;
					if (maxlng < coord.x) maxlng = coord.x;
					if (maxlat < coord.y) maxlat = coord.y;
				}
				if (minlng < 300) {
					Record newRecord = new Record();
					newRecord.addCell("minlng", minlng);
					newRecord.addCell("minlat", minlat);
					newRecord.addCell("maxlng", maxlng);
					newRecord.addCell("maxlat", maxlat);
					webRes.add(newRecord);
				}
			}
    	}
    	res.add(webRes);
    	
       }
    }
