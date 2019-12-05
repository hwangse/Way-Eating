package com.example.way_eating.network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 가게 대기 시간 변경시 이를 업데이트 하기 위한 클래스
// 모든 가게의 현재 대기 시간 정보만 받아온다.
public class UpdateStore extends AsyncTask<String, String, String> {
    // async thread의 callback 동작을 설정하기 위한 interface
    public interface AsyncResponse{
        void processFinish(String output);
    }
    public AsyncResponse delegate=null;

    public UpdateStore(AsyncResponse delegate){
        this.delegate=delegate;
    }

    @Override // 쓰레드가 프로그램 백그라운드에서 진행하는 동작
    protected String doInBackground(String... urls) {
        HttpURLConnection con=null;
        BufferedReader reader=null;
        try {
            try{
                URL url=new URL("http://ec2-15-164-226-100.ap-northeast-2.compute.amazonaws.com:3000/android_update_store");
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

    @Override // execute가 끝나고 실행되는 동작
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}