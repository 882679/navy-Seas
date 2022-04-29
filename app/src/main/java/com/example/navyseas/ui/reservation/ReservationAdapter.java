package com.example.navyseas.ui.reservation;

import static com.example.navyseas.MainActivity.navController;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.MainActivity;
import com.example.navyseas.R;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {
	private final LayoutInflater inflater;
	private final ArrayList<Activity> activityList;
	private final Context context;
	private final Student selectedStudent;
	private final DBHelper db;
	private final ArrayList<Activity> studentActivities;

	public ReservationAdapter(Context context, ArrayList<Activity> activityList, Student selectedStudent, ArrayList<Activity> studentActivities, DBHelper db) {
		inflater = LayoutInflater.from(context);
		this.activityList = activityList;
		this.context = context;
		this.selectedStudent = selectedStudent;
		this.studentActivities = studentActivities;
		this.db = db;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.card_view_reservation, parent, false);
		return new MyViewHolder(view, studentActivities);
	}

	@Override
	public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {
		Activity current = activityList.get(position);
		holder.setData(current);

		holder.itemView.setOnClickListener(v -> {
			Activity selectedActivity = activityList.get(position);
			if (db.checkActivity(selectedStudent, selectedActivity)) {
				new AlertDialog.Builder(context)
						.setTitle("Conferma prenotazione")
						.setMessage("Sei sicuro di voler prenotare l'attività '" +
								selectedActivity.getName() + "' per " +
								MainActivity.selectedStudent.getName() + " questo " +
								selectedActivity.getDay() + "?"
						)
						.setPositiveButton(android.R.string.yes, (dialog, which) -> {
							db.subscribe(new Reservation(
									selectedStudent.getId(),
									selectedActivity.getId()
							));
							navController.navigate(R.id.nav_profile);
						})
						.setNegativeButton(android.R.string.no, null)
						.setIcon(R.drawable.baseline_new_releases_black_24dp)
						.show();
			} else Toast.makeText(
					context.getApplicationContext(),
					"Errore! " + selectedStudent.getName() + " quel giorno è già impegnato.",
					Toast.LENGTH_LONG
			).show();
		});
	}

	@Override
	public int getItemCount() {
		return activityList.size();
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		private final TextView activityName;
		private final TextView price;
		private final TextView day;
		private final ImageView imgActivity;
		private final ArrayList<Activity> studentActivities;

		public MyViewHolder(View itemView, ArrayList<Activity> studentActivities) {
			super(itemView);
			activityName = itemView.findViewById(R.id.activityName);
			price = itemView.findViewById(R.id.expenses);
			day = itemView.findViewById(R.id.weekDay);
			imgActivity = itemView.findViewById(R.id.imageView);
			this.studentActivities = studentActivities;
		}

		// Imposto le informazioni delle cards nel recyclerview
		public void setData(Activity currentCard) {
			this.activityName.setText(currentCard.getName());
			this.imgActivity.setImageResource(MainActivity.getActivityIcon(currentCard));
			this.price.setText(String.format("%s €", currentCard.getPrice()));

			boolean redColor = false;
			for (Activity a : studentActivities) {
				if (a.getDay().equals(currentCard.getDay())) {
					redColor = true;
					break;
				}

			}

			this.day.setText(currentCard.getDay());
			if (redColor) this.day.setTextColor(Color.RED);
		}

	}
}
