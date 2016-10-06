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
import com.whatsloan.dsa.interfaces.RecyclerLeadsListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.LeadsDatum;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 29/3/16.
 */
public class CustomRecyclerAdapterForListOfLeads extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerLeadsListener m_cClickListener;
    private static ArrayList<LeadsDatum> m_cObjJsonLeads;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfLeads(Context pContext, ArrayList<LeadsDatum> pJsonLeads, RecyclerLeadsListener pListener){
        this.m_cObjContext = pContext;
        this.m_cObjJsonLeads = pJsonLeads;
        this.m_cClickListener = pListener;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonLeads.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonLeads.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View lView;
        // paging logic
        if (viewType == VIEW_ITEM) {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_list_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            vh =  ldataObjectHolder;
        } else {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
            ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
            vh =  lprogressViewHolder;
        }
        return vh;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable @Bind(R.id.ITEM_CARDVIEW)
        CardView arrowAction;

        @Nullable @Bind(R.id.LEAD_CUST_NAME)
        TextView fullName;

        @Nullable @Bind(R.id.CONTACT_NO)
        TextView contactNumber;

        @Nullable @Bind(R.id.TYPE_OF_LOAN)
        TextView typrOfLoan;

        @Nullable @Bind(R.id.CUST_DATE)
        TextView dateMember;

        @Nullable @Bind(R.id.CUST_ENGG)
        TextView memberEngg;

        @Nullable @Bind(R.id.LOAN_AMT)
        TextView loanAMt;

        @Nullable @Bind(R.id.AGENT_NAME)
        TextView agentName;

        @Nullable @Bind(R.id.LOCATION)
        TextView location;

        @Nullable @Bind(R.id.STATUS_NEW_OLD)
        TextView statusNewOld;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.LEAD_CUST_NAME, R.id.ITEM_CARDVIEW})
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.LEAD_CUST_NAME:
                case R.id.ITEM_CARDVIEW:
                    m_cClickListener.onInfoClick(getPosition(), null, m_cObjJsonLeads.get(getPosition()), v);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DataObjectHolder) {
            try {
                ((DataObjectHolder) holder).fullName.setText(m_cObjJsonLeads.get(position).getUser().getData().getFirstName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).contactNumber.setText(m_cObjJsonLeads.get(position).getUser().getData().getPhone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).location.setText(m_cObjJsonLeads.get(position).getUser().getData().getAddresses().getData().getCity().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).loanAMt.setText("" + m_cObjJsonLeads.get(position).getAmount());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).dateMember.setText(DSAMacros.getDateFormat(null, m_cObjJsonLeads.get(position).getCreatedAt().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).typrOfLoan.setText(m_cObjJsonLeads.get(position).getTypes().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).memberEngg.setText(m_cObjJsonLeads.get(position).getUser().getData().getDesignation().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).agentName.setText(m_cObjJsonLeads.get(position).getAgent().getData().getFirstName() +
                        " " + m_cObjJsonLeads.get(position).getAgent().getData().getLastName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).statusNewOld.setText(m_cObjJsonLeads.get(position).getStatus().getData().getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

}
