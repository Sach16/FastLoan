package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerLoansListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.LoanStatusTatData;
import com.whatsloan.dsa.model.LoansData;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 6/5/16.
 */
public class CustomExpandableListAdapterForCustomerLoans extends BaseExpandableListAdapter {

    private Context m_cContext;
    private List<String> m_cListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, LoansData> m_cListDataChild;
    private static RecyclerLoansListener m_cClickListener;

    public CustomExpandableListAdapterForCustomerLoans(Context context, List<String> listDataHeader,
                                                       HashMap<String, LoansData> listChildData, RecyclerLoansListener pListener) {
        this.m_cContext = context;
        this.m_cListDataHeader = listDataHeader;
        this.m_cListDataChild = listChildData;
        this.m_cClickListener = pListener;
    }

    @Override
    public int getGroupCount() {
        return this.m_cListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.m_cListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.m_cListDataChild.get(this.m_cListDataHeader.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.m_cContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customer_profile_progress_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.LEADS_TXT_NO);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        String[] lbuf = headerTitle.split(" ");
        lblListHeader.setText(lbuf[0]+" "+lbuf[1]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final LoansData lLoanData = (LoansData) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.m_cContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.customer_profile_progress_child, null);
        }

        RelativeLayout lRelLay = (RelativeLayout) convertView.findViewById(R.id.CHILD_LAY);
        TextView loantype = (TextView) convertView.findViewById(R.id.LOAN_TYPE);
        TextView loanamtapplied = (TextView) convertView.findViewById(R.id.LOAN_AMT_APPLIED);
        TextView appliedon = (TextView) convertView.findViewById(R.id.APPLIED_ON);
        TextView bankapplied = (TextView) convertView.findViewById(R.id.BANK_APPLIED);
        TextView dsaname = (TextView) convertView.findViewById(R.id.DSA_NAME);
        TextView dsaphone = (TextView) convertView.findViewById(R.id.DSA_PHONE);
        TextView dsaemail = (TextView) convertView.findViewById(R.id.DSA_EMAIL);
        TextView dsacontactname = (TextView) convertView.findViewById(R.id.DSA_CONTACT_NAME);
        TextView dsacontactphone = (TextView) convertView.findViewById(R.id.DSA_CONTACT_PHONE);
        TextView loanstatus = (TextView) convertView.findViewById(R.id.LOAN_STATUS);
        TextView appid = (TextView) convertView.findViewById(R.id.APP_ID);
        TextView totaltat = (TextView) convertView.findViewById(R.id.TOTAL_TAT);
//        TextView tatlead = (TextView) convertView.findViewById(R.id.TAT_LEAD);
//        TextView tatbanklogin = (TextView) convertView.findViewById(R.id.TAT_BANK_LOG_IN);
//        TextView tatsanction = (TextView) convertView.findViewById(R.id.TAT_SANCTION);
        RecyclerView listview = (RecyclerView) convertView.findViewById(R.id.LOAN_PROGRESS_LIST);
        TextView eligibleloanamt = (TextView) convertView.findViewById(R.id.ELIGIBLE_LOAN_AMT);
        TextView approvedloanamt = (TextView) convertView.findViewById(R.id.APPROVED_LOAN_AMT);
        TextView interestrate = (TextView) convertView.findViewById(R.id.INTEREST_RATE);
        TextView approveddate = (TextView) convertView.findViewById(R.id.APPROVED_DATE);
        TextView emiamt = (TextView) convertView.findViewById(R.id.EMI_AMT);
        TextView emistartdate = (TextView) convertView.findViewById(R.id.EMI_START_DATE);
        TextView documentnames = (TextView) convertView.findViewById(R.id.DOCUMENT_NAMES);
        ImageView leadDotImg = (ImageView) convertView.findViewById(R.id.LEAD_DOT_IMG);
        ImageView banklineImg = (ImageView) convertView.findViewById(R.id.BANK_LOGIN_LINE_IMG);
        ImageView bankDotImg = (ImageView) convertView.findViewById(R.id.BANK_LOGIN_DOT_IMG);
        ImageView sanclineImg = (ImageView) convertView.findViewById(R.id.SANCTION_LINE_IMG);
        ImageView sancDotImg = (ImageView) convertView.findViewById(R.id.SANCTION_DOT_IMG);
        ImageView disblineImg = (ImageView) convertView.findViewById(R.id.DISBURSAL_LINE_IMG);
        ImageView disbDotImg = (ImageView) convertView.findViewById(R.id.DISBURSAL_DOT_IMG);
        ImageView editLoanImg = (ImageView) convertView.findViewById(R.id.EDIT_CUST_LOAN_IMG);

