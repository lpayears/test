package com.lpa.test.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "yw_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yw_time;

    @Column(name = "num",length = 18)
    private String num;

    @Column(name = "company")
    private String company;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;

    @Column(name = "money")
    private Double money;

    @OneToOne
    @JoinColumn(name = "country_code")
    private Country country;

    @Column(name = "book_time")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate book_time;

    @Column(name = "staff")
    private String staff;

    @Column(name = "phone")
    private String phone;

    @Column(name ="address")
    private  String address;

    @Column(name = "finish_time")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate finish_time;

    @Column(name = "check_time")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate check_time;

    @Column(name = "statue" ,length = 1)
    private Integer statue;

    @Column(name = "dispose")
    private String dispose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){this.id = id;}

    public LocalDate getYw_time(){
        return yw_time;
    }
    public void setYw_time(LocalDate yw_time){
        this.yw_time = yw_time;
    }
    public String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }
    public String getCompany(){
        return company;
    }
    public void setCompany(String company){
        this.company = company;
    }

    public Currency getCurrency(){
        return currency;
    }
    public void setCurrency(Currency currency){
        this.currency = currency;
    }
    public Double getMoney(){
        return money;
    }
    public void setMoney(Double money){
        this.money = money;
    }
    public Country getCountry(){
        return country;
    }
    public void setCountry(Country country){
        this.country = country;
    }
    public LocalDate getBook_time(){
        return book_time;
    }
    public void setBook_time(LocalDate book_time){
        this.book_time = book_time;
    }
    public  String getStaff(){
        return staff;
    }
    public void setStaff(String staff){
        this.staff = staff;
    }
    public String getPhone(){
        return  phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public LocalDate getFinish_time(){
        return finish_time;
    }
    public void setFinish_time(LocalDate finish_time){
        this.finish_time = finish_time;
    }
    public Integer getStatue(){
        return statue;
    }
    public void setStatue(Integer statue){
        this.statue = statue;
    }
    public LocalDate getCheck_time(){return check_time;}
    public void setCheck_time(LocalDate check_time){
        this.check_time = check_time;
    }
    public String getDispose(){return dispose;}
    public void setDispose(String dispose){this.dispose = dispose;}

    public Project(){

    }


    @Override
    public String toString(){
        return "Project{"+
                "id=" + id +
                ", yw_time='" + yw_time + '\'' +
                ", num='" + num + '\'' +
                ", company='" + company + '\'' +
                ", currency=" + currency +
                ", money=" + money +
                ", country=" + country +
                ", book_time=" + book_time +
                ", staff=" + staff +
                ", phone=" + phone +
                ", address=" + address +
                ", finish_time=" + finish_time +
                ", check_time=" + check_time +
                ", statue=" + statue +
                '}';
    }


}
