package com.example.navyseas.ui.reservation;

import static com.example.navyseas.MainActivity.navController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.ui.home.HomeFragment;


public class DetailsFragment extends DialogFragment {

	private final Activity activity;
	private final Student student;

	public DetailsFragment(Activity activity, Student student) {
		this.activity = activity;
		this.student = student;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activity_details_for_booking, container,
				false);

		TextView activityName = rootView.findViewById(R.id.activityName);
		TextView description = rootView.findViewById(R.id.description);
		ImageView iconActivity = rootView.findViewById(R.id.iconActivity);

		activityName.setText(activity.getName());
		description.setText(String.format("Descrizione: %s", "\n\ndescrizione..........\n\ndescrizione..........\n\ndescrizione..........\n\ndescrizione.........."));
		iconActivity.setImageResource(MainActivity.getActivityIcon(activity));

		Button button = rootView.findViewById(R.id.btnAddReservation);
		button.setOnClickListener(v -> {
			if (HomeFragment.db.checkActivity(student, activity)) {
				HomeFragment.db.subscribe(new Reservation(
						student.getId(),
						activity.getId()
				));
				navController.navigate(R.id.nav_profile);

				dismiss();
			} else Toast.makeText(
					getContext(),
					"Errore! " + student.getName() + " quel giorno è già impegnato.",
					Toast.LENGTH_LONG
			).show();
		});

		return rootView;
	}

}
