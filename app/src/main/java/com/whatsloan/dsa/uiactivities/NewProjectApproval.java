package com.whatsloan.dsa.uiactivities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.RoundedCornersTransformation;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.BanksAll;
import com.whatsloan.dsa.model.BanksData;
import com.whatsloan.dsa.model.BuilderAll;
import com.whatsloan.dsa.model.BuilderData;
import com.whatsloan.dsa.model.CityAll;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.Members;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;
import com.whatsloan.dsa.utils.ScalingUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 24/3/16.
 */
public class NewProjectApproval extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    // status Id's
    @Nullable
    @Bind(R.id.STATUS_IMG_BUILDER)
    ImageView m_cBuilderStatImg;

    @Nullable
    @Bind(R.id.STATUS_LINE_BUILDER)
    ImageView m_cBuilderStatLine;

    @Nullable
    @Bind(R.id.STATUS_PROJECT_IMG)
    ImageView m_cNameStatImg;

    @Nullable
    @Bind(R.id.STATUS_PROJECT_LINE)
    ImageView m_cNameStatLine;

    @Nullable
    @Bind(R.id.STATUS_LOCATION_IMG)
    ImageView m_cLocStatImg;

    @Nullable
    @Bind(R.id.STATUS_LOCATION_LINE)
    ImageView m_cLocStatLine;

    @Nullable
    @Bind(R.id.STATUS_CITY_IMG)
    ImageView m_cCityStatImg;

    @Nullable
    @Bind(R.id.STATUS_CITY_LINE)
    ImageView m_cCityStatLine;

    @Nullable
    @Bind(R.id.STATUS_BANK_IMG)
    ImageView m_cBankStatImg;

    @Nullable
    @Bind(R.id.STATUS_BANK_LINE)
    ImageView m_cBankStatLine;

    @Nullable
    @Bind(R.id.STATUS_TEAM_IMG)
    ImageView m_cTeamStatImg;

    @Nullable
    @Bind(R.id.STATUS_STATUS_IMG)
    ImageView m_cUnitStatImg;

    @Nullable
    @Bind(R.id.STATUS_STATUS_LINE)
    ImageView m_cUnitStatLine;

    @Nullable
    @Bind(R.id.STATUS_POSSESSION_IMG)
    ImageView m_cPossStatImg;

    @Nullable
    @Bind(R.id.STATUS_POSSESSION_LINE)
    ImageView m_cPossStatLine;

    @Nullable
    @Bind(R.id.STATUS_PROJ_STATUS_IMG)
    ImageView m_cStatusStatImg;

    @Nullable
    @Bind(R.id.STATUS_STARTD_IMG)
    ImageView m_cDateStatImg;

    @Nullable
    @Bind(R.id.STATUS_STARTD_LINE)
    ImageView m_cDateStatLine;

    @Nullable
    @Bind(R.id.STATUS_ENDD_IMG)
    ImageView m_cDateEndImg;

    @Nullable
    @Bind(R.id.STATUS_ENDD_LINE)
    ImageView m_cDateEndLine;

    @Nullable
    @Bind(R.id.STATUS_PROJ_STATUS_LINE)
    ImageView m_cStatusStatLine;

    @Nullable
    @Bind(R.id.STATUS_TEAM_LINE)
    ImageView m_cTeamStatLine;

    @Nullable
    @Bind(R.id.STATUS_PHOTO_IMG)
    ImageView m_cPhotoStatImg;

    //edit Id's

    @Nullable
    @Bind(R.id.BUILDER_SPINNER)
    Spinner m_cSpinBuilderEdit;

    @Nullable
    @Bind(R.id.PROJECT_DESCRIPTION_EDIT)
    EditText m_cNameedit;

    @Nullable
    @Bind(R.id.LOCATION_DESCRIPTION_EDIT)
    EditText m_cLocedit;

    @Nullable
    @Bind(R.id.CITY_SPINNER)
    Spinner m_cSpinCityEdit;

    @Nullable
    @Bind(R.id.BANK_SPINNER)
    Spinner m_cSpinBankEdit;

    @Nullable
    @Bind(R.id.STATUS_DESCRIPTION_EDIT)
    EditText m_cUnitdetledit;

    @Nullable
    @Bind(R.id.TEAM_SPINNER)
    Spinner m_cSpinTeamEdit;

    @Nullable
    @Bind(R.id.POSSESSION_TXT_LAY)
    RelativeLayout m_cPossesPickrEdit;

    @Nullable
    @Bind(R.id.POSSESSION_DATE_TXT)
    TextView m_cPosDateTxtedit;

    @Nullable
    @Bind(R.id.STARTD_TXT_LAY)
    RelativeLayout m_cDateStartLayPickEdit;

    @Nullable
    @Bind(R.id.ENDD_TXT_LAY)
    RelativeLayout m_cDateEndLayPickEdit;

    @Nullable
    @Bind(R.id.STARTD_MAIN_TXT)
    TextView m_cStartDateTxtedit;

    @Nullable
    @Bind(R.id.ENDD_MAIN_TXT)
    TextView m_cEndDateTxtedit;

    @Nullable
    @Bind(R.id.STATUS_LAY)
    RelativeLayout m_cStatusLay;

    @Nullable
    @Bind(R.id.PROJ_STATUS_SPINNER)
    Spinner m_cSpinStatusEdit;

    @Nullable
    @Bind(R.id.RELEV_LAY)
    RelativeLayout m_cRelLay;

    @Nullable
    @Bind(R.id.STARTD_LAY)
    RelativeLayout m_cstartdLay;

    @Nullable
    @Bind(R.id.ENDD_LAY)
    RelativeLayout m_cenddLay;

    @Nullable
    @Bind(R.id.TAKE_PHOTO_TXT)
    TextView m_cPhotoTakeTxtt;

    @Nullable
    @Bind(R.id.GALLERY_PHOTO_TXT)
    TextView m_cPhotoChooseTxt;

    @Nullable
    @Bind(R.id.PHOTO_URL_LINK)
    TextView m_cPhotoUrlLink;

    @Nullable
    @Bind(R.id.PROJ_IMG)
    ImageView m_cProjImgView;

    //lsr init comp dates
    @Bind({ R.id.STARTD_LAY, R.id.ENDD_LAY})
    List<RelativeLayout> nameViews;


    ArrayList<String> m_cBuilderList;
    HashMap<String, String> m_cBuilderDic;
    ArrayList<String> m_cBankList;
    HashMap<String, String> m_cBankDic;
    ArrayList<String> m_cCitiesList;
    HashMap<String, String> m_cCitiesDic;
    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;
    ArrayList<String> m_cStatusList;
    HashMap<String, String> m_cStatusDic;
    ArrayAdapter<String> m_cSpinAdapter;

    private int m_cBuildersPos = -1;
    private int m_cCitiesPos = -1;
    private int m_cBankPos = -1;
    private int m_cTeamsPos = -1;
    private int m_cStatusPos = -1;

    public static final int TAKE_PICTURE = 101;
    private Uri m_cSelectedImageUri;
    boolean m_cImageProcessing;
    private Bitmap m_cObjSelectedBitMap;

    ProjectStruct m_cObjProjectStruct;
    Project m_cObjectProj;
    private Calendar m_cCalendar;
    public static final int POSS_DATE_PICKER_ID = 102;
    public static final int FROM_DATE_PICKER_ID = 111;
    public static final int TO_DATE_PICKER_ID = 112;
