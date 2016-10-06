package com.whatsloan.dsa.macros;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.utils.ScalingUtilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by S.K. Pissay on 20/3/16.
 */
public class DSAMacros {

    public static boolean m_cGPSEnabled = false;
    public static Location m_cObjCurLocation = null;

    public static final int NOTIFICATION_FOR_NO_NETWORK_CONNECTION = -68653;
    public static final int NOTIFICATION_FOR_NETWORK_CONNECTION_AVAILABLE = 1001;
    public static final int NOTIFICATION_NO_NETWORK_CONNECTION_RETRY = 1002;

    public static final int YES_MESSAGE = 1111;
    public static final int NO_MESSAGE = 1112;

    public static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";
    public static final String DISPLAY_DATE_TIME_FORMAT = "dd MMM yyyy HH:mm";
    public static final String DISPLAY_DATE_TIME_FORMAT_DAY = "E, dd MMM yyyy";
    public static final String DEFAULT_DATEFORMAT_MMDDYYYY = "MM-dd-yyyy";
    public static final String DEFAULT_TIMEFORMAT_HHMMSS = "HH:mm:ss";
    public static final String DATE_FORMAT_EEEMMDD_HHMMSS_ZYYYY = "EEE MMM dd HH:mm:ss z yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD_SSZ = "yyyy-MM-dd'T'HH:mm:ssZ";//yyyy-MM-dd'T'HH:mm:ssZ
    public static final String DATE_FORMAT_YYYY_MM_DD_HH = "yyyy:MM:dd HH:mm:ss";//2015:08:28 08:56:15
    public static final String TIME_FORMAT_HHMM_AM_PM = "hh:mm aa";
    public static final String DATE_FORMAT_DDMMMYYYY = "dd MMM yyyy";
    public static final String DATE_FORMAT_DDMMYYYY = "dd MMM";
    public static final String DATE_FORMAT_DOTTED_MMDDYY = "MM.dd.yy";
    public static final String DATE_FORMAT_UNDERSC_YYYY_MM_DD_HHMMSS_SSSS  = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
    public static final String DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS  = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_UNDERSC_DDMMYYYY_HHMMAA  = "dd-MM-yyyy hh:mm aa";


    public static final String DEFAULT_DATEFORMAT_DDMMYYYY = "dd-MM-yyyy";
    public static final String DEFAULT_DATEFORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String TIME_FORMAT_HHMMAA = "hh:mmaa";

    public static final String DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS_SSSS  = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_UNDERSC_YYYY_MM_DD_HHMMSS_ssss  = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS_SSSS  = "yyyy.MM.dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS_T  = "yyyy-MM-dd'T'HH:mm:ss";

    //public static final String ANDROID_APP_ID = "ANDROID_PATIENT_V1.1";
    public static final String EMAIL_PATTERN_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String PHONE_PATTERN_REGEX = "^[2-9]{1}[0-9]{9}$";

    public static final String ALPHA_NUMERIC_SEARCH_REGEX = "^[a-zA-Z0-9 -]*$";

