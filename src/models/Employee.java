/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author RAI
 */
public class Employee {
    private int employeeId, manager, department;
    private String firstName, lastName, email, phoneNumber, job;
    private Date hireDate;
    private double salary, comissionPct;

    public Employee(int employeeId, int manager, int department, String firstName, String lastName, String email, String phoneNumber, String job, Date hireDate, double salary, double comissionPct) {
        this.employeeId = employeeId;
        this.manager = manager;
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.hireDate = hireDate;
        this.salary = salary;
        this.comissionPct = comissionPct;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getComissionPct() {
        return comissionPct;
    }

    public void setComissionPct(double comissionPct) {
        this.comissionPct = comissionPct;
    }

    @Override
    public String toString() {
        return "Employee{" + "employeeId=" + employeeId + ", manager=" + manager + ", department=" + department + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", job=" + job + ", hireDate=" + hireDate + ", salary=" + salary + ", comission_pct=" + comissionPct + '}';
    }
    
}
