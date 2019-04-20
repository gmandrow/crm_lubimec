package com.thrashed.lubimec_crm;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.thrashed.lubimec_crm.Fragments.NewCustomerFragment;
import com.thrashed.lubimec_crm.Adapters.recordAdapter;
import com.thrashed.lubimec_crm.Fragments.LoginFragment;
import com.thrashed.lubimec_crm.Fragments.RecordFragment;
import com.thrashed.lubimec_crm.models.NewCustomerRecord;
import com.thrashed.lubimec_crm.network.Auth;
import com.thrashed.lubimec_crm.utils.timeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

import static com.thrashed.lubimec_crm.utils.timeUtils.DAY_MILLIS;

public class MainActivity extends AppCompatActivity implements CompactCalendarView.CompactCalendarAnimationListener, CompactCalendarView.CompactCalendarViewListener {

    @BindView(R.id.tab_week)
    LinearLayout mWeek;
    @BindViews({R.id.tab_week_mon, R.id.tab_week_tue, R.id.tab_week_wed, R.id.tab_week_thu,
            R.id.tab_week_fri, R.id.tab_week_sat, R.id.tab_week_sun})
    List<TextView> mWeekTab;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView mCalendar;

    @BindView(R.id.timetable_toolbar)
    Toolbar mToolbar;

    private recordAdapter recordAdapter;
    Auth auth;

    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       /* if (prefs.getBoolean("hasToken", false)){

            LoginFragment login_frag = new LoginFragment();
            login_frag.show(getSupportFragmentManager(), "custom");
            login_frag.setCancelable(false);
            login_frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

            //Intent intent = new Intent(MainActivity.this, FirstInitActivity.class);
            //startActivity(intent);

            prefs.edit().putBoolean("hasToken", true).commit();
        }*/

        setSupportActionBar(mToolbar);
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setUseThreeLetterAbbreviation(true);
        mCalendar.setAnimationListener(this);
        mCalendar.setListener(this);
        mCalendar.setVisibility(View.GONE);
        mCalendar.hideCalendar();
        recordAdapter = new recordAdapter(getSupportFragmentManager(), bundle,getApplicationContext());
        mViewPager.setAdapter(recordAdapter);
        mViewPager.setCurrentItem(timeUtils.dayNumber());
        mViewPager.setOffscreenPageLimit(7);







    }
    @OnPageChange(value = R.id.container, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        int b = 0;
        long pageritem = mViewPager.getCurrentItem();
        Date datePage = new Date( pageritem * 86400000) ;
        Calendar c = Calendar.getInstance();
        c.setTime(datePage);
        Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        for (int l = 0; l < mWeekTab.size(); l++) {
            mWeekTab.get(l).setTextColor(getResources().getColor(R.color.white));

        }


        switch (dayOfWeek){
            case 1:
                b = 6;
                break;
            case 2:
                b = 0;
                break;
            case 3:
                b = 1;
                break;
            case 4:
                b = 2;
                break;
            case 5:
                b = 3;
                break;
            case 6:
                b = 4;
                break;
            case 7:
                b = 5;
                break;


        }

        mWeekTab.get(b).setTextColor(getResources().getColor(R.color.colorTAB));


        //RecordFragment frag = new RecordFragment();
        //final String date = DateUtils.formatDateTime(getApplicationContext(),
          //      c.getTimeInMillis(),
            //    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        //bundle.putString("date",date);
        //frag.setArguments(bundle);
        //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        NewCustomerFragment newCustomerFragment = new NewCustomerFragment();
        newCustomerFragment.show(getSupportFragmentManager(), "custom");
        newCustomerFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);




    }

    @OnClick({R.id.tab_week_mon, R.id.tab_week_tue, R.id.tab_week_wed, R.id.tab_week_thu,
            R.id.tab_week_fri, R.id.tab_week_sat, R.id.tab_week_sun})
    public void onWeekTabClick(TextView view) {
        for (int i = 0; i < mWeekTab.size(); i++)
            if (mWeekTab.get(i).getId() == view.getId()) {
                for (int j = 0; j < mWeekTab.size(); j++)
                    if (mWeekTab.get(j).isSelected()) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + i - j, true);
                        break;
                    }

                break;
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()){
            case R.id.action_calendar:
                if (mCalendar.isShown()){
                    mCalendar.setVisibility(View.GONE);
                    mCalendar.hideCalendar();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_calendar));
                }
                else {
                    mCalendar.setVisibility(View.VISIBLE);
                    mCalendar.showCalendar();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_eject));
                }
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onOpened() {

    }

    @Override
    public void onClosed() {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onDayClick(Date dateClicked) {

        int date =  ((int)dateClicked.getTime()/(int) DAY_MILLIS);
        mViewPager.setCurrentItem( (int) (dateClicked.getTime()/ DAY_MILLIS)+1);


        Toast.makeText(this, String.valueOf(dateClicked), Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onMonthScroll(Date firstDayOfNewMonth) {

    }


    public void getData (String fio, String problem, String petname, String date, String animal, String sex, String veterinarian){
        Toast.makeText(this, "recieved", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        ArrayList<String> new_customer = new ArrayList<>();
        new_customer.add(fio);
        new_customer.add(problem);
        new_customer.add(petname);
        new_customer.add(date);
        new_customer.add(animal);
        new_customer.add(sex);
        new_customer.add(veterinarian);
        bundle.putStringArrayList("newcustomer",new_customer);
        RecordFragment frag = new RecordFragment();
        frag.setArguments(bundle);
    }

}
