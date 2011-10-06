package ru.amse.agregator.storage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import ru.amse.agregator.ranking.Categories;

public class DBWrapper extends StorageObject{	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESC = "description";
	public static final String FIELD_COORDS = "coordinates";
	public static final String FIELD_IMAGES = "images";
	public static final String FIELD_KEYWORDS = "keywords";
	public static final String FIELD_DATE_FOUNDATION = "date_foundation";
	public static final String FIELD_ARCHITECT = "architect"; 
	public static final String FIELD_COST = "cost";
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_CITY_ID = "city_id";
	public static final String FIELD_CITY_NAME = "city_name";
	public static final String FIELD_COUNTRY_ID = "country_id";
	public static final String FIELD_COUNTRY_NAME = "country_name";
	public static final String FIELD_CONTINENT_ID = "continent_id";
	public static final String FIELD_CONTINENT_NAME = "continent_name";
	public static final String FIELD_MUSIC = "music";
	public static final String FIELD_WEBSITE = "website";
	public static final String FIELD_ROOMS = "rooms";
	public static final String FIELD_CATEGORY = "keywords";
	public static final String FIELD_SOURCE_URL = "source_url";
	public static final String FIELD_RATING = "rating";
	public static final String FIELD_INFO = "info";
	
	public static final String FIELD_UNIQUE_ID = "unique_id";
	
	public static final String TYPE_CONTINENT = "Continent";
	public static final String TYPE_COUNTRY = "Country";
	public static final String TYPE_CITY = "City";
	public static final String TYPE_ATTRACTION = "Attraction";
	public static final String TYPE_SHOPPING = "Shopping";
	public static final String TYPE_MUSEUM = "Museum";
	public static final String TYPE_ENTERTAINMENT = "Entertainment";
	public static final String TYPE_ARCH_ATTRACTION = "ArchAttraction";
	public static final String TYPE_NATURAL_ATTRACTION = "NaturalAttraction";		
	public static final String TYPE_CAFE = "Cafe";	
	public static final String TYPE_HOTEL = "Hotel";
	public static final String TYPE_USER = "User";		
	public static final String TYPE_COMMENT = "Comment";
    public static final String TYPE_CATEGORY = "Category";
	
	private String myCityName = null;
	private String myCountryName = null;
	private String myContinentName = null;
	
    // this field is used in clusterization module to address the object in the whole database
	private UniqueId myUniqueId = null;



    public DBWrapper(DBObject dbObject){
		super(dbObject);
	}
		
	public DBWrapper() {
		this(new BasicDBObject());
	}
	
	//---------
	
	public void setStaticCityName(String cityName){
		myCityName = cityName;
	}
	
	public void setStaticCountryName(String countryName){
		myCountryName = countryName;
	}
	
	public void setStaticContinentName(String continentName){
		myContinentName = continentName;
	}
	
    public void setUniqueId(UniqueId value) {
    	myUniqueId = value;
    }
    
	public String getStaticCityName(){
		return myCityName;
	}
	
	public String getStaticCountryName(){
		return myCountryName;
	}
	
	public String getStaticContinentName(){
		return myContinentName;
	}
    
	public UniqueId getUniqueId() {
        return myUniqueId;
    }
	
	public void initFromDB(){
		setStaticCityName(getCityNameFromDB());
		setStaticCountryName(getCountryNameFromDB());
		setStaticContinentName(getContinentNameFromDB());
	}
	
	//----------
	
	public void setKeyValue(String key, String value){
		if(key.equals(FIELD_CITY_NAME)){
			setCityByName(value);
		} else if(key.equals(FIELD_COUNTRY_NAME)){
			setCountryByName(value);
		} else if(key.equals(FIELD_CONTINENT_NAME)){
			if (value == null || value.equals("")){
				DBWrapper otherCountry = Database.getDBObjectByIdAndType(Database.getCountryIdByName(getName()), DBWrapper.TYPE_COUNTRY);
				if(otherCountry != null){
					setContinentById(otherCountry.getContinentId());
				}
			}
			else {
				setContinentByName(value);
			}
		} else if(key.equals(FIELD_DESC)){
			setDescription(value);
		} else if(key.equals(FIELD_SOURCE_URL)){
			setSourceUrl(value);
		} else {
			myDBObj.put(key,value);
		}
	}
	
