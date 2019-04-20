package com.thrashed.lubimec_crm.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thrashed.lubimec_crm.R;
import com.thrashed.lubimec_crm.models.UserModels;
import com.thrashed.lubimec_crm.network.Api;
import com.thrashed.lubimec_crm.network.Auth;
import com.thrashed.lubimec_crm.network.RegistrationBody;
import com.thrashed.lubimec_crm.network.RegistrationResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends DialogFragment{

    @BindView(R.id.login_username)
    EditText login_username;

    @BindView(R.id.login_password)
    EditText login_password;

    @BindView(R.id.error_text)
    TextView error_text;

    @BindView(R.id.indeterminateBar)
    ProgressBar progress_bar;


    Activity activity = this.getActivity();

    private static final String ARG_SECTION_NUMBER = "section_number";

    public LoginFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LoginFragment newInstance(int sectionNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.login_fragment, container, false);

        ButterKnife.bind(this, rootView);
        progress_bar.setVisibility(ProgressBar.INVISIBLE);




        return rootView;
    }

    @OnClick(R.id.register)
    public void Onclick(View v){
        RegisterFragment reg_frag = new RegisterFragment();
        reg_frag.show(getActivity().getSupportFragmentManager(), "custom");
        reg_frag.setCancelable(false);
        reg_frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }


    @OnClick(R.id.login_but)
    public void onClick(View v){
        if (login_username.getText().toString().isEmpty()){
            error_text.setText("Введите Ник");
        }
        else if (login_password.getText().toString().isEmpty()){
            error_text.setText("Введите пароль!");

        }
        else if (login_password.getText().toString().isEmpty() & login_username.getText().toString().isEmpty()) {
            error_text.setText("Введите логин и пароль!");
        }
        else {
            progress_bar.setVisibility(ProgressBar.VISIBLE);
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            prefs.edit().putString("username", login_username.getText().toString()).apply();
            prefs.edit().putString("username", login_password.getText().toString()).apply();
            RegistrationBody body = new RegistrationBody(login_username
                    .getText()
                    .toString(),
                    login_password
                            .getText()
                            .toString());


            Auth api = Api.create(Auth.class);

            Call<RegistrationResponse> call = api.registerUser(login_username.getText().toString(),login_password.getText().toString());
            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.isSuccessful()) {

                        prefs.edit().putString("token", response.body().token).apply();
                        Log.d("logd",response.body().token);
                        if (!response.body().account_status){
                            NotAcceptedFragment reg_frag = new NotAcceptedFragment();
                            reg_frag.show(getActivity().getSupportFragmentManager(), "custom");
                            reg_frag.setCancelable(false);
                            reg_frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
                        }

                        LoginFragment.this.dismiss();
                    } else {
                        error_text.setText("Не удалось!");
                        Snackbar.make(getView(), "Неправильный логин или пароль!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Log.d("logd",response.toString());
                        progress_bar.setVisibility(ProgressBar.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Snackbar.make(getView(), "Нет!" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    progress_bar.setVisibility(ProgressBar.INVISIBLE);

                }
            });



        }

    }

}
