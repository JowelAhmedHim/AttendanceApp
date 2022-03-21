package com.example.attendance;

public class Constant {

    public static final String DB_NAME = "ATTENDANCE_DB";
    public static final int DB_VERSION = 1;

    // CLASS TABLE
    public static final String CLASS_TABLE_NAME = "CLASS_TABLE";

    public static final String C_ID = "_CID";
    public static final String CLASS_NAME_KEY = "CLASS_NAME";
    public static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";


    public static final String CREATE_CLASS_TABLE  =
            "CREATE TABLE " + CLASS_TABLE_NAME + " ( "+
                    C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                    CLASS_NAME_KEY + " TEXT NOT NULL, "+
                    SUBJECT_NAME_KEY + " TEXT NOT NULL, "+
                    "UNIQUE ( " + CLASS_NAME_KEY + ", " + SUBJECT_NAME_KEY + " )"+
                    ");";
    public static final String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS "+CLASS_TABLE_NAME;
    public static final String SELECT_CLASS_TABLE = "SELECT * FROM "+CLASS_TABLE_NAME;


    //STUDENT TABLE
    public static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";

    public static final String S_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY = "STUDENT_ROLL";

    public static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME + "( "+
                    S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                    C_ID + " INTEGER NOT NULL, " +
                    STUDENT_NAME_KEY + " TEXT NOT NULL, "+
                    STUDENT_ROLL_KEY + " TEXT NOT NULL, "+
                    " FOREIGN KEY ( "+C_ID+" ) REFERENCES " + CLASS_TABLE_NAME +" ( "+C_ID+" )"+
                    ");";
    public static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS "+STUDENT_TABLE_NAME;
    public static final String SELECT_STUDENT_TABLE = "SELECT * FROM "+STUDENT_TABLE_NAME;


    //STATUS TABLE
    public static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";

    public static final String CREATE_STATUS_TABLE =
            "CREATE TABLE "+ STATUS_TABLE_NAME + "( "+
                    STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                    S_ID + " INTEGER NOT NULL, "+
                    DATE_KEY + " DATE NOT NULL, "+
                    STATUS_KEY + " TEXT NOT NULL, "+
                    " UNIQUE ( "+S_ID + "," + DATE_KEY + " ),"+
                    " FOREIGN KEY ( "+ S_ID + " ) REFERENCES "+ STUDENT_TABLE_NAME + "( "+S_ID+" )"+
                    ");";
    public static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS "+STATUS_TABLE_NAME;
    public static final String SELECT_STATUS_TABLE = "SELECT * FROM "+STATUS_TABLE_NAME;
}
