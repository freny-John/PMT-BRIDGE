package com.bridge.pmt.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge.pmt.R;
import com.bridge.pmt.activities.SignInActivity;
import com.bridge.pmt.adapters.HoursAdapter;
import com.bridge.pmt.helpers.ConnectivityReceiver;
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


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.layout_dialog, null);
                alertDialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = alertDialogBuilder.create();

                final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                final TextView title = (TextView) dialogView.findViewById(R.id.diatitle);
                final TextView msg = (TextView) dialogView.findViewById(R.id.textdialog);
                title.setText("Logout");
                msg.setText("Are you sure you want to logout from this app?");
                final Button done = (Button) dialogView.findViewById(R.id.submit);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPrefManager.getInstance(getActivity()).logout();
                        startActivity(new Intent(getActivity(), SignInActivity.class));
                        getActivity().finish();


                        alertDialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();


                    }
                });



                alertDialog.show();




            }
        });

    }

}
