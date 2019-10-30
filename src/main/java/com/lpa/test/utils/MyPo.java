package com.lpa.test.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class MyPo {

    private Integer statue;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate endDate;
    private Integer export;
    private String num;
    private String company;

    public Integer getStatue(){return statue;}
    public void setStatue(Integer statue){this.statue = statue;}

    public LocalDate getStartDate(){return startDate;}
    public void setStartDate(LocalDate startDate){this.startDate=startDate;}

    public LocalDate getEndDate(){return endDate;}
    public void setEndDate(LocalDate endDate){this.endDate = endDate;}

    public Integer getExport(){return export;}
    public void setExport(Integer export){this.export = export;}

    public String getNum(){return num;}
    public void setNum(String num){this.num = num;}

    public String getCompany(){return company;}
    public void setCompany(String company){this.company =  company;}

    @Override
    public String toString(){
        return "MyPo [statue="+statue+",startDate="+startDate+",endDate="+endDate+",num="+num+",company"+company+",export="+export+"]";
    }

}
