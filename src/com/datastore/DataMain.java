package com.datastore;
import com.datastore.main.DataStore;
import org.json.simple.JSONObject;
public class DataMain {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Cristano");
        jsonObject.put("lastName", "Ronaldo");
        jsonObject.put("address", "Italy");

        System.out.println("******************** CREATE ********************");
        // Create Operation

        DataStore myDataStore = new DataStore(
                "C:\\Users\\Vignesh\\Documents\\DataStore");
        System.out.println(myDataStore.create("1", jsonObject, 60));// success
        System.out.println(myDataStore.create("1", jsonObject));// failure
        System.out.println(myDataStore.create("1", jsonObject, 60));// failure
        System.out.println(myDataStore.create("2", jsonObject));// success
        System.out.println(myDataStore.create(
                "ThisIsKeyNameValidation", new JSONObject()));// failure
        try {
            // wait for 10 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("******************** AFTER WAIT ********************");
        jsonObject.put("age", "25");
        System.out.println(myDataStore.create("1", jsonObject, 10));// success
        System.out.println(myDataStore.create("1", jsonObject));// failure
        System.out.println(myDataStore.create("1", jsonObject, 60));// failure
        System.out.println(myDataStore.create("2", jsonObject));// failure

        // Read Operation

        System.out
                .println("******************** READ ********************");

        System.out.println(myDataStore.read("1"));// success
        System.out.println(myDataStore.read("2"));// success
        System.out.println(myDataStore.read("3"));// failure
        System.out.println(myDataStore
                .read("ThisIsKeyNameValidation"));// failure
        try {
            // wait for 10 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        System.out.println("******************** AFTER WAIT ********************");
        System.out.println(myDataStore.read("1"));// failure
        System.out.println(myDataStore.read("2"));// success


        System.out.println("******************** DELETE ***********************");

        System.out.println(myDataStore.delete("1"));// failure
        System.out.println(myDataStore.delete("2"));// success
        System.out.println(myDataStore.delete("2"));// failure
        System.out.println(myDataStore.delete("3"));// failure
        System.out.println(myDataStore
                .delete("ThisIsKeyNameValidation"));// failure

    }
}
