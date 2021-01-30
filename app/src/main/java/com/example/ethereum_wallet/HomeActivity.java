package com.example.ethereum_wallet;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {


    TextView title;
    LinearLayout linlay;
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    AppBarConfiguration appBarConfiguration;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initviews();
        setSupportActionBar(toolbar);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();

//        NavigationUI.setupWithNavController(
//                toolbar, navController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() != R.id.wallet_frag){
                    linlay.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                }else{
                    linlay.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                }

                if(destination.getId() == R.id.transactionlist_frag){
                    title.setText("Transaction List");
                }else if(destination.getId() == R.id.sharePublicKey){
                    title.setText("Share Your Public Key");
                }else if(destination.getId() == R.id.changePIN_frag){
                    title.setText("Change PIN");
                }else{
                    title.setText("Hello");
                }

                Toast.makeText(HomeActivity.this, String.valueOf(destination.getId()), Toast.LENGTH_SHORT).show();

            }

        });

        linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.network);
            }
        });

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController)
//                || super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen()){
            drawerLayout.close();
        }else{
            super.onBackPressed();
        }
    }

    private void initviews() {
        toolbar = findViewById(R.id.my_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        linlay = findViewById(R.id.linlay);
        title = findViewById(R.id.title);
    }


}