        try{
            loantype.setText(lLoanData.getType().getData().getName());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            loanamtapplied.setText(DSAMacros.getRupees(""+lLoanData.getAmount()));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            appliedon.setText(DSAMacros.getDateFormat(null, lLoanData.getAppliedOn().getDate(),
                    DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            bankapplied.setText(lLoanData.getAgent().getData().getBanks().getData().getName());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            dsaname.setText(lLoanData.getTeam().getData().getName());
        }catch (Exception e){
            e.printStackTrace();
        }try{
//            dsaphone.setText(lLoanData.getAgent().getData().getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            dsaemail.setText(lLoanData.getAgent().getData().getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            dsacontactname.setText(lLoanData.getAgent().getData().getFirstName()+" "+
                    lLoanData.getAgent().getData().getLastName());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            dsacontactphone.setText(lLoanData.getAgent().getData().getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }try{
            loanstatus.setText(DSAMacros.s2l(lLoanData.getStatus().getData().getKey()));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            appid.setText(lLoanData.getAppid());
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH,25);
        thatDay.set(Calendar.MONTH,7); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 1985);
        Calendar today = Calendar.getInstance();
        long ldiff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        try{
            eligibleloanamt.setText(DSAMacros.getRupees(""+lLoanData.getEligibleAmount()));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            approvedloanamt.setText(DSAMacros.getRupees(""+lLoanData.getApprovedAmount()));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            interestrate.setText(lLoanData.getInterestRate() + "%");
        }catch (Exception e){
            e.printStackTrace();
        }try{
            approveddate.setText(DSAMacros.getDateFormat(null, lLoanData.getApprovalDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                    DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            emiamt.setText(DSAMacros.getRupees(""+lLoanData.getEmi()));
        }catch (Exception e){
            e.printStackTrace();
        }try{
            emistartdate.setText(DSAMacros.getDateFormat(null, lLoanData.getEmiStartDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                    DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
        }catch (Exception e){
            e.printStackTrace();
        }
        StringBuffer lBuff = new StringBuffer();
        try {
            for (int i = 0 ; i < lLoanData.getAttachments().getData().size(); i++) {
                AttachmentsData lAttachmentsData = lLoanData.getAttachments().getData().get(i);
                if(i != 0){
                    lBuff.append(", " + lAttachmentsData.getName());
                }else {
                    lBuff.append(lAttachmentsData.getName());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            documentnames.setText(lBuff.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            switch (lLoanData.getStatus().getData().getKey()) {
                case "FINAL_DISB":
                case "FULL_DISB":
                case "FIRST_DISB":
                    leadDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    banklineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    bankDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    sanclineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    sancDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    disblineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    disbDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    break;
                case "SANCTION":
                    leadDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    banklineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    bankDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    sanclineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    sancDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    disblineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    disbDotImg.setImageResource(android.R.color.transparent);
                    break;
                case "BANK_DECLINE":
                case "BANK_LOGIN":
                case "OFFICE_LOGIN":
                case "FOLLOW_UP":
                    leadDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    banklineImg.setBackgroundColor(m_cContext.getResources().getColor(R.color.buttonBg));
                    bankDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    sanclineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    sancDotImg.setImageResource(android.R.color.transparent);
                    disblineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    disbDotImg.setImageResource(android.R.color.transparent);
                    break;
                case "LEAD":
                case "RE_LOGIN":
                case "TAKE_OVER":
                    leadDotImg.setImageDrawable(m_cContext.getResources().getDrawable(R.drawable.cricle_));
                    banklineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    bankDotImg.setImageResource(android.R.color.transparent);
                    sanclineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    sancDotImg.setImageResource(android.R.color.transparent);
                    disblineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    disbDotImg.setImageResource(android.R.color.transparent);
                    break;
                default:
                    leadDotImg.setImageResource(android.R.color.transparent);
                    banklineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    bankDotImg.setImageResource(android.R.color.transparent);
                    sanclineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    sancDotImg.setImageResource(android.R.color.transparent);
                    disblineImg.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    disbDotImg.setImageResource(android.R.color.transparent);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            totaltat.setText(lLoanData.getTotalTat().getData().getDuration() + " Days");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            listview.setLayoutManager(new LinearLayoutManager(m_cContext));
            InnerListAdapter listAdapter = new  InnerListAdapter(m_cContext, lLoanData.getLoanStatusTat().getData());
            listview.setAdapter(listAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        documentnames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cClickListener.onInfoClick(0, lLoanData, null);
            }
        });

        editLoanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cClickListener.onEditClick(0, lLoanData, null);
            }
        });

        //to make the
        m_cClickListener.onJustClick(0, lLoanData, null);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class InnerListAdapter extends RecyclerView.Adapter<InnerListAdapter.DataObjectHolder>{

        private List<LoanStatusTatData> mObjStatus;
        private Context m_cObjContext;

        public InnerListAdapter(Context pContext, List<LoanStatusTatData> pObjHistory){
            this.mObjStatus = pObjHistory;
            this.m_cObjContext = pContext;
        }

        @Override
        public int getItemCount() {
            return mObjStatus.size();
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder {

            @Nullable
            @Bind(R.id.LEFT_LABEL)
            TextView leftLabel;

            @Nullable
            @Bind(R.id.RIGHT_LABEL)
            TextView rightLabel;

            public DataObjectHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            return ldataObjectHolder;
        }

        @Override
        public void onBindViewHolder(DataObjectHolder holder, int position) {

            try {
                holder.leftLabel.setText(mObjStatus.get(position).getStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                holder.rightLabel.setText(mObjStatus.get(position).getStatusTat() + " days" );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
