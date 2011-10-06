package ru.amse.agregator.miner;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import ru.amse.agregator.utils.HtmlTools;


public class MyUniversalListener implements ScraperRuntimeListener  {
		
	public void onNewProcessorExecution(Scraper scraper, BaseProcessor arg1) {}
	public void onExecutionContinued(Scraper arg0) {}	
	public void onExecutionError(Scraper arg0, Exception arg1) {}
	public void onExecutionPaused(Scraper arg0) {}
	public void onExecutionStart(Scraper arg0) {}
	public void onExecutionEnd(Scraper scraper) {}


	DataCleaner fcl;
	HtmlCleaner cleaner;
	CompactXmlSerializer cxs;
	
	public MyUniversalListener(){
		 fcl = new DataCleaner();
 		 cleaner = new HtmlCleaner();
 		 cxs = new CompactXmlSerializer(cleaner.getProperties());
	}
	
	
	public void onProcessorExecutionFinished(Scraper scr, BaseProcessor bp, @SuppressWarnings("rawtypes") Map arg2) {
			
		if (bp.getElementDef().getShortElementName().equalsIgnoreCase("var-def") && scr.getContext().getVar("addToDB").toInt() == 1){
			
			scr.pauseExecution();
			Variable incomeObject = (Variable) scr.getContext().get("returnValue");
			scr.getContext().setVar("addToDB", 0);
			scr.continueExecution();
			
			@SuppressWarnings("rawtypes")
			List myList = incomeObject.toList();
			//System.out.println(myList.toString());
			
			DBWrapper newEntry = new DBWrapper();
			for(int i=0; i<myList.size(); i+=2){
				
				if(myList.get(i) != null ){
							
				    if(myList.get(i).toString().equals(DBWrapper.FIELD_IMAGES)){
					    
				    	newEntry.setImagesArray(createImagesArray(HtmlTools.clearString(myList.get(i+1).toString())));
					    
				    } else if(myList.get(i).toString().equals(DBWrapper.FIELD_KEYWORDS)){
					
					    newEntry.setKeyWordsArray(createImagesArray(HtmlTools.clearString(myList.get(i+1).toString())));
					
				    } else if(myList.get(i).toString().equals(DBWrapper.FIELD_COORDS)){
		
					    ArrayList<Point2D.Double> coords = new ArrayList<Point2D.Double>();
					    String tmp = myList.get(i+1).toString();
					    double lon, lat;
                        if (tmp.indexOf(";") > 0) {
                            lon = Double.parseDouble(tmp.substring(0, tmp.indexOf(';')));
					        lat = Double.parseDouble(tmp.substring(tmp.indexOf(';') +1 ));
                            coords.add(new Point2D.Double(lon, lat));
					        newEntry.setCoordsArray(coords);
                        }
				    }
				    else {			
				    	try {
							
							StringWriter sw = new StringWriter();
							cleaner.clean(myList.get(i+1).toString()).serialize(cxs, sw);														
							XMLReader xr = XMLReaderFactory.createXMLReader();							
							xr.setContentHandler(fcl);
							StringReader rdr = new StringReader(sw.toString());
							xr.parse(new InputSource (rdr));
							newEntry.setKeyValue(myList.get(i).toString(),fcl.getData());

							System.out.println(fcl.getData());
							fcl.clear();
													
						} catch (IOException e) {
							e.printStackTrace();
						} catch (SAXException e) {
							e.printStackTrace();
						}

				    }
				}
			}
			Database.add(newEntry);
		}
	}
	
	private ArrayList<String> createImagesArray(String input){
        System.out.println(input);
		if(input == null || input.equals("")){
			return null;
		}

		ArrayList<String> images = new ArrayList<String>();
		
		if(input.indexOf(';') == -1){
			if (input.indexOf(' ') == -1){
				images.add(input);
			} else {
                return new ArrayList<String>(Arrays.asList(input.split("[ ;,]")));
			}
		}
		else{
			int fromIndex = 0;
			int toIndex = input.indexOf(';',fromIndex);
			while(toIndex != -1){
				images.add(input.substring(fromIndex, toIndex));
				fromIndex = toIndex + 1;
				if(fromIndex < input.length()){
					toIndex = input.indexOf(';',fromIndex);
				}
				else {
					break;
				}
				
			}
			if(fromIndex < input.length()){
				images.add(input.substring(fromIndex));
			}
			
			
		}
		return images;
	}
		
	//Create coordinates point from string
	@SuppressWarnings("unused")
	private Point2D.Double createCoord(String lon, String lat){
		double doubleLat, doubleLon;
		
		doubleLon = java.lang.Double.parseDouble(lon.substring(0, lon.indexOf('°')));
		doubleLon += java.lang.Double.parseDouble(lon.substring(lon.indexOf('°')+1,lon.indexOf('′'))) / 60;
		
		doubleLat = java.lang.Double.parseDouble(lat.substring(0, lat.indexOf('°')));
		doubleLat += java.lang.Double.parseDouble(lat.substring(lat.indexOf('°')+1,lat.indexOf('′'))) / 60;
		
		if(lon.indexOf('с') == -1)
			doubleLon *= -1;
		
		if(lat.indexOf('в') == -1)
			doubleLat *= -1;
		
		return new Point2D.Double(doubleLon, doubleLat);
	}
}
