package com.thrashed.lubimec_crm.DAO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Record implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "clientfio")
    private String client_fio;

    @ColumnInfo(name = "clientproblem")
    private String client_problem;

    public String getClient_problem() {
        return client_problem;
    }

    public void setClient_problem(String client_problem) {
        this.client_problem = client_problem;
    }

    @ColumnInfo(name = "clientpetname")
    private String client_petname;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "animal")
    private String animal;

    @ColumnInfo(name = "sex")
    private String sex;

    @ColumnInfo(name = "vet")
    private String vet;

    @ColumnInfo(name = "time")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVet() {
        return vet;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getClient_petname() {
        return client_petname;
    }

    public void setClient_petname(String client_petname) {
        this.client_petname = client_petname;
    }

    public String getClient_fio() {

        return client_fio;
    }

    public void setClient_fio(String client_fio) {
        this.client_fio = client_fio;
    }
}
