package com.example.neardeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.neardeal.ApiResponse.CheckoutResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.models.Cart;
import com.example.neardeal.utils.ConnectivityUtil;
import com.example.neardeal.utils.PopupUtil;
import com.example.neardeal.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    @BindView(R.id.Cet_name)
    EditText nameEditText;

    @BindView(R.id.Cet_no_hp)
    EditText noHpEditText;

    @BindView(R.id.Cet_address)
    EditText addressEditText;

    @BindView(R.id.btn_submit)
    Button btn_submit;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        userId = sharedPrefManager.getUserToken();
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View button) {
        if (button.getId() == R.id.btn_submit){
            if(ConnectivityUtil.isConnected(this)) {
                submit();
            }
            else {
                PopupUtil.showMsg(this, "No internet connection", PopupUtil.SHORT);
            }
        }
    }

    private void submit() {
        String name = nameEditText.getText().toString();
        String noHp = noHpEditText.getText().toString();
        String address = addressEditText.getText().toString();
        List<String> productIdList = new ArrayList<>();
        List<String> priceList = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Cart> realmResults = realm.where(Cart.class).findAll();

        for(int i = 0; i < realmResults.size(); i++) {
            Cart cart = realmResults.get(i);
            productIdList.add(String.valueOf(cart.getProductId()));
            priceList.add(Double.toString(cart.getPrice()));
        }

        realm.close();
        Realm.deleteRealm(realm.getConfiguration());
        PopupUtil.showLoading( this, "", "Please wait....");

        String productIds = TextUtils.join(",", productIdList);
        String prices = TextUtils.join(",", priceList);

        ApiEndpoint apiEndPoint = ApiClient.getClient(this).create(ApiEndpoint.class);
        Call<CheckoutResponse> call = apiEndPoint.checkout(name, noHp, address, productIds, prices, userId);

        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                PopupUtil.dismissDialog();
                final CheckoutResponse checkoutResponse = response.body();

                if (checkoutResponse.getSuccess()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PopupUtil.showMsg(CheckoutActivity.this, "Checkout berhasil", PopupUtil.SHORT);
                            Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                PopupUtil.dismissDialog();

            }
        });
    }
}
