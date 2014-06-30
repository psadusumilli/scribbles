package com.vijayrc.dummies;

import com.vijayrc.meta.ToString;

import java.util.Date;

@ToString
public class Employee {
    private Long id;
    private String name;
    private String designation;
    private Date joiningDate;

    public Employee(Long id, String name, String designation, Date joiningDate) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.joiningDate = joiningDate;
    }
}
