package com.whatsloan.dsa.uiactivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.joanzapata.pdfview.PDFView;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.RoundedCornersTransformation;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.LoansData;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.StatusData;
import com.whatsloan.dsa.model.Statuses;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.FileDownloader;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;
import com.whatsloan.dsa.utils.ScalingUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 26/5/16.
 */
public class CustomerLoanEdit extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelLayGrp;

    @Nullable
    @Bind(R.id.STAUS_SPINNER)
    Spinner m_cStatusSpinner;

    @Nullable
    @Bind(R.id.DOCUMENT_LIST)
    ListView m_cDocsListView;

    @Nullable
    @Bind(R.id.UPLOAD_FILE_TXT)
    TextView m_cUploadFileTxt;

    @Nullable
    @Bind(R.id.TAKE_PHOTO_TXT)
    TextView m_cTakePhotoTxt;

    @Nullable
    @Bind(R.id.UPLOAD_PHOTO_TXT)
    TextView m_cChoosePhotoTxt;

    @Nullable
    @Bind(R.id.SUBMIT_BTN_TXT)
    TextView m_cSubmitDocTxt;

    @Nullable
    @Bind(R.id.CANCEL_BTN_TXT)
    TextView m_cCancelDocTxt;

    @Nullable
    @Bind(R.id.DOC_VIEW_WITH_TXT)
    RelativeLayout m_cDocViewGrp;

    @Nullable
    @Bind(R.id.LOAN_IMG)
    ImageView m_cLoanImg;

    @Nullable
    @Bind(R.id.FILE_URL_TXT)
    TextView m_cFileUrlTxt;

    @Nullable
    @Bind(R.id.FILE_DESC_EDIT)
    EditText m_cFileDescEdit;

    @Nullable
    @Bind(R.id.SUBMIT_STS_TXT)
    TextView m_cSubmitSTSTxt;

    @Nullable
    @Bind(R.id.CUST_SPIN_LAY)
    RelativeLayout m_cCustSpinLay;

    @Nullable
    @Bind(R.id.CUST_LIST_LAY)
    RelativeLayout m_cCustListLay;

    @Nullable
    @Bind(R.id.GUID_TXT)
    TextView m_cGuidTxt;

    private LoansData m_cLoansData;
    ArrayList<String> m_cUriList;
    HashMap<String, String> m_cUriMap;

    public static final int TAKE_PICTURE = 101;
    private static final int FILE_SELECT_CODE = 555;
    private Uri m_cSelectedImageUri;
    boolean m_cImageProcessing;
    private Bitmap m_cObjSelectedBitMap;
    private boolean m_cIsStatus;

    private int m_cStatusPos = -1;

    ArrayList<String> m_cStatusList;
    HashMap<String, String> m_cStatusDic;

    ArrayAdapter<String> m_cSpinAdapter;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.customer_loan_edit);
        ButterKnife.bind(this);
        setTitle("Customer Loan Edit", false, true, true, false);

        m_cLoansData = getIntent().getParcelableExtra("LoansData");
        m_cIsStatus = getIntent().getBooleanExtra("IsStatus", false);
        if (m_cIsStatus) {
            m_cCustListLay.setVisibility(View.GONE);
        } else {
            m_cCustSpinLay.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cStatusSpinner.setOnItemSelectedListener(this);

        try {
            m_cGuidTxt.setText("DSA Name : " + m_cLoansData.getAgent().getData().getFirstName() + " " + m_cLoansData.getAgent().getData().getLastName() + " / " +
                    "Consumer Name : " + m_cLoansData.getCustomer().getData().getFirstName() + " " + m_cLoansData.getCustomer().getData().getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        m_cUriList = new ArrayList<>();
        m_cUriMap = new HashMap<>();
        try {
            for (AttachmentsData lAttachmentsData : m_cLoansData.getAttachments().getData()) {
                m_cUriList.add(lAttachmentsData.getName());
                m_cUriMap.put(lAttachmentsData.getName(), lAttachmentsData.getUri());
            }
            ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    android.R.id.text1, m_cUriList);
            m_cDocsListView.setAdapter(modeAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m_cDocsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Log.w("" + position, "" + position);
                String lCheck = m_cUriMap.get(m_cUriList.get(position));
                if (lCheck.toLowerCase().contains("pdf")) {
                    displayProgressBar(-1, "Loading...");
                    new DownloadFile().execute(m_cUriMap.get(m_cUriList.get(position)), "pdf.pdf");
                } else {
                    displayDocDialog(null, m_cUriMap.get(m_cUriList.get(position)));
                }
            }
        });

        displayProgressBar(-1, "Loading");
        RequestManager.getInstance(this).placeRequest(Constants.LOANS_STATUSES, Statuses.class, this, null, false);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what) {
            case TAKE_PICTURE:
                new captureImage().execute("");
                break;
            case DSAMacros.PRODUCT:
                new checkIsNetWorkAvailable(false).execute();
                break;
            case DSAMacros.NOTIFICATION_FOR_NETWORK_CONNECTION_AVAILABLE:
                displayDocDialog(new File(DSAMacros.getPdfFilePath(CustomerLoanEdit.this), m_cFileGUID + ".pdf"), null);
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
                            m_cDocViewGrp.setVisibility(View.VISIBLE);
                            try {
                                Picasso.with(this)
                                        .load(new File(picturePath.toString()))
                                        .error(R.drawable.profile_placeholder)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .transform(new RoundedCornersTransformation(10, 0))
                                        .config(Bitmap.Config.RGB_565)
                                        .fit()
                                        .into(m_cLoanImg);
                                m_cFileUrlTxt.setText(picturePath.toString());
                            } catch (Exception e) {
                                m_cLoanImg.setBackgroundResource(R.drawable.profile_placeholder);
                                e.printStackTrace();
                            }

                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);
                        } else {
                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);

                            File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
                            double kilobytes = (lFile.length() / 1024);
                            if (kilobytes > 2000) {
                                displaySnack(m_cRelLayGrp, "Selected file exceeds upload file limit");
                            }
                            m_cDocViewGrp.setVisibility(View.VISIBLE);
                            try {
                                Picasso.with(this)
                                        .load(lFile)
                                        .error(R.drawable.profile_placeholder)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .transform(new RoundedCornersTransformation(10, 0))
                                        .config(Bitmap.Config.RGB_565)
                                        .fit()
                                        .into(m_cLoanImg);
                                m_cFileUrlTxt.setText(lFile.toString());
                            } catch (Exception e) {
                                m_cLoanImg.setBackgroundResource(R.drawable.profile_placeholder);
                                e.printStackTrace();
                            }
                        }
