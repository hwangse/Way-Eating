package com.example.way_eating.ui.home;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.google.gson.Gson;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private MapFragment mapFragment;
    private LocationOverlay locationOverlay;
    private Location userLocation;
    private Location camLocation;
    private CameraUpdate cameraUpdate;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    private SearchView search;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    public ParsedData parsedData=new ParsedData();
    public String parsingString="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        userLocation=new Location("");
        camLocation=new Location("");
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
                    searchQueryRestaurant(s, userLocation);

                    if(parsedData!=null && parsedData.places!=null){
                        double x=Double.parseDouble(parsedData.places.get(0).x);
                        double y=Double.parseDouble(parsedData.places.get(0).y);
                        camLocation.setLongitude(x); // 경도
                        camLocation.setLatitude(y);  // 위도

                        mapFragment.getMapAsync(HomeFragment.this::onMapReady);
                        //cameraUpdate = CameraUpdate.scrollTo(new LatLng(userLocation.getLatitude(),userLocation.getLongitude()));

                        //cameraUpdate = CameraUpdate.scrollTo(new LatLng(y,x));
                        //naverMap.moveCamera(cameraUpdate);

                        Toast.makeText(getActivity(), "검색 완료 : " + parsedData.places.get(0).x+" , "+parsedData.places.get(0).y, Toast.LENGTH_SHORT).show();

                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 검색어가 변경되었을 때, 계속해서 검색 결과가 바뀐다.
                searchQueryRestaurant(s,userLocation);

                if(parsedData!=null && parsedData.places!=null) {
                    ArrayList<Place> places = new ArrayList<Place>();
                    places.addAll(parsedData.places);
                    //parsingString="";
                    for (int i = 0; i < places.size(); i++) {
                        list.add(places.get(i).name);
                       // parsingString += (places.get(i).name+"   ");
                    }
                    searchChangeAction(s);
                   // Toast.makeText(getActivity(),"변경 검색 완료 : "+parsingString,Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        // setting search engine adapter
        listView = (ListView) root.findViewById(R.id.listView);
        list = new ArrayList<String>();

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list,getContext());
        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        return root;
    }
    // 검색화면을 업데이트하는 메소드
    public void searchChangeAction(String txt) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        if(txt.length()!=0)
        {
            ArrayList<Place> arraylist = new ArrayList<Place>();
            arraylist.addAll(parsedData.places);
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                    list.add(arraylist.get(i).name);
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    @Override
    // Automatically Called After 'getMapAsync' method
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
                    parsingString=str;

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