package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Shop;

/**
 * Persistence Promotion Data
 * <p/>
 * Created by acstapassoli on 13/05/2016.
 */
public class PersistenceShop extends RepositoryShop {

    private final Persistence persistence;
    private final String TAG = PersistenceShop.class.getSimpleName();
    private final Context mContext;

    private String sql = "select    max(id) code," +
                        "           count(1) amount, "+
                        "           product_id " +
                        "   from    " + RepositoryShop.TABLE_NAME +
                        " group by   product_id";

    private String sqlproducts = "select product_id " +
                        "   from    " + RepositoryShop.TABLE_NAME;

    public PersistenceShop(Context context) {
        super(context);
        mContext = context;
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }

    public void save (Shop model){
        boolean isSave = persistence.insert(getContentValues(model));
        Log.i("SAVE CART", String.valueOf(isSave));

    }

    public boolean remove(long id){
        return persistence.delete(" id = ?", new String[]{String.valueOf(id)});
    }

    public boolean remove(){
        return persistence.delete();
    }

    public int count(){
        int count = 0;
        Cursor cursor = persistence.getDataBase().rawQuery("select count(*) c from "+TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()){
                count = cursor.getInt(cursor.getColumnIndex("c"));
            }
        }
        return count;
    }

    public long getMaxId(long productId){
        long id = 0;
        Cursor cursor = persistence.getDataBase().rawQuery("select max(id) id from "+TABLE_NAME+" where product_id = ?", new String[]{String.valueOf(productId)});
        if (cursor != null) {
            if (cursor.moveToFirst()){
                id = cursor.getLong(cursor.getColumnIndex("id"));
            }
        }
        return id;
    }

    public void delete(String id){
        persistence.delete("id = ?", new String[]{id});
    }

    public void deleteAll(){
        persistence.delete("1 = 1", null);
    }

    public ArrayList<Shop> getRecords(){
        ArrayList<Shop> list = new ArrayList<>();
//        Cursor cursor = persistence.find();
        Cursor cursor = persistence.getDataBase().rawQuery(sql,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Shop model = new Shop();
                    model.setId(cursor.getLong(cursor.getColumnIndex("code")));
                    model.setAmount(cursor.getLong(cursor.getColumnIndex("amount")));

                    PersistenceProduct perProd = null;
                    try{
                        perProd = new PersistenceProduct(mContext);
                        Product product = perProd.getProduct(cursor.getLong(cursor.getColumnIndex("product_id")));

                        model.setProduct(product);
                    } finally {
                        perProd.close();
                    }

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<Shop> getProducts(){
        ArrayList<Shop> list = new ArrayList<>();
//        Cursor cursor = persistence.find();
        Cursor cursor = persistence.getDataBase().rawQuery(sqlproducts,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Shop model = new Shop();
//                    model.setId(cursor.getLong(cursor.getColumnIndex("code")));
//                    model.setAmount(cursor.getLong(cursor.getColumnIndex("amount")));

                    PersistenceProduct perProd = null;
                    try{
                        perProd = new PersistenceProduct(mContext);
                        Product product = perProd.getProduct(cursor.getLong(cursor.getColumnIndex("product_id")));

                        model.setProduct(product);
                    } finally {
                        perProd.close();
                    }

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    private ContentValues getContentValues(Shop model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_id", model.getProduct().getId());
        return contentValues;
    }

}
