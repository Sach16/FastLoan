package com.whatsloan.dsa.uiactivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.joanzapata.pdfview.PDFView;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.BanksAll;
import com.whatsloan.dsa.model.BanksData;
import com.whatsloan.dsa.model.CityAll;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.LeadsSrcDatum;
import com.whatsloan.dsa.model.Products;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.Types;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.FileDownloader;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 4/5/16.
 */
public class DSAProducts extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    private static boolean FILTER_CLICKED = false;

    private int m_cCitiesPos = -1;
    private int m_cBankPos = -1;
    private int m_cTypesPos = -1;

    private static int page = 1;

    private View m_cView;

    int m_cPos;
    //    private ScrollView m_cScroll;
    ArrayList<ProjectStruct> m_cProjectList;
    ArrayList<String> m_cBankList;
    HashMap<String, String> m_cBankDic;
    ArrayList<String> m_cCitiesList;
    HashMap<String, String> m_cCitiesDic;
    ArrayList<String> m_cTypesList;
    HashMap<String, String> m_cTypesDic;
    ArrayList<String> m_cProductsList;
    HashMap<String, String> m_cProductsDic;

    ArrayList<String> m_cSpareList;

    ArrayAdapter<String> m_cSpinAdapter;

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelGrp;

    @Nullable
    @Bind(R.id.TO_SPINNER)
    Spinner m_cSelectBankSpin;

    @Nullable
    @Bind(R.id.FROM_SPINNER)
    Spinner m_cSelectCitiesSpin;

    @Nullable
    @Bind(R.id.RESULTS_TXT)
    TextView m_cResultTxt;

    @Nullable
    @Bind(R.id.RESULTS_COUNT_TXT)
    TextView m_cProjCountNo;

    @Nullable
    @Bind(R.id.SCREEN_IMAGE)
    ImageView m_cScreenImg;

    @Nullable
    @Bind(R.id.SPIN_FILT_BUTTON_TXT)
    TextView m_cSpinFiltButTxt;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoDataAvail;

    @Nullable
    @Bind(R.id.RADIO_GROUP)
    RadioGroup m_cRadioGroupEdit;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.product_docs);

        /*ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.product_docs);
        m_cView = stub.inflate();*/
        ButterKnife.bind(this);
        setTitle("Products", false, true, true, false);

//        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
//        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

        init();
    }

    private void init() {
        m_cProjCountNo.setVisibility(View.GONE);
        m_cResultTxt.setText("Select Type of Loan");
        m_cScreenImg.setImageDrawable(getResources().getDrawable(R.drawable.files));
        m_cSpinFiltButTxt.setText("Get Product Details");

        m_cRadioGroupEdit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = m_cRadioGroupEdit.findViewById(checkedId);
                m_cTypesPos = m_cRadioGroupEdit.indexOfChild(radioButton);
            }
        });

        m_cProjectList = new ArrayList<>();
        m_cSelectBankSpin.setOnItemSelectedListener(this);
        m_cSelectCitiesSpin.setOnItemSelectedListener(this);

        m_cCitiesList = new ArrayList<>();
        m_cCitiesList.add("Select City");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cCitiesList);
        m_cSelectCitiesSpin.setAdapter(m_cSpinAdapter);

        m_cBankList = new ArrayList<>();
        m_cBankList.add("Select Bank");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cBankList);
        m_cSelectBankSpin.setAdapter(m_cSpinAdapter);

        //Calling Cities api
        displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        RequestManager.getInstance(this).placeRequest(Constants.CITIES, CityAll.class, this, lParams, false);

        //Calling Banks api
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.PAGINATE, Constants.ALL);
//        llParams.put(Constants.INSCLUDE, Constants.ADDRESS + "," + Constants.OWNER);
//        placeRequest(Constants.BANKS, BanksAll.class, llParams, false); //todo uncomment for all banks
        RequestManager.getInstance(this).placeRequest(Constants.TEAMMEMBERS_BANKS, BanksAll.class, this, llParams, false);

        //Calling Products api
