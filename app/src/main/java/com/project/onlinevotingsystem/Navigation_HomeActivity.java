package com.project.onlinevotingsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

public class Navigation_HomeActivity extends AppCompatActivity {

    DrawerLayout navigationlayout;
    RoundedImageView profile_image;
    TextView usernameview,voteridview,title;
    NavigationView nav_menu;
    static String voterid;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_home);
        navigationlayout = findViewById(R.id.navigationlayout);
        nav_menu = findViewById(R.id.nav_menu);
        title = findViewById(R.id.apptitle);
        NavController navController = Navigation.findNavController(this,R.id.navigation_host_fragment);
        NavigationUI.setupWithNavController(nav_menu, navController);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> title.setText(navDestination.getLabel()));

        View view = nav_menu.inflateHeaderView(R.layout.navigation_header);
        profile_image = view.findViewById(R.id.profile_picture);
        usernameview = view.findViewById(R.id.usernameview);
        voteridview = view.findViewById(R.id.voteridview);

        Intent userdata = getIntent();
        Bundle userdatabundle = userdata.getBundleExtra("userdatabundle");

        username = userdatabundle.getString("username");
        voterid = userdatabundle.getString("voterid");


        usernameview.setText(username);
        voteridview.setText(voterid);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("user_profile/"+voterid);

        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            profile_image.setImageBitmap(bitmap);

        }).addOnFailureListener(e -> Toast.makeText(Navigation_HomeActivity.this,"Failed to Load Profile Image",Toast.LENGTH_LONG).show());
        usernameview.setText(username);
        voteridview.setText(voterid);

        findViewById(R.id.menubutton).setOnClickListener(v -> navigationlayout.openDrawer(GravityCompat.START));

    }
    public static String voteridreturn()
    {
        return voterid;
    }

}