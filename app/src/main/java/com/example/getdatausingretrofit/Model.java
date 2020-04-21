package com.example.getdatausingretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    //private String name;
    // private String last_name;
    // private String student_number;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("student_number")
    @Expose
    private String student_number;


    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

}
