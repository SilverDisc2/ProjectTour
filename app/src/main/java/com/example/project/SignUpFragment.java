package com.example.project;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private ImageView backFromRegIV;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText nameEt, genderET, mobileNumberET, emailET, passwordET, confirm_password;
    private Button signUpBTN;
    private Spinner spinner;
    private String name, gender, number, email, password, confPassword;
    String[] genderType = { "Male", "Female"};
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        backFromRegIV = view.findViewById(R.id.backFromRegIV);
        backFromRegIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initializeFragment(new LoginFragment());

            }
        });

        setUpFields(view);
        signUpClicked();

        return view;
    }

    protected void initializeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.loginFL, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setUpFields(View view) {

        nameEt = view.findViewById(R.id.nameET);
        mobileNumberET = view.findViewById(R.id.mobileNumberdET);
        //genderET = view.findViewById(R.id.genderET);
        spinner=(Spinner)view.findViewById(R.id.genderET);


        spinner = (Spinner) view.findViewById(R.id.genderET);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 gender=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

              /*  ArrayAdapter<String> arrayAdapter= new ArrayAdapter(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,genderType);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(arrayAdapter);*/



        emailET = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        confirm_password = view.findViewById(R.id.confirmPasswordET);
        signUpBTN = view.findViewById(R.id.signUpBTN);

    }

    private void signUpClicked() {
        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
               // gender = spinner.getText().toString();


                number = mobileNumberET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confPassword = confirm_password.getText().toString();

                if (password.contains(confPassword)) {

                    signUpWithEmailAndPassword(email, password);

                }
                else {

                    Toast.makeText(getActivity(), " Password doesn't match! ", Toast.LENGTH_SHORT).show();

                }

                if (name.equals("") || gender.equals("") || number.equals("") || email.equals("") || password.equals("")) {

                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private void signUpWithEmailAndPassword(final String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Map<String,Object> userMap=new HashMap<>();
                userMap.put("name",name);
                userMap.put("gender",gender);
                userMap.put("mobile",number);
                userMap.put("email",email);


                if (task.isSuccessful()){

                    String userId= firebaseAuth.getCurrentUser().getUid();
                    userMap.put("userId",userId);

                    DatabaseReference databaseReference=firebaseDatabase.getReference().child("UsersList").child(userId);
                    databaseReference.setValue(userMap);
                    Toast.makeText(getActivity(), "Sign Up Successfull", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




}
