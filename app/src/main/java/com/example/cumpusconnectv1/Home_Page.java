package com.example.cumpusconnectv1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity {
    String userName, userSecondName, userMevki, userUnivesty, userMail, selectedUniversityMevk;
    TextView signPg;
    Button basketbol, futbol, voleybol;
    RecyclerView recyclerView;
    UserListAdapter userListAdapter;
    AutoCompleteTextView mevkiTxtChc;
    ImageView searchMevki;
    List<Cleaner> newFilteredUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signPg = findViewById(R.id.signpg);
        userName = getIntent().getExtras().getString("firstName");
        userMevki = getIntent().getExtras().getString("mevki");
        userSecondName = getIntent().getExtras().getString("secondName");
        userUnivesty = getIntent().getExtras().getString("universty");
        userMail = getIntent().getExtras().getString("mail");

        basketbol = findViewById(R.id.basketbol);
        futbol = findViewById(R.id.futbol);
        voleybol = findViewById(R.id.voleybol);

        searchMevki = findViewById(R.id.searching_mevki);

        signPg.setText("Hoşgeldin\n\t\t\t\t\t\t\t\t\t" + userName+ " " + userSecondName);


        mevkiTxtChc = findViewById(R.id.mevki_choice);

        String[] person_mevk = getResources().getStringArray(R.array.person_mevk);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, person_mevk);
        mevkiTxtChc.setAdapter(adapter5);
        mevkiTxtChc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityMevk = parent.getItemAtPosition(position).toString();
                Toast.makeText(Home_Page.this, "Seçilen Alan: " + selectedUniversityMevk, Toast.LENGTH_SHORT).show();
            }
        });


        DBHelper dbHelper = new DBHelper(this);
        dbHelper.openDB();

        // Tüm kullanıcıları al
        List<Cleaner> allUsers = dbHelper.getAllCleaners();

        for(int i = 0; i< allUsers.size(); i++)
        {
            Cleaner testUsers = allUsers.get(i);

            if (!newFilteredUsers.contains(testUsers) && testUsers.getUnivers().toString().equals(userUnivesty) && !testUsers.getMail().toString().equals(userMail)) {
                newFilteredUsers.add(allUsers.get(i));
            }

        }

        // RecyclerView için adapter oluştur
        userListAdapter = new UserListAdapter(this, newFilteredUsers);

        // RecyclerView'ı ayarla
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);


        basketbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToBasketbol();
            }
        });

        futbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToFutbol();
            }
        });

        voleybol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToVoleybol();
            }
        });


        searchMevki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToSearching();
            }
        });

    }

    public void clickToBasketbol() {
        List<Cleaner> newBasketbolFilteredUsers = new ArrayList<>();

        for (int i = 0; i < newFilteredUsers.size(); i++) {
            Cleaner testUsers = newFilteredUsers.get(i);

            if (testUsers.getHobi().toString().equals("Basketbol")) {
                newBasketbolFilteredUsers.add(newFilteredUsers.get(i));
            }
        }

        // RecyclerView için yeni adapter
        userListAdapter = new UserListAdapter(this, newBasketbolFilteredUsers);

        //yeniliyoruz recyclerı
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);

    }

    public void clickToVoleybol(){
        List<Cleaner> newVoleybolFilteredUsers = new ArrayList<>();

        for (int i = 0; i < newFilteredUsers.size(); i++) {
            Cleaner testUsers = newFilteredUsers.get(i);

            if (testUsers.getHobi().toString().equals("Voleybol")) {
                newVoleybolFilteredUsers.add(newFilteredUsers.get(i));
            }
        }

        // RecyclerView için yeni adapter
        userListAdapter = new UserListAdapter(this, newVoleybolFilteredUsers);

        //yeniliyoruz recyclerı
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);
    }

    public void clickToFutbol(){
        List<Cleaner> newFutbolFilteredUsers = new ArrayList<>();

        for (int i = 0; i < newFilteredUsers.size(); i++) {
            Cleaner testUsers = newFilteredUsers.get(i);

            if (testUsers.getHobi().toString().equals("Futbol")) {
                newFutbolFilteredUsers.add(newFilteredUsers.get(i));
            }
        }

        // RecyclerView için yeni adapter
        userListAdapter = new UserListAdapter(this, newFutbolFilteredUsers);

        //yeniliyoruz recyclerı
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);
    }

    public void clickToSearching(){
        List<Cleaner> newSearchFilteredUsers = new ArrayList<>();

        for (int i = 0; i < newFilteredUsers.size(); i++) {
            Cleaner testUsers = newFilteredUsers.get(i);

            if (testUsers.getMevki().toString().equals(mevkiTxtChc.getText().toString())) {
                newSearchFilteredUsers.add(newFilteredUsers.get(i));
            }
        }

        // RecyclerView için yeni adapter
        userListAdapter = new UserListAdapter(this, newSearchFilteredUsers);

        //yeniliyoruz recyclerı
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);
    }

}
