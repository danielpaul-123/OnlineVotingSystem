package com.project.onlinevotingsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnterNewUsernameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterNewUsernameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnterNewUsernameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterNewUsernameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterNewUsernameFragment newInstance(String param1, String param2) {
        EnterNewUsernameFragment fragment = new EnterNewUsernameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button submit;
    EditText newusername,confirmnewusername;
    String usernamenew,newusernamehash,voterid;



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
        View view = inflater.inflate(R.layout.fragment_enter_new_username, container, false);
        submit = view.findViewById(R.id.submitbutton);
        newusername = view.findViewById(R.id.newusername);
        confirmnewusername = view.findViewById(R.id.usernameconfirm);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        voterid = Navigation_HomeActivity.voteridreturn();
        submit.setOnClickListener(v -> {

            if(newusername.getText().toString().isEmpty())
            {
                newusername.setError("Please Enter Your Username");
            }
            else if(confirmnewusername.getText().toString().isEmpty())
            {
                confirmnewusername.setError("Please Enter Your Password");
            }
            else
            {
                if ((newusername.getText().toString()).equals(confirmnewusername.getText().toString()))
                {
                    usernamenew = newusername.getText().toString();
                    newusernamehash = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2).encode(usernamenew);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference ref = db.collection("Test_User").document(String.valueOf(voterid));

                    Map<String,Object> hashdata = new HashMap<>();
                    hashdata.put("Username",newusernamehash);

                    ref.update(hashdata)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(getActivity(),"Username Updated Successfully ",Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.home);

                            })
                            .addOnFailureListener(e -> Toast.makeText(getActivity(),"Failed to Update Username. Please Try Again",Toast.LENGTH_LONG).show());

                }
                else
                {
                    Toast.makeText(getActivity(),"Usernames do not match. Please Try Again",Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}