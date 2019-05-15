package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;

public class RegisterUserActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        navController = Navigation.findNavController(RegisterUserActivity.this, R.id.reg_nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .build();


    }
}
