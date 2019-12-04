package com.example.way_eating.data;

// 유저의 웨이팅 정보를 저장할 클래스
// 웨이팅 등록을 하지 않을 경우 생성되지 않는다.
// 중복 웨이팅 등록 여부를 이 클래스를 통해 확인한다.
public class WaitingInfo {
    private Integer storeId;
    private String menu;
    private Integer numOfPeople;
    private Integer timeTotal; // 예상 대기 시간
    private Integer timeRemain;
    private Integer orderInLine; // 대기 줄에서 몇번째인지
    //constructor
    public WaitingInfo(Integer sId,String menu,Integer people){
        this.storeId=sId;
        this.menu=menu;
        this.numOfPeople=people;
    }
    // 초기 대기순서 및 시간을 설정하는 메소드
    public void setWaitingTime(Integer time,Integer order){
        this.timeTotal=time;
        this.timeRemain=time;
        this.orderInLine=order;
    }
}
