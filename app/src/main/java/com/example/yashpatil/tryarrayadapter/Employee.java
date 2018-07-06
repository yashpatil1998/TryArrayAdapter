package com.example.yashpatil.tryarrayadapter;

import java.io.Serializable;

/**
 * Created by Yash Patil on 18-06-2018.
 */

public class Employee implements Serializable {
    public Employee() {
    }

    private String id;
    private String name;

    public Employee(String mId, String mName) {
        id = mId;
        name = mName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String mId) {
        id = mId;
    }

    public void setName(String mName) {
        name = mName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
