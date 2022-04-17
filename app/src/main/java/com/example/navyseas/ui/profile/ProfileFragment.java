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

import com.example.navyseas.DataMockup;
import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.databinding.ActivityMainBinding;
import com.example.navyseas.databinding.FragmentProfileBinding;
import com.example.navyseas.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private final Student selectedStudent = MainActivity.selectedStudent;
    private final DataMockup dataMockup = MainActivity.dataMockup;
    private ProfileAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View root;
    private RecyclerView recyclerView;
    private ViewGroup container;
    private TextView amountTextView;
    private double amount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        this.container = container;

        final TextView textView = binding.textProfile;
        textView.setText("Profilo di "+selectedStudent.getName());

        recyclerView = root.findViewById(R.id.recyclerViewProfile);

        adapter = new ProfileAdapter(container.getContext(), selectedStudent.getActivities());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        enableSwipeToDeleteAndUndo();

        binding.fabAddReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navController.navigate(R.id.nav_reservation);
                Snackbar.make(view, "Prenotazione nuova attività", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        amountTextView = root.findViewById(R.id.amount);
        amount = 0;
        for (Activity a :
                selectedStudent.getActivities()) {
            amount += a.getPrice();
        }
        amountTextView.setText("Totale: "+ amount + "0 €");

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

                amount -= selectedStudent.getActivities().get(viewHolder.getAdapterPosition()).getPrice();
                updateAmount(amount);
                resizeRecyclerView();


                selectedStudent.removeActivity(viewHolder.getAdapterPosition());
                int pos=0;
                for (int j = 0; j< dataMockup.reservations.size(); j++) {
                    if (dataMockup.reservations.get(j).getStudent().equals(selectedStudent) && dataMockup.reservations.get(j).getActivity().equals(activityToRemove)){
                        dataMockup.reservations.remove(j);
                        pos=j;
                        break;
                    }
                }
                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(root, "La prenotazione è stata eliminata.", Snackbar.LENGTH_LONG);
                int finalJ = pos;
                snackbar.setAction("ANNULLA", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.restoreItem(activityToRemove, position);
                        recyclerView.scrollToPosition(position);
                        selectedStudent.getActivities().add(position, activityToRemove);
                        dataMockup.reservations.add(finalJ, new Reservation(activityToRemove, selectedStudent));
                        amount += activityToRemove.getPrice();
                        updateAmount(amount);
                        resizeRecyclerView();
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void resizeRecyclerView(){
        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        recyclerView.setLayoutParams(params);
    }

    private void updateAmount(double price){
        amountTextView.setText("Totale: "+ price + "0 €");
    }

}
