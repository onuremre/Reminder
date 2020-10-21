package com.example.reminderv2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Veritabanı sınıfı
 */
public class SQLite extends SQLiteOpenHelper implements IDatabase {

    private static final int DATABASE_VEERSION = 1;

    private static final String DATABASE_NAME = "sqlite_database";

    private static final String TABLE_REMINDER = "reminder";
    private static String REMINDER_ID = "id";
    private static String REMINDER_START_DATE = "start_date";
    private static String REMINDER_START_TIME = "start_time";
    private static String REMINDER_FINISH_DATE = "finish_date";
    private static String REMINDER_FINISH_TIME = "finish_time";
    private static String REMINDER_ALARM_DATE = "alarm_date";
    private static String REMINDER_ALARM_TIME = "alarm_time";
    private static String REMINDER_SUBJECT = "subject";
    private static String REMINDER_CONTENT = "content";
    private static String REMINDER_MAIL_STATUS = "mail_status";
    private static String REMINDER_MAIL_SUBJECT = "mail_subject";
    private static String REMINDER_MAIL_CONTENT = "mail_content";
    private static String REMINDER_USER_ID = "user_id";

    private static final String TABLE_USER = "user";
    private static String USER_ID = "id";
    private static String USER_USEERNAME = "username";
    private static String USER_PASSWORD = "password";
    private static String USER_MAIL = "mail";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VEERSION);
    }

    /**
     *
     * @param db Veritabanı nesnesi
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDER = "CREATE TABLE " + TABLE_REMINDER + "("
                + REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REMINDER_START_DATE + " TEXT,"
                + REMINDER_START_TIME + " TEXT,"
                + REMINDER_FINISH_DATE + " TEXT,"
                + REMINDER_FINISH_TIME + " TEXT,"
                + REMINDER_ALARM_DATE + " TEXT,"
                + REMINDER_ALARM_TIME + " TEXT,"
                + REMINDER_SUBJECT + " TEXT,"
                + REMINDER_CONTENT + " TEXT,"
                + REMINDER_MAIL_STATUS + " TEXT,"
                + REMINDER_MAIL_SUBJECT + " TEXT,"
                + REMINDER_MAIL_CONTENT + " TEXT,"
                + REMINDER_USER_ID + " INTEGER"
                + ")";
        String CREATE_USER = "CREATE TABLE " + TABLE_USER + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_USEERNAME + " TEXT,"
                + USER_PASSWORD + " TEXT,"
                + USER_MAIL + " TEXT"
                + ")";

        db.execSQL(CREATE_REMINDER);
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *
     * @param start_date Etkinliğin başlangıç tarihi
     * @param user_id Etkinlikle ilgili kişinin id'si
     * @return Reminder sınıfına ait tipte liste döndürüyoruz
     */
    @Override
    public List<Reminder> getReminders(String start_date, int user_id) {
        List<Reminder> reminderList = new ArrayList<Reminder>();

        String query = "SELECT * FROM " + TABLE_REMINDER + "," + TABLE_USER + " WHERE " +
                TABLE_REMINDER + "." + REMINDER_USER_ID + "=" + TABLE_USER + "." + USER_ID + " and "
                + REMINDER_START_DATE + "='" + start_date + "' and "
                + REMINDER_USER_ID + "=" + user_id + " ORDER BY " + REMINDER_START_TIME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                reminder.setId(cursor.getString(0));
                reminder.setMail_status(cursor.getString(9));
                reminder.setMail_subject(cursor.getString(10));
                reminder.setMail_content(cursor.getString(11));
                reminder.setUser_id(cursor.getString(12));

                reminderList.add(reminder);
            }while(cursor.moveToNext());
        }

        db.close();
        return reminderList;
    }

    /**
     * İlgili reminder'ın almamızı sağlar
     * @param id reminder id'si
     * @return Reminder sınıfına ait nesne
     */
    @Override
    public Reminder getReminder(int id) {
        Reminder reminder = new Reminder();
        String query = "SELECT * FROM " + TABLE_REMINDER + " WHERE id="+id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            reminder.setStart_date(cursor.getString(1));
            reminder.setStart_time(cursor.getString(2));
            reminder.setFinish_date(cursor.getString(3));
            reminder.setFinish_time(cursor.getString(4));
            reminder.setAlarm_date(cursor.getString(5));
            reminder.setAlarm_time(cursor.getString(6));
            reminder.setSubject(cursor.getString(7));
            reminder.setContent(cursor.getString(8));
            reminder.setMail_status(cursor.getString(9));
            reminder.setMail_subject(cursor.getString(10));
            reminder.setMail_content(cursor.getString(11));
            reminder.setUser_id(cursor.getString(12));
        }

        cursor.close();
        db.close();

        return reminder;
    }

    /**
     * Veritabanına reminder ekleme methodu
     * @param reminder
     */
    @Override
    public void insertReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(REMINDER_START_DATE,reminder.getStart_date());
        values.put(REMINDER_START_TIME,reminder.getStart_time());
        values.put(REMINDER_FINISH_DATE,reminder.getFinish_date());
        values.put(REMINDER_FINISH_TIME,reminder.getFinish_time());
        values.put(REMINDER_ALARM_DATE,reminder.getAlarm_date());
        values.put(REMINDER_ALARM_TIME,reminder.getAlarm_time());
        values.put(REMINDER_SUBJECT,reminder.getSubject());
        values.put(REMINDER_CONTENT,reminder.getContent());
        values.put(REMINDER_MAIL_STATUS,reminder.getMail_status());
        values.put(REMINDER_MAIL_SUBJECT,reminder.getMail_subject());
        values.put(REMINDER_MAIL_CONTENT,reminder.getMail_content());
        values.put(REMINDER_USER_ID,reminder.getUser_id());

        db.insert(TABLE_REMINDER, null, values);
        db.close();
    }

    /**
     * Reminder güncelleme methodu
     * @param reminder Reminder sınıfına ait nesne
     * @param id reminder nesnesinin id değeri
     */
    @Override
    public void updateReminder(Reminder reminder, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(REMINDER_START_DATE,reminder.getStart_date());
        values.put(REMINDER_START_TIME,reminder.getStart_time());
        values.put(REMINDER_FINISH_DATE,reminder.getFinish_date());
        values.put(REMINDER_FINISH_TIME,reminder.getFinish_time());
        values.put(REMINDER_ALARM_DATE,reminder.getAlarm_date());
        values.put(REMINDER_ALARM_TIME,reminder.getAlarm_time());
        values.put(REMINDER_SUBJECT,reminder.getSubject());
        values.put(REMINDER_CONTENT,reminder.getContent());
        values.put(REMINDER_MAIL_STATUS,reminder.getMail_status());
        values.put(REMINDER_MAIL_SUBJECT,reminder.getMail_subject());
        values.put(REMINDER_MAIL_CONTENT,reminder.getMail_content());
        values.put(REMINDER_USER_ID,reminder.getUser_id());

        db.update(TABLE_REMINDER, values, REMINDER_ID + " =?", new String[] {String.valueOf(id)});
        db.close();
    }

    /**
     * Reminder silme methodu
     * @param reminder Reminder sınıfına ait nesne
     */
    @Override
    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = Integer.parseInt(reminder.getId());
        db.delete(TABLE_REMINDER, REMINDER_ID + " =?", new String[] {String.valueOf(id)});
        db.close();
    }

    /**
     * Reminder id'sini elde etmek için oluşturulan method
     * @param start_date Etkinlik Başlangıç Tarihi
     * @param start_time Etkinlik Başlangıç Saati
     * @param finish_date Etkinlik Bitiş Tarihi
     * @param finish_time Etkinlik Bitiş Saati
     * @param alarm_date Etkinlik Alarm Tarihi
     * @param alarm_time Etkinlik Alarm Saati
     * @param subject Etkinlik Konusu
     * @param content Etkinlik Açıklaması
     * @return Reminder id'si
     */
    @Override
    public int getReminderId(String start_date, String start_time, String finish_date, String finish_time, String alarm_date, String alarm_time, String subject, String content) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + REMINDER_ID + " FROM " + TABLE_REMINDER + " WHERE "
                + REMINDER_START_DATE + " = '" + start_date + "' and "
                + REMINDER_START_TIME + " = '" + start_time + "' and "
                + REMINDER_FINISH_DATE + " = '" + finish_date + "' and "
                + REMINDER_FINISH_TIME + " = '" + finish_time + "' and "
                + REMINDER_ALARM_DATE + " = '" + alarm_date + "' and "
                + REMINDER_ALARM_TIME + " = '" + alarm_time + "' and "
                + REMINDER_SUBJECT + " = '" + subject + "' and "
                + REMINDER_CONTENT + " = '" + content + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String id=cursor.getString(0);
        cursor.close();
        db.close();
        return Integer.parseInt(id);
    }

    /**
     * User id'sini elde etmek için oluşturulan method
     * @param username Kullanıcı adı
     * @param password Kullanıcı şifresi
     * @return User id'si
     */
    @Override
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE username = '" + username + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            String id = cursor.getString(0);
            cursor.close();
            db.close();
            return Integer.parseInt(id);
        }
        else{
            cursor.close();
            db.close();
            return -1;
        }
    }

    /**
     * User ekleme methodu
     * @param user User sınıfına ait nesne
     */
    @Override
    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_USEERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_MAIL, user.getMail());

        db.insert(TABLE_USER, null, values);
        db.close();
    }
}
