package com.example.alexandremguay.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected ProgressBar progress;
    private static final String ns = null;
    protected XmlPullParserFactory xml;
    protected static XmlPullParser parser;
    protected URL url;
    protected HttpURLConnection conn;
    protected InputStream input;
    protected String address = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    protected String minTemp;
    protected String maxTemp;
    protected String currentTemp;
    protected String iconName;
    protected Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progress = findViewById(R.id.progress);
        new ForecastQuery().execute(address);

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                input = conn.getInputStream();
                xml = XmlPullParserFactory.newInstance();
                xml.setNamespaceAware(true);
                parser = xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(input, null);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) { //look for a start tag, if none is found, continue to next iteration of while loop
                        continue;
                    }
                    String name = parser.getName();

                    if (name.equals("temperature")) {

                        currentTemp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTemp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTemp = parser.getAttributeValue(null, "min");
                        publishProgress(75);

                    } else if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName + ".png";
                        if (fileExistence(iconFile)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(inputStream);
                        } else {
                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            icon = getImage(iconUrl);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                        Log.i("In Weather Forecast", "file name=" + iconFile);
                        publishProgress(100);
                    } else {
                        skip(parser);
                    }

                }
            } catch (IOException e) {
                return "IOException";
            } catch (Exception e) {
                return "XmlPullParserException";
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return "";

        }

        protected Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(input);
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        protected void onProgressUpdate(Integer... values) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(values[0]);
        }

        protected void onPostExecute(String result) {
            progress.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistence(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        private void skip(XmlPullParser par) throws XmlPullParserException, IOException {
            if (par.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (par.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }
    }
}


//        private List readFeed(XmlPullParser par) throws XmlPullParserException, IOException {
//            List entries = new ArrayList();
//
//            par.require(XmlPullParser.START_TAG, ns, "current");
//            while (par.next() != XmlPullParser.END_TAG) {
//                if (par.getEventType() != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                String name = par.getName();
//                if (name.equals("temperature")) {
//                    entries.add(readTemp(par));
//                } else {
//                    skip(par);
//                }
//            }
//            return entries;
//        }

//        private Entry readTemp(XmlPullParser par) throws XmlPullParserException, IOException {
//            par.require(XmlPullParser.START_TAG, ns, "temperature");
//            String value = null;
//            String min = null;
//            String max = null;
//            while (par.next() != XmlPullParser.END_TAG) {
//                if (par.getEventType() != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                String name = par.getName();
//
//                if (name.equals("value")) {
//                    value = readValue(par);
//                } else if (name.equals("min")) {
//                    min = readMin(par);
//                } else if (name.equals("max")) {
//                    max = readMax(par);
//                } else {
//                    skip(par);
//                }
//            }
//            return new Entry(value, min, max);
//        }

//        private String readValue(XmlPullParser par) throws IOException, XmlPullParserException {
//            par.require(XmlPullParser.START_TAG, ns, "value");
//            String value = readText(par);
//            par.require(XmlPullParser.END_TAG, ns, "value");
//            return value;
//        }
//
//        private String readMin(XmlPullParser par) throws IOException, XmlPullParserException {
//            par.require(XmlPullParser.START_TAG, ns, "minimum");
//            String min = readText(par);
//            par.require(XmlPullParser.END_TAG, ns, "minimum");
//            return min;
//        }
//
//        private String readMax(XmlPullParser par) throws IOException, XmlPullParserException {
//            par.require(XmlPullParser.START_TAG, ns, "maximum");
//            String max = readText(par);
//            par.require(XmlPullParser.END_TAG, ns, "maximum");
//            return max;
//        }




