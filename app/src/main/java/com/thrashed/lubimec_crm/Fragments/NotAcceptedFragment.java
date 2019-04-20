package com.thrashed.lubimec_crm.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thrashed.lubimec_crm.R;

import butterknife.ButterKnife;

public class NotAcceptedFragment extends DialogFragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.not_accepted_fragment, container, false);

        ButterKnife.bind(this, rootView);





        return rootView;
    }
}
