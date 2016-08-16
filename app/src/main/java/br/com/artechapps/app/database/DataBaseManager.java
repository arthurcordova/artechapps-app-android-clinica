package br.com.artechapps.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Database manager, execute create and update scripts tables.
 * If app version greater than the current, drop and recreates tables.
 *
 * Created by acstapassoli on 01/12/2015.
 */
public class DataBaseManager extends SQLiteOpenHelper {

    public final String TAG = DataBaseManager.class.getSimpleName();
    private SQLiteDatabase mDb;

    public DataBaseManager(Context context) {
        super(context, DataBaseConfig.NAME, null, DataBaseConfig.VERSION);
        mDb = getWritableDatabase();


    }

    private void forEachScript(ArrayList<String> scripts, SQLiteDatabase db){
        for (String script : scripts) {
            Log.i(TAG, script);
            db.execSQL(script);
        }
    }

    public SQLiteDatabase getmDb() {
        return mDb;
    }

    /**
     * If first time app install
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "............Creating database............");
        forEachScript(DataBaseConfig.loadCreateScripts(), db);
    }

    /**
     * If version changed
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "............Upgrading database............");
        forEachScript(DataBaseConfig.loadDropScripts(), db);
        onCreate(db);
    }

    /**
     * If back version
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "............Downgrading database............");
        onUpgrade(db,oldVersion,newVersion);
    }

    /**
     * WARNING - DROP ALL TABLES
     *
     * @param cx
     */
    public static void dropAll(Context cx){
        DataBaseManager dbm = new DataBaseManager(cx);
        dbm.forEachScript(DataBaseConfig.loadDropScripts(),dbm.getmDb());
        dbm.close();
    }
}
