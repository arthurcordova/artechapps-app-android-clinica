package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskMessagesDelete extends AsyncTaskHttp {

    private JSONArray mJson;
    private PersistenceMessage mPersistence;
    private RecyclerView mRecyclerView;
    private MainMenuActivity mActivity;


    public AsyncTaskMessagesDelete(String msg, Context context, boolean showDialog) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            mJson = connectServerA(EndPoints.MESSAGES_DELETE.concat(code), null, true);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//
//        if (mJson != null && mJson.length() > 0) {
//
//            try {
//                mPersistence = new PersistenceMessage(mContext);
//                mPersistence.save(mJson);
//            } finally {
//                mPersistence.close();
//            }
//        }
//    }
}
