package com.thrashed.lubimec_crm.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thrashed.lubimec_crm.Adapters.RV_Adapter;
import com.thrashed.lubimec_crm.DAO.DBClient;
import com.thrashed.lubimec_crm.DAO.Record;
import com.thrashed.lubimec_crm.MainActivity;
import com.thrashed.lubimec_crm.R;
import com.thrashed.lubimec_crm.models.RV_Records;
import com.thrashed.lubimec_crm.utils.RecyclerItemClickListner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class RecordFragment extends Fragment {

    private List<RV_Records> rv_records;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottom_sheet;
    @BindView(R.id.delete_item)
    TextView delete_text;
    RV_Adapter adapter;

    Bundle bundle = new Bundle();



    public RecordFragment() {
    }




    public static RecordFragment newInstance(int sectionNumber) {

        RecordFragment fragment = new RecordFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView =
                inflater.inflate(R.layout.record_fragment, container, false);

        ButterKnife.bind(this, rootView);



                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        getTasks();

        rv.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), rv, new RecyclerItemClickListner.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(rv.getAlpha() != 1){
                    rv.setAlpha(1);
                    bottom_sheet.setVisibility(View.GONE);
                }

            }

            @Override
            public void onItemLongClick(View view, final int position) {

                bottom_sheet.setVisibility(View.VISIBLE);

                rv.setAlpha((float) 0.5);
                delete_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adapter.notifyDataSetChanged();
                        rv.setAlpha(1);
                        bottom_sheet.setVisibility(View.GONE);

                    }
                });



            }
        }));


        rv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        // Context Context = getContext();


        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(boolean event) {
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void getTasks() {

       Calendar cal = Calendar.getInstance();

        final String date = DateUtils.formatDateTime(getContext(),
                cal.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);

        final String datte = this.getArguments().getString("date");
        Log.d("asd",this.getArguments().getString("date"));

        class GetTasks extends AsyncTask<Void, Void, List<Record>> {



            @Override
            protected List<Record> doInBackground(Void... voids) {
                List<Record> recordList = DBClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .recordDao()
                        .getRV_ALL(datte);
                Log.d("asd","Ебашу");
                return recordList;

            }

            @Override
            protected void onPostExecute(List<Record> tasks) {
                super.onPostExecute(tasks);
                for (int i = 0; i < tasks.size(); i++){

                    Log.d("asd",String.valueOf(tasks.get(i)));

                }
                Log.d("asd","Готов хлопчик ебать!");
                RV_Adapter adapter = new RV_Adapter(tasks);

                rv.setAdapter(adapter);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
    private void deleteTask(final Record task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DBClient.getInstance(getContext()).getAppDatabase()
                        .recordDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();


            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}



