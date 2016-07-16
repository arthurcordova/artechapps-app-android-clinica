package br.com.artechapps.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by acstapassoli on 30/09/2015.
 */
public class RepositoryDefault extends DataBaseHelper {

    protected SQLiteDatabase db;

    /**
     * Instance of DataBaseHelper
     * * @param context
     * * @param dbVersion Data base version (if not equals updates)
     * * @param scriptSQLCreate SQL create table
     * * @param scriptSQLDelete SQL drop table
     *
     * @param context
     * @param scriptSQLCreate
     * @param scriptSQLDelete
     */
    public RepositoryDefault(Context context, String[] scriptSQLCreate, String scriptSQLDelete) {
        super(context, DataBaseConfig.VERSION, scriptSQLCreate, scriptSQLDelete);
        db = super.getWritableDatabase();
    }
}
