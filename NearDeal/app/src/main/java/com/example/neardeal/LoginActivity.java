package com.example.neardeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.neardeal.ApiResponse.LoginResponse;
import com.example.neardeal.ApiServices.ApiClient;
import com.example.neardeal.ApiServices.ApiEndpoint;
import com.example.neardeal.utils.PopupUtil;
import com.example.neardeal.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_loginUser)
    EditText et_loginUser;

    @BindView(R.id.et_loginPass)
    EditText et_loginPass;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getIsLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
    @OnClick({R.id.btn_login})
    public void onClick(Button button) {
        PopupUtil.showLoading( this, "", "Please wait....");
        String username = et_loginUser.getText().toString();
        String password = et_loginPass.getText().toString();

        ApiEndpoint apiEndPoint = ApiClient.getClient(this).create(ApiEndpoint.class);
        Call<LoginResponse> call = apiEndPoint.login(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                PopupUtil.dismissDialog();
                final LoginResponse loginResponse = response.body();
                sharedPrefManager.setIsLoggedIn(SharedPrefManager.IS_LOGGED_IN, true);
                sharedPrefManager.setUserToken(SharedPrefManager.USER_TOKEN, String.valueOf(loginResponse.getUser().getId()));
                if (loginResponse.getSuccess()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    PopupUtil.showMsg(LoginActivity.this, "User dan password salah", PopupUtil.SHORT);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                PopupUtil.dismissDialog();
                t.printStackTrace();
            }
        });
    }
}
