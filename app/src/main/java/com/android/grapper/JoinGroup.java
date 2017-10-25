package com.android.grapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinGroup extends AppCompatActivity {


    private EditText mJoinCode;
    private Button mJoinGroup;
    String code;
    String flag="b";
    FirebaseDatabase database;
    DatabaseReference myRef;

    String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        mJoinCode=(EditText)findViewById(R.id.joinCode);
        mJoinGroup=(Button)findViewById(R.id.joinGroup2);

        mUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



        mJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=mJoinCode.getText().toString();
                final DatabaseReference myRef = database.getReference();



                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            TextView x= findViewById(R.id.xyz);

                            if(code.equals(singleSnapshot.getKey().toString()))
                            {
                                Intent i=new Intent(JoinGroup.this,CreateGroup.class);
                                i.putExtra("Code",code);
                                //myRef.child(code).child(mUID);
                                //myRef.child(code).child(mUID).child("Name").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                flag="a";


                                startActivity(i);
                            }
                        }
                        if(flag.equals("b"))
                        {

                            TextView x= findViewById(R.id.xyz);
                            x.setText("Code Not found");

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("", "onCancelled", databaseError.toException());
                    }
                });

            }
        });
    }
}
