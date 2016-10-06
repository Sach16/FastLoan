package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerTasksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.TasksData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 2/5/16.
 */
public class CustomRecyclerAdapterForListOfTodaysTasks extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerTasksListener m_cClickListener;
    private static ArrayList<TasksData> m_cObjJsonTasksData;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfTodaysTasks(Context pContext, ArrayList<TasksData> pObjJsonTasksData, RecyclerTasksListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjJsonTasksData = pObjJsonTasksData;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonTasksData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonTasksData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.TASK_NAME)
        TextView taskName;

        @Nullable
        @Bind(R.id.TASK_DESC)
        TextView taskDesc;

        @Nullable
        @Bind(R.id.STATUS_TXT)
        TextView statusTxt;

        @Nullable
        @Bind(R.id.START_DATE_TXT)
        TextView startDate;

        @Nullable
        @Bind(R.id.END_DATE_TXT)
        TextView endDate;

        @Nullable
        @Bind(R.id.START_TIME_TXT)
        TextView startTime;

        @Nullable
        @Bind(R.id.END_TIME_TEXT)
        TextView endTime;

        @Nullable
        @Bind(R.id.PRIORITY_TXT)
        TextView priorityTxt;

        @Nullable
        @Bind(R.id.STAGE_TXT)
        TextView stageTxt;

        @Nullable
        @Bind(R.id.CHAT_TASK_IMG)
        ImageView chatTaskImg;

        @Nullable
        @Bind(R.id.EDIT_TASK_IMG)
        ImageView editTaskImg;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.CHAT_TASK_IMG, R.id.EDIT_TASK_IMG})
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.CHAT_TASK_IMG:
                    m_cClickListener.onChatClick(getPosition(), m_cObjJsonTasksData.get(getPosition()), v);
                    break;
                case R.id.EDIT_TASK_IMG:
                    m_cClickListener.onEditClick(getPosition(), m_cObjJsonTasksData.get(getPosition()), v);
                    break;
            }
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.progressBar1)
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View lView;
        // paging logic
        if (viewType == VIEW_ITEM) {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.todays_task_list_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            vh =  ldataObjectHolder;
        } else {
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
            ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
            vh =  lprogressViewHolder;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DataObjectHolder) {

            try {
                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();
                if(lDAuth.getRole().equalsIgnoreCase(DSAMacros.DSA_OWNER))
                    ((DataObjectHolder) holder).editTaskImg.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).taskDesc.setText(m_cObjJsonTasksData.get(position).getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).statusTxt.setText(m_cObjJsonTasksData.get(position).getStatus().getData().getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).startDate.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getFrom().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).endDate.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getTo().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).startTime.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getFrom().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).endTime.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getTo().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).priorityTxt.setText(m_cObjJsonTasksData.get(position).getPriority());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                ((DataObjectHolder) holder).stageTxt.setText(m_cObjJsonTasksData.get(position).getStage().getData().getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                ((DataObjectHolder) holder).taskName.setText(m_cObjJsonTasksData.get(position).getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}
