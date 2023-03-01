package com.project.onlinevotingsystem;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    String name,date;
    LinearLayout electionview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        electionview = view.findViewById(R.id.electionlinear);
        read();

    }

    private void read(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Election_Data");
        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                name = documentSnapshot.getString("Name");
                date = documentSnapshot.getString("Date");

                LinearLayout linearLayout = new LinearLayout(getContext());

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                float[] cornerRadii = new float[] { 40, 40, 40, 40, 40, 40, 40, 40 };
                shape.setCornerRadii(cornerRadii);
                String linearlayoutcolorhash = "#AA121212";
                int colorint = Color.parseColor(linearlayoutcolorhash);
                shape.setColors(new int[]{colorint,colorint});

                linearLayout.setPadding(20,10,20,5);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setClickable(true);

                linearLayout.setOnClickListener(v -> {

                });
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(0,20,0,40);

                linearLayout.setLayoutParams(layoutParams1);
                linearLayout.setBackground(shape);
                int textcolor = ContextCompat.getColor(getContext(),R.color.lightgreytext);
                Typeface font = Typeface.create("Abeezee",Typeface.NORMAL);

                // Create a new TextView for the data and add it to the Linear Layout
                TextView nameview = new TextView(getContext());
                nameview.setText(name);
                nameview.setTextSize(23);
                nameview.setTypeface(font);
                nameview.setTextColor(textcolor);
                nameview.setPadding(5,10,0,10);
                nameview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(nameview);

                TextView dateview = new TextView(getContext());
                dateview.setTypeface(font);
                dateview.setText(date);
                dateview.setTextSize(15);
                dateview.setTextColor(textcolor);
                dateview.setPadding(5,10,0,10);
                dateview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(dateview);


                TextView timeview = new TextView(getContext());
                timeview.setText("09:00 AM to 03:00 PM");
                timeview.setTypeface(font);
                timeview.setTextSize(15);
                timeview.setTextColor(textcolor);
                timeview.setPadding(5,10,0,10);
                timeview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(timeview);

                // Add the Linear Layout to the parent view
                electionview.addView(linearLayout);

            }
        }).addOnFailureListener(e -> Toast.makeText(getContext(),"Failed to Read Documents",Toast.LENGTH_LONG).show());
    }

}