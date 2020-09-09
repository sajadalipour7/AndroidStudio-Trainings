package com.example.whatsthewheather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText cityName;
    private TextView resultTextView;
    public void findWeather(View view){
//        Log.i("cityName",cityName.getText().toString());
        InputMethodManager mgr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0);
        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            DownloadTask task=new DownloadTask();
            String apiKey="297248e88fe35a4895cc07f50154da8e";
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid="+apiKey);
        }catch (UnsupportedEncodingException e){
            Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=findViewById(R.id.cityName);
        resultTextView=findViewById(R.id.resultTextView);
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpsURLConnection urlConnection=null;
            try{
                url=new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            }catch (Exception e) {
//                Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try{
                String message="";
//                resultTextView.setText(result);
                JSONObject jsonObject=new JSONObject(result);
                String weatherInfo=jsonObject.getString("weather");
//                Log.i("Weather content",weatherInfo);
                JSONArray arr=new JSONArray(weatherInfo);
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart=arr.getJSONObject(i);
                    String main="";
                    String description="";
                    main=jsonPart.getString("main");
                    description=jsonPart.getString("description");
//                    Log.i("main",jsonPart.getString("main"));
//                    Log.i("description",jsonPart.getString("description"));
                    if(main!="" && description!=""){
                        message+=main+":"+description+"\r\n";
                    }
                }

                if(message!="") {
                    resultTextView.setText(message);
                }else{
//                    Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
//                Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG).show();
            }
        }
    }
}
