package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Navigation navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();
        firebaseHelperClass = new FirebaseHelperClass();

        toolbarImage = findViewById(R.id.toolbarImage);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

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
                    Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_profile:
                    Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());

                    navController.navigate(R.id.fragmentMyProfile);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.menu_settings:
                    Log.d("marko", "onNavigationItemSelected: " + menuItem.getItemId());
                    new AlertDialog.Builder(this)
                            .setTitle("Log out")
                            .setMessage("Are you sure you want to log out?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                auth.signOut();
                                startActivity(new Intent(MainActivity.this, RegisterUserActivity.class));
                                finish();
                                drawerLayout.closeDrawers();
                                dialog.dismiss();
                            }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();

                    break;
            }
            return true;
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }


}
