package com.example.navyseas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
	public static NavController navController;
	public static Student selectedStudent;
	public static Family selectedFamily;
	public static ArrayList<Student> children;
	private AppBarConfiguration mAppBarConfiguration;
	private DrawerLayout drawer;
	private NavigationView navigationView;

	public static int getActivityIcon(Activity a) {
		switch (a.getName()) {
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

	@SuppressLint("NonConstantResourceId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setSupportActionBar(binding.appBarMain.toolbar);

		drawer = binding.drawerLayout;
		navigationView = binding.navView;

		DBHelper dbHelper = new DBHelper(MainActivity.this);
		selectedFamily = Login.selectedFamily;
		children = dbHelper.getChildren(selectedFamily);

		mAppBarConfiguration = new AppBarConfiguration
				.Builder(R.id.nav_home, R.id.nav_profile, R.id.nav_logout)
				.setOpenableLayout(drawer)
				.build();

		View headerView = navigationView.getHeaderView(0);
		TextView familyName = headerView.findViewById(R.id.familyName);
		TextView familyEmail = headerView.findViewById(R.id.familyEmail);
		familyName.setText(selectedFamily.getName());
		familyEmail.setText(selectedFamily.getEmail());

		navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu menuGroup = navigationView.getMenu().addSubMenu("Figli");
		int i = 1;

		for (Student s : children) {
			menuGroup.add(R.id.nav_profile, i, Menu.NONE, s.getName()).setIcon(R.drawable.ic_student);
			i++;
		}

		SubMenu menuLogout = navigationView.getMenu().addSubMenu("Profilo");
		menuLogout.add(R.id.nav_logout, i, Menu.NONE, "Logout").setIcon(R.drawable.baseline_logout_black_24dp);

		navigationView.setNavigationItemSelectedListener(item -> {
			if (item.toString().equals("Home")) navController.navigate(R.id.nav_home);

			else if (item.toString().equals("Logout")) navController.navigate(R.id.nav_logout);

			else {
				for (int j = 0; j < children.size(); j++) {
					if (children.get(j).getName().contentEquals(item.getTitle()))
						selectedStudent = children.get(j);
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