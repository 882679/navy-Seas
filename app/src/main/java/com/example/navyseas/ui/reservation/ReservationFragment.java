package com.example.navyseas.ui.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.FragmentReservationBinding;

import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment {
	private final Student selectedStudent = MainActivity.selectedStudent;
	private FragmentReservationBinding binding;
	private DBHelper db;
	private ArrayList<Activity> studentActivities;
	private ArrayList<Activity> activities;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ReservationViewModel reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

		binding = FragmentReservationBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		db = new DBHelper(container.getContext());
		studentActivities = db.getStudentActivities(selectedStudent);
		activities = db.getActivities();

		final TextView textView = binding.textProfile;
		textView.setText(String.format("Nuova prenotazione per %s:", selectedStudent.getName()));

		RecyclerView recyclerView = root.findViewById(R.id.recyclerViewReservation);

		ArrayList<Activity> activitiesNotBooked = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			if (!studentActivities.contains(activities.get(i)))
				activitiesNotBooked.add(activities.get(i));
		}

		ReservationAdapter adapter = new ReservationAdapter(
				container.getContext(),
				activitiesNotBooked,
				selectedStudent,
				studentActivities,
				db
		);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		return root;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
