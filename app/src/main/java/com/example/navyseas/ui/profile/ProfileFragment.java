package com.example.navyseas.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.FragmentProfileBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
	private final Student selectedStudent = MainActivity.selectedStudent;
	private FragmentProfileBinding binding;
	private ProfileAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private View root;
	private RecyclerView recyclerView;
	private ViewGroup container;
	private TextView amountTextView;
	private double amount;
	private DBHelper db;
	private ArrayList<Reservation> studentReservations;
	private ArrayList<Activity> studentActivities;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

		binding = FragmentProfileBinding.inflate(inflater, container, false);
		root = binding.getRoot();
		this.container = container;

		db = new DBHelper(container.getContext());

		studentReservations = db.getStudentReservations(selectedStudent);
		studentActivities = db.getStudentActivities(selectedStudent);

		final TextView textView = binding.textProfile;
		textView.setText(String.format("Attività di %s:", selectedStudent.getName()));

		recyclerView = root.findViewById(R.id.recyclerViewProfile);

		adapter = new ProfileAdapter(container.getContext(), studentActivities);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		enableSwipeToDeleteAndUndo();

		binding.fabAddReservation.setOnClickListener(view -> {
			MainActivity.navController.navigate(R.id.nav_reservation);
			Snackbar.make(view, "Prenotazione nuova attività", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		});

		amountTextView = root.findViewById(R.id.amount);
		amountTextView.setText(String.format("Totale: %s €", db.getStudentAmount(selectedStudent)));

		return root;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	private void enableSwipeToDeleteAndUndo() {
		SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(container.getContext()) {
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
				final int position = viewHolder.getAdapterPosition();
				final Activity activityToRemove = adapter.getData().get(position);

				db.unsubscribe(selectedStudent.getId(), activityToRemove.getId());
				studentActivities = db.getStudentActivities(selectedStudent);

				resizeRecyclerView();
				amountTextView.setText(String.format("Totale: %s €", db.getStudentAmount(selectedStudent)));
				adapter.removeItem(position);

				Snackbar snackbar = Snackbar.make(root, "La prenotazione è stata eliminata.", Snackbar.LENGTH_LONG);
				snackbar.setAction("ANNULLA", view -> {
					db.subscribe(selectedStudent.getId(), activityToRemove.getId());
					studentReservations = db.getStudentReservations(selectedStudent);
					studentActivities = db.getStudentActivities(selectedStudent);

					adapter.restoreItem(activityToRemove, position);

					amountTextView.setText(String.format("Totale: %s €", db.getStudentAmount(selectedStudent)));
					resizeRecyclerView();
				});

				snackbar.setActionTextColor(Color.YELLOW);
				snackbar.show();
			}
		};

		ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
		itemTouchhelper.attachToRecyclerView(recyclerView);
	}

	private void resizeRecyclerView() {
		ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		recyclerView.setLayoutParams(params);
	}
}
