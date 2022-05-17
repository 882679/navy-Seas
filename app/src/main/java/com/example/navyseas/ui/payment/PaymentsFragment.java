package com.example.navyseas.ui.payment;

import static com.example.navyseas.MainActivity.navController;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.Login;
import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Payment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.navyseas.databinding.FragmentPaymentsBinding;

import static com.example.navyseas.MainActivity.navController;

public class PaymentsFragment extends Fragment {

    public static Family selectedFamily = Login.selectedFamily;
    public DBHelper db;
    private double amount;
    private FragmentPaymentsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DBHelper(container.getContext());

        //Navy@stud.unive.it


        amount = db.getAmount(selectedFamily);

        ArrayList<Payment> payments = db.getPayments(selectedFamily);
        System.out.println(Arrays.toString(payments.toArray()));

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewPayment);

        PaymentsAdapter paymentsAdapter = new PaymentsAdapter(container.getContext(), payments);

        recyclerView.setAdapter(paymentsAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        Button btn_pay = root.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(view -> {
            if (amount > 0) {
                db.pay(selectedFamily);
                navController.navigate(R.id.nav_payments);
            } else {
                Snackbar snackbar = Snackbar.make(view, "Non hai nessun'attività da pagare!", 2000);
                snackbar.setAction("ok", v -> {
                });
                snackbar.show();
            }

        });

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
