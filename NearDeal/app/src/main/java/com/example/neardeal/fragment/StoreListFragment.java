package com.example.neardeal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neardeal.ApiResponse.Store;
import com.example.neardeal.ApiResponse.StoreResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.MapsActivity;
import com.example.neardeal.ProductActivity;
import com.example.neardeal.R;
import com.example.neardeal.adapter.StoreItemAdapter;
import com.example.neardeal.utils.ConnectivityUtil;
import com.example.neardeal.utils.PopupUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreListFragment extends Fragment implements StoreItemAdapter.OnItemClickListener{
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    private StoreItemAdapter mAdapter;
    private List<Store> mStoreList;
    double mLat = 0;
    double mLng = 0;


    public StoreListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_list2, container, false);//ambil parameter yg dikirimFloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("lat", mLat);
                intent.putExtra("lng", mLng);
                intent.putExtra("data", storeToString());
                startActivity(intent);
            }
        });
        Bundle argumennt = getArguments();
        if (argumennt != null){
            mLat = argumennt.getDouble(KEY_LAT);
            mLng = argumennt.getDouble(KEY_LNG);
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_store);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStoreList = new ArrayList<>();
        mAdapter = new StoreItemAdapter(getActivity(), mStoreList);
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);

        if(ConnectivityUtil.isConnected(getActivity())){
            loadStores(mLat, mLng);
        }
        else{
            PopupUtil.showMsg(getActivity(), "No Internet connection", PopupUtil.SHORT);
        }

        return view;
    }

    private String storeToString(){
        Gson gson = new Gson();
        Type listOfObject = new TypeToken<List<Store>>(){}.getType();
        String list = gson.toJson(mStoreList, listOfObject);
        return list;
    }

    private void loadStores(double lat, double lng) {
        PopupUtil.showLoading(getActivity(), "", "Loading stores....");

        ApiEndpoint ApiEndpoint = ApiClient.getClient(getActivity()).create(ApiEndpoint.class);
        Call<StoreResponse> call = ApiEndpoint.getStore();

        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                PopupUtil.dismissDialog();
                final StoreResponse storeResponse = response.body();

                if (storeResponse != null) {
                    if (storeResponse.getSuccess()) {
                        Log.d("StoreListFragment", "Jumlah store:" + storeResponse.getStore().size());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mStoreList.addAll(storeResponse.getStore());
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(getActivity(), "failed connection", PopupUtil.SHORT);
            }
        });
    }

    @Override
    public void onItemClick(String storeId) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("store_id", storeId);
        startActivity(intent);
    }
}
