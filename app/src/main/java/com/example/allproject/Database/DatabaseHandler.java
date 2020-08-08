package com.example.allproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.allproject.Class.Category;
import com.example.allproject.Class.Employ;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DatabaseHandler extends SQLiteOpenHelper {


    //-------1
    private static final String TAG_Mgs = "DatabaseHandoler";

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "employDB01";
    // table name
    private static final String TABLE_USERINFO = "employ";



    private static final String EMPLOY_ID = "Id";
    private static final String EMPLOY_NAME = "name";
    private static final String EMPLOY_POSITION = "position";
    private static final String EMPLOY_CONTACT = "contact";
    private static final String EMPLOY_WEBPAGE = "webpage";
    private static final String EMPLOY_EMAIL = "email";
    private static final String EMPLOY_ADDRESS = "address";


    //-----------2--------


    //---4

    public DatabaseHandler( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //----------4

    //------extends SQLiteOpenHelper- by add--3--
    @Override
    public void onCreate(SQLiteDatabase db) {




        try {
            String CREATE_USERINFO = "CREATE TABLE " + TABLE_USERINFO + "("
                    + EMPLOY_ID + "  INTEGER PRIMARY KEY, "
                    + EMPLOY_NAME + " TEXT, "
                    + EMPLOY_POSITION + " TEXT, "
                    + EMPLOY_CONTACT+ " TEXT,"
                    + EMPLOY_WEBPAGE+ " TEXT,"
                    + EMPLOY_EMAIL+ " TEXT,"
                    + EMPLOY_ADDRESS+ " TEXT"
                    +")";

            db.execSQL(CREATE_USERINFO);
        } catch (Exception e) {

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);

    }
    //------extends SQLiteOpenHelper- by add--3--


    // insert data
    public void insertUserInfo(String name, String position, String contact, String webpage, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EMPLOY_NAME, name);
        values.put(EMPLOY_POSITION, position);
        values.put(EMPLOY_CONTACT, contact);
        values.put(EMPLOY_WEBPAGE, webpage);
        values.put(EMPLOY_EMAIL, email);
        values.put(EMPLOY_ADDRESS, address);

        // Inserting Row
        db.insert(TABLE_USERINFO, null, values);
        Log.d("TAG", "Insert success");
        db.close(); // Closing database connection
    }


    //----------------------view data


    public ArrayList<Employ> getAllInfo(Context context) {

        ArrayList<Employ> userInfoList = new ArrayList<Employ>();
        try {

            String selectQuery = "SELECT  * FROM  "+ TABLE_USERINFO+"" ;

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();  //Move the cursor to the first row.

            //Returns whether the cursor is pointing to the position after the last row.
            while (!cursor.isAfterLast()) {
                Employ customList = new Employ();

                String Id = cursor.getString(0).toString() ;

                customList.setId(Id);


                String name = cursor.getString(1).toString();
                customList.setName(name);

                String position = cursor.getString(2).toString();
                customList.setPosition(position);

                String contact = cursor.getString(3).toString();
                customList.setContact(contact);

                String webpage = cursor.getString(4).toString();
                customList.setWebpage(webpage);

                String email = cursor.getString(5).toString();
                customList.setEmail(email);

                String address = cursor.getString(6).toString();
                customList.setAddress(address);


                userInfoList.add(customList);
                cursor.moveToNext();  //Move the cursor to the next row.

            }
            cursor.close();
            db.close();

        }

        catch (Exception ex) {
            Log.e("getCampaignId Excep. :", ex.toString());
        }

        return userInfoList;
    }


    //----------------------view data





}//lb