package com.whatsloan.dsa.uiactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Attachments;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.CreatedAtAuth;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.LoginAuth;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.UpdatedAtAuth;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 28/3/16.
 */
public class DSALogin_OTP extends DSABaseActivity {

    @Nullable
    @Bind(R.id.REL_LAY)
    RelativeLayout m_cRelLay;

    @Nullable
    @Bind(R.id.OTP_LAY)
    LinearLayout m_cOtpLay;

    @Nullable
    @Bind(R.id.PHONE_NUMBER)
    EditText m_cPhoneEdit;

    @Nullable
    @Bind(R.id.OTP)
    EditText m_cOtpEdit;

    @Nullable
    @Bind(R.id.OTP_TXT)
    TextView m_cOtpTextHeader;

    @Nullable
    @Bind(R.id.SEND_OR_SIGN_IN)
    TextView m_cSignInTxt;

    private HashMap<String, String> m_cLoginParams;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_otp);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        if (DSAMacros.getOTPGen(this)) {
            m_cPhoneEdit.setText(DSAMacros.getOTPPhoneNo(this));
            m_cOtpLay.setVisibility(View.VISIBLE);
            m_cOtpTextHeader.setText(R.string.enter_otp);
            m_cSignInTxt.setText(R.string.sign_in);
        }
