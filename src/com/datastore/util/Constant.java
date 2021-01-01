package com.datastore.util;

public class Constant {
    public static final String defaultDataStoreLoc = "C:\\Users\\Public\\Documents";
    public static final int MILLISECONDS = 1000;
    public static final int MAX_LENGTH = 32;
    public static final int SIZE=1024;

    public static final String FAILURE_KEY_LENGTH_EXCEEDED = "Failed due to key length more than limit(32 chars";
    public static final String FAILURE_ENTER_NEW_KEY = "Operation failed - Key already available";
    public static final String FAILURE_KEY_NOT_AVAILABLE = "Operation failed- Key not available";
    public static final String SUCCESSFULLY_CREATED = "Create operation successful";
    public static final String CREATE_FAILURE = "Create operation failed";
    public static final String READ_FAILURE = "Rread operation failed due to unknown error, please try again later!";
    public static final String SUCCESSFULLY_DELETE = "Record deletion successful";
    public static final String DELETE_FAILURE = "Record deletion failed";
}
