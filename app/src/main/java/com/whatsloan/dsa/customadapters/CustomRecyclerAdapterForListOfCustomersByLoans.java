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
import com.whatsloan.dsa.interfaces.RecyclerCustomersListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.LoansData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 9/5/16.
 */
public class CustomRecyclerAdapterForListOfCustomersByLoans extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerCustomersListener m_cClickListener;
    private static ArrayList<LoansData> m_cObjJsonLoansCust;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfCustomersByLoans(Context pContext, ArrayList<LoansData> pObjJsonLoansData, RecyclerCustomersListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjJsonLoansCust = pObjJsonLoansData;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonLoansCust.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonLoansCust.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.ITEM_CARDVIEW)
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

        @OnClick(R.id.ITEM_CARDVIEW)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ITEM_CARDVIEW:
                    m_cClickListener.onInfoClick(getPosition(), m_cObjJsonLoansCust.get(getPosition()).getCustomer().getData(), v);
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DataObjectHolder) {

            final CustomersData lObjCustomer = m_cObjJsonLoansCust.get(position).getCustomer().getData();

            try {
                ((DataObjectHolder) holder).fullName.setText(lObjCustomer.getFirstName()+" "+
                        lObjCustomer.getLastName());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).contactNumber.setText(lObjCustomer.getPhone());
            }catch (Exception e){
                e.printStackTrace();
            }

            //TODO ask for single customer location

            try {
                ((DataObjectHolder) holder).location.setText(m_cObjJsonLoansCust.get(position).getUser().getData().getAddresses().getData().getCity().getData().getName());
            }catch (Exception e){
                e.printStackTrace();
            }

            /*int loanAmt = 0;
            for(LoansData loandata: m_cObjJsonLoansCust){
                loanAmt = loanAmt + loandata.getAmount();
            }*/

            try {
                ((DataObjectHolder) holder).typrOfLoan.setText(""+m_cObjJsonLoansCust.get(position).getType().getData().getName());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).loanAMt.setText(""+m_cObjJsonLoansCust.get(position).getAmount());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).memberEngg.setText(DSAMacros.s2l(m_cObjJsonLoansCust.get(position).getCustomer().getData().getRole()));
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).dateMember.setText(DSAMacros.getDateFormat(null, m_cObjJsonLoansCust.get(position).getCreatedAt().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).agentName.setText(m_cObjJsonLoansCust.get(position).getAgent().getData().getFirstName()+
                        " "+m_cObjJsonLoansCust.get(position).getAgent().getData().getLastName());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ((DataObjectHolder) holder).statusNewOld.setText(m_cObjJsonLoansCust.get(position).getStatus().getData().getLabel());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}