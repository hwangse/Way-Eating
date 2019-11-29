package com.example.way_eating.data;


import java.util.ArrayList;

/* wayEating 시스템의 전체 데이터를 저장하는 클래스 */
public class SystemData {
    public User user;
    public ArrayList<Store> stores;

    public SystemData(){
        user=null;
        stores=null;
    }

}
