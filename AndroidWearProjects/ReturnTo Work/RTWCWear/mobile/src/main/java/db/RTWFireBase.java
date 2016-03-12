package db;

import android.content.Context;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by ${MUTHU} on 5/18/2015.
 */
public class RTWFireBase {
    Firebase myFirebaseRef, Countref;
    Map<String, Object> sync_values;

    public void SyncCount(Context context, String child_name, Map<String, Object> data_values) {
        Firebase.setAndroidContext(context);
        sync_values = data_values;
        myFirebaseRef = new Firebase("https://insworkactivate.firebaseio.com/");
        Countref = myFirebaseRef.child(child_name);
        android.util.Log.d("Countref",Countref.toString()+"");
        android.util.Log.d("TEST", "TEST");
        Countref.updateChildren(sync_values, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                android.util.Log.d("function", "firebase");
                if (firebaseError != null) {
                    android.util.Log.d("Data could not be saved. ", firebaseError.getMessage());
                } else {
                    android.util.Log.d("Data saved successfully", "Data saved successfully.");
                }
            }
        });
    }
}
