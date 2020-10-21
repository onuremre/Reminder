package com.example.reminderv2.Database;

/**
 * Kullanıcı tablosu için gerekli sınıf
 */
public class User {

    private String id;//Kullanıcı id'si
    private String username;//Kullanıcı adı
    private String password;//Kullanıcı şifresi
    private String mail;// Kullanıcı mail adresi

    /**
     * User Constructor methodu
     * @param username
     * @param password
     * @param mail
     */
    public User(String username, String password, String mail){
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    //Get-Set methodları
    public String getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
