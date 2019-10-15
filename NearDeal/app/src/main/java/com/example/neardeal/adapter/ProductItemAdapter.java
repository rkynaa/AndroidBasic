package com.example.neardeal.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neardeal.ApiResponse.Deal;
import com.example.neardeal.ApiResponse.Product;
import com.example.neardeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by willshuffy on 06/11/17.
 */

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Product> mProductList;
    private LayoutInflater mLayoutInflater;

    public ProductItemAdapter(Context context, List<Product> products){
        mContext = context;
        mProductList = products;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_product, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = mProductList.get(position);

        holder.nameTextView.setText(product.getName());
        holder.descTextView.setText(product.getDescription());
        holder.dealTextView.setText("Rp " + String.valueOf(product.getPrice()));
        Picasso.with(mContext).load(product.getPhoto()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView container;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView descTextView;
        public TextView dealTextView;

        public ViewHolder(View itemView){
            super(itemView);

            container = itemView.findViewById(R.id.container_ip);
            imageView = itemView.findViewById(R.id.Piv_image);
            nameTextView = itemView.findViewById(R.id.Ptv_title);
            descTextView = itemView.findViewById(R.id.Ptv_descript);
            dealTextView = itemView.findViewById(R.id.Ptv_desc);

        }
    }
}