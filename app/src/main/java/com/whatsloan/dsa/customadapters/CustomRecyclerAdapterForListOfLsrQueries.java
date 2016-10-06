package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerBanksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.QueriesData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 20/4/16.
 */
public class CustomRecyclerAdapterForListOfLsrQueries extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerBanksListener m_cClickListener;
    private static ArrayList<QueriesData> m_cObjQueries;
    private static Project m_cObjectProj;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfLsrQueries(Context pContext, ArrayList<QueriesData> pObjQueries, Project pObjectProj, RecyclerBanksListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjQueries = pObjQueries;
        this.m_cObjContext = pContext;
        this.m_cObjectProj = pObjectProj;
    }

    @Override
    public int getItemCount() {
        return m_cObjQueries.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjQueries.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.LEAD_CUST_NAME)
        TextView querieName;

        @Nullable
        @Bind(R.id.LSR_DESC)
        TextView querieDesc;

        @Nullable
        @Bind(R.id.QUERY_STATUS)
        TextView querieStatus;

        @Nullable
        @Bind(R.id.ASSIGNED_MEMBER)
        TextView assignedMember;

        @Nullable
        @Bind(R.id.END_DATE)
        TextView endDate;

        @Nullable
        @Bind(R.id.ITEM_CARDVIEW)
        CardView topLay;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ITEM_CARDVIEW)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ITEM_CARDVIEW:
                    m_cClickListener.onLSRClicked(getPosition(), null, m_cObjectProj, m_cObjQueries.get(getPosition()), v);
                    break;
            }
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.progressBar1)
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View lView;
        // paging logic
        if (viewType == VIEW_ITEM) {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsr_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            vh =  ldataObjectHolder;
        } else {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
            ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
            vh =  lprogressViewHolder;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DataObjectHolder) {

            try {
                ((DataObjectHolder) holder).querieName.setText("Query "+(position + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).querieDesc.setText(m_cObjQueries.get(position).getQuery());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).querieStatus.setText(DSAMacros.s2l(m_cObjQueries.get(position).getStatus()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).assignedMember.setText(m_cObjQueries.get(position).getAssignee().getData().getFirstName()+" "+m_cObjQueries.get(position).getAssignee().getData().getLastName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).endDate.setText(DSAMacros.getDateFormat(null, m_cObjQueries.get(position).getEndDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}