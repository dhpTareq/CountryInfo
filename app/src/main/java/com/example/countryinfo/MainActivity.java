package com.example.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView commonName, officialName, capital, region, subRegion;
    EditText searchCountry;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commonName = findViewById(R.id.common_name);
        officialName = findViewById(R.id.official_name);
        capital = findViewById(R.id.capital);
        region = findViewById(R.id.region);
        subRegion = findViewById(R.id.sub_region);
        searchCountry = findViewById(R.id.search_country);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country = searchCountry.getText().toString();
                String url = "https://restcountries.com/v3.1/name/" + country;
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            JSONObject name = jsonObject.getJSONObject("name");
                            String commonNameJson = name.getString("common");
                            String officialNameJson = name.getString("official");
                            commonName.setText(commonNameJson);
                            officialName.setText(officialNameJson);

                            JSONArray capitals = jsonObject.getJSONArray("capital");
                            String capitalJson = capitals.getString(0);
                            capital.setText(capitalJson);

                            String regionJson = jsonObject.getString("region");
                            region.setText(regionJson);

                            String subRegionJson = jsonObject.getString("subregion");
                            subRegion.setText(subRegionJson);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });


    }
}