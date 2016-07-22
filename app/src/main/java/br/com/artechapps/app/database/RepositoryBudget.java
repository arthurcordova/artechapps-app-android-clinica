package br.com.artechapps.app.database;

import android.content.Context;


/**
 * Created by acstapassoli on 013/05/2016.
 */
public class RepositoryBudget extends RepositoryDefault {


    public static final String TABLE_NAME = "tbbudget";
    public static final String  SCRIPT_DATABASE_DELETE = "drop table if exists "+TABLE_NAME;
    public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table "+ TABLE_NAME +"( " +
                    "                       id integer primary key, " +
                    "                       status  text,"  +
                    "                       value_budget  numeric(10,5),"  +
                    "                       value_discount  numeric(10,5),"  +
                    "                       value_total  numeric(10,5),"  +
                    "                       date_budget timestamp); "
    };


    public  static final  String[] COLUMNS = new String[]{
            "id",
            "status",
            "value_budget",
            "value_discount",
            "value_total",
            "date_budget"

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
    public RepositoryBudget(Context context) {
        super(context, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
    }
}
