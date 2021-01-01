package com.datastore.util;

import com.datastore.Datamodel.Data;


import java.io.*;

import java.util.Date;
import java.util.HashMap;
public class Util {
   //check for Valid Key
    public static boolean isKeyNameValid(String key) {
        if (key.length() > Constant.MAX_LENGTH) {
            return false;
        }
        return true;
    }
    //Check if key Already Exists
    public static boolean isKeyExists(String key, String filePath) {
        boolean isKeyExists = false;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        HashMap<String, Data> datatable = new HashMap<String, Data>();
        try {
            File file = new File(filePath);
            // check if files exists
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                datatable = (HashMap<String, Data>) objectInputStream
                        .readObject();
                // check if key exists
                if (datatable.containsKey(key)) {
                    isKeyExists = true;
                }

                fileInputStream.close();
                objectInputStream.close();
            }
            if (isKeyExists) {
                Data data = datatable.get(key);
                long currentDateTimeMillis = new Date().getTime();
                if (data.getTimeToLive() > 0
                        && (currentDateTimeMillis - data
                        .getCreationDateTimeMillis()) >= (data
                        .getTimeToLive() * Constant.MILLISECONDS)) {
                    // the object is expired, So remove from datastore
                    datatable.remove(key);
                    fileOutputStream = new FileOutputStream(file);
                    objectOutputStream = new ObjectOutputStream(
                            fileOutputStream);
                    objectOutputStream.writeObject(datatable);
                    fileOutputStream.close();
                    objectOutputStream.close();

                    // Since object is removed the key is available for storage
                    isKeyExists = false;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isKeyExists;
    }
    // Method to Write Data to DataStore
    public static boolean writeData(Data data, String filePath) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        HashMap<String, Data> Datatable = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // read the existing file data
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                Datatable = (HashMap<String, Data>) objectInputStream
                        .readObject();

                fileInputStream.close();
                objectInputStream.close();

                // add the new element
                Datatable.put(data.getKey(), data);

                // calling method check the file size
                if(checkSize(Datatable)) {
                    return false;
                }
                // write the data to file
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(Datatable);
                fileOutputStream.close();
                objectOutputStream.close();

                return true;
            } else {
                Datatable = new HashMap<String, Data>();
                Datatable.put(data.getKey(), data);

                // write the data to file
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(Datatable);
                fileOutputStream.close();
                objectOutputStream.close();

                return true;
            }
        } catch (Exception exception) {
            return false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
    //Method to check size if it is under 1GB
    private static boolean checkSize(HashMap<String, Data> datatable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(datatable);
        oos.close();
        long fileSizeInBytes = byteArrayOutputStream.size();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes /Constant.SIZE;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB /Constant.SIZE;
        if(fileSizeInMB >1000){
            return  true;
        }
        return false;
    }
    public static Data readData(String key, String filePath) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        HashMap<String, Data> dataMap = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // read the existing file data
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                dataMap = (HashMap<String, Data>) objectInputStream
                        .readObject();

                fileInputStream.close();
                objectInputStream.close();
                return dataMap.get(key);
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Method to delete data from DataStore
    public static boolean deleteData(String key, String filePath) {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        HashMap<String, Data> datatable = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // read the existing file data
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                datatable = (HashMap<String, Data>) objectInputStream
                        .readObject();

                fileInputStream.close();
                objectInputStream.close();

                // add the new element
                datatable.remove(key);

                // write the data to file
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(datatable);
                fileOutputStream.close();
                objectOutputStream.close();

                return true;
            }
            return false;
        } catch (Exception exception) {
            return false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
