package com.example.codeit.team_24;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rohit33 on 20-01-2017.
 */

public class News extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.news, container, false);


   setText();

        return rootView;
}

    void setText(){
        final TextView mTextView = (TextView) getActivity().findViewById(R.id.text);

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="http://api.androidhive.info/volley/string_response.html";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                      //  mTextView.setText("Response is "+response.substring(0,500));
                        Log.d("njs",response);
                    }


                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError e){
               // mTextView.setText("That didnt work");
                Log.e("bdhj",e.toString());
            }
        });

        queue.add(stringRequest);
    }

}
