package com.bridge.pmt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge.pmt.R;
import com.bridge.pmt.fragments.HoursCalendarFragment;
import com.bridge.pmt.helpers.DataManager;
import com.bridge.pmt.models.HourDetail;
import com.bridge.pmt.models.HoursModel;
import com.bridge.pmt.models.WeekReport;

import java.util.List;


public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {

    private List<HourDetail> mHourmodels;
    private Context context;
    private HoursCalendarFragment hoursCalendarFragment;

    public HoursAdapter(List<HourDetail> mHourmodels, Context context, HoursCalendarFragment hoursCalendarFragment) {
        this.mHourmodels = mHourmodels;
        this.context = context;
        this.hoursCalendarFragment = hoursCalendarFragment;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hours, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.hourDetail =mHourmodels.get(position);
        holder.time.setText(holder.hourDetail.getHours().toString());
        holder.descrp.setText(holder.hourDetail.getDescription());
        holder.activty.setText(holder.hourDetail.getActivity().toString());
        holder.relloy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Toast.makeText(context,"Work in progress" , Toast.LENGTH_LONG).show();

                hoursCalendarFragment.popIt(holder.hourDetail);

            }
        });
        if(holder.hourDetail.getExtraWork()==0)
        {
            holder.extra.setVisibility(View.GONE);
        }
        else {
            holder.extra.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return mHourmodels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView activty;
        private TextView descrp;
        private ImageView extra;
        private RelativeLayout relloy;
        private HourDetail hourDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.time);
            activty = (TextView) itemView.findViewById(R.id.activty);
            descrp = (TextView) itemView.findViewById(R.id.descrp);
            extra = (ImageView) itemView.findViewById(R.id.extra);
            relloy = (RelativeLayout) itemView.findViewById(R.id.relloy);
        }


    }

}
