package com.thrashed.lubimec_crm.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thrashed.lubimec_crm.DAO.DBClient;
import com.thrashed.lubimec_crm.DAO.Record;
import com.thrashed.lubimec_crm.MainActivity;
import com.thrashed.lubimec_crm.R;
import com.thrashed.lubimec_crm.models.RV_Records;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewCustomerFragment extends DialogFragment {




    Calendar dateAndTime=Calendar.getInstance();

    @BindView(R.id.record_date_time)
    EditText record_date_time;

    @BindView(R.id.record_fio)
    EditText record_fio;

    @BindView(R.id.record_problem)
    EditText record_problem;

    @BindView(R.id.record_petname)
    EditText record_petname;

    @BindView(R.id.record_animal)
    Spinner record_animal;

    @BindView(R.id.record_vet)
    Spinner record_vet;

    @BindView(R.id.record_sex)
    Spinner record_sex;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.new_customer_fragment, container, false);

        ButterKnife.bind(this, rootView);
        setInitialDateTime();

        return rootView;


    }

    @OnClick(R.id.commit_record_button)
    public void CommitClick(View v){

        saveTask();
        NewCustomerFragment.this.dismiss();


    }


@OnClick(R.id.record_date_time)
        public void DateClick(View v){
    new DatePickerDialog(getContext(),
            R.style.TimePickerTheme, d,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH))
            .show();

}


DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setInitialDateTime();
            new TimePickerDialog(getContext(),
                    R.style.TimePickerTheme,
                    t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();

        }
    };
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

             dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();

        }
    };

    private void setInitialDateTime() {

        record_date_time.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
       
    }
    private void saveTask() {
        final String fio = record_fio.getText().toString().trim();
        final String problem = record_problem.getText().toString().trim();
        final String petname = record_petname.getText().toString().trim();
        final String date = DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        final String time =  DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME);
        final String animal = record_animal.getSelectedItem().toString().trim();
        final String sex = record_sex.getSelectedItem().toString().trim();
        final String vet = record_vet.getSelectedItem().toString().trim();



        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Record task = new Record();
                task.setClient_fio(fio);
                task.setClient_petname(petname);
                task.setClient_problem(problem);
                task.setDate(date);
                task.setAnimal(animal);
                task.setSex(sex);
                task.setVet(vet);
                task.setTime(time);

                //adding to database
                DBClient.getInstance(getContext()).getAppDatabase()
                        .recordDao()
                        .insert(task);
                Log.d("asd","Труженник ебать!");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("asd","Готово Ебать!");
                EventBus.getDefault().post(true);

            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}
