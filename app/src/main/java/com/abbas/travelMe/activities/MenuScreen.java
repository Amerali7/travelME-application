package com.abbas.travelMe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.abbas.travelMe.R;

public class MenuScreen extends AppCompatActivity implements View.OnClickListener {


    private Button generate;
    private Button add;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        Intent intent = getIntent();
        temp = intent.getIntExtra("ID", 0);

        generate = (Button) findViewById(R.id.generate);
        add = (Button) findViewById(R.id.addloc);
        generate.setOnClickListener(this);
        add.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.generate:
                Intent intent = new Intent(MenuScreen.this, Generate_Results.class);
                intent.putExtra("ID", temp);
                startActivity(intent);
                break;
            case R.id.addloc:
                Intent intentAdd = new Intent(MenuScreen.this, locationAuto.class);
                intentAdd.putExtra("ID", temp);
                startActivity(intentAdd);
                break;
        }
    }

}
