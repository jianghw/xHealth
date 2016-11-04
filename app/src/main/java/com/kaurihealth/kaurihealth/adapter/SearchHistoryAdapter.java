package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private final List<String> list;
    private final Context context;
    private final ItemClickBack mItemClickBack;

    public SearchHistoryAdapter(Fragment context, List<String> dataContainer, ItemClickBack itemClickBack) {
        this.context = context.getContext();
        this.list = dataContainer;
        this.mItemClickBack = itemClickBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_search_history_iteam, null);
        return new ViewHolder(view, mItemClickBack);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIteam(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_history)
        TextView mTvHistory;

        private final ItemClickBack itemClickBack;

        public ViewHolder(View itemView, ItemClickBack itemClickBack) {
            super(itemView);
            this.itemClickBack = itemClickBack;
            ButterKnife.bind(this, itemView);
        }

        public void setIteam(String content) {
            mTvHistory.setText(content);
        }

        @OnClick(R.id.tv_history)
        public void onClick(View view) {
            itemClickBack.onItemClick(mTvHistory.getText().toString().trim());
        }
    }

    public interface ItemClickBack {
        void onItemClick(String doctorID);
    }
}
