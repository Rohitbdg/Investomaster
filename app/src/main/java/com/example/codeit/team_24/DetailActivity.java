package com.example.codeit.team_24;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Rohit33 on 07-01-2017.
 */
public class DetailActivity extends ActionBarActivity {
    public static String symbol=null,company_name=null;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public static int list_no;
    View cview;

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog datePicker=new DatePickerDialog(this,
                    myDateListener, year, month, day);
            datePicker.updateDate(2017,0,1);
            return datePicker;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    String[] pardata=new String[3];
                    String date1=showDate(arg1, arg2+1, arg3);
                    pardata[0]=date1;
                    pardata[1]=symbol;
                    pardata[2]=company_name;
                    Intent intent=new Intent(getApplicationContext(),Particulardate_Data.class);
                    intent.putExtra(intent.EXTRA_TEXT,pardata);
                    startActivity(intent);
                }
            };

    private String  showDate(int year, int month, int day) {
        StringBuilder sb = new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day);
      /*  dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)); */
        String datess = sb.toString();
       // Toast.makeText(getApplicationContext(),datess, Toast.LENGTH_LONG).show();
        return datess;

    }

/*

*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             setDate(cview);
            }
        });



    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        private String mSymbolStr[]=new String[15];
        int n;


        private static final String LOG_TAG = DetailFragment.class.getSimpleName();


        @Override
        public void onStart(){
            super.onStart();
            getdata();
        }

        public void getdata(){
            FetchhistData histdate=new FetchhistData();
            histdate.execute(mSymbolStr[4]);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            //View rootView = inflater.inflate(R.layout.content_detail, container, false);
            View rootView = inflater.inflate(R.layout.table_detail, container, false);

            Intent intent = getActivity().getIntent();



            mSymbolStr = intent.getStringArrayExtra(intent.EXTRA_TEXT);

            symbol=mSymbolStr[4];
            company_name=mSymbolStr[0];
            list_no=Integer.parseInt(mSymbolStr[15]);

            ((TextView) rootView.findViewById(R.id.detail_name)).setText(mSymbolStr[0]);
            ((TextView) rootView.findViewById(R.id.detail_ask)).setText(mSymbolStr[1]);
            ((TextView) rootView.findViewById(R.id.detail_change)).setText(mSymbolStr[2]);
            ((TextView) rootView.findViewById(R.id.detail_perchange)).setText(mSymbolStr[3]);
            ((TextView) rootView.findViewById(R.id.detail_symbol)).setText(mSymbolStr[4]);
            ((TextView) rootView.findViewById(R.id.detail_dayr)).setText(mSymbolStr[5]);
            ((TextView) rootView.findViewById(R.id.detail_close)).setText(mSymbolStr[6]);
            ((TextView) rootView.findViewById(R.id.detail_open)).setText(mSymbolStr[7]);
            ((TextView) rootView.findViewById(R.id.detail_vol)).setText(mSymbolStr[8]);
            ((TextView) rootView.findViewById(R.id.detail_high)).setText(mSymbolStr[9]);
            ((TextView) rootView.findViewById(R.id.detail_low)).setText(mSymbolStr[10]);
            ((TextView) rootView.findViewById(R.id.detail_per)).setText(mSymbolStr[11]);
            ((TextView) rootView.findViewById(R.id.detail_div)).setText(mSymbolStr[12]);
            ((TextView) rootView.findViewById(R.id.detail_curr)).setText(mSymbolStr[13]);
            ((TextView) rootView.findViewById(R.id.detail_yrr)).setText(mSymbolStr[14]);


            ImageButton Starbutton = (ImageButton) rootView.findViewById(R.id.imageButton);



        /*
           PointsGraphSeries<DataPoint> series1 = new PointsGraphSeries<>();
           for(j=0; j<n; j++)
           {
               try
               {
                   DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                   DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
                   String inputDateStr=datedata[j];
                   Date date = inputFormat.parse(inputDateStr);
                   outdatedata[j] = outputFormat.format(date);
                   DateFormat formatter;
                   Date dates;
                   formatter = new SimpleDateFormat("MM/dd/yyyy");
                   outdatedatas[j] = (Date)formatter.parse(outdatedata[j]);
                   series1.appendData(new DataPoint(outdatedatas[j], Double.parseDouble(askdata[j])),true, 150);

               } catch (Exception e)
               {
                   //
               }

           } */
            GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 152),
                    new DataPoint(1, 185),
                    new DataPoint(2, 397),
                    new DataPoint(3, 211),
                    new DataPoint(5, 156),
                    new DataPoint(6, 270),
                    new DataPoint(7, 490),
                    new DataPoint(8, 190),

            });
            graph.addSeries(series);

            return rootView;


        }




        public class FetchhistData extends AsyncTask<String,Void,Void> {

            public final String LOG_TAG=FetchhistData.class.getSimpleName();


            public Void getDatafromJson(String jsonstring)throws JSONException {
                JSONObject datatr=new JSONObject(jsonstring);
                JSONObject datatable=datatr.getJSONObject("datatable");
                JSONArray datalist=datatable.getJSONArray("data");
                n=datalist.length();

String datedata[]=new String[n];
                String askdata[]=new String[n];
                for(int i=0;i<n;i++){
                    JSONArray singledata=datalist.getJSONArray(i);
                    datedata[i]=singledata.getString(0);
                    askdata[i]=singledata.getString(1);}


return null;
            }


            @Override
            public Void doInBackground(String...params) {
                String JsonStr;
                HttpURLConnection urlConnection=null;
                BufferedReader reader=null;

                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                String start_date=sharedPreferences.getString("start_date","2016-12-01");

                String apikey="EmxX4Wdgv3itmyEJoNv9";
                String today_date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                String columns="date,close";
                try{
                    final String BASE_URL="https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?";
                    final String QUERY_PARAM="ticker";
                    final String START_DATE="date.gte";
                    final String TODAY_DATE="date.lte";
                    final String COLUMNS="qopts.columns";
                    final String API_KEY="api_key";

                    Uri builtUri= Uri.parse(BASE_URL).buildUpon().
                            appendQueryParameter(START_DATE,start_date).
                            appendQueryParameter(TODAY_DATE,today_date).
                            appendQueryParameter(QUERY_PARAM,params[0]).
                            appendQueryParameter(COLUMNS,columns).
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
                try {
                    return getDatafromJson(JsonStr);
                }catch (JSONException e){

                }
                return null;
            }

        }




    }
}

