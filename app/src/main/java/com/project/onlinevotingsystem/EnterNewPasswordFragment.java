package com.project.onlinevotingsystem;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnterNewPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterNewPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnterNewPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterNewPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterNewPasswordFragment newInstance(String param1, String param2) {
        EnterNewPasswordFragment fragment = new EnterNewPasswordFragment();
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
    EditText newpassword,confirmpassword;
    Button Submit;
    String passwordnew,newpasswordconfirm,voterid,newpasswordhash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_new_password, container, false);
        newpassword = view.findViewById(R.id.newpassword);
        confirmpassword = view.findViewById(R.id.confirmnewpassword);
        Submit = view.findViewById(R.id.submitbutton);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),callback);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        voterid = Navigation_HomeActivity.voteridreturn();
        Submit.setOnClickListener(v -> {
            if(newpassword.getText().toString().isEmpty())
            {
                newpassword.setError("Please Enter Your Username");
            }
            else if(confirmpassword.getText().toString().isEmpty())
            {
                confirmpassword.setError("Please Enter Your Password");
            }
            else
            {
                passwordnew = newpassword.getText().toString();
                newpasswordconfirm = confirmpassword.getText().toString();
                if (passwordnew.equals(newpasswordconfirm))
                {
                    newpasswordhash = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2).encode(passwordnew);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference ref = db.collection("Test_User").document(String.valueOf(voterid));

                    Map<String,Object> hashdata = new HashMap<>();
                    hashdata.put("Password",newpasswordhash);

                    ref.update(hashdata)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(getActivity(),"Password Updated Successfully ",Toast.LENGTH_LONG).show();
                            navController.navigate(R.id.home);

                        })
                        .addOnFailureListener(e -> Toast.makeText(getActivity(),"Failed to Update Password. Please Try Again",Toast.LENGTH_LONG).show());

                }
                else
                {
                    Toast.makeText(getActivity(),"Passwords do not match. Please Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.home);
        }
    };
}