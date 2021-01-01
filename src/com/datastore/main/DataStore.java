package com.datastore.main;

import com.datastore.Datamodel.Data;
import com.datastore.util.Constant;
import com.datastore.util.Util;
import org.json.simple.JSONObject;
import java.util.*;
public class DataStore {
    private String Dataloc;
    private String Dataname;
// Constructor to initalize Datastore with default storage
    public DataStore() {
        try {
            Dataloc = Constant.defaultDataStoreLoc;
            Dataname = "datastore";
        } catch (Exception exception) {

        }
    }
    // Constructor to initalize Datastore in given storage path
    public DataStore(String Path) {
        try {
            Dataloc = Path;
            Dataname = "datastore";
        } catch (Exception exception) {

        }
    }
    // Method to Create a key in a DataStore without timeToLive
    public synchronized String create(String key, JSONObject value) {
        try {
            return create(key, value, -1);
        } catch (Exception exception) {
            return Constant.CREATE_FAILURE;
        }
    }
    // Method to Create a key in a DataStore with timeToLive
    public synchronized String create(String key, JSONObject value,
                                      int timeToLive) {
        try {
            String Path = Dataloc + "/" + Dataname;
            // validate the key
            if (!Util.isKeyNameValid(key)) {
                return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
            }
            if (Util.isKeyExists(key,Path)) {
                return Constant.FAILURE_ENTER_NEW_KEY;
            }
            // success flow
            Data data = new Data();
            data.setKey(key);
            if (timeToLive > 0) {
                data.setTimeToLive(timeToLive);
            } else {
                data.setTimeToLive(-1);
            }
            data.setValue(value);
            data.setCreationDateTimeMillis(new Date().getTime());

            if (Util.writeData(data, Path)) {
                return Constant.SUCCESSFULLY_CREATED;
            } else {
                return Constant.CREATE_FAILURE;
            }
        } catch (Exception exception) {
            return Constant.CREATE_FAILURE;
        }
    }
    // Method to read an key from the DataStore
    public synchronized Object read(String key) {
        try {
            String filePath = Dataloc + "/" + Dataname;
            // validate the key
            if (!Util.isKeyNameValid(key)) {
                return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
            }
            if (!Util.isKeyExists(key, filePath)) {
                return Constant.FAILURE_KEY_NOT_AVAILABLE;
            }
            // success flow

            Data data = Util.readData(key, filePath);
            if (null != data) {
                return data.getValue();
            }
            return Constant.READ_FAILURE;
        } catch (Exception exception) {
            exception.printStackTrace();
            return Constant.READ_FAILURE;
        }
    }
    // Method to read an element from the DataStore
    public synchronized Object delete(String key) {
        try {
            String filePath = Dataloc + "/" + Dataname;
            // validate the key
            if (!Util.isKeyNameValid(key)) {
                return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
            }
            if (!Util.isKeyExists(key, filePath)) {
                return Constant.FAILURE_KEY_NOT_AVAILABLE;
            }
            // success flow

            if (Util.deleteData(key, filePath)) {
                return Constant.SUCCESSFULLY_DELETE;
            }
            return Constant.DELETE_FAILURE;
        } catch (Exception exception) {
            exception.printStackTrace();
            return Constant.DELETE_FAILURE;
        }
    }
}
