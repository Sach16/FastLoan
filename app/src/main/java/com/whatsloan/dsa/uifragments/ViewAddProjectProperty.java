package com.whatsloan.dsa.uifragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.ProjectsAll;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.NewProjectApproval;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 25/3/16.
 */
public class ViewAddProjectProperty extends DSAFragmentBaseClass{

    //view ids
    @Nullable
    @Bind(R.id.NAME_BUILDER)
    TextView m_cBuilderNameView;

    @Nullable
    @Bind(R.id.PROJECT_NAME)
    TextView m_cProjNameView;

    @Nullable
    @Bind(R.id.LOCATION)
    TextView m_cLocationView;

    @Nullable
    @Bind(R.id.CITY)
    TextView m_cCityView;

    @Nullable
    @Bind(R.id.BANK1)
    TextView m_cBank1View;

    /*@Nullable
    @Bind(R.id.BANK2)
    TextView m_cBank2View;*/

    @Nullable
    @Bind(R.id.ASSIGN_TEAM_MEMBERS)
    TextView m_cTeamMembersView;

    @Nullable
    @Bind(R.id.BANK_APPROVAL_DATE)
    TextView m_cBankAppDateView;

    @Nullable
    @Bind(R.id.LSR_INITIATION)
    TextView m_cLsrInvView;

    @Nullable
    @Bind(R.id.LSR_COMPLETION)
    TextView m_cLsrCompleView;

    //Add new ids

    //action buttons
    @Nullable
    @Bind(R.id.ADD_NEW_APPROVALS)
    TextView m_cAddNewApprov;

    @Nullable
    @Bind(R.id.EDIT_SAVE_CANCEL_LAY)
    LinearLayout m_cSaveCancelLay;

    @Nullable
    @Bind(R.id.BANK_APPROVAL_DATE_LAY)
    LinearLayout m_cBAPLay;

    @Nullable
    @Bind(R.id.SUBMIT_TXT)
    TextView m_cSubmitTxt;

    @Nullable
    @Bind(R.id.CANCEL_TXT)
    TextView m_cCancelTxt;

    @Nullable
    @Bind(R.id.LSR_INITIATION_LAY)
    LinearLayout m_clsrInitiationLay;

    @Nullable
    @Bind(R.id.POSESSION_DATE_LAY)
    LinearLayout m_cPosDateLay;

    @Nullable
    @Bind(R.id.POSESSION_DATE_TXT)
    TextView m_cPosDate;

    public static final int BANK_DATE_PICKER_ID = 4;
    public static final int INIT_DATE_PICKER_ID = 5;

    private int m_cCitiesPos = -1;
    private int m_cBankPos = -1;

    final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    Project m_cObjectProj;
    ProjectStruct m_cObjProjectStruct;
    private static boolean m_cIsNewProj;
    private static boolean m_cIsApproved;
    int m_cPos;
    private ScrollView m_cScroll;

    public ViewAddProjectProperty() {
        super();
    }

