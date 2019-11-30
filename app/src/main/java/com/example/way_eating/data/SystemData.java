package com.example.way_eating.data;

import com.naver.maps.map.overlay.Marker;
import java.util.ArrayList;

/* wayEating 시스템의 전체 데이터를 저장하는 클래스
*  stores에는 store DB에 저장되어있는 음식점들이
*  user에는 현재 로그인한 유저에 대한 정보가 저장된다.
* */
public class SystemData {
    public User user;
    public ArrayList<Store> stores;
    public ArrayList<Marker> markers;

    public SystemData(){
        user=null;
        stores=null;
        markers=null;
    }

}
