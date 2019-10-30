package com.lpa.test.entity;


import javax.persistence.*;

@Entity
@Table(name =  "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "password", length = 40)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){this.id = id;}

    public String getName(){
        return name ;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public User(){}

    @Override
    public String toString(){
        return "User{"+
                "id="+ id +
                ",name='"+ name + '\'' +
                ",password='"+ password + "\'"+
                '}';
    }
}
