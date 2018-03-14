package com.example.amant.cookingjournal;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class AddRecipes extends AppCompatActivity {

    private EditText recipeName;
    private EditText recipeIngredients;
    private EditText recipeSteps;
    private EditText recipeUrl;
    private Spinner recipeCuisine;
    private MaterialRatingBar recipeRating;

    DatabaseReference dbRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipes);

        dbRecipes = FirebaseDatabase.getInstance().getReference("recipe");

        recipeName = (EditText) findViewById(R.id.recipeName);
        recipeIngredients = (EditText) findViewById(R.id.addIngredients);
        recipeSteps = (EditText) findViewById(R.id.addSteps);
        recipeUrl = (EditText) findViewById(R.id.addLink);
        recipeCuisine = (Spinner) findViewById(R.id.cuisineSpinner);
        recipeRating = (MaterialRatingBar) findViewById(R.id.recipeRating);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save){
            addRecipe();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addRecipe(){
        String name = recipeName.getText().toString().trim();
        String ingredients = recipeIngredients.getText().toString().trim();
        String steps = recipeSteps.getText().toString().trim();
        String cuisine = recipeCuisine.getSelectedItem().toString();
        String url = recipeUrl.getText().toString().trim();
        Float rating = recipeRating.getRating();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(ingredients) && !TextUtils.isEmpty(steps)){

            String id = dbRecipes.push().getKey();
            Recipes recipe = new Recipes(id, name, ingredients, steps, cuisine, url, rating);
            dbRecipes.child(id).setValue(recipe);

            Toast.makeText(this,"Recipe Added",Toast.LENGTH_SHORT).show();
        }else{
            if (name.matches(""))
            {               recipeName.setBackgroundResource(R.drawable.border_error);
                Toast.makeText(this, "Enter Recipe Name.",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                recipeName.setBackgroundResource(R.drawable.border);
            }
            if (ingredients.matches(""))
            {               recipeIngredients.setBackgroundResource(R.drawable.border_error);
                Toast.makeText(this, "Enter Ingredients.",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                recipeIngredients.setBackgroundResource(R.drawable.border);
            }
            if (steps.matches(""))
            {               recipeSteps.setBackgroundResource(R.drawable.border_error);
                Toast.makeText(this, "Enter Recipe Steps.",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                recipeSteps.setBackgroundResource(R.drawable.border);
            }
        }

    }
}

