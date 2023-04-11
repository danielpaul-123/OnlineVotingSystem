package com.project.onlinevotingsystem;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

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
    String name,date,voterid;
    LinearLayout electionview;
    static Integer id;
    LinearDotsLoader progressloader;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),callback);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        electionview = view.findViewById(R.id.electionlinear);
        progressloader = view.findViewById(R.id.loadingprogress);
        read();

    }

    protected void read(){
        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(firestoreSettings);
        CollectionReference collectionRef = db.collection("Election_Data");
        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                name = documentSnapshot.getString("Name");
                date = documentSnapshot.getString("Date");
                id = Integer.valueOf(documentSnapshot.getId());

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
                linearLayout.setId(id);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(0,20,0,40);

                linearLayout.setLayoutParams(layoutParams1);
                linearLayout.setBackground(shape);
                int textcolor = ContextCompat.getColor(getContext(),R.color.lightgreytext);
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
                Typeface datefont = Typeface.createFromAsset(getContext().getAssets(), "fonts/abeezee.ttf");
                Typeface timefont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito.ttf");

                // Create a new TextView for the data and add it to the Linear Layout
                TextView nameview = new TextView(getContext());
                nameview.setText(name);
                nameview.setTextSize(25);
                nameview.setTypeface(font);
                nameview.setTextColor(textcolor);
                nameview.setPadding(5,10,0,10);
                nameview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(nameview);

                TextView dateview = new TextView(getContext());
                dateview.setTypeface(datefont);
                dateview.setText(date);
                dateview.setTextSize(18);
                dateview.setTextColor(textcolor);
                dateview.setPadding(5,10,0,10);
                dateview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(dateview);


                TextView timeview = new TextView(getContext());
                timeview.setText("09:00 AM to 03:00 PM");
                timeview.setTypeface(timefont);
                timeview.setTextSize(18);
                timeview.setTextColor(textcolor);
                timeview.setPadding(5,10,0,15);
                timeview.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(timeview);

                // Add the Linear Layout to the parent view
                progressloader.setVisibility(View.GONE);
                electionview.addView(linearLayout);


                linearLayout.setOnClickListener(v -> {
                    progressloader.setVisibility(View.VISIBLE);
                    id = linearLayout.getId();
                    voterid = Navigation_HomeActivity.voteridreturn();

                    ZonedDateTime indiazoneddatetime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
                    LocalDateTime indiaLocalDateTime = indiazoneddatetime.toLocalDateTime();
                    int indiatime = indiaLocalDateTime.getHour();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
                    String indiadate = indiaLocalDateTime.format(formatter);

                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    database.setFirestoreSettings(firestoreSettings);

                    DocumentReference reference = database.collection("Election_Data").document(String.valueOf(id));
                    reference.get().addOnSuccessListener(documentSnapshot2 -> {
                        Map<String,Object> data = documentSnapshot2.getData();
                        String date = data.get("Date").toString();
                        Long startT = (Long) data.get("startTime");
                        Long endT = (Long) data.get("endTime");
                        Integer startTime = Math.toIntExact(startT);
                        Integer endTime = Math.toIntExact(endT);
                        System.out.println(startTime);
                        System.out.println(endTime);
                        if (indiadate.equals(date))
                        {
                            DocumentReference documentReference = database.collection("Test_User").document(voterid);
                            documentReference.get().addOnSuccessListener(documentSnapshot1 -> {
                                if(documentSnapshot1.exists())
                                {
                                    if(indiatime<startTime)
                                    {
                                        Snackbar snackbar = Snackbar.make(getView(),"The Election has not yet Started",Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction("Dismiss", v15 -> snackbar.dismiss());
                                        progressloader.setVisibility(View.GONE);
                                        snackbar.show();
                                    }
                                    else if (indiatime>endTime)
                                    {
                                        Snackbar snackbar = Snackbar.make(getView(),"The Election has already Ended",Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction("Dismiss", v15 -> snackbar.dismiss());
                                        progressloader.setVisibility(View.GONE);
                                        snackbar.show();
                                    }
                                    else
                                    {
                                        Double uservotestatus = documentSnapshot1.getDouble(String.valueOf(id));
                                        if (uservotestatus >0)
                                        {
                                            Snackbar snackbar = Snackbar.make(getView(),"You Vote has already been Placed",Snackbar.LENGTH_INDEFINITE);
                                            snackbar.setAction("Dismiss", v1 -> snackbar.dismiss());
                                            progressloader.setVisibility(View.GONE);
                                            snackbar.show();
                                        }
                                        else
                                        {
                                            NavController navController = Navigation.findNavController(getView());
                                            navController.navigate(R.id.voting);
                                        }
                                    }
                                }
                                else
                                {
                                    Snackbar snackbar = Snackbar.make(getView(),"Failed to show details. Please Try Again",Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction("Dismiss", v1 -> snackbar.dismiss());
                                    progressloader.setVisibility(View.GONE);
                                    snackbar.show();
                                }
                            }).addOnFailureListener(e -> {
                                Snackbar snackbar = Snackbar.make(getView(),"Failed to show details. Please Try Again",Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Dismiss", v1 -> snackbar.dismiss());
                                progressloader.setVisibility(View.GONE);
                                snackbar.show();
                            });
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(getView(),"Please ensure that you attempt to vote on the day of the election.",Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Dismiss", v1 -> snackbar.dismiss());
                            progressloader.setVisibility(View.GONE);
                            snackbar.show();
                        }

                    }).addOnFailureListener(e -> {
                        Snackbar snackbar = Snackbar.make(getView(),"Failed to show details. Please Try Again.",Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Dismiss", v1 -> snackbar.dismiss());
                        progressloader.setVisibility(View.GONE);
                        snackbar.show();
                    });


                });

            }
        }).addOnFailureListener(e -> Toast.makeText(getContext(),"Failed to Read Documents",Toast.LENGTH_LONG).show());

    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            AlertDialog.Builder exitdialog = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
            exitdialog.setIcon(R.drawable.baseline_how_to_vote_40);
            exitdialog.setTitle(R.string.app_name);
            exitdialog.setMessage("Are you sure you want to Exit?");
            exitdialog.setPositiveButton("Yes", (dialog, which) -> getActivity().finish());
            exitdialog.setNegativeButton("Cancel",null);
            exitdialog.show();

        }
    };

    public static Integer voteid() {return id;}

}