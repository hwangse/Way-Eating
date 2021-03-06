package com.example.way_eating.network;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 고객이 웨이팅을 요청했을 때 호출되는 클래스. RegisterConfirmActivity에서 확인 버튼을 누를 경우 호출된다.
// POST 방식으로 실행한다.
// 마찬가지로 고객이 웨이팅 취소 버튼을 눌렀을 때도 이 클래스가 호출된다.
// 서버에 보내는 JSON 형식의 파일에 대기 등록일 경우 "registerFlag": 1, 대기 취소일경우 "registerFlag":0 으로 전송된다.
public class RegisterWaiting extends AsyncTask<String, String, String> {
    private JSONObject waitingInfo;

    // async thread의 callback 동작을 설정하기 위한 interface
    public interface AsyncResponse{
        void processFinish(String output);
    }
    public AsyncResponse delegate=null;

    public RegisterWaiting(AsyncResponse delegate,JSONObject info){
        this.delegate=delegate;
        this.waitingInfo=info;
    }

    @Override
    protected String doInBackground(String... urls) {
        JSONObject responseJSON=null;
        try{
            HttpURLConnection conn;
            OutputStream os ;
            InputStream is;
            ByteArrayOutputStream baos;
            URL url=new URL("http://ec2-15-164-226-100.ap-northeast-2.compute.amazonaws.com:3000/android_register_waiting");

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // json object 전달
            os = conn.getOutputStream();
            os.write(waitingInfo.toString().getBytes());
            os.flush();
            // 서버의 응답을 받아오는 과정
            String response;
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                response = new String(byteData);
                return response;
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
//        //super.onPostExecute(result);
        delegate.processFinish(result);
    }
}
