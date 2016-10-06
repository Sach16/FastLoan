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
import com.whatsloan.dsa.model.BanksData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 15/4/16.
 */
public class CustomRecyclerAdapterForListOfApprovingBanks extends RecyclerView.Adapter{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerBanksListener m_cClickListener;
    private static ArrayList<BanksData> m_cObjBank;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForListOfApprovingBanks(Context pContext, ArrayList<BanksData> pObjBank, RecyclerBanksListener pListener){
        this.m_cClickListener = pListener;
        this.m_cObjBank = pObjBank;
        this.m_cObjContext = pContext;
    }

    @Override
    public int getItemCount() {
        return m_cObjBank.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjBank.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Nullable
        @Bind(R.id.BANK_IMG)
        ImageView bankImg;

        @Nullable
        @Bind(R.id.BANK_TXT)
        TextView bankName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.APPROVING_BANKS_CELL_VIEW)
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.APPROVING_BANKS_CELL_VIEW:
                    m_cClickListener.onBankClicked(getPosition(), null, null, v);
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
            lView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.approving_banks_cell, parent, false);
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

        if (holder instanceof DataObjectHolder) {

            try {
                Picasso.with(m_cObjContext)
                        .load(m_cObjBank.get(position).getAttachments().getData().get(0).getUri())
                        .error(R.drawable.profile_placeholder)
                        .placeholder(R.drawable.profile_placeholder)
                        .transform(new RoundedCornersTransformation(10, 0))
                        .into(((DataObjectHolder) holder).bankImg);
            } catch (Exception e) {
                ((DataObjectHolder) holder).bankImg.setBackgroundResource(R.drawable.profile_placeholder);
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).bankName.setText(m_cObjBank.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

}