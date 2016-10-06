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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfApprovingBanks;
import com.whatsloan.dsa.interfaces.RecyclerBanksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Bank;
import com.whatsloan.dsa.model.BankAll;
import com.whatsloan.dsa.model.BanksAll;
import com.whatsloan.dsa.model.BanksData;
import com.whatsloan.dsa.model.CityAll;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.ProjectsAll;
import com.whatsloan.dsa.model.QueriesData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 24/3/16.
 */
public class BankApprovingFragment extends DSAFragmentBaseClass implements RecyclerBanksListener, AdapterView.OnItemSelectedListener {

    private CustomRecyclerAdapterForListOfApprovingBanks m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;
    private static boolean FILTER_CLICKED = false;

    private int m_cCitiesPos = -1;
    private int m_cProjectPos = -1;

    private static int page = 1;
    int m_cPos;
    //    private ScrollView m_cScroll;

    ArrayList<BanksData> m_cBankList;
    ArrayList<String> m_cProjectList;
    HashMap<String, String> m_cProjectDic;
    ArrayList<String> m_cCitiesList;
    HashMap<String, String> m_cCitiesDic;

    ArrayAdapter<String> m_cSpinAdapter;

    @Nullable
    @Bind(R.id.TO_SPINNER)
    Spinner m_cSelectProjectsSpin;

    @Nullable
    @Bind(R.id.FROM_SPINNER)
    Spinner m_cSelectCitiesSpin;

    @Nullable
    @Bind(R.id.RESULTS_COUNT_TXT)
    TextView m_cBankCountNo;

    @Nullable
    @Bind(R.id.RECYC_APPRO_BANKS)
    RecyclerView m_cRecycApproving;

    @Nullable
    @Bind(R.id.SPIN_FILT_BUTTON_TXT)
    TextView m_cSpinFiltButTxt;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoData;

    public BankApprovingFragment() {
        super();
    }

