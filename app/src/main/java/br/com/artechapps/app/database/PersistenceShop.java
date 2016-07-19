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

    public PersistenceShop(Context context) {
        super(context);
        mContext = context;
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }

    public void save (Shop model){
        persistence.insert(getContentValues(model));

    }

    public boolean remove(long id){
        return persistence.delete(" id = ?", new String[]{String.valueOf(id)});
    }

    public boolean remove(){
        return persistence.delete();
    }

    public ArrayList<Shop> getRecords(){
        ArrayList<Shop> list = new ArrayList<>();
        Cursor cursor = persistence.find();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Shop model = new Shop();
                    model.setId(cursor.getLong(cursor.getColumnIndex(RepositoryProduct.COLUMNS[0])));

                    PersistenceProduct perProd = null;
                    try{
                        perProd = new PersistenceProduct(mContext);
                        Product product = perProd.getProduct(cursor.getLong(cursor.getColumnIndex(RepositoryShop.COLUMNS[1])));

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


//    public void save(JSONArray jsonArray) {
//        Product model;
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                JSONObject json = jsonArray.getJSONObject(i);
//                model = new Product();
//                model.setId(json.getLong(Product.JSON_CODE));
//                model.setDescription(json.getString(Product.JSON_DESC));
//                model.setValue(json.getDouble(Product.JSON_VALUE));
//                save(model);
//            } catch (JSONException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//    }
//
//    public void save(Product model) {
//        persistence.insert(getContentValues(model));
//    }
//
//    public ArrayList<Product> getProduct() {
//        ArrayList<Product> list = new ArrayList<>();
//        Cursor cursor = persistence.find(RepositoryProduct.COLUMNS[1]);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    Product model = new Product();
//                    model.setId(cursor.getLong(cursor.getColumnIndex(RepositoryProduct.COLUMNS[0])));
//                    model.setDescription(cursor.getString(cursor.getColumnIndex(RepositoryProduct.COLUMNS[1])));
//                    model.setValue(cursor.getDouble(cursor.getColumnIndex(RepositoryProduct.COLUMNS[2])));
//                    model.setImage(cursor.getBlob(cursor.getColumnIndex(RepositoryProduct.COLUMNS[3])));
//
//                    list.add(model);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        return list;
//    }
//
//
    private ContentValues getContentValues(Shop model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_id", model.getProduct().getId());
        return contentValues;
    }


//    public boolean existRigister(String whereClause, String[] whereArgs) {
//        boolean exist = true;
//        Cursor cursor = null;
//        try {
//            cursor = persistence.find(whereClause, whereArgs);
//            if (cursor == null || !cursor.moveToFirst()) {
//                exist = false;
//            }
//        } finally {
//            if (cursor != null ) {
//                cursor.close();
//            }
//        }
//        return exist;
//    }
//
//    public void insert(List<NotificationsModel> notificationsModels) {
//        List<ContentValues> contentValues = new ArrayList<>();
//        for(NotificationsModel notificationsModel : notificationsModels) {
//            if (!existRigister("_id=?", new String[]{String.valueOf(notificationsModel.getId())})) {
//                contentValues.add(getContentValues(notificationsModel));
//            }
//        }
//        if (contentValues.size() > 0) {
//            persistence.insert(contentValues);
//        }
//    }
//
//    public void insert(NotificationsModel notificationsModel) {
//        if (!existRigister("_id=?",new String[]{String.valueOf(notificationsModel.getId())})) {
//            persistence.insert(getContentValues(notificationsModel));
//        }
//    }
//
//    public void update(List<NotificationsModel> notificationsModels) {
//        for (NotificationsModel notificationsModel : notificationsModels) {
//            getDataBase().update(TABLE_NAME, getContentValues(notificationsModel),"id=?", new String[]{String.valueOf(notificationsModel.getId())});
//        }
//    }
//
//
//    private List<ContentValues> getContentValues(List<NotificationsModel> notificationsModels) {
//        List<ContentValues> contentValues = new ArrayList<>();
//        for (NotificationsModel notificationsModel : notificationsModels) {
//            contentValues.add(getContentValues(notificationsModel));
//        }
//        return  contentValues;
//    }
//
//    private ContentValues getContentValues(NotificationsModel notificationsModel) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", notificationsModel.getId());
//        contentValues.put("noti_type", notificationsModel.getType());
//        contentValues.put("noti_doctor", notificationsModel.getDoctorResponsible());
//        contentValues.put("noti_macro", notificationsModel.getMacros());
//        contentValues.put("noti_date_update", notificationsModel.getDateUpdate());
//        contentValues.put("noti_date_visualization", notificationsModel.getDateVisualization());
//        contentValues.put("noti_date_record", notificationsModel.getRecordDate());
//        contentValues.put("noti_pending", notificationsModel.getPending());
//        return  contentValues;
//    }
//
//    public List<NotificationsModel> getRegisters(String whereClause, String[] whereArgs, String orderBy) {
//        List<NotificationsModel> notificationsModels = new ArrayList<>();
//        Cursor cursor = null;
//        cursor = persistence.find(whereClause, whereArgs, orderBy);
//
//        if (cursor != null){
//            if (cursor.moveToFirst()) {
//                do {
//                    NotificationsModel notificationsModel =
//                            new NotificationsModel(cursor.getInt(cursor.getColumnIndex("_id")),
//                                    cursor.getString(cursor.getColumnIndex("noti_type")),
//                                    cursor.getString(cursor.getColumnIndex("noti_doctor")),
//                                    cursor.getString(cursor.getColumnIndex("noti_macro")),
//                                    cursor.getString(cursor.getColumnIndex("noti_date_update")),
//                                    cursor.getString(cursor.getColumnIndex("noti_date_visualization")),
//                                    cursor.getString(cursor.getColumnIndex("noti_date_record")),
//                                    cursor.getString(cursor.getColumnIndex("noti_pending")),
//                                    cursor.getBlob(cursor.getColumnIndex("noti_image_patiente")));
//
//                    notificationsModels.add(notificationsModel);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        return notificationsModels;
//    }

}
