package com.saraj.trialbyfire;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter implements DataLoadedCallback {
    private final LayoutInflater layoutInflater;
    private final DataProvider dataProvider;

    public MyAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        dataProvider = DataProviderFactory.getDataProviderInstance(this);
    }

    public void loadData(int pageNumber) {
        dataProvider.loadPage(pageNumber);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyVH(layoutInflater.inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MyVH) viewHolder).bind(dataProvider.getData(i));
        if (i % DataProvider.PAGE_SIZE > 0.75 * DataProvider.PAGE_SIZE) {
            dataProvider.loadPage(getItemCount() / DataProvider.PAGE_SIZE);
        }
    }

    @Override
    public int getItemCount() {
        return dataProvider.getSize();
    }

    @Override
    public void onDataLoaded(int pageNumber) {
//        notifyItemRangeInserted(pageNumber * DataProvider.PAGE_SIZE, DataProvider.PAGE_SIZE);
        notifyDataSetChanged();
    }

    @Override
    public void onDataLoadFailed(int pageNumber) {

    }

    @Override
    public void onDataLoadCancelled() {

    }

    public static final class MyVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_list_item)
        TextView textView;

        MyVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ListData data) {
            textView.setText(data.getTitle());
        }
    }
}
