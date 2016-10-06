package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.TasksData;

/**
 * Created by S.K. Pissay on 2/5/16.
 */
public interface RecyclerTasksListener {
    public void onChatClick(int pPostion, TasksData pTasksData, View pView);
    public void onEditClick(int pPostion, TasksData pTasksData, View pView);
    public void onCallClick(int pPostion, TasksData pTasksData, View pView, String pNo);
}