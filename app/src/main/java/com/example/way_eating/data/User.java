package com.example.way_eating.data;

import java.io.Serializable;

/* 유저가 로그인에 성공한다면 만들어질 클래스 */
public class User implements Serializable {
    // 유저 기본 정보
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String phoneNum;
    private String email;

    // 유저의 waiting information 을 저장할 클래스
    private WaitingInfo waitingInfo;

    // 유저 히스토리
    // user History
    // user Favorite
    public User(Integer id, String name, String sex, Integer age, String phoneNum, String email) {
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.age=age;
        this.phoneNum=phoneNum;
        this.email=email;
        this.waitingInfo=null;
    }

    public Integer getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getSex(){
        return this.sex;
    }
    public Integer getAge(){
        return this.age;
    }
    public String getPhoneNum(){
        return this.phoneNum;
    }
    public String getEmail(){
        return this.email;
    }
    // 이미 등록된 웨이팅 정보가 있는지 확인하는 메소드
    public boolean isAlreadyRegistered(){
        if(waitingInfo!=null) return true;
        else return false;
    }
    // 웨이팅 정보를 새롭게 생성하는 메소드
    public void createWaitingInfo(Integer sId,String menu,Integer people,Integer time,Integer order){
            this.waitingInfo=new WaitingInfo(sId,menu,people);
            this.waitingInfo.setWaitingTime(time,order);
    }
}
