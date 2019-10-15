package com.example.neardeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.neardeal.adapter.CartItemAdapter;
import com.example.neardeal.models.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {
    @BindView(R.id.rv_cart)
    RecyclerView recyclerView;

    @BindView(R.id.btn_checkout)
    Button btn_checkout;

    private CartItemAdapter mAdapter;
    private List<Cart> mCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mCartList = new ArrayList<>();
        mAdapter = new CartItemAdapter(this, mCartList);
        recyclerView.setAdapter(mAdapter);

        loadCarts();
    }

    private void loadCarts() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Cart> realmResults = realm.where(Cart.class).findAll();

        for(int i = 0; i < realmResults.size(); i++) {
            Cart cart = realmResults.get(i);
            mCartList.add(realm.copyFromRealm(cart));
        }

        mAdapter.notifyDataSetChanged();
        realm.close();
    }

    @OnClick({R.id.btn_checkout})
    public void onClick(Button button){
        startActivity(new Intent(this, CheckoutActivity.class));
    }
}
