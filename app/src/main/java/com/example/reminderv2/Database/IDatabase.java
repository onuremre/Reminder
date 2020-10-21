package com.example.reminderv2.Database;

import java.util.List;

/**
 * Veritabanı sınıfı için gerekli olan interface
 */
public interface IDatabase {

    public List<Reminder> getReminders(String start_date, int user_id);

    public Reminder getReminder(int id);

    public int getReminderId(String start_date, String start_time, String finish_date, String finish_time, String alarm_date, String alarm_time, String subject, String content);

    public void insertReminder(Reminder reminder);

    public void updateReminder(Reminder reminder, int id);

    public void deleteReminder(Reminder reminder);

    public int getUserId(String username, String password);

    public void insertUser(User user);
}
