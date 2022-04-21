package com.example.navyseas.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
	private Family selectedFamily;
	private FragmentHomeBinding binding;
	private SwipeRefreshLayout swipeContainer;
	private HomePageAdapter adapter;
	private ViewGroup container;
	private DBHelper db;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		this.container = container;

		db = new DBHelper(container.getContext());
		ArrayList<Family> f = db.getFamilies();
		selectedFamily = f.get(0);

		RecyclerView recyclerView = root.findViewById(R.id.recyclerViewHome);

		adapter = new HomePageAdapter(container.getContext(), selectedFamily);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		// Implementa la pull-to-refresh feature
		swipeContainer = root.findViewById(R.id.swipeContainerHome);
		swipeContainer.setOnRefreshListener(this::fetchTimelineAsync);

		// Refreshing colors
		swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

		TextView amountTextView = root.findViewById(R.id.amount);

		amountTextView.setText(String.format("Totale: %s €", db.getFamilyAmount(selectedFamily)));

		return root;
	}

	public void fetchTimelineAsync() {
		swipeContainer.setRefreshing(false);
		Toast.makeText(container.getContext(), "Prenotazioni aggiornate", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	}
}