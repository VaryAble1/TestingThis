package orbis.academy.adaptivescenery;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptor;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//
//import orbis.academy.R;
//
///**
// * Created by VeryAble on 3/12/17.
// */
//
//public class InfoFragment extends Fragment {
//        public InfoFragment() { }
//
//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            return inflater.inflate(R.layout.fragment_info, container, false);
//        }
//
//    SupportMapFragment mapFragment;
//
////Create a marker to place on the map, using the default green color.
////Make a LatLng object that specifies the latitude and longitude of the marker on the map.
////Creates the marker for the image on the fragment.
//    private void loadMap(GoogleMap googleMap) {
//        if (googleMap != null) {
//            // 1
//            BitmapDescriptor defaultMarker =
//                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
//            // 2
//            LatLng sceneryPosition = new LatLng(44.22438242, 6.944561);
//            // 3
////            mapFragment.getMap().addMarker(new MarkerOptions()
////                    .position(sceneryPosition)
////                    .icon(defaultMarker));
//        }
//    }
//
//
////Find the map fragment using its id.
////Attach an OnMapReadyCallback to be informed when the map has loaded.
////Once the map is ready to go, call the loadMap method to set it up as you desire.
//    private void setUpMapIfNeeded() {
//        if (mapFragment == null) {
//            // 1
//            mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
//            if (mapFragment != null) {
//                // 2
//                mapFragment.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap map) {
//                        // 3
//                        loadMap(map);
//                    }
//                });
//            }
//        }
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setUpMapIfNeeded();
//    }
////Thanks for looking
//}
