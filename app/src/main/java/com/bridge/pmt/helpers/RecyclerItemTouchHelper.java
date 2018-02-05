package com.bridge.pmt.helpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.bridge.pmt.adapters.HoursAdapter;

/**
 * Created by Thaher.m on 01-02-2018.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            if(viewHolder instanceof HoursAdapter.ViewHolder0 ) {

            final View foregroundView = ((HoursAdapter.ViewHolder0) viewHolder).relloy;

            getDefaultUIUtil().onSelected(foregroundView);
        }}
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof HoursAdapter.ViewHolder0 ) {
        final View foregroundView = ((HoursAdapter.ViewHolder0) viewHolder).relloy;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);}
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof HoursAdapter.ViewHolder0 ) {
        final View foregroundView =((HoursAdapter.ViewHolder0) viewHolder).relloy;
        getDefaultUIUtil().clearView(foregroundView);}
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof HoursAdapter.ViewHolder0 ) {  final View foregroundView = ((HoursAdapter.ViewHolder0) viewHolder).relloy;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);}
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(viewHolder instanceof HoursAdapter.ViewHolder0 ) {  listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());}
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
