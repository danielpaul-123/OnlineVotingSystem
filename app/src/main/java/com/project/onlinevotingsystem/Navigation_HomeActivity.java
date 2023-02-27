package com.project.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Navigation_HomeActivity extends AppCompatActivity {

    DrawerLayout navigationlayout;
    RoundedImageView profile_image;
    TextView usernameview,voteridview;
    NavigationView nav_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_home);

        navigationlayout = findViewById(R.id.navigationlayout);
        nav_menu = findViewById(R.id.nav_menu);
        View view = nav_menu.inflateHeaderView(R.layout.navigation_header);

        profile_image = view.findViewById(R.id.profile_picture);
        usernameview = view.findViewById(R.id.usernameview);
        voteridview = view.findViewById(R.id.voteridview);

        Intent userdata = getIntent();
        Bundle userdatabundle = userdata.getBundleExtra("userdatabundle");

        String username = userdatabundle.getString("username");
        String voterid = userdatabundle.getString("voterid");

        usernameview.setText(username);
        voteridview.setText(voterid);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("user_profile/"+voterid);

        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profile_image.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Navigation_HomeActivity.this,"Failed to Load Profile Image",Toast.LENGTH_LONG).show();

            }
        });
        usernameview.setText(username);
        voteridview.setText(voterid);

        findViewById(R.id.menubutton).setOnClickListener(v -> navigationlayout.openDrawer(GravityCompat.START));

    }
}