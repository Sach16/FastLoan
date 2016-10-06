package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.AttendanceData;
import com.whatsloan.dsa.model.Attendances;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
public interface RecyclerAttendanceListener {
    public void onInfoClick(int pPostion, Attendances pAttendances, AttendanceData pAttendanceData, View pView);
}