//    StringBuffer m_cStartEndDateBuff;
    private DatePickerDialog m_cDatePickerDialog;
    final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.new_project_approval);
        ButterKnife.bind(this);
        setTitle("New Project Approval", false, true, true, false);

        m_cObjectProj = getIntent().getParcelableExtra("ProjectObject");
        m_cObjProjectStruct = getIntent().getParcelableExtra("ProjectStruct");

        if (null != m_cObjectProj) {
            setTitle(m_cObjectProj.getData().getName(), false, true, true, false);
        } else {
            setTitle("New Project Approval", false, true, true, false);
        }
        init();

    }

    private void init() {
        //Add Option
        m_cstartdLay.setVisibility(View.GONE);
        m_cenddLay.setVisibility(View.GONE);
        ButterKnife.apply(nameViews, ENABLED, true);

        //Edit Option
        if (null != m_cObjectProj) {
            m_cNameedit.setText(m_cObjectProj.getData().getName());
            colorView(m_cNameStatImg, m_cNameStatLine, true);
            m_cLocedit.setText(m_cObjectProj.getData().getAddress().getData().getAlphaStreet() + " " + (m_cObjectProj.getData().getAddress().getData().getBetaStreet()
                    != null ? m_cObjectProj.getData().getAddress().getData().getBetaStreet() : ""));
            colorView(m_cLocStatImg, m_cLocStatLine, true);
            m_cUnitdetledit.setText("" + m_cObjectProj.getData().getUnitNumber());
            colorView(m_cUnitStatImg, m_cUnitStatLine, true);

            try{
                m_cStartDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getLsrStartDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cDateStatImg, m_cDateStatLine, true);
                m_cEndDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getLsrEndDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cDateEndImg, m_cDateEndLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                m_cPosDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cObjectProj.getData().getPosessionDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cPossStatImg, m_cPossStatLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

            m_cSpinBuilderEdit.setEnabled(false);
//            m_cNameedit.setEnabled(false);
//            m_cLocedit.setEnabled(false);
//            m_cSpinCityEdit.setEnabled(false);
//            m_cSpinBankEdit.setEnabled(false);
//            m_cUnitdetledit.setEnabled(false);
            ButterKnife.apply(nameViews, ENABLED, false);

            if (m_cObjProjectStruct != null && m_cObjProjectStruct.getStatus().equalsIgnoreCase(Constants.PENDING)) {
                m_cstartdLay.setVisibility(View.GONE);
                m_cenddLay.setVisibility(View.GONE);
            } else {
                m_cstartdLay.setVisibility(View.VISIBLE);
                m_cenddLay.setVisibility(View.VISIBLE);
                m_cStatusLay.setVisibility(View.GONE);
            }
            try {
                Picasso.with(this)
                        .load(m_cObjectProj.getData().getAttachments().getData().get(0).getUri())
                        .error(R.drawable.profile_placeholder)
                        .placeholder(R.drawable.profile_placeholder)
                        .transform(new RoundedCornersTransformation(10, 0))
                        .fit()
                        .into(m_cProjImgView);
                m_cPhotoUrlLink.setText(m_cObjectProj.getData().getAttachments().getData().get(0).getUri());
                colorView(m_cPhotoStatImg, null, true);
            } catch (Exception e) {
                m_cProjImgView.setBackgroundResource(R.drawable.profile_placeholder);
                colorView(m_cPhotoStatImg, null, false);
                e.printStackTrace();
            }
        }
        m_cNameedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    colorView(m_cNameStatImg, m_cNameStatLine, true);
                } else {
                    colorView(m_cNameStatImg, m_cNameStatLine, false);
                }

            }
        });
        m_cLocedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    colorView(m_cLocStatImg, m_cLocStatLine, true);
                } else {
                    colorView(m_cLocStatImg, m_cLocStatLine, false);
                }
            }
        });
        m_cUnitdetledit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    colorView(m_cUnitStatImg, m_cUnitStatLine, true);
                } else {
                    colorView(m_cUnitStatImg, m_cUnitStatLine, false);
                }
            }
        });
        m_cSpinBuilderEdit.setOnItemSelectedListener(this);
        m_cSpinCityEdit.setOnItemSelectedListener(this);
        m_cSpinBankEdit.setOnItemSelectedListener(this);
        m_cSpinTeamEdit.setOnItemSelectedListener(this);
        m_cSpinStatusEdit.setOnItemSelectedListener(this);

        m_cStatusList = new ArrayList<>();
        m_cStatusList.add("Select Status");
        m_cStatusList.add("PENDING");
        m_cStatusList.add("APPROVED");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cStatusList);
        m_cSpinStatusEdit.setAdapter(m_cSpinAdapter);

        displayProgressBar(-1, "Loading");
        RequestManager.getInstance(this).placeRequest(Constants.BUILDERS, BuilderAll.class, this, null, false);

        //Calling Cities api
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        RequestManager.getInstance(this).placeRequest(Constants.CITIES, CityAll.class, this, lParams, false);

        //Calling Banks api
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.PAGINATE, Constants.ALL);
        RequestManager.getInstance(this).placeRequest(Constants.TEAMMEMBERS_BANKS, BanksAll.class, this, llParams, false);

        //Calling Team api
        HashMap<String, String> lllParams = new HashMap<>();
        lllParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.USERTEAM_MEMBERS, Members.class, this, lllParams, false);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what) {
            case TAKE_PICTURE:
                new captureImage().execute("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        displayProgressBar(-1, "Loading...");
                        long ldate = 0;
                        Uri lUriwodata = null;
                        try {
                            lUriwodata = data.getData();
                        } catch (Exception e) {
                            lUriwodata = null;
                        }
                        if (data != null && lUriwodata != null) {
                            File lfile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
                            long lo = lfile.length()/1024;

                            m_cSelectedImageUri = data == null ? null : data.getData();
                            String lImageName = m_cImageGUID + ".jpg";

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            Cursor cursor = getContentResolver().query(m_cSelectedImageUri,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            copyFile(picturePath, lfile.getAbsolutePath());

                            //set the link in the uitext and show img
                            try {
                                Picasso.with(this)
                                        .load(new File(picturePath.toString()))
                                        .error(R.drawable.profile_placeholder)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .transform(new RoundedCornersTransformation(10, 0))
                                        .config(Bitmap.Config.RGB_565)
                                        .fit()
                                        .into(m_cProjImgView);
                                m_cPhotoUrlLink.setText(picturePath.toString());
                                colorView(m_cPhotoStatImg, null, true);
                            } catch (Exception e) {
                                m_cProjImgView.setBackgroundResource(R.drawable.profile_placeholder);
                                colorView(m_cPhotoStatImg, null, false);
                                e.printStackTrace();
                            }

                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);
                        } else {
                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);

                            File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
                            long lo = lFile.length()/1024;

                            //set the link in the uitext and show img
                            try {
                                Picasso.with(this)
                                        .load(lFile)
                                        .error(R.drawable.profile_placeholder)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .config(Bitmap.Config.RGB_565)
                                        .transform(new RoundedCornersTransformation(10, 0))
                                        .fit()
                                        .into(m_cProjImgView);
                                m_cPhotoUrlLink.setText(lFile.toString());
                                colorView(m_cPhotoStatImg, null, true);
                            } catch (Exception e) {
                                m_cProjImgView.setBackgroundResource(R.drawable.profile_placeholder);
                                colorView(m_cPhotoStatImg, null, false);
                                e.printStackTrace();
                            }
                        }
//						deletePicFromDCIM();
                    } catch (Exception e) {
                        displaySnack(m_cRelLay, "Unable to retrieve Image");
                        e.printStackTrace();
                        Log.w("IMAGE_NAME  : ", m_cImageGUID);
                    }
                }
                /*else {
                    finish();
                }*/
                break;
        }
    }

    public boolean validateAdd() {
        boolean lRetVal = false;
        String lName = m_cNameedit.getText().toString().trim();
        String lLoc = m_cLocedit.getText().toString().trim();
        String lUnit = m_cUnitdetledit.getText().toString().trim();
        String lPos = m_cPosDateTxtedit.getText().toString().trim();
        String lDateRange = m_cStartDateTxtedit.getText().toString().trim();
        String lPhotoLink = m_cPhotoUrlLink.getText().toString().trim();
        if (m_cBuildersPos > 0) {
            colorView(m_cBuilderStatImg, m_cBuilderStatLine, true);
            if (lName.length() > 0 && isAlphabetic(lName)) {
                colorView(m_cNameStatImg, m_cNameStatLine, true);
                if (lLoc.length() > 0 && isAlphabetic(lLoc)) {
                    colorView(m_cLocStatImg, m_cLocStatLine, true);
                    if (m_cCitiesPos > 0) {
                        colorView(m_cCityStatImg, m_cCityStatLine, true);
                        if (m_cBankPos > 0) {
                            colorView(m_cBankStatImg, m_cBankStatLine, true);
                            if (lUnit.length() > 0) {
                                colorView(m_cUnitStatImg, m_cUnitStatLine, true);
                                if (m_cTeamsPos > 0) {
                                    colorView(m_cTeamStatImg, m_cTeamStatLine, true);
                                    if (lPos.length() > 0) {
                                        colorView(m_cPossStatImg, m_cPossStatLine, true);
                                        if (m_cStatusPos > 0) {
                                            colorView(m_cStatusStatImg, m_cStatusStatLine, true);
                                            /*if (lPhotoLink.length() > 0) {
                                                colorView(m_cPhotoStatImg, null, true);*/
                                                lRetVal = true;
                                            /*} else {
                                                colorView(m_cPhotoStatImg, null, false);
                                            }*/
                                        } else {
                                            colorView(m_cStatusStatImg, m_cStatusStatLine, false);
                                        }
                                    } else {
                                        colorView(m_cPossStatImg, m_cPossStatLine, false);
                                    }
                                } else {
                                    colorView(m_cTeamStatImg, m_cTeamStatLine, false);
                                }
                            } else {
                                colorView(m_cUnitStatImg, m_cUnitStatLine, false);
                            }
                        } else {
                            colorView(m_cBankStatImg, m_cBankStatLine, false);
                        }
                    } else {
                        colorView(m_cCityStatImg, m_cCityStatLine, false);
                    }
                } else {
                    colorView(m_cLocStatImg, m_cLocStatLine, false);
                }
            } else {
                colorView(m_cNameStatImg, m_cNameStatLine, false);
            }
        } else {
            colorView(m_cBuilderStatImg, m_cBuilderStatLine, false);
        }
        return lRetVal;
    }

    private boolean validateEditApp() {
        boolean lRetVal = false;
        String lName = m_cNameedit.getText().toString().trim();
        String lPos = m_cPosDateTxtedit.getText().toString().trim();
        String lLoc = m_cLocedit.getText().toString().trim();
        String lUnit = m_cUnitdetledit.getText().toString().trim();
        String lStartDate = m_cStartDateTxtedit.getText().toString().trim();
        String lEndDate = m_cEndDateTxtedit.getText().toString().trim();
        if (lName.length() > 0 && isAlphabetic(lName)) {
            colorView(m_cNameStatImg, m_cNameStatLine, true);
            if (lLoc.length() > 0 && isAlphabetic(lLoc)) {
                colorView(m_cLocStatImg, m_cLocStatLine, true);
                if (m_cCitiesPos > 0) {
                    colorView(m_cCityStatImg, m_cCityStatLine, true);
                    if (m_cBankPos > 0) {
                        colorView(m_cBankStatImg, m_cBankStatLine, true);
                        if (lUnit.length() > 0) {
                            colorView(m_cUnitStatImg, m_cUnitStatLine, true);
                            if (m_cTeamsPos > 0) {
                                colorView(m_cTeamStatImg, m_cTeamStatLine, true);
                                if (lPos.length() > 0) {
                                    colorView(m_cPossStatImg, m_cPossStatLine, true);
                                    lRetVal = true;
                                } else {
                                    colorView(m_cPossStatImg, m_cPossStatLine, false);
                                }
                            } else {
                                colorView(m_cTeamStatImg, m_cTeamStatLine, false);
                            }
                        } else {
                            colorView(m_cUnitStatImg, m_cUnitStatLine, false);
                        }
                    } else {
                        colorView(m_cBankStatImg, m_cBankStatLine, false);
                    }
                } else {
                    colorView(m_cCityStatImg, m_cCityStatLine, false);
                }
            } else {
                colorView(m_cLocStatImg, m_cLocStatLine, false);
            }
        } else {
            colorView(m_cNameStatImg, m_cNameStatLine, false);
        }
        return lRetVal;
    }

    private boolean validateEditPen() {
        boolean lRetVal = false;
        String lName = m_cNameedit.getText().toString().trim();
        String lLoc = m_cLocedit.getText().toString().trim();
        String lUnit = m_cUnitdetledit.getText().toString().trim();
        String lPos = m_cPosDateTxtedit.getText().toString().trim();
        String lDateRange = m_cStartDateTxtedit.getText().toString().trim();
        if (lName.length() > 0 && isAlphabetic(lName)) {
            colorView(m_cNameStatImg, m_cNameStatLine, true);
            if (lLoc.length() > 0 && isAlphabetic(lLoc)) {
                colorView(m_cLocStatImg, m_cLocStatLine, true);
                if (m_cCitiesPos > 0) {
                    colorView(m_cCityStatImg, m_cCityStatLine, true);
                    if (m_cBankPos > 0) {
                        colorView(m_cBankStatImg, m_cBankStatLine, true);
                        if (lUnit.length() > 0) {
                            colorView(m_cUnitStatImg, m_cUnitStatLine, true);
                            if (m_cTeamsPos > 0) {
                                colorView(m_cTeamStatImg, m_cTeamStatLine, true);
                                if (lPos.length() > 0) {
                                    colorView(m_cPossStatImg, m_cPossStatLine, true);
                                    if (m_cStatusPos > 0) {
                                        colorView(m_cStatusStatImg, null, true);
                                        lRetVal = true;
                                    } else {
                                        colorView(m_cStatusStatImg, null, false);
                                    }
                                } else {
                                    colorView(m_cPossStatImg, m_cPossStatLine, false);
                                }
                            } else {
                                colorView(m_cTeamStatImg, m_cTeamStatLine, false);
                            }
                        } else {
                            colorView(m_cUnitStatImg, m_cUnitStatLine, false);
                        }
                    } else {
                        colorView(m_cBankStatImg, m_cBankStatLine, false);
                    }
                } else {
                    colorView(m_cCityStatImg, m_cCityStatLine, false);
                }
            } else {
                colorView(m_cLocStatImg, m_cLocStatLine, false);
            }
        } else {
            colorView(m_cNameStatImg, m_cNameStatLine, false);
        }
        return lRetVal;
    }

    @OnClick({R.id.CANCEL_BTN_TXT, R.id.SUBMIT_BTN_TXT, R.id.PROJECT_DESCRIPTION_EDIT, R.id.LOCATION_DESCRIPTION_EDIT
            , R.id.POSSESSION_TXT_LAY, R.id.STARTD_TXT_LAY, R.id.ENDD_TXT_LAY, R.id.TAKE_PHOTO_TXT, R.id.GALLERY_PHOTO_TXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.CANCEL_BTN_TXT:
                onBackPressed();
                break;
            case R.id.SUBMIT_BTN_TXT:
                if (null != m_cObjectProj) {
                    if (m_cObjProjectStruct != null && m_cObjProjectStruct.getStatus().equalsIgnoreCase(Constants.PENDING)) {
                        if (validateEditPen()) {
                            displayProgressBar(-1, "Loading...");
                            HashMap<String, String> lParams = new HashMap<>();
                            lParams.put(Constants.NAME, m_cNameedit.getText().toString());
                            lParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                            lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                            lParams.put(Constants.STREET, m_cLocedit.getText().toString());
                            lParams.put(Constants.UNIT_DETAILS, m_cUnitdetledit.getText().toString());
                            lParams.put(Constants.POSSESSION_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cPosDateTxtedit.getText().toString()));
//                            String[] lDates = m_cStartDateTxtedit.getText().toString().split("to");
//                            lParams.put(Constants.LSR_START_DATE, lDates[0].trim() + " 00:00:00");
//                            lParams.put(Constants.LSR_END_DATE, lDates[1].trim() + " 00:00:00");
                            try {
                                lParams.put(Constants.ASSIGNEE, m_cMembersDic.get(m_cMembersList.get(m_cTeamsPos)));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            lParams.put(Constants.STATUS, m_cStatusList.get(1));
                        /* banks/<bank_uuid>/projects/<project_uuid> */
//                            if(m_cPhotoUrlLink.getText().toString().contains("https://")) {
                                RequestManager.getInstance(this).placePutRequest(Constants.apiMethodEx(Constants.BANKS, m_cObjProjectStruct.getBank().getData().getUuid())
                                        + "/" + Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Response.class, this, lParams, true);
                           //with img
                           /* }else {
                                lParams.put(Constants._METHOD, "PUT");
                                RequestManager.getInstance(this).placeMultiPartRequest(Constants.apiMethodEx(Constants.BANKS, m_cObjProjectStruct.getBank().getData().getUuid())
                                                + "/" + Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Response.class, this, lParams,
                                        new File(m_cPhotoUrlLink.getText().toString()), Constants.PROJECT_PICTURE);
                            }*/
                        }
                    } else {
                        if (validateEditApp()) {
                            displayProgressBar(-1, "Loading...");
                            HashMap<String, String> lParams = new HashMap<>();
                            lParams.put(Constants.NAME, m_cNameedit.getText().toString());
                            lParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                            lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                            lParams.put(Constants.STREET, m_cLocedit.getText().toString());
                            lParams.put(Constants.UNIT_DETAILS, m_cUnitdetledit.getText().toString());
                            lParams.put(Constants.POSSESSION_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cPosDateTxtedit.getText().toString()));
//                            lParams.put(Constants.LSR_START_DATE,  DSAMacros.getDateFormatYYYYMMDD(null, m_cStartDateTxtedit.getText().toString()));
//                            lParams.put(Constants.LSR_END_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cEndDateTxtedit.getText().toString()));
                            lParams.put(Constants.ASSIGNEE, m_cMembersDic.get(m_cMembersList.get(m_cTeamsPos)));
                            lParams.put(Constants.STATUS, m_cStatusList.get(2));
                        /* banks/<bank_uuid>/projects/<project_uuid> */
//                            if(m_cPhotoUrlLink.getText().toString().contains("https://")) {
                                RequestManager.getInstance(this).placePutRequest(Constants.apiMethodEx(Constants.BANKS, m_cObjProjectStruct.getBank().getData().getUuid())
                                        + "/" + Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Response.class, this, lParams, true);
                            //with img
                            /*}else {
                                lParams.put(Constants._METHOD, "PUT");
                                RequestManager.getInstance(this).placeMultiPartRequest(Constants.apiMethodEx(Constants.BANKS, m_cObjProjectStruct.getBank().getData().getUuid())
                                                + "/" + Constants.apiMethodEx(Constants.SINGLE_PROJECT, m_cObjectProj.getData().getUuid()), Response.class, this, lParams,
                                        new File(m_cPhotoUrlLink.getText().toString()), Constants.PROJECT_PICTURE);
                            }*/
                        }
                    }
                } else {
                    if (validateAdd()) {
                        displayProgressBar(-1, "Loading...");
                        HashMap<String, String> lParams = new HashMap<>();
                        lParams.put(Constants.NAME, m_cNameedit.getText().toString());
                        lParams.put(Constants.BUILDER_UUID, m_cBuilderDic.get(m_cBuilderList.get(m_cBuildersPos)));
                        lParams.put(Constants.STREET, m_cLocedit.getText().toString());
                        lParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                        lParams.put(Constants.ASSIGNEE, m_cMembersDic.get(m_cMembersList.get(m_cTeamsPos)));
                        //status not required for add
//                        lParams.put(Constants.STATUS, m_cStatusList.get(m_cStatusPos));
                        lParams.put(Constants.UNIT_DETAILS, m_cUnitdetledit.getText().toString());
                        lParams.put(Constants.POSSESSION_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cPosDateTxtedit.getText().toString()));
                        if (null != m_cObjectProj) {
                            lParams.put(Constants.PROJECTID, m_cObjectProj.getData().getUuid());
                        }
                        //with file
//                        RequestManager.getInstance(this).placeMultiPartRequest(Constants.SINGLE_PROJECT, Response.class, this, lParams,
//                                new File(m_cPhotoUrlLink.getText().toString()), Constants.PROJECT_PICTURE);

                        RequestManager.getInstance(this).placeRequest(Constants.SINGLE_PROJECT, Response.class, this, lParams,
                                true);
                    }
                }
                break;
            case R.id.POSSESSION_TXT_LAY:
                showDatePickerDialog(POSS_DATE_PICKER_ID);
                break;
            case R.id.STARTD_TXT_LAY:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.ENDD_TXT_LAY:
                showDatePickerDialog(TO_DATE_PICKER_ID);
                break;
            case R.id.TAKE_PHOTO_TXT:
                takePhoto();
                break;
            case R.id.GALLERY_PHOTO_TXT:
                showGallery();
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        Intent lObjIntent;
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.BUILDERS:
                BuilderAll builderAll = (BuilderAll) response;
                m_cBuilderList = new ArrayList<>();
                m_cBuilderDic = new HashMap<>();
                if (builderAll.getData().size() > 0) {
                    m_cBuilderList.add("Select Builder");
                }
                for (BuilderData lBuilderData : builderAll.getData()) {
                    m_cBuilderList.add(lBuilderData.getName());
                    m_cBuilderDic.put(lBuilderData.getName(), lBuilderData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cBuilderList);
                m_cSpinBuilderEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cBuilderList.indexOf(m_cObjectProj.getData().getBuilderName().toString());
                        m_cSpinBuilderEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.hideDialog();
                break;
            case Constants.CITIES:
                CityAll lCityAll = (CityAll) response;
                m_cCitiesList = new ArrayList<>();
                m_cCitiesDic = new HashMap<>();
                if (lCityAll.getData().size() > 0) {
                    m_cCitiesList.add("Select City");
                }
                for (CityData lCityData : lCityAll.getData()) {
                    m_cCitiesList.add(lCityData.getName());
                    m_cCitiesDic.put(lCityData.getName(), lCityData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cCitiesList);
                m_cSpinCityEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.hideDialog();
                break;
            case Constants.TEAMMEMBERS_BANKS:
                BanksAll lBanksAll = (BanksAll) response;
                m_cBankList = new ArrayList<>();
                m_cBankDic = new HashMap<>();
                if (lBanksAll.getBanks().size() > 0) {
                    m_cBankList.add("Select Bank");
                }
                for (BanksData lBank : lBanksAll.getBanks()) {
                    m_cBankList.add(lBank.getName());
                    m_cBankDic.put(lBank.getName(), lBank.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cBankList);
                m_cSpinBankEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cBankList.indexOf(m_cObjectProj.getData().getBanks().getData().get(0).getName().toString());
                        m_cSpinBankEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.hideDialog();
                break;
            case Constants.USERTEAM_MEMBERS:
                Members lMembers = (Members) response;
                m_cMembersList = new ArrayList<>();
                m_cMembersDic = new HashMap<>();
                if (lMembers.getData().size() > 0) {
                    m_cMembersList.add("Select Member");
                }
                for (MembersData lMembersData : lMembers.getData()) {
                    m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                    m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cMembersList);
                m_cSpinTeamEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cMembersList.indexOf(m_cObjProjectStruct.getAgent().getData().getFirstName() + " " + m_cObjProjectStruct.getAgent().getData().getLastName());
                        m_cSpinTeamEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.hideDialog();
                break;
            case Constants.SINGLE_PROJECT:
                Response lResponse = (Response) response;
                lObjIntent = new Intent(this, BankApprovedProjects.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            default:
                if (apiMethod.contains(Constants.BANKS)) {
                    lObjIntent = new Intent(this, BankApprovedProjects.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        switch (apiMethod) {
            case Constants.SINGLE_PROJECT:
                hideDialog();
                break;
        }
        hideDialog();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.BUILDER_SPINNER:
                m_cBuildersPos = position;
                if (position > 0) {
                    colorView(m_cBuilderStatImg, m_cBuilderStatLine, true);
                } else if (position == 0) {
//                    colorView(m_cBuilderStatImg, m_cBuilderStatLine, false);
                }
                break;
            case R.id.CITY_SPINNER:
                m_cCitiesPos = position;
                if (position > 0) {
                    colorView(m_cCityStatImg, m_cCityStatLine, true);
//                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cCitiesPos > 0) {
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    } else {
                        lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.TEAMMEMBERS_BANKS, BanksAll.class, this, lParams, false);
                } else {
//                    colorView(m_cCityStatImg, m_cCityStatLine, false);
                }
                break;
            case R.id.BANK_SPINNER:
                m_cBankPos = position;
                if (position > 0) {
                    colorView(m_cBankStatImg, m_cBankStatLine, true);
                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cBankPos > 0) {
                        lParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                    }
                    lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
                    RequestManager.getInstance(this).placeRequest(Constants.USERTEAM_MEMBERS, Members.class, this, lParams, false);
                } else if (position == 0) {
//                    colorView(m_cBankStatImg, m_cBankStatLine, false);
                }
                break;
            case R.id.TEAM_SPINNER:
                m_cTeamsPos = position;
                if (position > 0) {
                    colorView(m_cTeamStatImg, m_cBankStatLine, true);
                } else if (position == 0) {
//                    colorView(m_cTeamStatImg, m_cBankStatLine, false);
                }
                break;
            case R.id.PROJ_STATUS_SPINNER:
                m_cStatusPos = position;
                if (position > 0) {
                    colorView(m_cStatusStatImg, null, true);
                } else if (position == 0) {
//                    colorView(m_cStatusStatImg, m_cStatusStatLine, false);
                }
                break;
        }
    }

    private void colorView(ImageView pImg, ImageView pLine, boolean pState) {
        if (pState) {
            try {
                pImg.setImageResource(R.drawable.cricle_tick);
                pLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                pImg.setImageResource(R.drawable.circlee);
                pLine.setBackgroundColor(Color.RED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case POSS_DATE_PICKER_ID:
                m_cDatePickerDialog = new DatePickerDialog(this, myBankDateListener, m_cCalendar.get(Calendar.YEAR),
                        m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                break;
            case FROM_DATE_PICKER_ID:
                if (fromDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, fromYear,
                            fromMonth, fromDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                m_cDatePickerDialog.setTitle("Select From Date");
                break;
            case TO_DATE_PICKER_ID:
                if (toDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, toYear,
                            toMonth, toDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                try {
                    m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cStartDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY).getTime());
                }catch (Exception e){
                    e.printStackTrace();
                    m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                m_cDatePickerDialog.setTitle("Select To Date");
                break;
        }
        m_cDatePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myBankDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            String lday = String.format("%02d", day);
            m_cPosDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cPossStatImg, m_cPossStatLine, true);
        }
    };

    private DatePickerDialog.OnDateSetListener myFromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            fromMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            fromDate = Integer.parseInt(lday);
            fromYear = year;
            m_cStartDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cDateStatImg, m_cDateStatLine, true);
        }
    };

    private DatePickerDialog.OnDateSetListener myToDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            toMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            toDate = Integer.parseInt(lday);
            toYear = year;
            m_cEndDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cDateEndImg, m_cDateEndLine, true);
        }
    };

    public void showGallery() {
        verifyStoragePermissions(this);
//        m_cObjUIHandler.sendEmptyMessage(DELETE_CACHES);
        m_cImageGUID = DSAMacros.getGUID();
        File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
        Uri imageUri = Uri.fromFile(lFile);

        // Gallery.
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(galleryIntent, TAKE_PICTURE);
    }

    public void takePhoto() {
//            m_cObjUIHandler.sendEmptyMessage(DELETE_CACHES);
        m_cImageGUID = DSAMacros.getGUID();
        File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
        Uri imageUri = Uri.fromFile(lFile);

        // Camera.
        //		final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(captureIntent, TAKE_PICTURE);
    }

    class captureImage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displayProgressBar(-1, "processing image...");
            m_cImageProcessing = true;
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                File lFile = new File(DSAMacros.getImageFilePath(NewProjectApproval.this), m_cImageGUID + ".jpg");
                if (lFile.exists()) {
                    m_cObjSelectedBitMap = BitmapFactory.decodeFile(lFile.getAbsolutePath());
                    Matrix matrix = new Matrix();

                    m_cObjSelectedBitMap = Bitmap.createBitmap(m_cObjSelectedBitMap, 0, 0,
                            m_cObjSelectedBitMap.getWidth(), m_cObjSelectedBitMap.getHeight(), matrix, true);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(NewProjectApproval.this, m_cObjSelectedBitMap);

                    pTakePicture = true;
                    pSavePicture = false;

                    if (null != m_cObjSelectedBitMap) {
                        if (lFile.exists()) {
                            lFile.delete();
                        }
                        FileOutputStream outStream = new FileOutputStream(lFile.getAbsoluteFile());
                        m_cObjSelectedBitMap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                        outStream.flush();
                        outStream.close();
                        outStream = null;
                    }
                    //todo uncomment to save picture
//                    savePicture(lDate);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(NewProjectApproval.this, m_cObjSelectedBitMap);

                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.print(e.getCause() + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hideDialog();
            m_cImageProcessing = false;
            hideDialog();
        }
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };
}