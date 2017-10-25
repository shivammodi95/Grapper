package com.android.grapper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);


        if(fragment==null)
        {
            fragment=new TryFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("A", "onStart() called now");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        goLogin(currentUser);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("A", "onResume() called now");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("A", "onPause() called now");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("A", "onStop() called now");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("A", "onDestroy() called now");
    }

    public void goLogin(FirebaseUser current){
        if(current!=null) {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
        }
        else
            return;
    }
}
