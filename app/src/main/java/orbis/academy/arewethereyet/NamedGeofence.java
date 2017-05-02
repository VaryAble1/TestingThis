package orbis.academy.arewethereyet;
//
//import android.support.annotation.NonNull;
//
//import com.google.android.gms.location.Geofence;
//
//import java.util.UUID;
//
///** A model class that will be used to serialize the geofences. */
//
//class NamedGeofence implements Comparable {
//
//  // region Properties
//
//  public String id;
//  public String name;
//  double latitude;
//  double longitude;
//  float radius;
//
//  // end region
//
//  // region Public
//
//    // Uses the Builder pattern to instantiate a new geofence object.
//    // First create a unique id for the geofence and then build a geofence based on
//    // the values in NamedGeofence. The geofence type is specified as GEOFENCE_TRANSITION_ENTER
//
//    Geofence geofence() {
//        id = UUID.randomUUID().toString();
//        return new Geofence.Builder()
//                .setRequestId(id)
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//                .setCircularRegion(latitude, longitude, radius)
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                .build();
//    }
//
//  // endregion
//
//  // region Comparable
//
//  @Override
//  public int compareTo(@NonNull Object another) {
//    NamedGeofence other = (NamedGeofence) another;
//    return name.compareTo(other.name);
//  }
//
//  // endregion
//}