	public String getInfo(){
		return (String) myDBObj.get(FIELD_INFO);
	}
	
    //sets an attribute represented by a listed of strings
    public void setListAttribute(final String attributeName, ArrayList<String> values) {
        myDBObj.put(attributeName, values);
    }
	
	public Object getValue(String key){
		if(key.equals(FIELD_CITY_NAME)){
			return getStaticCityName();
		} else if(key.equals(FIELD_COUNTRY_NAME)){
			return getStaticCountryName();
		} else if(key.equals(FIELD_CONTINENT_NAME)){
			return getStaticContinentName();
		}else {
			return myDBObj.get(key);
		}
	}
	
	public Set<String> getKeySet(){
		return getKeySet(false);
	}
	
	public Set<String> getKeySetWithoutWrapperFields(){
		return getKeySet(true);
	}
	
	public Set<String> getKeySet(boolean onlyDBFields){
		Set<String> set = new HashSet<String>(myDBObj.keySet());
		if(!onlyDBFields){
			if((myCityName != null)){
				set.add(FIELD_CITY_NAME);
			}
			if(myCountryName != null){
				set.add(FIELD_COUNTRY_NAME);
			}
			if(myContinentName != null){
				set.add(FIELD_CONTINENT_NAME);
			}
		}
		return set;
	}
	
	public Set<String> getKeySetWithoutNullWithoutWrapperFields(){
		return getKeySetWithoutNull(true);
	}
	
	public Set<String> getKeySetWithoutNull(){
		return getKeySetWithoutNull(false);
	}
	
	public Set<String> getKeySetWithoutNull(boolean onlyDBFields){
		Set<String> set = new HashSet<String>();
		for(String key : myDBObj.keySet()){
			if(myDBObj.get(key) != null){
				set.add(key);
			}
		}
		if(!onlyDBFields){
			if(myCityName != null){
				set.add(FIELD_CITY_NAME);
			}
			if(myCountryName != null){
				set.add(FIELD_COUNTRY_NAME);
			}
			if(myContinentName != null){
				set.add(FIELD_CONTINENT_NAME);
			}
		}

		return set;
	}
	
