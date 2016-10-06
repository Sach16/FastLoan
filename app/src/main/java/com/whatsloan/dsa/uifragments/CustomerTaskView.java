package com.whatsloan.dsa.uifragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfCustomersTasks;
import com.whatsloan.dsa.interfaces.RecyclerTasksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.StatusData;
import com.whatsloan.dsa.model.Statuses;
import com.whatsloan.dsa.model.Tasks;
import com.whatsloan.dsa.model.TasksData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.TaskAddEditScreen;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;
import com.whatsloan.dsa.utils.ScalingUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 3/4/16.
 */
public class CustomerTaskView extends DSAFragmentBaseClass implements RecyclerTasksListener {

    @Nullable
    @Bind(R.id.RECYC_CUST_TASKS)
    RecyclerView m_cRecycTasks;

    @Nullable
    @Bind(R.id.ADD_OR_EDIT_LAY)
    RelativeLayout m_cAddTaskLay;

    Dialog dialog;
    public static final String TAG = "UPLOAD_FILE_TAG";
    public static TasksData m_cSingleTask;
    public static final int TAKE_PICTURE = 101;
    private static final int FILE_SELECT_CODE = 555;
    public static final int RESULT_OK = -1;
    private Uri m_cSelectedImageUri;
    boolean m_cImageProcessing;
    private Bitmap m_cObjSelectedBitMap;
    public View dialogView;

    String m_cJsonObject;
    int m_cPos;
    private ScrollView m_cScroll;
    CustomersData m_cObjCustomer;

    private int m_cTasksPos = -1;

    private boolean m_cIsMultiPartUpload = false;

    ArrayList<String> m_cTasksList;
    HashMap<String, String> m_cTasksDic;

    ArrayAdapter<String> m_cSpinAdapter;

    ArrayList<TasksData> m_cTodaysList;
    private CustomRecyclerAdapterForListOfCustomersTasks m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;

    public CustomerTaskView() {
        super();
    }

    public static CustomerTaskView newInstance(int pPosition, String pJsonObject, CustomersData pObjCustomer) {
        CustomerTaskView lCustomerTaskView = new CustomerTaskView();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        args.putParcelable("CustomerObject", pObjCustomer);
        lCustomerTaskView.setArguments(args);

        return lCustomerTaskView;
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
        m_cObjMainView = inflater.inflate(R.layout.customer_tasks, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        m_cLayoutManager = new LinearLayoutManager(m_cObjMainActivity);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycTasks.setLayoutManager(m_cLayoutManager);
        return m_cObjMainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cObjMainActivity.m_cObjFragmentBase = CustomerTaskView.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cJsonObject = getArguments().getString("JsonObject");
        m_cObjCustomer = getArguments().getParcelable("CustomerObject");

        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        if (lDAuth.getRole().equalsIgnoreCase(DSAMacros.DSA_OWNER))
            m_cAddTaskLay.setVisibility(View.VISIBLE);

        m_cTodaysList = new ArrayList<>();
        TasksData tasksData = new TasksData(TasksData.CUSTOMER_INFO);
        tasksData.setCustomersData(m_cObjCustomer);
        m_cTodaysList.add(tasksData);
        m_cRecycAdapter = new CustomRecyclerAdapterForListOfCustomersTasks(m_cObjMainActivity, m_cTodaysList, this);
        m_cRecycTasks.setAdapter(m_cRecycAdapter);

        //Calling Tasks status api
        m_cObjMainActivity.displayProgressBar(-1, "Loading...");

        //calling tasks statuses api
        placeRequest(Constants.TASK_STATUSES, Statuses.class, null, false);

        //call cust tasks api
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS + "," + Constants.STAGE + "," + Constants.USER);
//        llParams.put(Constants.INSCLUDE, Constants.TASKS_MEMBERS + "," + Constants.TASKS_STATUS + "," + Constants.TASKS_STAGE + "," + Constants.TASKS_USER);
        if (null != DSAMacros.getLoanUuidId(m_cObjMainActivity)) {
            llParams.put(Constants.LOAN_UUID, DSAMacros.getLoanUuidId(m_cObjMainActivity));
        }
        placeRequest(Constants.TASKS_GETLOANTASKS, Tasks.class, llParams, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        m_cObjMainActivity.displayProgressBar(-1, "Loading...");
                        long ldate = 0;
                        Uri lUriwodata = null;
                        try {
                            lUriwodata = data.getData();
                        } catch (Exception e) {
                            lUriwodata = null;
                        }
                        if (data != null && lUriwodata != null) {
                            File lfile = new File(DSAMacros.getImageFilePath(m_cObjMainActivity), m_cObjMainActivity.m_cImageGUID + ".jpg");

                            m_cSelectedImageUri = data == null ? null : data.getData();
                            String lImageName = m_cObjMainActivity.m_cImageGUID + ".jpg";

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            Cursor cursor = m_cObjMainActivity.getContentResolver().query(m_cSelectedImageUri,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            m_cObjMainActivity.copyFile(picturePath, lfile.getAbsolutePath());
                            RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                            fileNameLay.setVisibility(View.VISIBLE);
                            TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                            fileName.setText(picturePath.toString());
                            fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);

                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);
                        } else {
                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);

                            File lFile = new File(DSAMacros.getImageFilePath(m_cObjMainActivity), m_cObjMainActivity.m_cImageGUID + ".jpg");
                            double kilobytes = (lFile.length() / 1024);
                            if(kilobytes > 2000) {
                                m_cObjMainActivity.displaySnack(m_cObjMainView, "Selected file exceeds upload file limit");
                            }
                            RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                            fileNameLay.setVisibility(View.VISIBLE);
                            TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                            fileName.setText(lFile.toString());
                            fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);
                        }
