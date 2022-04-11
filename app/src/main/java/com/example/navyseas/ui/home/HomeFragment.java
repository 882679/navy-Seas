package com.example.navyseas.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.navyseas.DataMockup;
import com.example.navyseas.R;
import com.example.navyseas.databinding.FragmentHomeBinding;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SwipeRefreshLayout swipeContainer;

    public static DataMockup dm;

    private HomePageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dm = new DataMockup();


        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewHome);

        adapter = new HomePageAdapter(container.getContext(), dm.activityList);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // implementa la pull-to-refresh feature
        swipeContainer = root.findViewById(R.id.swipeContainerHome);
        swipeContainer.setOnRefreshListener(() -> fetchTimelineAsync());

        // Refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        return root;
    }

    public void fetchTimelineAsync() {
        adapter.clear();
        dm = new DataMockup();
        adapter.addAll(dm.activityList);
        swipeContainer.setRefreshing(false);
        Snackbar.make(swipeContainer, "Prenotazioni aggiornate", 3000).show();
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