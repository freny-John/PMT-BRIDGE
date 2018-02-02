package com.bridge.pmt.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge.pmt.MainActivity;
import com.bridge.pmt.R;
import com.bridge.pmt.adapters.HoursAdapter;
import com.bridge.pmt.api.APIService;
import com.bridge.pmt.api.APIUrl;
import com.bridge.pmt.helpers.ConnectivityReceiver;
import com.bridge.pmt.helpers.DecimalPicker;
import com.bridge.pmt.helpers.RecyclerItemTouchHelper;
import com.bridge.pmt.helpers.SharedPrefManager;
import com.bridge.pmt.models.Activity;
import com.bridge.pmt.models.BaseResponse;
import com.bridge.pmt.models.HourDetail;
import com.bridge.pmt.models.ProjectList;
import com.bridge.pmt.models.WeekReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class HoursFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    RecyclerView recyclerView;
    LinearLayout empty;
    View rootView;
    FloatingActionButton btn;
    PopupWindow popupWindow;
    LayoutInflater inflater;
    View popupView;
    List<Activity> activityList = new ArrayList<>();
    List<ProjectList> projectList = new ArrayList<>();
    String token;
    int userId;
    private HorizontalCalendar horizontalCalendar;
    private HoursAdapter adapter;
    private List<WeekReport> weekReport;
    private List<HourDetail> currenthourDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (ConnectivityReceiver.isConnected()) {

            getProject_ActivityList();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_hours, container, false);
        createUI(rootView);
        getActivity().setTitle("Hours");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPrefManager.KEY_USER_TOKEN, null);
        userId = sharedPreferences.getInt(SharedPrefManager.KEY_USER_ID, 0);


        //Create Recyclerview for the weekly reports
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHours);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));







        if (ConnectivityReceiver.isConnected()) {

            getWeeklyHourReport();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }





        Log.i("SPDATA", "projectList " + projectList.size() + " reactivityList " + activityList.size());
        for (ProjectList member1 : projectList) {
            Log.i("SPDATA_project:", member1.toString());
        }
        for (Activity member2 : activityList) {
            Log.i("SPDATA_activity:", member2.toString());
        }
        btn = (FloatingActionButton) getActivity().findViewById(R.id.add_reg);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Calendar endDate = Calendar.getInstance();

        endDate.setTime(c.getTime());
        endDate.add(Calendar.DAY_OF_YEAR, 6);
        empty = (LinearLayout) rootView.findViewById(R.id.empty);
        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(c, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(14f, 24f, 14f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
//                Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(), position + " is selected!", Toast.LENGTH_SHORT).show();
//                Log.i("DATEASSAS" ,weekReport.get(position+2).getDate().trim());
//                Log.i("DATEASSAS" ,DateFormat.format("yyyy-mm-dd",date).toString());
                currenthourDetails.clear();
               if(weekReport.size()>0) { if (position <= 8 && position >= 2) {
                    currenthourDetails.addAll(weekReport.get(position - 2).getHourDetails());
                }
                adapter.notifyDataSetChanged();
                Log.i("ADAPTERADD ", "1");

                if (currenthourDetails.size() > 0) {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }}

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());
                final int pos = horizontalCalendar.getSelectedDatePosition();
                Date current = null;
                Date clciked = null;
                try {
                     current = df.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    clciked = df.parse( weekReport.get(pos - 2).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.i("ADDDDDD"," formattedDate "+formattedDate+" week "+ weekReport.get(pos - 2).getDate());

                assert current != null;
                if (current.compareTo(clciked) >= 0)
                {
                   // cuurent is after or on clicked
                                    popIt(new HourDetail());
//                    Toast.makeText(getActivity(), "Good", Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(getActivity(), "You cannot enter data for future dates", Toast.LENGTH_LONG).show();

                }


            }
        });

        return rootView;
    }

    private void getProject_ActivityList() {
        activityList = SharedPrefManager.getInstance(getActivity()).pullactivityList();
        projectList = SharedPrefManager.getInstance(getActivity()).pullprojectList();

        if (activityList.size() <= 0 || projectList.size() <= 0) {
            if (token != null) {


                //Web service to get the activity list
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Geting Activities...");
                progressDialog.setCancelable(false);

                progressDialog.show();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);
                Call<BaseResponse> call = service.getUserActivity(token);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();

                        Log.e("SERVER_RESPONSE_data", "ACTIVTY :" + String.valueOf((response.body().getData().getActivity())));

                        List<Activity> activityList = response.body().getData().getActivity();

                        // activity list to sharedpreferences
                        SharedPrefManager.getInstance(getActivity()).pushactivityList(activityList);

                        Log.e("ACTIVITY_LIST ", String.valueOf(activityList));

                        // get activitylist from sharedpreferences
                        Log.e("SP_LIST ", String.valueOf(SharedPrefManager.getInstance(getActivity()).pullactivityList()));
//                        Toast.makeText(getActivity(), "" + response.body().getData().getActivity(), Toast.LENGTH_LONG).show();

                        if (String.valueOf(response.body().getStatus()).equals("1")) {


//                        SharedPrefManager.getInstance(getApplicationContext()).userActivity(response.body().getData().getUser());
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                        finish();

                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                call = service.getUserProjects(token, userId);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();

                        Log.e("SERVER_RESPONSE_data", "pROJECT LIST " + String.valueOf((response.body().getData().getProjectList())));

                        List<ProjectList> projectList = response.body().getData().getProjectList();

                        // activity list to sharedpreferences
                        SharedPrefManager.getInstance(getActivity()).pushprojectList(projectList);

                        Log.e("PROJECT_LIST ", String.valueOf(projectList));

                        // get activitylist from sharedpreferences
                        Log.e("SP_LIST ", String.valueOf(SharedPrefManager.getInstance(getActivity()).pullactivityList()));
//                        Toast.makeText(getActivity(), "" + response.body().getData().getProjectList(), Toast.LENGTH_LONG).show();

                        if (String.valueOf(response.body().getStatus()).equals("1")) {


//                        SharedPrefManager.getInstance(getApplicationContext()).userActivity(response.body().getData().getUser());
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                        finish();

                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }

        }


    }

    private void createUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        weekReport = new ArrayList<>();
        weekReport.clear();
        currenthourDetails = new ArrayList<>();
        currenthourDetails.clear();
        adapter = new HoursAdapter(currenthourDetails, getContext(), HoursFragment.this);

        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void getWeeklyHourReport() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        // Toast.makeText(getActivity(),""+ userId, Toast.LENGTH_LONG).show();

        Call<BaseResponse> call = service.getWeekReport(token, userId);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();

//                Log.v("SERVER-RESPONSE", String.valueOf(response.body().getToken() ));

                Log.e("SERVER-RESPONSE-data", String.valueOf((response.body())));
                Log.e("SERVER-RESPONSE-data", String.valueOf((response.body().getData().getWeekReport())));
//                Toast.makeText(getApplicationContext(),response.body().getToken()  , Toast.LENGTH_LONG).show();
                if (response.body().getStatus().equals(1)) {
                    weekReport = response.body().getData().getWeekReport();
                    currenthourDetails.clear();
                    int pos = horizontalCalendar.getSelectedDatePosition();
                    if (pos <= 8 && pos >= 2) {
                        currenthourDetails.addAll(weekReport.get(pos - 2).getHourDetails());
                    }

                    adapter.notifyDataSetChanged();
                    Log.i("ADAPTERADD ", "1");

                    if (currenthourDetails.size() > 0) {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
//                   Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if (currenthourDetails.size() > 0) {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }


            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();

           Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onButtonShowPopupWindowClick(final HourDetail hourDetail, final String datedate, final Integer position) {

       // get a reference to the already created main layout

        // inflate the layout of the popup window
        inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);

        Button cancel = (Button) popupView.findViewById(R.id.cancel);
        final Button submit = (Button) popupView.findViewById(R.id.submit);
        TextView date = (TextView) popupView.findViewById(R.id.date);
        final TextView descp = (TextView) popupView.findViewById(R.id.descp);
        final DecimalPicker horizontalNumberPicker = (DecimalPicker) popupView.findViewById(R.id.horizontalNumberPicker);
        final Spinner projects_list = (Spinner) popupView.findViewById(R.id.projects_list);
        final Spinner activity_list = (Spinner) popupView.findViewById(R.id.activity_list);
        final CheckBox checkBox = (CheckBox) popupView.findViewById(R.id.checkBox);
        horizontalNumberPicker.setFormat("%.1f");//Weight format
//        horizontalNumberPicker.setOnValueChangeListener(new DecimalPicker.OnValueChangeListener() {
//
//            @Override
//            public void onValueChange(DecimalPicker picker, double oldValue, double newValue) {
//                //Do what you want to handle value change.
//            }
//        });
        List<String> activities = new ArrayList<>();
        activities.clear();
        List<String> projects = new ArrayList<>();
        projects.clear();

        for (ProjectList member1 : projectList) {
//            Log.i("SPDATA_project:", member1.toString());
            projects.add(member1.getProject().getName());
        }
        for (Activity member2 : activityList) {
//            Log.i("SPDATA_project:", member1.toString());
            activities.add(member2.getName());
        }

////        getLISTS();
// // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, activities);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, projects);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        activity_list.setAdapter(dataAdapter1);
        projects_list.setAdapter(dataAdapter2);
//        activity_list.setItems(activities);
//        projects_list.setItems(projects);

        date.setText(datedate);
        if (hourDetail.getProjId() > 0) {
            int position1 = getIndexofProject(hourDetail.getProjId(), projectList);
            projects_list.setSelection(position1);
        }

        if (!hourDetail.getActivity().isEmpty()) {
            int position2 = getIndexofActivity(hourDetail.getActivity(), activityList);
            activity_list.setSelection(position2);
        }
        if (hourDetail.getHours() > 0) {
            horizontalNumberPicker.setNumber(String.valueOf(hourDetail.getHours()));
        }

        if (!hourDetail.getDescription().isEmpty()) {
            descp.setText(hourDetail.getDescription());
        }
        Log.i("CHECVBOX", " VAL:  " + hourDetail.getExtraWork());
        if (hourDetail.getExtraWork() > 0) {
            checkBox.setChecked(true);

        } else {
            checkBox.setChecked(false);
        }

        if (hourDetail.getId() > 0) {
            submit.setText("Update");
        } else {
            submit.setText("Add");

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourDetail.setHours(Double.parseDouble(horizontalNumberPicker.getNumber()));
                hourDetail.setProjId(projectList.get(projects_list.getSelectedItemPosition()).getProjectId());
                hourDetail.setActivity(activityList.get(activity_list.getSelectedItemPosition()).getCode()); //IMPORTANT actity sending back will be CODE
                hourDetail.setDescription(descp.getText().toString().trim());
                if (checkBox.isChecked()) {
                    hourDetail.setExtraWork(1);
                    Log.i("CHECVBOX", " VAL:  " + hourDetail.getExtraWork());

                } else {
                    hourDetail.setExtraWork(0);
                    Log.i("CHECVBOX", " VAL:  " + hourDetail.getExtraWork());


                }

                if (hourDetail.getHours() > 0) {
                    if (hourDetail.getId() > 0) {

                        //UPDATE CALL


                        if (ConnectivityReceiver.isConnected()) {

                            updateHourReport(hourDetail);
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        //ADD CALL


                        if (ConnectivityReceiver.isConnected()) {

                            addHourReport(hourDetail, position);
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                        }


                    }
                } else {
                    Toast.makeText(getActivity(), "Enter Hours!", Toast.LENGTH_LONG).show();

                }
            }
        });

        // show the popup window
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);


    }

    private int getIndexofActivity(String activity, List<Activity> activityList) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activity.equals(activityList.get(i).getCode())) {
                return i;
            }
        }
        return 0;
    }

    public int getIndexofProject(Integer projId, List<ProjectList> projectList) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projId.equals(projectList.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }



    public void popIt(final HourDetail hourDetail) {
        if(popupWindow==null||!popupWindow.isShowing()){
            if(weekReport.size()>0) {
           final int pos = horizontalCalendar.getSelectedDatePosition();
        if (pos <= 8 && pos >= 2) {
//            Toast.makeText(getActivity(), "Position : " + pos + " Date :" + weekReport.get(pos - 2).getDate(), Toast.LENGTH_LONG).show();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onButtonShowPopupWindowClick(hourDetail, weekReport.get(pos - 2).getDate(), pos);

                }
            });

        }}else{
           Log.i("POPIT","No Value");

           Toast.makeText(getActivity(), "Please Refresh the hour list", Toast.LENGTH_LONG).show();

       }}
    }

