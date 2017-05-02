package orbis.academy.arewethereyet;
//
//import android.Manifest;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by VeryAble  on 4/8/17.
// */
//
//class GeofenceController {
//
//    private final String TAG = GeofenceController.class.getName();
//
//    private Context context;
//    private GoogleApiClient googleApiClient;
//    private Gson gson;
//    private SharedPreferences prefs;
//    private GeofenceControllerListener listener;
//
//    // Declares the add geofence and connection failed callbacks.
//    private GoogleApiClient.ConnectionCallbacks connectionAddListener =
//            new GoogleApiClient.ConnectionCallbacks() {
//                @Override
//                public void onConnected(Bundle bundle) {
//// Create an IntentService PendingIntent
//                    Intent intent = new Intent(context, AreWeThereIntentService.class);
//                    PendingIntent pendingIntent =
//                            PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//// Associate the service PendingIntent with the geofence and call addGeofences
//                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    PendingResult<Status> result = LocationServices.GeofencingApi.addGeofences(
//                            googleApiClient, getAddGeofencingRequest(), pendingIntent);
//
//// Implement PendingResult callback
//                    result.setResultCallback(new ResultCallback<Status>() {
//
//                        @Override
//                        public void onResult(Status status) {
//                            if (status.isSuccess()) {
//                                // If successful, save the geofence
//                                saveGeofence();
//                            } else {
//                                // If not successful, log and send an error
//                                Log.e(TAG, "Registering geofence failed: " + status.getStatusMessage() +
//                                        " : " + status.getStatusCode());
//                                sendError();
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void onConnectionSuspended(int i) {
//
//                }
//            };
//
//    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
//            new GoogleApiClient.OnConnectionFailedListener() {
//                @Override
//                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                }
//            };
//
//
//    private List<NamedGeofence> namedGeofences;
//    List<NamedGeofence> getNamedGeofences() {
//        return namedGeofences;
//    }
//
//    private List<NamedGeofence> namedGeofencesToRemove;
//
//    private Geofence geofenceToAdd;
//    private NamedGeofence namedGeofenceToAdd;
//
//// This adds a private static property to hold the singleton reference to the
//// GeofenceController class, as well as a method to create and access the instance.
//
//    private static GeofenceController INSTANCE; //<-~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Memory Leak!!!
//
//    static GeofenceController getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new GeofenceController();
//        }
//        return INSTANCE;
//    }
//
//// This method simply initializes the context and some other properties of the controller.
//
//    void init(Context context) {
//        this.context = context.getApplicationContext();
//
//        gson = new Gson();
//        namedGeofences = new ArrayList<>();
//        namedGeofencesToRemove = new ArrayList<>();
//        prefs = this.context.getSharedPreferences(Constants.SharedPrefs.Geofences, Context.MODE_PRIVATE);
//
//        loadGeofences();
//    }
//
//    interface GeofenceControllerListener {
//        void onGeofencesUpdated(); //call the interface method when adding or removing geofences
//        void onError(); //call if an error occurs.
//    }
//
//    private GeofencingRequest getAddGeofencingRequest() {
//        List<Geofence> geofencesToAdd = new ArrayList<>();
//        geofencesToAdd.add(geofenceToAdd);
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//        builder.addGeofences(geofencesToAdd);
//        return builder.build();
//    }
//
//    private void connectWithCallbacks(GoogleApiClient.ConnectionCallbacks callbacks) {
//        googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(callbacks)
//                .addOnConnectionFailedListener(connectionFailedListener)
//                .build();
//        googleApiClient.connect();
//    }
//
//// Calls the listener to pass along the error
//    private void sendError() {
//        if (listener != null) {
//            listener.onError();
//        }
//    }
//
//// Adds the new geofence to the controller’s list of geofences
//// and calls the listener’s onGeofencesUpdated method.
//    private void saveGeofence() {
//        namedGeofences.add(namedGeofenceToAdd);
//        if (listener != null) {
//            listener.onGeofencesUpdated();
//        }
//
//// Use Gson to convert namedGeofenceToAdd into JSON and store that JSON as a string in the users’ shared preferences.
//        String json = gson.toJson(namedGeofenceToAdd);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(namedGeofenceToAdd.id, json);
//        editor.apply();
//    }
//
//// Simply hold the references to the geofence object and listener you’re going to create.
//    void addGeofence(NamedGeofence namedGeofence, GeofenceControllerListener listener) {
//        this.namedGeofenceToAdd = namedGeofence;
//        this.geofenceToAdd = namedGeofence.geofence();
//        this.listener = listener;
//
//        connectWithCallbacks(connectionAddListener);
//    }
//
///* Create a map for all the geofence keys. Then loop over all the keys and use Gson
//** to convert the saved JSON back into a NamedGeofence. Then sort the geofences by name.*/
//    private void loadGeofences() {
//        // Loop over all geofence keys in prefs and add to namedGeofences
//        Map<String, ?> keys = prefs.getAll();
//        for (Map.Entry<String, ?> entry : keys.entrySet()) {
//            String jsonString = prefs.getString(entry.getKey(), null);
//            NamedGeofence namedGeofence = gson.fromJson(jsonString, NamedGeofence.class);
//            namedGeofences.add(namedGeofence);
//        }
//
//        // Sort namedGeofences by name
//        Collections.sort(namedGeofences);
//    }
//
//    private GoogleApiClient.ConnectionCallbacks connectionRemoveListener =
//            new GoogleApiClient.ConnectionCallbacks() {
//                @Override
//                public void onConnected(Bundle bundle) {
//// Create a list of geofences to remove. Builds a list of geofence id values to remove.
//                    List<String> removeIds = new ArrayList<>();
//                    for (NamedGeofence namedGeofence : namedGeofencesToRemove) {
//                        removeIds.add(namedGeofence.id);
//                    }
//
//                    if (removeIds.size() > 0) {
//// Use GoogleApiClient and the GeofencingApi to remove the geofences. Removes the list of geofences
//                        PendingResult<Status> result = LocationServices.GeofencingApi.removeGeofences(
//                                googleApiClient, removeIds);
//                        result.setResultCallback(new ResultCallback<Status>() {
//
//// Handles success or failure of the removal in a result callback (PendingResult)
//                            @Override
//                            public void onResult(Status status) {
//                                if (status.isSuccess()) {
//                                    removeSavedGeofences();
//                                } else {
//                                    Log.e(TAG, "Removing geofence failed: " + status.getStatusMessage());
//                                    sendError();
//                                }
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onConnectionSuspended(int i) {
//                    Log.e(TAG, "Connecting to GoogleApiClient suspended.");
//                    sendError();
//                }
//            };
//
//    void removeGeofences(List<NamedGeofence> namedGeofencesToRemove,
//                         GeofenceControllerListener listener) {
//        this.namedGeofencesToRemove = namedGeofencesToRemove;
//        this.listener = listener;
//
//        connectWithCallbacks(connectionRemoveListener);
//    }
//
//    void removeAllGeofences(GeofenceControllerListener listener) {
//        namedGeofencesToRemove = new ArrayList<>();
//        for (NamedGeofence namedGeofence : namedGeofences) {
//            namedGeofencesToRemove.add(namedGeofence);
//        }
//        this.listener = listener;
//
//        connectWithCallbacks(connectionRemoveListener);
//    }
//
//    private void removeSavedGeofences() {
//        SharedPreferences.Editor editor = prefs.edit();
//
//        for (NamedGeofence namedGeofence : namedGeofencesToRemove) {
//            int index = namedGeofences.indexOf(namedGeofence);
//            editor.remove(namedGeofence.id);
//            namedGeofences.remove(index);
//            editor.apply();
//        }
//
//        if (listener != null) {
//            listener.onGeofencesUpdated();
//        }
//    }
//
//// Thanks for looking
//}