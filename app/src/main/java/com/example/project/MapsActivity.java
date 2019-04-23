package com.example.project;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.project.NearByPlaces.Location;
import com.example.project.NearByPlaces.NearbyPlacesResponse;
import com.example.project.NearByPlaces.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double lat = 23.8164269;
    private Double lon = 90.3701279;
    private int radius = 1500;
    private String place_type = "restaurant";
    private String place_type2 = "hospital";
    private List<Result> results;
    private List<Address> addressList;

    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Dhaka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings();
    }

    public void restaurantBTN(View view) {

        mMap.clear();
        retrievePlaces(place_type);


    }

    public void hospitalBTN(View view) {
        retrievePlaces(place_type2);


    }
    public void retrievePlaces(String placeType){

        Nearby_places nearby_places=RetrofitClass.getRetrofitInstance().create(Nearby_places.class);
        String urll = String.format("nearbysearch/json?location=%f,%f&radius=%d&type=%s&key=%s", lat, lon, radius, placeType, getResources().getString(R.string.place_key));

        Call<NearbyPlacesResponse> nearbyPlacesResponseCall=nearby_places.getNearByData(urll);
        nearbyPlacesResponseCall.enqueue(new Callback<NearbyPlacesResponse>() {
            @Override
            public void onResponse(Call<NearbyPlacesResponse> call, Response<NearbyPlacesResponse> response) {

                if(response.code()==200){
                    NearbyPlacesResponse nearbyPlacesResponse=response.body();
                    results=nearbyPlacesResponse.getResults();

                    Location location=new Location();

                    for (int index = 0; index < results.size(); index++) {

                        double lat = results.get(index).getGeometry().getLocation().getLat();
                        double lng = results.get(index).getGeometry().getLocation().getLng();
                        Log.d("places", "onResponse: " + lat + lng);
                        LatLng latLng = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(latLng.latitude) + String.valueOf(latLng.longitude)).snippet(results.get(index).getName()));//.snippet(addressList.get(index).getCountryCode()).snippet(addressList.get(index).getAddressLine(0)));


                    }
                }
            }

            @Override
            public void onFailure(Call<NearbyPlacesResponse> call, Throwable t) {

            }
        });



    }
}
