package ru.amse.agregator.storage;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class StorageObject {
	protected BasicDBObject myDBObj;
	
	public static final String FIELD_ID = "_id";
	
	public StorageObject(DBObject dbObject){
		//myDBObj = new BasicDBObject(dbObject.toMap());
		setAllFromDBObject(dbObject);
	}
	
	public StorageObject(){
		this(new BasicDBObject());
	}
	
	public void setAllFromDBObject(DBObject dbObject){
		myDBObj = new BasicDBObject(dbObject.toMap());
	}
	
	protected void copyField(DBObject dbObj, String key){
		myDBObj.put(key,dbObj.get(key));
	}
	
	public void setId(ObjectId id){
		myDBObj.put(FIELD_ID,id);
	}
	
	public ObjectId getId(){
		return (ObjectId) myDBObj.get(FIELD_ID);
	}
	
	public void removeField(String fieldName){
		if(myDBObj.containsField(fieldName)){
			myDBObj.removeField(fieldName);
		}
	}
	
	public void removeId(){
		removeField(FIELD_ID);
	}
	
	public BasicDBObject toDBObject(){
		return new BasicDBObject(myDBObj);
	}

	@Override
	public String toString(){
		return myDBObj.toString();
	}
}