package com.abbas.travelMe.sql;
//REFERENCE
//YouTube. (2018). ANDROID LOGIN AND REGISTER WITH SQLITE DATABASE PT2. [online] Available at: https://www.youtube.com/watch?v=3RewvdB82PY&t=2208s .
//Sort a Map&lt;Key, V. (2018). Sort a Map<Key, Value> by values. [online] Stackoverflow.com. Available at: https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//arraylist and sort
import com.abbas.travelMe.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UserManager.db";

    private static final String TABLE_USER = "user";
    private static final String TABLE_PREF = "pref";
    private static final String TABLE_LOC = "Location";
    private static final String TABLE_LOC_PREF = "lpref";
    private static final String TABLE_LIKE = "like";

    //User table columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Pref table columns
    private static final String COLUMN_PREFU_ID = "pref_id";
    private static final String COLUMN__PREFU_Sporty = "pref_Sporty";
    private static final String COLUMN__PREFU_Nature = "pref_nature";
    private static final String COLUMN__PREFU_Thrill = "pref_thrill";
    private static final String COLUMN__PREFU_Art = "pref_art";
    private static final String COLUMN__PREFU_Social = "pref_social";
    private static final String COLUMN__PREFU_Romantic = "pref_romantic";
    private static final String COLUMN__PREFU_Introvert = "pref_introvert";
    private static final String COLUMN__PREFU_Shopaholic = "pref_shopaholic";
    private static final String COLUMN__PREFU_Film = "pref_film";
    private static final String COLUMN__PREFU_Music = "pref_music";
    private static final String COLUMN_F_ID = "f_id";

    //Location table columns
    private static final String COLUMN_LOC_ID = "loc_id";
    private static final String COLUMN_LOC_Name = "loc_name";
    private static final String COLUMN_LOC_PlaceID="Place_ID";

    //Pref LOCATION table columns
    private static final String COLUMN_PREFL_ID = "prefL_id";
    private static final String COLUMN__PREFL_Sporty = "prefL_Sporty";
    private static final String COLUMN__PREFL_Nature = "prefL_nature";
    private static final String COLUMN__PREFL_Thrill = "prefL_thrill";
    private static final String COLUMN__PREFL_Art = "prefL_art";
    private static final String COLUMN__PREFL_Social = "prefL_social";
    private static final String COLUMN__PREFL_Romantic = "prefL_romantic";
    private static final String COLUMN__PREFL_Introvert = "prefL_introvert";
    private static final String COLUMN__PREFL_Shopaholic = "prefL_shopaholic";
    private static final String COLUMN__PREFL_Film = "prefL_film";
    private static final String COLUMN__PREFL_Music = "prefL_music";
    private static final String COLUMN_FLOC_ID = "f_id";

    //likes table
    private static final String COLUMN_LIKE_ID = "like_id";
    private static final String COLUMN_F_USER_ID = "f_u_id";
    private static final String COLUMN_F_LOC_ID = "f_l_id";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";


    private static final String CREATE_PREF_TABLE = "CREATE TABLE " + TABLE_PREF + "("
            + COLUMN_PREFU_ID + " INTEGER PRIMARY KEY," + COLUMN__PREFU_Sporty + " TEXT," + COLUMN__PREFU_Nature + " TEXT," +
            COLUMN__PREFU_Thrill + " TEXT," + COLUMN__PREFU_Art + " TEXT," + COLUMN__PREFU_Social + " TEXT," +
            COLUMN__PREFU_Romantic + " TEXT," + COLUMN__PREFU_Introvert + " TEXT," + COLUMN__PREFU_Shopaholic +
            " TEXT," + COLUMN__PREFU_Film + " TEXT," + COLUMN__PREFU_Music + " TEXT, " +
            COLUMN_F_ID + " INTEGER, FOREIGN KEY(" + COLUMN_F_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";


    private String CREATE_LOC_TABLE = "CREATE TABLE " + TABLE_LOC + "("
            + COLUMN_LOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LOC_Name + " TEXT," + COLUMN_LOC_PlaceID + " TEXT " + ")";


    private static final String CREATE_PREFL_TABLE = "CREATE TABLE " + TABLE_LOC_PREF + "("
            + COLUMN_PREFL_ID + " INTEGER PRIMARY KEY," + COLUMN__PREFL_Sporty + " TEXT," + COLUMN__PREFL_Nature + " TEXT," +
            COLUMN__PREFL_Thrill + " TEXT," + COLUMN__PREFL_Art + " TEXT," + COLUMN__PREFL_Social + " TEXT," +
            COLUMN__PREFL_Romantic + " TEXT," + COLUMN__PREFL_Introvert + " TEXT," + COLUMN__PREFL_Shopaholic +
            " TEXT," + COLUMN__PREFL_Film + " TEXT," + COLUMN__PREFL_Music + " TEXT, " +
            COLUMN_FLOC_ID + " INTEGER, FOREIGN KEY(" + COLUMN_FLOC_ID + ") REFERENCES " + TABLE_LOC + "(" + COLUMN_LOC_ID + "))";


    private static final String CREATE_LIKE_TABLE = "CREATE TABLE " + TABLE_LIKE + "("
            + COLUMN_LIKE_ID + " INTEGER PRIMARY KEY,"  +
            COLUMN_F_USER_ID + " INTEGER," +
            COLUMN_F_LOC_ID + " INTEGER REFERENCES " + TABLE_LOC + " (" + COLUMN_LOC_ID + "))";


    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_PREF_TABLE = "DROP TABLE IF EXISTS " + TABLE_PREF;
    private String DROP_USER_LOC = "DROP TABLE IF EXISTS " + TABLE_LOC;
    private String DROP_PREFL_TABLE = "DROP TABLE IF EXISTS " + TABLE_LOC_PREF;
    private String DROP_LIKE_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIKE;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PREF_TABLE);
        db.execSQL(CREATE_LOC_TABLE);
        db.execSQL(CREATE_PREFL_TABLE);
        db.execSQL(CREATE_LIKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PREF_TABLE);
        db.execSQL(DROP_USER_LOC);
        db.execSQL(DROP_PREFL_TABLE);
        db.execSQL(DROP_LIKE_TABLE);
        onCreate(db);
    }

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();


    }

    public void addplace(String placeName, String PlaceID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_LOC_Name, placeName);
        value.put(COLUMN_LOC_PlaceID, PlaceID);
        db.insert(TABLE_LOC,null, value);
        db.close();
    }


    public int queryID(String name) {
        int result = 0;
        String theID = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = '" + name + "'";

        Cursor c = getReadableDatabase().rawQuery(theID, null);
        while (c.moveToNext()) {
            result = c.getInt(0);
            Log.w("myApp", String.valueOf(result));

        }
        return result;
    }

    public int queryLocID(String name) {
        int result = 0;
        String theID = "SELECT " + COLUMN_LOC_ID + " FROM " + TABLE_LOC + " WHERE " + COLUMN_LOC_Name + " = '" + name + "'";

        Cursor c = getReadableDatabase().rawQuery(theID, null);
        while (c.moveToNext()) {
            result = c.getInt(0);
            Log.w("the location ID IS", String.valueOf(result));
        }
        return result;
    }

    public int email(String email) {
        int result = 0;
        String ID = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'";

        Cursor c = getReadableDatabase().rawQuery(ID, null);
        while (c.moveToNext()) {
            result = c.getInt(0);
            Log.w("myApp", String.valueOf(result));

        }
        return result;
    }

    public void updatePref(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8, boolean b9, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN__PREFU_Sporty, b);
        v.put(COLUMN__PREFU_Nature, b1);
        v.put(COLUMN__PREFU_Thrill, b2);
        v.put(COLUMN__PREFU_Art, b3);
        v.put(COLUMN__PREFU_Social, b4);
        v.put(COLUMN__PREFU_Romantic, b5);
        v.put(COLUMN__PREFU_Introvert, b6);
        v.put(COLUMN__PREFU_Shopaholic, b7);
        v.put(COLUMN__PREFU_Film, b8);
        v.put(COLUMN__PREFU_Music, b9);
        v.put(COLUMN_F_ID, id);
        db.insert(TABLE_PREF, null, v);
        db.close();
    }

    public void updateLocPref(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8, boolean b9, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN__PREFL_Sporty, b);
        v.put(COLUMN__PREFL_Nature, b1);
        v.put(COLUMN__PREFL_Thrill, b2);
        v.put(COLUMN__PREFL_Art, b3);
        v.put(COLUMN__PREFL_Social, b4);
        v.put(COLUMN__PREFL_Romantic, b5);
        v.put(COLUMN__PREFL_Introvert, b6);
        v.put(COLUMN__PREFL_Shopaholic, b7);
        v.put(COLUMN__PREFL_Film, b8);
        v.put(COLUMN__PREFL_Music, b9);
        v.put(COLUMN_FLOC_ID, id);
        db.insert(TABLE_LOC_PREF, null, v);
        db.close();
    }

