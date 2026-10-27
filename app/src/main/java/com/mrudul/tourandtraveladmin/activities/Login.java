package com.mrudul.tourandtraveladmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.model.LoginRequest;
import com.mrudul.tourandtraveladmin.model.LoginResponse;
import com.mrudul.tourandtraveladmin.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText usernameInput,passwordInput;
    AppCompatButton loginBtn,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameInput = findViewById(R.id.adminLoginUsername);
        passwordInput = findViewById(R.id.adminLoginPassword);
        loginBtn = findViewById(R.id.adminLoginBtn);
        cancel = findViewById(R.id.adminCancelBtn);


        loginBtn.setOnClickListener(v->{

            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();


            if (username.isEmpty()){
                usernameInput.setError("enter the username");
                usernameInput.requestFocus();
                return;
            }

            if (password.isEmpty()){
                passwordInput.setError("enter the username");
                passwordInput.requestFocus();
                return;
            }

            login(username,password);

        });



        cancel.setOnClickListener(v->{
            finish();
        });
    }

    private void login(String username, String password) {

        loginBtn.setEnabled(false);

        LoginRequest request = new LoginRequest(username,password);

        Call<LoginResponse> call = RetrofitClient.getApiService().login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginBtn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null){
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.isSuccess()){
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginBtn.setEnabled(true);

                Log.e("LOGIN_ERROR", "Connection failed", t);

                Toast.makeText(Login.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}