//Web service to Add the hour report

    private void addHourReport(HourDetail hourDetail, Integer position) {

        String date = weekReport.get(position - 2).getDate();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Adding ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("ADDING ", " REPORT "
                + token + " " + "User Id:" + userId + " ACTIVITY " + hourDetail.getActivity() + " getHours " + hourDetail.getHours() + " getProjId " + hourDetail.getProjId() + " datedate " + date + " Description " + hourDetail.getDescription());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<BaseResponse> call = service.addHourReport(token, userId, date, hourDetail.getHours(), hourDetail.getProjId(), hourDetail.getActivity(), hourDetail.getDescription(), hourDetail.getExtraWork());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();

                Log.e("RESPONSE-ADD REPORT", String.valueOf((response.body())));

//                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (response.body().getStatus().equals(1)) {

                    //Refreshing the Project,Activity and Hour Report Lists


                    if (ConnectivityReceiver.isConnected()) {

                        getProject_ActivityList();
                        getWeeklyHourReport();
                        popupWindow.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                    }




                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.e(" ON failure", t.getMessage());

            }
        });


    }


//Web service to Update the hour report

    private void updateHourReport(HourDetail hourDetail) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating ...");
        progressDialog.setCancelable(false);

        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Log.i("CHECVBOX"," VAL CALL:  "+hourDetail.getExtraWork());


        Call<BaseResponse> call = service.updateHourReport(token, hourDetail.getPdate(), hourDetail.getHours(), hourDetail.getProjId(), hourDetail.getActivity(), hourDetail.getDescription(), hourDetail.getExtraWork(), hourDetail.getId());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();


                Log.e("RESPONSE-data", String.valueOf((response.body())));

