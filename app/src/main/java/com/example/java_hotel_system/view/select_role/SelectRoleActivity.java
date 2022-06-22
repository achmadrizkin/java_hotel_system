package com.example.java_hotel_system.view.select_role;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.login.facebook.FacebookAuth;
import com.example.java_hotel_system.view_model.LoginFacebookViewModel;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SelectRoleActivity extends AppCompatActivity {
    private LoginFacebookViewModel viewModel;
    private FirebaseAuth mAuth;
    private String valSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);

        //
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(LoginFacebookViewModel.class);

        Spinner spinnerRole = findViewById(R.id.spinnerRole);
        Button btnNext = findViewById(R.id.btnNext);

        //
        Intent intent = getIntent();
        String log_via = intent.getExtras().getString("log_via");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               valSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valSpinner.length() > 2) {
                    UserRequest a = new UserRequest(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getPhotoUrl().toString(), valSpinner, log_via);
                    postUserByLoginFromView(a);
                }
            }
        });
    }

    private void postUserByLoginFromView(UserRequest userRequest) {
        viewModel = new ViewModelProvider(this).get(LoginFacebookViewModel.class);
        viewModel.postUserByFacebookObservable().observe(SelectRoleActivity.this, new Observer<UserRequest>() {
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
                            "Success to create/update new user",
                            Toast.LENGTH_LONG
                    ).show();
                    startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                    finish();
                }
            }
        });

        viewModel.postUserByFacebookOfData(userRequest);
    }

}