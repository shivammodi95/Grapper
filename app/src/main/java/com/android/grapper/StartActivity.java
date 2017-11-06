package com.android.grapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private Button mLogout;
    private Button mJoinGroup;
    private Button mCreateGroup;
    private FirebaseAuth mAuth;
    String code;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        code=getRndom();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        Log.d("New Activity","Reached new activity");





        TextView x=findViewById(R.id.printName);
        x.setText("Hi! "+user.getDisplayName()+"\n");

        mLogout= (Button)findViewById(R.id.logOut);
        mJoinGroup = (Button)findViewById(R.id.joinGroup);
        mCreateGroup = (Button)findViewById(R.id.createGroup);

        mCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(StartActivity.this, CreateGroup.class);
                i.putExtra("Code",code);


                        startActivity(i);

                    }
        });

        mJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, JoinGroup.class));

            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });


    }



    String getRndom(){
        String r = "";
        for(int i=0;i<6;i++)
        {
            r+=(char)(Math.random()*26+65);
        }
        return r;
    }
}
