package com.thrashed.lubimec_crm.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.format.DateUtils;
import android.util.Log;

import com.thrashed.lubimec_crm.Fragments.RecordFragment;
import com.thrashed.lubimec_crm.MainActivity;

import static com.thrashed.lubimec_crm.utils.timeUtils.DAY_MILLIS;


public class recordAdapter extends FragmentPagerAdapter {

    private final Context context;
    private Bundle bundle;

    public recordAdapter(FragmentManager fm, Bundle bundle, Context context)
    {

        super(fm);
        this.bundle = bundle;
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        bundle = new Bundle();
        final String date = DateUtils.formatDateTime(context,
                position*DAY_MILLIS,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        bundle.putString("date",date);
        Log.d("asd", "getItem: ");
        RecordFragment recfrag = new RecordFragment();
        recfrag.setArguments(bundle);
        return recfrag;
    }

    @Override
    public int getCount() {
        return  Integer.MAX_VALUE;


    }
}





