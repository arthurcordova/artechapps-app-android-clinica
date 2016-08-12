package br.com.artechapps.app.database;

import android.content.Context;


/**
 * Created by acstapassoli on 013/05/2016.
 */
public class RepositoryProduct extends RepositoryDefault {


    public static final String TABLE_NAME = "tbproduct";
    public static final String  SCRIPT_DATABASE_DELETE = "drop table if exists "+TABLE_NAME;
    public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table "+ TABLE_NAME +"( " +
                    "                       id integer primary key, " +
                    "                       description  text,"  +
                    "                       value  numeric(10,5),"  +
                    "                       type  text,"  +
                    "                       image blob); "
    };
    public  static final  String[] COLUMNS = new String[]{
            "id",
            "description",
            "value",
            "type",
            "image"

    };

    /**
     * Instance of DataBaseHelper
     * * @param context
     * * @param dbVersion Data base version (if not equals updates)
     * * @param scriptSQLCreate SQL create table
     * * @param scriptSQLDelete SQL drop table
     *
     * @param context
     */
    public RepositoryProduct(Context context) {
        super(context, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
    }
}
