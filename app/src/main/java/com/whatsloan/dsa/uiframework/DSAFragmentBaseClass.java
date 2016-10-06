package com.whatsloan.dsa.uiframework;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.whatsloan.dsa.interfaces.ServerCallback;
import com.whatsloan.dsa.network.RequestManager;

import java.io.File;
import java.util.HashMap;

/**
 * Created by S.K. Pissay on 20/3/16.
 */
public abstract class DSAFragmentBaseClass extends Fragment implements View.OnClickListener, ServerCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    protected DSABaseActivity m_cObjMainActivity;
    protected UIHandler m_cObjUIHandler;
    protected View m_cObjMainView;
    protected boolean m_cIsActivityAttached;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity pObjActivity) {
        super.onAttach(pObjActivity);
        m_cObjMainActivity = (DSABaseActivity) getActivity();
        m_cObjUIHandler = new UIHandler();
        m_cIsActivityAttached = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        m_cIsActivityAttached = false;
    }

    @Override
    public void onClick(View v) {
    }

    public final class UIHandler extends Handler {
        public void handleMessage(Message pObjMessage) {
            handleUIMessage(pObjMessage);
        }
    }

    public void handleUIhandler(Message pObjMessage) {
        Message lObJMsg = new Message();
        lObJMsg.what = pObjMessage.what;
        lObJMsg.arg1 = pObjMessage.arg1;
        lObJMsg.arg2 = pObjMessage.arg2;
        lObJMsg.obj = pObjMessage.obj;
        m_cObjUIHandler.sendMessage(lObJMsg);
    }

    protected abstract void handleUIMessage(Message pObjMessage);

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

    public void placeRequest(String methodName, Class clazz, HashMap<String, String> params, boolean isPOST) {
        RequestManager.getInstance(getActivity()).placeRequest(methodName, clazz, this, params, isPOST);
    }

    public void placePutRequest(String methodName, Class clazz, HashMap<String, String> params, boolean isPuT) {
        RequestManager.getInstance(getActivity()).placePutRequest(methodName, clazz, this, params, isPuT);
    }

    public void placeMultiPartRequest(String methodName, Class clazz, HashMap<String, String> feedParams, File file, String fileKey) {
        RequestManager.getInstance(getActivity()).placeMultiPartRequest(methodName, clazz, this, feedParams, file, fileKey);
    }

    public void placeMultiPartPutRequest(String methodName, Class clazz, HashMap<String, String> feedParams, File file, String fileKey) {
        RequestManager.getInstance(getActivity()).placeMultiPartPutRequest(methodName, clazz, this, feedParams, file, fileKey);
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {

    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        if(error instanceof NoConnectionError){
            Toast.makeText(m_cObjMainActivity, "Please check Network connection", Toast.LENGTH_SHORT).show();
            m_cObjMainActivity.hideDialog();
        }
        if(error.networkResponse.statusCode == 401){
            m_cObjMainActivity.logout();
        }
    }

}
