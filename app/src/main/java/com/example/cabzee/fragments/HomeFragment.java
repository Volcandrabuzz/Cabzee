package com.example.cabzee.fragments;

import static com.example.cabzee.R.drawable.baseline_person_pin_circle_24;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cabzee.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;




public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final int FINE_PERMISSION_CODE=101;
    FusedLocationProviderClient fusedLocationProviderClient;
    View view;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Marker myMarker;


    Location currentlocation;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(HomeFragment.this);
        getLastLocation();
        return view;

    }


    private void getLastLocation() {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission
                    (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getcurrentlocation();
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantresults){
        super.onRequestPermissionsResult(requestCode,permissions,grantresults);
        if(requestCode==FINE_PERMISSION_CODE){
            if(grantresults.length>0 && grantresults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
        }
        else{
            Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }


    }


    @SuppressLint("MissingPermission")
    private void getcurrentlocation() {
        LocationManager locationManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                    if(location!=null){
                        currentlocation=location;
                    }
                    else{
                        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 4000)
                            .setWaitForAccurateLocation(false)
                            .setMinUpdateIntervalMillis(2000)
                            .build();
                        LocationCallback locationCallback=new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1=locationResult.getLastLocation();
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }

                }
            });
        }
        else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        try{
            mMap=googleMap;
            double latitude=currentlocation.getLatitude();
            double longitude=currentlocation.getLongitude();
            LatLng latLng=new LatLng(latitude,longitude);
            myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("User Here")
                    .icon(bitmapDescriptorFromVector(getContext(),baseline_person_pin_circle_24)));
            MarkerOptions markeroptions = new MarkerOptions().position(latLng).title("Current Location ");
            mMap.addMarker(markeroptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }


        try{
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.style));
        }
        catch (Resources.NotFoundException e){
            e.printStackTrace();
        }


    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResID) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResID);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);




    }
}
