package com.example.cabzee.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final int FINE_PERMISSION_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    View view;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Marker myMarker;


    Location currentlocation;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantresults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantresults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantresults.length > 0 && grantresults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        } else {
            Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }


    }


    private void getcurrentlocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null) {
                        currentlocation = locationResult.getLastLocation();
                        updateMapWithLocation(currentlocation);
                    }
                }
            };

            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,4000)
                    .setWaitForAccurateLocation(true)
                    .setMinUpdateIntervalMillis(1000)
                    .build();

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
        else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }



    private void updateMapWithLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

        if (myMarker != null) {
            myMarker.remove();
        }
        myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("User Here")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_person_pin_circle_24)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        rotateMarker(myMarker, -360, mMap);
    }



    @Override
    public void onMapReady(GoogleMap googleMap){
            mMap=googleMap;
            if(currentlocation!=null) {
                double latitude = currentlocation.getLatitude();
                double longitude = currentlocation.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("User Here")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_person_pin_circle_24)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                rotateMarker(myMarker, -360, mMap);

                try {
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }


    }
    private void rotateMarker(final Marker mcurrent, float toRotation,GoogleMap mMap) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = mcurrent.getRotation();
        final long duration = 1500;

        final android.view.animation.Interpolator interpolator = new android.view.animation.LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                float rot = t * toRotation + (1 - t) * startRotation;

                mcurrent.setRotation(-rot > 180 ? rot / 2 : rot);
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

}
