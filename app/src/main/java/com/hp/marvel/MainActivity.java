package com.hp.marvel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.loopj.android.http.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements Configs {
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    ArrayList<String> name;
    ArrayList<String>bio;
    ArrayList<String> realname;
    ArrayList<String>team;
    ArrayList<String>firstappearance;
    ArrayList<String>createdby;
    ArrayList<String>imageurl;
    String API_ENDPOINT="harishpadmanabh-HP/MORTALKOMBAT/db";
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        AsyncHttpClient client = new AsyncHttpClient();
        params = new RequestParams();
        //init array list
        name = new ArrayList<>();
        bio = new ArrayList<>();
        realname = new ArrayList<>();
        firstappearance = new ArrayList<>();
        imageurl = new ArrayList<>();
        team = new ArrayList<>();
        createdby = new ArrayList<>();


        recyclerView=findViewById(R.id.rv);



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
                    Toast.makeText(MainActivity.this, "Length = " + marvelArray.length(), Toast.LENGTH_LONG).show();

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
                    
                    
                    setupRecyclerView(recyclerView,MainActivity.this,
                                      name,bio,imageurl);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });


    }

    private void setupRecyclerView(RecyclerView recyclerView, Context context, ArrayList<String> name, ArrayList<String> bio, ArrayList<String> imageurl) {
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);

        recyclerView.setAdapter(new MarvelAdapter(name,bio,imageurl,MainActivity.this));



    }

    class MarvelAdapter extends RecyclerView.Adapter<MarvelAdapter.MarvelVH> {
        ArrayList<String> name;
        ArrayList<String>bio;
        ArrayList<String> imageurl;
        Context context;

        public MarvelAdapter(ArrayList<String> name, ArrayList<String> bio, ArrayList<String> imageurl, Context context) {
            this.name = name;
            this.bio = bio;
            this.imageurl = imageurl;
            this.context = context;
        }

        @NonNull
        @Override
        public MarvelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
 

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.singleitem, parent, false);
            return new MarvelVH(itemView);



        }

        @Override
        public void onBindViewHolder(@NonNull MarvelVH holder, int position) {

            holder.dispname.setText(name.get(position));
            Glide.with(context).load(imageurl.get(position).trim()).into(holder.disppic);


        }

        @Override
        public int getItemCount() {
            return name.size();
        }

        class MarvelVH extends RecyclerView.ViewHolder{
            ImageView disppic;
            TextView dispname;
            View mview;

            public MarvelVH(@NonNull View itemView) {
                super(itemView);
                mview=itemView;
                disppic=itemView.findViewById(R.id.displaypic);
                dispname=itemView.findViewById(R.id.dispname);







            }
        }
}




}

