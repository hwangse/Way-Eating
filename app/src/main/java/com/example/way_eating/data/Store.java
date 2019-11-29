package com.example.way_eating.data;

/* 서버가 DB에서 Store 정보를 가져와서 각각의 상점에 대해 생성할 class */
public class Store {
    private Integer id;
    public String name;
    private String type;
    private String email;
    private String phoneNum;
    private String address;
    private Integer waitTimeLunch;
    private Integer waitTimeDinner;
    // store location
    // store Menu
    public Store(Integer id,String name,String type,String email,String phoneNum,Integer waitTimeLunch,Integer waitTimeDinner){
        this.id=id;
        this.name=name;
        this.type=type;
        this.email=email;
        this.phoneNum=phoneNum;
        //this.address=address;
        this.waitTimeLunch=waitTimeLunch;
        this.waitTimeDinner=waitTimeDinner;
    }
}
