package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerAttendanceListener;
import com.whatsloan.dsa.model.AttendanceData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
public class CustomRecyclerAdapterForAttendanceHistory extends RecyclerView.Adapter<CustomRecyclerAdapterForAttendanceHistory.DataObjectHolder>{

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerAttendanceListener m_cClickListener;
    private static ArrayList<AttendanceData>  m_cObjJsonAttendance;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForAttendanceHistory(Context pContext, ArrayList<AttendanceData> pObjJsonAttendance, RecyclerAttendanceListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjJsonAttendance = pObjJsonAttendance;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonAttendance.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.LIST_HEADER)
        LinearLayout header;

        @Nullable
        @Bind(R.id.MEMBER_NAME)
        TextView memberName;

        @Nullable
        @Bind(R.id.PRESENT_NO)
        TextView presentNo;

        @Nullable
        @Bind(R.id.ABSENT_NO)
        TextView absentNo;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.LIST_HEADER)
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.LIST_HEADER:
                    m_cClickListener.onInfoClick(getPosition(), null, m_cObjJsonAttendance.get(getPosition()), v);
                    break;
            }
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_attendance_history_cell, parent, false);
        DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
        return ldataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        AttendanceData lObjdata = m_cObjJsonAttendance.get(position);

        try {
            holder.memberName.setText(lObjdata.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            holder.presentNo.setText(""+lObjdata.getPresent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            holder.absentNo.setText(""+lObjdata.getLeave());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
