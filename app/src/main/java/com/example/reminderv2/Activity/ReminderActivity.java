package com.example.reminderv2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminderv2.Database.Reminder;
import com.example.reminderv2.Database.SQLite;
import com.example.reminderv2.R;

public class ReminderActivity extends AppCompatActivity {

    Reminder reminder;

    private Intent intent;

    private TextView tvShowStartDate, tvShowStartTime, tvShowFinishDate, tvShowFinishTime, tvShowAlarmDate, tvShowAlarmTime, tvShowSubject, tvShowContent, tvShowMailStatus, tvShowMailSubject, tvShowMailContent;
    private Button bBack, bUpdate;

    private int id=-1, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// Telefonu yan çevirmeyi engeller
        setContentView(R.layout.activity_reminder);

        tvShowStartDate = findViewById(R.id.tvShowStartDate);
        tvShowStartTime = findViewById(R.id.tvShowStartTime);
        tvShowFinishDate = findViewById(R.id.tvShowFinishDate);
        tvShowFinishTime = findViewById(R.id.tvShowFinishTime);
        tvShowAlarmDate = findViewById(R.id.tvShowAlarmDate);
        tvShowAlarmTime = findViewById(R.id.tvShowAlarmTime);
        tvShowSubject = findViewById(R.id.tvShowSubject);
        tvShowContent = findViewById(R.id.tvShowContent);
        tvShowMailStatus = findViewById(R.id.tvShowMailStatus);
        tvShowMailSubject = findViewById(R.id.tvShowMailSubject);
        tvShowMailContent = findViewById(R.id.tvShowMailContent);
        bBack = findViewById(R.id.bBack);
        bUpdate = findViewById(R.id.bUpdate);

        intent = getIntent();
        id = intent.getIntExtra("id",-1);
        user_id = intent.getIntExtra("user_id",-1);

        if(id!=-1){//Programın hata vermesini engellemek için id değerini kontrol ediyoruz
            SQLite database = new SQLite(getApplicationContext());
            reminder = database.getReminder(id);
            tvShowStartDate.setText(reminder.getStart_date());
            tvShowStartTime.setText(reminder.getStart_time());
            tvShowFinishDate.setText(reminder.getFinish_date());
            tvShowFinishTime.setText(reminder.getFinish_time());
            tvShowAlarmDate.setText(reminder.getAlarm_date());
            tvShowAlarmTime.setText(reminder.getAlarm_time());
            tvShowSubject.setText(reminder.getSubject());
            tvShowContent.setText(reminder.getContent());
        }
        else{
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG);
        }

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Back butonuna tıklayınca user_id'yi de alarak MainActivity'ye dönüyoruz
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {////Back butonuna tıklayınca user_id'yi ve reminder id'yi alarak EditActivity'ye geçiyoruz
                intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("idd",id);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });
    }
}
