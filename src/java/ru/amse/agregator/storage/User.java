package ru.amse.agregator.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class User extends StorageObject{
	
	public static String FIELD_LOGIN = "login";
	public static String FIELD_NAME = "name";
	public static String FIELD_PASSWORD = "password";
	public static String FIELD_MY_TOUR =  "tours";
	
	public User(DBObject dbObject){
		super(dbObject);
	}
	
	public User(){
		super (new BasicDBObject());
		myDBObj.put(FIELD_MY_TOUR, new ArrayList<ObjectId>());
	}
	
	public void setKeyValue(String key, String value){
        myDBObj.put(key,value);
	}
	
	private void setTour(ArrayList<ObjectId> arrayId){
		myDBObj.put(FIELD_MY_TOUR, arrayId);
	}
	
	@SuppressWarnings("unused")
	public String getLogin(){
		return (String)myDBObj.get(FIELD_LOGIN);
	}
	
	@SuppressWarnings("unused")
	public String getName(){
		return (String)myDBObj.get(FIELD_NAME);
	}
	
	private String getPassword(){
		return (String)myDBObj.get(FIELD_PASSWORD);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ObjectId> getTour(){
		return (ArrayList<ObjectId>)myDBObj.get(FIELD_MY_TOUR);
	}
	
	public void addTour(ObjectId id){
		ArrayList<ObjectId> arrayAttr = getTour();
		arrayAttr.add(id);
		setTour(arrayAttr);
	}

}
