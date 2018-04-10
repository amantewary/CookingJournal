package com.example.amant.cookingjournal;


import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RecipeDetails extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    /*
        1. Used a stock image for adding in Collapsing Toolbar.
        Image Source: https://pietrerossepalinuro.it/wp-content/uploads/2018/02/spaghetti-ingredients-2378728_960_720-960x507.jpg

        2. Used a Third-Party Rating Bar.
        https://github.com/DreaminginCodeZH/MaterialRatingBar
        For license information check the README file.
     */
    MaterialRatingBar recipeRating;
    TextView recipeIngredients;
    TextView recipeSteps;
    TextView recipeUrl;
    String recipeId;
    String title;
    String subtitle;
    FloatingActionButton fab;
    NestedScrollView menuSheet;
    CardView schedulerButton;
    CardView editButton;
    CardView deleteButton;
    SubtitleCollapsingToolbarLayout collapsingToolbarLayout;
    BottomSheetBehavior menuBehavior;
    Recipes recipe;
    Intent intent;

    DatabaseReference recipeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        /*
            Used a Third-Party Toolbar Wrapper with Subtitle Support.
            https://github.com/HendraAnggrian/collapsingtoolbarlayout-subtitle
            For license information check the README file.
         */
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        recipeIngredients = findViewById(R.id.recipeDetailIngredients);
        recipeSteps = findViewById(R.id.recipeDetailSteps);
        recipeUrl = findViewById(R.id.recipeDetailLink);
        recipeRating = findViewById(R.id.recipeDetailRating);
        recipeRef = FirebaseDatabase.getInstance().getReference("recipe");

        intent = getIntent();
        recipeId = intent.getStringExtra(MainActivity.RECIPE_ID);
        collapsingToolbarLayout.setExpandedSubtitleTextAppearance(R.style.TextAppearance_AppCompat_Subhead);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuSheet = findViewById(R.id.menu_sheet);
        menuBehavior = BottomSheetBehavior.from(menuSheet);
        fab = findViewById(R.id.fab2);
        menuBehavior.setPeekHeight(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    menuBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    menuBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schedulerButton = findViewById(R.id.scheduler_card);
        schedulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getFragmentManager(), "time picker");
                menuBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        editButton = findViewById(R.id.edit_card);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetails.this, EditRecipe.class);
                intent.putExtra("recipeid", recipeId);
                startActivity(intent);
                menuBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        deleteButton = findViewById(R.id.delete_card);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(RecipeDetails.this);
                deleteAlert.setTitle("Delete Recipe");
                deleteAlert.setMessage("Are you sure?");
                deleteAlert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recipeRef.child(recipeId).removeValue();
                        finish();
                    }
                });
                deleteAlert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleteAlert.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recipeRef.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipe = dataSnapshot.getValue(Recipes.class);
                title = recipe.getRecipeTitle();
                subtitle = recipe.getRecipeCuisine();
                collapsingToolbarLayout.setTitle(title);
                collapsingToolbarLayout.setSubtitle(subtitle);
                recipeIngredients.setText(recipe.getRecipeIngredients());
                recipeSteps.setText(recipe.getRecipeSteps());
                recipeUrl.setText(recipe.getRecipeUrl());
                recipeRating.setRating(recipe.getRecipeRating());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        /*
            Setting the Calendar instance to the time picked
            using TimePicker Dialog
         */
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calender.set(Calendar.MINUTE, minute);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        /*
            Converting Time to millisecond for setting it in AlarmManager &
            Creating a dynamic requestId using current system time.
         */
        long timeInMillis = calender.getTimeInMillis();
        final int requestId = (int) System.currentTimeMillis();
        /*
            Setting an AlarmManager and passing the intent to NotificationReceiver.java
         */
        AlarmManager cookingAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(RecipeDetails.this, NotificationReceiver.class);
        intent.putExtra("recipe", title);
        PendingIntent reminder = PendingIntent.getBroadcast(this, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        cookingAlarm.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, reminder);
        Toast.makeText(this, "Voila! We will remind you when its time to cook.", Toast.LENGTH_LONG).show();
    }
}
