package com.whatsloan.dsa.uiactivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 20/3/16.
 */
public class DSAStartDay extends DSABaseActivity {

    public static final int UPDATE_ARC = 1001;
    public static final int SHOW_START_COLOR = 1011;
    public static final int UPDATE_TIME_MILIS = 1111;
    private static final float START_INC = 270.0f;
    public static double m_TotalDegree = 360;
    private ColorArcView m_cCustomArcView;

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelLayGrp;

    @Nullable
    @Bind(R.id.START_DAY)
    ImageView m_cStartDay;

    @Nullable
    @Bind(R.id.ARC_LAYOUT)
    FrameLayout m_cObjArcMeterLay;

    @Nullable
    @Bind(R.id.FULL_NAME)
    TextView m_cFullName;

    @Nullable
    @Bind(R.id.DAY_DATE_STAMP)
    TextView m_cDayDateStamp;

    @Nullable
    @Bind(R.id.TIME_STAMP)
    TextView m_cTimeStamp;

    private int m_cCalculated_Angle = 0;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.startday_screen);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        m_cFullName.setText(lDAuth.getFirstName() + " " + lDAuth.getLastName());
        Calendar c = Calendar.getInstance();
        m_cDayDateStamp.setText(DSAMacros.getDateFormat(new Date(), null, null, DSAMacros.DISPLAY_DATE_TIME_FORMAT_DAY));
        m_cTimeStamp.setText(DSAMacros.getDateFormat(new Date(), null, null, DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
        m_cObjUIHandler.sendEmptyMessageDelayed(UPDATE_TIME_MILIS, 500);
        m_cObjUIHandler.sendEmptyMessageDelayed(UPDATE_ARC, 10);
        m_cObjUIHandler.sendEmptyMessageDelayed(SHOW_START_COLOR, 3000);
    }

    @OnClick(R.id.START_DAY)
    public void onClick(View v) {
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.START_DAY:
                RequestManager.getInstance(this).placeRequest(Constants.STARTDAY, Response.class, this, null, true);
                break;
        }
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        Intent lObjIntent;
        switch (pObjMessage.what) {
            case UPDATE_ARC:
                m_cCustomArcView = new ColorArcView(this, m_cCalculated_Angle += 3);
                m_cObjArcMeterLay.addView(m_cCustomArcView);
                m_cObjUIHandler.sendEmptyMessageDelayed(UPDATE_ARC, 20);
                break;
            case SHOW_START_COLOR:
                m_cStartDay.setVisibility(View.VISIBLE);
                break;
            case UPDATE_TIME_MILIS:
                m_cTimeStamp.setText(DSAMacros.getDateFormat(new Date(), null, null, DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
                m_cObjUIHandler.sendEmptyMessageDelayed(UPDATE_TIME_MILIS, 500);
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        Intent lObjIntent;
        switch (apiMethod){
            case Constants.STARTDAY:
                Response lResponse = (Response) response;
                DSAMacros.saveStartDay(this, m_cDayDateStamp.getText().toString(), m_cTimeStamp.getText().toString());
                lObjIntent = new Intent(this, DSALandingScreen.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                finish();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        switch (apiMethod){
            case Constants.STARTDAY:
                if(error.networkResponse.statusCode == 401){
                    logout();
                    displaySnack(m_cRelLayGrp, "Session Expired");
                }
                break;
        }
    }

    public class ColorArcView extends View {
        float lEndAngle = 0;
        int lColor = 0;

        public ColorArcView(Context context, float pEndAngle) {
            super(context);
            lEndAngle = pEndAngle;
            lColor = R.color.buttonBg;
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);
            int x = m_cObjArcMeterLay.getWidth();
            int y = m_cObjArcMeterLay.getHeight();
            int radius;
            radius = (x + y) / 4;
            //Defining the Stroke and it Width
            final Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(20.5f);
            final RectF oval = new RectF();
            float center_x, center_y;
            center_x = x / 2;
            center_y = y / 2;
            oval.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
            paint.setColor(getResources().getColor(lColor));
            canvas.drawArc(oval, START_INC, lEndAngle, true, paint);
        }
    }
}
