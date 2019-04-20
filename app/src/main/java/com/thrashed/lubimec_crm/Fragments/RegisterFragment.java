package com.thrashed.lubimec_crm.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.thrashed.lubimec_crm.DAO.DBClient;
import com.thrashed.lubimec_crm.DAO.Record;
import com.thrashed.lubimec_crm.MainActivity;
import com.thrashed.lubimec_crm.R;
import com.thrashed.lubimec_crm.network.Api;
import com.thrashed.lubimec_crm.network.Auth;
import com.thrashed.lubimec_crm.network.Register;
import com.thrashed.lubimec_crm.network.RegistrationBody;
import com.thrashed.lubimec_crm.network.RegistrationResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends DialogFragment {


@BindView(R.id.register_password)
EditText reg_password;
@BindView(R.id.register_username)
EditText reg_username;
@BindView(R.id.register_fio)
EditText reg_fio;
@BindView(R.id.register_role)
Spinner reg_role;
@BindView(R.id.errortext_reg)
TextView reg_errortext;
@BindView(R.id.reg_progressbar)
ProgressBar progress_bar;





    public RegisterFragment() {
    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.register_fragment, container, false);

        ButterKnife.bind(this, rootView);
        progress_bar.setVisibility(ProgressBar.INVISIBLE);





        return rootView;
    }

@OnClick(R.id.back_to_login)
    public void ONClick(View view){
    RegisterFragment.this.dismiss();

}

@OnClick(R.id.reg_but)
    public void Onclick(View v){
    if (reg_username.getText().toString().isEmpty()){
        reg_errortext.setText("Введите Ник");
    }
    else if (reg_password.getText().toString().isEmpty()){
        reg_errortext.setText("Введите пароль!");
    }
    else if (reg_fio.getText().toString().isEmpty()){
        reg_errortext.setText("Введите ФИО!");
    }
    else if (reg_role.getSelectedItem().toString().equals("Выберите роль")){
        reg_errortext.setText("Выберите должность!");
    }
    else if (reg_username.getText().toString().isEmpty() & reg_username.getText().toString().isEmpty()) {
        reg_errortext.setText("Введите логин и пароль!");
    }
    else {
        progress_bar.setVisibility(ProgressBar.VISIBLE);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        prefs.edit().putString("username", reg_username.getText().toString()).apply();
        prefs.edit().putString("username", reg_password.getText().toString()).apply();
        RegistrationBody.RegisterBody body = new RegistrationBody.RegisterBody(
                reg_username
                        .getText()
                        .toString(),
                reg_password
                        .getText()
                        .toString(),
                reg_fio
                        .getText()
                        .toString(),
                reg_role
                        .getSelectedItem()
                        .toString());


        Register api = Api.create(Register.class);

        Call<RegistrationResponse> call = api.registrationUser(reg_username.getText().toString(),reg_password.getText().toString(),reg_fio.getText().toString(),reg_role.getSelectedItem().toString());
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().account_status){
                        NotAcceptedFragment reg_frag = new NotAcceptedFragment();
                        reg_frag.show(getActivity().getSupportFragmentManager(), "custom");
                        reg_frag.setCancelable(false);
                        reg_frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
                    }
                    prefs.edit().putString("token", response.body().token).apply();

                } else {
                    reg_errortext.setText("Не удалось!");
                    Snackbar.make(getView(), "Этот ник существует!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.d("logd",response.toString());
                    progress_bar.setVisibility(ProgressBar.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                reg_errortext.setText("No!");
                progress_bar.setVisibility(ProgressBar.INVISIBLE);
            }
        });}

}
}












