package com.example.alexandremguay.android;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecast1 extends Activity {
    protected static final String Activity_Name = "WeatherForecast";
    private ProgressBar progressBar;
    TextView currTemp;
    TextView minTemp;
    TextView maxTemp;
    ImageView currentWeather;
    String max="";
    String min="";
    String value="";
    String icon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currTemp = findViewById(R.id.currentTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        currentWeather = findViewById(R.id.currentWeather);

        Log.i(Activity_Name, "In onCreate()");

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        new ForecastQuery().execute();

    }

    private class ForecastQuery extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private final String ns = null;

        String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        String imageUrl = "";
        Bitmap bit;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(Activity_Name, "In doInBackground()");
            URL url;
            HttpURLConnection urlConnection = null;
            XmlPullParserFactory factory;

            try {
                url = new URL(urlStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(in, null);
                parser.nextTag();

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equals("temperature")) {
                                max = parser.getAttributeValue(null,"max");
                                publishProgress("25");
                                min = parser.getAttributeValue(null,"min");
                                publishProgress("50");
                                value = parser.getAttributeValue(null,"value");
                                publishProgress("75");
                            }

                            if (name.equals("weather")) {
                                icon = parser.getAttributeValue(null,"icon");
                            }
                            break;
                    }
                    eventType = parser.next();
                }

                imageUrl = "http://openweathermap.org/img/w/"+icon+".png";

                if (fileExistence(icon+".png")) {

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(icon + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bit = BitmapFactory.decodeStream(fis);
                    publishProgress("100");
                    Log.i(Activity_Name,"Saved local:"+icon+".png");


                } else {
                    Bitmap image  = Utils.getImage(imageUrl);
                    publishProgress("100");
                    FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    bit = image;
                    Log.i(Activity_Name,"Downloaded Image:"+icon+".png");

                }
                return "";

            } catch (Exception e) {
                e.printStackTrace();
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }


        protected void onProgressUpdate (Integer ... values) {
            super.onProgressUpdate(String.valueOf(values));
            progressBar.setProgress(values[0]);
        }


        protected void onPostExecute (String s) {

            super.onPostExecute(s);
            currTemp.setText(value);
            minTemp.setText(min);
            maxTemp.setText(max);
            currentWeather.setImageBitmap(bit);
            progressBar.setVisibility(View.INVISIBLE);

        }

        public boolean fileExistence(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }


    }



}