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
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    EditText usernamefield,passwordfield;
    Button submitbutton;
    String username,password,userNamehash,passWordhash;
    Boolean usrcheck,pswdcheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        usernamefield = view.findViewById(R.id.username);
        passwordfield = view.findViewById(R.id.password);
        submitbutton = view.findViewById(R.id.submitbutton);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        String voterid = Navigation_HomeActivity.voteridreturn();

        submitbutton.setOnClickListener(v -> {
            if(usernamefield.getText().toString().isEmpty())
            {
                usernamefield.setError("Please Enter Your Username");
            }
            else if(passwordfield.getText().toString().isEmpty())
            {
                passwordfield.setError("Please Enter Your Password");
            }
            else
            {
                username = usernamefield.getText().toString();
                password = passwordfield.getText().toString();

                FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.setFirestoreSettings(firestoreSettings);
                DocumentReference docRef = db.collection("Test_User").document(voterid);
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        Map<String, Object> data = documentSnapshot.getData();
                        userNamehash = data.get("Username").toString();
                        passWordhash = data.get("Password").toString();

                        usrcheck = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(username, userNamehash);
                        pswdcheck = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(password, passWordhash);

                        if(usrcheck && pswdcheck)
                            {
                                Toast.makeText(getActivity(),"Username and Password Correct",Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.enternewpassword);

                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Incorrect Username or Password. Please Try Again",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            {
                                Toast.makeText(getActivity(),"No User Account Found. Please Try Again",Toast.LENGTH_LONG).show();
                            }
                    })
                .addOnFailureListener(e -> Toast.makeText(getActivity(),"Failed to Connect to Server. Please Try Again",Toast.LENGTH_LONG).show());
            }
        });

    }

}