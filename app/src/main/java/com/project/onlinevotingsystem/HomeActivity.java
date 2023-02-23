package com.project.onlinevotingsystem;


import android.annotation.SuppressLint;
import android.os.Bundle;
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

                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(0,10,0,10);

                linearLayout.setLayoutParams(layoutParams1);

                // Create a new TextView for the data and add it to the Linear Layout
                TextView nameview = new TextView(this);
                nameview.setText(name);
                nameview.setTextSize(20);
                nameview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(nameview);

                TextView dateview = new TextView(this);
                dateview.setText("Date:"+date);
                dateview.setTextSize(20);
                dateview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(dateview);

                // Add the Linear Layout to the parent view
                electionview.addView(linearLayout);



            }
        }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this,"Failed to Read Documents",Toast.LENGTH_LONG).show());
    }
}