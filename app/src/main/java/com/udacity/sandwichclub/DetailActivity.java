package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView labelMainName;
    TextView tvAlsoKnownAs;
    TextView tvPlaceOfOrigin;
    TextView tvIngredients;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Find the proper views
        labelMainName = findViewById(R.id.label_main_name);
        tvAlsoKnownAs = findViewById(R.id.tv_also_known_as);
        tvPlaceOfOrigin = findViewById(R.id.tv_origin);
        tvIngredients = findViewById(R.id.tv_ingredients);
        tvDescription = findViewById(R.id.tv_description);

        //Fill the textviews with data
        labelMainName.setText(sandwich.getMainName());
        tvAlsoKnownAs.setText(checkIfDataMissing(makeCommaSeparatedList(sandwich)));
        tvPlaceOfOrigin.setText(checkIfDataMissing(sandwich.getPlaceOfOrigin()));
        tvIngredients.setText(checkIfDataMissing(makeLineBreakInList(sandwich)));
        tvDescription.setText(checkIfDataMissing(sandwich.getDescription()));
    }

    private String checkIfDataMissing(String stringToCheck){
        if (stringToCheck.equals("")){
            return getString(R.string.detail_missing_data);
        } else {
            return stringToCheck;
        }
    }

    private String makeCommaSeparatedList(Sandwich sandwich){
        StringBuilder commaSeparatedListBuilder = new StringBuilder();

        //Go through the alsoKnownAs list
        for (int i = 0; i < sandwich.getAlsoKnownAs().size();i++) {
            commaSeparatedListBuilder.append(sandwich.getAlsoKnownAs().get(i));

            //if this is not the last element, add a comma
            if (i != sandwich.getAlsoKnownAs().size()-1 ) {
                commaSeparatedListBuilder.append(", ");
            }
        } return commaSeparatedListBuilder.toString();
    }

    private String makeLineBreakInList(Sandwich sandwich){
        StringBuilder lineBreakListBuilder = new StringBuilder();

        //Go through the ingredients list
        for (int i = 0; i < sandwich.getIngredients().size();i++)  {
            lineBreakListBuilder.append(sandwich.getIngredients().get(i));

            //if this is not the last element, add a line break
            if (i != sandwich.getIngredients().size()-1) {
                lineBreakListBuilder.append("\n");
            }

        } return lineBreakListBuilder.toString();
    }
}
