package com.whatsloan.dsa.uiactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.WindowManager;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.Date;

/**
 * Created by S.K. Pissay on 28/3/16.
 */
public class DSASplashScreen extends DSABaseActivity{

    public static final int START_APPLICATION = 1000;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        m_cObjUIHandler.sendEmptyMessageDelayed(START_APPLICATION, 2000);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        Intent lObjIntent;
        switch (pObjMessage.what) {
            case START_APPLICATION:
                //CHECKING AUTH
                if (null != DSAMacros.getLoginAuth(this)) {
                    String l = DSAMacros.getDateFormat(new Date(), null, null, DSAMacros.DISPLAY_DATE_TIME_FORMAT_DAY);
                    String r = DSAMacros.getStartDay(this).get(0);
                    if(l.equalsIgnoreCase(r)){
                        lObjIntent = new Intent(this, DSALandingScreen.class);
                    }else {
                        lObjIntent = new Intent(this, DSAStartDay.class);
                    }
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                } else {
                    lObjIntent = new Intent(this, DSALogin_OTP.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                break;
        }
    }
}
