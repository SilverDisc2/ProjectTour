package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private TextView signUpTV;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private EditText emailET, passwordET;
    private Button loginBTN;
    private String email, password;
    private FirebaseAuth firebaseAuth;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
getActivity().finish();

            startActivity(new Intent(getActivity(), NavigationActivity.class));
        }        signUpTV = view.findViewById(R.id.signUpTV);
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeFragment(new SignUpFragment());
            }
        });

        emailET = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        loginBTN = view.findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Please enter both fields!", Toast.LENGTH_SHORT).show();
                } else {
                    loginEmailAndPassword(email, password);

                }
            }
        });


        return view;
    }

    private void loginEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Signed In!", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(getActivity(),NavigationActivity.class));

                    Intent intent = new Intent(getActivity(),NavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Email or password doesn't match!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    protected void initializeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.loginFL, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
