package ru.amse.agregator.storage;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Tour extends StorageObject{
	public static String FIELD_ID_USER = "id_user";
	public static String FIELD_NAME = "name";
	public static String FIELD_MY_ATTRACTION = "my Attraction";
	
	public Tour(DBObject dbObject){
		super(dbObject);
	}
	
	public Tour(){
		super (new BasicDBObject());
		myDBObj.put(FIELD_MY_ATTRACTION, new ArrayList<ObjectId>());
	}
	
	public void setKeyValue(String key, String value){
		if (key.equals(FIELD_ID_USER)){
			myDBObj.put(FIELD_ID_USER, value);
		}
		else if(key.equals(FIELD_NAME)){
			myDBObj.put(FIELD_NAME, value);
		}
		else {
			myDBObj.put(key,value);
		}
	}
	
	public void setAttraction(ArrayList<ObjectId> arrayId){
		myDBObj.put(FIELD_MY_ATTRACTION, arrayId);
	}
	
	
	public String getIdUser(){
		return (String)myDBObj.get(FIELD_ID_USER);
	}
	
	@SuppressWarnings("unused")
	public String getName(){
		return (String)myDBObj.get(FIELD_NAME);
	}
		
	@SuppressWarnings("unchecked")
	public ArrayList<ObjectId> getAttraction(){
		return (ArrayList<ObjectId>)myDBObj.get(FIELD_MY_ATTRACTION);
	}
	
	public void addAttraction(ObjectId id){
		ArrayList<ObjectId> arrayAttr = getAttraction();
		arrayAttr.add(id);
		setAttraction(arrayAttr);
	}


}
