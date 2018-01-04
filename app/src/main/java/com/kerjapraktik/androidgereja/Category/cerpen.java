package com.kerjapraktik.androidgereja.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kerjapraktik.androidgereja.MainApplication;
import com.kerjapraktik.androidgereja.R;
import com.kerjapraktik.androidgereja.adapters.BeritaAdapter;
import com.kerjapraktik.androidgereja.berita;
import com.kerjapraktik.androidgereja.models.BeritaModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class cerpen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    BeritaAdapter adapter;
    ArrayList<BeritaModel> list;
    ProgressDialog pd;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationMenuItemView navItem;

    String post = "https://www.gerejagamping.org/wp-json/wp/v2/posts";
    String category = "https://www.gerejagamping.org/wp-json/wp/v2/posts?categories=2&orderby=date&order=desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        mDrawer = (DrawerLayout)findViewById(R.id.activity_coba_nav);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer, R.string.open, R.string.close);

        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView view = (NavigationView)findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {

                selectDrawerItem(menuItem);
                mDrawer.closeDrawers();

                return true;
            }

        });

        recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mySwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        list = new ArrayList<>();
        adapter = new BeritaAdapter(getApplicationContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        getData(category);
    }
    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.berita:
                Intent berita = new Intent(cerpen.this, berita.class);
                startActivity(berita);
                finish();
                break;
            case R.id.renungan:
                Intent renungan = new Intent(cerpen.this, renungan.class);
                startActivity(renungan);
                finish();
                break;
            case R.id.khotbah:
                Intent khotbah = new Intent(cerpen.this, khotbah.class);
                startActivity(khotbah);
                finish();
                break;
            case R.id.cerpen:
                Toast.makeText(cerpen.this,"sudah dihalaman",Toast.LENGTH_SHORT).show();
                break;

        }
    }



    void getData(String url2){
        list.clear();
        pd = new ProgressDialog(cerpen.this);
        pd.setMessage("Please wait....");
        pd.show();

        final JsonArrayRequest request = new JsonArrayRequest(
                url2,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //200 ok ->> parsing json
                        try{

                            int limit = 10;
                            if (response.length() < 10)
                                limit = response.length();

                            //limit list berita
                            for (int i = 0 ; i < limit; i++){


                                JSONObject jsonObject = response.getJSONObject(i);
                                BeritaModel beritaModel = new BeritaModel();
                                beritaModel.setIdBerita(jsonObject.getInt("id"));
                                beritaModel.setTanggalBerita(jsonObject.getString("date"));

                                JSONObject title = jsonObject.getJSONObject("title");
                                beritaModel.setJudulBerita(title.getString("rendered"));
                                JSONObject content = jsonObject.getJSONObject("content");
                                beritaModel.setIsiBerita(content.getString("rendered"));
                                beritaModel.setFeatured_image(jsonObject.getInt("featured_media"));

                                //list.add(beritaModel);
                                loadImage(String.valueOf(jsonObject.getInt("featured_media")), beritaModel);
                            }
                        }catch (Exception e){

                        }
                       /* //pd.dismiss();
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }*/
                        //kasitau adapter, ada data yg berubah
                        //adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //500 404 403
                        pd.dismiss();
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Log.d("errorvolley", error.toString());
                    }
                }
        );
        MainApplication.getsInstance().addToRequestQueue(request);
    }

    void loadImage(String id, final BeritaModel beritaModel){
        JsonObjectRequest request = new JsonObjectRequest(
                "https://www.gerejagamping.org/wp-json/wp/v2/media/" + id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject guid = response.getJSONObject("guid");
                            String url = guid.getString("rendered");
                            beritaModel.setUrlImage(url);
                            list.add(beritaModel);
                        }catch (Exception e){

                        }
                        adapter.notifyDataSetChanged();
                        pd.dismiss();
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
        MainApplication.getsInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)){

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData(category);
    }
}
