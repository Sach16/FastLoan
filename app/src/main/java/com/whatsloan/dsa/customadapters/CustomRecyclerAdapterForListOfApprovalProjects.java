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

import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.RoundedCornersTransformation;
import com.whatsloan.dsa.interfaces.RecyclerBanksListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.ProjectStruct;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 19/4/16.
 */
public class CustomRecyclerAdapterForListOfApprovalProjects extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerBanksListener m_cClickListener;
    private static ArrayList<ProjectStruct> m_cObjProjectStruct;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfApprovalProjects(Context pContext, ArrayList<ProjectStruct> pObjProjectStructs, RecyclerBanksListener pListener) {
        this.m_cClickListener = pListener;
        this.m_cObjProjectStruct = pObjProjectStructs;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjProjectStruct.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjProjectStruct.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.CUST_IMG)
        ImageView custImg;

        @Nullable
        @Bind(R.id.APPROV_BUILDER_NAME)
        TextView approvBuilderName;

        @Nullable
        @Bind(R.id.PROJECT_NAME)
        TextView approvProjName;

        @Nullable
        @Bind(R.id.APPROV_LOCATION)
        TextView location;

        @Nullable
        @Bind(R.id.APPROV_CITY)
        TextView city;

        @Nullable
        @Bind(R.id.SUBMITTED_BANK)
        TextView bankName;

        @Nullable
        @Bind(R.id.SUBMITTED_BANK_DATE)
        TextView bankDate;

        @Nullable
        @Bind(R.id.SUBMITTED_BANK_BY)
        TextView submitedBy;


        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.APPROVED_PROJECT_CELL_VIEW)
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.APPROVED_PROJECT_CELL_VIEW:
                    m_cClickListener.onProjectClicked(getPosition(), m_cObjProjectStruct.get(getPosition()),
                            m_cObjProjectStruct.get(getPosition()).getProject(), v);
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
            lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.approval_projects_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            vh = ldataObjectHolder;
        } else {
            lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
            ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
            vh = lprogressViewHolder;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DataObjectHolder) {

            try {
                Picasso.with(m_cObjContext)
                        .load(m_cObjProjectStruct.get(position).getProject().getData().getAttachments().getData().get(0).getUri())
                        .error(R.drawable.profile_placeholder)
                        .placeholder(R.drawable.profile_placeholder)
                        .transform(new RoundedCornersTransformation(10, 0))
                        .fit()
                        .into(((DataObjectHolder) holder).custImg);
            } catch (Exception e) {
                ((DataObjectHolder) holder).custImg.setBackgroundResource(R.drawable.profile_placeholder);
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).approvBuilderName.setText(m_cObjProjectStruct.get(position).getProject().getData().getBuilderName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).approvProjName.setText(m_cObjProjectStruct.get(position).getProject().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String lbetaStr = "";
            try {
                if (m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getBetaStreet().equalsIgnoreCase("null") || m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getBetaStreet() == null) {
                    lbetaStr = m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getBetaStreet();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            try {
                if (lbetaStr.length() > 0)
                    ((DataObjectHolder) holder).location.setText(m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getAlphaStreet() + " "
                            + lbetaStr);
                else
                    ((DataObjectHolder) holder).location.setText(m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getAlphaStreet());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).city.setText(m_cObjProjectStruct.get(position).getProject().getData().getAddress().getData().getCity().getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO : change array to obj in proj.data.bank.get(0)

            try {
                ((DataObjectHolder) holder).bankName.setText(m_cObjProjectStruct.get(position).getProject().getData().getBanks().getData().get(0).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).bankDate.setText(DSAMacros.getDateFormat(null, m_cObjProjectStruct.get(position).getCreatedAt().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).submitedBy.setText(m_cObjProjectStruct.get(position).getAgent().getData().getFirstName()+
                " "+m_cObjProjectStruct.get(position).getAgent().getData().getLastName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}
