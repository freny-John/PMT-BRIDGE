package com.bridge.pmt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridge.pmt.R;
import com.bridge.pmt.api.APIService;
import com.bridge.pmt.api.APIUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class HoursFragment extends Fragment {

    private RecyclerView recyclerViewUsers;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hours, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hours");

        recyclerViewUsers = (RecyclerView) view.findViewById(R.id.recyclerViewHours);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);



//
//        Call<Users> call = service.getUsers();
//
//        call.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                adapter = new UserAdapter(response.body().getUsers(), getActivity());
//                recyclerViewUsers.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//
//            }
//        });
//





    }
}
