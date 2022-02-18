/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author RAI
 */
public class Department {
    private int departmentId, manager, location;
    private String departmentName;

    public Department(int departmentId, String departmentName, int manager, int location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.manager = manager;
        this.location = location;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" + "departmentId=" + departmentId + ", manager=" + manager + ", location=" + location + ", departmentName=" + departmentName + '}';
    }
}
