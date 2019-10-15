package com.example.neardeal.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neardeal.ApiResponse.Store;
import com.example.neardeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by willshuffy on 06/11/17.
 */

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Store> mStoreList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        public void onItemClick(String storeId);
    }

    public void setListener(OnItemClickListener listener){
        mListener = listener;
    }

    public StoreItemAdapter(Context context, List<Store> storeList){
        mContext = context;
        mStoreList = storeList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_store, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Store store = mStoreList.get(position);

        holder.nameTextView.setText(store.getName());
        holder.descTextView.setText(store.getDescription());
        holder.dealTextView.setText("Belum Ada Deal!");
        Picasso.with(mContext).load(store.getPhoto()).into(holder.imageView);

        if (mListener != null){
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(String.valueOf(store.getId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStoreList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView container;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView descTextView;
        public TextView dealTextView;

        public ViewHolder(View itemView){
            super(itemView);

            container = itemView.findViewById(R.id.container_is);
            imageView = itemView.findViewById(R.id.iv_image);
            nameTextView = itemView.findViewById(R.id.tv_title);
            descTextView = itemView.findViewById(R.id.tv_descript);
            dealTextView = itemView.findViewById(R.id.tv_desc);

        }
    }

}