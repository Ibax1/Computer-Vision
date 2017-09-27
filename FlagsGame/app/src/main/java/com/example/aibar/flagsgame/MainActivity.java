package com.example.aibar.flagsgame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.view.Gravity.CENTER;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView textView;
    private TableLayout tableLayout;
    private ArrayList<TableRow> tableRows;
    private ArrayList<ImageButton> imageButtons;
    private ArrayList<String> countryNames;
    private ArrayList<String> countryDescription;
    private int[] order;
    private int[] resources;
    private int currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // LinearLayout
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams (
                ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //linearLayout.setBackgroundColor(0xFF0000);


        // TextView
        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams (
                ViewGroup.LayoutParams.WRAP_CONTENT ,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewParam.topMargin = 50;
        textViewParam.bottomMargin = 50;
        textViewParam.gravity = Gravity.CENTER_HORIZONTAL;

        textView = new TextView(this);

        textView.setLayoutParams(textViewParam);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setText("Text");
        textView.setTextSize(35);
        //textView.setTextColor(0xFF0000);


        // TableLayout
        LinearLayout.LayoutParams tableLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tableLayoutParams.gravity = CENTER;
        //tableLayoutParams.leftMargin = 20;

        tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(tableLayoutParams);


        // TableRow
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
        );
        tableRowParams.gravity = CENTER;


        // ImageBuutons
        TableRow.LayoutParams imageButtonParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
        );
        tableLayoutParams.gravity = CENTER;
        tableLayoutParams.bottomMargin = 5;
        tableLayoutParams.topMargin = 5;
        tableLayoutParams.leftMargin = 5;
        tableLayoutParams.rightMargin = 5;

        imageButtons = new ArrayList<ImageButton>();
        for(int i = 0; i < 12; i++) {
            ImageButton imageButton = new ImageButton(this);
            imageButton.setLayoutParams(imageButtonParams);
            imageButtons.add(imageButton);
        }


        // Set resources
        resources = new int[imageButtons.size()];
        for(int i = 0; i < 12; i++) {
            resources[i] = getResources().getIdentifier("a" + Integer.toString(i + 1), "drawable", getPackageName());
        }


        // Shuffle Flags and data
        setData();
        shuffleFlags();


        // Set structure
        tableRows = new ArrayList<TableRow>();
        for(int i = 0; i < 4; i++) {

            tableRows.add(new TableRow(this));
            tableRows.get(i).setLayoutParams(tableRowParams);

            for (int j = 0; j < 3; j++) {
                tableRows.get(i).addView(imageButtons.get(i * 3 + j));
            }

            tableLayout.addView(tableRows.get(i));
        }

        linearLayout.addView(textView);
        linearLayout.addView(tableLayout);


        // Show it all to the world
        setContentView(linearLayout);


        // Listeners
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleFlags();
            }
        });

        for(int i = 0; i < imageButtons.size(); i++) {
            imageButtons.get(i).setOnClickListener(new CustomOnClickListener(i) {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    if(order[this.index] == currentCountry) {
                        builder.setTitle("Correct!");
                    } else {
                        builder.setTitle("Incorrect! You have choosen " + countryNames.get(order[this.index]));
                    }
                    builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shuffleFlags();
                        }
                    });
                    builder.setIcon(resources[order[this.index]]);
                    builder.setMessage(countryDescription.get(order[this.index]));
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }


    private void shuffleFlags() {
        boolean[] check = new boolean[imageButtons.size()];
        order = new int[imageButtons.size()];
        for(int i = 0; i < imageButtons.size(); i++) {
            check[i] = true;
        }

        for(int i = 0; i < imageButtons.size(); i++) {
            int rand;
            while(true) {
                Random ran = new Random();
                rand = ran.nextInt(imageButtons.size());
                if(check[rand]) {
                    check[rand] = false;
                    break;
                }
            }
            

            order[i] = rand;
            imageButtons.get(i).setImageResource(resources[rand]);
        }

        while (true) {
            Random ran = new Random();
            int temp = order[ran.nextInt(imageButtons.size())];
            if(temp != currentCountry) {
                currentCountry = temp;
                textView.setText(countryNames.get(currentCountry));
                break;
            }
        }
    }



    private void setData() {
        countryNames = new ArrayList<String>();
        countryNames.add("Kazakhstan");
        countryNames.add("Japan");
        countryNames.add("China");
        countryNames.add("CSA");
        countryNames.add("Empire TES");
        countryNames.add("Starks");
        countryNames.add("ISIS");
        countryNames.add("France");
        countryNames.add("Roman Empire");
        countryNames.add("Holy Roman Empire");
        countryNames.add("Mobile Infantry");
        countryNames.add("Sparta");

        countryDescription = new ArrayList<String>();
        countryDescription.add("You know a lot about this country");
        countryDescription.add("The homeland of manga, anime, Miadzaki and hentai");
        countryDescription.add("A big country with a lot of people");
        countryDescription.add("Confideration of States of America");
        countryDescription.add("Located in the centre of continent. The capital is Empire City");
        countryDescription.add("Winter is coming.");
        countryDescription.add("A bunch of terrorists");
        countryDescription.add("Their people love bagettes.");
        countryDescription.add("Vici Vini Vidi or something like that");
        countryDescription.add("Not holy, not Roman and not empire. Total lie.");
        countryDescription.add("Startroopers! They protect mankind.");
        countryDescription.add("This is Sparta!!!");

    }

}













































