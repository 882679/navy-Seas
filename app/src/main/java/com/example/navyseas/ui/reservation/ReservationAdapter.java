package com.example.navyseas.ui.reservation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.ui.home.HomeFragment;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {
	private final LayoutInflater inflater;
	private final ArrayList<Activity> activityList;
	private final Student selectedStudent;
	private final ArrayList<Activity> studentActivities;
	private final FragmentManager fragManager;

	public ReservationAdapter(Context context, FragmentManager getSupportFragmentManager, ArrayList<Activity> activityList, Student selectedStudent, ArrayList<Activity> studentActivities) {
		inflater = LayoutInflater.from(context);
		this.activityList = activityList;
		this.selectedStudent = selectedStudent;
		this.studentActivities = studentActivities;
		this.fragManager = getSupportFragmentManager;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.card_view_reservation, parent, false);
		return new MyViewHolder(view, studentActivities);
	}

	@Override
	public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {
		Activity currentActivity = activityList.get(position);
		holder.setData(currentActivity);

		holder.itemView.setOnClickListener(v -> {
			DetailsDialog dFragment = new DetailsDialog(currentActivity, selectedStudent);
			// Show DialogFragment
			dFragment.show(fragManager, "Activity Details Fragment");
		});

	}

	@Override
	public int getItemCount() {
		return activityList.size();
	}

	public void removeItem(int position) {
		activityList.remove(position);
		notifyItemRemoved(position);
	}

	public ArrayList<Activity> getData() {
		return activityList;
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	public void restoreItem() {
		notifyDataSetChanged();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		private final TextView activityName;
		private final TextView capacity;
		private final TextView price;
		private final TextView day;
		private final ImageView imgActivity;
		private final ArrayList<Activity> studentActivities;

		public MyViewHolder(View itemView, ArrayList<Activity> studentActivities) {
			super(itemView);
			activityName = itemView.findViewById(R.id.activityName);
			capacity = itemView.findViewById(R.id.capacity);
			price = itemView.findViewById(R.id.expenses);
			day = itemView.findViewById(R.id.weekDay);
			imgActivity = itemView.findViewById(R.id.imageView);
			this.studentActivities = studentActivities;
		}

		// Imposto le informazioni delle cards nel recyclerview
		public void setData(Activity currentActivity) {
			this.activityName.setText(currentActivity.getName());
			this.capacity.setText(HomeFragment.db.getNumberOfReservations(currentActivity)+"/"+currentActivity.getCapacity());
			this.imgActivity.setImageResource(MainActivity.getActivityIcon(currentActivity));
			this.price.setText(String.format("%s €", currentActivity.getPrice()));

			boolean redColor = false;
			for (Activity a : studentActivities) {
				if (a.getDay().equals(currentActivity.getDay())) {
					redColor = true;
					break;
				}

			}

			this.day.setText(currentActivity.getDay());
			if (redColor) this.day.setTextColor(Color.RED);
		}

	}
}
