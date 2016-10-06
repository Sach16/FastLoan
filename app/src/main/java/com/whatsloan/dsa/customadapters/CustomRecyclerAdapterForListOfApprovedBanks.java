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
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 13/4/16.
 */
public class CustomRecyclerAdapterForListOfApprovedBanks extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerBanksListener m_cClickListener;
    private static ArrayList<ProjectStruct> m_cObjProjectStruct;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfApprovedBanks(Context pContext, ArrayList<ProjectStruct> pObjProjectStruct, RecyclerBanksListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjProjectStruct = pObjProjectStruct;
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

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.CUST_IMG)
        ImageView custImg;

        @Nullable
        @Bind(R.id.APPROV_PROJ_NAME)
        TextView approvProjName;

        @Nullable
        @Bind(R.id.TYPE_OF_LOAN)
        TextView typeOfLoan;

        @Nullable
        @Bind(R.id.LOAN_STATUS)
        TextView loanStatus;

        @Nullable
        @Bind(R.id.LOAN_UNIT_DETAILS)
        TextView loanUnitDet;

        @Nullable
        @Bind(R.id.POSSESSION_DATE)
        TextView posessionDate;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.APPROVED_PROJECT_CELL_VIEW)
        public void onClick(View v) {
            switch (v.getId()){
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
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_projects_cell, parent, false);
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
            Project lObjProject = m_cObjProjectStruct.get(position).getProject();

            try {
                Picasso.with(m_cObjContext)
                        .load(lObjProject.getData().getAttachments().getData().get(0).getUri())
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
                ((DataObjectHolder) holder).approvProjName.setText(lObjProject.getData().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).typeOfLoan.setText(lObjProject.getData().getBuilderName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).loanStatus.setText(DSAMacros.s2l(lObjProject.getData().getStatus().getData().getStatus()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).loanUnitDet.setText(""+lObjProject.getData().getUnitNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).posessionDate.setText(DSAMacros.getDateFormat(null, lObjProject.getData().getPosessionDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}