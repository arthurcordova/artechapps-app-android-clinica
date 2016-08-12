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

/**
 * Persistence Promotion Data
 * <p/>
 * Created by acstapassoli on 13/05/2016.
 */
public class PersistenceProduct extends RepositoryProduct {

    private final Persistence persistence;
    private final String TAG = PersistenceProduct.class.getSimpleName();

    public PersistenceProduct(Context context) {
        super(context);
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }


    public void save(JSONArray jsonArray) {
        Product model;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                model = new Product();
                model.setId(json.getLong(Product.JSON_CODE));
                model.setDescription(json.getString(Product.JSON_DESC));
                model.setValue(json.getDouble(Product.JSON_VALUE));
                model.setType(json.getString(Product.JSON_TYPE));
//                somente teste
//                if (111d == model.getId()){
//                    model.setType("M");
//                }
                save(model);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void save(Product model) {
        persistence.insert(getContentValues(model));
    }

    public ArrayList<Product> getProduct() {
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = persistence.find(RepositoryProduct.COLUMNS[1]);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Product model = new Product();
                    model.setId(cursor.getLong(cursor.getColumnIndex(RepositoryProduct.COLUMNS[0])));
                    model.setDescription(cursor.getString(cursor.getColumnIndex(RepositoryProduct.COLUMNS[1])));
                    model.setValue(cursor.getDouble(cursor.getColumnIndex(RepositoryProduct.COLUMNS[2])));
                    model.setType(cursor.getString(cursor.getColumnIndex(RepositoryProduct.COLUMNS[3])));
                    model.setImage(cursor.getBlob(cursor.getColumnIndex(RepositoryProduct.COLUMNS[4])));

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public Product getProduct(long id) {
        Product model = new Product();
        Cursor cursor = persistence.find(" id = ?",new String[]{String.valueOf(id)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    model.setId(cursor.getLong(cursor.getColumnIndex(RepositoryProduct.COLUMNS[0])));
                    model.setDescription(cursor.getString(cursor.getColumnIndex(RepositoryProduct.COLUMNS[1])));
                    model.setValue(cursor.getDouble(cursor.getColumnIndex(RepositoryProduct.COLUMNS[2])));
                    model.setImage(cursor.getBlob(cursor.getColumnIndex(RepositoryProduct.COLUMNS[3])));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return model;
    }


    private ContentValues getContentValues(Product model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", model.getId());
        contentValues.put("description", model.getDescription());
        contentValues.put("value", model.getValue());
        contentValues.put("type", model.getType());
        contentValues.put("image", model.getImage());
        return contentValues;
    }

}
