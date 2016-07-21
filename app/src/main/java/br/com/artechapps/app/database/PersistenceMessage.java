package br.com.artechapps.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.artechapps.app.model.Message;

/**
 * Persistence Promotion Data
 * <p/>
 * Created by acstapassoli on 13/05/2016.
 */
public class PersistenceMessage extends RepositoryMessage {

    private final Persistence persistence;
    private final String TAG = PersistenceMessage.class.getSimpleName();

    private final Context mContext;

    public PersistenceMessage(Context context) {
        super(context);
        mContext = context;
        persistence = new Persistence(db, TABLE_NAME, COLUMNS);
    }

    public void save (JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            try{
                JSONObject json = jsonArray.getJSONObject(i);
                Message msg = new Message();
                msg.setId(json.getLong("id"));
                msg.setTitle(json.getString("titulo"));
                msg.setAuthor(json.getString("autor"));
                msg.setMessage(json.getString("mensagem"));
                msg.setType(json.getString("tipoMensagem"));
                try {
                    msg.setSentDate(json.getString("dataEnvio"));
                    msg.setSeeDate(json.getString("dataVisualizacao"));
                    msg.setSee(json.getBoolean("visualizada"));
                    msg.setIdClient(json.getLong("idMsgCliente"));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                save( msg );
            } catch (JSONException ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    }

    public void save (Message model){
        persistence.insert(getContentValues(model));

    }

    public boolean remove(long id){
        return persistence.delete(" id = ?", new String[]{String.valueOf(id)});
    }

    public boolean remove(){
        return persistence.delete();
    }

    public ArrayList<Message> getRecords(){
        ArrayList<Message> list = new ArrayList<>();
        Cursor cursor = persistence.find();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Message model = new Message();

                    model.setId(cursor.getLong(cursor.getColumnIndex(RepositoryMessage.COLUMNS[0])));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[1])));
                    model.setAuthor(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[2])));
                    model.setMessage(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[3])));
                    model.setType(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[4])));
                    model.setSentDate(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[5])));
                    model.setSeeDate(cursor.getString(cursor.getColumnIndex(RepositoryMessage.COLUMNS[6])));
                    model.setSee(cursor.getInt(cursor.getColumnIndex(RepositoryMessage.COLUMNS[7]))>0);
                    model.setIdClient(cursor.getLong(cursor.getColumnIndex(RepositoryMessage.COLUMNS[8])));

                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    private ContentValues getContentValues(Message model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RepositoryMessage.COLUMNS[0], model.getId());
        contentValues.put(RepositoryMessage.COLUMNS[1], model.getTitle());
        contentValues.put(RepositoryMessage.COLUMNS[2], model.getAuthor());
        contentValues.put(RepositoryMessage.COLUMNS[3], model.getMessage());
        contentValues.put(RepositoryMessage.COLUMNS[4], model.getType());
        contentValues.put(RepositoryMessage.COLUMNS[5], model.getSentDate());
        contentValues.put(RepositoryMessage.COLUMNS[6], model.getSeeDate());
        contentValues.put(RepositoryMessage.COLUMNS[7], model.isSee());
        contentValues.put(RepositoryMessage.COLUMNS[8], model.getIdClient());
        return contentValues;
    }
}