//        m_cPhoneEdit.setText("09595747237");
//        m_cOtpEdit.setText("Qwerty123");

    }

    public boolean validatePhone() {
        boolean lRetVal = false;
        String lPhone = m_cPhoneEdit.getText().toString().trim();
        String lPassword = m_cOtpEdit.getText().toString().trim();
        if (lPhone.length() > 0) {
            if (isPhoneNoValid(lPhone)) {
                lRetVal = true;
            } else {
                displaySnack(m_cRelLay, "Please Enter Valid Phone Number");
            }
        } else {
            displaySnack(m_cRelLay, "Please Enter Phone Number");
        }
        return lRetVal;
    }

    public boolean validateCred() {
        boolean lRetVal = false;
        String lPhone = m_cPhoneEdit.getText().toString().trim();
        String lOTP = m_cOtpEdit.getText().toString().trim();
        if (lPhone.length() > 0) {
            if (isPhoneNoValid(lPhone)) {
                if (lOTP.length() > 0) {
                    if (lOTP.length() >= 6) {
                        lRetVal = true;
                    } else {
                        displaySnack(m_cRelLay, "Enter valid OTP.");
                    }
                } else {
                    displaySnack(m_cRelLay, "OTP Required");
                }
            } else {
                displaySnack(m_cRelLay, "Please Enter Valid Phone Number");
            }
        } else {
            displaySnack(m_cRelLay, "Please Enter Phone Number");
        }
        return lRetVal;
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick(R.id.SEND_OR_SIGN_IN)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SEND_OR_SIGN_IN:
                if (!DSAMacros.getOTPGen(this)) {
                    if (validatePhone()) {
                        m_cLoginParams = new HashMap<>();
                        m_cLoginParams.put(DSAMacros.LOGIN_PHONE_NUMBER, m_cPhoneEdit.getText().toString());
                        m_cLoginParams.put(DSAMacros.LOGIN_ROLE, DSAMacros.LOGIN_ROLE_SPE);
                        displayProgressBar(-1, "");
                        RequestManager.getInstance(this).placeRequest(Constants.AUTHLOGIN, Response.class, this, m_cLoginParams, true);
                    }
                    break;
                } else {
                    if (validateCred()) {
                        m_cLoginParams = new HashMap<>();
                        m_cLoginParams.put(DSAMacros.LOGIN_PHONE_NUMBER, m_cPhoneEdit.getText().toString());
                        m_cLoginParams.put(DSAMacros.LOGIN_OTP, m_cOtpEdit.getText().toString());
                        m_cLoginParams.put(Constants.INSCLUDE, Constants.ATTACHMENTS + "," + Constants.ADDRESSES);
                        displayProgressBar(-1, "");
                        RequestManager.getInstance(this).placeRequest(Constants.OTP_VERIFY, LoginAuth.class, this, m_cLoginParams, true);
                    }
                }
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        switch (apiMethod) {
            case Constants.AUTHLOGIN:
                hideDialog();
                DSAMacros.setOTPGen(this, true);
                DSAMacros.setOTPPhoneNo(this, m_cPhoneEdit.getText().toString());
                if (DSAMacros.getOTPGen(this)) {
                    m_cPhoneEdit.setText(DSAMacros.getOTPPhoneNo(this));
                    m_cOtpLay.setVisibility(View.VISIBLE);
                    m_cOtpTextHeader.setText(R.string.enter_otp);
                    m_cSignInTxt.setText(R.string.sign_in);
                }
                break;
            case Constants.OTP_VERIFY:
                hideDialog();
                Intent lObjIntent;
                LoginAuth lLoginAuth = (LoginAuth) response;
                if(DSAMacros.convertStringToDate(lLoginAuth.getData().getUpdatedAt().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS).before(new Date())){
                    startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                }
                saveDataAuth(lLoginAuth.getData());
                saveCreatedAuth(lLoginAuth.getData().getCreatedAt());
                saveUpdatedAuth(lLoginAuth.getData().getUpdatedAt());
                saveAttachments(lLoginAuth.getData().getAttachments());
                saveAttachmentsData(lLoginAuth.getData().getAttachments().getData());
                saveCityData(lLoginAuth.getData().getAddresses().getData().getCity().getData());

                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();

                DSAMacros.saveLoginAuth(this, lDAuth.getToken());
                lObjIntent = new Intent(this, DSAStartDay.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                finish();
                break;
            default:
                super.onAPIResponse(response, apiMethod);
                break;
        }
    }

    private void saveDataAuth(DataAuth pDataAuth) {
        DataAuth.deleteAll(DataAuth.class);
        DataAuth lDataAuth = new DataAuth(pDataAuth.getUuid(), pDataAuth.getFirstName(), pDataAuth.getLastName(), pDataAuth.getEmail(), pDataAuth.getRole(), pDataAuth.getToken(), pDataAuth.getCreatedAt(), pDataAuth.getUpdatedAt(), pDataAuth.getAttachments(), pDataAuth.getAddresses());
        lDataAuth.save();
    }

    private void saveCreatedAuth(CreatedAtAuth pCreatedAuth) {
        CreatedAtAuth.deleteAll(CreatedAtAuth.class);
        CreatedAtAuth lCreatedAuth = new CreatedAtAuth(pCreatedAuth.getDate(), pCreatedAuth.getTimezoneType(), pCreatedAuth.getTimezone());
        lCreatedAuth.save();
    }

    private void saveUpdatedAuth(UpdatedAtAuth pUpdatedAuth) {
        UpdatedAtAuth.deleteAll(UpdatedAtAuth.class);
        UpdatedAtAuth lUpdatedAuth = new UpdatedAtAuth(pUpdatedAuth.getDate(), pUpdatedAuth.getTimezoneType(), pUpdatedAuth.getTimezone());
        lUpdatedAuth.save();
    }

    private void saveAttachments(Attachments pAttachments) {
        Attachments.deleteAll(Attachments.class);
        Attachments lAttachments = new Attachments(pAttachments.getData());
        lAttachments.save();
    }

    private void saveAttachmentsData(List<AttachmentsData> pdata) {
        AttachmentsData.deleteAll(AttachmentsData.class);
        for (AttachmentsData lAttachmentsData : pdata){
            AttachmentsData lAttachData = new AttachmentsData(lAttachmentsData.getUuid(), lAttachmentsData.getName(), lAttachmentsData.getDescription(), lAttachmentsData.getUri(), lAttachmentsData.getType());
            lAttachData.save();
        }
    }

    private void saveCityData(CityData pdata) {
        CityData.deleteAll(CityData.class);
        CityData lCityData = new CityData(pdata.getUuid(), pdata.getName(), pdata.getLatitude(), pdata.getLongitude());
        lCityData.save();
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        switch (apiMethod) {
            case Constants.AUTHLOGIN:
                hideDialog();
                if(error instanceof NoConnectionError){
                    Toast.makeText(this, "Please check Network connection", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }else {
                    displayToast("Phone No. is not registered");
                }
                break;
            case Constants.OTP_VERIFY:
                hideDialog();
                if(error instanceof NoConnectionError){
                    Toast.makeText(this, "Please check Network connection", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }else {
                    displayToast(getResources().getString(R.string.phone_no_otp_not_reg));
                }
                break;
            default:
                super.onErrorResponse(error, apiMethod);
                break;
        }

    }
}