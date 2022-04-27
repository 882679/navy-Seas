package com.example.navyseas.ui;

import static com.example.navyseas.MainActivity.navController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.ui.home.HomeFragment;

public class ActivityDetailsFragment extends DialogFragment {
	private final boolean isHome;
	private Activity activity;
	private int activityID;
	private int studentID;

	public ActivityDetailsFragment(Activity activity, boolean isHome) {
		this.activity = activity;
		this.isHome = isHome;
	}

	public ActivityDetailsFragment(int activityID, int studentID, boolean isHome) {
		this.activityID = activityID;
		this.studentID = studentID;
		this.isHome = isHome;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activity_details, container, false);

		TextView activityName = rootView.findViewById(R.id.activityName);
		TextView description = rootView.findViewById(R.id.description);
		ImageView iconActivity = rootView.findViewById(R.id.iconActivity);

		Activity currentActivity;
		if (isHome) currentActivity = HomeFragment.db.getActivityByID(activityID);
		else currentActivity = new Activity();

		activityName.setText(isHome ? currentActivity.getName() : activity.getName());
		description.setText(String.format("Descrizione: %s", "\n\ndescrizione..........\n\ndescrizione..........\n\ndescrizione..........\n\ndescrizione.........."));
		iconActivity.setImageResource(MainActivity.getActivityIcon(isHome ? currentActivity : activity));

		Button button = rootView.findViewById(R.id.btnDeleteReservation);
		button.setOnClickListener(v -> {
			HomeFragment.db.unsubscribe(
					new Reservation(
							isHome ? studentID : MainActivity.selectedStudent.getId(),
							isHome ? activityID : activity.getId()
					)
			);
			navController.navigate(isHome ? R.id.nav_home : R.id.nav_profile);
			dismiss();
		});

		return rootView;
	}
}
