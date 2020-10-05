package com.abbas.travelMe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.view.View;

import com.abbas.travelMe.R;
import com.abbas.travelMe.sql.DatabaseHelper;

public class preferencesActivity extends AppCompatActivity {//implements View.OnClickListener {

    private CheckBox sporty, nature, thrill, art, social, romantic, introvert, shop, film, music;
    private boolean checker, checker1, checker2, checker3, checker4, checker5, checker6, checker7, checker8, checker9;
    private Button submit;
    private DatabaseHelper db;
    int temp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        db = new DatabaseHelper(getApplicationContext());

        Intent intent= getIntent();
        temp = intent.getIntExtra("ID", 0); // users ID
        // USER selects preferences which align with their own
        sporty = (CheckBox) findViewById(R.id.sporty);
        nature = (CheckBox) findViewById(R.id.nature);
        thrill = (CheckBox) findViewById(R.id.thrill);
        art = (CheckBox) findViewById(R.id.art);
        social = (CheckBox) findViewById(R.id.social);
        romantic = (CheckBox) findViewById(R.id.romantic);
        introvert = (CheckBox) findViewById(R.id.introvent);
        shop = (CheckBox) findViewById(R.id.shopaholic);
        film = (CheckBox) findViewById(R.id.film);
        music = (CheckBox) findViewById(R.id.music);


        checkSporty();
        checkFilm();
        checkArt();
        checkRomantic();
        checkThrill();
        checkIntrovert();
        checkMusic();
        checkNature();
        checkShop();

        submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updatePref(checker, checker1, checker2, checker3, checker4, checker5, checker6, checker7, checker8, checker9, temp);
                Intent intent = new Intent(preferencesActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    public boolean checkSporty() {
        sporty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {


                if (isChecked) {
                    checker = true;
                    Toast.makeText(preferencesActivity.this, "Android Checked", Toast.LENGTH_LONG).show();
                } else {
                    checker = false;
                }
                //  db.addPref(checker);
            }

        });
        return checker;
    }

    public boolean checkNature() {
        nature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker1 = true;
                } else {
                    checker1 = false;
                }

            }

        });
        return checker1;
    }

    public boolean checkThrill() {
        thrill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker2 = true;
                } else {
                    checker2 = false;
                }

            }

        });
        return checker2;
    }

    public boolean checkArt() {
        art.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker3 = true;
                } else {
                    checker3 = false;
                }

            }

        });
        return checker3;
    }

    public boolean checkSocial() {
        social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker4 = true;
                } else {
                    checker4 = false;
                }

            }

        });
        return checker4;
    }

    public boolean checkRomantic() {
        romantic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker5 = true;
                } else {
                    checker5 = false;
                }

            }

        });
        return checker5;
    }

    public boolean checkIntrovert() {
        introvert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker6 = true;
                } else {
                    checker6 = false;
                }

            }

        });
        return checker6;
    }

    public boolean checkShop() {
        shop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker7 = true;
                } else {
                    checker7 = false;
                }

            }

        });
        return checker7;
    }

    public boolean checkFilm() {
        film.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker8 = true;
                } else {
                    checker8 = false;
                }

            }

        });
        return checker8;
    }

    public boolean checkMusic() {
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    checker9 = true;
                } else {
                    checker9 = false;
                }

            }

        });
        return checker9;
    }


}

