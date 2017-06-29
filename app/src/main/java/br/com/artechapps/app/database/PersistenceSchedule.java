package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Schedule;

/**
 * Persistence Promotion Data
 * <p/>
 * Created by acstapassoli on 13/05/2016.
 */
public class PersistenceSchedule extends RepositorySchedule {

    private final Persistence persistence;
    private final String TAG = PersistenceSchedule.class.getSimpleName();

    private final Context mContext;

    public PersistenceSchedule(Context context) {
        super(context);
        mContext = context;
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }

    public void save(JSONArray jsonArray) {
        Schedule model;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                model = new Schedule();
                model.setCode(json.getLong(Schedule.JSON_CODE));
                model.setTime(json.getString(Schedule.JSON_TIME));
                model.setDate(json.getString(Schedule.JSON_DATE));
                model.setRepeat(json.getString(Schedule.JSON_REPEAT));
                model.setStatus(json.getString(Schedule.JSON_STATUS));

                Product product = new Product();
                product.setId(json.getLong(Schedule.JSON_PRODUCT_CODE));
                product.setDescription(json.getString(Schedule.JSON_PRODUCT_DESC));

                model.setProduct(product);

                save(model);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }


    public void save(Schedule model) {
        persistence.insert(getContentValues(model));

    }

    public boolean remove(long id) {
        return persistence.delete(" id = ?", new String[]{String.valueOf(id)});
    }

    public boolean remove() {
        return persistence.delete();
    }

    public ArrayList<Schedule> getRecords() {
        ArrayList<Schedule> list = new ArrayList<>();
//        Cursor cursor = persistence.find();
        Cursor cursor = persistence.getDataBase().rawQuery("select * from tbschedule order by date, time desc", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Schedule model = new Schedule();

                    model.setCode(cursor.getLong(cursor.getColumnIndex(COLUMNS[0])));
                    model.getProduct().setId(cursor.getLong(cursor.getColumnIndex(COLUMNS[1])));
                    model.getProduct().setDescription(cursor.getString(cursor.getColumnIndex(COLUMNS[2])));
                    model.setTime(cursor.getString(cursor.getColumnIndex(COLUMNS[3])));
                    model.setRepeat(cursor.getString(cursor.getColumnIndex(COLUMNS[4])));
                    model.setStatus(cursor.getString(cursor.getColumnIndex(COLUMNS[5])));
                    model.setDate(cursor.getString(cursor.getColumnIndex(COLUMNS[6])));

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<Schedule> getRecordsOK() {
        ArrayList<Schedule> list = new ArrayList<>();
        Cursor cursor = persistence.find(" status = ?", new String[]{"AGENDADO"});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Schedule model = new Schedule();

                    model.setCode(cursor.getLong(cursor.getColumnIndex(COLUMNS[0])));
                    model.getProduct().setId(cursor.getLong(cursor.getColumnIndex(COLUMNS[1])));
                    model.getProduct().setDescription(cursor.getString(cursor.getColumnIndex(COLUMNS[2])));
                    model.setTime(cursor.getString(cursor.getColumnIndex(COLUMNS[3])));
                    model.setRepeat(cursor.getString(cursor.getColumnIndex(COLUMNS[4])));
                    model.setStatus(cursor.getString(cursor.getColumnIndex(COLUMNS[5])));
                    model.setDate(cursor.getString(cursor.getColumnIndex(COLUMNS[6])));

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public int count() {
        int count = 0;
        Cursor cursor = persistence.getDataBase().rawQuery("select count(*) c from " + TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(cursor.getColumnIndex("c"));
            }
        }
        return count;
    }

    private ContentValues getContentValues(Schedule model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[0], model.getCode());
        contentValues.put(COLUMNS[1], model.getProduct().getId());
        contentValues.put(COLUMNS[2], model.getProduct().getDescription());
        contentValues.put(COLUMNS[3], model.getTime());
        contentValues.put(COLUMNS[4], model.getRepeat());
        contentValues.put(COLUMNS[5], model.getStatus());
        contentValues.put(COLUMNS[6], model.getDate());

        return contentValues;
    }

    public void updateStatusCancel(String id) {
        String sql = "update " + TABLE_NAME + " set " + COLUMNS[5] + " = \"CANCELADO\" where " + COLUMNS[0] + " = " + id;
        persistence.getDataBase().execSQL(sql);
    }

}
