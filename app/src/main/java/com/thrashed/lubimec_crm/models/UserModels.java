package com.thrashed.lubimec_crm.models;

public class UserModels{
private String username;
private String password;
private String fio;
private String role;


    public String getFio() {
        return fio;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
        }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

