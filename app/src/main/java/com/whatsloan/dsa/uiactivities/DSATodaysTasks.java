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
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfTodaysTasks;
import com.whatsloan.dsa.interfaces.RecyclerTasksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.StatusData;
import com.whatsloan.dsa.model.Statuses;
import com.whatsloan.dsa.model.Tasks;
import com.whatsloan.dsa.model.TasksData;
import com.whatsloan.dsa.model.TeamMembers;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;
import com.whatsloan.dsa.utils.ScalingUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 24/3/16.
 */
public class DSATodaysTasks extends DSABaseActivity implements RecyclerTasksListener, AdapterView.OnItemSelectedListener {

    @Nullable
    @Bind(R.id.ASSIGNED_TO)
    Spinner m_cSpinAssignTo;

    @Nullable
    @Bind(R.id.TASK_STATUS)
    Spinner m_cSpinTaskStatus;

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelLayGrp;

    @Nullable
    @Bind(R.id.ADD_LAY)
    RelativeLayout m_cAddLay;

    private View m_cView;

    private int m_cMembersPos = -1;
    private int m_cTasksPos = -1;
    private int m_cTasksDiagPos = -1;

    private boolean m_cIsMultiPartUpload = false;

    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;
    ArrayList<String> m_cTasksList;
    HashMap<String, String> m_cTasksDic;

    ArrayAdapter<String> m_cSpinAdapter;

    private static boolean FILTER_CLICKED = false;
    public static final int TAKE_PICTURE = 101;
    private static final int FILE_SELECT_CODE = 555;
    private Uri m_cSelectedImageUri;
    boolean m_cImageProcessing;
    private Bitmap m_cObjSelectedBitMap;
    public View dialogView;

    @Nullable
    @Bind(R.id.RECYC_TODAYSTASKS)
    RecyclerView m_cRecycTodaysTasks;

    @Nullable
    @Bind(R.id.RESULTS_TXT)
    TextView m_cResultHeader;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoData;

    public static final String TAG = "UPLOAD_FILE_TAG";
    ArrayList<TasksData> m_cTodaysList;
    public static TasksData m_cSingleTask;
    private CustomRecyclerAdapterForListOfTodaysTasks m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;
    int m_cPos;
    private static int page = 1;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.todays_task_list);

//        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
//        stub.setLayoutResource(R.layout.todays_task_list);
//        m_cView = stub.inflate();
        ButterKnife.bind(this);
        setTitle("Today's Task", false, true, true, false);

