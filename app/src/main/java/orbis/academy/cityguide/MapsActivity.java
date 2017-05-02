package orbis.academy.cityguide;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import orbis.academy.R;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, LocationListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int PLACE_PICKER_REQUEST = 3;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
// Declare a LocationRequest member variable and a location updated state variable.
    private LocationRequest mLocationRequest;
    private boolean mLocationUpdateState;
// REQUEST_CHECK_SETTINGS is used as the request code passed to onActivityResult.
    private static final int REQUEST_CHECK_SETTINGS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// Instantiates the mGoogleApiClient field if it’s null.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        createLocationRequest();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadPlacePicker();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
// Initiates a background connection of the client to Google Play services.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
// Closes the connection to Google Play services if the client is not null and is connected.
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

// Add a marker on Nevada side of Lake Tahoe and move the camera
        LatLng tahoe = new LatLng(39, -120);
        mMap.addMarker(new MarkerOptions().position(tahoe).title("Lake Tahoe is the 2nd deepest lake in the U.S."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tahoe, 11));

/* Enable the zoom controls on the map and declare MapsActivity as the callback triggered
** when the user clicks a marker on this map.*/
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
            setUpMap();

// Start location updates if user’s location settings are turned on.
        if (mLocationUpdateState) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
// update mLastLocation with the new location and update the map with the new location coordinates.
        mLastLocation = location;
        if (null != mLastLocation) {
            placeMarkerOnMap(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

/* setMyLocationEnabled enables the my-location layer which draws a light blue dot on the
** user’s location. It also adds a button to the map that, when tapped, centers the map on
** the user’s location. */
        mMap.setMyLocationEnabled(true);
//More detailed map
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//getLocationAvailability determines the availability of location data on the device.
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
//getLastLocation gives you the most recent location currently available
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

/* If able to retrieve the most recent location, then move the camera
** to the user’s current location. */
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
//add pin at user's location
                placeMarkerOnMap(currentLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }

    protected  void placeMarkerOnMap(LatLng location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location);

        /**Change ic_user_location for recently took photo location*/
// The marker on location should now use the ic_user_location icon in the project:
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getResources(), R.mipmap.ic_user_location)));

//added a call to getAddress() and added this address as the marker title
        String titleStr = getAddress(location);  // add these two lines
        markerOptions.title(titleStr);
//Add marker to map
        mMap.addMarker(markerOptions);
    }

    private String getAddress( LatLng latLng ) {
// Creates a Geocoder object to turn a latitude and longitude coordinate into an address and vice versa.
        Geocoder geocoder = new Geocoder( this );
        String addressText = "";
        List<Address> addresses = null;
        Address address = null;
        try {
// Asks the geocoder to get the address from the location passed to the method.
            addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
// If the response contains any address, then append it to a string and return.
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressText += (i == 0)?address.getAddressLine(i):("\n" + address.getAddressLine(i));
                }
            }
        } catch (IOException ignored) {
        }
        return addressText;
    }

    protected void startLocationUpdates() {
// In startLocationUpdates(), if the ACCESS_FINE_LOCATION permission has not been granted, request it now and return.
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
// If there is permission, request for location updates.
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                this);
    }

/* Create an instance of LocationRequest, add it to an instance of LocationSettingsRequest.
** Builder and retrieve and handle any changes to be made based on the current state
** of the user’s location settings. */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
// setInterval() specifies the rate at which your app will like to receive updates.
        mLocationRequest.setInterval(10000);
/* setFastestInterval() specifies the fastest rate at which the app can handle updates.
** Setting the fastestInterval rate places a limit on how fast updates will be sent to your app. */
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
// A SUCCESS status means all is well and you can go ahead and initiate a location request.

                    case LocationSettingsStatusCodes.SUCCESS:
                        mLocationUpdateState = true;
                        startLocationUpdates();
                        break;
/* A RESOLUTION_REQUIRED status means the location settings have some issues which can be fixed.
** This could be as a result of the user’s location settings turned off. */
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
/* A SETTINGS_CHANGE_UNAVAILABLE status means the location settings have some issues that you
** can’t fix. This could be as a result of the user choosing NEVER on the dialog above. */
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

/* Override FragmentActivity’s onActivityResult() method and start the update request if it has a
** RESULT_OK result for a REQUEST_CHECK_SETTINGS request. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                mLocationUpdateState = true;
                startLocationUpdates();
            }
        }

///* retrieve details about the selected place if it has a RESULT_OK result for a PLACE_PICKER_REQUEST
//** request, and then place a marker on that position on the map. */
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(this, data);
//                String addressText = place.getName().toString();
//                addressText += "\n" + place.getAddress().toString();
//
//                placeMarkerOnMap(place.getLatLng());
//            }
//        }
    }

// Override onPause() to stop location update request
    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

// Override onResume() to restart the location update request.
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mLocationUpdateState) {
            startLocationUpdates();
        }
    }

//// Creates a new builder for an intent to start the Place Picker UI, and then the PlacePicker intent.
//    private void loadPlacePicker() {
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//        try {
//            startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
//        } catch(GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//    }
//Thanks for looking
}
