package com.project.onlinevotingsystem;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    String name,date;
    LinearLayout electionview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        electionview = findViewById(R.id.electionlinear);
        read();
    }
    @SuppressLint("SetTextI18n")
    private void read(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Election_Data");
        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                name = documentSnapshot.getString("Name");
                date = documentSnapshot.getString("Date");

                LinearLayout linearLayout = new LinearLayout(this);

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                float[] cornerRadii = new float[] { 40, 40, 40, 40, 40, 40, 40, 40 };
                shape.setCornerRadii(cornerRadii);
                String colorhash = "#2FFFFFFF";
                int colorint = Color.parseColor(colorhash);
                shape.setColors(new int[]{colorint,colorint});

                linearLayout.setPadding(20,10,20,10);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(0,20,0,40);

                linearLayout.setLayoutParams(layoutParams1);
                linearLayout.setBackground(shape);

                // Create a new TextView for the data and add it to the Linear Layout
                TextView nameview = new TextView(this);
                nameview.setText(name);
                nameview.setTextSize(30);
                nameview.setTextColor(Color.WHITE);
                nameview.setPadding(5,10,0,10);
                nameview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(nameview);

                TextView dateview = new TextView(this);
                dateview.setText("On "+date);
                dateview.setTextSize(20);
                dateview.setTextColor(Color.WHITE);
                dateview.setPadding(5,10,0,10);
                dateview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(dateview);

                TextView timeview = new TextView(this);
                timeview.setText("From 09:00 AM to 03:00 PM");
                timeview.setTextSize(20);
                timeview.setTextColor(Color.WHITE);
                timeview.setPadding(5,10,0,10);
                timeview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(timeview);

                // Add the Linear Layout to the parent view
                electionview.addView(linearLayout);

            }
        }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this,"Failed to Read Documents",Toast.LENGTH_LONG).show());
    }

}