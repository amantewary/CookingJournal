package com.example.amant.cookingjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final String RECIPE_TITLE = "recipeTitle";
    public static final String RECIPE_INGREDIENTS = "recipeIngrdients";
    public static final String RECIPE_STEPS = "recipeSteps";
    public static final String RECIPE_CUISINE = "recipeCuisine";
    public static final String RECIPE_RATING = "recipeRating";
    public static final String RECIPE_URL = "recipeUrl";

    RecyclerView recipeListView;
    List<Recipes> recipeList;
    RecipeList recipeAdapter;
    EditText searchBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe");
        Toolbar toolbar = findViewById(R.id.toolbar);
        recipeListView = findViewById(R.id.recipeList);
        recipeList = new ArrayList<>();
        searchBar = findViewById(R.id.searchBar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecipes.class);
                startActivity(intent);
            }
        });
//TODO: [REMOVE] Old listView onClickListner().
//        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Recipes recipes = recipeList.get(position);
//                Intent intent = new Intent(MainActivity.this, RecipeDetails.class);
//                intent.putExtra(RECIPE_TITLE, recipes.getRecipeTitle());
//                intent.putExtra(RECIPE_INGREDIENTS, recipes.getRecipeIngredients());
//                intent.putExtra(RECIPE_STEPS, recipes.getRecipeSteps());
//                intent.putExtra(RECIPE_CUISINE, recipes.getRecipeCuisine());
//                intent.putExtra(RECIPE_RATING, recipes.getRecipeRating());
//                intent.putExtra(RECIPE_URL, recipes.getRecipeUrl());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipes recipes = recipeSnapshot.getValue(Recipes.class);
                    recipeList.add(recipes);
                }
                recipeAdapter = new RecipeList(MainActivity.this, recipeList);
                recipeListView.setAdapter(recipeAdapter);
                recipeListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
