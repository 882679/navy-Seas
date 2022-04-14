package com.example.navyseas.ui.reservation;

import static com.example.navyseas.MainActivity.navController;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Student;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final List<Activity> activityList;
    private final Context context;
    private final Student selectedStudent = MainActivity.selectedStudent;


    public ReservationAdapter(Context context, List<Activity> activityList) {
        inflater = LayoutInflater.from(context);
        this.activityList = activityList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_reservation, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {
        Activity current = activityList.get(position);
        holder.setData(current);

        holder.itemView.setOnClickListener(v -> {
            Activity selectedActivity = activityList.get(position);
            new AlertDialog.Builder(context)
                    .setTitle("Conferma prenotazione")
                    .setMessage("Sei sicuro di voler prenotare l'attività '"
                            + selectedActivity.getName() + "' per " +
                            MainActivity.selectedStudent.getName() + " questo " + selectedActivity.getDay() + "?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (selectedStudent.subscribe(MainActivity.familyCollection, MainActivity.studentCollection, MainActivity.students, selectedActivity)) {
                                navController.navigate(R.id.nav_profile);
                            } else {
                                Toast.makeText(context.getApplicationContext(), "Errore! " + selectedStudent.getName() + " quel giorno è già impegnato.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
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

    // aggiunge la lista dei paesi nel recyclerview
    public void addAll(List<Activity> list) {
        activityList.addAll(list);
        notifyDataSetChanged();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView activityName;
        private final TextView price;
        private final TextView day;
        private final ImageView imgActivity;

        public MyViewHolder(View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            price = itemView.findViewById(R.id.expenses);
            day = itemView.findViewById(R.id.weekDay);
            imgActivity = itemView.findViewById(R.id.imageView);
        }

        // imposto le informazioni delle cards nel recyclerview
        public void setData(Activity currentCard) {
            this.activityName.setText(currentCard.getName());
            this.imgActivity.setImageResource(R.drawable.icon_activity);
            this.price.setText(currentCard.getPrice() + " €");
            this.day.setText(currentCard.getDay());
        }

    }

}
