package com.HWI.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class YourPrescription extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recView;
    private RecyclerView.LayoutManager lManager;
    private List<prescModel> list;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_prescription);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recView = findViewById(R.id.recView);
        lManager = new GridLayoutManager(this,1);
        recView.setLayoutManager(lManager);
        fab = findViewById(R.id.add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(YourPrescription.this, AddPrescription.class);
                startActivity(i);
            }
        });

        list = new ArrayList<>();
        list.add(new prescModel("","PRES A","MED A,B,C","NO"));
        list.add(new prescModel("","PRES B","MED A,B","Yes"));
        list.add(new prescModel("","PRES D","MED A,C","Yes"));
        list.add(new prescModel("","PRES F","MED A,B,C,E","NO"));
        list.add(new prescModel("","PRES Z","MED A,B,C,D,E","Yes"));
        final prescAdapater adapter = new prescAdapater(list);
        adapter.notifyDataSetChanged();
        recView.setAdapter(adapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }
}