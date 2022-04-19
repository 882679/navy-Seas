package com.example.navyseas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.database.SQLHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navyseas.databinding.ActivityMainBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static NavController navController;
    public static DataMockup dataMockup = new DataMockup();
    public static Student selectedStudent;

    private static SQLHelper database;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /*try {
            database = new SQLHelper();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ResultSet ris = database.executeQuery("SELECT * FROM family"); //verifica che nel database l'utente sia già registrato
            if (ris.next()) { //il primo valore è antecedente a valori del resultset, perciò se questo valore è l'ultimo il risultato è vuoto
                System.out.println(ris.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }*/


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

    public static int getActivityIcon(Activity a){
        switch (a.getName()){
            case "Scacchi":
                return R.drawable.knight;
            case "Lettura":
                return R.drawable.reading2;
            case "Aiuto compiti":
                return R.drawable.homework;
            case "Disegno":
                return R.drawable.draw2;
            case "Calcio":
                return R.drawable.football2;
            case "Pallavolo":
                return R.drawable.volleyball;
        }
        return R.drawable.knight;
    }

}