//        RequestManager.getInstance(this).placeRequest(Constants.BANKS_PRODUCTS_GETPRODUCTS, Types.class, this, null, false);
    }

    private void addRadios(String pRadioTag) {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RadioButton lRd = new RadioButton(this);
        lRd.setLayoutParams(lparams);
        lRd.setText(pRadioTag);
        this.m_cRadioGroupEdit.addView(lRd);
    }

    @OnClick({R.id.VIEW_DETAILS_TXT, R.id.SPIN_FILT_BUTTON_TXT})
    public void onClick(View v) {
        super.onClick(v);
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.VIEW_DETAILS_TXT:
                try {
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.ADDRESSES + "," + Constants.ATTACHMENTS);
                    if (m_cCitiesPos > 0) {
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    }
                    if (m_cBankPos > 0) {
                        lParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                    }
                    if (m_cTypesPos >= 0) {
                        lParams.put(Constants.TYPE_UUID, m_cTypesDic.get(m_cTypesList.get(m_cTypesPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.BANKS_RODUCTS_DOCUMENTFILTERS, Products.class, this, lParams, false);
                    displayProgressBar(-1, "Loading");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.SPIN_FILT_BUTTON_TXT:
                try {
                    HashMap<String, String> llParams = new HashMap<>();
                    llParams.put(Constants.INSCLUDE, Constants.ADDRESSES + "," + Constants.ATTACHMENTS);
                    if (m_cCitiesPos > 0) {
                        llParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    }
                    if (m_cBankPos > 0) {
                        llParams.put(Constants.BANKID, m_cBankDic.get(m_cBankList.get(m_cBankPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.BANKS_PRODUCTS_GETPRODUCTS, Types.class, this, llParams, false);
                    displayProgressBar(-1, "Loading");
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
            case Constants.BANKS_PRODUCTS_GETPRODUCTS:
                Types lTypes = (Types) response;
                m_cTypesList = new ArrayList<>();
                m_cTypesDic = new HashMap<>();
                this.m_cRadioGroupEdit.removeAllViews();
                if(lTypes.getData().size() > 0) {
                    for (LeadsSrcDatum lLeadsSrcDatum : lTypes.getData()) {
                        m_cTypesList.add(lLeadsSrcDatum.getName());
                        m_cTypesDic.put(lLeadsSrcDatum.getName(), lLeadsSrcDatum.getUuid());
                        addRadios(lLeadsSrcDatum.getName());
                    }
                    m_cNoDataAvail.setVisibility(View.GONE);
                } else {
                    m_cNoDataAvail.setVisibility(View.VISIBLE);
                }
                hideDialog();
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
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cCitiesList);
                    m_cSelectCitiesSpin.setAdapter(m_cSpinAdapter);
                }
                hideDialog();
                break;
            //todo uncomment for all banks
//            case Constants.BANKS:
            case Constants.TEAMMEMBERS_BANKS:
                BanksAll lBanksAll = (BanksAll) response;
                if (lBanksAll.getBanks().size() > 0) {
                    m_cBankList = new ArrayList<>();
                    m_cBankDic = new HashMap<>();
                    m_cBankList.add("Select Bank");
                    for (BanksData lBank : lBanksAll.getBanks()) {
                        m_cBankList.add(lBank.getName());
                        m_cBankDic.put(lBank.getName(), lBank.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cBankList);
                    m_cSelectBankSpin.setAdapter(m_cSpinAdapter);
                }
                hideDialog();
                break;
            case Constants.BANKS_RODUCTS_DOCUMENTFILTERS:
                try{
                    Products lProducts = (Products) response;
                    if(lProducts.getData().size() > 0){
                        m_cSpareList = new ArrayList<>();
                        m_cProductsList = new ArrayList<>();
                        m_cProductsDic = new HashMap<>();
                        for (AttachmentsData lAttachmentsData : lProducts.getData().get(0).getAttachments().getData()){
                            Log.d("URL Link :", lAttachmentsData.getUri());
                            if(lAttachmentsData.getUri()!=null){
                                m_cProductsList.add(lAttachmentsData.getUri());
                                m_cSpareList.add(lAttachmentsData.getName());
                                m_cProductsDic.put(lAttachmentsData.getName(), lAttachmentsData.getUri());
                            }
                        }
                        displayProductsList(m_cProductsList, m_cSpareList);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                hideDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        hideDialog();
        page = 1;
        if(apiMethod == Constants.BANKS_PRODUCTS_GETPRODUCTS) {
            if (error.networkResponse.statusCode == 422) {
                if (m_cBankPos <= 0) {
                    displaySnack(m_cRelGrp, "Select Bank");
                }
                if (m_cCitiesPos <= 0) {
                    displaySnack(m_cRelGrp, "Select City");
                }
            }
        }
        if(apiMethod == Constants.BANKS_RODUCTS_DOCUMENTFILTERS) {
            if (error.networkResponse.statusCode == 422) {
                if (m_cBankPos <= 0) {
                    displaySnack(m_cRelGrp, "Select Bank");
                }
                if (m_cCitiesPos <= 0) {
                    displaySnack(m_cRelGrp, "Select City");
                }
                if (m_cTypesPos <= -1){
                    displaySnack(m_cRelGrp, "Select Type of Loan");
                }
            }
        }
        if (!FILTER_CLICKED) {
            hideDialog();
            FILTER_CLICKED = false;
        }
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what){
            case DSAMacros.PRODUCT:
                new checkIsNetWorkAvailable(false).execute();
                break;
            case DSAMacros.NOTIFICATION_FOR_NETWORK_CONNECTION_AVAILABLE:
                displayDocDialog(new File(DSAMacros.getPdfFilePath(DSAProducts.this), m_cFileGUID + ".pdf"), null);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.FROM_SPINNER:
                m_cCitiesPos = position;
                if (position >= 0) {
                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cCitiesPos > 0) {
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    } else {
                        lParams.put(Constants.PAGINATE, Constants.ALL);
                    }
//                    placeRequest(Constants.BANKS, BanksAll.class, lParams, false); //todo uncomment for all banks
                    RequestManager.getInstance(this).placeRequest(Constants.TEAMMEMBERS_BANKS, BanksAll.class, this, lParams, false);
//                    m_cSelectBankSpin.performClick();
                }
                break;
            case R.id.TO_SPINNER:
                m_cBankPos = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("NOTHING_SELECTED", "" + parent.getId());
    }

    private void displayProductsList(final ArrayList<String> pObjUris, final ArrayList<String> pObjNames){

        final ArrayList<String> lObjUris = pObjUris;
        final ArrayList<String> lObjNames = pObjNames;

        final View dialogView = View.inflate(this, R.layout.products_list_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ListView modeList = (ListView) dialogView.findViewById(R.id.PRODUCTS_LIST);
        ImageView imgView = (ImageView) dialogView.findViewById(R.id.DIALOG_CLOSE);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, lObjNames);
        modeList.setAdapter(modeAdapter);

        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Log.w("" + position, "" + position);
//                Intent lInt = new Intent(Intent.ACTION_VIEW);
//                lInt.setData(Uri.parse(lObjUris.get(position)));
//                startActivity(lInt);

                if (lObjUris.get(position).contains("pdf")) {
                    displayProgressBar(-1, "Loading...");
                    new DownloadFile().execute((String) lObjUris.get(position), "pdf.pdf");
                } else {
                    displayDocDialog(null, lObjUris.get(position));
                }
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                m_cFileGUID = DSAMacros.getGUID();
                File pdfFile = new File(DSAMacros.getPdfFilePath(DSAProducts.this), m_cFileGUID + ".pdf");
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
                Message lMsg = new Message();
                lMsg.what = DSAMacros.PRODUCT;
                Object[] lObj = new Object[]{new File(DSAMacros.getPdfFilePath(DSAProducts.this), m_cFileGUID + ".pdf"), null};
                lMsg.obj = lObj;
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

        if(null != pImgUrl) {
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
        }else {
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