    public static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9 ]*$";

    public static final String ALPHABETIC_REGEX = "^[a-zA ]*$";

    public static final String INCLUDE = "include";
    public static final String PAGINATION_PAGE = "page";
    public static final String LOGIN_PHONE_NUMBER = "phone";
    public static final String LOGIN_OTP = "otp";
    public static final String LOGIN_ROLE = "app_role";
    public static final String LOGIN_ROLE_SPE = "DSA";
    public static final String TO_BE_STARTED = "TO_BE_STARTED";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String COMPLETED = "COMPLETED";
    public static final String DSA_OWNER = "DSA_OWNER";
    public static final String DSA_MEMBER = "DSA_MEMBER";

    public static final String COMPLETED_STATUS = "Completed";
    public static final String CANCELLED_STATUS = "Cancelled";
    public static final String OVERDUE_STATUS = "Overdue";

    public static final int ABSENT = 0;
    public static final int PRESENT = 1;
    public static final int TODAY = 2;
    public static final int HOLIDAY = 3;
    public static final int PRODUCT = 1234132;

    public static String BINARY_MEDIA_TYPE_JPEG = "image/jpeg";
    public static String BINARY_MEDIA_TYPE_JPG = "image/jpg";
    public static String BINARY_MEDIA_TYPE_WAV = "audio/wav";
    public static String BINARY_MEDIA_TYPE_AMR = "audio/amr";
    public static String IMAGE_FILE_EXTENSIONS = "jpeg jpg bmp gif";
    public static String AUDIO_FILE_EXTENSIONS = "wav amr mp3";
    public static String TXT_FILE_EXT = "txt";
    public static String SPACE = " ";

    public static String EPRESCRIPTION_PATH = Environment.getExternalStorageDirectory()+"/EURemedi/EPrescreption";

    public static void ShowFile(Context pObjContext, String pPath){
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(pPath);

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(Uri.fromFile(file), type);

        try {
            pObjContext.startActivity(intent);
        } catch (Exception e){
            Toast.makeText(pObjContext, "Not able to open the file format,..", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static boolean isNetworkConnected(Context pObjContext) {
        ConnectivityManager cm = (ConnectivityManager) pObjContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static String getRupees(String pAmount) {
        StringBuffer lRetVal = new StringBuffer();
        if (null != pAmount && pAmount.toString().trim().length() > 0) {
            if (pAmount.contains("₹"))
                return pAmount;
            else
                lRetVal.append("\u20B9 ").append(pAmount);
        } else {
            lRetVal.append("\u20B9 ").append("0");
        }
        return lRetVal.toString();
    }

    //Slug to label
    public static String s2l(String pSlug){
        StringBuffer lBuff = new StringBuffer();
        String lSample = pSlug.toLowerCase().replace("_" , " ");
        String[] titleArray = lSample.split(" ");
        for(String lparam : titleArray){
            lBuff.append(Character.toUpperCase(lparam.charAt(0))).append(lparam.substring(1)).append(" ");
        }
        return lBuff.toString();
    }

    public static String getMIMEtype(String pFileName) {
        String lRetVal = SPACE;
        if (null != pFileName) {
            int lIndex = pFileName.lastIndexOf('.');
            if (lIndex > 0) {
                String lExtention = pFileName.substring(lIndex + 1);
                if (null != lExtention) {
                    if (AUDIO_FILE_EXTENSIONS.indexOf(lExtention.toLowerCase()) != -1) {
                        lRetVal = BINARY_MEDIA_TYPE_AMR;
                    } else if (IMAGE_FILE_EXTENSIONS.indexOf(lExtention.toLowerCase()) != -1) {
                        lRetVal = BINARY_MEDIA_TYPE_JPG;
                    }
                }
            }
        }
        return lRetVal;
    }

    public static void showCustomAlert(Context pObjContext, int pObjX, int pObjY) {
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = (LayoutInflater) pObjContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.toast, null);

        Toast toast = new Toast(pObjContext);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER, pObjX, pObjY);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static int roundOffFloat(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Integer.valueOf(twoDForm.format(d));
    }

    public static void exportDatabse(Context pObjContext, String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+pObjContext.getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    public static String getDateFormatDDMMYYYY(Date pDate, String pDateTxt) {
        String lFormatedDate = null;
        SimpleDateFormat lObjFormat = new SimpleDateFormat(DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY);
        Date lObjDate = null;
        try {
            if(null != pDateTxt) {
                SimpleDateFormat lObjFormat1 = new SimpleDateFormat(DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS);
                lObjDate = lObjFormat1.parse(pDateTxt);
            } else if(null != pDate){
                lObjDate = pDate;
            }
            if(null != lObjDate) {
//					lObjFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US);
                lFormatedDate = lObjFormat.format(lObjDate);
            }
        } catch(Exception ex) {
            lFormatedDate = "";
            ex.printStackTrace();
        }
        return lFormatedDate;
    }

    public static String getDateFormatYYYYMMDD(Date pDate, String pDateTxt) {
        String lFormatedDate = null;
        SimpleDateFormat lObjFormat = new SimpleDateFormat(DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS);
        Date lObjDate = null;
        try {
            if(null != pDateTxt) {
                SimpleDateFormat lObjFormat1 = new SimpleDateFormat(DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY);
                lObjDate = lObjFormat1.parse(pDateTxt);
            } else if(null != pDate){
                lObjDate = pDate;
            }
            if(null != lObjDate) {
//					lObjFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US);
                lFormatedDate = lObjFormat.format(lObjDate);
            }
        } catch(Exception ex) {
            lFormatedDate = "";
            ex.printStackTrace();
        }
        return lFormatedDate;
    }

    public static String getDateFormat(Date pDate, String pDateTxt, String pInPut, String pOutPut) {
        String lFormatedDate = null;
        SimpleDateFormat lObjFormat = new SimpleDateFormat(pOutPut);
        Date lObjDate = null;
        try {
            if(null != pDateTxt) {
                SimpleDateFormat lObjFormat1 = new SimpleDateFormat(pInPut);
                lObjDate = lObjFormat1.parse(pDateTxt);
            } else if(null != pDate){
                lObjDate = pDate;
            }
            if(null != lObjDate) {
//					lObjFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.US);
                lFormatedDate = lObjFormat.format(lObjDate);
            }
        } catch(Exception ex) {
            lFormatedDate = "";
            ex.printStackTrace();
        }
        return lFormatedDate;
    }

    public static Date convertStringToDate(String pDateString, String pFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pFormat);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(pDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        if(diff < 0) {
            diff = 1;
        }
        return diff;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String getUtcDateFormat(Date pDate, String pDateTxt, String pInPut, String pOutPut) {
        String lFormatedDate = null;
        SimpleDateFormat lObjFormat = new SimpleDateFormat(pOutPut);
        lObjFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date lObjDate = null;
        try {
            if(null != pDateTxt) {
                SimpleDateFormat lObjFormat1 = new SimpleDateFormat(pInPut);
                lObjDate = lObjFormat1.parse(pDateTxt);
            } else if(null != pDate){
                lObjDate = pDate;
            }
            if(null != lObjDate) {
                lFormatedDate = lObjFormat.format(lObjDate);
            }
        } catch(Exception ex) {
            lFormatedDate = "";
        }
        return lFormatedDate;
    }

    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static void saveLoginAuth(Context pContext, String pValue){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_AUTH_ID", pValue);
        editor.commit();
    }

    public static void setOTPGen(Context pContext, boolean pState){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("OTP_GEN", pState);
        editor.commit();
    }

    public static void setOTPPhoneNo(Context pContext, String pOtpPhone){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("OTP_PHONE", pOtpPhone);
        editor.commit();
    }

    public static void saveStartDay(Context pContext, String pDate, String pTime){
        SharedPreferences prefs = pContext.getSharedPreferences("START_DAY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("START_DAY_DATE", pDate);
        editor.putString("START_DAY_TIME", pTime);
        editor.commit();
    }
    public static void saveUserName(Context pContext, String pValue){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_USERID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("USER_NAME", pValue);
        editor.commit();
    }

    public static void saveGCMRegId(Context pContext, String pValue) {
        SharedPreferences lSharedPref = pContext.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = lSharedPref.edit();
        editor.putString("GCM_REG_ID", pValue);
        editor.commit();
    }

    public static void savePaymentCount(Context pContext, int pValue){
        SharedPreferences prefs = pContext.getSharedPreferences("PAYMENT_FAILURE_COUNT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("FAILURE_COUNT", pValue);
        editor.commit();
    }

    public static void saveLoanUuid(Context pContext, String pValue){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_UUID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_UUID_ID", pValue);
        editor.commit();
    }

    public static String getLoanUuidId(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_UUID", Context.MODE_PRIVATE);
        return prefs.getString("LOGIN_UUID_ID", null);
    }

    public static String getGCMRegId(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        return prefs.getString("GCM_REG_ID", null);
    }

    public static int getPaymentCount(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("PAYMENT_FAILURE_COUNT", Context.MODE_PRIVATE);
        return prefs.getInt("FAILURE_COUNT", 0);
    }

    public static String getUserName(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_USERID", Context.MODE_PRIVATE);
        return prefs.getString("USER_NAME", null);
    }

    public static String getLoginAuth(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        return prefs.getString("LOGIN_AUTH_ID", null);
    }

    public static boolean getOTPGen(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        return prefs.getBoolean("OTP_GEN", false);
    }

    public static String getOTPPhoneNo(Context pContext){
        SharedPreferences prefs = pContext.getSharedPreferences("LOGIN_AUTH", Context.MODE_PRIVATE);
        return prefs.getString("OTP_PHONE", null);
    }

    public static ArrayList<String> getStartDay(Context pContext){
        ArrayList<String> lArry = new ArrayList<>();
        SharedPreferences prefs = pContext.getSharedPreferences("START_DAY", Context.MODE_PRIVATE);
        lArry.add(prefs.getString("START_DAY_DATE", null));
        lArry.add(prefs.getString("START_DAY_TIME", null));
        return lArry;
    }

    public static boolean removeLoginAuth(Context pContext) {
        SharedPreferences preferences = pContext.getSharedPreferences("LOGIN_AUTH", 0);
        preferences.edit().remove("LOGIN_AUTH_ID").commit();
        preferences.edit().remove("OTP_GEN").commit();
        return preferences.edit().remove("OTP_PHONE").commit();
    }

    public  static final String EMAIL_PATTERNS1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    public  static final String EMAIL_PATTERNS2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isEmailValid(String pEmail) {

        boolean lRetVal = false;
        if (pEmail.matches(EMAIL_PATTERNS1) || pEmail.matches(EMAIL_PATTERNS2)) {
            lRetVal = true;
        } else {
            lRetVal = false;
        }
        return lRetVal;
    }

    public static String getGUID() {
        return UUID.randomUUID().toString();
    }

    public static JSONObject parseJson(String pString) {
        JSONObject lRetVal = null;
        try {
            lRetVal = (JSONObject) new JSONTokener(pString).nextValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lRetVal;
    }

    public static Bitmap cropImage(Bitmap pBitMap) {
        pBitMap.setDensity(Bitmap.DENSITY_NONE);
        if (pBitMap.getWidth() >= pBitMap.getHeight()){
            pBitMap = Bitmap.createBitmap(
                    pBitMap,
                    pBitMap.getWidth()/2 - pBitMap.getHeight()/2,
                    0,
                    pBitMap.getHeight(),
                    pBitMap.getHeight()
            );
        } else {
            pBitMap = Bitmap.createBitmap(
                    pBitMap,
                    0,
                    pBitMap.getHeight() / 2 - pBitMap.getWidth() / 2,
                    pBitMap.getWidth(),
                    pBitMap.getWidth()
            );
        }

        return pBitMap;
    }

    public static File getImageFilePath(Context pObjScreen) {
        File lObjMediaStorageDir = new File(getRootMediaFilePath(pObjScreen), "Images");
        if(!lObjMediaStorageDir.exists()) {
            lObjMediaStorageDir.mkdirs();
        }
        return lObjMediaStorageDir;
    }

    public static File getPdfFilePath(Context pObjScreen) {
        File lObjMediaStorageDir = new File(getRootMediaFilePath(pObjScreen), "Pdfs");
        if(!lObjMediaStorageDir.exists()) {
            lObjMediaStorageDir.mkdirs();
        }
        return lObjMediaStorageDir;
    }

    public static Bitmap scaleImage(Bitmap pBitMap, boolean pIsRoatate) {
        if(pIsRoatate) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            pBitMap = Bitmap.createBitmap(pBitMap, 0, 0, pBitMap.getWidth(), pBitMap.getHeight(), matrix, true);
        }
        int newWidth = 800;
        int newHeight = 1000;
        pBitMap = ScalingUtilities.createScaledBitmap(pBitMap, newWidth, newHeight, ScalingUtilities.ScalingLogic.CROP);
        return pBitMap;
    }

    public static Bitmap scaleImage(Context pObjContext, Bitmap pBitMap) {
        Bitmap bitmap = pBitMap;
        // Get current dimensions AND the desired bounding box
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        DisplayMetrics metrics = pObjContext.getResources().getDisplayMetrics();
        int displayWidth = metrics.widthPixels;
        int displayHeight = metrics.heightPixels;

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float scale = 1;

        if(scale == 0) //(float)displayWidth >= width && (float)displayHeight >= height)
        {// No scaling
            scale = 1;
        }
        else
        {
            float xScale = ((float) displayWidth) / width;
            float yScale = ((float) displayHeight) / height;
            scale = (xScale <= yScale) ? xScale : yScale;
        }
        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // Create a new bitmap and convert it to a format understood by the ImageView
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        return bitmap;

    }

    public static File getRootMediaFilePath(Context pObjScreen) {
        File lObjMediaStorageDir = pObjScreen.getExternalFilesDir(null);
        if(null == lObjMediaStorageDir){
            lObjMediaStorageDir = pObjScreen.getFilesDir();
        }
        lObjMediaStorageDir = new File(lObjMediaStorageDir, "DSA");
        if(!lObjMediaStorageDir.exists()) {
            lObjMediaStorageDir.mkdirs();
        }
        return lObjMediaStorageDir;
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean copyFile(String from, String to) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                File source = new File(from);
                File destination= new File(to);
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

    public static void saveFile(Context context, Bitmap b, String picName){
        FileOutputStream fos;
        Bitmap lBitmap = loadBitmap(context, picName);
        try {
            if(lBitmap == null) {
                fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
                b.compress(Bitmap.CompressFormat.PNG, 60, fos);
                fos.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.d(DSAMacros.class.getName(), "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(DSAMacros.class.getName(), "io exception");
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmap(Context context, String picName){
        Bitmap b = null;
        FileInputStream fis;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;
            fis = context.openFileInput(picName);
            b = BitmapFactory.decodeStream(fis, null, options);
            fis.close();
        }
        catch (FileNotFoundException e) {
            Log.d(DSAMacros.class.getName(), "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(DSAMacros.class.getName(), "io exception");
            e.printStackTrace();
        }
        return b;
    }

    // Add an event to the calendar of the user.
    public static Long addEvent(Context context, String date, String pTitle) {
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date lDate = new Date();
        Long lEventId = 0L;
        try {
            lDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calDate = Calendar.getInstance();
        calDate.set(Calendar.DATE, lDate.getDate());
//        Calendar tmp = (Calendar) now.clone();
//        Calendar calDate = now;

        try {
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 15 * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, pTitle);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance()
                    .getTimeZone().getID());
            System.out.println(Calendar.getInstance().getTimeZone().getID());
            /*Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            // Save the eventId into the Task object for possible future delete.
            lEventId = Long.parseLong(uri.getLastPathSegment());
//            Long lEventId = pEvnetId;
            // Add a 5 minute, 1 hour and 1 day reminders (3 reminders)
            setReminder(context, cr, lEventId, 5);
//            setReminder(context, cr, lEventId, 7);
//            setReminder(context, cr, lEventId, 10);
            setReminder(context, cr, lEventId, 60);
            setReminder(context, cr, lEventId, 1440);*/

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Reminder not able to add", Toast.LENGTH_SHORT).show();
        }
        return lEventId;
    }

    // routine to add reminders with the event
    private static void setReminder(Context context, ContentResolver cr, long eventID, int timeBefore) {
        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
//            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            Cursor c = CalendarContract.Reminders.query(cr, eventID,
                    new String[]{CalendarContract.Reminders.MINUTES});
            if (c.moveToFirst()) {
                System.out.println("calendar"
                        + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Reminder not able to set", Toast.LENGTH_SHORT).show();
        }
    }

    // function to remove an event from the calendar using the eventId stored within the Task object.
    public static void removeEvent(Context context, Long lEventId) {
        try {
            ContentResolver cr = context.getContentResolver();
            int iNumRowsDeleted = 0;
            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            Uri eventUri = ContentUris.withAppendedId(eventsUri, lEventId);
            iNumRowsDeleted = cr.delete(eventUri, null, null);
            Log.i("REMOVE EVENT", "Deleted " + iNumRowsDeleted + " calendar entry.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int updateEvent(Context context, String date, String title, Long eventId) {
        int iNumRowsUpdated = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            Date lDate = new Date();
            try {
                lDate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar calDate = new GregorianCalendar(lDate.getYear(), lDate.getMonth(), lDate.getDay(),
                    lDate.getHours(), lDate.getMinutes());

            ContentValues event = new ContentValues();

            event.put(CalendarContract.Events.TITLE, title);
            event.put("hasAlarm", 1); // 0 for false, 1 for true
            event.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            event.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 15 * 60 * 1000);

            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            Uri eventUri = ContentUris.withAppendedId(eventsUri, eventId);

            iNumRowsUpdated = context.getContentResolver().update(eventUri, event, null, null);

            Log.i("UPDATE EVENT", "Updated " + iNumRowsUpdated + " calendar entry.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iNumRowsUpdated;
    }

}
