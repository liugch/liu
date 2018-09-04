package com.edu.model;

import org.springframework.stereotype.Component;

/**
 * @Author: 何有悠然
 * @ClassName: User
 * @CreateDate: 2018/8/7 14:57
 * @Description: TODO
 **/
@Component
public class User {
    private Integer id;
    private String nickname;
    private Integer age;
    private String mail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                '}';
    }

}
