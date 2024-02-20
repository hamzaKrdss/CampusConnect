package com.example.cumpusconnectv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private Context context;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        this.context = context;
    }

    public DBHelper openDB() {
        DBControl dbControl = new DBControl(context);
        db = dbControl.getWritableDatabase();
        return this;
    }

    public void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


    //Hata mesajlarını vb. islemleri burada yürütüyorum
    public boolean registerCleaner(Cleaner cleaner) {
        try {
            // Aynı e-posta adresine sahip kullanıcıyı kontrol et
            if (isEmailExists(cleaner.getMail())) {
                String errorMsg = "Bu e-posta adresi zaten kullanımda.";
                Log.e("DB_OPERATION", errorMsg);
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                return false;
            }

            // Aynı e-posta adresine sahip kullanıcı yoksa kayıt işlemini gerçekleştir
            ContentValues cValues = new ContentValues();
            cValues.put("firstName", cleaner.getFirstName());
            cValues.put("secondName", cleaner.getSecondName());
            cValues.put("mail", cleaner.getMail());
            cValues.put("phoneNumber", cleaner.getPhoneNumber());
            cValues.put("password", cleaner.getPassword());
            cValues.put("univers", cleaner.getUnivers());
            cValues.put("age", cleaner.getAge());
            cValues.put("gend", cleaner.getGend());
            cValues.put("hobi", cleaner.getHobi());
            cValues.put("mevki", cleaner.getMevki());
            cValues.put("fakulte", cleaner.getFakulte());
            cValues.put("bolum", cleaner.getBolum());
            cValues.put("sinif", cleaner.getSinif());

            long result = db.insert("cleanerInfo", null, cValues);

            if (result != -1) {
                Log.d("DB_OPERATION", "Kayıt oluşturuldu: " + cleaner.getMail());
                return true;
            } else {
                String errorMsg = "Kayıt oluşturulurken bir hata oluştu. Hata kodu: " + result;
                Log.e("DB_OPERATION", errorMsg);
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Kayıt oluşturulurken bir hata oluştu: " + e.getMessage();
            Log.e("DB_OPERATION", errorMsg);
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    // Aynı e-posta adresine sahip kullanıcı var mı kontrolü
    private boolean isEmailExists(String email) {
        String[] columns = {"mail"};
        String selection = "mail = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("cleanerInfo", columns, selection, selectionArgs, null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }


    //Burada ise kayıt olmuş bir kişinin giriş yaparken mail ve sifre kontrolunu yapıyoruz
    public Cleaner loginCleaner(String mail, String password) {
        Cleaner cleaner = null;

        try {
            String[] columns = {"mail", "password", "firstName", "secondName", "phoneNumber",
                    "univers", "age", "gend", "hobi", "mevki", "fakulte", "bolum", "sinif"};

            String selection = "mail = ? AND password = ?";
            String[] selectionArgs = {mail, password};

            Cursor cursor = db.query("cleanerInfo", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int mailIndex = cursor.getColumnIndex("mail");
                int passwordIndex = cursor.getColumnIndex("password");
                int firstNameIndex = cursor.getColumnIndex("firstName");
                int secondNameIndex = cursor.getColumnIndex("secondName");
                int phoneNumberIndex = cursor.getColumnIndex("phoneNumber");
                int universIndex = cursor.getColumnIndex("univers");
                int ageIndex = cursor.getColumnIndex("age");
                int gendIndex = cursor.getColumnIndex("gend");
                int hobiIndex = cursor.getColumnIndex("hobi");
                int mevkiIndex = cursor.getColumnIndex("mevki");
                int fakulteIndex = cursor.getColumnIndex("fakulte");
                int bolumIndex = cursor.getColumnIndex("bolum");
                int sinifIndex = cursor.getColumnIndex("sinif");

                if (mailIndex != -1 && passwordIndex != -1 && firstNameIndex != -1 && secondNameIndex != -1
                        && phoneNumberIndex != -1 && universIndex != -1 && ageIndex != -1 && gendIndex != -1
                        && hobiIndex != -1 && mevkiIndex != -1 && fakulteIndex != -1 && bolumIndex != -1
                        && sinifIndex != -1) {
                    cleaner = new Cleaner();
                    cleaner.setMail(cursor.getString(mailIndex));
                    cleaner.setPassword(cursor.getString(passwordIndex));
                    cleaner.setFirstName(cursor.getString(firstNameIndex));
                    cleaner.setSecondName(cursor.getString(secondNameIndex));
                    cleaner.setPhoneNumber(cursor.getString(phoneNumberIndex));
                    cleaner.setUnivers(cursor.getString(universIndex));
                    cleaner.setAge(cursor.getString(ageIndex));
                    cleaner.setGend(cursor.getString(gendIndex));
                    cleaner.setHobi(cursor.getString(hobiIndex));
                    cleaner.setMevki(cursor.getString(mevkiIndex));
                    cleaner.setFakulte(cursor.getString(fakulteIndex));
                    cleaner.setBolum(cursor.getString(bolumIndex));
                    cleaner.setSinif(cursor.getString(sinifIndex));
                }
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cleaner;
    }


    //Tüm Kullanıcıları burada tutuyorum
    public List<Cleaner> getAllCleaners() {
        List<Cleaner> cleanerList = new ArrayList<>();

        try {
            String[] columns = {"mail", "password", "firstName", "secondName", "phoneNumber",
                    "univers", "age", "gend", "hobi", "mevki", "fakulte", "bolum", "sinif"};

            Cursor cursor = db.query("cleanerInfo", columns, null, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Cleaner cleaner = new Cleaner();
                    int mailIndex = cursor.getColumnIndex("mail");
                    int passwordIndex = cursor.getColumnIndex("password");
                    int firstNameIndex = cursor.getColumnIndex("firstName");
                    int secondNameIndex = cursor.getColumnIndex("secondName");
                    int phoneNumberIndex = cursor.getColumnIndex("phoneNumber");
                    int universIndex = cursor.getColumnIndex("univers");
                    int ageIndex = cursor.getColumnIndex("age");
                    int gendIndex = cursor.getColumnIndex("gend");
                    int hobiIndex = cursor.getColumnIndex("hobi");
                    int mevkiIndex = cursor.getColumnIndex("mevki");
                    int fakulteIndex = cursor.getColumnIndex("fakulte");
                    int bolumIndex = cursor.getColumnIndex("bolum");
                    int sinifIndex = cursor.getColumnIndex("sinif");

                    if (mailIndex != -1 && passwordIndex != -1 && firstNameIndex != -1 && secondNameIndex != -1
                            && phoneNumberIndex != -1 && universIndex != -1 && ageIndex != -1 && gendIndex != -1
                            && hobiIndex != -1 && mevkiIndex != -1 && fakulteIndex != -1 && bolumIndex != -1
                            && sinifIndex != -1) {
                        cleaner.setMail(cursor.getString(mailIndex));
                        cleaner.setPassword(cursor.getString(passwordIndex));
                        cleaner.setFirstName(cursor.getString(firstNameIndex));
                        cleaner.setSecondName(cursor.getString(secondNameIndex));
                        cleaner.setPhoneNumber(cursor.getString(phoneNumberIndex));
                        cleaner.setUnivers(cursor.getString(universIndex));
                        cleaner.setAge(cursor.getString(ageIndex));
                        cleaner.setGend(cursor.getString(gendIndex));
                        cleaner.setHobi(cursor.getString(hobiIndex));
                        cleaner.setMevki(cursor.getString(mevkiIndex));
                        cleaner.setFakulte(cursor.getString(fakulteIndex));
                        cleaner.setBolum(cursor.getString(bolumIndex));
                        cleaner.setSinif(cursor.getString(sinifIndex));

                        cleanerList.add(cleaner);
                    }
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cleanerList;
    }

    //Burada sadece Admin'e ozel girilen tüm kullanıcıların kayıtlarını silme islemi yapılıyor
    public void deleteAllUsers() {
        try {
            db.delete("cleanerInfo", null, null);
            Log.d("DB_OPERATION", "Tüm kullanıcılar silindi.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB_OPERATION", "Tüm kullanıcıları silme hatası: " + e.getMessage());
        }
    }


    //SQL sorgusu kullanarak filtrelenmiş kayıtları getirir.
    public List<Cleaner> getAllCleanersWithQuery(String query) {
        List<Cleaner> cleanerList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Cleaner cleaner = new Cleaner();
                    int mailIndex = cursor.getColumnIndex("mail");
                    int passwordIndex = cursor.getColumnIndex("password");
                    int firstNameIndex = cursor.getColumnIndex("firstName");
                    int secondNameIndex = cursor.getColumnIndex("secondName");
                    int phoneNumberIndex = cursor.getColumnIndex("phoneNumber");
                    int universIndex = cursor.getColumnIndex("univers");
                    int ageIndex = cursor.getColumnIndex("age");
                    int gendIndex = cursor.getColumnIndex("gend");
                    int hobiIndex = cursor.getColumnIndex("hobi");
                    int mevkiIndex = cursor.getColumnIndex("mevki");
                    int fakulteIndex = cursor.getColumnIndex("fakulte");
                    int bolumIndex = cursor.getColumnIndex("bolum");
                    int sinifIndex = cursor.getColumnIndex("sinif");

                    if (mailIndex != -1 && passwordIndex != -1 && firstNameIndex != -1 && secondNameIndex != -1
                            && phoneNumberIndex != -1 && universIndex != -1 && ageIndex != -1 && gendIndex != -1
                            && hobiIndex != -1 && mevkiIndex != -1 && fakulteIndex != -1 && bolumIndex != -1
                            && sinifIndex != -1) {
                        cleaner.setMail(cursor.getString(mailIndex));
                        cleaner.setPassword(cursor.getString(passwordIndex));
                        cleaner.setFirstName(cursor.getString(firstNameIndex));
                        cleaner.setSecondName(cursor.getString(secondNameIndex));
                        cleaner.setPhoneNumber(cursor.getString(phoneNumberIndex));
                        cleaner.setUnivers(cursor.getString(universIndex));
                        cleaner.setAge(cursor.getString(ageIndex));
                        cleaner.setGend(cursor.getString(gendIndex));
                        cleaner.setHobi(cursor.getString(hobiIndex));
                        cleaner.setMevki(cursor.getString(mevkiIndex));
                        cleaner.setFakulte(cursor.getString(fakulteIndex));
                        cleaner.setBolum(cursor.getString(bolumIndex));
                        cleaner.setSinif(cursor.getString(sinifIndex));

                        cleanerList.add(cleaner);
                    }
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cleanerList;
    }

    // Kullanıcıyı silen metot
    public boolean deleteCleaner(String email) {
        try {
            // Silme işlemi için gerekli SQL sorgusunu oluştur
            String whereClause = "mail = ?";
            String[] whereArgs = {email};

            // SQLiteDatabase üzerinden delete işlemini gerçekleştir
            int result = db.delete("cleanerInfo", whereClause, whereArgs);

            // Silme işlemi başarılı ise result değeri pozitif olacaktır
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // Silme işlemi sırasında bir hata oluştu
            return false;
        }
    }

}
