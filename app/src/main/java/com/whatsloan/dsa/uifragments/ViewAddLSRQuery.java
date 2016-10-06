package com.whatsloan.dsa.uifragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfLsrQueries;
import com.whatsloan.dsa.interfaces.RecyclerBanksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Bank;
import com.whatsloan.dsa.model.BankAll;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.Queries;
import com.whatsloan.dsa.model.QueriesData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.AddLSRQuery;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 25/3/16.
 */
public class ViewAddLSRQuery extends DSAFragmentBaseClass implements RecyclerBanksListener {

    private CustomRecyclerAdapterForListOfLsrQueries m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;
    ArrayList<QueriesData> m_cQuerieList;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoDataAvailable;

    @Nullable
    @Bind(R.id.ADD_NEW_LSR)
    TextView m_cAddNewLsr;

    @Nullable
    @Bind(R.id.RECYC_LSR)
    RecyclerView m_cRecycLsr;

    Project m_cObjectProj;
    ProjectStruct m_cObjProjectStruct;
    int m_cPos;
//    private ScrollView m_cScroll;

    public ViewAddLSRQuery() {
        super();
    }

    public static ViewAddLSRQuery newInstance(int pPosition, ProjectStruct pObjProjectStruct, Project pObjectProj) {
        ViewAddLSRQuery lViewAddLSRQuery = new ViewAddLSRQuery();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putParcelable("ProjectObject", pObjectProj);
        args.putParcelable("ProjectStruct", pObjProjectStruct);
        lViewAddLSRQuery.setArguments(args);

        return lViewAddLSRQuery;
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
        m_cObjMainView = inflater.inflate(R.layout.new_or_add_lsr, container, false);
        ButterKnife.bind(this, m_cObjMainView);

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
        m_cObjMainActivity.m_cObjFragmentBase = ViewAddLSRQuery.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cObjectProj = getArguments().getParcelable("ProjectObject");
        m_cObjProjectStruct = getArguments().getParcelable("ProjectStruct");
        m_cQuerieList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(m_cObjMainActivity);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycLsr.setLayoutManager(m_cLayoutManager);
        m_cRecycLsr.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = m_cLayoutManager.getChildCount();
                    totalItemCount = m_cLayoutManager.getItemCount();
                    pastVisiblesItems = m_cLayoutManager.findFirstVisibleItemPosition();

                    int page = totalItemCount / 15;
                    /*if (m_cLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            m_cLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            doPagination(++page);
                        }
                    }*/
                }
            }
        });

        //Calling Single project api for LSR queries
        m_cObjMainActivity.displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.INSCLUDE, Constants.QUERIES);
        lParams.put(Constants.AGENT_ID, ""+m_cObjProjectStruct.getAgentId());
        lParams.put(Constants.BANK_ID, ""+m_cObjProjectStruct.getBankId());
            placeRequest(Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Project.class, lParams, false);
    }

    private void doPagination(int pPage) {
        m_cQuerieList.add(null);
        m_cRecycAdapter.notifyItemInserted(m_cQuerieList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        lParams.put(Constants.INSCLUDE, Constants.QUERIES);
        lParams.put(Constants.AGENT_ID, ""+m_cObjProjectStruct.getAgentId());
        lParams.put(Constants.BANK_ID, ""+m_cObjProjectStruct.getBankId());
        placeRequest(Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Project.class, lParams, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick(R.id.ADD_NEW_LSR)
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.ADD_NEW_LSR:
                lObjIntent = new Intent(m_cObjMainActivity, AddLSRQuery.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        Project lProject = (Project) response;
        Queries lQueries = lProject.getData().getQueries();

        if (lQueries.getData().size() > 0) {
            if (m_cLoading) {
                for (QueriesData lQueriesData : lQueries.getData()) {
                    m_cQuerieList.add(lQueriesData);
                }
                if (null != m_cQuerieList && m_cQuerieList.size() > 0) {
                    m_cRecycAdapter = new CustomRecyclerAdapterForListOfLsrQueries(m_cObjMainActivity, m_cQuerieList, m_cObjectProj, this);
                    m_cRecycLsr.setAdapter(m_cRecycAdapter);
                }
//                        m_cProjCountNo.setText("(" + m_cQuerieList.size() + " projects)");
            } else {
                m_cQuerieList.remove(m_cQuerieList.size() - 1);
                m_cRecycAdapter.notifyItemRemoved(m_cQuerieList.size());
                for (QueriesData lQueriesData : lQueries.getData()) {
                    m_cQuerieList.add(lQueriesData);
                }
//                        m_cProjCountNo.setText("(" + m_cQuerieList.size() + " projects)");
                m_cRecycAdapter.notifyItemInserted(m_cQuerieList.size());
                m_cLoading = true;
            }
            m_cNoDataAvailable.setVisibility(View.GONE);
        } else {
            if(m_cQuerieList.size() > 0) {
                m_cQuerieList.remove(m_cQuerieList.size() - 1);
                m_cRecycAdapter.notifyItemRemoved(m_cQuerieList.size());
                m_cLoading = false;
            }else {
                m_cNoDataAvailable.setVisibility(View.VISIBLE);
            }
        }
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void onProjectClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, View pView) {

    }

    @Override
    public void onBankClicked(int pPostion, BankAll pBankAll, Bank pBank, View pView) {

    }

    @Override
    public void onLSRClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, QueriesData pQueriesData, View pView) {
        Intent lObjIntent = new Intent(m_cObjMainActivity, AddLSRQuery.class);
        lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        lObjIntent.putExtra("ProjectObject", pProject);
        lObjIntent.putExtra("LSRObject", pQueriesData);
        startActivity(lObjIntent);
    }

}
