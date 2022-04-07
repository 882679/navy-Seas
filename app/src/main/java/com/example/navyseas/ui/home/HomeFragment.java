package com.example.navyseas.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.FragmentHomeBinding;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        List<Activity> reservations = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        /*students.add(new Student(new ObjectId(), "Alvise", reservations));
        students.add(new Student(new ObjectId(), "Angelo", reservations));
        students.add(new Student(new ObjectId(), "Alessandro", reservations));
        students.add(new Student(new ObjectId(), "Giulia", reservations));*/
        reservations.add(new Activity(new ObjectId(), "Tennis", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Lettura", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Cinema", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Bicicletta", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Tennis", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Lettura", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Cinema", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Bicicletta", "Lunedi", 10.0, students));

        HomePageAdapter adapter = new HomePageAdapter(container.getContext(), reservations);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }
}