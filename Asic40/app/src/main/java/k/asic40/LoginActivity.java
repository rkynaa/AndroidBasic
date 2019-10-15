package k.asic40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    Button btnLogin;

    String username="admin",password="1234";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linkToLayout();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameInput=etUsername.getText().toString();
                String passwordInput=etPassword.getText().toString();

                if (!usernameInput.equals(username)&&!passwordInput.equals(password)){
                        showToast("Username dan Password Salah");
                }
                else if (!usernameInput.equals(username)||!passwordInput.equals(password)){
                    showToast("Username atau Password Salah");
                }else{
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }


            }
        });

    }

    private void linkToLayout(){
        etUsername=findViewById(R.id.et_username);
        etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_login);
    }

    private void showToast(String pesan) {
        Toast.makeText(this, pesan, Toast.LENGTH_LONG).show();
    }
}