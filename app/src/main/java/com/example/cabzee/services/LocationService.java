//package com.example.cabzee.services;
//
//import android.Manifest;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Build;
//import android.os.IBinder;
//import android.os.Looper;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//
//import com.example.cabzee.R;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.Priority;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class LocationService extends Service {
//    public  static LocationRequest locationRequest;
//    private final int FINE_PERMISSION_CODE=1;
//
//    private final LocationCallback locationCallback=new LocationCallback() {
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationresult) {
//            super.onLocationResult(locationresult);
//            if(locationresult.getLastLocation() != null){
//                double latitude=locationresult.getLastLocation().getLatitude();
//                double longitude=locationresult.getLastLocation().getLongitude();
//
//
//                Toast.makeText(LocationService.this,latitude +" . "+ longitude,Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    private void StartLocationService(){
//        String channelId="Location_notification_channel";
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent resultintent=new Intent();
//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,resultintent,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),channelId);
//        builder.setSmallIcon(R.drawable.logo);
//        builder.setContentTitle("CABZEE is accessing your location");
//        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
//        builder.setContentText("Running");
//        builder.setContentIntent(pendingIntent);
//        builder.setAutoCancel(false);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
//            if(notificationManager!=null &&notificationManager.getNotificationChannel(channelId)==null){
//                NotificationChannel notificationChannel=new NotificationChannel(channelId,"CABZEE",NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.setDescription("This channel is used by location service");
//                notificationManager.createNotificationChannel(notificationChannel);
//
//            }
//        }
//        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 4000)
//                .setWaitForAccurateLocation(false)
//                .setMinUpdateIntervalMillis(2000)
//                .build();
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
//                PackageManager.PERMISSION_GRANTED){
//
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());
//        startForeground(constants.LOCATION_SERVICE_ID,builder.build());
//
//
//    }
//    private void stopLocationServices(){
//        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
//        stopForeground(true);
//        stopSelf();
//
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent,int flags,int startId){
//        if(intent!=null){
//            String action=intent.getAction();
//            if(action!=null){
//                if(action.equals(constants.ACTION_START_LOCATION_SERVICE)){
//                    StartLocationService();
//                } else if (action.equals(constants.ACTION_STOP_LOCATION_SERVICE)) {
//                    stopLocationServices();
//
//                }
//            }
//        }
//        return super.onStartCommand(intent,flags,startId);
//    }
//
//
//}
