package ru.amse.agregator.storage;

import com.mongodb.*;
import org.bson.types.ObjectId;
import ru.amse.agregator.ranking.Categories;

import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Pattern;

public class Database {
	private static Mongo myMongo = null;
	private static DB myDB = null;

	private static String myCurrentAddress;
	private static int myCurrentPort;

	private static ArrayList<String> myCollections;

	private static String myLastContinentName = null;
	private static String myLastCityName = null;
	private static String myLastCountryName = null;
	private static ObjectId myLastContinentId = null;
	private static ObjectId myLastCityId = null;
	private static ObjectId myLastCountryId = null;
	private static DB myLastDB = null;

	public static final String 	DB_SERVER_ADDRESS = "localhost";
	public static final int 	DB_SERVER_PORT = 27017;
	public static final String 	MAIN_DB_NAME = "mainDB";
	public static final String 	DIRTY_DB_NAME = "dirtyDB";

	public static final String 	COLLECTION_HOTELS = "hotels";
	public static final String 	COLLECTION_CONTINENTS = "continents";
	public static final String 	COLLECTION_COUNTRIES = "countries";
	public static final String 	COLLECTION_CITIES = "cities";
	public static final String 	COLLECTION_CAFE = "cafe";
	public static final String 	COLLECTION_USERS = "users";
	public static final String 	COLLECTION_COMMENTS = "comments";
	public static final String 	COLLECTION_ATTRACTIONS = "attractions";
	public static final String  COLLECTION_TOURS = "tours";
    public static final String  COLLECTION_CATEGORIES = "keywords";

	public static ArrayList<User> getAllUsers(){
		DBCollection collection = myDB.getCollection(COLLECTION_USERS);
		if (collection != null){
			ArrayList<User> users = new ArrayList<User>();
			DBCursor cur = collection.find();
			while(cur.hasNext()){
				users.add(new User(cur.next()));
			}
			return users;
		}
		return null;
	}

    public static User getUser(final String login, final String pwd){
        final DBObject maybeUser = findInCollection(COLLECTION_USERS, new BasicDBObject(User.FIELD_LOGIN, login).append(User.FIELD_PASSWORD, pwd));
        if(maybeUser != null){
            return new User(maybeUser);
        } else {
            return null;
        }
    }

	
	public static ArrayList<Tour> getAllToursByUser(ObjectId userId){
		User user = (User)findInCollection(COLLECTION_USERS,StorageObject.FIELD_ID, userId);
		if (user != null){
			ArrayList<ObjectId> toursId =  user.getTour();
			ArrayList<Tour> tours = new ArrayList<Tour>();
			for (ObjectId id : toursId){
				tours.add((Tour)findInCollection(COLLECTION_TOURS,StorageObject.FIELD_ID, id));
			}
			return tours;
		}
		return null;
	}
	
	
	/**
	 * Add user to collection with name "COLLECTION_USERS". If parameter is null then function returns null.
	 * @param user user which is wanted to add
	 * @return ObjectId of user in collection after adding
	 */
	public static ObjectId addUser(User user){
		if(user != null){
			user.setId(addToCollection(COLLECTION_USERS, user.toDBObject()));
			return user.getId();
		} else {
			return null;
		}
	}
	public static ObjectId addTour(Tour tour){
		if(tour != null){
			tour.setId(addToCollection(COLLECTION_TOURS, tour.toDBObject()));
			return tour.getId();
		} else {
			return null;
		}
	}
	
	//Connect to dirty database
	public static void connectToDirtyBase(){
		connect(DB_SERVER_ADDRESS, DB_SERVER_PORT,DIRTY_DB_NAME);
	}
	
	//Connect to main database
	public static void connectToMainBase(){
		connect(DB_SERVER_ADDRESS, DB_SERVER_PORT,MAIN_DB_NAME);
	}

    //Connect to database specified by name
	public static void connectToBase(String dbName){
		connect(DB_SERVER_ADDRESS, DB_SERVER_PORT, dbName);
	}
	
