package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

import org.bson.types.ObjectId;



public class StorageTester {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	
   
        Database.connectToMainBase();  
        //Database.unificationNames();
        
        User user = new User();
        user.setKeyValue(User.FIELD_LOGIN, "aaaa");
        user.setKeyValue(User.FIELD_NAME, "Name");
        Database.addUser(user);
        ArrayList<User> users = Database.getAllUsers();
        User user1 = new User();
        user1.setKeyValue(User.FIELD_LOGIN, "affffff");
        user1.setKeyValue(User.FIELD_NAME, "Name");
        Database.addUser(user1);
       
        ArrayList<DBWrapper> collectionUsers = Database.getAllDBObjects();
        int k = 0;
        Tour tour = new Tour();
        tour.setKeyValue(Tour.FIELD_NAME, "aaaa");
        DBWrapper attr = new DBWrapper();
        attr.setName(";;;");
        attr.setType(Database.COLLECTION_ATTRACTIONS);
        Database.add(attr);
        tour.setId((ObjectId) attr.getId());
        
        System.out.println(tour.getName());
        tour.setKeyValue(Tour.FIELD_ID_USER, user.getId().toString());
        tour.setKeyValue(Tour.FIELD_ID_USER, user.getId().toString());
        System.out.println(attr.getId());
        tour.addAttraction(attr.getId());
        Database.addTour(tour);
       
        
    }
}
