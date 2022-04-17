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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.DataMockup;
import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.FragmentReservationBinding;
import com.example.navyseas.ui.home.HomeFragment;
import com.example.navyseas.ui.reservation.ReservationViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment {
    private FragmentReservationBinding binding;
    private final Student selectedStudent = MainActivity.selectedStudent;

    private DataMockup dataMockup = MainActivity.dataMockup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReservationViewModel reservationViewModel =
                new ViewModelProvider(this).get(ReservationViewModel.class);


        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        textView.setText("Attività non prenotate da "+selectedStudent.getName()+":");

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewReservation);

        List<Activity> activitiesNotBooked = new ArrayList<>();
        for (int i = 0; i < dataMockup.activityList.size(); i++){
            if (!selectedStudent.getActivities().contains(dataMockup.activityList.get(i))){
                activitiesNotBooked.add(dataMockup.activityList.get(i));
            }
        }


        ReservationAdapter adapter = new ReservationAdapter(container.getContext(), activitiesNotBooked, selectedStudent);

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
