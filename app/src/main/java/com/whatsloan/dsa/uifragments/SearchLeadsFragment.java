package com.whatsloan.dsa.uifragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfLeads;
import com.whatsloan.dsa.interfaces.RecyclerLeadsListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CustomersLoan;
import com.whatsloan.dsa.model.LeadCustHead;
import com.whatsloan.dsa.model.Leads;
import com.whatsloan.dsa.model.LeadsDatum;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.DSACustomerProfileLoanTask;
import com.whatsloan.dsa.uiactivities.DSASearch;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
public class SearchLeadsFragment extends DSAFragmentBaseClass implements RecyclerLeadsListener {
    String m_cJsonObject;
    int m_cPos;
    private ScrollView m_cScroll;
    private Leads m_cObjLeads;

    String m_cKey;
    String m_cValue;
    String m_cBuilder_uuid;
    String m_cProject_uuid;
    boolean both;

    private static int page = 1;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Bind(R.id.RECYC_LEAD_LIST)
    RecyclerView m_cRecyclerLeads;

    ArrayList<LeadsDatum> m_cLeadList;
    LinearLayoutManager m_cLayoutManager;
    private CustomRecyclerAdapterForListOfLeads m_cRecyclerAdapter;

    public SearchLeadsFragment() {
        super();
    }

