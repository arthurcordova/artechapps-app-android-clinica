package br.com.artechapps.app.database;

import android.content.Context;


/**
 * Created by acstapassoli on 013/05/2016.
 */
public class RepositoryShop extends RepositoryDefault {


    public static final String TABLE_NAME = "tbshop";
    public static final String  SCRIPT_DATABASE_DELETE = "drop table if exists "+TABLE_NAME;
    public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table "+ TABLE_NAME +"( " +
                    "                       id integer primary key autoincrement, " +
                    "                       product_id  long);"

    };
    public  static final  String[] COLUMNS = new String[]{
            "id",
            "product_id"
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
    public RepositoryShop(Context context) {
        super(context, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
    }
}
