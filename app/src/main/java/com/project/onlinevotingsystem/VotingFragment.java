package com.project.onlinevotingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VotingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VotingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VotingFragment newInstance(String param1, String param2) {
        VotingFragment fragment = new VotingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Integer id;
    Integer notaid = 4862;
    LinearLayout votedata,votedata2,votesubmitbuttonlayout;
    TextView votenametext;
    String name,date,candidate,voterid;
    StringBuilder sb = new StringBuilder();
    LinearDotsLoader loadingprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_voting, container, false);
        return view;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        votedata = view.findViewById(R.id.voteoption);
        votedata2 = view.findViewById(R.id.voteoption2);
        votenametext = view.findViewById(R.id.votenametext);
        loadingprogress = view.findViewById(R.id.loadingprogress);
        votesubmitbuttonlayout = view.findViewById(R.id.votesubmitbuttonlayout);

        id = HomeFragment.voteid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Election_Data").document(String.valueOf(id));
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists())
            {
                Map<String,Object> data = documentSnapshot.getData();
                name = data.get("Name").toString();
                date = data.get("Date").toString();

                int textcolor = ContextCompat.getColor(getContext(),R.color.lightgreytext);
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
                Typeface font2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/abeezee.ttf");

                votenametext.setText(name);

                RadioGroup voteoptionlist = new RadioGroup(getContext());
                RadioGroup.LayoutParams voteoptionlayout = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );

                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(Color.rgb(35,62,154)));
                stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, new ColorDrawable(Color.BLUE));

                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                Color.rgb(49,82,194),
                                Color.WHITE
                        }
                );

                for (int i = 1; i < 10; i++) {
                    sb.setLength(0);
                    sb.append("Contestant");
                    sb.append(i);
                    if(documentSnapshot.contains(String.valueOf(sb)))
                    {
                        candidate = data.get(String.valueOf(sb)).toString();
                        RadioButton votecandidate = new RadioButton(getContext());
                        votecandidate.setText(candidate);
                        votecandidate.setTextSize(21);
                        votecandidate.setId(i);
                        votecandidate.setTypeface(font);
                        votecandidate.setTextColor(textcolor);
                        votecandidate.setTypeface(font2);
                        votecandidate.setButtonTintList(colorStateList);
                        votecandidate.setPadding(30,35,0,35);
                        voteoptionlist.addView(votecandidate, voteoptionlayout);
                    }
                    else
                    {
                        i=11;
                    }
                }

                RadioButton votecandidate = new RadioButton(getContext());
                votecandidate.setText("NOTA");
                votecandidate.setTextSize(21);
                votecandidate.setId(notaid);
                votecandidate.setTextColor(textcolor);
                votecandidate.setTypeface(font2);
                votecandidate.setButtonTintList(colorStateList);
                votecandidate.setPadding(30,35,0,35);
                voteoptionlist.addView(votecandidate, voteoptionlayout);

                Button votesubmit = new Button(getContext());
                votesubmit.setText("Submit Your Vote");
                votesubmit.setTextSize(22);
                votesubmit.setTypeface(font2);
                votesubmit.setBackgroundResource(R.drawable.button_bg);
                votesubmit.setPadding(10, 30, 10, 30);

                votedata2.addView(voteoptionlist);
                votesubmitbuttonlayout.addView(votesubmit);
                loadingprogress.setVisibility(View.GONE);

                votesubmit.setOnClickListener(v -> {
                    votecandidate.setError(null);
                    if (voteoptionlist.getCheckedRadioButtonId() <=0)
                    {
                        votecandidate.setError("Please select your option");
                    }
                    else if (voteoptionlist.getCheckedRadioButtonId() == notaid)
                    {

                        loadingprogress.setVisibility(View.VISIBLE);
                        id = HomeFragment.voteid();
                        voterid = Navigation_HomeActivity.voteridreturn();
                        FirebaseFirestore update = FirebaseFirestore.getInstance();
                        DocumentReference updateref = update.collection("Election_Stats").document(String.valueOf(id));
                        Map<String,Object> voteupdate = new HashMap<>();
                        voteupdate.put("NOTA", FieldValue.increment(1));
                        updateref.update(voteupdate).addOnSuccessListener(unused -> {
                            DocumentReference updateuser = update.collection("Test_User").document(voterid);
                            Map<String,Object> userupdate = new HashMap<>();
                            userupdate.put(String.valueOf(id), FieldValue.increment(1));
                            updateuser.update(userupdate).addOnSuccessListener(unused1 -> {
                                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());
                                View alertbuild = getLayoutInflater().inflate(R.layout.dialog_positive, null);
                                Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);
                                alertdialogbuilder.setView(alertbuild);
                                alertdialogbuilder.setCancelable(false);
                                AlertDialog alertDialog = alertdialogbuilder.create();
                                alertDialog.show();
                                dialogbutton.setOnClickListener(v12 -> {
                                    alertDialog.dismiss();
                                    NavController navController = Navigation.findNavController(getView());
                                    navController.navigate(R.id.home);
                                });
                            }).addOnFailureListener(e -> {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                View alertbuild = getLayoutInflater().inflate(R.layout.dialog_negative, null);
                                Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);
                                dialogBuilder.setView(alertbuild);
                                dialogBuilder.setCancelable(false);
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                                dialogbutton.setOnClickListener(v3 -> {
                                    alertDialog.dismiss();
                                });
                            });
                        }).addOnFailureListener(e -> {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                            View alertbuild = getLayoutInflater().inflate(R.layout.dialog_negative, null);
                            Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);
                            dialogBuilder.setView(alertbuild);
                            dialogBuilder.setCancelable(false);
                            AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();
                            dialogbutton.setOnClickListener(v4 -> {
                                alertDialog.dismiss();
                            });
                        });
                    }
                    else
                    {

                        loadingprogress.setVisibility(View.GONE);
                        id = HomeFragment.voteid();
                        sb.setLength(0);
                        sb.append("Contestant");
                        sb.append(voteoptionlist.getCheckedRadioButtonId());
                        voterid = Navigation_HomeActivity.voteridreturn();
                        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();
                        FirebaseFirestore update = FirebaseFirestore.getInstance();
                        update.setFirestoreSettings(firestoreSettings);
                        DocumentReference updateref = update.collection("Election_Stats").document(String.valueOf(id));
                        Map<String,Object> voteupdate = new HashMap<>();
                        voteupdate.put(String.valueOf(sb), FieldValue.increment(1));

                        updateref.update(voteupdate).addOnSuccessListener(unused -> {
                            DocumentReference updateuser = update.collection("Test_User").document(voterid);
                            Map<String,Object> userupdate = new HashMap<>();
                            userupdate.put(String.valueOf(id), FieldValue.increment(1));

                            updateuser.update(userupdate).addOnSuccessListener(unused12 -> {
                                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());

                                View alertbuild = getLayoutInflater().inflate(R.layout.dialog_positive, null);
                                Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);

                                alertdialogbuilder.setView(alertbuild);
                                alertdialogbuilder.setCancelable(false);
                                AlertDialog alertDialog = alertdialogbuilder.create();
                                alertDialog.show();
                                dialogbutton.setOnClickListener(v12 -> {
                                    alertDialog.dismiss();
                                    NavController navController = Navigation.findNavController(getView());
                                    navController.navigate(R.id.home);
                                });

                            }).addOnFailureListener(e -> {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                View alertbuild = getLayoutInflater().inflate(R.layout.dialog_negative, null);
                                Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);
                                dialogBuilder.setView(alertbuild);
                                dialogBuilder.setCancelable(false);
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                                dialogbutton.setOnClickListener(v12 -> {
                                    alertDialog.dismiss();
                                });
                            });

                        }).addOnFailureListener(e -> {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                            View alertbuild = getLayoutInflater().inflate(R.layout.dialog_negative, null);
                            Button dialogbutton = alertbuild.findViewById(R.id.btnDialog);
                            dialogBuilder.setView(alertbuild);
                            dialogBuilder.setCancelable(false);
                            AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();
                            dialogbutton.setOnClickListener(v2 -> {
                                alertDialog.dismiss();
                            });
                        });
                    }
                });
            }
        });
    }

}