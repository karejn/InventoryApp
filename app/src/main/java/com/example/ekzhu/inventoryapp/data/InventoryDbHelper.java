package com.example.ekzhu.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ekzhu.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by ekzhu on 17.07.2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String TAG = InventoryDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "inventory.db";

    /** Database version */
    private static final int DATABASE_VERSION = 1;


    /**
     * Default Constructor
     * @param context
     */
    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Function - CREATE table
     * This method is called when the database is created for the first time
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COL_NAME + " TEXT NOT NULL, "
                + InventoryEntry.COL_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.COL_PRICE + " REAL NOT NULL DEFAULT 0.0, "
                + InventoryEntry.COL_PICTURE + " TEXT NOT NULL DEFAULT 'No images', "
                + InventoryEntry.COL_DESCRIPTION + " TEXT NOT NULL DEFAULT 'New Product ', "
                + InventoryEntry.COL_ITEMS_SOLD + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.COL_SUPPLIER + " TEXT NOT NULL DEFAULT 'new' "
                + ");";

        db.execSQL(SQL_CREATE_INVENTORY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
