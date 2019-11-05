package com.hp.marvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hp.marvel.swipeAdapter.Adapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SwipeView extends AppCompatActivity implements Configs{
    ViewPager viewPager;
    Adapter adapter;
    JSONArray jarray;
    JSONObject jobject;
    AsyncHttpClient client;
    RequestParams params;
    ArrayList<String> name;
    ArrayList<String>bio;
    ArrayList<String> realname;
    ArrayList<String>team;
    ArrayList<String>firstappearance;
    ArrayList<String>createdby;
    ArrayList<String>imageurl;
    String API_ENDPOINT="harishpadmanabh-HP/MORTALKOMBAT/db";
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view);

        //init
        client = new AsyncHttpClient();
        params = new RequestParams();
        //init array list
        name = new ArrayList<>();
        bio = new ArrayList<>();
        realname = new ArrayList<>();
        firstappearance = new ArrayList<>();
        imageurl = new ArrayList<>();
        team = new ArrayList<>();
        createdby = new ArrayList<>();
        getdataFromApi();

    }
    private void getdataFromApi() {

        client.get(BASE_URL + API_ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                Log.e("response", String.valueOf(response));
                try {

                    //storing whole response to jobject
                    jobject = response;
                    //  Log.e(content,"content");

                    //getting posts object
                    JSONObject postsObject = jobject.getJSONObject("posts");

                    JSONArray marvelArray = postsObject.getJSONArray("Marvel");
                    Toast.makeText(SwipeView.this, "Length = " + marvelArray.length(), Toast.LENGTH_LONG).show();

                    for (int i = 0; i < marvelArray.length(); i++) {
                        JSONObject singleMarvelObject = marvelArray.getJSONObject(i);

                        //get data from each object and store to arraylist
                        name.add(singleMarvelObject.getString("name"));
                        Log.e("name", singleMarvelObject.getString("name"));

                        bio.add(singleMarvelObject.getString("bio"));
                        Log.e("bio", singleMarvelObject.getString("bio"));

                        imageurl.add(singleMarvelObject.getString("imageurl"));
                        Log.e("image", singleMarvelObject.getString("imageurl"));


                    }


                    adapter = new Adapter(name,imageurl, SwipeView.this);

                    viewPager = findViewById(R.id.viewPager);
                    viewPager.setAdapter(adapter);
                    viewPager.setPadding(130, 0, 130, 0);



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SwipeView.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
