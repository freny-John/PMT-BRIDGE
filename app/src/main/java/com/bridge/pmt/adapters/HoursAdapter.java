package com.bridge.pmt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge.pmt.R;
import com.bridge.pmt.fragments.HoursFragment;
import com.bridge.pmt.models.HourDetail;

import java.util.List;


public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HourDetail> mHourmodels;
    private Context context;
    private HoursFragment hoursCalendarFragment;

    public HoursAdapter(List<HourDetail> mHourmodels, Context context, HoursFragment hoursCalendarFragment) {
        this.mHourmodels = mHourmodels;
        this.context = context;
        this.hoursCalendarFragment = hoursCalendarFragment;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType)
        {
            case 1:      {    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hours, parent, false);
                return new ViewHolder0(view);
            }
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hours_space, parent, false);
                return new ViewHolder2(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hours, parent, false);
                return new ViewHolder0(view);

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i("PODSITI"," position " +position);
        switch (holder.getItemViewType()) {
            case 1:
                final ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                viewHolder0.hourDetail =mHourmodels.get(position);
                viewHolder0.time.setText(String.valueOf(viewHolder0.hourDetail.getHours()));
                viewHolder0.descrp.setText(viewHolder0.hourDetail.getDescription());
                viewHolder0.activty.setText(viewHolder0.hourDetail.getActivity());
                viewHolder0.relloy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Work in progress" , Toast.LENGTH_LONG).show();

                        hoursCalendarFragment.popIt(viewHolder0.hourDetail);

                    }
                });
                if(viewHolder0.hourDetail.getExtraWork()==0)
                {
                    viewHolder0.extra.setVisibility(View.INVISIBLE);
                }
                else {
                    viewHolder0.extra.setVisibility(View.VISIBLE);

                }                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                break;
        }



    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position<mHourmodels.size())
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return mHourmodels.size()+1;
    }

    public void removeItem(int position) {
        mHourmodels.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(HourDetail item, int position) {
        mHourmodels.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView activty;
        private TextView descrp;
        private ImageView extra;
        public RelativeLayout relloy;
        private RelativeLayout viewBackground;
        public HourDetail hourDetail;

        ViewHolder0(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.time);
            activty = (TextView) itemView.findViewById(R.id.activty);
            descrp = (TextView) itemView.findViewById(R.id.descrp);
            extra = (ImageView) itemView.findViewById(R.id.extra);
            relloy = (RelativeLayout) itemView.findViewById(R.id.relloy);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }


    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {


        ViewHolder2(View itemView) {
            super(itemView);


        }


    }

}
