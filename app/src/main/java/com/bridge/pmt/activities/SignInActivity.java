package com.bridge.pmt.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bridge.pmt.MainActivity;
import com.bridge.pmt.R;
import com.bridge.pmt.api.APIService;
import com.bridge.pmt.api.APIUrl;
import com.bridge.pmt.helpers.SharedPrefManager;
import com.bridge.pmt.helpers.ValidationManager;
import com.bridge.pmt.models.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setElevation(0);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        buttonSignIn.setOnClickListener(this);
    }

    private void userSignIn() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if(ValidationManager.isValidEmail(email)){

    if(ValidationManager.isValidPassword(password)){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        Call<BaseResponse> call = service.userLogin(email, password);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();

//                Log.v("SERVER-RESPONSE", String.valueOf(response.body().getToken() ));

             Log.e("SERVER-RESPONSE-data", String.valueOf((response.body().getStatus() )));
//                Toast.makeText(getApplicationContext(),response.body().getToken()  , Toast.LENGTH_LONG).show();

                if ( String.valueOf(response.body().getStatus() ).equals("1")) {


                SharedPrefManager.getInstance(getApplicationContext()).userDetails(response.body().getData().getUser());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                  finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                }
            }



            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }
else{
        Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_LONG).show();

    }

}

    else{
            Toast.makeText(getApplicationContext(), "Enter a valid Email", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userSignIn();
        }
    }
}
