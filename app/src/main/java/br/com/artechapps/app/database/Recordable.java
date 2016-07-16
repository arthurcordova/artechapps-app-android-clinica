package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 *
 * Created by acstapassoli on 30/09/2015.
 */
public interface Recordable {

    /**
     * For insert values
     * @param values Values to insert
     * @return boolean
     */
    boolean insert(ContentValues values);
    /**
     * For insert values
     * @param values Values to insert
     * @return boolean
     */
    boolean insert(List<ContentValues> values);
    /**
     * For update record
     * @param values Update values
     * @param whereClause Where clause
     * @param whereArgs Where clause values
     * @return boolean
     */
    boolean update(ContentValues values, String whereClause, String[] whereArgs);
    /**
     * Delete record
     * @param whereClause Where clause
     * @param whereArgs Where clause values
     * @return boolean
     */
    boolean delete(String whereClause, String[] whereArgs);

    /**
     * For all records
     * @return All records table. Ex: SELECT * FROM TABLE_NAME
     */
    Cursor find();

    /**
     * For all records
     * @return All records table. Ex: SELECT * FROM TABLE_NAME
     */
    Cursor find(String orderBy);

    /**
     * For unique record
     * @param id Record id
     * @return Only one record. Ex: SELECT * FROM TABLE_NAME WHERE RECORD_ID = ?
     */
    Cursor find(Integer id);

    /**
     * Find with restrictions
     * @param whereClause
     * @param whereArgs
     * @return Return one more record
     */
    Cursor find(String whereClause, String[] whereArgs);

    /**
     * Find with restrictions
     * @param whereClause
     * @param whereArgs
     * @param orderBy
     * @return Return one more record
     */
    Cursor find(String whereClause, String[] whereArgs, String orderBy);

    /**
     * Close data base
     * @param db SQLiteDatabase
     * @return boolean
     */
    boolean closeDb(SQLiteDatabase db);

}
