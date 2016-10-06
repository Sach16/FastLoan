package com.whatsloan.dsa.uifragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomExpandableListAdapterForCustomerLoans;
import com.whatsloan.dsa.customviews.UserCircularImageView;
import com.whatsloan.dsa.interfaces.RecyclerLoansListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.CustomersLoan;
import com.whatsloan.dsa.model.LoansData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.CustomerLoanEdit;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 3/4/16.
 */
public class CustomerLoanDetails extends DSAFragmentBaseClass implements RecyclerLoansListener {

    @Nullable
    @Bind(R.id.RECYC_LOANSDETAILS)
    ExpandableListView m_cExpandLoans;

    @Nullable
    @Bind(R.id.PHONE_CALL)
    TextView m_cPhoneCall;

    @Nullable
    @Bind(R.id.PHONE_VIDEO_CALL)
    TextView m_cPhoneVideoCall;

    String m_cJsonObject;
    int m_cPos, previousItem = 0;

    CustomersData m_cObjCustomer;
    List<String> m_clistDataHeader;
    HashMap<String, LoansData> m_clistDataChild;

    UserCircularImageView m_cCustImg;
    TextView m_cCustName;
    TextView m_cLocation;
    ImageView m_cImageView;

    private CustomExpandableListAdapterForCustomerLoans m_cRecycAdapter;

    public CustomerLoanDetails() {
        super();
    }

    public static CustomerLoanDetails newInstance(int pPosition, String pJsonObject, CustomersData pObjCustomer) {
        CustomerLoanDetails lCustomerLoanDetails = new CustomerLoanDetails();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        args.putParcelable("CustomerObject", pObjCustomer);
        lCustomerLoanDetails.setArguments(args);

        return lCustomerLoanDetails;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_cIsActivityAttached = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_cObjMainView = inflater.inflate(R.layout.customer_profile_progress, container, false);
        ButterKnife.bind(this, m_cObjMainView);
        addHeader();
        return m_cObjMainView;
    }

