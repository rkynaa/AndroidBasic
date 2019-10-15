package com.example.neardeal.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neardeal.R;
import com.example.neardeal.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by willshuffy on 06/11/17.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Cart> mCartList;
    private LayoutInflater mLayoutInflater;

    public CartItemAdapter(Context context, List<Cart> carts){
        mContext = context;
        mCartList = carts;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_cart, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cart cart = mCartList.get(position);

        holder.nameTextView.setText(cart.getProductName());
        holder.dealTextView.setText("Rp " + String.valueOf(cart.getPrice()));
        Picasso.with(mContext).load(cart.getPhoto()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView container;
        public ImageView imageView;
        public TextView nameTextView;
        public TextView dealTextView;

        public ViewHolder(View itemView){
            super(itemView);

            container = itemView.findViewById(R.id.container_ic);
            imageView = itemView.findViewById(R.id.Civ_image);
            nameTextView = itemView.findViewById(R.id.Ctv_title);
            dealTextView = itemView.findViewById(R.id.Ctv_desc);

        }
    }
}