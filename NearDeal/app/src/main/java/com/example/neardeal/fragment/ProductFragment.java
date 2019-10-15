package com.example.neardeal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.neardeal.ApiResponse.Product;
import com.example.neardeal.ApiResponse.ProductResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.R;
import com.example.neardeal.adapter.ProductItemAdapter;
import com.example.neardeal.utils.ConnectivityUtil;
import com.example.neardeal.utils.PopupUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {


    List<Product> productList;
    String mStoreId;
    ProductItemAdapter productItemAdapter;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mStoreId = getArguments().getString("store_id");

        System.out.println(" Produc Fr Id : "+mStoreId);
        productItemAdapter = new ProductItemAdapter(getActivity(), productList);
        RecyclerView recyclerView = view.findViewById(R.id.rv_product);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productItemAdapter);
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
        Call<ProductResponse> call = apiEndPoint.getProduct(mStoreId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();

                if (productResponse != null){
                    if (productResponse.getSuccess()){
                        Log.d("StoreListFragment", "Jumlah store:" + productResponse.getProduct().size());
                        productList.addAll(productResponse.getProduct());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                productItemAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                PopupUtil.showMsg(getActivity(), "gagal!", Toast.LENGTH_LONG);
            }
        });

    }
}
