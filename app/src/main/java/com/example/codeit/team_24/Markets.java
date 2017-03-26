package com.example.codeit.team_24;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Rohit33 on 06-01-2017.
 */

public class Markets extends Fragment {
    String resultstr[][]=new String[17][16];

ArrayAdapter mForecastAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);}

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.action_refresh, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updatedata();
            return true;
        }


        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.toString();

    }

    @Override
    public void onStart(){
        super.onStart();
       updatedata();

    }

    public void updatedata(){
        Log.v("Team_24","Entered updatedata");
        FetchData fetchData=new FetchData();
        fetchData.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<String> weekForecast = new ArrayList<String>();
        View rootView = inflater.inflate(R.layout.markets, container, false);

        mForecastAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_market, R.id.list_item_market_textview,weekForecast) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        /*

         mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_market,
                R.id.list_item_market_textview,
                weekForecast

        );
*/
        ListView listView = (ListView) rootView.findViewById(R.id.listView_market);
        listView.setAdapter(mForecastAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
               /* Context context = getContext();
                CharSequence text = forecastdata[i];
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                //toast.setGravity(Gravity.TOP|Gravity.LEFT,0,0);
                toast.show();*/
                Intent intent=new Intent(getContext(),DetailActivity.class);
                intent.putExtra(intent.EXTRA_TEXT,resultstr[i]);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchData extends AsyncTask<Void,Void,String[][]>{
                        public final String LOG_TAG=FetchData.class.getSimpleName();

                        HttpURLConnection urlConnection;
                        BufferedReader reader;

                        public String [][] getDatafromJson(String jsonstring)throws JSONException{

                            JSONObject jsondata = new JSONObject(jsonstring);
                            JSONObject query=jsondata.getJSONObject("query");
                            JSONObject results=query.getJSONObject("results");
                            JSONArray quote=results.getJSONArray("quote");

                            for(int i=0;i<17;i++){
                                JSONObject stock=quote.getJSONObject(i);
                                String name=stock.getString("Name");
                                String ask=stock.getString("Ask");
                                String change=stock.getString("Change");
                                String percentChange=stock.getString("PercentChange");
                                String symbol=stock.getString("symbol");
                                String dayr=stock.getString("DaysRange");
                                String close=stock.getString("PreviousClose");
                                String open=stock.getString("Open");
                                String vol=stock.getString("Volume");
                                String dayh=stock.getString("DaysHigh");
                                String dayl=stock.getString("DaysLow");
                                String per=stock.getString("PERatio");
                                String div=stock.getString("DividendYield");
                                String curr=stock.getString("Currency");
                                String yrr=stock.getString("YearRange");

                                resultstr[i][0]=name;
                                resultstr[i][1]=ask;
                                resultstr[i][2]=change;
                                resultstr[i][3]=percentChange;
                                resultstr[i][4]=symbol;
                                resultstr[i][5]=dayr;
                                resultstr[i][6]=close;
                                resultstr[i][7]=open;
                                resultstr[i][8]=vol;
                                resultstr[i][9]=dayh;
                                resultstr[i][10]=dayl;
                                resultstr[i][11]=per;
                                resultstr[i][12]=div;
                                resultstr[i][13]=curr;
                                resultstr[i][14]=yrr;
                                resultstr[i][15]= (new Integer(i)).toString();

                            }
                            return resultstr;

                        }


        @Override
                        public String[][] doInBackground(Void...params) {
                            String JsonStr;
                            try{
                                URL url=new URL("http://query.yahooapis.com/v1/public/yql?q=select%20symbol,Ask,Name,Change,PercentChange,DaysRange,PreviousClose,Open,Volume,DaysHigh,DaysLow,PERatio,DividendYield,Currency,YearRange%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22AAPL%22,%22TWTR%22,%22AA%22,%22BAC%22,%22KO%22,%22XOM%22,%22FB%22,%22F%22,%22GM%22,%22GOOGL%22,%22HPQ%22,%22IBM%22,%22JPM%22,%22MCD%22,%22MSFT%22,%22SBUX%22,%22TSLA%22)&format=json&env=http://datatables.org/alltables.env");
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


            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String[][] result) {
            if(result!=null){
                mForecastAdapter.clear();
                for(String[] dayForecast:result){
                    String listdata=dayForecast[0]+'\t'+'\t'+dayForecast[1]+'\t'+'\t'+dayForecast[2]+'\t'+'\t'+'('+dayForecast[3]+')';
                    mForecastAdapter.add(listdata);
                }

            }
        }
    }

}
