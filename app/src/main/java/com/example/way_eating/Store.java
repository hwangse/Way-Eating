package com.example.way_eating;

/* 서버가 DB에서 Store 정보를 가져와서 각각의 상점에 대해 생성할 class */
public class Store {
    public Integer id;
    public String name;
    private String type;
    private String email;
    private String phoneNum;
    private String address;
    public double locX,locY; // 위도 경도 정보
    private Integer waitTimeLunch;
    private Integer waitTimeDinner;
    // store location
    // store Menu
    public Store(Integer id,String name,String type,String email,String phoneNum,Integer waitTimeLunch,Integer waitTimeDinner,double x,double y){
        this.id=id;
        this.name=name;
        this.type=type;
        this.email=email;
        this.phoneNum=phoneNum;
        //this.address=address;
        this.waitTimeLunch=waitTimeLunch;
        this.waitTimeDinner=waitTimeDinner;
        this.locX=x;
        this.locY=y;
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
}
