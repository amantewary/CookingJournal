package com.example.amant.cookingjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RecipeDetails extends AppCompatActivity {

    TextView recipeIngredients;
    TextView recipeSteps;
    TextView recipeUrl;
    String reviewId;
    FloatingActionButton fab;
    NestedScrollView menuSheet;
    ConstraintLayout schedulerButton;
    ConstraintLayout editButton;
    SubtitleCollapsingToolbarLayout collapsingToolbarLayout;
    BottomSheetBehavior menuBehavior;
    MaterialRatingBar recipeRating;
//    DatePickerDialog dpd;
//    java.util.Calendar now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.RECIPE_TITLE);
        String subtitle = intent.getStringExtra(MainActivity.RECIPE_CUISINE);
        /*
            Used Third-Party Toolbar Wrapper with Subtitle Support.
            https://github.com/HendraAnggrian/collapsingtoolbarlayout-subtitle
            For license information check the README file.
         */
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setSubtitle(subtitle);
        collapsingToolbarLayout.setExpandedSubtitleTextAppearance(R.style.TextAppearance_AppCompat_Subhead);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuSheet = findViewById(R.id.menu_sheet);
        menuBehavior = BottomSheetBehavior.from(menuSheet);
        fab = findViewById(R.id.fab2);
        recipeIngredients = findViewById(R.id.recipeDetailIngredients);
        recipeSteps = findViewById(R.id.recipeDetailSteps);
        recipeUrl = findViewById(R.id.recipeDetailLink);
        recipeRating = findViewById(R.id.recipeDetailRating);

        String ingredients = intent.getStringExtra(MainActivity.RECIPE_INGREDIENTS);
        String steps = intent.getStringExtra(MainActivity.RECIPE_STEPS);
        String link = intent.getStringExtra(MainActivity.RECIPE_URL);
        /*
        Referred here for understanding how to parse float value from one activity to another.
        https://stackoverflow.com/questions/24492037/send-float-from-activity-to-activity
         */
        Bundle bundle =intent.getExtras();
        Float rating = bundle.getFloat(MainActivity.RECIPE_RATING);

        recipeIngredients.setText(ingredients);
        recipeSteps.setText(steps);
        recipeUrl.setText(link);
        recipeRating.setRating(rating);
        menuBehavior.setPeekHeight(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    menuBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    menuBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schedulerButton = findViewById(R.id.scheduler_layout);
//        scheduler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                now = Calendar.getInstance();
//                dpd = DatePickerDialog.newInstance(
//                        RecipeDetails.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd.show(getFragmentManager(), "Datepickerdialog");
//            }
//        });
        editButton = findViewById(R.id.edit_layout);
        reviewId = intent.getStringExtra(MainActivity.RECIPE_ID);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetails.this,EditRecipe.class);
                intent.putExtra("reviewid", reviewId);
                Log.e("Here",""+ reviewId);
                startActivity(intent);
                menuBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
