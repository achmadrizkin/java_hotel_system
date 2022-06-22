package com.example.java_hotel_system.view.bottomNav.profile;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.example.java_hotel_system.view.bottomNav.profile.info_app.InfoAppActivity;
import com.example.java_hotel_system.view.bottomNav.profile.map.GetLocationMap;
import com.example.java_hotel_system.view.bottomNav.profile.qr_details.QrDetailsActivity;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.example.java_hotel_system.view.select_role.SelectRoleActivity;
import com.example.java_hotel_system.view_model.LoginFacebookViewModel;
import com.example.java_hotel_system.view_model.UserDetailsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private ImageView profile_image;
    private TextView tvDisplayName, tvEmail;
    private ProgressBar pbLoading;

    private ConstraintLayout clLogin, clNotLogin;
    private Button btnToLogin, btnInfoApp, btnAddRoom;

    private Button btnScanQR;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private UserDetailsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        profile_image = view.findViewById(R.id.profile_image);
        tvDisplayName = view.findViewById(R.id.tvDisplayName);
        tvEmail = view.findViewById(R.id.tvEmail);

        btnToLogin = view.findViewById(R.id.btnToLogin);
        btnSignOut = view.findViewById(R.id.btnLogOut);
        btnInfoApp = view.findViewById(R.id.btnInfoApp);
        btnAddRoom = view.findViewById(R.id.btnAddRoom);
        btnScanQR = view.findViewById(R.id.btnScanQR);
        pbLoading = view.findViewById(R.id.pbLoading);

        clLogin = view.findViewById(R.id.clLogin);
        clNotLogin = view.findViewById(R.id.clNotLogin);

        clLogin.setVisibility(View.GONE);
        clNotLogin.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);

        if (user != null) {
            // call API
            getUserDetailByUIDFromView(mAuth.getCurrentUser().getUid());
        } else {
            clNotLogin.setVisibility(View.VISIBLE);
            clLogin.setVisibility(View.GONE);
        }


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GetLocationMap.class));
            }
        });

        btnInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoAppActivity.class));
            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionHandler();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) {
            Log.d(TAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;

            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;
        }

        // QR CODE SUCCESS
        //TODO: PASS THIS TO NEW ACTIVITY
        if(requestCode == REQUEST_CODE_QR_SCAN) {
            if(data==null)
                return;

            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
//            Log.d(TAG,"Have scan result in your app activity :"+ result);
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//            alertDialog.setTitle("Scan result");
//            alertDialog.setMessage(result);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

            // intent
            Intent p = new Intent(getActivity(), QrDetailsActivity.class);
            p.putExtra("result", result);
            startActivity(p);
        }
    }

    private void permissionHandler() {
        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent(getActivity(), QrCodeActivity.class);
                        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionDeniedResponse.getRequestedPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private void getUserDetailByUIDFromView(String uid) {
        viewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
        viewModel.getUserDetailsByUIDObservable().observe(getActivity(), new Observer<ListUser>() {
            @Override
            public void onChanged(ListUser t) {
                if (t == null) {

                } else {
                    tvDisplayName.setText(t.getData().get(0).getName());
                    tvEmail.setText(t.getData().get(0).getRole() + " " + t.getData().get(0).getLog_via());
                    Glide.with(profile_image).load(t.getData().get(0).getImage_url()).into(profile_image);

                    if (t.getData().get(0).getRole().equals("user")) {
                        btnAddRoom.setVisibility(View.GONE);
                    } else {
                        btnAddRoom.setVisibility(View.VISIBLE);
                    }


                    clLogin.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.GONE);
                    clNotLogin.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getUserDetailsByUIDOfData(uid);
    }

}