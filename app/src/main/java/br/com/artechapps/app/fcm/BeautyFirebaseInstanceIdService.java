package br.com.artechapps.app.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by acstapassoli on 08/10/17.
 */

public class BeautyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "FCM_TOKEN";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, token);
    }
}
