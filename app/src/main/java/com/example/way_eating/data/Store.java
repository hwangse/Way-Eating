package com.example.way_eating.data;

import org.json.JSONObject;

/* 서버가 DB에서 Store 정보를 가져와서 각각의 상점에 대해 생성할 class */
public class Store {
    public Integer id;
    public String name;
    private String type;
    private String email;
    private String phoneNum;
    private String address;
    private String homepage;
    private double openTime;
    private double closeTime;
    public double locX,locY; // 위도 경도 정보
    private Integer waitTimeLunch;
    private Integer waitTimeDinner;
    public boolean isOpen;
    public JSONObject menu;

    // store Menu
    public Store(Integer id,String name,String type,String email,String phoneNum,Integer waitTimeLunch,Integer waitTimeDinner,double x,double y,String address,String homepage,double open,double close,boolean isOpen,JSONObject menu){
        this.id=id;
        this.name=name;
        this.type=type;
        this.email=email;
        this.phoneNum=phoneNum;
        this.waitTimeLunch=waitTimeLunch;
        this.waitTimeDinner=waitTimeDinner;
        this.locX=x;
        this.locY=y;
        this.address=address;
        this.homepage=homepage;
        this.openTime=open;
        this.closeTime=close;
        this.isOpen=isOpen;
        this.menu=menu;
    }
    public Integer getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNum(){
        return this.phoneNum;
    }
    public String getAddress(){
        return this.address;
    }
    public Integer waitTimeLunch(){
        return this.waitTimeLunch;
    }
    public Integer waitTimeDinner(){
        return this.waitTimeDinner;
    }
    public String getHomepage() {return this.homepage;}
    public double getOpenTime() {return this.openTime;}
    public double getCloseTime() {return this.closeTime;}
}
