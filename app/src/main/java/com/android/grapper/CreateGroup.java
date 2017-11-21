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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateGroup extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

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

    String lat1,long1;
    public int flagg=0,flagg2=1;




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

                if(dataSnapshot.getChildrenCount()!=3) {
                    flagg++;

                    Toast.makeText(getApplicationContext(), "No Location "+flagg, Toast.LENGTH_SHORT).show();
                }

                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getChildrenCount()>=3) {
                    float latt = Float.parseFloat(dataSnapshot.child("Lat").getValue().toString());
                    float longg = Float.parseFloat(dataSnapshot.child("Long").getValue().toString());

                    LatLng x = new LatLng(latt, longg);

                    Log.d("jkl", "jkl");
                    float latt2 = Float.parseFloat(lat1);
                    float longg2 = Float.parseFloat(long1);


                    LatLng x2 = new LatLng(latt2, longg2);
                    if (CalculationByDistance(x, x2) > 1.0) {
                        Toast.makeText(getApplicationContext(),dataSnapshot.child("Name").getValue().toString()+" is more than a kilometer away", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("less", "onChildChanged: lesser");
                    }
                }


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

                if(dataSnapshot.getChildrenCount()==1)
                {flagg--;}

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
            if(flagg<=0) {
                Intent i = new Intent(CreateGroup.this, MapsActivity.class);
                i.putExtra("Code", code);
                startActivity(i);
            }
            else {Toast.makeText(getApplicationContext(), "User Location off:", Toast.LENGTH_SHORT).show();}
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

            lat1=String.valueOf(mLastLocation.getLatitude());
            long1=String.valueOf(mLastLocation.getLongitude());
            flagg--;
            flagg2--;
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
    public void onLocationChanged(Location location){
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
    public void onBackPressed(){}

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

}
