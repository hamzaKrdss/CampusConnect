package com.example.cumpusconnectv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Register_Page extends AppCompatActivity {
    AppCompatEditText firstName, secondName, mailTxt, phoneNm, passwordTxt, ageTxt;
    RadioGroup genderGrp;
    AutoCompleteTextView uniTxt, fakulteTxt, bolumTxt, sinifTxt, hobiTxt, mevkiTxt;
    AppCompatButton loginBtn;
    String selectedUniversity, gender, selectedUniversityDeparts, selectedUniversityJob,
            selectedUniversityCls, selectedUniversityHoby,selectedUniversityMevk;

    TextView signTxt;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.secondName);
        mailTxt = findViewById(R.id.mailTxt);
        phoneNm = findViewById(R.id.phoneNm);
        passwordTxt = findViewById(R.id.passwordTxt);
        ageTxt = findViewById(R.id.ageTxt);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        //-------------------------Cinsiyet Seçimi----------------------------------------
        genderGrp = findViewById(R.id.genderRadioGroup);
        // Cinsiyet değişikliğini dinleyen bir listener
        genderGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Seçilen cinsiyeti güncelle
                if (checkedId == R.id.maleRadioButton) {
                    gender = "Erkek";
                } else if (checkedId == R.id.femaleRadioButton) {
                    gender = "Kadın";
                }
            }
        });

        //---------------------------------Üniversite Seçimi--------------------------------
        uniTxt = findViewById(R.id.uniTxt);

        String[] uniArray = getResources().getStringArray(R.array.turkey_universty);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, uniArray);
        uniTxt.setAdapter(adapter);
        uniTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversity = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Üniversite: " + selectedUniversity, Toast.LENGTH_SHORT).show();
            }
        });
        //------------------------------Fakülte Seçimi-----------------------------------

        fakulteTxt = findViewById(R.id.fakulteTxt);

        String[] uniDeparts = getResources().getStringArray(R.array.universty_departs);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, uniDeparts);
        fakulteTxt.setAdapter(adapter1);
        fakulteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityDeparts = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Bölüm: " + selectedUniversityDeparts, Toast.LENGTH_SHORT).show();
            }
        });
        //----------------------------Bölüm Seçimi-------------------------------------

        bolumTxt = findViewById(R.id.bolumTxt);

        String[] uniJob = getResources().getStringArray(R.array.universty_job);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, uniJob);
        bolumTxt.setAdapter(adapter2);
        bolumTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityJob = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Alan: " + selectedUniversityJob, Toast.LENGTH_SHORT).show();
            }
        });
        //------------------------Sinif Seçimi-----------------------------------------

        sinifTxt = findViewById(R.id.sinifTxt);

        String[] uniCls = getResources().getStringArray(R.array.universty_class);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, uniCls);
        sinifTxt.setAdapter(adapter3);
        sinifTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityCls = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Alan: " + selectedUniversityJob, Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------------Hobi Seçimi----------------------------------------

        hobiTxt = findViewById(R.id.hobiTxt);

        String[] personHobby = getResources().getStringArray(R.array.person_hobby);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, personHobby);
        hobiTxt.setAdapter(adapter4);
        hobiTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityHoby = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Alan: " + selectedUniversityHoby, Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------------Mevki Seçimi----------------------------------------

        mevkiTxt = findViewById(R.id.mevkiTxt);

        String[] person_mevk = getResources().getStringArray(R.array.person_mevk);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, person_mevk);
        mevkiTxt.setAdapter(adapter5);
        mevkiTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityMevk = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register_Page.this, "Seçilen Alan: " + selectedUniversityMevk, Toast.LENGTH_SHORT).show();
            }
        });

        //-----------------------------------------------------------------
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn();
            }
        });

        //--------------------------Giris Yap---------------------------------------

        signTxt = findViewById(R.id.logPg);
        signTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Register_Page.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    public void registerBtn() {
        if (firstName.getText().toString().isEmpty() || secondName.getText().toString().isEmpty() ||
                mailTxt.getText().toString().isEmpty() || phoneNm.getText().toString().isEmpty() ||
                passwordTxt.getText().toString().isEmpty() || ageTxt.getText().toString().isEmpty() ||
                selectedUniversity == null || gender == null || selectedUniversityDeparts == null ||
                selectedUniversityJob == null || selectedUniversityCls == null ||
                selectedUniversityHoby == null || selectedUniversityMevk == null) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
        } else {
            Cleaner cleaner = new Cleaner(firstName.getText().toString(), secondName.getText().toString(),
                    mailTxt.getText().toString(), phoneNm.getText().toString(), passwordTxt.getText().toString(),
                    selectedUniversity, ageTxt.getText().toString(), gender, selectedUniversityHoby,
                    selectedUniversityMevk, selectedUniversityDeparts, selectedUniversityJob, selectedUniversityCls);

            if (dbHelper.registerCleaner(cleaner)) {
                Toast.makeText(this, "Kayıt Oluşturuldu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Kayıt oluşturulamadı. Lütfen bilgilerinizi kontrol edin.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

}
