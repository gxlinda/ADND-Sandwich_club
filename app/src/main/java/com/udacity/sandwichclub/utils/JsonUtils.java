package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Try to parse the JSON string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        try {

            // Create a JSONObject from the JSON string array
            JSONObject baseJsonObject = new JSONObject(json);
            JSONObject name = baseJsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = baseJsonObject.getString("placeOfOrigin");

            String description = baseJsonObject.getString("description");

            String image = baseJsonObject.getString("image");

            JSONArray ingredients = baseJsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            Sandwich actualSandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

            return actualSandwich;


        } catch (JSONException e) {
            // If an error is thrown catch the exception here, so the app doesn't crash, and we print it to the log
            Log.e("JsonUtils", "Problem parsing the sandwiche JSON", e);
        }
        return null;
    }
}
