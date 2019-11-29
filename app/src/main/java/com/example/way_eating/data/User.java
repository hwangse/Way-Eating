package com.example.way_eating.data;

/* 유저가 로그인에 성공한다면 만들어질 클래스 */
public class User {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String phoneNum;
    private String email;
    // user History
    // user Favorite
    public User(Integer id,String name,String sex,Integer age,String phoneNum,String email){
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.age=age;
        this.phoneNum=phoneNum;
        this.email=email;
    }
    /*
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
     */
}
