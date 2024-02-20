package com.example.cumpusconnectv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppCompatButton loginBtn;
    TextView regPg;
    AppCompatEditText mail, password;
    private DBHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        regPg = findViewById(R.id.regpg);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        regPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPageActivity();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity();
            }
        });


    }

    public void registerPageActivity()
    {
        Intent intent = new Intent(MainActivity.this, Register_Page.class);
        startActivity(intent);
    }

    public void homePageActivity()
    {
        String enteredMail = mail.getText().toString();
        String enteredPassword = password.getText().toString();

        if(enteredPassword.equals("12345") && enteredMail.equals("admin"))
        {
            Intent intent = new Intent(MainActivity.this, AdministratorPage.class);
            startActivity(intent);
        }
        else {
            Cleaner loggedCleaner = dbHelper.loginCleaner(enteredMail, enteredPassword);

            if (loggedCleaner != null) {
                // Kullanıcı bulundu, giriş yapabilirsiniz
                Toast.makeText(this, "Hoş geldiniz, " + loggedCleaner.getFirstName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Home_Page.class);
                intent.putExtra("firstName", loggedCleaner.getFirstName());
                intent.putExtra("secondName", loggedCleaner.getSecondName());
                intent.putExtra("mevki", loggedCleaner.getMevki());
                intent.putExtra("universty", loggedCleaner.getUnivers());
                intent.putExtra("mail", loggedCleaner.getMail());
                startActivity(intent);

            } else {
                // Kullanıcı bulunamadı, hata mesajı göster
                Toast.makeText(this, "Geçersiz kullanıcı adı veya şifre", Toast.LENGTH_SHORT).show();
            }
        }

    }


    //Geçici kullanıyorum burayı normal bir uygulamada kullanılmayacak
    public void deleteAllUsers(View view) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.openDB();

        // Tüm kullanıcıları sil
        dbHelper.deleteAllUsers();

        dbHelper.closeDB();

        // Silindikten sonra yapılacak işlemleri buraya ekleyebilirsiniz.
    }

}
