package com.bridge.pmt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bridge.pmt.R;
import com.bridge.pmt.activities.SignInActivity;
import com.bridge.pmt.helpers.SharedPrefManager;
import com.bridge.pmt.models.HourDetail;


public class AccountFragment extends Fragment {


    private EditText editTextName, editTextEmail, editTextPassword;
    private RadioGroup radioGender;
    FloatingActionButton exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_account, container, false);

        exit = (FloatingActionButton) getActivity().findViewById(R.id.logout);



        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Account");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
            }
        });

    }

}