	//Connect to server address:port, and database dbName
	public static void connect(String address, int port, String dbName){
		if(!address.equals(myCurrentAddress) || myCurrentPort != port){
			try {
				myMongo = new Mongo(address, port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}
			myCurrentAddress = address;
			myCurrentPort = port;
		}
		
		if(myMongo != null){
			myDB = myMongo.getDB(dbName);
			
			myCollections = new ArrayList<String>();
			myCollections.add(COLLECTION_ATTRACTIONS);
			myCollections.add(COLLECTION_CITIES);
			myCollections.add(COLLECTION_COUNTRIES);
			myCollections.add(COLLECTION_CONTINENTS);
			myCollections.add(COLLECTION_CAFE);
			myCollections.add(COLLECTION_HOTELS);
			myCollections.add(COLLECTION_USERS);
			myCollections.add(COLLECTION_CATEGORIES);
		}
	}
	
	public static void switchBaseTo(String dbName){
		if(myMongo != null){
			myDB = myMongo.getDB(dbName);
		}
	}
		
	public static void removeCollection(String collectionName) {
		if (myDB != null) {
			myDB.getCollection(collectionName).drop();
		}
	}

    public static void removeAllCollections() {
        Set<String> set = myDB.getCollectionNames();
        //@todo remove constant from code
        for (String collectionName : set) {
            if (!collectionName.equals("system.indexes")) {
                removeCollection(collectionName);
            }
        }
    }
	
	//Print all collections of current database (myDB)
	public static void printAll() {
		Set<String> set = myDB.getCollectionNames();
        System.out.println("There are "  + set.size() + " collections in database");
        for (String collectionName : set) {
            System.out.println(collectionName + " has " +
                    myDB.getCollection(collectionName).count() + " elements");
        }
		for(String collectionName : set){
			DBCollection coll = myDB.getCollection(collectionName);
			DBCursor cur = coll.find();
			System.out.println("\n Collection: " + collectionName + "\n");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}
	}
		
	public static ArrayList<DBWrapper> getAllCities(){
		return getAllCollection(COLLECTION_CITIES);
	}
	
	public static ArrayList<DBWrapper> getAllAttractions(){
		return getAllCollection(COLLECTION_ATTRACTIONS);
	}
	
	/**
	 * This function works only with collections which are consisting of {@link DBWrapper}, you can't write getAllCollection (COLLECTION_USERS)
	 * @param collectionName the name of collection
	 * @return all elements from collection
	 */
	public static ArrayList<DBWrapper> getAllCollection(String collectionName){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(collectionName).find();
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				allCollection.add(dbWrapper);
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllDBObjects(){
		ArrayList<DBWrapper> allObjects = new ArrayList<DBWrapper>();
		if(myDB != null){
			for(String collectionName : myCollections){
				if (!collectionName.equals("system.indexes")&&(!collectionName.equals(COLLECTION_USERS))) {
					DBCursor cur = myDB.getCollection(collectionName).find();
					while(cur.hasNext()){
						DBWrapper dbWrapper = new DBWrapper(cur.next());
						dbWrapper.initFromDB();
						allObjects.add(dbWrapper);
					}
				}
			}
		}
		return allObjects;
	}
	
	public static ArrayList<DBWrapper> getAllWithType(String type){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type));
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				allCollection.add(dbWrapper);
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllDBObjectsWithKeyValue(String key, String value){
		ArrayList<DBWrapper> collection = new ArrayList<DBWrapper>();
		if(myDB != null){
			for(String collectionName : myCollections){
				if (!collectionName.equals(COLLECTION_USERS)){
					DBCursor cur = myDB.getCollection(collectionName).find(new BasicDBObject(key,value));
					while(cur.hasNext()){
						DBWrapper dbWrapper = new DBWrapper(cur.next());
						dbWrapper.initFromDB();
						collection.add(dbWrapper);
					}
				}
			}
		}
		return collection;
	}
	
	public static ArrayList<DBWrapper> getTopNWithType(int count,String type){
		ArrayList<DBWrapper> top = new ArrayList<DBWrapper>();
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject();
			if(!type.equals(DBWrapper.TYPE_ATTRACTION)){
				criteria.put(DBWrapper.FIELD_TYPE,type);
			}
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1)).limit(count);
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				top.add(dbWrapper);
			}
		}
		return top;
	}
	
	public static ArrayList<DBWrapper> getTopNWithKeyValue(int count,String type, String key, Object value){
		ArrayList<DBWrapper> top = new ArrayList<DBWrapper>();
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject();
			if(!type.equals(DBWrapper.TYPE_ATTRACTION)){
				criteria.put(DBWrapper.FIELD_TYPE,type);
			}
			criteria.put(key,value);
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1)).limit(count);
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				top.add(dbWrapper);
			}
		}
		return top;
	}
		
	public static ObjectId add(DBWrapper storageObject){
		if(storageObject != null){
			storageObject.setId(new ObjectId());
			String collectionName = typeCollection(storageObject.getType());
			storageObject.setId(addToCollection(collectionName, storageObject.toDBObject()));
			return storageObject.getId();
		} else {
			return null;
		}
	}
	
	public static ObjectId update(DBWrapper storageObject){
		if(storageObject != null && storageObject.getId() != null){
			if(getDBObjectByIdAndType(storageObject.getId(),storageObject.getType()) != null){
				String collectionName = typeCollection(storageObject.getType());
				storageObject.setId(addToCollection(collectionName, storageObject.toDBObject()));
				return storageObject.getId();
			} else {
				return null;
			}		
		} else {
			return null;
		}
	}
	
	private static String typeCollection(String type){
		String collectionName;
		if(type.equals(DBWrapper.TYPE_CONTINENT)){
			collectionName = COLLECTION_CONTINENTS;
		} else if(type.equals(DBWrapper.TYPE_COUNTRY)) {
			collectionName = COLLECTION_COUNTRIES;
		} else if(type.equals(DBWrapper.TYPE_CITY)) {
			collectionName = COLLECTION_CITIES;
		} else if(type.equals(DBWrapper.TYPE_HOTEL)) {
			collectionName = COLLECTION_HOTELS;
		} else if(type.equals(DBWrapper.TYPE_CAFE)) {
			collectionName = COLLECTION_CAFE;
		} else if(type.equals(DBWrapper.TYPE_USER)) {
			collectionName = COLLECTION_USERS;
        } else if (type.equals(DBWrapper.TYPE_CATEGORY)) {
            collectionName = COLLECTION_CATEGORIES;
		} else if(type.equals(DBWrapper.TYPE_COMMENT)) {
			collectionName = COLLECTION_COMMENTS;
		} else {
			collectionName = COLLECTION_ATTRACTIONS;
		}
		return collectionName;
	}
	
	public static DBWrapper getDBObjectByIdAndType(ObjectId id, String type, Boolean incRatin) {
		DBObject retObj = findInCollectionById(typeCollection(type), id);
		if(retObj != null){
			DBWrapper dbWrapper = new DBWrapper(retObj);
			dbWrapper.initFromDB();
			if(incRatin){
				//@todo Добавить быстрое увеличение рейтинга
				dbWrapper.incRating();
				update(dbWrapper);
			}
			return dbWrapper;
		} else {
			return null;
		}
	}
	
	public static DBWrapper getDBObjectByIdAndType(ObjectId id, String type) {
		return getDBObjectByIdAndType(id,type, false);
	}
	
	public static DBWrapper getDBObjectByIdAndTypeAndIncRating(ObjectId id, String type) {
		return getDBObjectByIdAndType(id,type, true);
	}
	
	public static DBWrapper getAttractionById(ObjectId id, Boolean incRatin) {
		return getDBObjectByIdAndType(id,DBWrapper.TYPE_ARCH_ATTRACTION, incRatin);
	}
	
	public static DBWrapper getAttractionById(ObjectId id) {
		return getAttractionById(id, false);
	}
	
	public static DBWrapper getAttractionByIdAndIncRating(ObjectId id) {
		return getAttractionById(id,true);
	}
	
	public static ObjectId getCityIdByName(String cityName){
		if(myLastDB != null && myLastDB.equals(myDB) && cityName != null && cityName.equals(myLastCityName)){
			return myLastCityId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,cityName);
		DBObject obj = findInCollection(COLLECTION_CITIES,criteria);
		if(obj != null) {
			myLastDB = myDB;
			myLastCityName = cityName;
			myLastCityId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastCityId;
		} else {
			return null;
		}
	}
	
	public static ObjectId getCountryIdByName(String countryName){
		if(myLastDB != null && myLastDB.equals(myDB) && countryName != null && countryName.equals(myLastCountryName)){
			return myLastCountryId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,countryName);
		DBObject obj = findInCollection(COLLECTION_COUNTRIES,criteria);
		if(obj != null){
			myLastDB = myDB;
			myLastCountryName = countryName;
			myLastCountryId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastCountryId;
		} else {
			return null;
		}
	}
	
	public static ObjectId getContinentIdByName(String continentName){
		if(myLastDB != null && myLastDB.equals(myDB) && continentName != null && continentName.equals(myLastContinentName)){
			return myLastContinentId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,continentName);
		DBObject obj = findInCollection(COLLECTION_CONTINENTS,criteria);
		if(obj != null){
			myLastDB = myDB;
			myLastContinentName = continentName;
			myLastContinentId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastContinentId;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ObjectId> getAllIdByType(String type){
		ArrayList<ObjectId> allCollection = new ArrayList<ObjectId>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type),new BasicDBObject(DBWrapper.FIELD_ID,1));
			while(cur.hasNext()){
				allCollection.add((ObjectId)cur.next().get(DBWrapper.FIELD_ID));
			}
		}
		return allCollection;
	}
	
	
	private static ObjectId addToCollection(String collectionName, DBObject object){ 
		if(myDB != null){
			myDB.getCollection(collectionName).save(object); 
			return (ObjectId) object.get(DBWrapper.FIELD_ID); 
		} else { 
			return null; 
		}  
	}
	
	//Replace in collection 'collectionName', object with id 'id' by 'newObj'
	@SuppressWarnings("unused")
	private static ObjectId updateInCollectionById(String collectionName, ObjectId id, DBObject newObj){
		if(myDB != null){
			newObj.put(DBWrapper.FIELD_ID, id);
			return (ObjectId) myDB.getCollection(collectionName).save(newObj).getField(DBWrapper.FIELD_ID);
		} else {
			return null;
		}		
	}
	
	//Find one object in collection 'collectionName' where field id == 'id'
	private static DBObject findInCollectionById(String collectionName, ObjectId id){
		return findInCollection(collectionName,StorageObject.FIELD_ID, id);
	}
	
	//Find one object in collection 'collectionName' where field 'key' == 'value'
	public static DBObject findInCollection(String collectionName, String key, Object value){
			return findInCollection(collectionName, new BasicDBObject(key, value));
	}
	
	//Find one object in collection 'collectionName' where are present fields with values as in the 'criteria' 
	private static DBObject findInCollection(String collectionName, DBObject criteria){
		if(myDB != null){
			return myDB.getCollection(collectionName).findOne(criteria);
		} else {
			return null;
		}	
	}

	public static void unificationNames() {
		Set<String> collections = myDB.getCollectionNames();
		for (String collection : collections) {
			ArrayList<DBWrapper> array = Database.getAllCollection(collection);
			if (array == null){
				System.out.println("пустой array");
			}
			else{
				for (DBWrapper obj : array) {
					if (obj == null){
						System.out.println("пустой obj");
					}
					else{
						String name = obj.getName();
						if (name == null){
							System.out.println("пустой name");
						}
						else{
							char[] arrayName = name.toCharArray();
							if (arrayName.length == 0){
								System.out.println("пустой arrayName");
							}
							else{
								arrayName[0] = Character.toUpperCase(arrayName[0]);
								for ( int i = 1 ; i < arrayName.length; ++i){
									if (Character.isUpperCase(arrayName[i])&&((arrayName[i] >'а' && arrayName[i] <'я') || (arrayName[i] >'А' && arrayName[i] <'Я'))){
										if (Character.isLetter(arrayName[i-1])){
											arrayName[i] = Character.toLowerCase(arrayName[i]);			
										}
									}
								}
								name =   new String(arrayName);
								name = name.replaceAll("-", " - ");
								name = name.replaceAll("  ", " ");
								
								obj.setName(name);
								Database.update(obj);
								
							}
						}
					}
				}	
			}
		}


		Pattern first = Pattern.compile("[-']");
		Pattern second = Pattern.compile("[^а-яА-Яa-zA-Z0-9]");
		for (String collection : collections) {
			ArrayList<DBWrapper> array = Database.getAllCollection(collection);
			if (array == null){
				System.out.println("пустой array");
			}
			else{
				for (DBWrapper obj : array) {
					
					String name = obj.getName();
					if (name == null){
						System.out.println("пустой name");
					}
					else{
						
						if (first.matcher(obj.getName()).find()){
							String nameInDbModified = second.matcher(obj.getName()).replaceAll(""); 
							for (DBWrapper obj1 : array){
								if (obj1.getName() != null){
									String nameInDb = second.matcher(obj1.getName()).replaceAll(""); 						
									if (nameInDb.equalsIgnoreCase(nameInDbModified)) {
										obj1.setName(obj.getName());
										Database.update(obj1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	//Find all objects in collection 'collectionName' where are present fields with values as in the 'criteria' 
	private static ArrayList<DBWrapper> findAllInCollection(String collectionName, DBObject criteria){
		return findAllInCollection(collectionName, criteria, null);		
	}
	
	//Find all objects in collection 'collectionName' where are present fields with values as in the 'criteria'. And Sort with order 
	private static ArrayList<DBWrapper> findAllInCollection(String collectionName, DBObject criteria, DBObject order){
		ArrayList<DBWrapper> objects = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(collectionName).find(criteria);
			if(order != null){
				cur = cur.sort(order);
			}
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				objects.add(dbWrapper);
			}
		}
		return objects;		
	}
	
	
	public static void removeAllWithKeyValue(String type, String key, Object value){
		myDB.getCollection(typeCollection(type)).remove(new BasicDBObject(key,value));
	}
	
	public static DB getDB(){
		return myDB;
	}
	
	//------------------------
	//Methods for Gui
	
	public static ArrayList<DBWrapper> getAllContinents() {
		BasicDBObject orderBy = new BasicDBObject(DBWrapper.FIELD_NAME, 1);
		return findAllInCollection(COLLECTION_CONTINENTS,
                new BasicDBObject(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_CONTINENT), orderBy);
	}
	
	public static ArrayList<DBWrapper> getAllCountriesByContinent(ObjectId continentId){
		BasicDBObject criteria = new BasicDBObject();
		//criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_COUNTRY);
		criteria.put(DBWrapper.FIELD_CONTINENT_ID,continentId);
		BasicDBObject orderBy = new BasicDBObject(DBWrapper.FIELD_NAME, 1);
		return findAllInCollection(COLLECTION_COUNTRIES,criteria,orderBy);		
	}
	
	public static ArrayList<DBWrapper> getAllAttrByCity(ObjectId cityId){
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_CITY_ID, cityId);
		BasicDBObject orderBy = new BasicDBObject(DBWrapper.FIELD_NAME,1);
		return findAllInCollection(COLLECTION_ATTRACTIONS, criteria,orderBy);
	}
	
	public static ArrayList<DBWrapper> getAllCitiesByCountry(ObjectId countryId){
		BasicDBObject criteria = new BasicDBObject();
		//criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_CITY);
		criteria.put(DBWrapper.FIELD_COUNTRY_ID,countryId);
		BasicDBObject orderBy = new BasicDBObject(DBWrapper.FIELD_NAME, 1);
		return findAllInCollection(COLLECTION_CITIES,criteria,orderBy);	
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getAllTypesOfObjectByCity(ObjectId cityId){
		if(myDB != null){
			ArrayList<String> types =
                    (ArrayList<String>) myDB.getCollection(COLLECTION_ATTRACTIONS).distinct(DBWrapper.FIELD_TYPE,
                            new BasicDBObject(DBWrapper.FIELD_CITY_ID, cityId));
			return types;
		} else{
			return null;
		}
	}
	
	public static ArrayList<DBWrapper> getAllObjectOfSelectedTypeInCity(ObjectId cityId, String type){
		ArrayList<DBWrapper> objects = new ArrayList<DBWrapper>();
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_CITY_ID,cityId);
			criteria.put(DBWrapper.FIELD_TYPE,type);
			DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria);
			while(cur.hasNext()){
				objects.add(new DBWrapper(cur.next()));
			}
		}
		return objects;	
	}

    public static ArrayList<String> getTopNCategories(int count) {
        final Set<String> set = new HashSet<String>();
        if (myDB != null) {
            BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_ARCH_ATTRACTION);
            DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1));

            while (cur.hasNext() && set.size() < count) {
                DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
                if (dbWrapper.getCategoryArray() != null) {
                    set.addAll(dbWrapper.getCategoryArray());
                }
            }
        }

        final int upperBound = Math.min(count, set.size());

        return new ArrayList<String>(new ArrayList<String>(set).subList(0, upperBound - 1));
    }

    public static ArrayList<String> getTopNCategoriesInContinent(int count, final ObjectId continentId) {
        final Set<String> set = new HashSet<String>();
        if (myDB != null) {
            BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_ARCH_ATTRACTION);
			criteria.put(DBWrapper.FIELD_CONTINENT_ID, continentId);
            DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1));

            while (cur.hasNext() && set.size() < count) {
                DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
                if (dbWrapper.getCategoryArray() != null) {
                    set.addAll(dbWrapper.getCategoryArray());
                }
            }
        }

        final int upperBound = Math.max(Math.min(count, set.size()), 1);

        return new ArrayList<String>(new ArrayList<String>(set).subList(0, upperBound - 1));
    }

    public static ArrayList<String> getTopNCategoriesInCountry(int count, final ObjectId countryId) {
        final Set<String> set = new HashSet<String>();
        if (myDB != null) {
            BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_ARCH_ATTRACTION);
			criteria.put(DBWrapper.FIELD_COUNTRY_ID, countryId);
            DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1));

            while (cur.hasNext() && set.size() < count) {
                DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
                if (dbWrapper.getCategoryArray() != null) {
                    set.addAll(dbWrapper.getCategoryArray());
                }
            }
        }

        final int upperBound = Math.max(Math.min(count, set.size()), 1);
        return new ArrayList<String>(new ArrayList<String>(set).subList(0, upperBound - 1));
    }


    public static ArrayList<String> getTopNCategoriesInCity(int count, final ObjectId cityId) {
        final Set<String> set = new HashSet<String>();
        if (myDB != null) {
            BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_ARCH_ATTRACTION);
			criteria.put(DBWrapper.FIELD_CITY_ID, cityId);
            DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1));

            while (cur.hasNext() && set.size() < count) {
                DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
                if (dbWrapper.getCategoryArray() != null) {
                    set.addAll(dbWrapper.getCategoryArray());
                }
            }
        }

        final int upperBound = Math.max(Math.min(count, set.size()), 1);

        return new ArrayList<String>(new ArrayList<String>(set).subList(0, upperBound - 1));
    }


	//-------------------------------------------------------------
	public static void createIndexes(){
		if(myDB != null){
			BasicDBObject keys;
			for(String collectionname : myCollections){
									
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_CONTINENT_ID,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_COUNTRY_ID,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_CITY_ID,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_CONTINENT_ID,1);
				keys.put(DBWrapper.FIELD_TYPE,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_COUNTRY_ID,1);
				keys.put(DBWrapper.FIELD_TYPE,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_CITY_ID,1);
				keys.put(DBWrapper.FIELD_TYPE,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
								
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_TYPE,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_TYPE,1);
				keys.put(DBWrapper.FIELD_ID,1);
				myDB.getCollection(collectionname).ensureIndex(keys);
				
				keys = new BasicDBObject();
				keys.put(DBWrapper.FIELD_TYPE,1);
				keys.put(DBWrapper.FIELD_RATING,1);
				myDB.getCollection(collectionname).ensureIndex(keys);

                keys = new BasicDBObject();
                keys.put(DBWrapper.FIELD_NAME,1);
                myDB.getCollection(collectionname).ensureIndex(keys);

                keys = new BasicDBObject();
                keys.put(DBWrapper.FIELD_NAME,1);
                keys.put(DBWrapper.FIELD_TYPE,1);
                myDB.getCollection(collectionname).ensureIndex(keys);

                keys = new BasicDBObject();
                keys.put(DBWrapper.FIELD_NAME,1);
                keys.put(DBWrapper.FIELD_CITY_ID,1);
                myDB.getCollection(collectionname).ensureIndex(keys);

                keys = new BasicDBObject();
                keys.put(DBWrapper.FIELD_NAME,1);
                keys.put(DBWrapper.FIELD_COUNTRY_ID,1);
                myDB.getCollection(collectionname).ensureIndex(keys);

                keys = new BasicDBObject();
                keys.put(DBWrapper.FIELD_NAME,1);
                keys.put(DBWrapper.FIELD_CONTINENT_ID,1);
                myDB.getCollection(collectionname).ensureIndex(keys);

			}

		}
	}
	
	//-------------------------------------------------------------
	
	//---------------------------------
	//--Methods for Private GUI-start--
	//---------------------------------
	
	public static Vector<Vector<Object>> getCollectionValues(String collectionName,Vector<String> attributs){
		Vector<Vector<Object>> res = new Vector<Vector<Object>>();
		DBCollection coll = myDB.getCollection(collectionName);
		DBCursor cur = coll.find();
		
		while(cur.hasNext()){
			DBObject temp= cur.next();
			res.add(new Vector<Object>());
			for (Vector<Object> v : res){
				Set<String> fieldsSet = temp.keySet();
				for ( int i = 0; i < attributs.size();i++){
					if ( fieldsSet.contains(attributs.get(i))){
						v.add(temp.get(attributs.get(i)));
					}
					else
						v.add(temp.get(null));
				}
				
			}
		}
		
		return res;
	}
	public static Set<String> getTypesNames(String nameCollection){
		Set <String> tempRes = new HashSet<String>();	
		try{
			
			DBCollection coll = myDB.getCollection(nameCollection);
			DBCursor cur = coll.find();
			String typeName;
			while (cur.hasNext()){
				DBWrapper temp= new DBWrapper(cur.next());
				typeName = temp.getType();
			    if ( !(tempRes.contains(typeName)) ){
					tempRes.add(typeName);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Epic Fail here");
		}
		return  tempRes;
	}
	
	
	public static Set<String> getAttributsNames(String nameType){
		Set <String> tempRes = new HashSet<String>();	
		tempRes.add("_id");
		tempRes.add(DBWrapper.FIELD_TYPE);
		tempRes.add(DBWrapper.FIELD_NAME);
		tempRes.add(DBWrapper.FIELD_KEYWORDS);
		if (nameType.equalsIgnoreCase("User")){}
		else if (nameType.equalsIgnoreCase("Comment")){}
		else {
			tempRes.add(DBWrapper.FIELD_DESC);
			tempRes.add(DBWrapper.FIELD_COORDS);
			tempRes.add(DBWrapper.FIELD_IMAGES);
			if (!nameType.equalsIgnoreCase("City")){
				tempRes.add(DBWrapper.FIELD_COST);
				tempRes.add(DBWrapper.FIELD_ADDRESS);
				tempRes.add(DBWrapper.FIELD_CITY_ID);
				tempRes.add(DBWrapper.FIELD_WEBSITE);
				if (nameType.equalsIgnoreCase("ArchAttraction")){
					tempRes.add(DBWrapper.FIELD_ARCHITECT);
					tempRes.add(DBWrapper.FIELD_DATE_FOUNDATION);
				}
				else if(nameType.equalsIgnoreCase("Cafe")){
					tempRes.add(DBWrapper.FIELD_MUSIC);
				}
				else if(nameType.equalsIgnoreCase("Hotel")){
					tempRes.add(DBWrapper.FIELD_ROOMS);
				}
			}
		}
			
		return  tempRes;
	}
	

	public static Vector<Vector<Object>> getCollectionValues(String nameType, Set<String> attr){
		Vector<Vector<Object>> res = new Vector<Vector<Object>>();
		Vector<DBWrapper> allCollection = new Vector<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(typeCollection(nameType)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,nameType));
			while(cur.hasNext()){
				allCollection.add(new DBWrapper(cur.next()));
			}
		}
		for (int i = 0; i < allCollection.size();++i){
			Vector<Object> v = new Vector<Object>();
			for(String str1 : attr){
			  v.add(allCollection.get(i).myDBObj.get(str1));
			}
			res.add(v);
		}
		return res;
	}
	public static int getTypesCount(String nameCollection){
		int count = 0;
		try{
			Set <String> tempRes = new HashSet<String>();	
			DBCollection coll = myDB.getCollection(nameCollection);
			DBCursor cur = coll.find();
			String typeName;
			
			while (cur.hasNext()){
				DBWrapper temp= (DBWrapper)cur.next();
				typeName = temp.getType(); 
			    if ( !(tempRes.contains(typeName)) ){
					tempRes.add(typeName);
				}
			}
			for (@SuppressWarnings("unused") String v : tempRes){			
				count++;
			}
		}
		catch (Exception e) {
			System.out.println("Epic Fail");
		}
		return count;
	}
	/*
	public static void setAttribut(String nameAttribut, String valueAttribut, Object id){
		DBWrapper a = getDBObjectById((ObjectId)id);
		a.setAttribut(nameAttribut,valueAttribut);
		myDB.getCollection(COLLECTION_MAIN).save(a.toDBObject());
	}
	*/

	//---------------------------------
	//--Methods for Private GUI end----
	//---------------------------------

	//-------------------------------------------------------------
	
	//---------------------------------
	//--Methods for Clusterization-----
	//---------------------------------
	
    public static DBWrapper getByUniqueId(UniqueId uniqueId) {
        switchBaseTo(uniqueId.getDatabaseName());
        return getDBObjectByIdAndType(uniqueId.getId(),
                uniqueId.getCollectionName());
    }
    
	//---------------------------------
	//--Methods for Clusterization end-
	//---------------------------------



    public static ArrayList<DBWrapper> getTopNWithTypeAndCategory(int count,
                                                                  final String attractionType,
                                                                  final String category) {
        if (category == null) {
            return getTopNWithType(count, attractionType);
        }

        final ArrayList<DBWrapper> result = new ArrayList<DBWrapper>();
        if(myDB != null){
            BasicDBObject criteria = new BasicDBObject();
            if(!attractionType.equals(DBWrapper.TYPE_ATTRACTION)){
                criteria.put(DBWrapper.FIELD_TYPE, attractionType);
            }

            DBCursor cur = myDB.getCollection(typeCollection(attractionType)).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING,-1)).limit(3000);

            try {
                while(cur.hasNext() && result.size() < count) {
                    DBWrapper dbWrapper = new DBWrapper(cur.next());
                    dbWrapper.initFromDB();
                    if (Categories.isInCategory(dbWrapper, category)) {
                        result.add(dbWrapper);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    public static ArrayList<DBWrapper> getTopNWithKeyValueAndCategory(int count,
                                                                      String type,
                                                                      String key,
                                                                      Object value,
                                                                      final String category) {
        if (category == null) {
            return getTopNWithKeyValue(count, type, key, value);
        }

        ArrayList<DBWrapper> result = new ArrayList<DBWrapper>();
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject();
			if(!type.equals(DBWrapper.TYPE_ATTRACTION)){
				criteria.put(DBWrapper.FIELD_TYPE, type);
			}
			criteria.put(key, value);
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(criteria).sort(new BasicDBObject(DBWrapper.FIELD_RATING, -1));
			while(cur.hasNext() && result.size() < count){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				if (Categories.isInCategory(dbWrapper, category)) {
                    result.add(dbWrapper);
                } else {
                }
			}
		}
		return result;
    }
}
