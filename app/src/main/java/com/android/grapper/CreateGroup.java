package com.android.grapper;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CreateGroup extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;


    String mUID=FirebaseAuth.getInstance().getCurrentUser().getUid();

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    String code;
    int counter;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //List<String> myDataset;
    List<MyAdapterDataSet> x= new ArrayList<MyAdapterDataSet>(2);

    Button mMapButton;
    Button mLeaveGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent i=getIntent();
        code=i.getStringExtra("Code");


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(code);

        myRef2 = database.getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        //myDataset={"one"};


//kxffqode
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                    Log.d("", "code equals");
//                    TextView s = (TextView) findViewById(R.id.check);
//                    s.setText("Reached");
//                    String name = snap.child("Name").getValue().toString();
//                    MyAdapterDataSet n = new MyAdapterDataSet(name);
//                    x.add(n);
//                }
//            }

//
//
//
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d("", "code equals");
                    TextView yy = (TextView) findViewById(R.id.check);
                    yy.setText("Reached");
                    String name = dataSnapshot.child("Name").getValue().toString();
                    MyAdapterDataSet n = new MyAdapterDataSet(name);
                    x.add(n);

                mAdapter = new MyAdapter(x);

                mRecyclerView.setAdapter(mAdapter);

                //}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String namee = dataSnapshot.child("Name").getValue().toString();
                for(int i=0;i<x.size();i++)
                {
                    if(x.get(i).name.equals(namee)){
                        x.remove(i);
                    }
                }
                mAdapter = new MyAdapter(x);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //DatabaseReference ref = database.getReference("GID");

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }







        myRef.child(mUID);
        myRef.child(mUID).child("Name").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        TextView x=findViewById(R.id.codeDisplay);
        x.setText(code);

        mMapButton=(Button)findViewById(R.id.mapButton);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateGroup.this,MapsActivity.class);
                i.putExtra("Code",code);
                startActivity(i);
            }
        });

        mLeaveGroup=(Button)findViewById(R.id.leaveGroup);
        mLeaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(mUID).removeValue();
                startActivity(new Intent(CreateGroup.this,StartActivity.class));
            }
        });
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        catch (SecurityException e)
        {}
        if (mLastLocation != null) {
            myRef.child(mUID).child("Lat").setValue(String.valueOf(mLastLocation.getLatitude()));
            myRef.child(mUID).child("Long").setValue(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // We are not connected anymore!
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // We tried to connect but failed!
    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onBackPressed(){}

}
