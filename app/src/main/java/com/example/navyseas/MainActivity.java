package com.example.navyseas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;

import com.example.navyseas.database.Model.Student;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navyseas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private DataMockup dataMockup = new DataMockup();
    public static Student selectedStudent;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SubMenu menuGroup = navigationView.getMenu().addSubMenu("Figli");
        int index = 1;
        for (Student stud :
                dataMockup.family.getChildren()) {
            menuGroup.add(R.id.nav_profile, index, Menu.NONE, stud.getName()).setIcon(R.drawable.ic_student);

            index++;
        }
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.toString().equals("Home")) {
                navController.navigate(R.id.nav_home);
            } else {
                for (int i = 0; i < dataMockup.students.size(); i++){
                    if (dataMockup.students.get(i).getName().contentEquals(item.getTitle())){
                        selectedStudent = dataMockup.students.get(i);
                    }
                }
                navController.navigate(R.id.nav_profile);
            }
            drawer.close();
            return true;
        });
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}