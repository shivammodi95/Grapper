package com.android.grapper;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database;
    String code;
    float inc=0.00000002f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i=getIntent();
        code=i.getStringExtra("Code");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final List<MarkerOptions> mMarkers= new ArrayList<MarkerOptions>(1);

        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(code);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                float latt = Float.parseFloat(dataSnapshot.child("Lat").getValue().toString());
                float longg =Float.parseFloat(dataSnapshot.child("Long").getValue().toString());
                String name = dataSnapshot.child("Name").getValue().toString();
                latt=latt+inc;
                inc+=0.00002f;
                LatLng x=new LatLng(latt,longg);

                MarkerOptions singleMarker= new MarkerOptions().position(x).title(name);
                mMarkers.add(singleMarker);
                mMap.addMarker(singleMarker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(x));



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String namee = dataSnapshot.child("Name").getValue().toString();
                for(int i=0;i<mMarkers.size();i++)
                {
                    if(mMarkers.get(i).getTitle().toString().equals(namee))
                    {
                        mMarkers.remove(i);
                    }
                }
                float latt = Float.parseFloat(dataSnapshot.child("Lat").getValue().toString());
                float longg =Float.parseFloat(dataSnapshot.child("Long").getValue().toString());
                String name = dataSnapshot.child("Name").getValue().toString();
                latt=latt+inc;
                inc+=0.00002f;
                LatLng x=new LatLng(latt,longg);

                MarkerOptions singleMarker= new MarkerOptions().position(x).title(name);
                mMarkers.add(singleMarker);
                mMap.clear();
                for(int i=0;i<mMarkers.size();i++)
                {
                    mMap.addMarker(mMarkers.get(i));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String namee = dataSnapshot.child("Name").getValue().toString();
                for(int i=0;i<mMarkers.size();i++)
                {
                    if(mMarkers.get(i).getTitle().toString().equals(namee))
                    {
                        mMarkers.remove(i);
                    }
                }
                mMap.clear();
                for(int i=0;i<mMarkers.size();i++)
                {
                    mMap.addMarker(mMarkers.get(i));
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Add a marker in Sydney and move the camera
        //mMap.setMinZoomPreference(6.0f);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
    }
}
