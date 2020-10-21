package com.example.reminderv2.Database;

/**
 * Etkinlik tablosu için gerekli sınıf
 */
public class Reminder {

    private String id;//Etkinlik id'si
    private String start_date;//Etkinlik başlangıç tarihi
    private String start_time;//Etkinlik başlangıç saati
    private String finish_date;//Etkinlik bitiş tarihi
    private String finish_time;//Etkinlik bitiş saati
    private String alarm_date;//Etkinlik alarm tarihi
    private String alarm_time;//Etkinlik alarm saati
    private String subject;//Etkinlik konusu
    private String content;//Etkinlik açıklaması
    private String mail_status;//Etkinlik saati geldiğinde mail atılıp atılmayacağının kontrolü
    private String mail_subject;//Etkinlik için atılacak mailin konusu
    private String mail_content;//Etkinlik için atılacak mailin açıklaması
    private String user_id;//Etkinlik foreign key'i

    public Reminder(){}

    /**
     * Reminder Constructor'ı
     * @param start_date
     * @param start_time
     * @param finish_date
     * @param finish_time
     * @param alarm_date
     * @param alarm_time
     * @param subject
     * @param content
     */
    public Reminder(String start_date, String start_time, String finish_date, String finish_time, String alarm_date, String alarm_time, String subject, String content) {
        this.start_date = start_date;
        this.start_time = start_time;
        this.finish_date = finish_date;
        this.finish_time = finish_time;
        this.alarm_date = alarm_date;
        this.alarm_time = alarm_time;
        this.subject = subject;
        this.content = content;
    }

    //Get-Set methodları
    public String getId(){ return id; }

    public void setId(String id){
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMail_status() {
        return mail_status;
    }

    public void setMail_status(String mail_status) {
        this.mail_status = mail_status;
    }

    public String getMail_subject() {
        return mail_subject;
    }

    public void setMail_subject(String mail_subject) {
        this.mail_subject = mail_subject;
    }

    public String getMail_content() {
        return mail_content;
    }

    public void setMail_content(String mail_content) {
        this.mail_content = mail_content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
