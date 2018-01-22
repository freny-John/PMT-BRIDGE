package com.bridge.pmt.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bridge.pmt.MainActivity;
import com.bridge.pmt.R;
import com.bridge.pmt.api.APIService;
import com.bridge.pmt.api.APIUrl;
import com.bridge.pmt.adapters.HoursAdapter;
import com.bridge.pmt.helpers.SharedPrefManager;
import com.bridge.pmt.helpers.ValidationManager;
import com.bridge.pmt.models.BaseResponse;
import com.bridge.pmt.models.HourDetail;
import com.bridge.pmt.models.WeekReport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HoursCalendarFragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<WeekReport> weekReport;
    private List<HourDetail> currenthourDetails;
    LinearLayout empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hours_calendar, container, false);
        createUI(rootView);
        getActivity().setTitle("Hours");


        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Calendar endDate = Calendar.getInstance();

        endDate.setTime(c.getTime());
        endDate.add(Calendar.DAY_OF_YEAR, 6);
        empty = (LinearLayout)rootView.findViewById(R.id.empty);
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
                if(position<=8 &&position>=2)
                {currenthourDetails.addAll(weekReport.get(position-2).getHourDetails());}
                adapter.notifyDataSetChanged();

                if(currenthourDetails.size()>0)
                {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });

        return rootView;
    }

    private void createUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        weekReport = new ArrayList<>();
        weekReport.clear();
        currenthourDetails = new ArrayList<>();
        currenthourDetails.clear();
                adapter = new HoursAdapter(currenthourDetails,getContext());

        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHours);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        Call<BaseResponse> call = service.getUsers();
//
//        call.enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                adapter = new HoursAdapter(response.body().getUsers(), getActivity());
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse> call, Throwable t) {
//
//            }
//        });


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(SharedPrefManager.KEY_USER_TOKEN, null);
        int userId = sharedPreferences.getInt(SharedPrefManager.KEY_USER_ID,0);

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
               if(response.body().getStatus().equals(1))
               {
                  weekReport = response.body().getData().getWeekReport();
                   currenthourDetails.clear();
                   currenthourDetails.addAll(weekReport.get(1).getHourDetails());
                   adapter.notifyDataSetChanged();
                   if(currenthourDetails.size()>0)
               {
                   empty.setVisibility(View.GONE);
                   recyclerView.setVisibility(View.VISIBLE);
               }
               else {
                   empty.setVisibility(View.VISIBLE);
                   recyclerView.setVisibility(View.GONE);
               }
               }
               else {
//                   Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                   if(currenthourDetails.size()>0)
                   {
                       empty.setVisibility(View.GONE);
                       recyclerView.setVisibility(View.VISIBLE);
                   }
                   else {
                       empty.setVisibility(View.VISIBLE);
                       recyclerView.setVisibility(View.GONE);
                   }
               }
            }


            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();

//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });






    }
}
