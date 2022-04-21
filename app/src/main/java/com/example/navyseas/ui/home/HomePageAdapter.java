package com.example.navyseas.ui.home;

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
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

	private final LayoutInflater inflater;
	private final ArrayList<Reservation> reservations;
	private final ArrayList<Activity> activities;
	private final ArrayList<Student> students;

	public HomePageAdapter(Context context, Family selectedFamily) {
		inflater = LayoutInflater.from(context);
		DBHelper db = new DBHelper(context);
		reservations = db.getChildrenReservations(selectedFamily);
		activities = db.getActivities();
		students = db.getStudents();
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.card_view_home, parent, false);
		return new MyViewHolder(view, students, reservations, activities);
	}

	@Override
	public void onBindViewHolder(@NonNull HomePageAdapter.MyViewHolder holder, int position) {
		Reservation current = reservations.get(position);
		holder.setData(current);
	}

	@Override
	public int getItemCount() {
		return reservations.size();
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	public void addAll(ArrayList<Reservation> list) {
		reservations.addAll(list);
		notifyDataSetChanged();
	}

	public void clear() {
		reservations.clear();
		notifyDataSetChanged();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		private final TextView activityName;
		private final TextView studentName;
		private final TextView price;
		private final TextView day;
		private final ImageView imgActivity;
		private final ArrayList<Reservation> reservations;
		private final ArrayList<Activity> activities;
		private final ArrayList<Student> students;

		public MyViewHolder(View itemView, ArrayList<Student> students, ArrayList<Reservation> reservations, ArrayList<Activity> activities) {
			super(itemView);
			activityName = itemView.findViewById(R.id.activityName);
			studentName = itemView.findViewById(R.id.studentName);
			price = itemView.findViewById(R.id.expenses);
			day = itemView.findViewById(R.id.weekDay);
			imgActivity = itemView.findViewById(R.id.imageView);
			this.activities = activities;
			this.reservations = reservations;
			this.students = students;
		}

		// Imposto le informazioni delle cards nel recyclerview
		public void setData(Reservation currentCard) {
			String name = null, day = null, sName = null;
			Activity a = null;
			Double price = null;

			for (int i = 0; i < activities.size(); i++) {
				if (currentCard.getActivityID() == activities.get(i).getId()) {
					name = activities.get(i).getName();
					a = activities.get(i);
					price = activities.get(i).getPrice();
					day = activities.get(i).getDay();
				}
			}

			for (int i = 0; i < students.size(); i++) {
				if (currentCard.getStudentID() == students.get(i).getId())
					sName = students.get(i).getName();
			}

			this.activityName.setText(name);
			this.imgActivity.setImageResource(MainActivity.getActivityIcon(a));

			this.studentName.setText(sName);
			this.price.setText(String.format("%s €", price));
			this.day.setText(day);
		}

	}

}
