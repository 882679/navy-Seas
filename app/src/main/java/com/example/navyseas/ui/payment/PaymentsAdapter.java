package com.example.navyseas.ui.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.R;
import com.example.navyseas.database.Model.Payment;

import java.util.ArrayList;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.MyViewHolder> {
	private final LayoutInflater inflater;
	private final ArrayList<Payment> payments;

	public PaymentsAdapter(Context context, ArrayList<Payment> payments) {
		inflater = LayoutInflater.from(context);
		this.payments = payments;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.element_list_payment, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Payment currentActivity = payments.get(position);
		holder.setData(currentActivity);
	}

	@Override
	public int getItemCount() {
		return payments.size();
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}


	public ArrayList<Payment> getData() {
		return payments;
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		private final TextView date;
		private final TextView amount;

		public MyViewHolder(View itemView) {
			super(itemView);
			date = itemView.findViewById(R.id.date);
			amount = itemView.findViewById(R.id.amount);
		}

		// Imposto le informazioni delle cards nel recyclerview
		public void setData(Payment currentItem) {
			this.date.setText(currentItem.getDate());
			this.amount.setText(String.format("%s0 €", currentItem.getAmount()));
		}

	}

}