/*
    public void addLocation() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues va = new ContentValues();
        String location = "marble arch";
        va.put(COLUMN_LOC_Name, location);
        va.put(COLUMN_LOC_PlaceID, "dasdasd23213");
        db.insert(TABLE_LOC, null, va);
        db.close();

        SQLiteDatabase dba = this.getWritableDatabase();
        String theID = "SELECT " + COLUMN_LOC_ID + " FROM " + TABLE_LOC + " WHERE " + COLUMN_LOC_Name + " = '" + location + "'";
        int result = 0;
        Cursor c = getReadableDatabase().rawQuery(theID, null);
        while (c.moveToNext()) {
            result = c.getInt(0);
            Log.w("myAppi", String.valueOf(result));
        }
        ContentValues val = new ContentValues();
        val.put(COLUMN__PREFL_Sporty, false);
        val.put(COLUMN__PREFL_Nature, false);
        val.put(COLUMN__PREFL_Thrill, false);
        val.put(COLUMN__PREFL_Art, false);
        val.put(COLUMN__PREFL_Social, false);
        val.put(COLUMN__PREFL_Romantic, false);
        val.put(COLUMN__PREFL_Introvert, false);
        val.put(COLUMN__PREFL_Shopaholic, false);
        val.put(COLUMN__PREFL_Film, false);
        val.put(COLUMN__PREFL_Music, true);
        val.put(COLUMN_FLOC_ID, result);
        dba.insert(TABLE_LOC_PREF, null, val);
        dba.close();
    }
*/


    public boolean checkUser(String email) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String email, String password) {// check for user in the table
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getPreference(int id) {
        ArrayList<String> results = new ArrayList<>();

        String query = "SELECT " + COLUMN__PREFU_Sporty + ", " + COLUMN__PREFU_Nature + ", " +
                COLUMN__PREFU_Thrill + ", " + COLUMN__PREFU_Art + ", " +
                COLUMN__PREFU_Social + ", " + COLUMN__PREFU_Romantic + ", " +
                COLUMN__PREFU_Introvert + ", " + COLUMN__PREFU_Shopaholic + ", " +
                COLUMN__PREFU_Film + ", " + COLUMN__PREFU_Music + " FROM " + TABLE_PREF + " WHERE " + COLUMN_F_ID + " = " + id;

        Cursor c = getReadableDatabase().rawQuery(query, null);
        while (c.moveToNext()) {
            for (int i = 0; i < 10; i++) {
                results.add(c.getString(i));
            }
        }
        return results;
    }


    public Map<Integer, Integer> countNo(ArrayList<String> users) {
        ArrayList<String> res = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int p=1;
        int m=0;
        String query = "SELECT " + COLUMN__PREFL_Sporty + ", " + COLUMN__PREFL_Nature + ", " +
                COLUMN__PREFL_Thrill + ", " + COLUMN__PREFL_Art + ", " +
                COLUMN__PREFL_Social + ", " + COLUMN__PREFL_Romantic + ", " +
                COLUMN__PREFL_Introvert + ", " + COLUMN__PREFL_Shopaholic + ", " +
                COLUMN__PREFL_Film + ", " + COLUMN__PREFL_Music + " FROM " + TABLE_LOC_PREF;
        Cursor c = getReadableDatabase().rawQuery(query, null);
        while (c.moveToNext()) {
            for (int i = 0; i < 10; i++) {
                res.add(c.getString(i));

            }
            System.out.println("praha "+res);
            m=countMatches(res,users);
            map.put(p,m);
            res.clear();
            p++;
        }
        System.out.println("prartna"+ map);

        map=sortByValue(map);

        return map;
    }

    public int countMatches(ArrayList<String> a, ArrayList<String> b) {
        int counter = 0;



        for (int i = 0; i < a.size(); i++) {

            if (a.get(i).equals("1") && b.get(i).equals("1")) {
                counter++;
            }
        }

        System.out.println(counter);

        return counter;
    }

    private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
        System.out.println("before");
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
            public int compare(Object o2, Object o1) {
                System.out.println("before");
                return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue()).compareTo(((Map.Entry<K, V>) (o2)).getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        System.out.println("before");
        for (Iterator<Map.Entry<K, V>> it = list.iterator(); it.hasNext();) {
            Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public String [] getlocname(List<Integer> sorted) {

        String[] arr =new String[sorted.size()];
        //System.out.println(sorted.get(1));


        for (int i =1; i<=sorted.size(); i++) {
            int k=i-1;
         String query = "SELECT " + COLUMN_LOC_Name + " FROM " + TABLE_LOC + " WHERE " + COLUMN_LOC_ID + " = " + sorted.get(k);
         Cursor c = getReadableDatabase().rawQuery(query, null);
          while (c.moveToNext()) {

               System.out.println(c.getString(0));
               arr[k] = c.getString(0);

         }
        }

         System.out.println(arr);

          return arr;
         }

         public String ThePlaceID(String placename){
             String PlaceId="";
             String query = "SELECT " + COLUMN_LOC_PlaceID + " FROM " + TABLE_LOC + " WHERE " + COLUMN_LOC_Name + " = '" + placename + "'";
             Cursor c = getReadableDatabase().rawQuery(query, null);
             while (c.moveToNext()) {
                 PlaceId = c.getString(0);
             }
            return PlaceId;
         }

         public void addLike(int user, int loc){
             SQLiteDatabase db = this.getWritableDatabase();
             ContentValues va = new ContentValues();
             va.put(COLUMN_F_USER_ID,user );
             va.put(COLUMN_F_LOC_ID, loc);
             db.insertOrThrow(TABLE_LIKE, null, va);
             db.close();

         }

         public int Likequeryusers() {
             int k=0;
             String query = "SELECT DISTINCT " + COLUMN_F_USER_ID + " FROM " + TABLE_LIKE;
             Cursor c = getReadableDatabase().rawQuery(query, null);
             while (c.moveToNext()) {
                 k=k+1;
             }
          return k;
         }

    public ArrayList<String> gettheuser(int userID){
             String query = "SELECT DISTINCT " + COLUMN_F_LOC_ID + " FROM " + TABLE_LIKE  + " WHERE " + COLUMN_F_USER_ID + " = '" + userID + "'";
             Cursor c = getReadableDatabase().rawQuery(query, null);
                ArrayList<String> userlocations= new ArrayList<String>();
             while (c.moveToNext()) {
                 userlocations.add(c.getString(0));
             }

             return userlocations;
         }

    public ArrayList<String> gettheusers(int k){
        String query = "SELECT  DISTINCT " + COLUMN_F_LOC_ID + " FROM " + TABLE_LIKE  + " WHERE " + COLUMN_F_USER_ID + " = '" + k + "'";
        Cursor c = getReadableDatabase().rawQuery(query, null);
        ArrayList<String> user= new ArrayList<String>();
        while (c.moveToNext()) {
            user.add(c.getString(0));
        }

        return user;
    }

}
