package com.example.neardeal.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.neardeal.ApiResponse.Deal;
import com.example.neardeal.ApiResponse.Product;
import com.example.neardeal.R;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by willshuffy on 06/11/17.
 */

public class DealItemAdapter extends RecyclerView.Adapter<DealItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Deal> mDealList;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat mSimpleDateFormat;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        public void onItemClick(String productId, int newPrice);
    }

    public void setListener(DealItemAdapter.OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public DealItemAdapter(Context context, List<Deal> dealList){
        mContext = context;
        mDealList = dealList;
        mLayoutInflater = LayoutInflater.from(context);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_deal, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Deal deal = mDealList.get(position);

        holder.nameTextView.setText(deal.getProduct().getName());
        holder.priceOldTextView.setText("Rp "+String.valueOf(deal.getProduct().getPrice()));
        holder.priceOldTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        int discount = deal.getDiscount();
        int priceOld = deal.getProduct().getPrice();
        final int price = priceOld - (priceOld*discount/100);
        holder.priceTextView.setText(Integer.toString(price));

        Date today = new Date();

        try {
            Date startDate = mSimpleDateFormat.parse(deal.getStartDate());
            Date endDate = mSimpleDateFormat.parse(deal.getEndDate());

            //Log.d("DealItemAdapter", String.format("StartDate: %s, EndDate: %s",
            //mSimpleDateFormat.format(startDate), mSimpleDateFormat.format(endDate)));

            long delta1 = today.getTime() - startDate.getTime();
            long delta2 = endDate.getTime() - today.getTime();

            int delta1InDays = Days.daysBetween(new DateTime(today.getTime()), new DateTime(startDate.getTime())).getDays();
            int delta2InDays = Days.daysBetween(new DateTime(today.getTime()), new DateTime(endDate.getTime())).getDays();

            Log.d("DealItemAdapter", String.format("productId:%s delta1:%d delta2:%d",
                    deal.getProduct().getId(), delta1, delta2));

            if (delta1 < 0) {
                holder.dateTextView.setText(String.format("Diskon mulai %d hari lagi ", Math.abs(delta1InDays)));
            } else if (delta2 < 0) {
                holder.dateTextView.setVisibility(View.INVISIBLE);
            } else {
                if (delta2InDays == 0) {
                    holder.cardView.setVisibility(View.GONE);
//                    holder.dateTextView.setText(" Diskon berakhir hari ini");
                } else {
                    holder.dateTextView.setText(String.format("Diskon berakhir %d hari lagi ", Math.abs(delta2InDays)));
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.with(mContext).load(deal.getProduct().getPhoto()).into(holder.imageView);

        if (onItemClickListener != null){
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(String.valueOf(deal.getProduct().getId()), price);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDealList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView priceOldTextView;
        public TextView dateTextView;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.Div_image);
            nameTextView = itemView.findViewById(R.id.Dtv_title);
            priceTextView = itemView.findViewById(R.id.Dtv_disc);
            priceOldTextView = itemView.findViewById(R.id.Dtv_norm);
            dateTextView = itemView.findViewById(R.id.Div_button);

            cardView = itemView.findViewById(R.id.container_id);
        }
    }
}