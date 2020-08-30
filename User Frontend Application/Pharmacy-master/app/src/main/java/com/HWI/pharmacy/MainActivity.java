package com.HWI.pharmacy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Toolbar toolbar;
    private FloatingActionButton pres,refill,add;
    public static final int RC_SIGN_IN=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        FirebaseApp.initializeApp(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.sign_out);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pres = findViewById(R.id.prescription);
        refill = findViewById(R.id.refill);
        add = findViewById(R.id.add_pres);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo == null ){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,YourPrescription.class);
                startActivity(i);
            }
        });

        refill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SelectMedicine.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPrescription.class);
                startActivity(i);
            }
        });

        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override


            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =firebaseAuth.getCurrentUser();

                if(user != null){
                    String name = user.getDisplayName();
                }
                else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .setTheme(R.style.AppTheme)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.profile:
                Intent i =new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(i);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}