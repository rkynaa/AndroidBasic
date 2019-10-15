package com.example.neardeal;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neardeal.ApiResponse.ProductDetailResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.models.Cart;
import com.example.neardeal.utils.ConnectivityUtil;
import com.example.neardeal.utils.PopupUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    int newPrice;
    String productId;
    ProductDetailResponse productDetailResponse;
    @BindView(R.id.cv_image)
    CircleImageView storeImage;

    @BindView(R.id.tv_product_name)
    TextView storeNameTextView;

    @BindView(R.id.et_product_desc)
    EditText descriptionTextView;

    @BindView(R.id.tv_product_price_new)
    TextView priceTextView;

    @BindView(R.id.tv_product_price_old)
    TextView oldPriceTextView;

    @BindView(R.id.iv_product_image)
    ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        newPrice = getIntent().getIntExtra("new_price", newPrice);
        productId = getIntent().getStringExtra("product_id");

        if(ConnectivityUtil.isConnected(this)){
            System.out.println("Product load id: " + productId);
            loadDetail();
        }
        else{
            PopupUtil.showMsg(this, "No Internet connection", PopupUtil.SHORT);
        }
    }
    private void loadDetail(){
        PopupUtil.showLoading( this, "", "Loading stores....");

        ApiEndpoint apiEndPoint = ApiClient.getClient(this).create(ApiEndpoint.class);
        Call<ProductDetailResponse> call = apiEndPoint.getProductDetail(productId);

        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                PopupUtil.dismissDialog();
                productDetailResponse = response.body();
                if (productDetailResponse != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(ProductDetailActivity.this).load(productDetailResponse.getProductDetail().getPhoto()).into(storeImage);
                            Picasso.with(ProductDetailActivity.this).load(productDetailResponse.getProductDetail().getStorePhoto()).into(productImage);
                            storeNameTextView.setText(productDetailResponse.getProductDetail().getStoreName());
                            descriptionTextView.setText(productDetailResponse.getProductDetail().getDescription());
                            oldPriceTextView.setText("Rp "+productDetailResponse.getProductDetail().getPrice());
                            priceTextView.setText("Rp "+String.valueOf(newPrice));

                            oldPriceTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            setTitle(productDetailResponse.getProductDetail().getName());

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                PopupUtil.dismissDialog();

            }
        });

    }
    @OnClick({R.id.btn_buy})
    public void onClick(Button button) {
        Realm realm = Realm.getDefaultInstance();
        // get product detail
        final String name = productDetailResponse.getProductDetail().getName();
        final double price = productDetailResponse.getProductDetail().getPrice();
        final String photo = productDetailResponse.getProductDetail().getPhoto();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // find last id
                RealmResults<Cart> carts = realm.where(Cart.class)
                        .findAllSorted("id", Sort.DESCENDING);

                int lastId = 0;

                if(carts.size() > 0) {
                    lastId = carts.first().getId();
                }

                Cart cart = new Cart();
                cart.setId(lastId + 1);
                cart.setProductId(Integer.valueOf(productId));
                cart.setProductName(name);
                cart.setPrice(price);
                cart.setPhoto(photo);

                realm.copyToRealm(cart);

                PopupUtil.showMsg(ProductDetailActivity.this, "Berhasil ditambahkan ke keranjang belanja",
                        PopupUtil.SHORT);
            }
        });

        realm.close();
    }
}