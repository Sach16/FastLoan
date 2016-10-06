package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerCustomersListener;
import com.whatsloan.dsa.model.LoansData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 24/4/16.
 */
public class CustomRecyclerAdapterForCustomerLoanDetails extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerCustomersListener m_cClickListener;
    private static ArrayList<LoansData> m_cObjJsonLoansData;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForCustomerLoanDetails(Context pContext, ArrayList<LoansData> pObjJsonLoansData, RecyclerCustomersListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjJsonLoansData = pObjJsonLoansData;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonLoansData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonLoansData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable @Bind(R.id.LOAN_TYPE)
        TextView loanType;

        @Nullable @Bind(R.id.LOAN_AMT_APPLIED)
        TextView appliedLoanAmt;

        @Nullable @Bind(R.id.APPLIED_ON)
        TextView appliedOn;

        @Nullable @Bind(R.id.BANK_APPLIED)
        TextView bankApplied;

        @Nullable @Bind(R.id.DSA_NAME)
        TextView dsaName;

        @Nullable @Bind(R.id.DSA_PHONE)
        TextView dsaPhone;

        @Nullable @Bind(R.id.DSA_EMAIL)
        TextView dsaEmail;

        @Nullable @Bind(R.id.DSA_CONTACT_NAME)
        TextView dsaContactName;

        @Nullable @Bind(R.id.LOAN_STATUS)
        TextView loanStatus;

        @Nullable @Bind(R.id.APP_ID)
        TextView applicId;

        @Nullable @Bind(R.id.TOTAL_TAT)
        TextView totalAMt;

        /*@Nullable @Bind(R.id.TAT_LEAD)
        TextView leadStatus;

        @Nullable @Bind(R.id.TAT_BANK_LOG_IN)
        TextView bankLoginStatus;

        @Nullable @Bind(R.id.TAT_SANCTION)
        TextView sanctionStatus;*/

        @Nullable @Bind(R.id.ELIGIBLE_LOAN_AMT)
        TextView eligibleLoanAmt;

        @Nullable @Bind(R.id.APPROVED_LOAN_AMT)
        TextView approvedLoanAmt;

        @Nullable @Bind(R.id.INTEREST_RATE)
        TextView interestRate;

        @Nullable @Bind(R.id.EMI_AMT)
        TextView emiAmt;

        @Nullable @Bind(R.id.EMI_START_DATE)
        TextView emiStartDate;

        @Nullable @Bind(R.id.DOCUMENT_NAMES)
        TextView documentNames;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.GO_ARROW)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.GO_ARROW:
//                    m_cClickListener.onInfoClick(getPosition(), m_cObjJsonLoansData.get(getPosition()), v);
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
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_profile_progress_child, parent, false);
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
//                ((DataObjectHolder) holder).fullName.setText(m_cObjJsonLoansData.get(position).getFirstName() + " " +
//                        m_cObjJsonLoansData.get(position).getLastName());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}
