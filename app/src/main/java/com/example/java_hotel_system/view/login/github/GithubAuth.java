package com.example.java_hotel_system.view.login.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.login.facebook.FacebookAuth;
import com.example.java_hotel_system.view_model.LoginViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GithubAuth extends AppCompatActivity {
    EditText etEmail;
    Button btnLoginGithub;
    FirebaseAuth mAuth;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_auth);

        etEmail = findViewById(R.id.etEmail);
        btnLoginGithub = findViewById(R.id.btnLoginGithub);

        mAuth = FirebaseAuth.getInstance();

        btnLoginGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
                } else {
                    //
                    OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                    // Target specific email with login hint.
                    provider.addCustomParameter("login", email);

                    // Request read access to a user's email addresses.
                    // This must be preconfigured in the app's API permissions.
                    List<String> scopes =
                            new ArrayList<String>() {
                                {
                                    add("user:email");
                                }
                            };
                    provider.setScopes(scopes);

                    Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                    if (pendingResultTask != null) {
                        // There's something already here! Finish the sign-in for your user.
                        pendingResultTask
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                // User is signed in.
                                                // IdP data available in
                                                // authResult.getAdditionalUserInfo().getProfile().
                                                // The OAuth access token can also be retrieved:
                                                // authResult.getCredential().getAccessToken().
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(GithubAuth.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                    } else {
                        mAuth
                                .startActivityForSignInWithProvider(/* activity= */ GithubAuth.this, provider.build())
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                openNextActivity();
                                                if (mAuth.getCurrentUser() != null) {
                                                    PostUserRequest a = new PostUserRequest(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getPhotoUrl().toString(), "GITHUB");
                                                    postUserByLoginFromApiCall(a);
                                                }
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure.
                                                Toast.makeText(GithubAuth.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                    }
                }
            }
        });
    }

    private void openNextActivity() {
        Intent i = new Intent(GithubAuth.this, BottomNavigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void postUserByLoginFromApiCall(PostUserRequest postUserRequest) {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.postUserByLoginObservable().observe(GithubAuth.this, new Observer<PostUserRequest>() {
            @Override
            public void onChanged(PostUserRequest recyclerData) {
                if (recyclerData == null) {
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
                }
            }
        });
        viewModel.postUserByLoginOfData(postUserRequest);
    }
}