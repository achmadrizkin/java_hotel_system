package com.example.java_hotel_system.view.login;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.model.user.PostUserResponse;
import com.example.java_hotel_system.service.RetroService;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.login.facebook.FacebookAuth;
import com.example.java_hotel_system.view_model.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private TextView tvSkip;
    private ImageView ivGoogle, ivFacebook;

    //
    private String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1;

    //
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvSkip = findViewById(R.id.tvSkip);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivFacebook = findViewById(R.id.ivFacebook);

        mAuth = FirebaseAuth.getInstance();

        //
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("606600641805-6cmtbgdohp7ciipnfvmcea90ic8ehshs.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                startActivity(p);
            }
        });

        ivGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up or login
                signIn();
            }
        });

        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(LoginActivity.this, FacebookAuth.class);
                p.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(p);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "A: ", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Success Sign In", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                    finish();

                    //
                    if (mAuth.getCurrentUser() != null) {
                        PostUserRequest a = new PostUserRequest(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getPhotoUrl().toString());
                        postUserByLoginFromApiCall(a);
                    } else {
                        PostUserRequest a = new PostUserRequest("ASHIAPPPPpp","12345","www.ashiap.com");
                        postUserByLoginFromApiCall(a);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Error Sign In", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void postUserByLoginFromApiCall(PostUserRequest postUserRequest) {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.postUserByLoginObservable().observe(LoginActivity.this, new Observer<PostUserRequest>() {
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