//						deletePicFromDCIM();
                    } catch (Exception e) {
                        m_cObjMainActivity.displaySnack(m_cObjMainView, "Unable to retrieve Image");
                        e.printStackTrace();
                        Log.w("IMAGE_NAME  : ", m_cObjMainActivity.m_cImageGUID);
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
                    m_cObjMainActivity.hideDialog();
                    double kilobytes = (lFile.length() / 1024);
                    if(kilobytes > 2000) {
                        m_cObjMainActivity.displaySnack(m_cObjMainView, "Selected file exceeds upload file limit");
                    }
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                    RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                    fileNameLay.setVisibility(View.VISIBLE);
                    TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                    fileName.setText(uri.getPath().toString());
                    fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);

                }
                break;
        }
    }

    @OnClick(R.id.ADD_OR_EDIT_LAY)
    public void onClick(View v) {
        Intent lObjIntent;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ADD_OR_EDIT_LAY:
                lObjIntent = new Intent(m_cObjMainActivity, TaskAddEditScreen.class);
                lObjIntent.putExtra("IS_NEW_TASK", true);
                lObjIntent.putExtra("CUSTOMER_DETAILS", m_cObjCustomer);
                if (null != DSAMacros.getLoanUuidId(m_cObjMainActivity)) {
                    lObjIntent.putExtra("LOAN_UUID", DSAMacros.getLoanUuidId(m_cObjMainActivity));
                }
                startActivity(lObjIntent);
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        m_cObjMainActivity.hideDialog();
        switch (apiMethod) {
            case Constants.TASK_STATUSES:
                Statuses lStatuses = (Statuses) response;
                if (lStatuses.getData().size() > 0) {
                    m_cTasksList = new ArrayList<>();
                    m_cTasksDic = new HashMap<>();
                    m_cTasksList.add("Task Status");
                    for (StatusData lStatusData : lStatuses.getData()) {
                        m_cTasksList.add(lStatusData.getLabel());
                        m_cTasksDic.put(lStatusData.getLabel(), lStatusData.getUuid());
                    }
                    //Note: Showing the statuses list only in the alert dialog
//                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cTasksList);
//                    m_cSpinTaskStatus.setAdapter(m_cSpinAdapter);
                }
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.TASKS_GETLOANTASKS:
                Tasks lTasks = (Tasks) response;
                if (lTasks!=null && lTasks.getData() !=null && lTasks.getData().size() > 0) {
                        for (TasksData lTasksData : lTasks.getData()) {
                            lTasksData.setType(TasksData.VIEW_ITEM);
                            m_cTodaysList.add(lTasksData);
                        }
                        if (null != m_cTodaysList && m_cTodaysList.size() > 0) {
                            if (m_cRecycAdapter != null)
                                m_cRecycAdapter.notifyDataSetChanged();
                        }
                }
                break;
            default:
                dialog.dismiss();
                onResume();
                m_cObjMainActivity.displaySnack(m_cObjMainView, "Successfully uploaded");
                m_cIsMultiPartUpload = false;
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        String ljson;
        m_cObjMainActivity.hideDialog();
        switch (apiMethod) {
            case Constants.TASK_STATUSES:
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.TASKS_GETLOANTASKS:
                m_cObjMainActivity.hideDialog();
                break;
            default:
                dialog.dismiss();
                if(!m_cIsMultiPartUpload) {
                    if (error.networkResponse.statusCode == 422) {
                        ljson = new String(error.networkResponse.data);
                        if (null != m_cObjMainActivity.trimMessage(ljson, Constants.REMARKS_STATUS)) {
                            m_cObjMainActivity.displayToast(m_cObjMainActivity.trimMessage(ljson, Constants.REMARKS_STATUS));
                        } else if (null != m_cObjMainActivity.trimMessage(ljson, Constants.TASK_STATUS_UUID_STATUS)) {
                            m_cObjMainActivity.displayToast(m_cObjMainActivity.trimMessage(ljson, Constants.TASK_STATUS_UUID_STATUS));
                        } else {
                            m_cObjMainActivity.displayToast(m_cObjMainActivity.trimMessage(ljson, "Please select file from local storage"));
                        }
                    }
                }else {
                    m_cObjMainActivity.displayToast("Please select file from local storage");
                }
                m_cObjMainActivity.hideDialog();
                m_cIsMultiPartUpload = false;
                break;
        }
    }

    @Override
    public void onChatClick(int position, TasksData pTasksData, View pView) {
        m_cSingleTask = pTasksData;
        displayChatDialog(pTasksData);
    }

    @Override
    public void onEditClick(int position, TasksData pTasksData, View pView) {
        Intent lObjIntent = new Intent(m_cObjMainActivity, TaskAddEditScreen.class);
        lObjIntent.putExtra("IS_NEW_TASK", false);
        lObjIntent.putExtra("TASKS_DATA", pTasksData);
        lObjIntent.putExtra("CUSTOMER_DETAILS", m_cObjCustomer);
        startActivity(lObjIntent);
    }

    @Override
    public void onCallClick(int pPostion, TasksData pTasksData, View pView, String pNo) {
        if (null != pNo) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + pNo));
            m_cObjMainActivity.verifyCallPermissions(m_cObjMainActivity);
            if (ActivityCompat.checkSelfPermission(m_cObjMainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
    }

    private void displayChatDialog(final TasksData pTasksData) {

        dialogView = View.inflate(m_cObjMainActivity, R.layout.update_task_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(m_cObjMainActivity);

        ImageView imgView = (ImageView) dialogView.findViewById(R.id.DIALOG_CLOSE);
        TextView uploadFile = (TextView) dialogView.findViewById(R.id.UPLOAD_FILE_TXT);
        TextView takePhoto = (TextView) dialogView.findViewById(R.id.TAKE_PHOTO_TXT);
        TextView uploadPhoto = (TextView) dialogView.findViewById(R.id.UPLOAD_PHOTO_TXT);
        TextView followUp = (TextView) dialogView.findViewById(R.id.UPDATE_MSG_TXT);
        final TextView fileDesc = (TextView) dialogView.findViewById(R.id.FILE_DESC_EDIT);
        RelativeLayout fileNameLay = (RelativeLayout) dialogView.findViewById(R.id.FILE_NAME_REL_LAY);
        final TextView fileName = (TextView) dialogView.findViewById(R.id.FILE_NAME_TXT);
        Spinner statusSpin = (Spinner) dialogView.findViewById(R.id.STAUS_SPINNER);
        final TextView txtWatch = (TextView) dialogView.findViewById(R.id.TXT_WATCH);
        final EditText remarksEdit = (EditText) dialogView.findViewById(R.id.ADDLSR_MAIN_TXT);
        TextView submitTasks = (TextView) dialogView.findViewById(R.id.SUBMIT_TASKS_DIAG_TXT);
        final TextView cancelTasks = (TextView) dialogView.findViewById(R.id.CANCEL_TASKS_DIAG_TXT);

        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cTasksList);
        statusSpin.setAdapter(m_cSpinAdapter);
        if (null != pTasksData) {
//            remarksEdit.setText(pTasksData.getDescription());
            txtWatch.setText("(" + remarksEdit.getText().toString().length() + "/162)");
            int lPos = 0;
            try {
                lPos = m_cTasksList.indexOf(pTasksData.getStatus().getData().getLabel());
                statusSpin.setSelection(lPos);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String lLabel = pTasksData.getStatus().getData().getLabel();
            if(lLabel.equalsIgnoreCase(DSAMacros.COMPLETED_STATUS)||lLabel.equalsIgnoreCase(DSAMacros.CANCELLED_STATUS)
                    ||lLabel.equalsIgnoreCase(DSAMacros.OVERDUE_STATUS))
                statusSpin.setEnabled(false);
        }
        statusSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_cTasksPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        remarksEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                m_cQueryTxtCount.setText("("+(count+after+start)+"/162)");
                txtWatch.setText("(" + remarksEdit.getText().toString().length() + "/162)");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                m_cQueryTxtCount.setText("(" + (1 + before + start) + "/162)");
                txtWatch.setText("(" + remarksEdit.getText().toString().length() + "/162)");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


//        followUp.setText("Followup with " + pTasksData.getMembers().getData().get(0).getFirstName() +
//                " " + pTasksData.getMembers().getData().get(0).getLastName());

        builder.setView(dialogView);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cImageProcessing = true;
                takePhoto();
            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cImageProcessing = true;
                showGallery();
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submitTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cObjMainActivity.displayProgressBar(-1, "Loading");
                HashMap<String, String> lParams = new HashMap<>();
                lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS);
                if (fileDesc.getText().toString().trim().length() > 0) {
                    lParams.put(Constants.NAME, fileDesc.getText().toString());
                }
                if (m_cTasksPos > 0) {
                    lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
                }
                if(remarksEdit.getText().toString().trim().length() > 0) {
                    lParams.put(Constants.REMARKS, remarksEdit.getText().toString());
                }
                if (fileName.getText().toString().trim().length() > 0) {
                    if (checkRemarksName(remarksEdit.getText().toString().trim(), fileDesc.getText().toString().trim())) {
                        placeMultiPartRequest(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()),
                                Response.class, lParams,
                                new File(fileName.getText().toString()),
                                Constants.PDF_UPLOAD);
                        m_cIsMultiPartUpload = true;
                    } else {
                        m_cObjMainActivity.hideDialog();
                    }
                } else {
                    if (checkRemarks(remarksEdit.getText().toString().trim())) {
                        placeRequest(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()),
                                Response.class, lParams,
                                true);
                        m_cIsMultiPartUpload = false;
                    } else {
                        m_cObjMainActivity.hideDialog();
                    }
                }
            }
        });
        cancelTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private boolean checkRemarksName(String pRem, String lName) {
        boolean lRetVal = false;
        if (pRem.isEmpty()){
            m_cObjMainActivity.displayToast("Please enter remarks");
            return false;
        }else if (lName.isEmpty()){
            m_cObjMainActivity.displayToast("Please enter file name");
            return false;
        }else {
            lRetVal = true;
        }
        return lRetVal;
    }

    private boolean checkRemarks(String pRem) {
        boolean lRetVal = false;
        if (pRem.isEmpty()) {
            m_cObjMainActivity.displayToast("Please enter remarks");
            return false;
        }else {
            lRetVal = true;
        }
        return lRetVal;
    }

    public void showGallery() {
        m_cObjMainActivity.verifyStoragePermissions(m_cObjMainActivity);
//        m_cObjUIHandler.sendEmptyMessage(DELETE_CACHES);
        m_cObjMainActivity.m_cImageGUID = DSAMacros.getGUID();
        File lFile = new File(DSAMacros.getImageFilePath(m_cObjMainActivity), m_cObjMainActivity.m_cImageGUID + ".jpg");
        Uri imageUri = Uri.fromFile(lFile);

        // Gallery.
        m_cObjMainActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(galleryIntent, TAKE_PICTURE);
    }

    public void takePhoto() {
//            m_cObjUIHandler.sendEmptyMessage(DELETE_CACHES);
        m_cObjMainActivity.m_cImageGUID = DSAMacros.getGUID();
        File lFile = new File(DSAMacros.getImageFilePath(m_cObjMainActivity), m_cObjMainActivity.m_cImageGUID + ".jpg");
        Uri imageUri = Uri.fromFile(lFile);

        // Camera.
        //		final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(captureIntent, TAKE_PICTURE);
    }

    private void showFileChooser() {
        m_cObjMainActivity.verifyStoragePermissions(m_cObjMainActivity);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(m_cObjMainActivity, "Please install a File Manager.",
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

                File lFile = new File(DSAMacros.getImageFilePath(m_cObjMainActivity), m_cObjMainActivity.m_cImageGUID + ".jpg");
                if (lFile.exists()) {
                    m_cObjSelectedBitMap = BitmapFactory.decodeFile(lFile.getAbsolutePath());
                    Matrix matrix = new Matrix();

                    m_cObjSelectedBitMap = Bitmap.createBitmap(m_cObjSelectedBitMap, 0, 0,
                            m_cObjSelectedBitMap.getWidth(), m_cObjSelectedBitMap.getHeight(), matrix, true);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(m_cObjMainActivity, m_cObjSelectedBitMap);

                    m_cObjMainActivity.pTakePicture = true;
                    m_cObjMainActivity.pSavePicture = false;

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
//                    savePicture(lDate);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(m_cObjMainActivity, m_cObjSelectedBitMap);

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
            m_cObjMainActivity.hideDialog();
        }
    }
}

