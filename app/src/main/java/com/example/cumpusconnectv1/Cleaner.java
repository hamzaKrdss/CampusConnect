package com.example.cumpusconnectv1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class Cleaner {

    private String firstName;
    private String secondName;
    private String mail;
    private String phoneNumber;
    private String password;
    private String univers;
    private String age;
    private String gend;

    //Alt taraf için bir değişiklilik yapabilirim.

    private String hobi;
    private String mevki;
    private String fakulte;
    private String bolum;
    private String sinif;

    private String registrationDate;

    public Cleaner(String firstName, String secondName, String mail, String phoneNumber,
                   String password, String univers, String age, String gend, String hobi,
                   String mevki, String fakulte, String bolum, String sinif) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.univers = univers;
        this.age = age;
        this.gend = gend;
        this.hobi = hobi;
        this.mevki = mevki;
        this.fakulte = fakulte;
        this.bolum = bolum;
        this.sinif = sinif;
        this.registrationDate = getCurrentDateTime();
    }
    public Cleaner(){

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Cleaner cleaner = (Cleaner) obj;

        return getMail().equals(cleaner.getMail()) && getPassword().equals(cleaner.getPassword());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnivers() {
        return univers;
    }

    public void setUnivers(String univers) {
        this.univers = univers;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGend() {
        return gend;
    }

    public void setGend(String gend) {
        this.gend = gend;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }

    public String getMevki() {
        return mevki;
    }

    public void setMevki(String mevki) {
        this.mevki = mevki;
    }

    public String getFakulte() {
        return fakulte;
    }

    public void setFakulte(String fakulte) {
        this.fakulte = fakulte;
    }

    public String getBolum() {
        return bolum;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }
    public String getRegistrationDate() {
        if (registrationDate == null) {
            registrationDate = getCurrentDateTime();
        }
        return registrationDate;
    }
    public String getRegistrationYear() {
        if (registrationDate != null && registrationDate.length() >= 4) {
            return registrationDate.substring(0, 4);
        } else {
            return "";
        }
    }

}