    public static SearchLeadsFragment newInstance(int pPosition, String pKey,
                                                  String pValue, String pBuilder_uuid, String pProject_uuid, boolean pboth) {
        SearchLeadsFragment lSearchLeadsFragment = new SearchLeadsFragment();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("KEY", pKey);
        args.putString("VALUE", pValue);
        args.putString("BUILDERUUID", pBuilder_uuid);
        args.putString("PROJECTUUID", pProject_uuid);
        args.putBoolean("BOTH", pboth);
        lSearchLeadsFragment.setArguments(args);

        return lSearchLeadsFragment;
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
        m_cObjMainView = inflater.inflate(R.layout.search_lead_list, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        m_cObjMainActivity.m_cObjFragmentBase = SearchLeadsFragment.this;
        m_cPos = getArguments().getInt("Position", 0);

        m_cKey = getArguments().getString("KEY");
        m_cValue = getArguments().getString("VALUE");
        m_cBuilder_uuid = getArguments().getString("BUILDERUUID");
        m_cProject_uuid = getArguments().getString("PROJECTUUID");
        both = getArguments().getBoolean("BOTH", false);

        m_cLeadList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(m_cObjMainActivity);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecyclerLeads.setLayoutManager(m_cLayoutManager);
        m_cRecyclerLeads.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = m_cLayoutManager.getChildCount();
                    totalItemCount = m_cLayoutManager.getItemCount();
                    pastVisiblesItems = m_cLayoutManager.findFirstVisibleItemPosition();

//                    int page = totalItemCount / 15;
                    if (m_cLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            m_cLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            page = page + 1;
                            doPagination(page);
                        }
                    }
                }
            }
        });


        /*if(((DSASearch) m_cObjMainActivity).getScreenCount() == 0) {
            callSearchServer(m_cKey, m_cValue, m_cBuilder_uuid, m_cProject_uuid);
        }else {
            callSearchServer(null, "", null, null);
        }*/

        callSearchServer(m_cKey, m_cValue, m_cBuilder_uuid, m_cProject_uuid);

        /*if(null != m_cObjLeads) {
            for (LeadsDatum lLeadsDatum : m_cObjLeads.getData()) {
                m_cLeadList.add(lLeadsDatum);
            }
            if (null != m_cObjLeads && m_cObjLeads.getData().size() > 0) {
                m_cRecyclerAdapter = new CustomRecyclerAdapterForListOfLeads(m_cObjMainActivity, m_cLeadList, this);
                m_cRecyclerLeads.setAdapter(m_cRecyclerAdapter);
            }
        }else {
            try {
                m_cLeadList.clear();
                m_cRecyclerAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }*/

        /*m_cScroll = (ScrollView) m_cObjMainView.findViewById(R.id.SCROLL);
        m_cObjMainActivity.swipeView.setEnabled(false);

        m_cScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (m_cScroll.getScrollY() == 0) {
                    m_cObjMainActivity.swipeView.setEnabled(true);
                } else {
                    m_cObjMainActivity.swipeView.setEnabled(false);
                }
            }
        });*/

        return m_cObjMainView;
    }

    private void doPagination(int pPage) {
        m_cLeadList.add(null);
        m_cRecyclerAdapter.notifyItemInserted(m_cLeadList.size() - 1);
        callSearchServerPage(m_cKey, m_cValue, m_cBuilder_uuid, m_cProject_uuid, pPage);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onInfoClick(int pPostion, Leads pLeads, LeadsDatum pLeadsDatum, View pView) {
        m_cObjMainActivity.displayProgressBar(-1, "Loading...");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.INCLUDE, Constants.SETTINGS + "," + Constants.LOANS + "," + Constants.ADDRESSES + ","
                + Constants.ATTACHMENTS + "," + Constants.LOANS_ATTACHMENTS + "," + Constants.LOANS_HISTORY
                + "," + Constants.LOANS_AGENT_BANKS + "," + Constants.LOANS_TOTAL_TAT + "," + Constants.LOANS_LOAN_STATUS_TAT);
        placeRequest(Constants.apiMethodEx(Constants.CUSTOMERS, pLeadsDatum.getUser().getData().getUuid()),
                CustomersLoan.class, lParams, false);
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.SEARCH_LEADS:
                LeadCustHead lLeadCustHead = (LeadCustHead) response;
                if (lLeadCustHead.getData().getLeads().getData().size() > 0) {
                    if (m_cLoading) {
                        for (LeadsDatum lLeadsDatum : lLeadCustHead.getData().getLeads().getData()) {
                            m_cLeadList.add(lLeadsDatum);
                        }
                        if (null != m_cLeadList && m_cLeadList.size() > 0) {
                            m_cRecyclerAdapter = new CustomRecyclerAdapterForListOfLeads(m_cObjMainActivity, m_cLeadList, this);
                            m_cRecyclerLeads.setAdapter(m_cRecyclerAdapter);
                        }
                    } else {
                        m_cLeadList.remove(m_cLeadList.size() - 1);
                        m_cRecyclerAdapter.notifyItemRemoved(m_cLeadList.size());
                        for (LeadsDatum lLeadsDatum : lLeadCustHead.getData().getLeads().getData()) {
                            m_cLeadList.add(lLeadsDatum);
                        }
                        m_cRecyclerAdapter.notifyItemInserted(m_cLeadList.size());
                        m_cLoading = true;
                    }
                    ((DSASearch) m_cObjMainActivity).setLeadCount(lLeadCustHead.getData().getLeads().getMeta().getPagination().getTotal());
                } else {
                    if (m_cLeadList.size() > 0) {
                        m_cLeadList.remove(m_cLeadList.size() - 1);
                        m_cRecyclerAdapter.notifyItemRemoved(m_cLeadList.size());
                        m_cLoading = false;
                        page = 1;
                    }
                    ((DSASearch) m_cObjMainActivity).setLeadCount(lLeadCustHead.getData().getLeads().getMeta().getPagination().getTotal());
                }
                m_cObjMainActivity.hideDialog();
                break;
            default:
                if(apiMethod.contains("/")){
                    CustomersLoan lCustomersLoan = (CustomersLoan) response;
                    Intent lIntent = new Intent(m_cObjMainActivity, DSACustomerProfileLoanTask.class);
                    lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lIntent.putExtra("CUSTOMER_ID", lCustomersLoan.getData());
                    startActivity(lIntent);
                }else {
                    super.onAPIResponse(response, apiMethod);
                }
                m_cObjMainActivity.hideDialog();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*if (isVisibleToUser) {
            m_cIsVisibleToUser = isVisibleToUser;
            // Fetch data or something...
        }*/
        /*if (getView() != null) {
            m_cIsVisibleToUser = true;
            // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
        } else {
            m_cIsVisibleToUser = false;
        }*/
    }

    private void callSearchServer(String Key, String Value, String pBuilder_uuid, String pProject_uuid) {
        //call lead api
        m_cObjMainActivity.displayProgressBar(-1, "Loading Leads...");
        HashMap<String, String> lParams = new HashMap<>();
        if (null != Key && Key.trim().length() > 0) {
            lParams.put(Key, Value);
        }
        if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
            lParams.put(Constants.BUILDER_UUID, pBuilder_uuid);
        }
        if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
            lParams.put(Constants.PROJECTID, pProject_uuid);
        }
        lParams.put(Constants.INSCLUDE, "source,assignee,user.designation,user.addresses,loan");
        placeRequest(Constants.SEARCH_LEADS, LeadCustHead.class, lParams, false);
    }

    private void callSearchServerPage(String Key, String Value, String pBuilder_uuid, String pProject_uuid, int pPage) {
        //call lead api
        HashMap<String, String> lParams = new HashMap<>();
        if(pPage > 0){
            lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        }
        if (null != Key && Key.trim().length() > 0) {
            lParams.put(Key, Value);
        }
        if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
            lParams.put(Constants.BUILDER_UUID, pBuilder_uuid);
        }
        if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
            lParams.put(Constants.PROJECTID, pProject_uuid);
        }
        lParams.put(Constants.INSCLUDE, "source,assignee,user.designation,user.addresses,loan");
        placeRequest(Constants.SEARCH_LEADS, LeadCustHead.class, lParams, false);
    }
}
