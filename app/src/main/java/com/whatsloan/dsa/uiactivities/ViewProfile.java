package com.whatsloan.dsa.uiactivities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.joanzapata.pdfview.PDFView;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.UserCircularImageView;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AgentData;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.User;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.FileDownloader;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 4/5/16.
 */
public class ViewProfile extends DSABaseActivity {

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelLayGrp;

    @Nullable
    @Bind(R.id.CUST_IMG)
    UserCircularImageView m_cCustImg;

    @Nullable
    @Bind(R.id.VIEW_DASHBOARD)
    TextView m_cViewDashboard;

    @Nullable
    @Bind(R.id.APPROV_BUILDER_NAME)
    TextView m_cName;

    @Nullable
    @Bind(R.id.PHONE_NUMB)
    TextView m_cMobNo;

    @Nullable
    @Bind(R.id.DESIGNATION_NAME)
    TextView m_cDesigntion;

    @Nullable
    @Bind(R.id.MAIL_ID_TXT)
    TextView m_cMailId;

    @Nullable
    @Bind(R.id.ADDRESS_TXT)
    TextView m_cAddress;

    @Nullable
    @Bind(R.id.PROFILE_RATING_BAR)
    RatingBar m_cProfileRatingBar;

    @Nullable
    @Bind(R.id.DOB_TXT)
    TextView m_cDOB;

    @Nullable
    @Bind(R.id.CITY_TXT)
    TextView m_cCity;

    @Nullable
    @Bind(R.id.GENDER_TEXT_TXT)
    TextView m_cGender;

    @Nullable
    @Bind(R.id.ADD_TXT)
    TextView m_cAdd;

    @Nullable
    @Bind(R.id.ADD_PROOF_PROOF_TXT)
    TextView m_cAddProofTxt;

    @Nullable
    @Bind(R.id.ID_PROOF_PROOF_TXT)
    TextView m_cIdProofTxt;

    @Nullable
    @Bind(R.id.DOJ_TXT)
    TextView m_cDOJ;

    @Nullable
    @Bind(R.id.DOJ_YRS_TXT)
    TextView m_cDOJyrs;

    @Nullable
    @Bind(R.id.JOINEDAS_TXT)
    TextView m_cJoinedAS;

    @Nullable
    @Bind(R.id.ADD_BTN)
    Button m_cAddresViewBtn;

    @Nullable
    @Bind(R.id.ID_BTN)
    Button m_cIdViewBtn;

    @Nullable
    @Bind(R.id.PRODUCTS_HANDLED_TXT_BTN)
    Button m_cProdHandledBtn;

    @Nullable
    @Bind(R.id.EXPERIENCE_WITH_BANKS_BTN)
    Button m_cExpWithBanksBtn;

    @Nullable
    @Bind(R.id.REPORTS_TO_NAME_NO_REL_LAY)
    RelativeLayout m_cReportsToRelLay;

    @Nullable
    @Bind(R.id.REPORTS_TO_NAME_NO)
    TextView m_cReportsToNameNo;

    @Nullable
    @Bind(R.id.DSA_WISE_TEXT)
    TextView m_cDsaWiseNo;

    @Nullable
    @Bind(R.id.CITY_WISE_TXT)
    TextView m_cCityWiseNo;

    @Nullable
    @Bind(R.id.ALL_INDIA_TXT)
    TextView m_cAllIndiaNo;

    @Nullable
    @Bind(R.id.PHONE_CALL)
    TextView m_cPhoneCall;

    @Nullable
    @Bind(R.id.PHONE_VIDEO_CALL)
    TextView m_cPhoneVideoCall;

