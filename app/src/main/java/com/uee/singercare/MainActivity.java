package com.uee.singercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import Dilshan.MyHistory;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /* COPY THE WHOLE NAVIGATION BAR CODE BLOCK TO A ACTIVITY WHERE THE NAV IS NEEDED,
        BUT NEED TO DO CHANGES IN "MenuItem menuItem = menu.getItem();" AND SWITCH STATEMENTS, LOVE YOU!
       */
        // NAVIGATION BAR START
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0); // IN HERE I = ITEM NUMBER (HOME=1,HISTORY=2,CONTACT=3,WARRANTY=4)
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_mainActivity:

                        break;
                    case R.id.ic_myHistory:
                        Intent intent = new Intent(MainActivity.this, MyHistory.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                    case R.id.ic_Contact:

                        break;
                    case R.id.ic_Warrenty:

                        break;
                }
                return true;
            }
        });
        //NAVIGATION BAR END


    }
}