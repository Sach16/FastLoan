package com.whatsloan.dsa.uiframework;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.ServerCallback;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiactivities.BankApprovedProjects;
import com.whatsloan.dsa.uiactivities.DSACampaigns;
import com.whatsloan.dsa.uiactivities.DSALandingScreen;
import com.whatsloan.dsa.uiactivities.DSALogin_OTP;
import com.whatsloan.dsa.uiactivities.DSASearch;
import com.whatsloan.dsa.uiactivities.DSATodaysTasks;
import com.whatsloan.dsa.uiactivities.ViewProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by S.K. Pissay on 20/3/16.
 */
public abstract class DSABaseActivity extends AppCompatActivity implements View.OnClickListener, ServerCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int DISPLAY_ERROR_ALERT_ID = 1;
    public static final int DISPLAY_PROGRESS_BAR_ID = 2;
    public static final int DISPLAY_ANIM_DIAL_ID = 3;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_CALL = 42;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static String[] PERMISSIONS_CALL = {
            Manifest.permission.CALL_PHONE,
    };
//    public static final String GCM_PROJECT_NUMBER = "159301724926";

    /*TODO enter gcm project no. down*/
    public static final String GCM_PROJECT_NUMBER = "";

    public boolean pTakePicture;
    public boolean pSavePicture;
    public String m_cImageGUID;
    public String m_cPatientID;
    public String m_cPictureID;
    public String m_cCaseID;
    public String m_cFileGUID;

    protected int m_cDialogID;
    protected AlertDialog m_cObjDialog;
    protected ProgressDialog m_cObjProgressBar;
    protected Snackbar m_cSnackBar;
    protected Dialog m_cObjAnimDialog;
    public UIHandler m_cObjUIHandler;
    public SwipeRefreshLayout swipeView;

    public HashMap<Integer, String> mFragmentTags = new HashMap<Integer, String>();

    public String m_cJsonSpecialityObject;
    public HashMap<String, HashMap<String, Object>> m_cJsonDoctorProfileObject;
    public HashMap<String, ArrayList<String>> m_cJsonAppSlot;

    // Drawer layout and its propeties
    public DrawerLayout m_cObjSliderMenu;
    public FrameLayout m_cContainFragment;
    public FragmentManager m_cObjFragmentManager;

    public DSAFragmentBaseClass m_cObjFragmentBase;

    public boolean ISSWIPE;

    protected abstract void handleUIMessage(Message pObjMessage);

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(pSavedInstance);
        m_cObjUIHandler = new UIHandler();

        // Set the Status Bar Color
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        // REGISTERING & GETTING PUSH NOTIFICATION
        getRegId();
    }

    public void getRegId() {
        /*new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                GoogleCloudMessaging gcm = null;
                String regid = DSAMacros.getGCMRegId(DSABaseActivity.this);
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(DSABaseActivity.this);
                    }
                    regid = gcm.register(GCM_PROJECT_NUMBER);
                    *//*TODO send new gcm session id below*//*
                    *//*RequestManager.getInstance(DSABaseActivity.this).updateGCM(DSAMacros.getSessionId(DSABaseActivity.this),
                            regid, DSABaseActivity.this);*//*
                    DSAMacros.saveGCMRegId(getApplicationContext(), regid);

                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                etRegId.setText(msg + "\n");
            }
        }.execute(null, null, null);*/
    }

    protected void setTitle(String lTitle, boolean isLogin, boolean isBack, boolean isSearch, boolean islogo) {
        /*TODO set title for DSA id's*/

        /*StringBuffer lBuff = new StringBuffer();
        lTitle = lTitle.toLowerCase();
        String[] titleArray = lTitle.split(" ");
        for(int i=0; i<titleArray.length; i++) {
            if(!TextUtils.isEmpty(titleArray[i]) && titleArray[i].length()>0) {
                if (i == titleArray.length - 1) {
                    lBuff.append(titleArray[i].substring(0, 1).toUpperCase() + titleArray[i].substring(1, titleArray[i].length()).toLowerCase());
                } else {
                    lBuff.append(titleArray[i].substring(0, 1).toUpperCase() + titleArray[i].substring(1, titleArray[i].length()).toLowerCase() + " ");
                }
            } else {
                lBuff.append("");
            }
        }*/
        ImageView lImageHambger = (ImageView) findViewById(R.id.TITLE_HAMBURGER);
        ImageView lUserPic = (ImageView) findViewById(R.id.USER_PIC);
        ImageView lImageBack = (ImageView) findViewById(R.id.TITLE_BACK);
        TextView lCustName = (TextView) findViewById(R.id.CUST_NAME);
        TextView lLocName = (TextView) findViewById(R.id.LOCATION);
        if (isLogin) {
            TextView lTitleText = (TextView) findViewById(R.id.TITLE_MAIN_TXT);
            lTitleText.setText(lTitle);

            findViewById(R.id.TITLE_LOGO).setVisibility(View.GONE);
            findViewById(R.id.TITLE_MAIN_TXT).setVisibility(View.GONE);
            lImageHambger.setVisibility(View.VISIBLE);
            if (isBack) {
                lImageHambger.setVisibility(View.VISIBLE);
                lImageHambger.setOnClickListener(this);
            } else {
                lImageHambger.setVisibility(View.GONE);
                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();
                lCustName.setText(lDAuth.getFirstName() + " " + lDAuth.getLastName());

                Select ldataCity = Select.from(CityData.class);
                CityData lCity = (CityData) ldataCity.first();
                lLocName.setText(lCity.getName());

                Select ldataAttach = Select.from(AttachmentsData.class);
                AttachmentsData lAttchData = (AttachmentsData) ldataAttach.first();
                try {
                    Picasso.with(this)
                            .load(lAttchData.getUri())
                            .error(R.drawable.profile_placeholder)
                            .placeholder(R.drawable.profile_placeholder)
                            .fit()
                            .centerCrop()
                            .into(lUserPic);
                } catch (Exception e) {
                    lUserPic.setBackgroundResource(R.drawable.profile_placeholder);
                    e.printStackTrace();
                }
            }
            lImageHambger.setOnClickListener(this);
            if (isSearch) {
                findViewById(R.id.TITLE_SEARCH_LAY).setVisibility(View.VISIBLE);
                findViewById(R.id.TITLE_SEARCH).setOnClickListener(this);
            } else {
                findViewById(R.id.TITLE_SEARCH_LAY).setVisibility(View.GONE);
            }
            if (islogo) {
                findViewById(R.id.TITLE_LOGO).setVisibility(View.VISIBLE);
            }
        } else {
            TextView lTitleText = (TextView) findViewById(R.id.TITLE_MAIN_TXT);
            lTitleText.setText(lTitle);
            if (islogo) {
                findViewById(R.id.TITLE_LOGO).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.TITLE_MAIN_TXT).setVisibility(View.VISIBLE);
            if (isBack) {
                lImageHambger.setVisibility(View.GONE);
                lImageBack.setVisibility(View.VISIBLE);
                lImageBack.setOnClickListener(this);
            } else {
                lImageHambger.setVisibility(View.VISIBLE);
                lImageHambger.setOnClickListener(this);
                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();
                lCustName.setText(lDAuth.getFirstName() + " " + lDAuth.getLastName());

                Select ldataCity = Select.from(CityData.class);
                CityData lCity = (CityData) ldataCity.first();
                lLocName.setText(lCity.getName());

                Select ldataAttach = Select.from(AttachmentsData.class);
                AttachmentsData lAttchData = (AttachmentsData) ldataAttach.first();
                try {
                    Picasso.with(this)
                            .load(lAttchData.getUri())
                            .error(R.drawable.profile_placeholder)
                            .placeholder(R.drawable.profile_placeholder)
                            .fit()
                            .centerCrop()
                            .into(lUserPic);
                } catch (Exception e) {
                    lUserPic.setBackgroundResource(R.drawable.profile_placeholder);
                    e.printStackTrace();

                }
            }
            if (isSearch) {
                findViewById(R.id.TITLE_SEARCH_LAY).setVisibility(View.VISIBLE);
                findViewById(R.id.TITLE_SEARCH).setOnClickListener(this);
            } else {
                findViewById(R.id.TITLE_SEARCH_LAY).setVisibility(View.GONE);
            }
            findViewById(R.id.TITLE_CALENDAR).setOnClickListener(this);

        }
    }

    //ui controls

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //api controls

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void complete(int code) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        Intent lObjIntent;
        if (apiMethod == Constants.AUTHLOGOUT) {
            hideDialog();
            logout();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        if (apiMethod == Constants.AUTHLOGOUT) {
            hideDialog();
        }
        if (error.networkResponse == null) {
            Toast.makeText(this, "Please check network connection", Toast.LENGTH_SHORT).show();
            hideDialog();
        }
    }

    public final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message pObjMessage) {
            if (null != m_cObjFragmentBase && m_cObjFragmentBase.m_cIsActivityAttached) {
                m_cObjFragmentBase.handleUIhandler(pObjMessage);
            }
            handleUIMessage(pObjMessage);
        }
    }

    @Override
    protected Dialog onCreateDialog(int pID) {
        Dialog lRetVal = null;
        if (pID == DISPLAY_PROGRESS_BAR_ID) {
            lRetVal = m_cObjProgressBar;
        } else if (pID == DISPLAY_ANIM_DIAL_ID) {
            lRetVal = m_cObjAnimDialog;
        } else {
            lRetVal = m_cObjDialog;
        }
        m_cDialogID = pID;
        return lRetVal;
    }

    @SuppressWarnings("deprecation")
    public void hideDialog() {
        try {
            if (null != m_cObjDialog) {
                m_cObjDialog.dismiss();
                removeDialog(m_cDialogID);
            }
            if (null != m_cObjProgressBar) {
                m_cObjProgressBar.dismiss();
                removeDialog(m_cDialogID);
            }
            if (null != m_cObjAnimDialog) {
                m_cObjAnimDialog.dismiss();
                removeDialog(m_cDialogID);
            }
            m_cObjProgressBar = null;
            m_cObjDialog = null;
            m_cObjAnimDialog = null;
            m_cDialogID = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayToast(String message){
        Toast toast= Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void displaySnack(View pView, String pSnackText) {
        hideDialog();
        m_cSnackBar = Snackbar.make(pView, pSnackText, Snackbar.LENGTH_LONG);
        m_cSnackBar.show();
    }

    public void displaySnackRetry(View pView, String pSnackText) {
        hideDialog();
        m_cSnackBar = Snackbar.make(pView, pSnackText, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m_cObjUIHandler.sendEmptyMessage(DSAMacros.NOTIFICATION_NO_NETWORK_CONNECTION_RETRY);
                    }
                });
        m_cSnackBar.show();
    }

    public void createProgressDialog() {
        hideDialog();
        m_cObjProgressBar = new ProgressDialog(this);
        try {
            m_cObjProgressBar.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }try {
            m_cObjProgressBar.setCancelable(false);
            m_cObjProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            m_cObjProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            m_cObjProgressBar.setContentView(R.layout.progressdialog);
            m_cDialogID = DISPLAY_PROGRESS_BAR_ID;
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            m_cObjProgressBar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayPagingProgressDialog() {
        hideDialog();
        m_cObjProgressBar = new ProgressDialog(this);
        try {
            m_cObjProgressBar.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        m_cObjProgressBar.setCancelable(false);
        m_cObjProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        m_cObjProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_cObjProgressBar.setContentView(R.layout.progressdialog_paging);
        m_cDialogID = DISPLAY_PROGRESS_BAR_ID;
        try {
            m_cObjProgressBar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayProgressBar(int pStrResID, String pErrorString) {
        createProgressDialog();
    }

    public void displaySpinnerProgressBar() {
        hideDialog();
        try {
            m_cObjProgressBar = new ProgressDialog(this/*, R.style.CustomDialogTheme*/);
            //m_cObjProgressBar.addContentView(new ProgressBar(this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            m_cObjProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            m_cObjProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            m_cObjProgressBar.setCancelable(false);
            m_cDialogID = DISPLAY_PROGRESS_BAR_ID;
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            m_cObjProgressBar.show();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("deprecation")
    public void displayErrorAlert(int pStrResID, String pErrorString) {
        displayErrorAlert(pStrResID, pErrorString, false);
    }

    public void displayErrorAlert(int pStrResID, String pErrorString, final boolean pSendMsg) {
        hideDialog();
        String lMessage = (null != pErrorString) ? pErrorString : this.getResources().getString(pStrResID);
        try {
            AlertDialog.Builder lObjBuilder = new AlertDialog.Builder(this);
            lObjBuilder.setCancelable(false);
            lObjBuilder.setTitle(getString(R.string.app_name));
            lObjBuilder.setMessage(lMessage);
            lObjBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface pObjDialog, int id) {
                    hideDialog();
                    if (pSendMsg) {
                        m_cObjUIHandler.sendEmptyMessage(1);
                    }
                }
            });
            m_cObjDialog = lObjBuilder.create();
            m_cObjDialog.show();
            showDialog(DISPLAY_ERROR_ALERT_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayAlert(int pStrResID, String pTitle, String pMessage) {
        hideDialog();
        String lMessage = (null != pMessage) ? pMessage : getResources().getString(pStrResID);
        try {
            AlertDialog.Builder lObjBuilder = new AlertDialog.Builder(this);
            lObjBuilder.setCancelable(false);
            lObjBuilder.setTitle(pTitle);
            lObjBuilder.setMessage(lMessage);
            lObjBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface pObjDialog, int id) {
                    hideDialog();
                }
            });
            m_cObjDialog = lObjBuilder.create();
            m_cObjDialog.show();
            showDialog(DISPLAY_ERROR_ALERT_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayYesNoAlert(int pStrResID, String pErrorString) {
        hideDialog();
        String lMessage = (null != pErrorString) ? pErrorString : getResources().getString(pStrResID);
        AlertDialog.Builder lObjBuilder = new AlertDialog.Builder(this);
        lObjBuilder.setCancelable(false);
        lObjBuilder.setTitle(getString(R.string.app_name));
        lObjBuilder.setMessage(lMessage);
        lObjBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface pObjDialog, int id) {
                hideDialog();
                m_cObjUIHandler.sendEmptyMessage(DSAMacros.YES_MESSAGE);
            }
        });

        lObjBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideDialog();
                m_cObjUIHandler.sendEmptyMessage(DSAMacros.NO_MESSAGE);
            }
        });
        m_cObjDialog = lObjBuilder.create();
        m_cObjDialog.show();
        showDialog(DISPLAY_ERROR_ALERT_ID);
    }

    public void showSettingsAlert() {
        hideDialog();
        String lMessage = getResources().getString(R.string.alert_gps_not_enabled);
        AlertDialog.Builder lObjBuilder = new AlertDialog.Builder(this);
        lObjBuilder.setCancelable(false);
        lObjBuilder.setTitle("GPS is settings");
        lObjBuilder.setMessage(lMessage);

        // On pressing Settings button
        lObjBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });

        // on pressing cancel button
        lObjBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                hideDialog();
                m_cObjUIHandler.sendEmptyMessage(1);
            }
        });

        // Showing Alert Message
        m_cObjDialog = lObjBuilder.create();
        m_cObjDialog.show();
        showDialog(DISPLAY_ERROR_ALERT_ID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void deleteImage() {
        File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
        if (lFile.exists()) {
            lFile.delete();
        }
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public boolean isEmailValid(String pEmail) {
        boolean lRetVal = true;
        if (pEmail.trim().length() > 0) {
            CharSequence inputStr = pEmail;
            Pattern pattern = Pattern.compile(DSAMacros.EMAIL_PATTERN_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                lRetVal = true;
            else
                lRetVal = false;
        }
        return lRetVal;
    }

    public boolean isPhoneNoValid(String pPhone) {
        boolean lRetVal = true;
        if (pPhone.trim().length() > 0) {
            CharSequence inputStr = pPhone;
            Pattern pattern = Pattern.compile(DSAMacros.PHONE_PATTERN_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                lRetVal = true;
            else
                lRetVal = false;
        }
        return lRetVal;
    }

    public boolean isAlphaNumeric(String pAlphaNum) {
        boolean lRetVal = true;
        if (pAlphaNum.trim().length() > 0) {
            CharSequence inputStr = pAlphaNum;
            Pattern pattern = Pattern.compile(DSAMacros.ALPHA_NUMERIC_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                lRetVal = true;
            else
                lRetVal = false;
        }
        return lRetVal;
    }

    public boolean isAlphaNumSearch(String pAlphaNum) {
        boolean lRetVal = true;
        if (pAlphaNum.trim().length() > 0) {
            CharSequence inputStr = pAlphaNum;
            Pattern pattern = Pattern.compile(DSAMacros.ALPHA_NUMERIC_SEARCH_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                lRetVal = true;
            else
                lRetVal = false;
        }
        return lRetVal;
    }

    public boolean isAlphabetic(String pAlpha) {
        boolean lRetVal = true;
        if (pAlpha.trim().length() > 0) {
            CharSequence inputStr = pAlpha;
            Pattern pattern = Pattern.compile(DSAMacros.ALPHABETIC_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                lRetVal = true;
            else
                lRetVal = false;
        }
        return lRetVal;
    }

    public boolean copyFile(String from, String to) {
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File source = new File(from);
                File destination = new File(to);
                verifyStoragePermissions(this);
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyCallPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_CALL,
                    REQUEST_CALL
            );
        }
    }

    public class checkIsNetWorkAvailable extends AsyncTask<String, Void, String> {
        private boolean isSucesses = false;
        private boolean m_cIsDialogReq = false;

        public checkIsNetWorkAvailable(boolean pIsDialogReq){
            this.m_cIsDialogReq = pIsDialogReq;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!m_cIsDialogReq) {
                displayProgressBar(-1, "Loading...");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            isSucesses =  activeNetworkInfo != null && activeNetworkInfo.isConnected();
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!m_cIsDialogReq) {
                hideDialog();
            }
            if(isSucesses) {
                m_cObjUIHandler.sendEmptyMessage(DSAMacros.NOTIFICATION_FOR_NETWORK_CONNECTION_AVAILABLE);
            } else {
                displayToast("Please check network connection");
            }
        }
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            JSONObject lObj = obj.getJSONObject(Constants.DATA).getJSONObject(Constants.MESSAGES);
            trimmedString = (String) lObj.getJSONArray(key).get(0);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    @Override
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
        /*TODO below code for slider id's*/
            case R.id.USER_PIC:
                lObjIntent = new Intent(this, ViewProfile.class);
                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();
                lObjIntent.putExtra(Constants.MEMBER_UUID, lDAuth.getUuid());
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
            case R.id.TITLE_CALENDAR:
                lObjIntent = new Intent(this, DSATodaysTasks.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.TITLE_HAMBURGER:
                if (null != m_cObjSliderMenu) {
                    m_cObjSliderMenu.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.TITLE_SEARCH:
                lObjIntent = new Intent(this, DSASearch.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.TITLE_BACK:
                onBackPressed();
                break;
            case R.id.HOME:
                if (null != m_cObjSliderMenu) {
                    m_cObjSliderMenu.closeDrawer(Gravity.LEFT);
                    lObjIntent = new Intent(this, DSALandingScreen.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                break;
            case R.id.CAMPAIGNS:
                if (null != m_cObjSliderMenu) {
                    m_cObjSliderMenu.closeDrawer(Gravity.LEFT);
                    lObjIntent = new Intent(this, DSACampaigns.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                break;
            case R.id.BANK_APP_PROJ:
                if (null != m_cObjSliderMenu) {
                    m_cObjSliderMenu.closeDrawer(Gravity.LEFT);
                    lObjIntent = new Intent(this, BankApprovedProjects.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                break;
            case R.id.LOGOUT:
                if (null != m_cObjSliderMenu) {
                    m_cObjSliderMenu.closeDrawer(Gravity.LEFT);
                    HashMap<String, String> lLogoutParams = new HashMap<>();
                    lLogoutParams.put(DSAMacros.LOGIN_PHONE_NUMBER, DSAMacros.getOTPPhoneNo(this));
                    displayProgressBar(-1, "");
                    RequestManager.getInstance(this)
                            .placeRequest(Constants.AUTHLOGOUT, Response.class, this, lLogoutParams, true);
//                    logout();
                }
                break;
        }
    }

    public boolean logout() {
        Intent lObjIntent;
        if (DSAMacros.removeLoginAuth(this)) {
            lObjIntent = new Intent(this, DSALogin_OTP.class);
            lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(lObjIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            return true;
        }
        return false;
    }
}
