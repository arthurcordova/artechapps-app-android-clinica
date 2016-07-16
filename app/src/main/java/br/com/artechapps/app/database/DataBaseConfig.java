package br.com.artechapps.app.database;

import java.util.ArrayList;

import br.com.artechapps.app.BuildConfig;

/**
 * Created by acstapassoli on 22/06/2016.
 */
public class DataBaseConfig {

    public static final int VERSION = 1;
    public static final String NAME = BuildConfig.APPLICATION_ID.replace(".","_").concat("_db");

    /**
     * CREATE
     * @return
     */
    public static ArrayList loadCreateScripts(){
        ArrayList<String> mCreateScripts = new ArrayList<>();
        mCreateScripts.add(RepositoryProduct.SCRIPT_DATABASE_CREATE[0]);

        return mCreateScripts;
    }

    /**
     * DROP
     * @return
     */
    public static ArrayList loadDropScripts(){
        ArrayList<String> mDropScripts = new ArrayList<>();
        mDropScripts.add(RepositoryProduct.SCRIPT_DATABASE_DELETE);

        return mDropScripts;
    }

}
