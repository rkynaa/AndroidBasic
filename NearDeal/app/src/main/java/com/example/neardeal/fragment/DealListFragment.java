package com.example.neardeal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.neardeal.ApiResponse.Deal;
import com.example.neardeal.ApiResponse.DealResponse;
import com.example.neardeal.ApiResponse.Product;
import com.example.neardeal.ApiResponse.ProductResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.ProductDetailActivity;
import com.example.neardeal.R;
import com.example.neardeal.adapter.DealItemAdapter;
import com.example.neardeal.adapter.ProductItemAdapter;
import com.example.neardeal.utils.ConnectivityUtil;
import com.example.neardeal.utils.PopupUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealListFragment extends Fragment implements DealItemAdapter.OnItemClickListener{


    List<Deal> dealList;
    String mStoreId;
    DealItemAdapter dealItemAdapter;


    public DealListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dealList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_deal_list, container, false);
        mStoreId = getArguments().getString("store_id");

        System.out.println(" deal Fr Id : "+mStoreId);
        dealItemAdapter = new DealItemAdapter(getActivity(), dealList);
        dealItemAdapter.setListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_deal);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dealItemAdapter);
        // Inflate the layout for this fragment
        if(ConnectivityUtil.isConnected(getActivity())){
            loadProducts();
        }
        else{
            PopupUtil.showMsg(getActivity(), "No Internet connection", PopupUtil.SHORT);
        }
        return view;
    }

    private void loadProducts(){
        ApiEndpoint apiEndPoint = ApiClient.getClient(getActivity()).create(ApiEndpoint.class);
        Call<DealResponse> call = apiEndPoint.getDeal(mStoreId);
        call.enqueue(new Callback<DealResponse>() {
            @Override
            public void onResponse(Call<DealResponse> call, Response<DealResponse> response) {
                DealResponse dealResponse = response.body();

                if (dealResponse != null){
                    if (dealResponse.getSuccess()){
                        Log.d("StoreListFragment", "Jumlah store:" + dealResponse.getDeal().size());
                        dealList.addAll(dealResponse.getDeal());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dealItemAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<DealResponse> call, Throwable t) {
                PopupUtil.showMsg(getActivity(), "gagal!", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void onItemClick(String productId, int newPrice) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product_id", productId);
        intent.putExtra("new_price", newPrice);
        System.out.println("Product click id: "+ productId);
        startActivity(intent);
    }
}
