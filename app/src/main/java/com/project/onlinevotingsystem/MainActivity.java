package com.project.onlinevotingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button login;
    ImageView logo;
    EditText voterid, username, password;
    String voterID, userName, passWord, userNamehash, passWordhash;
    boolean usrcheck, pswdcheck;
    LinearDotsLoader linearDotsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        login = findViewById(R.id.loginbutton);
        voterid = findViewById(R.id.voterid);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        linearDotsLoader = findViewById(R.id.loginprogress);

        new Thread(() -> {
            try {
                Thread.sleep(750);
                runOnUiThread(() -> {
                    Animation logotranslate = AnimationUtils.loadAnimation(this, R.anim.translator);
                    logo.startAnimation(logotranslate);
                });
                Thread.sleep(500);
                runOnUiThread(() -> {
                    Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                    login.startAnimation(fadein);
                    voterid.startAnimation(fadein);
                    username.startAnimation(fadein);
                    password.startAnimation(fadein);
                    login.setVisibility(View.VISIBLE);
                    voterid.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        login.setOnClickListener(v -> {

            if (voterid.getText().toString().isEmpty()) {
                voterid.setError("Please Enter Your Voter's ID");
            } else if (username.getText().toString().isEmpty()) {
                username.setError("Please Enter Your Username");
            } else if (password.getText().toString().isEmpty()) {
                password.setError("Please Enter Your Password");
            } else {
                linearDotsLoader.setVisibility(View.VISIBLE);
                voterID = voterid.getText().toString();
                userName = username.getText().toString();
                passWord = password.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("Test_User").document(voterID);
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {

                                Map<String, Object> data = documentSnapshot.getData();
                                userNamehash = data.get("Username").toString();
                                passWordhash = data.get("Password").toString();

                                usrcheck = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(userName, userNamehash);
                                pswdcheck = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(passWord, passWordhash);

                                if (usrcheck && pswdcheck) {
                                    Bundle userdata = new Bundle();
                                    userdata.putString("username", userName);
                                    userdata.putString("voterid", voterID);
                                    Intent a = new Intent(this, Navigation_HomeActivity.class);
                                    a.putExtra("userdatabundle", userdata);
                                    startActivity(a);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                } else {
                                    Toast.makeText(this, "Incorrect Username or Password. Please Try Again", Toast.LENGTH_LONG).show();
                                    linearDotsLoader.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(this, "No User Account Found. Please Try Again", Toast.LENGTH_LONG).show();
                                linearDotsLoader.setVisibility(View.GONE);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to Connect to Server. Please Try Again", Toast.LENGTH_LONG).show();
                            linearDotsLoader.setVisibility(View.GONE);
                        });
            }
        });
    }

}