    private void addHeader() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View mHeaderView = inflater.inflate(R.layout.cust_pro_det_top, null);
        m_cCustImg = (UserCircularImageView) mHeaderView.findViewById(R.id.PRO_PIC);
        m_cCustName = (TextView) mHeaderView.findViewById(R.id.CUST_NAME);
        m_cLocation = (TextView) mHeaderView.findViewById(R.id.LOCATION);
        m_cImageView = (ImageView) mHeaderView.findViewById(R.id.EDIT_CUSTOMER_IMG);
        m_cImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        m_cExpandLoans.addHeaderView(mHeaderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cObjMainActivity.m_cObjFragmentBase = CustomerLoanDetails.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cJsonObject = getArguments().getString("JsonObject");
        m_cObjCustomer = getArguments().getParcelable("CustomerObject");

        m_cPhoneCall.setTag(R.id.PHONE_NO_CUST, m_cObjCustomer.getPhone());
        m_cPhoneVideoCall.setTag(R.id.PHONE_NO_CUST, m_cObjCustomer.getPhone());

        //calling customer details for loans to refresh
        m_cObjMainActivity.displayProgressBar(-1, "Loading...");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.INCLUDE, Constants.SETTINGS + "," + Constants.LOANS  + "," + Constants.ADDRESSES + ","
                + Constants.ATTACHMENTS + "," + Constants.LOANS_ATTACHMENTS + "," + Constants.LOANS_HISTORY
                + "," + Constants.LOANS_AGENT_BANKS + "," + Constants.LOANS_TOTAL_TAT + "," + Constants.LOANS_LOAN_STATUS_TAT
                + "," + Constants.LOANS_TEAM);
        placeRequest(Constants.apiMethodEx(Constants.CUSTOMERS, m_cObjCustomer.getUuid()),
                CustomersLoan.class, lParams, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        if (apiMethod.contains("/")) {
            CustomersLoan lCustomersLoan = (CustomersLoan) response;
            m_cObjCustomer = lCustomersLoan.getData();

            if (null != m_cObjCustomer) {
                try {
                    for (AttachmentsData lAtachData : m_cObjCustomer.getAttachments().getData()) {
                        if (lAtachData.getType().equalsIgnoreCase(Constants.PROFILE_PICTURE)) {
                            Picasso.with(m_cObjMainActivity)
                                    .load(lAtachData.getUri())
                                    .error(R.drawable.profile_placeholder)
                                    .placeholder(R.drawable.profile_placeholder)
                                    .fit()
                                    .into(m_cCustImg);
                        }
                    }
                } catch (Exception e) {
                    m_cCustImg.setImageResource(R.drawable.profile_placeholder);
                    e.printStackTrace();
                }
                String firstName = m_cObjCustomer.getFirstName();
                String lastName = m_cObjCustomer.getLastName();

                if (firstName != null && !firstName.isEmpty() && lastName != null & !lastName.isEmpty()) {
                    m_cCustName.setText(firstName + " " + lastName);
                } else if (firstName != null && !firstName.isEmpty()) {
                    m_cCustName.setText(firstName);
                } else if (lastName != null & !lastName.isEmpty()) {
                    m_cCustName.setText(lastName);
                }

                try {
                    String location = m_cObjCustomer.getAddresses().getData().getCity().getData().getName();
                    m_cLocation.setText(location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (null != m_cObjCustomer.getLoans().getData()) {
                m_clistDataHeader = new ArrayList<String>();
                m_clistDataChild = new HashMap<String, LoansData>();
                for (LoansData lLoansData : m_cObjCustomer.getLoans().getData()) {
                    m_clistDataHeader.add(lLoansData.getType().getData().getName() + " " + lLoansData.getUuid());
                    m_clistDataChild.put(lLoansData.getType().getData().getName() + " " + lLoansData.getUuid(), lLoansData);
                }
                m_cRecycAdapter = new CustomExpandableListAdapterForCustomerLoans(m_cObjMainActivity, m_clistDataHeader,
                        m_clistDataChild, this);
                m_cExpandLoans.setAdapter(m_cRecycAdapter);

                m_cExpandLoans.expandGroup(0);
                m_cExpandLoans.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousItem)
                            m_cExpandLoans.collapseGroup(previousItem);
                        previousItem = groupPosition;
                    }
                });
            }


            m_cObjMainActivity.hideDialog();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void onInfoClick(int pPostion, LoansData pLoansData, View pView) {
        DSAMacros.saveLoanUuid(m_cObjMainActivity, pLoansData.getUuid());
        Intent lIntent = new Intent(m_cObjMainActivity, CustomerLoanEdit.class);
        lIntent.putExtra("LoansData", pLoansData);
        lIntent.putExtra("IsStatus", false);
        lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lIntent);
    }

    @Override
    public void onEditClick(int pPostion, LoansData pLoansData, View pView) {
        DSAMacros.saveLoanUuid(m_cObjMainActivity, pLoansData.getUuid());
        Intent lIntent = new Intent(m_cObjMainActivity, CustomerLoanEdit.class);
        lIntent.putExtra("LoansData", pLoansData);
        lIntent.putExtra("IsStatus", true);
        lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lIntent);
    }

    @Override
    public void onJustClick(int pPostion, LoansData pLoansData, View pView) {
        DSAMacros.saveLoanUuid(m_cObjMainActivity, pLoansData.getUuid());
    }

    @OnClick({R.id.PHONE_CALL, R.id.PHONE_VIDEO_CALL})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.PHONE_CALL:
            case R.id.PHONE_VIDEO_CALL:
                if (null != (String) m_cPhoneCall.getTag(R.id.PHONE_NO_CUST)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + (String) m_cPhoneCall.getTag(R.id.PHONE_NO_CUST)));
                    m_cObjMainActivity.verifyCallPermissions(m_cObjMainActivity);
                    if (ActivityCompat.checkSelfPermission(m_cObjMainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
                break;
        }
    }
}

