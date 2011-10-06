package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class GetTopYalet implements Yalet{

	private String type;
	private String count;
	
    public void setType(final String type) { this.type = type; }
    public void setCount(String count){ this.count = count; }
	
	public void process(InternalRequest req, InternalResponse res) {
			
		Database.connectToMainBase();	
		ArrayList<DBWrapper> dbRes = Database.getTopNWithType(new Integer(count), type);
		ArrayList<Record> webRes = new ArrayList<Record>(); 
		
		for (DBWrapper tmp : dbRes) {
			 Record newRecord = new Record();
			 
			 newRecord.addCell("name", tmp.getName());
			 newRecord.addCell("id", tmp.getId().toString());
			 if(tmp.getImagesArray() == null || tmp.getImagesArray().size() == 0)
				 newRecord.addCell("imageLink", "images/not_image.gif");
			 else{
				 newRecord.addCell("imageLink", tmp.getImagesArray().get(0));
			 }
			 newRecord.addCell("upName", tmp.getStaticCountryName());
			 if(tmp.getCountryId() != null){
				 newRecord.addCell("upId", tmp.getCountryId().toString());
			 }
			 
			 //newRecord.addCell("upId", tmp.getCountryId().toString());
			 webRes.add(newRecord);
		}
		res.add(webRes);		
	}
}