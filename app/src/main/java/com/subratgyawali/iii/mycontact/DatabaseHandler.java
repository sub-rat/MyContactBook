package com.subratgyawali.iii.mycontact;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iii on 11/28/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    //    All static variable
//    Database version
    private static final int DATABASE_VERSION = 1;
    public DatabaseUpdatedListener databaseUpdatedListener;
    //    Database name
    private static final String DATABASE_NAME = "myContact";

    //    Contact table name
    private static final String TABLE_NAME_CONTACT = "contacts";

    //    Contact table columns name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //creating tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_NAME_CONTACT + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_PH_NO + " TEXT, "
                + KEY_EMAIL + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    //upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop older version if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONTACT);
        //create table again
        onCreate(sqLiteDatabase);
    }


    //    Adding new Contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());

        //inserting row
        if (db.insert(TABLE_NAME_CONTACT, null, values) != -1) {
            databaseUpdatedListener.setDatabaseSuccess("Test", "Test", "Test");
        } else {
            databaseUpdatedListener.setDatabaseError("Test");
        }

        db.close();
    }


    //    Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_CONTACT,
                new String[]{KEY_ID, KEY_NAME, KEY_PH_NO, KEY_EMAIL},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return contact;

    }


    //    getting all contact
    public List<Contact> getAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_CONTACT;
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all list and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;


    }


    //    getting contact count
    public int getContactCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();

    }

    //    updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());

        // updating row
//        return db.update(TABLE_NAME_CONTACT, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(contact.get_id())});
int update = db.update(TABLE_NAME_CONTACT, values, KEY_ID + " = ?",
        new String[]{String.valueOf(contact.get_id())});
        if(update != -1){
    databaseUpdatedListener.setDatabaseSuccess(contact.getName(),contact.getPhone(),contact.getEmail());
                }else {
                    databaseUpdatedListener.setDatabaseError("failed");
                }
        return update;
    }

    //    deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
       int del = db.delete(TABLE_NAME_CONTACT, KEY_ID + " = ?",new String[]{String.valueOf(contact.get_id())});
        Log.d("deleted_data",del+"");
        if (del != -1){
        databaseUpdatedListener.setDatabaseSuccess("Test", "Test", "Test");
    } else {
        databaseUpdatedListener.setDatabaseError("Test");
    }
        db.close();
    }
}