//						deletePicFromDCIM();
                    } catch (Exception e) {
                        displaySnack(m_cRelLayGrp, "Unable to retrieve Image");
                        e.printStackTrace();
                        Log.w("IMAGE_NAME  : ", m_cImageGUID);
                    }
                }
                /*else {
                    finish();
                }*/
                break;
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    File lFile = new File(uri.getPath());
                    double kilobytes = (lFile.length() / 1024);
                    if (kilobytes > 2000) {
                        displaySnack(m_cRelLayGrp, "Selected file exceeds upload file limit");
                    }
                    hideDialog();
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload

                    m_cDocViewGrp.setVisibility(View.VISIBLE);
                    m_cFileUrlTxt.setText(uri.getPath().toString());

                }
                break;
        }
    }

    @OnClick({R.id.UPLOAD_FILE_TXT, R.id.TAKE_PHOTO_TXT, R.id.UPLOAD_PHOTO_TXT, R.id.SUBMIT_BTN_TXT,
            R.id.CANCEL_BTN_TXT, R.id.SUBMIT_STS_TXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.UPLOAD_FILE_TXT:
                showFileChooser();
                break;
            case R.id.TAKE_PHOTO_TXT:
                takePhoto();
                break;
            case R.id.UPLOAD_PHOTO_TXT:
                showGallery();
                break;
            case R.id.SUBMIT_BTN_TXT:
                displayProgressBar(-1, "Loading...");
                HashMap<String, String> lParams = new HashMap<>();
                if (m_cFileDescEdit.getText().toString().trim().length() > 0) {
                    lParams.put(Constants.NAME, m_cFileDescEdit.getText().toString());
                    RequestManager.getInstance(this).placeMultiPartRequest(Constants.apiMethodEx(Constants.LOANS_UPLOAD, m_cLoansData.getUuid())
                            , Response.class, this, lParams,
                            new File(m_cFileUrlTxt.getText().toString()), Constants.PDF_UPLOAD);
                } else {
                    displayToast("Please enter file name");
                    hideDialog();
                }
                break;
            case R.id.CANCEL_BTN_TXT:
                onBackPressed();
                break;
            case R.id.SUBMIT_STS_TXT:
                displayProgressBar(-1, "Loading...");
                HashMap<String, String> llParams = new HashMap<>();
                if (m_cStatusPos > 0) {
                    llParams.put(Constants.LOAN_STATUS_UUID, m_cStatusDic.get(m_cStatusList.get(m_cStatusPos)));
                }
                RequestManager.getInstance(this).placeRequest(Constants.apiMethodEx(Constants.LOANS_UPDATESTATUS, m_cLoansData.getUuid())
                        , Response.class, this, llParams, true);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.STAUS_SPINNER:
                m_cStatusPos = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        if (apiMethod.contains(Constants.LOANS_UPLOAD)) {
            Response lResponse = (Response) response;
            displaySnack(m_cRelLayGrp, "Successfully Uploaded");
            onBackPressed();

        } else if (apiMethod.contains(Constants.LOANS_UPDATESTATUS)) {
            Response llResponse = (Response) response;
            displaySnack(m_cRelLayGrp, "Successfully Updated");
        } else if (apiMethod.contains(Constants.LOANS_STATUSES)) {
            Statuses lStatuses = (Statuses) response;
            m_cStatusList = new ArrayList<>();
            m_cStatusDic = new HashMap<>();
            if (lStatuses.getData().size() > 0) {
                m_cStatusList.add("Select Loan Status");
            }
            for (StatusData lStatusData : lStatuses.getData()) {
                m_cStatusList.add(lStatusData.getLabel());
                m_cStatusDic.put(lStatusData.getLabel(), lStatusData.getUuid());
            }
            m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_plain_bold, m_cStatusList);
            m_cStatusSpinner.setAdapter(m_cSpinAdapter);
            if (null != m_cLoansData) {
                int lPos = 0;
                try {
                    lPos = m_cStatusList.indexOf(m_cLoansData.getStatus().getData().getLabel());
                    m_cStatusSpinner.setSelection(lPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            hideDialog();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        hideDialog();
        if (error.networkResponse.statusCode == 422) {
            Toast.makeText(this, "Please select file from local storage", Toast.LENGTH_SHORT).show();
            hideDialog();
        }
    }

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

    private void showFileChooser() {
        verifyStoragePermissions(this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
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

                File lFile = new File(DSAMacros.getImageFilePath(CustomerLoanEdit.this), m_cImageGUID + ".jpg");
                if (lFile.exists()) {
                    m_cObjSelectedBitMap = BitmapFactory.decodeFile(lFile.getAbsolutePath());
                    Matrix matrix = new Matrix();

                    m_cObjSelectedBitMap = Bitmap.createBitmap(m_cObjSelectedBitMap, 0, 0,
                            m_cObjSelectedBitMap.getWidth(), m_cObjSelectedBitMap.getHeight(), matrix, true);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(CustomerLoanEdit.this, m_cObjSelectedBitMap);

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

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(CustomerLoanEdit.this, m_cObjSelectedBitMap);

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

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                m_cFileGUID = DSAMacros.getGUID();
                File pdfFile = new File(DSAMacros.getPdfFilePath(CustomerLoanEdit.this), m_cFileGUID + ".pdf");
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile);
            } catch (Exception e) {
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
            } catch (Exception e) {
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
        final ProgressBar progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar1);

        if (null != pImgUrl) {
            jpegView.setVisibility(View.VISIBLE);
            try {
                progressBar.setVisibility(View.INVISIBLE);
                Picasso.with(this)
                        .load(pImgUrl)
                        .error(R.drawable.file)
                        .placeholder(R.drawable.file)
                        .into(jpegView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            } catch (Exception e) {
                progressBar.setVisibility(View.GONE);
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