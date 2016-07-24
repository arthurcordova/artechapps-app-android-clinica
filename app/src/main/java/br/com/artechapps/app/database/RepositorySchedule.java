package br.com.artechapps.app.database;

import android.content.Context;


/**
 * Created by acstapassoli on 013/05/2016.
 */
public class RepositorySchedule extends RepositoryDefault {


    public static final String TABLE_NAME = "tbschedule";
    public static final String  SCRIPT_DATABASE_DELETE = "drop table if exists "+TABLE_NAME;
    public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table "+ TABLE_NAME +"( " +
                    "                       id integer primary key autoincrement, " +
                    "                       schedule_code long,"  +
                    "                       product_code long,"  +
                    "                       product_desc text,"  +
                    "                       time text,"  +
                    "                       repeat text,"  +
                    "                       status text); "
    };

    public  static final  String[] COLUMNS = new String[]{
            "id",
            "schedule_code",
            "product_code",
            "product_desc",
            "time",
            "repeat",
            "status"
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
    public RepositorySchedule(Context context) {
        super(context, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
    }
}
