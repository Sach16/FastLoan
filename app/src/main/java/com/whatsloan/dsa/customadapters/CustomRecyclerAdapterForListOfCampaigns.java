package com.whatsloan.dsa.customadapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.interfaces.RecyclerCampaignListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CampaignsData;
import com.whatsloan.dsa.model.MembersData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 4/4/16.
 */
public class CustomRecyclerAdapterForListOfCampaigns extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerCampaignListener m_cClickListener;
    private static ArrayList<CampaignsData> m_cObjJsonCampaignData;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfCampaigns(Context pContext, ArrayList<CampaignsData> pObjJsonCampaignData, RecyclerCampaignListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjJsonCampaignData = pObjJsonCampaignData;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonCampaignData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonCampaignData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.CAMPAIGN_TEXT)
        TextView campaignName;

        @Nullable
        @Bind(R.id.CAMPAIGN_DESC)
        TextView campaignDesc;

        @Nullable
        @Bind(R.id.CAMPAIGN_TYPE)
        TextView campaignType;

        @Nullable
        @Bind(R.id.VENUE_TXT)
        TextView venueAdd;

        @Nullable
        @Bind(R.id.FROM_TXT)
        TextView fromText;

        @Nullable
        @Bind(R.id.TO_TXT)
        TextView toText;

        @Nullable
        @Bind(R.id.PROMO_TXT)
        TextView promoText;

        @Nullable
        @Bind(R.id.ORGANIZED_TXT)
        TextView orgBy;

        @Nullable
        @Bind(R.id.TEAM_MEMBERS)
        TextView teamMembers;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

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
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaigns_cell, parent, false);
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
                ((DataObjectHolder) holder).campaignName.setText(m_cObjJsonCampaignData.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).campaignDesc.setText(m_cObjJsonCampaignData.get(position).getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).campaignType.setText(DSAMacros.s2l(m_cObjJsonCampaignData.get(position).getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).venueAdd.setText(m_cObjJsonCampaignData.get(position).getAddresses().getData().get(position).getState());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).fromText.setText(DSAMacros.getDateFormat(null, m_cObjJsonCampaignData.get(position).getFrom().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_UNDERSC_DDMMYYYY_HHMMAA));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).toText.setText(DSAMacros.getDateFormat(null, m_cObjJsonCampaignData.get(position).getTo().getDate(),
                        DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS, DSAMacros.DATE_FORMAT_UNDERSC_DDMMYYYY_HHMMAA));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).promoText.setText(m_cObjJsonCampaignData.get(position).getPromotionals());
            } catch (Exception e) {
                e.printStackTrace();
            }

            StringBuffer lBuff = new StringBuffer();
            lBuff.append("");
            try {
                for (int i = 0 ; i < m_cObjJsonCampaignData.get(position).getCampaignMembers().getData().size(); i++) {
                    MembersData lMembersData = m_cObjJsonCampaignData.get(position).getCampaignMembers().getData().get(i);
                    if(i != 0){
                        lBuff.append(", " + lMembersData.getFirstName() + " " + lMembersData.getLastName());
                    }else {
                        lBuff.append(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).teamMembers.setText(lBuff.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).orgBy.setText(m_cObjJsonCampaignData.get(position).getOrganizer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}
