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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
	public static DBHelper db;
	public Family selectedFamily;
	private FragmentHomeBinding binding;
	private SwipeRefreshLayout swipeContainer;
	private ViewGroup container;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		this.container = container;

		db = new DBHelper(container.getContext());

		// TODO: Il login va fatto una volta sola e da quel punto in poi l'oggetto Family deve
		//  rimanere lo stesso per ogni schermata senza dover ri-effetture il login
		/*Family selectedFamily = MainActivity.selectedFamily;*/

		selectedFamily = db.login("Navy@stud.unive.it", "Navy");

		RecyclerView recyclerView = root.findViewById(R.id.recyclerViewHome);

		HomeAdapter adapter = new HomeAdapter(container.getContext(), selectedFamily, getParentFragmentManager());

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

		amountTextView.setText(String.format("Totale: %s €", HomeFragment.db.getAmount(selectedFamily)));

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