//        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
//        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cSpinAssignTo.setOnItemSelectedListener(this);
        m_cSpinTaskStatus.setOnItemSelectedListener(this);
        m_cTodaysList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(this);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycTodaysTasks.setLayoutManager(m_cLayoutManager);
        m_cRecycTodaysTasks.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        if (lDAuth.getRole().equalsIgnoreCase(DSAMacros.DSA_OWNER))
            m_cAddLay.setVisibility(View.VISIBLE);


        m_cMembersList = new ArrayList<>();
        m_cMembersList.add("Assigned To");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
        m_cSpinAssignTo.setAdapter(m_cSpinAdapter);

        m_cTasksList = new ArrayList<>();
        m_cTasksList.add("Task Status");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cTasksList);
        m_cSpinTaskStatus.setAdapter(m_cSpinAdapter);

        //Calling Cities api
        displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.USERTEAM, TeamMembers.class, this, lParams, false);

        //Calling Tasks status api
        RequestManager.getInstance(this).placeRequest(Constants.TASK_STATUSES, Statuses.class, this, null, false);

        //Calling Todays tasks api
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS + "," +
                Constants.STAGE + "," + Constants.USER + "," + Constants.CUSTOMER);
        RequestManager.getInstance(this).placeRequest(Constants.TODAYS_TASK, Tasks.class, this, llParams, false);
    }


    private void doPagination(int pPage) {
        m_cTodaysList.add(null);
        m_cRecycAdapter.notifyItemInserted(m_cTodaysList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS + "," +
                Constants.STAGE + "," + Constants.USER + "," + Constants.CUSTOMER);

        if (m_cMembersPos > 0) {
            lParams.put(Constants.ASSIGNED_TO_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
        }
        if (m_cTasksPos > 0) {
            lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
        }
        RequestManager.getInstance(this).placeRequest(Constants.TODAYS_TASK, Tasks.class, this, lParams, false);
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
                            File lfile = new File(DSAMacros.getImageFilePath(DSATodaysTasks.this), m_cImageGUID + ".jpg");

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
                            RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                            fileNameLay.setVisibility(View.VISIBLE);
                            TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                            fileName.setText(picturePath.toString());
                            fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);

                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);
                        } else {
                            m_cObjUIHandler.sendEmptyMessageDelayed(TAKE_PICTURE, 500);

                            File lFile = new File(DSAMacros.getImageFilePath(this), m_cImageGUID + ".jpg");
                            double kilobytes = (lFile.length() / 1024);
                            if(kilobytes > 2000) {
                                displaySnack(m_cRelLayGrp, "Selected file exceeds upload file limit");
                            }
                            RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                            fileNameLay.setVisibility(View.VISIBLE);
                            TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                            fileName.setText(lFile.toString());
                            fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);
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
                    if(kilobytes > 2000) {
                        displaySnack(m_cRelLayGrp, "Selected file exceeds upload file limit");
                    }
                    hideDialog();
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                    RelativeLayout fileNameLay = (RelativeLayout) dialog.findViewById(R.id.FILE_NAME_REL_LAY);
                    fileNameLay.setVisibility(View.VISIBLE);
                    TextView fileName = (TextView) dialog.findViewById(R.id.FILE_NAME_TXT);
                    fileName.setText(uri.getPath().toString());
                    fileName.setCompoundDrawables(getResources().getDrawable(R.drawable.file), null, null, null);

                    /*displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS);
                    if (m_cTasksPos > 0) {
                        lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
                    }
                    lParams.put(Constants.REMARKS, "babu file upload");
                    RequestManager.getInstance(DSATodaysTasks.this).placeMultiPartRequest(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()),
                            Response.class, this, lParams, lFile, Constants.PDF_UPLOAD);*/

                    /*StringBuffer lBuff = new StringBuffer();
                    lBuff.append("http://stage.52.77.80.241.xip.io/api/v1/");
                    lBuff.append(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()));
                    lBuff.append("?api_token=2f72104815eee9fb3d394e1f992ac771");
                    if (m_cTasksPos > 0) {
                        lBuff.append("&").append(Constants.TASK_STATUS_UUID);
                        lBuff.append("=").append(m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
                    }
                    lBuff.append("&").append(Constants.REMARKS);
                    lBuff.append("=").append("remarks");

                    verifyStoragePermissions(this);
                    new SendMultipartFile().execute(lBuff.toString(), uri.getPath());*/
                }
                break;
        }
    }

    /*private class SendMultipartFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String filename = params[1];
            Log.d(TAG, "UPLOAD: SendMultipartFile");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

//        File file = new File("/sdcard/spider.jpg");
            File file = new File(filename);

            Log.d(TAG, "UPLOAD: setting up multipart entity");

            MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            Log.d(TAG, "UPLOAD: file length = " + file.length());
            Log.d(TAG, "UPLOAD: file exist = " + file.exists());

            try {
                mpEntity.addPart("document", new FileBody(file, "application/octet"));
                mpEntity.addPart("id", new StringBody("1"));
            } catch (UnsupportedEncodingException e1) {
                Log.d(TAG, "UPLOAD: UnsupportedEncodingException");
                e1.printStackTrace();
            }

            httppost.setEntity(mpEntity);
            Log.d(TAG, "UPLOAD: executing request: " + httppost.getRequestLine());
            Log.d(TAG, "UPLOAD: request: " + httppost.getEntity().getContentType().toString());


            HttpResponse response;
            try {
                Log.d(TAG, "UPLOAD: about to execute");
                response = httpclient.execute(httppost);
                Log.d(TAG, "UPLOAD: executed");
                HttpEntity resEntity = response.getEntity();
                Log.d(TAG, "UPLOAD: respose code: " + response.getStatusLine().toString());
                if (resEntity != null) {
                    Log.d(TAG, "UPLOAD: " + EntityUtils.toString(resEntity));
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
        }
    }*/

    /*public static void executeMultipartPost(String url, String imgPath, String field1, String field2){
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost poster = new HttpPost(url);

            File lFile = new File(imgPath);  //get the actual file from the device
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            entity.addPart("field1", new StringBody(field1));
            entity.addPart("field2", new StringBody(field2));
            entity.addPart("image", new FileBody(lFile));
            poster.setEntity(entity);

            client.execute(poster, new ResponseHandler<Object>() {
                public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    HttpEntity respEntity = response.getEntity();
                    String responseString = EntityUtils.toString(respEntity);
                    // do something with the response string
                    return null;
                }
            });
        } catch (Exception e){
            //do something with the error
            e.printStackTrace();
        }
    }*/

    @OnClick({R.id.ADD_LAY, R.id.RESULTS_TXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ADD_LAY:
                //loan status api
                RequestManager.getInstance(this).placeRequest(Constants.USER_LOANS, Response.class, this, null, false);
                break;
            case R.id.RESULTS_TXT:
//                displayChatDialog();
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        Intent lObjIntent;
        switch (apiMethod) {
            case Constants.USERTEAM:
                TeamMembers lTeamMembers = (TeamMembers) response;
                if (lTeamMembers.getData().getMembers().getData().size() > 0) {
                    m_cMembersList = new ArrayList<>();
                    m_cMembersDic = new HashMap<>();
                    m_cMembersList.add("Assigned To");
                    for (MembersData lMembersData : lTeamMembers.getData().getMembers().getData()) {
                        m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                        m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
                    m_cSpinAssignTo.setAdapter(m_cSpinAdapter);
                }
                hideDialog();
                break;
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
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cTasksList);
                    m_cSpinTaskStatus.setAdapter(m_cSpinAdapter);
                }
                hideDialog();
                break;
            case Constants.TODAYS_TASK:
                if (!FILTER_CLICKED) {
                    Tasks lTasks = (Tasks) response;
                    if (lTasks.getData().size() > 0) {
                        if (m_cLoading) {
                            for (TasksData lTasksData : lTasks.getData()) {
                                m_cTodaysList.add(lTasksData);
                            }
                            if (null != m_cTodaysList && m_cTodaysList.size() > 0) {
                                m_cRecycAdapter = new CustomRecyclerAdapterForListOfTodaysTasks(this, m_cTodaysList, this);
                                m_cRecycTodaysTasks.setAdapter(m_cRecycAdapter);
                            }
                        } else {
                            m_cTodaysList.remove(m_cTodaysList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cTodaysList.size());
                            for (TasksData lTasksData : lTasks.getData()) {
                                m_cTodaysList.add(lTasksData);
                            }
                            m_cRecycAdapter.notifyItemInserted(m_cTodaysList.size());
                            m_cLoading = true;
                        }
                        m_cNoData.setVisibility(View.GONE);
                    } else {
                        if (m_cTodaysList.size() > 0) {
                            m_cTodaysList.remove(m_cTodaysList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cTodaysList.size());
                            m_cLoading = false;
                            page = 1;
                        } else {
                            m_cNoData.setVisibility(View.VISIBLE);
                        }
                    }
                    hideDialog();
                } else {
                    Tasks lTasks = (Tasks) response;
                    if (lTasks.getData().size() > 0) {
                        /*m_cRecycAdapter.notifyItemRangeRemoved(0, m_cTodaysList.size());
                        m_cTodaysList = new ArrayList<>();*/
                        if (m_cLoading) {
                            m_cTodaysList.clear();
                            m_cRecycAdapter.notifyDataSetChanged();
                            for (TasksData lTasksData : lTasks.getData()) {
                                m_cTodaysList.add(lTasksData);
                            }
                            if (null != m_cTodaysList && m_cTodaysList.size() > 0) {
                                m_cRecycAdapter.notifyDataSetChanged();
                            }
                        } else {
                            m_cTodaysList.remove(m_cTodaysList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cTodaysList.size());
                            for (TasksData lTasksData : lTasks.getData()) {
                                m_cTodaysList.add(lTasksData);
                            }
                            m_cRecycAdapter.notifyItemInserted(m_cTodaysList.size());
                            m_cLoading = true;
                        }
                        FILTER_CLICKED = false;
                        m_cNoData.setVisibility(View.GONE);
                    } else {
                        if (!m_cLoading) {
                            m_cTodaysList.remove(m_cTodaysList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cTodaysList.size());
                            m_cLoading = false;
                        } else {
                            if (m_cTodaysList.size() > 0) {
                                m_cTodaysList.clear();
                                m_cRecycAdapter.notifyDataSetChanged();
                                hideDialog();
                                m_cLoading = false;
                                page = 1;
                            } else {
                                m_cNoData.setVisibility(View.VISIBLE);
                            }
                        }
                        FILTER_CLICKED = false;
                    }
                    hideDialog();
                }
                break;
            case Constants.USER_LOANS:
                Response lResponse1 = (Response) response;
                if (lResponse1.getData().getStatus() == true) {
                    lObjIntent = new Intent(this, TaskAddEditScreen.class);
                    lObjIntent.putExtra("IS_NEW_TASK", true);
                    startActivity(lObjIntent);
                } else {
                    displaySnack(m_cRelLayGrp, "No Loans Available");
                }
                break;
            default:
                dialog.dismiss();
                hideDialog();
                onResume();
                displaySnack(m_cRelLayGrp, "Successfully uploaded");
                m_cIsMultiPartUpload = false;
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        String ljson;
        String lMsg;
        super.onErrorResponse(error, apiMethod);
        switch (apiMethod) {
            case Constants.USERTEAM:
                hideDialog();
                break;
            case Constants.TASK_STATUSES:
                hideDialog();
                break;
            case Constants.TODAYS_TASK:
                page = 1;
                if (!FILTER_CLICKED) {
                    hideDialog();
                    FILTER_CLICKED = false;
                }
                break;
            default:
                dialog.dismiss();
                if(!m_cIsMultiPartUpload) {
                    if (error.networkResponse.statusCode == 422) {
                        ljson = new String(error.networkResponse.data);
                        if (null != trimMessage(ljson, Constants.REMARKS_STATUS)) {
                            displayToast(trimMessage(ljson, Constants.REMARKS_STATUS));
                        } else if (null != trimMessage(ljson, Constants.TASK_STATUS_UUID_STATUS)) {
                            displayToast(trimMessage(ljson, Constants.TASK_STATUS_UUID_STATUS));
                        } else {
                            displayToast(trimMessage(ljson, "Please select file from local storage"));
                        }
                    }
                }else {
                    displayToast("Please select file from local storage");
                }
                hideDialog();
                m_cIsMultiPartUpload = false;
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.ASSIGNED_TO:
                m_cMembersPos = position;
                if (position > 0) {
                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS + "," +
                            Constants.STAGE + "," + Constants.USER + "," + Constants.CUSTOMER);

                    if (m_cMembersPos > 0) {
                        lParams.put(Constants.ASSIGNED_TO_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                    }
                    if (m_cTasksPos > 0) {
                        lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.TODAYS_TASK, Tasks.class, this, lParams, false);
                    m_cLoading = true;
                    FILTER_CLICKED = true;
                }
                break;
            case R.id.TASK_STATUS:
                m_cTasksPos = position;
                if (position > 0) {
                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS + "," +
                            Constants.STAGE + "," + Constants.USER + "," + Constants.CUSTOMER);

                    if (m_cMembersPos > 0) {
                        lParams.put(Constants.ASSIGNED_TO_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                    }
                    if (m_cTasksPos > 0) {
                        lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.TODAYS_TASK, Tasks.class, this, lParams, false);
                    m_cLoading = true;
                    FILTER_CLICKED = true;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onChatClick(int pPostion, TasksData pTasksData, View pView) {
        m_cSingleTask = pTasksData;
        displayChatDialog(pTasksData);
    }

    @Override
    public void onEditClick(int pPostion, TasksData pTasksData, View pView) {
        Intent lObjIntent = new Intent(this, TaskAddEditScreen.class);
        lObjIntent.putExtra("IS_NEW_TASK", false);
        lObjIntent.putExtra("TASKS_DATA", pTasksData);
        lObjIntent.putExtra("CUSTOMER_DETAILS", pTasksData.getCustomer().getData());
        startActivity(lObjIntent);
    }

    @Override
    public void onCallClick(int pPostion, TasksData pTasksData, View pView, String pNo) {

    }

    private void displayChatDialog(final TasksData pTasksData) {

        dialogView = View.inflate(this, R.layout.update_task_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_plain_bold, m_cTasksList);
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
                m_cTasksDiagPos = position;
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

        followUp.setText("Followup with " + pTasksData.getMembers().getData().get(0).getFirstName() +
                " " + pTasksData.getMembers().getData().get(0).getLastName());

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
                displayProgressBar(-1, "Loading");
                HashMap<String, String> lParams = new HashMap<>();
                lParams.put(Constants.INSCLUDE, Constants.MEMBERS + "," + Constants.STATUS);
                if(fileDesc.getText().toString().trim().length() > 0){
                    lParams.put(Constants.NAME, fileDesc.getText().toString());
                }
                if (m_cTasksDiagPos > 0) {
                    lParams.put(Constants.TASK_STATUS_UUID, m_cTasksDic.get(m_cTasksList.get(m_cTasksDiagPos)));
                }
                if(remarksEdit.getText().toString().trim().length() > 0) {
                    lParams.put(Constants.REMARKS, remarksEdit.getText().toString());
                }
                if(fileName.getText().toString().trim().length() > 0) {
                    if(checkRemarksName(remarksEdit.getText().toString().trim(), fileDesc.getText().toString().trim())){
                        RequestManager.getInstance(DSATodaysTasks.this).placeMultiPartRequest(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()),
                                Response.class, DSATodaysTasks.this, lParams,
                                new File(fileName.getText().toString()),
                                Constants.PDF_UPLOAD);
                        m_cIsMultiPartUpload = true;
                    }else {
                        hideDialog();
                    }
                }else {
                    if(checkRemarks(remarksEdit.getText().toString().trim())) {
                        RequestManager.getInstance(DSATodaysTasks.this).placeRequest(Constants.apiMethodEx(Constants.TASKS_UPDATESTATUS, m_cSingleTask.getUuid()),
                                Response.class, DSATodaysTasks.this, lParams,
                                true);
                        m_cIsMultiPartUpload = false;
                    }else {
                        hideDialog();
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
            displayToast("Please enter remarks");
            return false;
        }else if (lName.isEmpty()){
            displayToast("Please enter file name");
            return false;
        }else {
            lRetVal = true;
        }
        return lRetVal;
    }

    private boolean checkRemarks(String pRem) {
        boolean lRetVal = false;
        if (pRem.isEmpty()){
            displayToast("Please enter remarks");
            return false;
        }else {
            lRetVal = true;
        }
        return lRetVal;
    }

    public void showGallery() {
        verifyStoragePermissions(this);
//        m_cObjUIHandler.sendEmptyMessage(DELETE_CACHES);
        m_cImageGUID = DSAMacros.getGUID();
        File lFile = new File(DSAMacros.getImageFilePath(DSATodaysTasks.this), m_cImageGUID + ".jpg");
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
        File lFile = new File(DSAMacros.getImageFilePath(DSATodaysTasks.this), m_cImageGUID + ".jpg");
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

                File lFile = new File(DSAMacros.getImageFilePath(DSATodaysTasks.this), m_cImageGUID + ".jpg");
                if (lFile.exists()) {
                    m_cObjSelectedBitMap = BitmapFactory.decodeFile(lFile.getAbsolutePath());
                    Matrix matrix = new Matrix();

                    m_cObjSelectedBitMap = Bitmap.createBitmap(m_cObjSelectedBitMap, 0, 0,
                            m_cObjSelectedBitMap.getWidth(), m_cObjSelectedBitMap.getHeight(), matrix, true);

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(DSATodaysTasks.this, m_cObjSelectedBitMap);

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

                    m_cObjSelectedBitMap = ScalingUtilities.scaleCameraImage(DSATodaysTasks.this, m_cObjSelectedBitMap);

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
}
