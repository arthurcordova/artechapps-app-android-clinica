package br.com.artechapps.app.database;

import android.content.Context;


/**
 * Created by acstapassoli on 013/05/2016.
 */
public class RepositoryMessage extends RepositoryDefault {


    public static final String TABLE_NAME = "tbmessage";
    public static final String  SCRIPT_DATABASE_DELETE = "drop table if exists "+TABLE_NAME;
    public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table "+ TABLE_NAME +"( " +
                    "                       id long primary key, " +
                    "                       title  text,"  +
                    "                       author  text,"  +
                    "                       message  text,"  +
                    "                       type  text,"  +
                    "                       sent_date  timestamp,"  +
                    "                       see_date  timestamp,"  +
                    "                       see  boolean,"  +
                    "                       id_client long); "
    };


    public  static final  String[] COLUMNS = new String[]{
            "id",
            "title",
            "author",
            "message",
            "type",
            "sent_date",
            "see_date",
            "see",
            "id_client"
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
    public RepositoryMessage(Context context) {
        super(context, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
    }
}
