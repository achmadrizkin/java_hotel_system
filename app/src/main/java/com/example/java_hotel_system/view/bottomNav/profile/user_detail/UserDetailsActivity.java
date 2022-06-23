package com.example.java_hotel_system.view.bottomNav.profile.user_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.select_role.SelectRoleActivity;
import com.example.java_hotel_system.view.splash_screen.MainActivity;
import com.example.java_hotel_system.view_model.LoginFacebookViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserDetailsActivity extends AppCompatActivity {
    private LoginFacebookViewModel viewModel;
    private TextView tvName, tvLoginAs;
    private ImageView ivProfilePicture;
    private Button btnUpdateData;
    private Spinner spinner;
    private String valSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        String image_url = intent.getExtras().getString("image_url");
        String name = intent.getExtras().getString("name");
        String log_via = intent.getExtras().getString("log_via");
        String uid = intent.getExtras().getString("uid");
        String role = intent.getExtras().getString("role");

        tvName = findViewById(R.id.tvName);
        tvLoginAs = findViewById(R.id.tvLoginAs);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        spinner = findViewById(R.id.spinner);

        //
        viewModel = new ViewModelProvider(this).get(LoginFacebookViewModel.class);

        // SPINNER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvName.setText(name);
        tvLoginAs.setText("Login Via: " + log_via);
        Glide.with(this).load(image_url).placeholder(R.drawable.erorr_picture).into(ivProfilePicture);

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                UserRequest a = new UserRequest(name, uid, image_url, valSpinner, log_via);
                postUserByLoginFromView(a);
            }
        });
    }

    private void postUserByLoginFromView(UserRequest userRequest) {
        viewModel = new ViewModelProvider(this).get(LoginFacebookViewModel.class);
        viewModel.postUserByFacebookObservable().observe(UserDetailsActivity.this, new Observer<UserRequest>() {
            @Override
            public void onChanged(UserRequest userRequest) {
                if (userRequest == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Failed to create/update new user",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Success to create/update user",
                            Toast.LENGTH_LONG
                    ).show();

                    Intent p = new Intent(UserDetailsActivity.this, BottomNavigationActivity.class);
                    startActivity(p);
                    finish();
                }
            }
        });

        viewModel.postUserByFacebookOfData(userRequest);
    }

}