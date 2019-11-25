package com.example.way_eating.network;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetStore extends AsyncTask<String, String, String> {
    public interface AsyncResponse{
        void processFinish(String output);
    }
    public AsyncResponse delegate=null;

    public GetStore(AsyncResponse delegate){
        this.delegate=delegate;
    }

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection con=null;
        BufferedReader reader=null;
        try {
            try{
                URL url=new URL("http://ec2-15-164-226-100.ap-northeast-2.compute.amazonaws.com:3000/android_store");
                con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                return buffer.toString();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        delegate.processFinish(result);
    }
}