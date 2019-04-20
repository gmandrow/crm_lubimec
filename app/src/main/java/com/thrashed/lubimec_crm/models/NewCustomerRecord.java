package com.thrashed.lubimec_crm.models;

public class NewCustomerRecord {
    public String ncr_fio;
    public String ncr_animal;
    public String ncr_problem;
    public String ncr_time;
    public String ncr_petname;
    public String ncr_veterinarian;
    public String ncr_sex;

    public NewCustomerRecord(String s, String fio, String animal, String problem, String time, String petname, String veterinarian) {
        this.ncr_fio = fio;
        this.ncr_animal = animal;
        this.ncr_problem = problem;
        this.ncr_time = time;
        this.ncr_petname = petname;
        this.ncr_veterinarian = veterinarian;
        this.ncr_sex = ncr_sex;
    }
}

