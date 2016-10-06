package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerLandingListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Members;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.TaskStatusCountData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
public class CustomRecyclerAdapterForListOfLanding extends RecyclerView.Adapter<CustomRecyclerAdapterForListOfLanding.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerLandingListener m_cClickListener;
    private static Members m_cObjJsonMembers;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfLanding(Context pContext, Members pObjJson, RecyclerLandingListener pListener) {
        this.m_cClickListener = pListener;
        this.m_cObjJsonMembers = pObjJson;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonMembers.getData().size();
    }

    public void setOnItemClickListener(RecyclerLandingListener myClickListener) {
        this.m_cClickListener = myClickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.PHONE_NUMBER)
        TextView phoneNumber;

        @Nullable
        @Bind(R.id.TIME_STAMP)
        TextView timeStamp;

        @Nullable
        @Bind(R.id.TOTAL_TASK_NO)
        TextView totalTaskNo;

        @Nullable
        @Bind(R.id.TO_BE_STARTED_NO)
        TextView toBeStartedNo;

        @Nullable
        @Bind(R.id.IN_PROGRESS_NO)
        TextView inProgressNo;

        @Nullable
        @Bind(R.id.COMPLETED_NO)
        TextView completedNo;

        @Nullable
        @Bind(R.id.MEMBER_NAME)
        TextView memberName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ITEM_CARDVIEW)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ITEM_CARDVIEW:
                    m_cClickListener.onInfoClick(getPosition(), m_cObjJsonMembers, m_cObjJsonMembers.getData().get(getPosition()), v);
                    break;
            }
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.landing_recyc_cell, parent, false);
        DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
        return ldataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        MembersData lObjData = m_cObjJsonMembers.getData().get(position);

        try{
            holder.memberName.setText(lObjData.getFirstName()+" "+lObjData.getLastName());
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            holder.phoneNumber.setText(lObjData.getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (null != lObjData.getAttendances().getData())
                holder.timeStamp.setText(DSAMacros.getDateFormat(null, lObjData.getAttendances().getData().get(0).getStartTime().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            holder.totalTaskNo.setText(""+lObjData.getTasks().getData().size());
        }catch (Exception e){
            e.printStackTrace();
        }

        int tobestarted = 0;
        int inprogress = 0;
        int completed = 0;

        for(TaskStatusCountData lStatusData : lObjData.getTaskStatusCount().getData()){
            switch (lStatusData.getStatus().getKey()){
                case DSAMacros.TO_BE_STARTED:
                    tobestarted = lStatusData.getCount();
                    break;
                case DSAMacros.IN_PROGRESS:
                    inprogress = lStatusData.getCount();
                    break;
                case DSAMacros.COMPLETED:
                    completed = lStatusData.getCount();
                    break;
            }
        }

        try{
            holder.toBeStartedNo.setText(""+tobestarted);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            holder.inProgressNo.setText(""+inprogress);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            holder.completedNo.setText(""+completed);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
