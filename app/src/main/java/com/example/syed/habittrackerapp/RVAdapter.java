package com.example.syed.habittrackerapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by syed on 06/06/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.HabitsViewHolder> {

    List<Habit> habits;
    private LayoutInflater inflater;
    int[] deleteHabitList;
    private ClickCallback clickCallback;

    public interface ClickCallback {
        void itemClick(int p);
    }

    public void setClickCallback(final ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }


    public RVAdapter(List<Habit> habits, Context c) {
        inflater = LayoutInflater.from(c);
        this.habits = habits;
    }

    @Override
    public HabitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_main, parent, false);
        HabitsViewHolder nvh = new HabitsViewHolder(view);
        return nvh;
    }

    @Override
    public void onBindViewHolder(HabitsViewHolder holder, int position) {
        holder.title.setText(habits.get(position).getTitle());
        holder.desc.setText(habits.get(position).getDesc());
        holder.repetition.setText(habits.get(position).getRepetition());
        holder.status.setText(habits.get(position).getStatus());
    }

    public int[] getDeleteHabitList() {
        return deleteHabitList;
    }

    public void setDeleteHabitList() {
        this.deleteHabitList = new int[habits.size()];
        for (int i = 0; i < deleteHabitList.length; i++) {
            deleteHabitList[i] = 0;
        }
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public class HabitsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.repetition)
        TextView repetition;
        @BindView(R.id.status_check)
        TextView status;
        @BindView(R.id.status)
        @Nullable
        CheckBox check;
        private View container;


        HabitsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            container = itemView.findViewById(R.id.LL);
            setDeleteHabitList();
            check = (CheckBox) itemView.findViewById(R.id.check);
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        deleteHabitList[getAdapterPosition()] = 1;
                    } else
                        deleteHabitList[getAdapterPosition()] = 0;
                }
            });
        }


    }
}
