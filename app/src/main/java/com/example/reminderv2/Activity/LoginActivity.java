package com.example.reminderv2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reminderv2.Database.SQLite;
import com.example.reminderv2.Database.User;
import com.example.reminderv2.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    SQLite database;

    Intent intent;

    private EditText etUsername, etPassword, etSingupUsername, etSingupPassword, etSingupPassword2, etSingupMail;
    private Button bSingup, bLogin, bAccept;

    String username, password, singupUsername, singupPassword, singupPassword2, singupMail;
    int flag = 0, user_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// Telefonu yan çevirmeyi engeller
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etSingupUsername = findViewById(R.id.etSingupUsername);
        etSingupPassword = findViewById(R.id.etSingupPassword);
        etSingupPassword2 = findViewById(R.id.etSingupPassword2);
        etSingupMail = findViewById(R.id.etSingupMail);
        bSingup = findViewById(R.id.bSingup);
        bLogin = findViewById(R.id.bLogin);
        bAccept = findViewById(R.id.bAccept);

        bSingup.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bAccept.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSingup://Singup için gerekli olan EditTextleri ve Butonu görünür-görünmez yapma
                if(flag == 0){
                    etSingupUsername.setVisibility(View.VISIBLE);
                    etSingupPassword.setVisibility(View.VISIBLE);
                    etSingupPassword2.setVisibility(View.VISIBLE);
                    etSingupMail.setVisibility(View.VISIBLE);
                    bAccept.setVisibility(View.VISIBLE);

                    flag=1;
                }
                else{
                    etSingupUsername.setVisibility(View.INVISIBLE);
                    etSingupPassword.setVisibility(View.INVISIBLE);
                    etSingupPassword2.setVisibility(View.INVISIBLE);
                    etSingupMail.setVisibility(View.INVISIBLE);
                    bAccept.setVisibility(View.INVISIBLE);

                    flag=0;
                }
                break;

            case R.id.bLogin:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                //Boş değerleri engelliyoruz
                if(username.matches("") || password.matches("")){
                    Toast.makeText(getApplicationContext(),R.string.blank,Toast.LENGTH_LONG).show();
                }
                else{
                    database = new SQLite(getApplicationContext());
                    user_id = database.getUserId(username, password);

                    //Veritabanında böyle bir kullanıcının olup olmadığının kontrolü
                    //Varsa id değerini alarak MainActivity'ye geçiyoruz
                    if(user_id != -1){
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.wrong_login,Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.bAccept://Yeni kayıt için gerekli olan Accept butonunun tıklanma fonksiyonu
                singupUsername = etSingupUsername.getText().toString();
                singupPassword = etSingupPassword.getText().toString();
                singupPassword2 = etSingupPassword2.getText().toString();
                singupMail = etSingupMail.getText().toString();

                //Boş değerleri engelliyoruz
                if(singupUsername.matches("") || singupPassword.matches("") || singupPassword2.matches("") || singupMail.matches("")){
                    Toast.makeText(getApplicationContext(),R.string.blank,Toast.LENGTH_LONG).show();
                }
                else{
                    //Girilen şifreler uyuşuyorsa veritabanına yeni kullanıcıyı ekliyoruz
                    if(singupPassword.equals(singupPassword2)){
                        //User sınıfından yeni nesne üretiyoruz
                        User user = new User(singupUsername, singupPassword, singupMail);
                        database = new SQLite(getApplicationContext());
                        //Veritabanına yeni kullanıcı ekleme fonksiyonu
                        database.insertUser(user);
                        Toast.makeText(getApplicationContext(),R.string.singup_succesfull,Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.passwords_not_equal,Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
}
