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
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.UserCircularImageView;
import com.whatsloan.dsa.interfaces.RecyclerTasksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.TasksData;
import com.whatsloan.dsa.network.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 11/5/16.
 */
public class CustomRecyclerAdapterForListOfCustomersTasks extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 2;
    private final int VIEW_PROG = 1;
    private final int CUSTOMER_INFO = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerTasksListener m_cClickListener;
    private static ArrayList<TasksData> m_cObjJsonTasksData;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfCustomersTasks(Context pContext, ArrayList<TasksData> pObjJsonTasksData, RecyclerTasksListener pListener) {
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
        if (TasksData.CUSTOMER_INFO.equals(m_cObjJsonTasksData.get(position).getType())) {
            return CUSTOMER_INFO;
        } else if (TasksData.VIEW_PROG.equals(m_cObjJsonTasksData.get(position).getType())) {
            return VIEW_PROG;
        } else {
            return VIEW_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View lView;
        // paging logic
        switch (viewType) {
            case CUSTOMER_INFO:
                lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_pro_det_top, parent, false);
                CustomerInfoViewHolder customerInfoViewHolder = new CustomerInfoViewHolder(lView);
                vh = customerInfoViewHolder;
                break;
            case VIEW_PROG:
                lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
                ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
                vh = lprogressViewHolder;
                break;
            case VIEW_ITEM:
                lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_cell, parent, false);
                DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
                vh = ldataObjectHolder;
                break;
            default:
                lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_cell, parent, false);
                DataObjectHolder ldataObjectHolder1 = new DataObjectHolder(lView);
                vh = ldataObjectHolder1;
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case CUSTOMER_INFO:
                CustomerInfoViewHolder customerInfoViewHolder = (CustomerInfoViewHolder) holder;
                TasksData taskData = m_cObjJsonTasksData.get(position);
                CustomersData m_cObjCustomer = taskData.getCustomersData();

                customerInfoViewHolder.CUST_NAME.setText(m_cObjCustomer.getFirstName() + " " + m_cObjCustomer.getLastName());
                customerInfoViewHolder.LOCATION.setText(m_cObjCustomer.getAddresses().getData().getCity().getData().getName());
                customerInfoViewHolder.editCustomerImg.setTag(R.id.PHONE_NO_CUST_TASK, m_cObjCustomer.getPhone());
                customerInfoViewHolder.editCustomerImg.setImageResource(R.drawable.call);

                if (null != m_cObjCustomer) {
                    try {
                        for (AttachmentsData lAtachData : m_cObjCustomer.getAttachments().getData()) {
                            if (lAtachData.getType().equalsIgnoreCase(Constants.PROFILE_PICTURE)) {
                                Picasso.with(m_cObjContext)
                                        .load(lAtachData.getUri())
                                        .error(R.drawable.profile_placeholder)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .fit()
                                        .into(customerInfoViewHolder.PRO_PIC);
                            }
                        }
                    } catch (Exception e) {
                        customerInfoViewHolder.PRO_PIC.setImageResource(R.drawable.profile_placeholder);
                        e.printStackTrace();
                    }

                }

                break;
            case VIEW_PROG:
                ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
                progressViewHolder.progressBar.setIndeterminate(true);
                break;
            case VIEW_ITEM:
                displayData(holder, position);
                break;
            default:
                displayData(holder, position);
                break;

        }


//        if (holder instanceof DataObjectHolder) {

//            try {
//                ((DataObjectHolder) holder).startDate.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getFrom().getDate(),
//                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).endDate.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getTo().getDate(),
//                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).startTime.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getFrom().getDate(),
//                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).endTime.setText(DSAMacros.getDateFormat(null, m_cObjJsonTasksData.get(position).getTo().getDate(),
//                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).priorityTxt.setText(m_cObjJsonTasksData.get(position).getPriority());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).stageTxt.setText(m_cObjJsonTasksData.get(position).getStage().getData().getLabel());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).taskDesc.setText(m_cObjJsonTasksData.get(position).getDescription());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).statusTxt.setText(m_cObjJsonTasksData.get(position).getStatus().getData().getLabel());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                ((DataObjectHolder) holder).taskName.setText(m_cObjJsonTasksData.get(position).getDescription());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//        } else {
//
//        }
    }

    private void displayData(RecyclerView.ViewHolder holder, int position) {
        DataObjectHolder dataObjectHolder = (DataObjectHolder) holder;
        TasksData taskData = m_cObjJsonTasksData.get(position);

        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        if(lDAuth.getRole().equalsIgnoreCase(DSAMacros.DSA_OWNER))
            dataObjectHolder.editTaskImg.setVisibility(View.VISIBLE);

        String startDate = DSAMacros.getDateFormat(null, taskData.getFrom().getDate(),
                DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY);
        String endDate = DSAMacros.getDateFormat(null, taskData.getTo().getDate(),
                DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_DDMMYYYY);
        String startTime = DSAMacros.getDateFormat(null, taskData.getFrom().getDate(),
                DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM);
        String endTime = DSAMacros.getDateFormat(null, taskData.getTo().getDate(),
                DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.TIME_FORMAT_HHMM_AM_PM);
        String priorityText = taskData.getPriority();
        String statusLabel = taskData.getStatus().getData().getLabel();
        String stageLabel = taskData.getStage().getData().getLabel();
        String description = taskData.getDescription();

        dataObjectHolder.startDate.setText(startDate);
        dataObjectHolder.endDate.setText(endDate);
        dataObjectHolder.startTime.setText(startTime);
        dataObjectHolder.endTime.setText(endTime);
        dataObjectHolder.priorityTxt.setText(priorityText);
        dataObjectHolder.stageTxt.setText(stageLabel);
        dataObjectHolder.taskDesc.setText(description);
        dataObjectHolder.statusTxt.setText(statusLabel);
        dataObjectHolder.taskName.setText(description);
    }

    public static class CustomerInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.PRO_PIC)
        UserCircularImageView PRO_PIC;

        @Nullable
        @Bind(R.id.CUST_NAME)
        TextView CUST_NAME;

        @Nullable
        @Bind(R.id.LOCATION)
        TextView LOCATION;

        @Nullable
        @Bind(R.id.EDIT_CUSTOMER_IMG)
        ImageView editCustomerImg;

        public CustomerInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.EDIT_CUSTOMER_IMG)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.EDIT_CUSTOMER_IMG:
                    if (null != (String) editCustomerImg.getTag(R.id.PHONE_NO_CUST_TASK)) {
                        m_cClickListener.onCallClick(getPosition(), m_cObjJsonTasksData.get(getPosition()), v, (String) editCustomerImg.getTag(R.id.PHONE_NO_CUST_TASK));
                    }
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

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            switch (v.getId()) {
                case R.id.CHAT_TASK_IMG:
                    m_cClickListener.onChatClick(getPosition(), m_cObjJsonTasksData.get(getPosition()), v);
                    break;
                case R.id.EDIT_TASK_IMG:
                    m_cClickListener.onEditClick(getPosition(), m_cObjJsonTasksData.get(getPosition()), v);
                    break;
            }
        }
    }
}
