package com.example.navyseas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.Menu;

import com.example.navyseas.database.DatabaseUtility;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navyseas.databinding.ActivityMainBinding;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static NavController navController;

    public static Student selectedStudent;

    public static MongoDatabase db;

    public static MongoCollection<Student> studentCollection;
    public static MongoCollection<Activity> activityCollection;
    public static MongoCollection<Family> familyCollection;

    public static FindIterable<Student> iteratorStudents;
    public static FindIterable<Activity> iteratorActivities;
    public static FindIterable<Family> iteratorFamily;

    public static ArrayList<Activity> activities;
    public static ArrayList<Student> students;
    public static ArrayList<Family> families;
    public static Family currentFamily;

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

        MongoDatabase db2 = DatabaseUtility.createConnection();
        getCollectionsFromDB();



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
                currentFamily.getChildren()) {
            menuGroup.add(R.id.nav_profile, index, Menu.NONE, stud.getName()).setIcon(R.drawable.ic_student);

            index++;
        }
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.toString().equals("Home")) {
                navController.navigate(R.id.nav_home);
            } else {
                for (int i = 0; i < currentFamily.getChildren().size(); i++){
                    if (currentFamily.getChildren().get(i).getName().contentEquals(item.getTitle())){
                        selectedStudent = currentFamily.getChildren().get(i);
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

    public void getCollectionsFromDB(){
        studentCollection = db.getCollection("Student", Student.class);
        activityCollection = db.getCollection("Activity", Activity.class);
        familyCollection = db.getCollection("Family", Family.class);

        System.out.println("families: "+ families.toString());

        iteratorStudents = studentCollection.find();
        iteratorActivities = activityCollection.find();
        iteratorFamily = familyCollection.find();

        activities = new ArrayList<>();
        for (Activity a : iteratorActivities) {
            activities.add(a);
        }

        students = new ArrayList<>();
        for (Student s : iteratorStudents) {
            students.add(s);
        }

        families = new ArrayList<>();
        for (Family f : iteratorFamily) {
            families.add(f);
        }

        currentFamily = families.get(0);

    }

}