    public static ViewAddProjectProperty newInstance(int pPosition, Project pObjectProj, ProjectStruct pObjProjectStruct, boolean pIsNewProj, boolean pIsApproved) {
        ViewAddProjectProperty lViewAddProjectProperty = new ViewAddProjectProperty();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putParcelable("ProjectObject", pObjectProj);
        args.putParcelable("ProjectStruct", pObjProjectStruct);
        args.putBoolean("IsNewProj", pIsNewProj);
        args.putBoolean("IsApproved", pIsApproved);
        lViewAddProjectProperty.setArguments(args);

        return lViewAddProjectProperty;
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
        m_cObjMainView = inflater.inflate(R.layout.new_or_edit_project, container, false);
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
        m_cObjMainActivity.m_cObjFragmentBase = ViewAddProjectProperty.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cObjectProj = getArguments().getParcelable("ProjectObject");
        m_cObjProjectStruct = getArguments().getParcelable("ProjectStruct");
        m_cIsNewProj = getArguments().getBoolean("IsNewProj");
        m_cIsApproved = getArguments().getBoolean("IsApproved");
        m_cCalendar = Calendar.getInstance();

        //Calling Projects api
        if(m_cIsApproved) {
            m_cObjMainActivity.displayProgressBar(-1, "Loading...");
            HashMap<String, String> lllParams = new HashMap<>();
            lllParams.put(Constants.INSCLUDE, Constants.PROJECTS_BANKS + "," + Constants.PROJECTS_ADDRESS + "," + Constants.PROJECTS_OWNER + "," + Constants.ASSIGNEE + "," + Constants.PROJECTS_ATTACHMENTS);
            if (null != m_cObjectProj) {
                lllParams.put(Constants.PROJECTID, m_cObjectProj.getData().getUuid());
                lllParams.put(Constants.AGENT_UUID, m_cObjProjectStruct.getAgent().getData().getUuid());
            }
            placeRequest(Constants.PROJECTS, ProjectsAll.class, lllParams, false);
        }else {
            m_cObjMainActivity.displayProgressBar(-1, "Loading...");
            HashMap<String, String> lllParams = new HashMap<>();
            lllParams.put(Constants.INSCLUDE, Constants.PROJECTS_BANKS + "," + Constants.PROJECTS_ADDRESS + "," + Constants.PROJECTS_OWNER + "," + Constants.ASSIGNEE + "," + Constants.PROJECTS_ATTACHMENTS);
            if (null != m_cObjectProj) {
                lllParams.put(Constants.PROJECTID, m_cObjectProj.getData().getUuid());
                lllParams.put(Constants.AGENT_UUID, m_cObjProjectStruct.getAgent().getData().getUuid());
            }
            placeRequest(Constants.PROJECTSAPPROVAL, ProjectsAll.class, lllParams, false);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.ADD_NEW_APPROVALS, R.id.SUBMIT_TXT, R.id.CANCEL_TXT})
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.ADD_NEW_APPROVALS:
                lObjIntent = new Intent(m_cObjMainActivity, NewProjectApproval.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lObjIntent.putExtra("ProjectObject", m_cObjectProj);
                lObjIntent.putExtra("ProjectStruct", m_cObjProjectStruct);
                startActivity(lObjIntent);
                //use below code to edit in the same fragment
//                m_cIsNewProj = true;
//                init();
                break;
            case R.id.SUBMIT_TXT:
                break;
            case R.id.CANCEL_TXT:
                m_cIsNewProj = false;
                init();
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod){
            case Constants.PROJECTS:
                ProjectsAll lProjectsAll = (ProjectsAll) response;
                m_cObjProjectStruct = lProjectsAll.getProjectStruct().get(0);
                m_cObjectProj = lProjectsAll.getProjectStruct().get(0).getProject();

                inJect();

                m_cObjMainActivity.hideDialog();
                break;
            case Constants.PROJECTSAPPROVAL:
                ProjectsAll llProjectsAll = (ProjectsAll) response;
                m_cObjProjectStruct = llProjectsAll.getProjectStruct().get(0);
                m_cObjectProj = llProjectsAll.getProjectStruct().get(0).getProject();

                inJect();

                m_cObjMainActivity.hideDialog();
                break;
        }
    }

    private void inJect() {
        if (!m_cIsNewProj) {
            m_cObjMainView.findViewById(R.id.PROJECT_PROPERTY_VIEW).setVisibility(View.VISIBLE);
            m_cSaveCancelLay.setVisibility(View.GONE);
            m_cAddNewApprov.setVisibility(View.VISIBLE);
            m_cBuilderNameView.setText(m_cObjectProj.getData().getBuilderName());
            m_cProjNameView.setText(m_cObjectProj.getData().getName());
            StringBuffer lBuff = new StringBuffer();
            if (null != m_cObjectProj.getData().getAddress()) {
                lBuff.append(m_cObjectProj.getData().getAddress().getData().getAlphaStreet()+" ");
            }
            if (null != m_cObjectProj.getData().getAddress()) {
                lBuff.append(m_cObjectProj.getData().getAddress().getData().getBetaStreet() != null?
                        m_cObjectProj.getData().getAddress().getData().getBetaStreet() : "");
            }
            if(null != lBuff){
                m_cLocationView.setText(lBuff);
            }
            if (null != m_cObjectProj.getData().getAddress()) {
                m_cCityView.setText(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
            }
            m_cBank1View.setText(m_cObjProjectStruct.getBank().getData().getName());
            if (null != m_cObjProjectStruct.getAgent()) {
                m_cTeamMembersView.setText(m_cObjProjectStruct.getAgent().getData().getFirstName()+" "+m_cObjProjectStruct.getAgent().getData().getLastName());
            }
            // lsr date
            if (null != m_cObjectProj.getData().getLsrStartDate()) {
                m_cLsrInvView.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getLsrStartDate().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                m_cLsrCompleView.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getLsrEndDate().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            }else {
//                m_clsrInitiationLay.setVisibility(View.GONE);
            }
            // bank approval date
            if (null != m_cObjProjectStruct.getApprovedDate()) {
                m_cBankAppDateView.setText(DSAMacros.getDateFormat(null, m_cObjProjectStruct.getApprovedDate().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            }else {
//                m_cBAPLay.setVisibility(View.GONE);
            }
            // posession date
            if (null != m_cObjectProj.getData().getPosessionDate()) {
                m_cPosDate.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getPosessionDate().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            }else {
                m_cPosDateLay.setVisibility(View.GONE);
            }
            //TODO reopen add lsr approv bank approv dates
            if(m_cObjProjectStruct != null && m_cObjProjectStruct.getStatus().equalsIgnoreCase(Constants.PENDING)) {
//                m_clsrInitiationLay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

}