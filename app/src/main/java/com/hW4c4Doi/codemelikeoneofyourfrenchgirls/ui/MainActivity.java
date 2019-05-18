package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.google.android.material.navigation.NavigationView;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.MyViewModelFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private ImageView toolbarImage;
    private FirebaseHelperClass firebaseHelperClass;
    private FirebaseViewModel viewModel;
    private static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_PERMISSION_RESULT = 1234;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient client;
    private double userLat;
    private double userLng;
    private static final int ACTIVITY_NUM = 0;
    private static final int REQUEST_CHECK_SETTINGS = 0;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbarImage = findViewById(R.id.toolbarImage);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    userLat = location.getLatitude();
                    userLng = location.getLongitude();
                }
            }

        };

        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        getLocationPermission();
        createLocationRequest();
        getLastKnownLocation();


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();
        firebaseHelperClass = new FirebaseHelperClass();

        viewModel = ViewModelProviders.of(MainActivity.this, new MyViewModelFactory(getApplication(), EventDatabase.getInstance(getApplicationContext())))
                .get(FirebaseViewModel.class);


        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentGroups);
        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentMyProfile);
        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentSettings);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.fragmentInsideEvent:
                    toolbarImage.setVisibility(View.INVISIBLE);
                    break;
                case R.id.fragmentCreateGroup:
                    toolbarImage.setVisibility(View.INVISIBLE);
                    break;
                case R.id.fragmentInsideGroup:
                    toolbarImage.setVisibility(View.INVISIBLE);
                    break;
                case R.id.fragmentEvents:
                    toolbarImage.setVisibility(View.VISIBLE);
                    break;
                case R.id.fragmentMyProfile:
                    toolbarImage.setVisibility(View.VISIBLE);
                    break;
                case R.id.fragmentSettings:
                    toolbarImage.setVisibility(View.VISIBLE);
                    break;
                case R.id.fragmentCreateEvent:
                    toolbarImage.setVisibility(View.INVISIBLE);
                    break;


            }
        });


        setupWithNavController(toolbar, navController, appBarConfiguration);

        appBarConfiguration.getDrawerLayout().addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
        });



        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_events:
                    if (navController.getCurrentDestination().getId() == R.id.fragmentEvents) {
                        Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());

                        drawerLayout.closeDrawers();
                        break;
                    } else {
                        navController.navigate(R.id.fragmentEvents);
                        drawerLayout.closeDrawers();
                        break;
                    }
                case R.id.menu_groups:
                    Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());
                    navController.navigate(R.id.fragmentGroups);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.menu_profile:
                    Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());

                    navController.navigate(R.id.fragmentMyProfile);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.menu_settings:
                    Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());


                    navController.navigate(R.id.fragmentSettings);
                    drawerLayout.closeDrawers();
                    break;
            }
            drawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        });


    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task != null) {
                    if (task.getResult() == null) {
                        getLastKnownLocation();
                        Log.d("marko", "onComplete: location got");
                    } else {
                        userLat = task.getResult().getLatitude();
                        userLng = task.getResult().getLongitude();
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("marko", "onFailure: " + e.getMessage());
            }
        });
    }

    private void getLocationPermission() {
        String[] permissions = {FINE_LOCATION,
                COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                //getLastKnownLocation();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION}, LOCATION_REQUEST_PERMISSION_RESULT);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION}, LOCATION_REQUEST_PERMISSION_RESULT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_REQUEST_PERMISSION_RESULT: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLastKnownLocation();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000000);
        mLocationRequest.setFastestInterval(500000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                mLocationPermissionGranted = true;
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationPermissionGranted) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

}
