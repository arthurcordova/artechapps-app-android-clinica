package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.artechapps.app.model.Budget;

/**
 * Persistence Promotion Data
 * <p/>
 * Created by acstapassoli on 13/05/2016.
 */
public class PersistenceBudget extends RepositoryBudget {

    private final Persistence persistence;
    private final String TAG = PersistenceBudget.class.getSimpleName();

    private final Context mContext;

    public PersistenceBudget(Context context) {
        super(context);
        mContext = context;
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }

    public void save (JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            try{
                JSONObject json = jsonArray.getJSONObject(i);
                Budget msg = new Budget();
                msg.setCode(json.getLong("codOrcamento"));
                msg.setStatus(json.getString("status"));
                msg.setValueBudget(json.getDouble("valorOrcamento"));
                msg.setValueDiscount(json.getDouble("valorDesconto"));
                msg.setValueTotal(json.getDouble("valorTotal"));
                msg.setDate(json.getString("dataOrcamento"));
                save( msg );
            } catch (JSONException ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    }

    public void save (Budget model){
        persistence.insert(getContentValues(model));

    }

    public boolean remove(long id){
        return persistence.delete(" id = ?", new String[]{String.valueOf(id)});
    }

    public boolean remove(){
        return persistence.delete();
    }

    public ArrayList<Budget> getRecords(){
        ArrayList<Budget> list = new ArrayList<>();
//        Cursor cursor = persistence.find();

        Cursor cursor = persistence.getDataBase().rawQuery("select * from " +
                "                                         tbbudget " +
                "                                            order by date_budget desc", new String[]{});


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Budget model = new Budget();
                    model.setCode(cursor.getLong(cursor.getColumnIndex(COLUMNS[0])));
                    model.setStatus(cursor.getString(cursor.getColumnIndex(COLUMNS[1])));
                    model.setValueBudget(cursor.getDouble(cursor.getColumnIndex(COLUMNS[2])));
                    model.setValueDiscount(cursor.getDouble(cursor.getColumnIndex(COLUMNS[3])));
                    model.setValueTotal(cursor.getDouble(cursor.getColumnIndex(COLUMNS[4])));
                    model.setDate(cursor.getString(cursor.getColumnIndex(COLUMNS[5])));
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    private ContentValues getContentValues(Budget model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[0], model.getCode());
        contentValues.put(COLUMNS[1], model.getStatus());
        contentValues.put(COLUMNS[2], model.getValueBudget());
        contentValues.put(COLUMNS[3], model.getValueDiscount());
        contentValues.put(COLUMNS[4], model.getValueTotal());
        contentValues.put(COLUMNS[5], model.getDate());
        return contentValues;
    }

}
