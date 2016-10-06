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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfApprovalProjects;
import com.whatsloan.dsa.interfaces.RecyclerBanksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Bank;
import com.whatsloan.dsa.model.BankAll;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.ProjectsAll;
import com.whatsloan.dsa.model.QueriesData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.NewEditProjectLsr;
import com.whatsloan.dsa.uiactivities.NewProjectApproval;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 24/3/16.
 */
public class BankProjectsForApproval extends DSAFragmentBaseClass implements RecyclerBanksListener{

    @Nullable
    @Bind(R.id.RECYC_APPROVAL)
    RecyclerView m_cRecycApproval;

    @Nullable
    @Bind(R.id.ADD_NEW_APPROVALS_LAY)
    RelativeLayout m_caddnewapprovalslay;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoData;


    ArrayList<ProjectStruct> m_cProjectList;
    private CustomRecyclerAdapterForListOfApprovalProjects m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;
    int m_cPos;
    private ScrollView m_cScroll;

    private static int page = 1;

    public BankProjectsForApproval() {
        super();
    }

    public static BankProjectsForApproval newInstance(int pPosition) {
        BankProjectsForApproval lBankProjectsForApproval = new BankProjectsForApproval();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        lBankProjectsForApproval.setArguments(args);

        return lBankProjectsForApproval;
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
        m_cObjMainView = inflater.inflate(R.layout.projects_for_approval, container, false);
        ButterKnife.bind(this, m_cObjMainView);

       /* m_cScroll = (ScrollView) m_cObjMainView.findViewById(R.id.SCROLL);
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
        m_cObjMainActivity.m_cObjFragmentBase = BankProjectsForApproval.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cProjectList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(m_cObjMainActivity);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycApproval.setLayoutManager(m_cLayoutManager);
        m_cRecycApproval.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            int lpage = page + 1;
                            doPagination(lpage);
                        }
                    }
                }
            }
        });

        //calling projects for approval api
        m_cObjMainActivity.displayProgressBar(-1, "Loading...");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.INSCLUDE, Constants.PROJECTS_BANKS + "," + Constants.PROJECTS_ADDRESS + "," + Constants.PROJECTS_OWNER + "," + Constants.ASSIGNEE + "," + Constants.PROJECTS_ATTACHMENTS);
        placeRequest(Constants.PROJECTSAPPROVAL, ProjectsAll.class, lParams, false);
    }

    private void doPagination(int pPage) {
        m_cProjectList.add(null);
        m_cRecycAdapter.notifyItemInserted(m_cProjectList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        lParams.put(Constants.INSCLUDE, Constants.PROJECTS_BANKS + "," + Constants.PROJECTS_ADDRESS + "," + Constants.PROJECTS_OWNER + "," + Constants.ASSIGNEE + "," + Constants.PROJECTS_ATTACHMENTS);
        placeRequest(Constants.PROJECTSAPPROVAL, ProjectsAll.class, lParams, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.ADD_NEW_APPROVALS_LAY)
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.ADD_NEW_APPROVALS_LAY:
                lObjIntent = new Intent(m_cObjMainActivity, NewProjectApproval.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lObjIntent.putExtra("IS_NEW_PROJECT", true);
                startActivity(lObjIntent);
//                m_cObjMainActivity.finish();
                break;
        }
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.PROJECTSAPPROVAL:
                ProjectsAll lProjectsAll = (ProjectsAll) response;
                if (lProjectsAll.getProjectStruct().size() > 0) {
                    if (m_cLoading) {
                        for (ProjectStruct lProject : lProjectsAll.getProjectStruct()) {
                            m_cProjectList.add(lProject);
                        }
                        if (null != m_cProjectList && m_cProjectList.size() > 0) {
                            m_cRecycAdapter = new CustomRecyclerAdapterForListOfApprovalProjects(m_cObjMainActivity, m_cProjectList, this);
                            m_cRecycApproval.setAdapter(m_cRecycAdapter);
                        }
//                        m_cProjCountNo.setText("(" + m_cProjectList.size() + " projects)");
                    }else {
                        m_cProjectList.remove(m_cProjectList.size() - 1);
                        m_cRecycAdapter.notifyItemRemoved(m_cProjectList.size());
                        for (ProjectStruct lProject : lProjectsAll.getProjectStruct()) {
                            m_cProjectList.add(lProject);
                        }
//                        m_cProjCountNo.setText("(" + m_cProjectList.size() + " projects)");
                        m_cRecycAdapter.notifyItemInserted(m_cProjectList.size());
                        m_cLoading = true;
                    }
                    m_cNoData.setVisibility(View.GONE);
                } else {
                    if(m_cProjectList.size() > 0) {
                        m_cProjectList.remove(m_cProjectList.size() - 1);
                        m_cRecycAdapter.notifyItemRemoved(m_cProjectList.size());
                        m_cLoading = false;
                        page = 1;
                    }else {
                        m_cNoData.setVisibility(View.VISIBLE);
                    }
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
    }

    @Override
    public void onProjectClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, View pView) {
        Intent lObjIntent;
        lObjIntent = new Intent(m_cObjMainActivity, NewEditProjectLsr.class);
        lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        lObjIntent.putExtra("IS_NEW_PROJECT", false);
        lObjIntent.putExtra("APPROVED", false);
        lObjIntent.putExtra("PROJECT", pProject);
        lObjIntent.putExtra("PROJECTSTRUCT", pProjectStruct);
        startActivity(lObjIntent);
//        m_cObjMainActivity.finish();
    }

    @Override
    public void onBankClicked(int pPostion, BankAll pBankAll, Bank pBank, View pView) {

    }

    @Override
    public void onLSRClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, QueriesData pQueriesData, View pView) {

    }

}