    public static BankApprovingFragment newInstance(int pPosition) {
        BankApprovingFragment lbankApprovingFragment = new BankApprovingFragment();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        lbankApprovingFragment.setArguments(args);

        return lbankApprovingFragment;
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
        m_cObjMainView = inflater.inflate(R.layout.approving_banks, container, false);
        ButterKnife.bind(this, m_cObjMainView);
        m_cSpinFiltButTxt.setText("Get Banks Approved");


        /*m_cScroll = (ScrollView) m_cObjMainView.findViewById(R.id.SCROLL);
//        m_cObjMainActivity.swipeView.setEnabled(false);

        m_cScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (m_cScroll.getScrollY() == 0) {
//                    m_cObjMainActivity.swipeView.setEnabled(true);
                } else {
//                    m_cObjMainActivity.swipeView.setEnabled(false);
                }
            }
        });*/

        return m_cObjMainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cObjMainActivity.m_cObjFragmentBase = BankApprovingFragment.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cBankList = new ArrayList<>();
        m_cSelectProjectsSpin.setOnItemSelectedListener(BankApprovingFragment.this);
        m_cSelectCitiesSpin.setOnItemSelectedListener(BankApprovingFragment.this);
        m_cLayoutManager = new LinearLayoutManager(m_cObjMainActivity);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycApproving.setLayoutManager(m_cLayoutManager);
        m_cRecycApproving.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            doPagination(page + 1);
                        }
                    }
                }
            }
        });

        m_cCitiesList = new ArrayList<>();
        m_cCitiesList.add("Select City");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item, m_cCitiesList);
        m_cSelectCitiesSpin.setAdapter(m_cSpinAdapter);

        m_cProjectList = new ArrayList<>();
        m_cProjectList.add("Select Project");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item, m_cProjectList);
        m_cSelectProjectsSpin.setAdapter(m_cSpinAdapter);

        m_cBankCountNo.setText("(0 Banks)");

        //Calling Cities api
        HashMap<String, String> params = new HashMap<>();
        m_cObjMainActivity.displayProgressBar(-1, "Loading");
        params.put(Constants.PAGINATE, Constants.ALL);
        placeRequest(Constants.CITIES, CityAll.class, params, false);

        //Calling Projects api
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        placeRequest(Constants.PROJECTS, ProjectsAll.class, lParams, false);

        //Calling Banks api
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.INSCLUDE, Constants.ATTACHMENTS);
        placeRequest(Constants.BANKSAPPROVING, BanksAll.class, llParams, false);

    }

    private void doPagination(int pPage) {
        m_cBankList.add(null);
        m_cRecycAdapter.notifyItemInserted(m_cBankList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.INSCLUDE, Constants.ATTACHMENTS);
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        if (m_cCitiesPos > 0) {
            lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
        }
        if (m_cProjectPos > 0) {
            lParams.put(Constants.PROJECTID, m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)));
        }
        placeRequest(Constants.BANKSAPPROVING, BanksAll.class, lParams, false);
    }

    @OnClick(R.id.SPIN_FILT_BUTTON_TXT)
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.SPIN_FILT_BUTTON_TXT:
                try {
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.ATTACHMENTS);
                    if (m_cCitiesPos > 0) {
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    }
                    if (m_cProjectPos > 0) {
                        lParams.put(Constants.PROJECTID, m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)));
                    }
                    placeRequest(Constants.BANKSAPPROVING, BanksAll.class, lParams, false);
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    m_cLoading = true;
                    FILTER_CLICKED = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.BANKSAPPROVING:
                if (!FILTER_CLICKED) {
                    BanksAll lBanksAll = (BanksAll) response;
                    if (lBanksAll.getBanks().size() > 0) {
                        if (m_cLoading) {
                            for (BanksData lBank : lBanksAll.getBanks()) {
                                m_cBankList.add(lBank);
                            }
                            if (null != m_cBankList && m_cBankList.size() > 0) {
                                m_cRecycAdapter = new CustomRecyclerAdapterForListOfApprovingBanks(m_cObjMainActivity, m_cBankList, this);
                                m_cRecycApproving.setAdapter(m_cRecycAdapter);
                            }
                            m_cBankCountNo.setText("(" + m_cBankList.size() + " Banks)");
                        } else {
                            m_cBankList.remove(m_cBankList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cBankList.size());
                            for (BanksData lBank : lBanksAll.getBanks()) {
                                m_cBankList.add(lBank);
                            }
                            m_cBankCountNo.setText("(" + m_cBankList.size() + " Banks)");
                            m_cRecycAdapter.notifyItemInserted(m_cProjectList.size());
                            m_cLoading = true;
                        }
                    } else {
                        if (m_cBankList.size() > 0) {
                            m_cBankList.remove(m_cBankList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cBankList.size());
                            m_cLoading = false;
                            page = 1;
                        } else {
                            m_cNoData.setVisibility(View.VISIBLE);
                        }
                    }
                    m_cObjMainActivity.hideDialog();
                } else {
                    BanksAll lBanksAll = (BanksAll) response;
                    if (lBanksAll.getBanks().size() > 0) {
                        /*m_cRecycAdapter.notifyItemRangeRemoved(0, m_cProjectList.size());
                        m_cProjectList = new ArrayList<>();*/
                        if (m_cLoading) {
                            m_cBankList.clear();
                            m_cRecycAdapter.notifyDataSetChanged();
                            for (BanksData lBank : lBanksAll.getBanks()) {
                                m_cBankList.add(lBank);
                            }
                            if (null != m_cBankList && m_cBankList.size() > 0) {
                                m_cRecycAdapter.notifyDataSetChanged();
                            }
                            m_cBankCountNo.setText("(" + m_cBankList.size() + " Banks)");
                        } else {
                            m_cBankList.remove(m_cBankList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cBankList.size());
                            for (BanksData lBank : lBanksAll.getBanks()) {
                                m_cBankList.add(lBank);
                            }
                            m_cBankCountNo.setText("(" + m_cBankList.size() + " Banks)");
                            m_cRecycAdapter.notifyItemInserted(m_cBankList.size());
                            m_cLoading = true;
                        }
                        FILTER_CLICKED = false;
                    } else {
                        if (!m_cLoading) {
                            m_cBankList.remove(m_cBankList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cBankList.size());
                            m_cLoading = false;
                        } else {
                            if (m_cBankList.size() > 0) {
                                m_cBankList.clear();
                                m_cRecycAdapter.notifyDataSetChanged();
                                m_cBankCountNo.setText("(" + m_cBankList.size() + " Banks)");
                                m_cObjMainActivity.hideDialog();
                                m_cLoading = false;
                                page = 1;
                            } else {
                                m_cNoData.setVisibility(View.VISIBLE);
                            }
                        }
                        FILTER_CLICKED = false;
                    }
                    m_cObjMainActivity.hideDialog();
                }
                break;
            case Constants.CITIES:
                CityAll lCityAll = (CityAll) response;
                if (lCityAll.getData().size() > 0) {
                    m_cCitiesList = new ArrayList<>();
                    m_cCitiesDic = new HashMap<>();
                    m_cCitiesList.add("Select City");
                    for (CityData lCityData : lCityAll.getData()) {
                        m_cCitiesList.add(lCityData.getName());
                        m_cCitiesDic.put(lCityData.getName(), lCityData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item, m_cCitiesList);
                    m_cSelectCitiesSpin.setAdapter(m_cSpinAdapter);
                }
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.PROJECTS:
                ProjectsAll lProjectsAll = (ProjectsAll) response;
                if (lProjectsAll.getProjectStruct().size() > 0) {
                    m_cProjectList = new ArrayList<>();
                    m_cProjectDic = new HashMap<>();
                    m_cProjectList.add("Select Project");
                    for (ProjectStruct lProject : lProjectsAll.getProjectStruct()) {
                        m_cProjectList.add(lProject.getProject().getData().getName());
                        m_cProjectDic.put(lProject.getProject().getData().getName(), lProject.getProject().getData().getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item, m_cProjectList);
                    m_cSelectProjectsSpin.setAdapter(m_cSpinAdapter);
                }
                m_cObjMainActivity.hideDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
        page = 1;
        if (!FILTER_CLICKED) {
            m_cObjMainActivity.hideDialog();
            FILTER_CLICKED = false;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.FROM_SPINNER:
                m_cCitiesPos = position;
                if (position >= 0) {
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cCitiesPos > 0) {
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    } else {
                        lParams.put(Constants.PAGINATE, Constants.ALL);
                    }
                    placeRequest(Constants.PROJECTS, ProjectsAll.class, lParams, false);
                }
                break;
            case R.id.TO_SPINNER:
                m_cProjectPos = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProjectClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, View pView) {

    }

    @Override
    public void onBankClicked(int pPostion, BankAll pBankAll, Bank pBank, View pView) {

    }

    @Override
    public void onLSRClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, QueriesData pQueriesData, View pView) {

    }

}
