package com.example.cumpusconnectv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdministratorPage extends AppCompatActivity
{
    AutoCompleteTextView univers_choice, mevki_choice, time_choice, hobby_choice;
    ImageView searching_img;
    RecyclerView recyclerViewAdmin;
    UserListAdapter userListAdapter;
    String selectedUniversity="", selectedUniversityMevk="", selectedTime="", selectedHobby="";
    List<Cleaner> newFilteredUsers = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_page);

        univers_choice = findViewById(R.id.univers_choice);
        mevki_choice = findViewById(R.id.mevki_choice);
        time_choice = findViewById(R.id.timeChoice);
        hobby_choice = findViewById(R.id.hobby_choice);

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.openDB();

        // Tüm kullanıcıları al
        List<Cleaner> allUsers = dbHelper.getAllCleaners();

        for(int i = 0; i< allUsers.size(); i++)
        {
            newFilteredUsers.add(allUsers.get(i));
        }

        // RecyclerView için adapter oluştur
        userListAdapter = new UserListAdapter(this, newFilteredUsers);

        // RecyclerView'ı ayarla
        recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);
        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdmin.setAdapter(userListAdapter);


        /**List<Cleaner> newFilteredUsersAdmin = new ArrayList<>();

        for (int i = 0; i < allUsers.size(); i++) {
            Cleaner testUsers = allUsers.get(i);
            if(i==0){
                Toast.makeText(this, "ilk katılımcı: " + testUsers.getFirstName(), Toast.LENGTH_SHORT).show();
            }

            if(allUsers.size()-1 == i)
            {
                Toast.makeText(this, "son katılımcı: "+testUsers.getFirstName() , Toast.LENGTH_SHORT).show();
            }
        }**/

        recyclerViewAdmin.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
                    final int position = rv.getChildAdapterPosition(childView);
                    if (position != RecyclerView.NO_POSITION) {
                        Cleaner user = newFilteredUsers.get(position);

                        // Detayları gösteren AlertDialog oluştur
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdministratorPage.this);
                        builder.setTitle("Kullanıcı Detayları");

                        // Kullanıcının detaylarını içeren bir metin oluştur
                        String userDetails = "Ad: " + user.getFirstName() + " " + user.getSecondName() +
                                "\nE-posta: " + user.getMail() +
                                "\nTelefon Numarası: " + user.getPhoneNumber() +
                                "\nŞifre: " + user.getPassword() +
                                "\nYaş: " + user.getAge() +
                                "\nCinsiyet: " + user.getGend() +
                                "\nÜniversite: " + user.getUnivers() +
                                "\nFakülte: " + user.getFakulte() +
                                "\nBölüm: " + user.getBolum() +
                                "\nSınıf: " + user.getSinif() +
                                "\nHobi: " + user.getHobi() +
                                "\nMevki: " + user.getMevki() +
                                "\nKayıt Tarihi: " + user.getRegistrationDate();

                        // Detayları içeren metni AlertDialog içerisine ayarla
                        builder.setMessage(userDetails);

                        builder.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Kullanıcı 'Sil' dediğinde buraya gelir
                                deleteCleaner(position);
                            }
                        });

                        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Kullanıcı 'İptal' dediğinde buraya gelir
                                dialog.dismiss();
                            }
                        });

                        // Detayları içeren AlertDialog'ı göster
                        builder.show();

                        return true;
                    }
                }
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }


            private void deleteCleaner(int position) {
                // Burada SQLite veritabanından seçili kullanıcıyı sil
                DBHelper dbHelper = new DBHelper(AdministratorPage.this);
                dbHelper.openDB();

                Cleaner userToDelete = newFilteredUsers.get(position);
                boolean isDeleted = dbHelper.deleteCleaner(userToDelete.getMail());

                dbHelper.closeDB();

                if (isDeleted) {
                    // Kullanıcı başarıyla silindi
                    Toast.makeText(AdministratorPage.this, "Kullanıcı başarıyla silindi", Toast.LENGTH_SHORT).show();

                    // RecyclerView'ı güncelle
                    updateRecyclerView();
                } else {
                    // Kullanıcı silinirken bir hata oluştu
                    Toast.makeText(AdministratorPage.this, "Kullanıcı silinirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String[] admin_universty = getResources().getStringArray(R.array.turkey_universty_Admin);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, admin_universty);
        univers_choice.setAdapter(adapter5);
        univers_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversity = parent.getItemAtPosition(position).toString();
                Toast.makeText(AdministratorPage.this, "Seçilen: " + selectedUniversity, Toast.LENGTH_SHORT).show();
            }
        });

        String[] admin_mevki = getResources().getStringArray(R.array.admin_mevk);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,admin_mevki);
        mevki_choice.setAdapter(adapter6);
        mevki_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUniversityMevk = parent.getItemAtPosition(position).toString();
                Toast.makeText(AdministratorPage.this, "Seçilen: "+ selectedUniversityMevk, Toast.LENGTH_SHORT).show();
            }
        });

        String[] admin_time = getResources().getStringArray(R.array.time_str);
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, admin_time);
        time_choice.setAdapter(adapter7);
        time_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = parent.getItemAtPosition(position).toString();
                DBHelper dbHelper = new DBHelper(AdministratorPage.this);
                dbHelper.openDB();

                List<Cleaner> newFilteredUsersAdmin = new ArrayList<>();

                // Tüm kullanıcıları al
                List<Cleaner> allUsers = dbHelper.getAllCleaners();

                for (int i = 0; i < allUsers.size(); i++) {
                    Cleaner testUsers = allUsers.get(i);
                    String testUsersDate = testUsers.getRegistrationDate().toString();
                    String testUserYear = testUsersDate.substring(0, 4);

                    // .equals() metodunu kullanarak string karşılaştırma yapın
                    if (testUserYear.equals(selectedTime)) {
                        newFilteredUsersAdmin.add(allUsers.get(i));
                    }
                }

                // RecyclerView için adapter oluştur
                userListAdapter = new UserListAdapter(AdministratorPage.this, newFilteredUsersAdmin);

                // RecyclerView'ı ayarla
                recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);
                recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(AdministratorPage.this));
                recyclerViewAdmin.setAdapter(userListAdapter);
                Toast.makeText(AdministratorPage.this, "Seçilen: " + selectedTime, Toast.LENGTH_SHORT).show();
            }

        });

        String[] admin_hobby = getResources().getStringArray(R.array.admin_hobby);
        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, admin_hobby);
        hobby_choice.setAdapter(adapter8);
        hobby_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedHobby = parent.getItemAtPosition(position).toString();
                Toast.makeText(AdministratorPage.this, "Seçilen: " + selectedHobby, Toast.LENGTH_SHORT).show();
            }
        });

        searching_img = findViewById(R.id.searching_img);
        searching_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecyclerView();
            }
        });

    }

    public void onItemClick(Cleaner user) {
        // Burada tıklanan kullanıcının bilgilerini kullanarak bir Toast mesajı gösterin
        String registrationDate = user.getRegistrationDate();
        Toast.makeText(this, "Tıklanan Kullanıcı: " + user.getFirstName() + " " + user.getSecondName() +
                "\nKayıt Tarihi: " + registrationDate, Toast.LENGTH_SHORT).show();
    }
    public void updateRecyclerView() {
        List<Cleaner> filteredList = applyFilters();
        userListAdapter = new UserListAdapter(this, filteredList);

        recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);
        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdmin.setAdapter(userListAdapter);
    }

    private List<Cleaner> applyFilters() {
        List<Cleaner> filteredList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.openDB();

        // Kolayca ek koşullar eklemek için gerçek koşullu sağlar
        String baseQuery = "SELECT * FROM cleanerInfo WHERE 1=1";

        // Seçime göre koşulları ekliyor
        if (!selectedUniversity.isEmpty() && !selectedUniversity.equals("All")) {
            baseQuery += " AND univers = '" + selectedUniversity + "'";
        }

        if (selectedUniversityMevk != null && !selectedUniversityMevk.isEmpty() && !selectedUniversityMevk.equals("All")) {
            baseQuery += " AND mevki = '" + selectedUniversityMevk + "'";
        }

        /**if (!selectedTime.isEmpty() && !selectedTime.equals("All")) {
            baseQuery += " AND registrationYear = '" + selectedTime + "'";
        }**/

        if (!selectedHobby.isEmpty() && !selectedHobby.equals("All")) {
            baseQuery += " AND hobi = '" + selectedHobby + "'";
        }

        // Son sorguyu yürütün
        List<Cleaner> allCleaners = dbHelper.getAllCleanersWithQuery(baseQuery);


        filteredList.addAll(allCleaners);

        dbHelper.closeDB();
        return filteredList;
    }

}