	public Map<String, Object> getMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		for(String key : this.getKeySet()){
			map.put(key, myDBObj.get(key));
		}
		if(myCityName != null){
			map.put(FIELD_CITY_NAME, myCityName);
		}
		if(myCountryName != null){
			map.put(FIELD_COUNTRY_NAME, myCountryName);
		}
		if(myContinentName != null){
			map.put(FIELD_CONTINENT_NAME, myContinentName);
		}
		return map;
	}
	
	public Map<String, Object> getMapWithoutNull(){
		Map<String, Object> map = new HashMap<String, Object>();
		for(String key : this.getKeySetWithoutNull()){
			map.put(key, myDBObj.get(key));
		}
		if(myCityName != null){
			map.put(FIELD_CITY_NAME, myCityName);
		}
		if(myCountryName != null){
			map.put(FIELD_COUNTRY_NAME, myCountryName);
		}
		if(myContinentName != null){
			map.put(FIELD_CONTINENT_NAME, myContinentName);
		}
		return map;
	}

	//----------
	
	@Deprecated
	public void setAttribut(String nameAttribut, String valueAttribut){
		if (nameAttribut.equalsIgnoreCase(FIELD_TYPE)){
			setType(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_NAME)){
			setName(valueAttribut);
		}	
		if (nameAttribut.equalsIgnoreCase(FIELD_DESC)){
			setDescription(valueAttribut);
		}	
		if (nameAttribut.equalsIgnoreCase(FIELD_COORDS)){
			int i = 0;
			ArrayList<Point2D.Double> coords = new ArrayList<Point2D.Double>();
			while (i < valueAttribut.length()){
				int j = valueAttribut.indexOf(" ", i);
				String sub ="";
				if (j == -1) {
					sub = valueAttribut.substring(i);
					i = valueAttribut.length();					
				}
				else{
					String sub1;
					sub = valueAttribut.substring(i,j);
					i = j+1;
					Double x = Double.parseDouble(sub);
					j = valueAttribut.indexOf(" ", i);
					if ( j == -1){
						sub1 = valueAttribut.substring(i);
						i = valueAttribut.length();
					}
					else{
						sub1 = valueAttribut.substring(i,j);
						i = j+1;
					}
					Double y = Double.parseDouble(sub1);
					Point2D.Double p = new Point2D.Double(x,y);
					coords.add(p);
				}
			}
			setCoordsArray(coords);
		}	
		if (nameAttribut.equalsIgnoreCase(FIELD_IMAGES)){
			setImage(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_KEYWORDS)){
			ArrayList<String> keyWordsArray = new ArrayList<String>();
			int i = 0;
			while (i < valueAttribut.length()){
				int j = valueAttribut.indexOf(" ", i);
				String sub ="";
				if (j == -1) {
					sub = valueAttribut.substring(i);
					i = valueAttribut.length();
				}
				else{
					sub = valueAttribut.substring(i,j);
					i = j+1;
				}
				keyWordsArray.add(sub);
			}
			setKeyWordsArray(keyWordsArray);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_DATE_FOUNDATION)){
			//setDate(new SimpleDateFormat ().parse(valueAttribut));
		}	
		if (nameAttribut.equalsIgnoreCase(FIELD_ARCHITECT)){
			setArchitect(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_COST)){
			setCost(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_ADDRESS)){
			setAddress(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_MUSIC)){
			setMusic(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_WEBSITE)){
			setWebsite(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_ROOMS)){
			setRooms(valueAttribut);
		}
		if (nameAttribut.equalsIgnoreCase(FIELD_CITY_ID)){
			//реализовать
		}	
	}

        @Override
        public String toString() {

            String result = myDBObj.toString();

            if (myContinentName != null) {
               result = "continent_name : " + myContinentName + ", " + result;
            }
            if (myCountryName != null) {
               result = "country_name : " + myCountryName + ", " + result;
            }
            if (myCityName != null) {
               result = "city_name : " + myCityName + ", " + result;
            }
            
            return result;
        }
	
	
	//--------------------------
	//-----Set-methods-start----
	//--------------------------
	
	public void setType (String type){
		myDBObj.put(FIELD_TYPE,type);
	}
	
	public void setAddress(String address){
		myDBObj.put(FIELD_ADDRESS,address);
	}	
	
	public void setDate(Date date){
		myDBObj.put(FIELD_DATE_FOUNDATION, date);
	}
	
	public void setArchitect(String autor){
		myDBObj.put(FIELD_ARCHITECT, autor);
	}
	
	public void setCost(String cost){
		myDBObj.put(FIELD_COST, cost);
	}
	
	public void setWebsite(String name){
		myDBObj.put(FIELD_WEBSITE,name);
	}
	
	public void setName(String name){
		myDBObj.put(FIELD_NAME,name);
	}
	
	public void setDescription(String desc){
		ArrayList<String> descArray = new ArrayList<String>();
		descArray.add(desc);
		setDescriptionArray(descArray);
	}
	
	public void setDescriptionArray(ArrayList<String> descArray){
		myDBObj.put(FIELD_DESC,descArray);
	}
	
	public void setSourceUrl(String source){
		ArrayList<String> sourcesArray = new ArrayList<String>();
		sourcesArray.add(source);
		setSourceUrlArray(sourcesArray);
	}
	
	public void setSourceUrlArray(ArrayList<String> sourcesArray){
		myDBObj.put(FIELD_SOURCE_URL,sourcesArray);
	}
	
	public void setCoordsArray(ArrayList<Point2D.Double> coordsArray){
		ArrayList<DBObject> coords = new ArrayList<DBObject>();
		for(Point2D.Double p : coordsArray){
			coords.add(new BasicDBObject("x",p.x).append("y", p.y));
		}
		myDBObj.put(FIELD_COORDS,coords);
	}
	
	public void setCoordinates(Point2D.Double coords){
		ArrayList<Point2D.Double> coordsArray = new ArrayList<Point2D.Double>();
		coordsArray.add(coords);
		setCoordsArray(coordsArray);
	}
	
	public void setImagesArray(ArrayList<String> imagesArray){
		myDBObj.put(FIELD_IMAGES,imagesArray);
	}
	
	public void setImage(String image){
		ArrayList<String> imagesArray = new ArrayList<String>();
		imagesArray.add(image);
		setImagesArray(imagesArray);
	}
	
	public void setKeyWordsArray(ArrayList<String> keyWordsArray){
		myDBObj.put(FIELD_KEYWORDS,keyWordsArray);
	}
	
	public void setRooms(String rooms){
		myDBObj.put(FIELD_ROOMS,rooms);
	}
	
	public void setMusic(String music){
		myDBObj.put(FIELD_MUSIC,music);
	}
	
	public void setRating(Integer newRating){
		myDBObj.put(FIELD_RATING,newRating);
	}
	
	public void incRating(){
		Integer rating = getRating();
		if(rating == null){
			rating = 0;
		}
		setRating(rating+1);
	}
	
	
	//--------------------------
	//-----Set-methods-end------
	//--------------------------
	
	//-------------------------------------------------------------

	//--------------------------
	//-----XXXByIdByName start--
	//--------------------------

	public void setCityById(ObjectId id){
		myDBObj.put(FIELD_CITY_ID, id);
	}	
	
	public void setCityByName(String name){
		this.setStaticCityName(name);
		ObjectId id = Database.getCityIdByName(name);
		if(id == null){
			DBWrapper a = new DBWrapper();
			a.setName(name);
			a.setType(TYPE_CITY);
			id = Database.add(a);
		}
		setCityById(id);
	}
	
	public void setCountryById(ObjectId id){
		myDBObj.put(FIELD_COUNTRY_ID, id);
	}	
	
	public void setCountryByName(String name){
		this.setStaticCountryName(name);
		ObjectId id = Database.getCountryIdByName(name);
		if(id == null){
			DBWrapper a = new DBWrapper();
			a.setName(name);
			a.setType(TYPE_COUNTRY);
			id = Database.add(a);
		}
		setCountryById(id);
	}
	
	public void setContinentById(ObjectId id){
		myDBObj.put(FIELD_CONTINENT_ID, id);
	}	
	
	public void setContinentByName(String name){
		this.setStaticContinentName(name);
		ObjectId id = Database.getContinentIdByName(name);
		if(id == null){
			DBWrapper a = new DBWrapper();
			a.setName(name);
			a.setType(TYPE_CONTINENT);
			id = Database.add(a);
		}
		setContinentById(id);
	}
		
	//--------------------------
	//-----XXXByIdByName end----
	//--------------------------
	
	//-------------------------------------------------------------

	//--------------------------
	//-----Get-methods-start----
	//--------------------------
	
	public String getAddress(){
		return myDBObj.getString(FIELD_ADDRESS);
	}			
	
	public String getArchitect(){
		return (String) myDBObj.get(FIELD_ARCHITECT);
	}		
	
	public String getCost(){
		return (String) myDBObj.get(FIELD_COST);
	}		
	
	public Date getBuildDate(){
		return (Date) myDBObj.get(FIELD_DATE_FOUNDATION);
	}

	public String getRooms(){
		return myDBObj.getString(FIELD_ROOMS);
	}
	
	public String getMusic(){
		return myDBObj.getString(FIELD_MUSIC);
	}

	public Integer getRating(){
		return (Integer) myDBObj.get(FIELD_RATING);
	}
	
	public String getType(){
		return myDBObj.getString(FIELD_TYPE);
	}	
	
	public String getWebsite(){
		return myDBObj.getString(FIELD_WEBSITE);
	}	
	
	public String getName(){
		return myDBObj.getString(FIELD_NAME);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDescriptionArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_DESC);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getCategoryArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_CATEGORY);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getSourceUrlArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_SOURCE_URL);
	}

	@SuppressWarnings("unchecked")		
	public ArrayList<Point2D.Double> getCoordsArray(){
		ArrayList<DBObject> coordsArray = (ArrayList<DBObject>) myDBObj.get(FIELD_COORDS);
		ArrayList<Point2D.Double> coords = new ArrayList<Point2D.Double>();
		if(coordsArray != null){
			for(DBObject obj : coordsArray){
				coords.add(new Point2D.Double((Double)obj.get("x"),(Double)obj.get("y")));
			}
		}
		return coords;
	}		
	
	public Point2D.Double getCoords(){
		ArrayList<Point2D.Double> coordsAr = getCoordsArray();
		if(!coordsAr.isEmpty()){
			return coordsAr.get(0);
		} else {
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getImagesArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_IMAGES);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getKeyWordsArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_KEYWORDS);
	}
	
	public ObjectId getCityId(){
		return (ObjectId) myDBObj.get(FIELD_CITY_ID);
	}
	
	public ObjectId getCountryId(){
		return (ObjectId) myDBObj.get(FIELD_COUNTRY_ID);
	}
	
	public ObjectId getContinentId(){
		return (ObjectId) myDBObj.get(FIELD_CONTINENT_ID);
	}
	
	public String getCityNameFromDB(){
		DBWrapper city = Database.getDBObjectByIdAndType(this.getCityId(),TYPE_CITY);
		if(city != null){
			return city.getName();
		} else {
			return null;
		}
	}
	
	public String getCountryNameFromDB(){
		DBWrapper country = Database.getDBObjectByIdAndType(this.getCountryId(),TYPE_COUNTRY);
		if(country != null){
			return country.getName();
		} else {
			return null;
		}
	}
	
	public String getContinentNameFromDB(){
		DBWrapper continent = Database.getDBObjectByIdAndType(this.getContinentId(),TYPE_CONTINENT);
		if(continent != null){
			return continent.getName();
		} else {
			return null;
		}
	}

    public boolean isInCategory(String category) {
        return getKeyWordsArray().contains(category);
    }


	//--------------------------
	//-----Get-methods-end----
	//--------------------------
	
	//-------------------------------------------------------------
	
	//-----------------------------
	//-----Static methods start----
	//-----------------------------


    public static ArrayList<String> getTypeNames() {
        ArrayList<String> typeNames = new ArrayList<String>();

        typeNames.add(TYPE_CONTINENT);
        typeNames.add(TYPE_COUNTRY);
        typeNames.add(TYPE_CITY);
        typeNames.add(TYPE_ARCH_ATTRACTION);
        typeNames.add(TYPE_CAFE);
        typeNames.add(TYPE_COMMENT);
        typeNames.add(TYPE_ENTERTAINMENT);
        typeNames.add(TYPE_HOTEL);
        typeNames.add(TYPE_MUSEUM);
        typeNames.add(TYPE_NATURAL_ATTRACTION);
        typeNames.add(TYPE_SHOPPING);
        typeNames.add(TYPE_ATTRACTION);

        return typeNames;
    }
    
    public static String typeToRuName(String type) {
    	String ruTypeName;
    	if(type.equals(TYPE_ARCH_ATTRACTION)){
    		ruTypeName = "Архитектура";
    	} else if(type.equals(TYPE_CAFE)){
    		ruTypeName = "Кафе";
    	} else if(type.equals(TYPE_CITY)){
    		ruTypeName = "Города";
    	} else if(type.equals(TYPE_COMMENT)){
    		ruTypeName = "Комментарии";
    	} else if(type.equals(TYPE_CONTINENT)){
    		ruTypeName = "Континенты";
    	} else if(type.equals(TYPE_COUNTRY)){
    		ruTypeName = "Страны";
    	} else if(type.equals(TYPE_ENTERTAINMENT)){
    		ruTypeName = "Развлечения";
    	} else if(type.equals(TYPE_HOTEL)){
    		ruTypeName = "Отели";
    	} else if(type.equals(TYPE_MUSEUM)){
    		ruTypeName = "Музеи";
    	} else if(type.equals(TYPE_NATURAL_ATTRACTION)){
    		ruTypeName = "Природа";
    	} else if(type.equals(TYPE_SHOPPING)){
    		ruTypeName = "Шопинг";
    	} else {
    		ruTypeName = type;
    	}
        return ruTypeName;
    }
    
	//-----------------------------
	//-----Static methods end------
	//-----------------------------

}