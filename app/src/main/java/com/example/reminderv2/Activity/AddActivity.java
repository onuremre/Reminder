package com.example.reminderv2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminderv2.AlertReceiver;
import com.example.reminderv2.Database.Reminder;
import com.example.reminderv2.Database.SQLite;
import com.example.reminderv2.PickerFragment.DatePickerFragment;
import com.example.reminderv2.PickerFragment.TimePickerFragment;
import com.example.reminderv2.R;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private DialogFragment timePicker,datePicker;

    private Intent intent;

    private Button bStartDate,bStartTime,bFinishDate,bFinishTime,bAlarmDate,bAlarmTime,bCancel,bAccept;
    private EditText etSubject,etContent,etMailSubject,etMailContent;
    private Switch sMailStatus;

    Calendar c;

    String hour,date,start_date,start_time,finish_date,finish_time,alarm_date,alarm_time,subject,content,mail_subject,mail_content;
    int flag, user_id, id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// Telefonu yan çevirmeyi engeller
        setContentView(R.layout.activity_add);

        bStartDate = findViewById(R.id.bStartDate);
        bStartTime = findViewById(R.id.bStartTime);
        bFinishDate = findViewById(R.id.bFinishDate);
        bFinishTime = findViewById(R.id.bFinishTime);
        bAlarmDate = findViewById(R.id.bAlarmDate);
        bAlarmTime = findViewById(R.id.bAlarmTime);
        etSubject = findViewById(R.id.etSubject);
        etContent = findViewById(R.id.etContent);
        sMailStatus = findViewById(R.id.sMailStatus);
        etMailSubject = findViewById(R.id.etMailSubject);
        etMailContent = findViewById(R.id.etMailContent);
        bCancel = findViewById(R.id.bCancel);
        bAccept = findViewById(R.id.bAccept);

        bStartDate.setOnClickListener(this);
        bStartTime.setOnClickListener(this);
        bFinishDate.setOnClickListener(this);
        bFinishTime.setOnClickListener(this);
        bAlarmDate.setOnClickListener(this);
        bAlarmTime.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bAccept.setOnClickListener(this);
        sMailStatus.setOnClickListener(this);

        intent = getIntent();
        user_id = intent.getIntExtra("user_id",-1);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bStartDate://Butona tıklayınca datePicker objesi oluşturarak tarih seçilmesini sağlıyoruz
                datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), getResources().getString(R.string.date_picker));
                flag=0;
                break;
            case R.id.bStartTime:
                timePicker = new TimePickerFragment();//Butona tıklayınca timePicker objesi oluşturarak saat seçilmesini sağlıyoruz
                timePicker.show(getSupportFragmentManager(), getResources().getString(R.string.time_picker));
                flag=1;
                break;
            case R.id.bFinishDate://Butona tıklayınca datePicker objesi oluşturarak tarih seçilmesini sağlıyoruz
                datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), getResources().getString(R.string.date_picker));
                flag=2;
                break;
            case R.id.bFinishTime://Butona tıklayınca timePicker objesi oluşturarak saat seçilmesini sağlıyoruz
                timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), getResources().getString(R.string.time_picker));
                flag=3;
                break;
            case R.id.bAlarmDate://Butona tıklayınca datePicker objesi oluşturarak tarih seçilmesini sağlıyoruz
                datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), getResources().getString(R.string.date_picker));
                flag=4;
                break;
            case R.id.bAlarmTime:
                timePicker = new TimePickerFragment();//Butona tıklayınca timePicker objesi oluşturarak saat seçilmesini sağlıyoruz
                timePicker.show(getSupportFragmentManager(), getResources().getString(R.string.time_picker));
                flag=5;
                break;
            case R.id.sMailStatus://Mail Status adlı switch'in açık olup olmadığını kontrol ediyoruz. Açıksa mail konu ve açıklama textlerini görünür yapıyoruz
                if(sMailStatus.isChecked()){
                    etMailSubject.setVisibility(View.VISIBLE);
                    etMailContent.setVisibility(View.VISIBLE);
                }
                else{
                    etMailSubject.setVisibility(View.INVISIBLE);
                    etMailContent.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.bCancel://Cancel butonuna tıklayınca user_id' yi de alarak MainActivity' ye geri dönüyoruz.
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
            case R.id.bAccept:
                start_date = bStartDate.getText().toString();
                start_time = bStartTime.getText().toString();
                finish_date = bFinishDate.getText().toString();
                finish_time = bFinishTime.getText().toString();
                alarm_date = bAlarmDate.getText().toString();
                alarm_time = bAlarmTime.getText().toString();
                subject = etSubject.getText().toString();
                content = etContent.getText().toString();
                mail_subject = etMailSubject.getText().toString();
                mail_content = etMailContent.getText().toString();

                //Boş değer olmasını engelliyoruz
                if(start_date.matches("Seç") || start_time.matches("Seç") || finish_date.matches("Seç") || finish_time.matches("Seç") || alarm_date.matches("Seç") || alarm_time.matches("Seç") || subject.matches("") || content.matches("Seç")){
                    Toast.makeText(getApplicationContext(),R.string.blank,Toast.LENGTH_LONG).show();
                }
                else {
                    //Reminder sınıfında nesne üretiyoruz
                    Reminder reminder = new Reminder(start_date,start_time,finish_date,finish_time,alarm_date,alarm_time,subject,content);
                    //User id Constuctor methoduna dahil olmadığı için ayrıca ekliyoruz
                    reminder.setUser_id(String.valueOf(user_id));
                    if(sMailStatus.isChecked()){
                        reminder.setMail_status("1");
                        reminder.setMail_subject(mail_subject);
                        reminder.setMail_content(mail_content);
                    }
                    else{
                        reminder.setMail_status("0");
                        reminder.setMail_subject("0");
                        reminder.setMail_content("0");
                    }
                    SQLite database = new SQLite(getApplicationContext());
                    //Veritabanına ekleme fonksiyonu
                    database.insertReminder(reminder);
                    //Alarmda kullanmak için reminder id'sini aldık
                    id = database.getReminderId(start_date,start_time,finish_date,finish_time,alarm_date,alarm_time,subject,content);
                    //Seçilen tarihi ve Reminder'ın id'sini alarm fonksiyonumuza parametre olarak vererek etkinliği ve alarmı birbirine bağladık
                    startAlarm(c, id);
                    Toast.makeText(this, R.string.create_reminder, Toast.LENGTH_SHORT).show();
                    //user_id i de alarak MainActivit' ye dönüyoruz
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * Saati almamızı sağlayan fonksiyon
     * @param view
     * @param hourOfDay Seçilen saat
     * @param minute Seçilen dakika
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String h = String.valueOf(hourOfDay);
        String m = String.valueOf(minute);
        if(m.length() == 1){
            m = 0 + m;
        }
        if(h.length() == 1){
            h = 0 + h;
        }
        hour = h + ":" +m;

        switch (flag){
            case 1:
                bStartTime.setText(hour);
                break;
            case 3:
                bFinishTime.setText(hour);
                break;
            case 5:
                bAlarmTime.setText(hour);
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                break;
        }
    }

    /**
     * Tarihi almamızı sağlayan fonksiyon
     * @param view
     * @param year Seçilen yıl
     * @param month Seçilen ay
     * @param dayOfMonth Seçilen gün
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = year + "-" + (month+1) + "-" + dayOfMonth;

        switch (flag){
            case 0:
                bStartDate.setText(date);
                break;
            case 2:
                bFinishDate.setText(date);
                break;
            case 4:
                bAlarmDate.setText(date);
                c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month+1);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                break;
        }
    }

    /**
     * Alarm oluşturulmasını sağlayan fonksiyon
     * @param c Alarmın çalışacağı tarih
     * @param id Alarmın benzersiz id'si
     */
    public void startAlarm(Calendar c, int id){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("iddd",id);
        int _id = id;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, intent,0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}
