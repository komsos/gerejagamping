package com.kerjapraktik.androidgereja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class DetailBeritaActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String idberita = getIntent().getStringExtra("idBerita");
        String featured = getIntent().getStringExtra("featured");
        String judul = getIntent().getStringExtra("judul");
        String isi = getIntent().getStringExtra("isi");

        imageView = (ImageView) findViewById(R.id.imageView);

        getSupportActionBar().setTitle(Html.fromHtml(judul));
        TextView isiBerita = (TextView) findViewById(R.id.isiBerita);
        isiBerita.setText(Html.fromHtml(isi));

        //load image
        loadImage(featured);
    }

    void loadImage(String id){
        JsonObjectRequest request = new JsonObjectRequest(
                "https://www.gerejagamping.org/wp-json/wp/v2/media/" + id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject guid = response.getJSONObject("guid");
                            String url = guid.getString("rendered");
                            Glide.with(getApplicationContext())
                                    .load(url)
                                    .crossFade()
                                    .centerCrop()
                                    .into(imageView);
                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MainApplication.getsInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.onBackPressed();
                break;
        }

        return true;
    }
}
