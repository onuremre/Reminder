package com.example.reminderv2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.reminderv2.AlertReceiver;
import com.example.reminderv2.Database.Reminder;
import com.example.reminderv2.Database.SQLite;
import com.example.reminderv2.R;
import com.example.reminderv2.ReminderAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Caker
 * @since 2019-04-01
 * @version 1.0.0
 */
public class MainActivity extends AppCompatActivity {

    Reminder reminder;

    private Intent intent;

    private List<Reminder> reminders;

    private CalendarView calendarView;//Takvim nesnesi
    private BottomSheetBehavior mBottomSheetBehavior;

    private String date;
    private int day,month,year,user_id=-1,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// Telefonu yan çevirmeyi engeller
        setContentView(R.layout.activity_main);

        final SQLite database = new SQLite(getApplicationContext());

        intent = getIntent();
        user_id = intent.getIntExtra("user_id",-1);

        Calendar c = Calendar.getInstance();//Bulunduğumuz tarihi alıyoruz
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH)+1;
        year = c.get(Calendar.YEAR);

        date = year + "-" + month + "-" + day;//Tarihi veritabanında saklamak için uygun hale getiriyoruz

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        final View bottomSheet = findViewById(R.id.bottom_sheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        calendarView = findViewById(R.id.calendarView);

        reminders = database.getReminders(date, user_id);
        adapter.setReminders(reminders);

        /**
         * Takvimde bir güne tıklayınca çalışır
         */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "-" + (month+1) + "-" + dayOfMonth;
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                reminders = database.getReminders(date, user_id);
                adapter.setReminders(reminders);
            }
        });

        /**
         * Recycler View itemlerine tıklayınca çalışır
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * İtemlerin hareket etmesini sağlayan fonksiyon
             * @param recyclerView RecyclerView öğesi
             * @param viewHolder
             * @param target
             * @return Hareket etmesini engellemek için false yaptık
             */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }


            /**
             * İtemlerin sağ-sol hareketini sağlar
             * @param viewHolder
             * @param direction
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //cancelAlarm();
                database.deleteReminder(adapter.getReminderAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,R.string.delete_reminder,Toast.LENGTH_SHORT).show();
                List<Reminder> reminders = database.getReminders(date, user_id);
                adapter.setReminders(reminders);
            }
        }).attachToRecyclerView(recyclerView);

        /**
         * İtemlerin tıklanma fonksiyonu
         */
        adapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reminder reminder) {
                intent = new Intent(getApplicationContext(), ReminderActivity.class);
                intent.putExtra("id",Integer.parseInt(reminder.getId()));
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });
    }

    /**
     * Menü oluşturma
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Menü itemine tıklama fonksiyonu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Oluşturulan alarmın iptal edilmesini sağlar
     * @param id Alarmın benzersiz id'si
     */
    public void cancelAlarm(int id){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent,0);

        alarmManager.cancel(pendingIntent);
    }
}
