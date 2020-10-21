package com.example.reminderv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reminderv2.Database.Reminder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Reminder elemanlarını göstermek için gerekli olan adapter sınıfı
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder> {
    private List<Reminder> reminders = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {
        Reminder currentReminder = reminders.get(position);
        holder.tvStartTime.setText(currentReminder.getStart_time());
        holder.tvSubject.setText(currentReminder.getSubject());
    }

    /**
     * İtem sayısını almamızı sağlayan method
     * @return item sayısı
     */
    @Override
    public int getItemCount() {
        return reminders.size();
    }

    /**
     *
     * @param reminders
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    /**
     * Seçili reminder'ı almamızı sağlayan method
     * @param position seçili reminder'ın pozisyonu
     * @return Reminder sınıfına ait nesne
     */
    public Reminder getReminderAt(int position) {
        return reminders.get(position);
    }

    class ReminderHolder extends RecyclerView.ViewHolder {
        private TextView tvStartTime, tvSubject;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvSubject = itemView.findViewById(R.id.tvSubject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(reminders.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Reminder reminder);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}