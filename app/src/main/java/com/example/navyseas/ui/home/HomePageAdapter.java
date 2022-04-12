package com.example.navyseas.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navyseas.R;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Reservation> reservations;

    public HomePageAdapter(Context context, ArrayList<Reservation> reservations) {
        inflater = LayoutInflater.from(context);
        this.reservations = reservations;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_home, parent, false);
        return new MyViewHolder(view);
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

    // aggiunge la lista dei paesi nel recyclerview
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

        public MyViewHolder(View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            studentName = itemView.findViewById(R.id.studentName);
            price = itemView.findViewById(R.id.expenses);
            day = itemView.findViewById(R.id.weekDay);
            imgActivity = itemView.findViewById(R.id.imageView);
        }

        // imposto le informazioni delle cards nel recyclerview
        public void setData(Reservation currentCard) {
            this.activityName.setText(currentCard.getActivity().getName());
            this.imgActivity.setImageResource(R.drawable.icon_activity);
            this.studentName.setText(currentCard.getStudent().getName());
            this.price.setText(""+currentCard.getActivity().getPrice());
            this.day.setText(currentCard.getActivity().getDay());
        }

    }

}
