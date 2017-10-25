package com.android.grapper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.ContentValues.TAG;

/**
 * Created by saraf on 10/18/2017.
 */

public class SignUpFragment extends android.support.v4.app.Fragment {

    private FirebaseAuth mAuth;
    private EditText mNewName;
    private EditText mNewUserName;
    private EditText mNewPassword;
    private Button mNewSignUp;

    private String email, password, name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        mNewName=(EditText)v.findViewById(R.id.newName);
        mNewUserName=(EditText)v.findViewById(R.id.newUserName);
        mNewPassword=(EditText)v.findViewById(R.id.newPassword);
        mNewSignUp=(Button)v.findViewById(R.id.newSignUp);

        mNewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= mNewUserName.getText().toString();
                password= mNewPassword.getText().toString();
                name= mNewName.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdate= new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                    user.updateProfile(profileUpdate);
                                    Toast.makeText(getActivity(), "Logging In",
                                            Toast.LENGTH_SHORT).show();
                                    while(user.getDisplayName()==null){};
                                    Intent intent = new Intent(getActivity(), StartActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();


                                }

                                // ...
                            }
                        });
            }
        });




        return v;
    }


}
