package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * Persistence loaded as the repository
 *
 * Created by acstapassoli on 30/09/2015.
 */
public class Persistence implements Recordable, Serializable {

    private final String TAG = Persistence.class.getSimpleName();
    private final String MSG_ERRO_INSERT = "Erro ao inserir o registro. -> ";
    private final String MSG_ERRO_UPDATE = "Erro ao atualizar o registro. -> ";
    private final String MSG_ERRO_DELETE = "Erro ao excluir o registro. -> ";
    private final String MSG_ERRO_SELECT = "Erro ao encontrar o registro. -> ";
    private SQLiteDatabase db;
    private String tableName;
    private String[] tableColumns;

    public Persistence(SQLiteDatabase db, String tableName, String[] tableColumns){
        this.db = db;
        this.tableName = tableName;
        this.tableColumns = tableColumns;
    }

    /**
     * Insert data
     *
     * @param values Values to insert
     * @return
     */
    @Override
    public boolean insert(ContentValues values) {
        try {
            db.insert(tableName, null, values);
            return true;
        } catch (SQLiteException ex){
            logError(MSG_ERRO_INSERT+ex.getMessage());
            return false;
        }
    }

    /**
     * Insert data
     *
     * @param values values Values to insert
     * @return
     */
    @Override
    public boolean insert(List<ContentValues> values) {
        try {
            for (ContentValues value : values) {
                db.insert(tableName, null, value);
            }
            return true;
        } catch (SQLiteException ex){
            logError(MSG_ERRO_INSERT+ex.getMessage());
            return false;
        }
    }

    /**
     * Update data
     *
     * @param values Update values
     * @param whereClause Where clause
     * @param whereArgs Where clause values
     * @return
     */
    @Override
    public boolean update(ContentValues values, String whereClause, String[] whereArgs) {
        try {
            db.update(tableName, values, whereClause, whereArgs);
            return true;
        } catch (SQLiteException ex){
            logError(MSG_ERRO_UPDATE+ex.getMessage());
            return false;
        }
    }

    /**
     * Delete with where clause
     *
     * @param whereClause Where clause
     * @param whereArgs Where clause values
     * @return
     */
    @Override
    public boolean delete(String whereClause, String[] whereArgs) {
        try {
            db.delete(tableName, whereClause, whereArgs);
            return true;
        } catch (SQLiteException ex){
            logError(MSG_ERRO_DELETE+ex.getMessage());
            return false;
        }
    }

    /**
     * Delete all records. Always close db.
     * @return
     */
    public boolean delete(){
        try {
            db.execSQL("delete from " + tableName);
            return true;
        } catch (SQLiteException ex){
            logError(MSG_ERRO_DELETE+ex.getMessage());
            return false;
        }
    }

    /**
     * Find all
     *
     * @return
     */
    @Override
    public Cursor find() {
        try {
            return db.query(true, tableName, tableColumns, null,null,null,null,null,null);
        } catch (SQLiteException ex){
            logError(MSG_ERRO_SELECT + ex.getMessage());
            return null;
        }
    }

    /**
     * Find with order by
     *
     * @param orderBy
     * @return
     */
    @Override
    public Cursor find(String orderBy) {
        try {
            return db.query(true, tableName, tableColumns, null,null,null,null,orderBy,null);
        } catch (SQLiteException ex){
            logError(MSG_ERRO_SELECT + ex.getMessage());
            return null;
        }
    }

    /**
     * Find with PK
     *
     * @param id Record id
     * @return
     */
    @Override
    public Cursor find(Integer id) {
        try {
            return db.query(true, tableName, tableColumns, "_id=?",new String[]{String.valueOf(id)},null,null,null,null);
        } catch (SQLiteException ex){
            logError(MSG_ERRO_SELECT + ex.getMessage());
            return null;
        }
    }

    /**
     * Find with dynamic where clause
     *
     * @param whereClause
     * @param whereArgs
     * @return
     */
    @Override
    public Cursor find(String whereClause, String[] whereArgs) {
        try {
            return db.query(true, tableName, tableColumns, whereClause, whereArgs, null,null,null,null);
        } catch (SQLiteException ex){
            logError(MSG_ERRO_SELECT + ex.getMessage());
            return null;
        }
    }

    /**
     * Find with dynamic where clause and order by
     *
     * @param whereClause
     * @param whereArgs
     * @param orderBy
     * @return
     */
    @Override
    public Cursor find(String whereClause, String[] whereArgs, String orderBy) {
        try {
            return db.query(true, tableName, tableColumns, whereClause, whereArgs, null,null, orderBy, null);
        } catch (SQLiteException ex){
            logError(MSG_ERRO_SELECT + ex.getMessage());
            return null;
        }
    }


    /**
     * Close database connection
     *
     * @param db SQLiteDatabase
     * @return
     */
    @Override
    @Deprecated
    public boolean closeDb(SQLiteDatabase db){
        if (db != null){
            db.close();
            return true;
        }
        return false;
    }

    /**
     * Get database
     *
     * @return
     */
    public SQLiteDatabase getDataBase(){
        return db;
    }

    private void logError(String msg){
        Log.e(TAG, msg);
    }
}
