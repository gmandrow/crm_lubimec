package com.thrashed.lubimec_crm.network;

public class RegistrationBody{
    private String user;
    private String password;



    public RegistrationBody(String login, String password){
        this.user = login;
        this.password = password;

    }

    public static class RegisterBody{
        private String user_reg;
        private String password_reg;
        private String fio_reg;
        private String role_reg;

        public RegisterBody (String user_reg, String password_reg, String fio_reg, String role_reg){
            this.user_reg = user_reg;
            this.password_reg = user_reg;
            this.fio_reg = fio_reg;
            this.role_reg = role_reg;
        }
    }

}

