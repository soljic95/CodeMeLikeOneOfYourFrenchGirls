package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.google.android.material.navigation.NavigationView;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private ImageView toolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbarImage = findViewById(R.id.toolbarImage);
        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentGroups);
        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentMyProfile);
        appBarConfiguration.getTopLevelDestinations().add(R.id.fragmentSettings);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
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
            }
        });


        setupWithNavController(toolbar, navController, appBarConfiguration);

        appBarConfiguration.getDrawerLayout().addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
        });


        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            }
        });


    }


}
