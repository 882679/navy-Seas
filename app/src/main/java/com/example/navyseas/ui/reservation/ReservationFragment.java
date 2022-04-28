package com.example.navyseas.ui.reservation;

import static com.example.navyseas.MainActivity.navController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
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
import com.example.navyseas.databinding.FragmentReservationBinding;
import com.example.navyseas.ui.home.HomeFragment;

import java.util.ArrayList;

public class ReservationFragment extends Fragment {
	public static ReservationAdapter reservationAdapter;
	private final Student selectedStudent = MainActivity.selectedStudent;
	public ArrayList<Activity> activitiesNotBooked = new ArrayList<>();
	private FragmentReservationBinding binding;
	private ViewGroup container;
	private RecyclerView recyclerView;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentReservationBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		DBHelper database = new DBHelper(container.getContext());
		ArrayList<Activity> studentActivities = database.getActivities(selectedStudent);
		ArrayList<Activity> activities = database.getActivities();
		this.container = container;

		for (int i = 0; i < activities.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < studentActivities.size(); j++) {
				if (studentActivities.get(j).getId() == activities.get(i).getId()) flag = false;
			}

			if (flag) activitiesNotBooked.add(activities.get(i));
		}

		final TextView textView = binding.textProfile;
		textView.setText(String.format("Nuova prenotazione per %s:", selectedStudent.getName()));

		recyclerView = root.findViewById(R.id.recyclerViewReservation);

		reservationAdapter = new ReservationAdapter(
				container.getContext(),
				getParentFragmentManager(),
				activitiesNotBooked,
				selectedStudent,
				studentActivities
		);

		recyclerView.setAdapter(reservationAdapter);
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		enableSwipeToDeleteAndUndo();

		return root;
	}

	private void enableSwipeToDeleteAndUndo() {
		SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(container.getContext()) {
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
				final int position = viewHolder.getAdapterPosition();
				final Activity activityToAdd = reservationAdapter.getData().get(position);

				if (HomeFragment.db.checkActivity(selectedStudent, activityToAdd)) {
					HomeFragment.db.subscribe(new Reservation(
							selectedStudent.getId(),
							activityToAdd.getId()
					));

					reservationAdapter.removeItem(position);
					navController.navigate(R.id.nav_reservation);
					navController.getBackQueue().remove(navController.getBackQueue().last());
				} else {
					reservationAdapter.restoreItem();
					Toast.makeText(
							getContext(),
							"Errore! " + selectedStudent.getName() + " quel giorno è già impegnato.",
							Toast.LENGTH_LONG
					).show();
				}
			}
		};

		ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
		itemTouchhelper.attachToRecyclerView(recyclerView);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