//                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (response.body().getStatus().equals(1)) {




                    if (ConnectivityReceiver.isConnected()) {

                        //Refreshing the Project,Activity and Hour Report Lists
                        getProject_ActivityList();
                        getWeeklyHourReport();
                        popupWindow.dismiss();


                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                    }




                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }


    //Web service to Delete the hour report

    private void deleteHourReport(HourDetail hourDetail) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Deleting ...");
        progressDialog.setCancelable(false);

        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);



        Call<BaseResponse> call = service.deleteHourReport(token, hourDetail.getId());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();


                Log.e("RESPONSE-data", String.valueOf((response.body())));

//                Toast.makeText(getActivity(),response.body().getMessage()  , Toast.LENGTH_LONG).show();

                if (response.body().getStatus().equals(1)) {


                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
           });


    }


    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof HoursAdapter.ViewHolder0) {
            final HourDetail deletedItem = currenthourDetails.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            adapter.removeItem(viewHolder.getAdapterPosition());

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.layout_dialog, null);
            alertDialogBuilder.setView(dialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
            final TextView title = (TextView) dialogView.findViewById(R.id.diatitle);
            final TextView msg = (TextView) dialogView.findViewById(R.id.textdialog);
            title.setText("Delete Entry");
            msg.setText("Are you sure you want to delete this entry?");
            final Button done = (Button) dialogView.findViewById(R.id.submit);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (ConnectivityReceiver.isConnected()) {
                        Log.e("REPOT ID", String.valueOf(((HoursAdapter.ViewHolder0) viewHolder).hourDetail.getId()));
                   deleteHourReport(((HoursAdapter.ViewHolder0) viewHolder).hourDetail);

                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                    }

                    alertDialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                    adapter.restoreItem(deletedItem, deletedIndex);

                }
            });



            alertDialog.show();









        }
    }
}
