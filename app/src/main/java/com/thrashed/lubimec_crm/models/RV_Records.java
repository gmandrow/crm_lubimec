package com.thrashed.lubimec_crm.models;

public class RV_Records {

    public String drv_fio;
    public String drv_animal;
    public String drv_problem;
    public String drv_time;
    public String drv_petname;
    public String drv_veterinarian;
    
    public RV_Records(String fio, String animal, String problem, String time, String petname, String veterinarian) {
        this.drv_fio = fio;
        this.drv_animal = animal;
        this.drv_problem = problem;
        this.drv_time = time;
        this.drv_petname = petname;
        this.drv_veterinarian = veterinarian;
    }
}