    private String m_cMemUUID;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.view_profile);
        ButterKnife.bind(this);

        /*if (m_cQueryData != null) {
            setTitle(m_cQueryData.getQuery(), false, true, true, false);
        } else {*/
        setTitle("Team Profile View", false, true, true, false);
        /*}*/
        init();
    }

    private void init() {
        m_cMemUUID = getIntent().getStringExtra(Constants.MEMBER_UUID);
        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        if (lDAuth.getUuid().equals(m_cMemUUID) && lDAuth.getRole().equalsIgnoreCase(DSAMacros.DSA_OWNER)) {
            m_cReportsToRelLay.setVisibility(View.GONE);
        }

        //call view profile Api
        displayProgressBar(-1, "Loading...");
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.INSCLUDE, "settings,addresses,attachments,designation,reports_to,rating");
        RequestManager.getInstance(this).placeRequest(Constants.apiMethodEx(Constants.USERS, m_cMemUUID), User.class, this, llParams, false);
    }

    @OnClick({R.id.VIEW_DASHBOARD, R.id.ADD_BTN, R.id.ID_BTN, R.id.PRODUCTS_HANDLED_TXT_BTN,
            R.id.EXPERIENCE_WITH_BANKS_BTN, R.id.PHONE_CALL, R.id.PHONE_VIDEO_CALL})
    public void onClick(View v) {
        Intent lObjInt;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.VIEW_DASHBOARD:
                lObjInt = new Intent(this, DSADashboard.class);
                lObjInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjInt);
                finish();
                break;
            case R.id.ADD_BTN:
                if (null != (String) m_cAddresViewBtn.getTag(R.id.ADDRESS_DOC)) {
                    if (((String) m_cAddresViewBtn.getTag(R.id.ADDRESS_DOC)).contains("pdf")) {
                        displayProgressBar(-1, "Loading...");
                        new DownloadFile().execute((String) m_cAddresViewBtn.getTag(R.id.ADDRESS_DOC), "pdf.pdf");
                    } else {
                        displayDocDialog(null, (String) m_cAddresViewBtn.getTag(R.id.ADDRESS_DOC));
                    }
                } else {
                    displaySnack(m_cRelLayGrp, "No Address Proof");
                }
                break;
            case R.id.ID_BTN:
                if (null != (String) m_cIdViewBtn.getTag(R.id.ID_DOC)) {
                    if (((String) m_cIdViewBtn.getTag(R.id.ID_DOC)).contains("pdf")) {
                        displayProgressBar(-1, "Loading...");
                        new DownloadFile().execute((String) m_cIdViewBtn.getTag(R.id.ID_DOC), "pdf.pdf");
                    } else {
                        displayDocDialog(null, (String) m_cIdViewBtn.getTag(R.id.ID_DOC));
                    }
                } else {
                    displaySnack(m_cRelLayGrp, "No ID Proof");
                }
                break;
            case R.id.PRODUCTS_HANDLED_TXT_BTN:
                if (null != (String) m_cProdHandledBtn.getTag(R.id.PRODUCT_DOC)) {
                    if (((String) m_cProdHandledBtn.getTag(R.id.PRODUCT_DOC)).contains("pdf")) {
                        displayProgressBar(-1, "Loading...");
                        new DownloadFile().execute((String) m_cProdHandledBtn.getTag(R.id.PRODUCT_DOC), "pdf.pdf");
                    } else {
                        displayDocDialog(null, (String) m_cProdHandledBtn.getTag(R.id.PRODUCT_DOC));
                    }
                } else {
                    displaySnack(m_cRelLayGrp, "No Document");
                }
                break;
            case R.id.EXPERIENCE_WITH_BANKS_BTN:
                if (null != (String) m_cExpWithBanksBtn.getTag(R.id.EXPERIENCE_DOC)) {
                    if (((String) m_cExpWithBanksBtn.getTag(R.id.EXPERIENCE_DOC)).contains("pdf")) {
                        displayProgressBar(-1, "Loading...");
                        new DownloadFile().execute((String) m_cExpWithBanksBtn.getTag(R.id.EXPERIENCE_DOC), "pdf.pdf");
                    } else {
                        displayDocDialog(null, (String) m_cExpWithBanksBtn.getTag(R.id.EXPERIENCE_DOC));
                    }
                } else {
                    displaySnack(m_cRelLayGrp, "No Document");
                }
                break;
            case R.id.PHONE_CALL:
            case R.id.PHONE_VIDEO_CALL:
                if (null != (String) m_cPhoneCall.getTag(R.id.PHONE_NO)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + (String) m_cPhoneCall.getTag(R.id.PHONE_NO)));
                    verifyCallPermissions(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what){
            case DSAMacros.PRODUCT:
                new checkIsNetWorkAvailable(false).execute();
                break;
            case DSAMacros.NOTIFICATION_FOR_NETWORK_CONNECTION_AVAILABLE:
                displayDocDialog(new File(DSAMacros.getPdfFilePath(ViewProfile.this), m_cFileGUID + ".pdf"), null);
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        if (apiMethod.contains("/")) {
            User lUser = (User) response;
            if (null != lUser.getData()) {
                inject(lUser);
            }
            hideDialog();
        }
    }

    private void inject(User pObjUser) {
        try {
            try {
                for (AttachmentsData lAtachData : pObjUser.getData().getAttachments().getData()) {
                    if (lAtachData.getType().equalsIgnoreCase(Constants.PROFILE_PICTURE)) {
                        Picasso.with(this)
                                .load(lAtachData.getUri())
                                .error(R.drawable.profile_placeholder)
                                .placeholder(R.drawable.profile_placeholder)
                                .fit()
                                .into(m_cCustImg);
                        Select ldataAuth = Select.from(DataAuth.class);
                        DataAuth lDAuth = (DataAuth) ldataAuth.first();
                        if (lDAuth.getUuid().equals(m_cMemUUID)) {
                            /*ArrayList<AttachmentsData> listAll;
                            // Simple select with all rows:-
                            listAll = (ArrayList<AttachmentsData>) AttachmentsData.listAll(AttachmentsData.class);

                            for (AttachmentsData attachmentsData : listAll) {
                                attachmentsData.delete();
                            }*/
                            AttachmentsData.deleteAll(AttachmentsData.class);
                            AttachmentsData lAttachData = new AttachmentsData(lAtachData.getUuid(), lAtachData.getName(), lAtachData.getDescription(), lAtachData.getUri(), lAtachData.getType());
                            lAttachData.save();
                        }
                    }
                }

            } catch (Exception e) {
                m_cCustImg.setImageResource(R.drawable.profile_placeholder);
                e.printStackTrace();
            }
            try {
                m_cName.setText(pObjUser.getData().getFirstName() + "\n " +
                        pObjUser.getData().getLastName());

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cMobNo.setText(pObjUser.getData().getPhone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pObjUser.getData().getDesignation() != null)
                    m_cDesigntion.setText(pObjUser.getData().getDesignation().getData().getName());
                else
                    m_cDesigntion.setVisibility(View.GONE);
            } catch (Exception e) {
                m_cDesigntion.setVisibility(View.GONE);
                e.printStackTrace();
            }
            try {
                m_cMailId.setText(pObjUser.getData().getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cAddress.setText(pObjUser.getData().getAddresses().getData().getAlphaStreet() + " " +
                        pObjUser.getData().getAddresses().getData().getBetaStreet());

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cDOB.setText(DSAMacros.getDateFormat(null, pObjUser.getData().getSettings().getData().getDob(), DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cCity.setText(pObjUser.getData().getAddresses().getData().getCity().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cGender.setText(DSAMacros.s2l(pObjUser.getData().getSettings().getData().getGender().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cAdd.setText(pObjUser.getData().getAddresses().getData().getAlphaStreet() + " " +
                        pObjUser.getData().getAddresses().getData().getBetaStreet());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cDOJ.setText(DSAMacros.getDateFormat(null, pObjUser.getData().getSettings().getData().getDOJ(), DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cDOJyrs.setText(pObjUser.getData().getSettings().getData().getExpOnDOJ() + " Year(s)");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                StringBuffer lBuff = new StringBuffer();
                for (int i = 0; i < pObjUser.getData().getReportsTo().getData().size(); i++) {
                    AgentData lReportsTo = pObjUser.getData().getReportsTo().getData().get(i);
                    if (i != 0) {
                        lBuff.append(", " + lReportsTo.getFirstName() + " " + lReportsTo.getLastName() + ", " + lReportsTo.getPhone());
                    } else {
                        lBuff.append(lReportsTo.getFirstName() + " " + lReportsTo.getLastName() + ", " + lReportsTo.getPhone());
                    }
                }
                m_cReportsToNameNo.setText(lBuff.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cJoinedAS.setText(pObjUser.getData().getSettings().getData().getJoinedAs());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (AttachmentsData lAttachmentsData : pObjUser.getData().getAttachments().getData()) {
                    switch (lAttachmentsData.getType()) {
                        case Constants.ID_PROOF:
                            m_cIdProofTxt.setText(lAttachmentsData.getDescription());
                            m_cIdViewBtn.setTag(R.id.ID_DOC, lAttachmentsData.getUri());
                            break;
                        case Constants.ADDRESS_PROOF:
                            m_cAddProofTxt.setText(lAttachmentsData.getDescription());
                            m_cAddresViewBtn.setTag(R.id.ADDRESS_DOC, lAttachmentsData.getUri());
                            break;
                        case Constants.EXPERIENCE_DOCUMENT:
                            m_cExpWithBanksBtn.setTag(R.id.EXPERIENCE_DOC, lAttachmentsData.getUri());
                            break;
                        case Constants.PRODUCT_DOCUMENT:
                            m_cProdHandledBtn.setTag(R.id.PRODUCT_DOC, lAttachmentsData.getUri());
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cPhoneCall.setTag(R.id.PHONE_NO, pObjUser.getData().getPhone());
                m_cPhoneVideoCall.setTag(R.id.PHONE_NO, pObjUser.getData().getPhone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cDsaWiseNo.setText(pObjUser.getData().getRating().getData().getDsaWise());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cCityWiseNo.setText(pObjUser.getData().getRating().getData().getCityWise());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cAllIndiaNo.setText(pObjUser.getData().getRating().getData().getAllIndia());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_cProfileRatingBar.setRating(Float.parseFloat(pObjUser.getData().getRating().getData().getDsaRating()));
                m_cProfileRatingBar.setStepSize(Float.parseFloat(pObjUser.getData().getRating().getData().getDsaRating()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                m_cFileGUID = DSAMacros.getGUID();
                File pdfFile = new File(DSAMacros.getPdfFilePath(ViewProfile.this), m_cFileGUID + ".pdf");
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                hideDialog();
                m_cObjUIHandler.sendEmptyMessage(DSAMacros.PRODUCT);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void displayDocDialog(final File pFilePdf, String pImgUrl) {

        View dialogView = View.inflate(this, R.layout.pdf_read_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ImageView imgView = (ImageView) dialogView.findViewById(R.id.DIALOG_CLOSE);
        PDFView pdfViewer = (PDFView) dialogView.findViewById(R.id.PDFVIEW);
        ImageView jpegView = (ImageView) dialogView.findViewById(R.id.JPEGVIEW);

        if (null != pImgUrl) {
            jpegView.setVisibility(View.VISIBLE);
            try {
                Picasso.with(this)
                        .load(pImgUrl)
                        .error(R.drawable.file)
                        .placeholder(R.drawable.file)
                        .into(jpegView);
            } catch (Exception e) {
                jpegView.setBackgroundResource(R.drawable.file);
                e.printStackTrace();
            }
        } else {
            pdfViewer.setVisibility(View.VISIBLE);
            try {
                        /*File outFile = new File(this.getCacheDir(), uri.getPath().toString() + "-pdfview.pdf");
                        outFile.mkdirs();*/
                pdfViewer.fromFile(pFilePdf)
                        .defaultPage(1)
                        .showMinimap(false)
                        .enableSwipe(true)
                        .load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
