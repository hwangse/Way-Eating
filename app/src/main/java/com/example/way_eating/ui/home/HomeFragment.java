package com.example.way_eating.ui.home;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.example.way_eating.activity.MainActivity;
import com.example.way_eating.data.Store;
import com.example.way_eating.data.SystemData;
import com.example.way_eating.network.GetStore;
import com.example.way_eating.network.UpdateStore;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    // variable for Naver Map Controlling
    private HomeViewModel homeViewModel;
    private MapFragment mapFragment;
    private LocationOverlay locationOverlay;
    private Location userLocation;
    private Location camLocation;
    private CameraUpdate cameraUpdate;
    private boolean searchButtonClicked=false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    // for the async search task using Naver REST API
    private static Handler handler;

    // variable for search function
    private SearchView search;
    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    // search result will be stored here
    public ParsedData parsedData=new ParsedData();

    // 시스템 정보를 저장할 클래스 선언, Systemdata의 Stores 에는 현재 저장된 음식점의 정보가, User에는 User의 정보가 저장된다.
    // 외부에서 사용할 경우 반드시 SystemData클래스를 생성하는게 아닌, homefragment의 systemdata를 가져와서 하도록!!!
    static public SystemData systemData=null;
    // 테스트용 textView
    TextView tvData;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create 2 kinds of async thread and Handler for searching function
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String txt=search.getQuery().toString();
                updateListView(txt);
            }
        };

        // create fragment view model
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        userLocation=new Location("");
        camLocation=new Location("");
        camLocation.setLongitude(127);
        camLocation.setLatitude(37.6);

        ////////테스트용 textData
        tvData= root.findViewById(R.id.textView);

        // 만약 시스템 데이터 정보가 없다면 이를 생성한다.
        if(systemData==null) systemData=new SystemData();
        // 만약 유저 정보가 생성되지 않았다면 이를 생성한다.
        if(systemData.user==null){
            systemData.user= MainActivity.userInMain;
            Toast.makeText(getActivity(), systemData.user.getName() + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();
        }
        // 안드로이드 시스템 상에 store정보가 없다면 GetStore 호출을 통해 store array를 생성해준다.
        if(systemData.stores==null){
            makeStoreClasses();
        }else{
            updateStoreClasses();
        }

        // this is for the Showing the Naver Map
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        // basic settings for current location
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        // call the 'NaverMap' object -> onMayReady method is automatically called
        mapFragment.getMapAsync(this);

        // setting search engine
        search=root.findViewById(R.id.searchRestaurant);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 검색 버튼이 눌렸을 때, 검색한 곳으로 지도를 옮겨줌과 동시에 기본 정보를 보여준다.
                if(s.length()!=0) {
                    listView.setVisibility(View.INVISIBLE);
                    searchQueryRestaurant(s, userLocation);

                    if(parsedData!=null && parsedData.places!=null && parsedData.places.size()>0){
                        double x=Double.parseDouble(parsedData.places.get(0).x);
                        double y=Double.parseDouble(parsedData.places.get(0).y);
                        camLocation.setLongitude(x); // 경도
                        camLocation.setLatitude(y);  // 위도

                        searchButtonClicked=true;
                        mapFragment.getMapAsync(HomeFragment.this::onMapReady);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                searchButtonClicked=false;
                // 검색어가 변경되었을 때, 계속해서 검색 결과가 바뀐다.
                searchQueryRestaurant(s,userLocation);
                return false;
            }
        });
        // setting search engine adapter (검색시에 아래 나오는 음식점 리스트를 제어해줄 어댑터)
        listView =  root.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String target=list.get(i);
                double targetX=0,targetY=0;

                for(int a=0;a<parsedData.places.size();a++){
                    if(parsedData.places.get(a).name==target){
                        //Toast.makeText(getActivity(),target,Toast.LENGTH_SHORT).show();
                        targetX=Double.parseDouble(parsedData.places.get(a).x);
                        targetY=Double.parseDouble(parsedData.places.get(a).y);
                        break;
                    }
                }
                listView.setVisibility(View.INVISIBLE);
                // move the camera  location to selected
                camLocation.setLongitude(targetX);
                camLocation.setLatitude(targetY);
                searchButtonClicked=true;
                mapFragment.getMapAsync(HomeFragment.this::onMapReady);
            }
        });

        list = new ArrayList<String>();
        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list,getContext());
        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // 만약 시스템 데이터 정보가 없다면 이를 생성한다.
        //if(systemData==null) systemData=new SystemData();
        // 만약 유저 정보가 생성되지 않았다면 이를 생성한다.
        //if(systemData.user==null){
        //    systemData.user= MainActivity.userInMain;
        //    Toast.makeText(getActivity(), systemData.user.getName() + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();
        //}
        // 만약 상점 데이터 정보가 없다면 시스템 내에 상점 정보를 받아온다.
        //if(systemData.stores==null){
        //    makeStoreClasses();
        //}

        return root;
    }

    // store 클래스가 이미 생성된 상태에서 store 대기 시간 정보를 update해주는 메소드
    public void updateStoreClasses(){
        // get 요청을 하고 난 뒤 => 각 마커의 시간 정보를 수정한다.
        UpdateStore updateStore=new UpdateStore((String output)->{
            try{
                JSONObject jsonObject=new JSONObject(output);
                String arr=jsonObject.getString("times");
                // 현재 대기 시간을 받아옴.
                arr=arr.substring(1,arr.length()-1);
                ArrayList<String> timeList = new ArrayList<>(Arrays.asList(arr.split(",")));
                for(int i=0;i<timeList.size();i++){
                    systemData.markers.get(i).setCaptionText(timeList.get(i)+"분");
                }
             //   Toast.makeText(getActivity(),jsonObject.getString("times"),Toast.LENGTH_SHORT).show();
//                marker.setPosition(new LatLng(tmpY, tmpX));
//                marker.setCaptionAligns(Align.Top);
//                marker.setCaptionColor(Color.BLUE);
//                marker.setCaptionHaloColor(Color.rgb(200, 255, 200));
//                marker.setCaptionTextSize(16);
//                systemData.markers.add(marker);
            }catch(JSONException e){
                e.printStackTrace();
            }
        });
        updateStore.execute();
    }
    // 어플리케이션내에 storeData를 생성하는 메소드
    public void makeStoreClasses(){
        GetStore getStore=new GetStore((String output)->{
<<<<<<< Updated upstream
                // 받아온 상점 JSON 파일을 파싱하여 class를 생성한다.
                if(output!=null){
                    try {
                        // 서버로부터 받아온 JSON 객체
                        JSONObject jsonObject = new JSONObject(output);
                        // 시스템 데이터의 Store과 Marker 리스트를 생성한다.
                        systemData.stores=new ArrayList<>();
                        systemData.markers=new ArrayList<>();

                        for(int i=0;i<jsonObject.length();i++){
                            JSONObject jsonObj= jsonObject.getJSONObject(i+"");
                            JSONObject location=jsonObj.getJSONObject("location");
                            JSONObject menu=jsonObj.getJSONObject("menu");

                            Integer tmpId=jsonObj.getInt("id");
                            String tmpName=jsonObj.getString("name");
                            String tmpType=jsonObj.getString("type");
                            String tmpEmail=jsonObj.getString("email");
                            String tmpPhone=jsonObj.getString("phone");
                            Integer tmpLunch=jsonObj.getInt("timeLunch");
                            Integer tmpDinner=jsonObj.getInt("timeDinner");
                            String tmpAddr=jsonObj.getString("address");
                            String tmpHomepage=jsonObj.getString("homepage");
                            double tmpOpen=jsonObj.getDouble("open");
                            double tmpClose=jsonObj.getDouble("close");
                            double tmpX=location.getDouble("x");
                            double tmpY=location.getDouble("y");
                            boolean tmpIsOpen=jsonObj.getBoolean("isOpen");
                            tvData.setText(menu.toString());
//                            Log.v("menu",menu.toString());
                            systemData.stores.add(new Store(tmpId,tmpName,tmpType,tmpEmail,tmpPhone,tmpLunch,tmpDinner,tmpX,tmpY,tmpAddr,tmpHomepage,tmpOpen,tmpClose,tmpIsOpen,menu));

                            // time marker 생성
                            Marker marker=new Marker();
                            //////이거 대모용으로 만들어놓은 임시 대기 시간임!!!!/////
                            //Random r=new Random();
                            //int num=r.nextInt(20)+5;
                            marker.setCaptionText(0+"분");
                            ///////////////////////////////////////////
                            marker.setPosition(new LatLng(tmpY, tmpX));
                            marker.setCaptionAligns(Align.Top);
                            marker.setCaptionColor(Color.BLUE);
                            marker.setCaptionHaloColor(Color.rgb(200, 255, 200));
                            marker.setCaptionTextSize(16);
                            systemData.markers.add(marker);
                        }

                    }catch(JSONException e){
                        e.printStackTrace();
=======
            // 받아온 상점 JSON 파일을 파싱하여 class를 생성한다.
            if(output!=null){
                try {
                    // 서버로부터 받아온 JSON 객체
                    JSONObject jsonObject = new JSONObject(output);
                    // 시스템 데이터의 Store과 Marker 리스트를 생성한다.
                    systemData.stores=new ArrayList<>();
                    systemData.markers=new ArrayList<>();

                    for(int i=0;i<jsonObject.length();i++){
                        JSONObject jsonObj= jsonObject.getJSONObject(i+"");
                        JSONObject location=jsonObj.getJSONObject("location");

                        Integer tmpId=jsonObj.getInt("id");
                        String tmpName=jsonObj.getString("name");
                        String tmpType=jsonObj.getString("type");
                        String tmpEmail=jsonObj.getString("email");
                        String tmpPhone=jsonObj.getString("phone");
                        Integer tmpLunch=jsonObj.getInt("timeLunch");
                        Integer tmpDinner=jsonObj.getInt("timeDinner");
                        double tmpX=location.getDouble("x");
                        double tmpY=location.getDouble("y");
                        systemData.stores.add(new Store(tmpId,tmpName,tmpType,tmpEmail,tmpPhone,tmpLunch,tmpDinner,tmpX,tmpY));

                        // time marker 생성
                        Marker marker=new Marker();
                        //////이거 대모용으로 만들어놓은 임시 대기 시간임!!!!/////
                        Random r=new Random();
                        int num=r.nextInt(20)+5;
                        marker.setCaptionText(num+"분");
                        ///////////////////////////////////////////
                        marker.setPosition(new LatLng(tmpY, tmpX));
                        marker.setCaptionAligns(Align.Top);
                        marker.setCaptionColor(Color.BLUE);
                        marker.setCaptionHaloColor(Color.rgb(200, 255, 200));
                        marker.setCaptionTextSize(16);
                        systemData.markers.add(marker);
>>>>>>> Stashed changes
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(),"Error : 서버 접속 불가",Toast.LENGTH_SHORT).show();
            }
        });
        getStore.execute();
    }
    // update list view when searching places
    public void updateListView(String s){
        if(parsedData!=null && parsedData.places!=null) {
            searchChangeAction(s);
        }
    }
    @Override
    // Automatically Called After 'getMapAsync' method, 지도에 대해 모든 초기화를 진행하는 함수
    public void onMapReady(@NonNull final NaverMap naverMap) {
        // Setting Location Overlay
        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);

        // Setting Location based Map
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
        naverMap.getUiSettings().setLocationButtonEnabled(true);

        // 위치 바뀔 경우 계속해서 update 될 수 있도록 listener 등록
        NaverMap.OnLocationChangeListener listener=new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                locationOverlay.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

                // change user location
                userLocation.setLongitude(location.getLongitude());
                userLocation.setLatitude(location.getLatitude());

            }
        };

        // camera update
        cameraUpdate = CameraUpdate.scrollTo(new LatLng(camLocation.getLatitude(),camLocation.getLongitude()));
        naverMap.moveCamera(cameraUpdate);
        naverMap.addOnLocationChangeListener(listener);

        // 만약에 timeMarker가 null이 아니라면 각각을 지도 상에 띄워준다.
        if(systemData.markers!=null){
            for(int i=0;i<systemData.markers.size();i++)
                systemData.markers.get(i).setMap(naverMap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    // 음식점 검색시에 네이버 API를 이용하여 검색어와 관련된 사용자 주변의 5개 장소를 보여준다. (새로운 쓰레드 생성)
    public void searchQueryRestaurant(final String query,final Location loc){
        // Making Background Thread for Networking connection
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create URL
                    URL url = new URL("https://naveropenapi.apigw.ntruss.com/map-place/v1/search?query="+query+"&coordinate="+loc.getLongitude()+","+loc.getLatitude());

                    // Create Connection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID","lvd8yed6xo");
                    conn.setRequestProperty("X-NCP-APIGW-API-KEY","Al5MVTts91hihEVC724xVDMMaygsHq8LfIV7JcX0");

                    //conn.setRequestMethod("GET");
                    InputStream responseBody = conn.getInputStream();
                    // Convert InputStream to String Object
                    Scanner sc = new Scanner(responseBody);
                    StringBuffer sb = new StringBuffer();
                    while(sc.hasNext()){
                        sb.append(sc.nextLine());
                    }
                    String str=sb.toString();

                    // parsing JSON
                    Gson gson=new Gson();
                    parsedData=gson.fromJson(str,ParsedData.class);

                    conn.disconnect();
                }
                catch (Exception e) {
                    // Error calling the rest api
                    Log.e("REST_API", "GET method failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        });

    }
    // 검색화면을 업데이트하는 메소드
    public void searchChangeAction(String txt) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        ArrayList<Place> arraylist = new ArrayList<Place>();
        arraylist.addAll(parsedData.places);
        // 리스트의 모든 데이터를 검색한다.
        for(int i = 0;i < arraylist.size(); i++)
        {
            list.add(arraylist.get(i).name);
        }

        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}
// These are the classes for the parsed Json data
class ParsedData{
    String status;
    Meta meta;
    ArrayList<Place> places;
    String errorMessage;
}
class Meta{
    int totalCount;
    int count;
}
class Place{
    String name;
    String road_address;
    String jibun_address;
    String phone_number;
    String x;
    String y;
    double distance;
    String sessionId;
}