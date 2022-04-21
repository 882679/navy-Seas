package com.example.navyseas.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

	private final LayoutInflater inflater;
	private final ArrayList<Activity> activities;

	public ProfileAdapter(Context context, ArrayList<Activity> activities) {
		inflater = LayoutInflater.from(context);
		this.activities = activities;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.card_view_profile, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, int position) {
		Activity current = activities.get(position);
		holder.setData(current);
	}

	@Override
	public int getItemCount() {
		return activities.size();
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	public void removeItem(int position) {
		activities.remove(position);
		notifyItemRemoved(position);
	}

	public void restoreItem(Activity item, int position) {
		activities.add(position, item);
		notifyItemInserted(position);
	}

	public ArrayList<Activity> getData() {
		return activities;
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	public void addAll(ArrayList<Activity> list) {
		activities.addAll(list);
		notifyDataSetChanged();
	}


	static class MyViewHolder extends RecyclerView.ViewHolder {

		private final TextView activityName;
		private final TextView studentName;
		private final TextView price;
		private final TextView day;
		private final ImageView imgActivity;

		public MyViewHolder(View itemView) {
			super(itemView);
			activityName = itemView.findViewById(R.id.activityName);
			studentName = itemView.findViewById(R.id.studentName);
			price = itemView.findViewById(R.id.expenses);
			day = itemView.findViewById(R.id.weekDay);
			imgActivity = itemView.findViewById(R.id.imageView);
		}

		// Imposto le informazioni delle cards nel recyclerview
		public void setData(Activity currentCard) {
			this.activityName.setText(currentCard.getName());
			this.imgActivity.setImageResource(MainActivity.getActivityIcon(currentCard));
			this.price.setText(String.format("%s0 €", currentCard.getPrice()));
			this.day.setText(currentCard.getDay());
		}

	}

}
