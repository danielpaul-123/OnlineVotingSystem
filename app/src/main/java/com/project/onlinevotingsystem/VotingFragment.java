package com.project.onlinevotingsystem;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    LinearLayout votedata,votedata2;
    TextView votenametext;
    String name,date,candidate;
    StringBuilder sb = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_voting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        votedata = view.findViewById(R.id.voteoption);
        votedata2 = view.findViewById(R.id.voteoption2);
        votenametext = view.findViewById(R.id.votenametext);

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
                        System.out.println(votecandidate.getId());
                        votecandidate.setTypeface(font2);
                        votecandidate.setButtonTintList(colorStateList);
                        votecandidate.setPadding(30,30,0,30);
                        voteoptionlist.addView(votecandidate, voteoptionlayout);
                    }
                    else
                    {
                        i=11;
                    }
                }

                RadioButton votecandidate = new RadioButton(getContext());
                votecandidate.setText("NOTA");
                votecandidate.setTextSize(19);
                votecandidate.setId(notaid);
                System.out.println(votecandidate.getId());
                votecandidate.setTypeface(font2);
                votecandidate.setButtonTintList(colorStateList);
                votecandidate.setPadding(30,30,0,30);
                voteoptionlist.addView(votecandidate, voteoptionlayout);

                votedata2.addView(voteoptionlist);
            }
        });
    }

}