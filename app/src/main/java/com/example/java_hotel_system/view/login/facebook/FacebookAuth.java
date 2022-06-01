package com.example.java_hotel_system.view.login.facebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.example.java_hotel_system.view_model.LoginViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FacebookAuth extends LoginActivity {

    // FACEBOOK
    CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        //
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                            finish();

                            // POST IF USER FIRST TIME LOGIN
                            // IF USER ALREADY LOGIN, it just update
                            if (mAuth.getCurrentUser() != null) {
                                PostUserRequest a = new PostUserRequest(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getPhotoUrl().toString());
                                postUserByLoginFromApiCall(a);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void postUserByLoginFromApiCall(PostUserRequest postUserRequest) {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.postUserByLoginObservable().observe(FacebookAuth.this, new Observer<PostUserRequest>() {
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