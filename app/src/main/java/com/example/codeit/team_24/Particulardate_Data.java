package com.example.codeit.team_24;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.codeit.team_24.R.id.container;

/**
 * Created by Rohit33 on 08-01-2017.
 */

public class Particulardate_Data extends ActionBarActivity{
public static boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ParticularFragment())
                    .commit();
        }

        setContentView(R.layout.par_detail);




    }



    public static class ParticularFragment extends Fragment{

        String[] intdata=new String[3];
        final String [] qdata=new String[2];
        private String[] resutldata=new String[9];
        View rootView;

        @Override
        public void onStart() {
            super.onStart();
            getcaldata();



        }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
            Intent intent = getActivity().getIntent();
            intdata = intent.getStringArrayExtra(intent.EXTRA_TEXT);
            qdata[0]=intdata[0];
            qdata[1]=intdata[1];

        View rootview=inflater.inflate(R.layout.particular_date,container,false);
/*


*/

            ((TextView) rootview.findViewById(R.id.detail_pname)).setText(intdata[2]);

            return rootview;
    }







    public String[] getDatafromJson(String jsonstr)throws JSONException{
        JSONObject jsonObject=new JSONObject(jsonstr);
            JSONObject datatable=jsonObject.getJSONObject("datatable");
            JSONArray data=datatable.getJSONArray("data");

            JSONArray datas=data.getJSONArray(0);


            for(int i=0;i<9;i++){
                resutldata[i]=datas.getString(i);
            Log.v("dnaajd",resutldata[i]);
        }


return resutldata;

    }

    public void getcaldata() {
        Fetchpardata fetchpardata=new Fetchpardata();
        fetchpardata.execute(qdata);
    }


    public class Fetchpardata extends AsyncTask<String[],Void,String[]>{
        public final String LOG_TAG= Fetchpardata.class.getSimpleName();
        String JsonStr=null;

        @Override
        public String[] doInBackground(String[]...params){

            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            String apikey="EmxX4Wdgv3itmyEJoNv9";

            try{
                final String BASE_URL="https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?";
                final String QUERY_PARAM1="date";
                final String QUERY_PARAM2="ticker";
                final String API_KEY="api_key";

                Uri builtUri= Uri.parse(BASE_URL).buildUpon().
                        appendQueryParameter(QUERY_PARAM1,params[0][0]).
                        appendQueryParameter(QUERY_PARAM2,params[0][1]).
                        appendQueryParameter(API_KEY,apikey).build();


                URL url=new URL(builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }

                if (buffer.length() == 0) {
                    return null;
                }

                JsonStr = buffer.toString();

                Log.v(LOG_TAG,"JSON QUERY STRING: "+JsonStr);

            }catch (IOException e){
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;

            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

        try{
           return getDatafromJson(JsonStr);

        }
        catch(JSONException e){
            Log.e(LOG_TAG,e.getMessage(),e);
            e.printStackTrace();
        }
            return null;

        }

        @Override
        protected void onPostExecute(String [] str) {
            TextView code=(TextView)getActivity().findViewById(R.id.detail_pcode);
            TextView date=(TextView)getActivity().findViewById(R.id.detail_pdate);
            TextView close=(TextView)getActivity().findViewById(R.id.detail_pclose);
            TextView open=(TextView)getActivity().findViewById(R.id.detail_popen);
            TextView high=(TextView)getActivity().findViewById(R.id.detail_phigh);
            TextView low=(TextView)getActivity().findViewById(R.id.detail_plow);
            TextView vol=(TextView)getActivity().findViewById(R.id.detail_pvol);
            TextView div=(TextView)getActivity().findViewById(R.id.detail_pdiv);
            TextView split=(TextView)getActivity().findViewById(R.id.detail_psplit);

            code.setText(str[0]);
            date.setText(str[1]);
            open.setText(str[2]);
            high.setText(str[3]);
            low.setText(str[4]);
            close.setText(str[5]);
            vol.setText(str[6]);
            div.setText(str[7]);
            split.setText(str[8]);

            /*

            ((TextView) rootview.findViewById(R.id.detail_pcode)).setText(resutldata[0]);
            ((TextView) rootview.findViewById(R.id.detail_pdate)).setText(resutldata[1]);
            ((TextView) rootview.findViewById(R.id.detail_pclose)).setText(resutldata[3]);
            ((TextView) rootview.findViewById(R.id.detail_popen)).setText(resutldata[2]);
            ((TextView) rootview.findViewById(R.id.detail_pvol)).setText(resutldata[6]);
            ((TextView) rootview.findViewById(R.id.detail_phigh)).setText(resutldata[3]);
            ((TextView) rootview.findViewById(R.id.detail_plow)).setText(resutldata[4]);
            ((TextView) rootview.findViewById(R.id.detail_pdiv)).setText(resutldata[7]);
            ((TextView) rootview.findViewById(R.id.detail_psplit)).setText(resutldata[8]);
            ((TextView)rootview.findViewById(R.id.detail_pdate)).setText(resutldata[5]);
            ((TextView)rootview.findViewById(R.id.detail_phigh)).setText( resutldata[3]);
*/
        }



        }

    }
}

