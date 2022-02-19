/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Employee;

/**
 *
 * @author RAI
 */
public class EmployeeDao implements Dao<Employee> {

    private final Connection connection;
    private List<Employee> employees = new ArrayList<>();

    public EmployeeDao(Connection connection) {
        this.connection = connection;

        String query = "SELECT * FROM employee";
        try {
            ResultSet rs = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDate(6), rs.getString(7), rs.getDouble(8),
                        rs.getDouble(9), rs.getInt(10), rs.getInt(11)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Employee> get(long id) {
        return Optional.ofNullable(employees.get((int) id));
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }

    @Override
    public void save(Employee employee) {
        try {
            int id = this.generateId();
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO employee(id, first_name, last_name, email, phone_number, hire_date, job, salary, comission_pct, manager, department) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            psmt.setInt(1, id);
            psmt.setString(2, employee.getFirstName());
            psmt.setString(3, employee.getLastName());
            psmt.setString(4, employee.getEmail());
            psmt.setString(5, employee.getPhoneNumber());
            java.sql.Date mysqlDate = new java.sql.Date(employee.getHireDate().getTime());
            psmt.setDate(6, mysqlDate);
            psmt.setString(7, employee.getJob());
            psmt.setDouble(8, employee.getSalary());
            psmt.setDouble(9, employee.getComissionPct());
            psmt.setInt(10, employee.getManager());
            psmt.setInt(11, employee.getDepartment());
            psmt.execute();
            employee.setEmployeeId(id);
            employees.add(employee);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Employee employee, String[] params) {
        try {
            String firstName = params[0],
                    lastName = params[1],
                    email = params[2],
                    phoneNumber = params[3];
            Date hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[4]);
            String job = params[5];
            double salary = Double.parseDouble(params[6]),
                    comissionPct = Double.parseDouble(params[7]);
            int manager = Integer.parseInt(params[8]),
                    department = Integer.parseInt(params[9]);
            
            employee.setFirstName(firstName);
            employee.setLastName(Objects.requireNonNull(
                    lastName, "Last name cannot be null"));
            employee.setEmail(Objects.requireNonNull(
                    email, "Email cannot be null"));
            employee.setPhoneNumber(phoneNumber);
            employee.setHireDate(Objects.requireNonNull(
                    hireDate, "Hire date cannot be null"));
            employee.setLastName(Objects.requireNonNull(
                    job, "Job cannot be null"));
            employee.setSalary(salary);
            employee.setComissionPct(comissionPct);
            employee.setManager(manager);
            employee.setDepartment(department);
            
            try {
                PreparedStatement psmt = connection.prepareStatement("UPDATE employee SET first_name=?, last_name=?, email=?, phone_number=?, hire_date=?, job=?, salary=?, comission_pct=?, manager=?, department=? WHERE id=?");
                psmt.setString(1, firstName);
                psmt.setString(2, lastName);
                psmt.setString(3, email);
                psmt.setString(4, phoneNumber);
                java.sql.Date mysqlDate = new java.sql.Date(hireDate.getTime());
                psmt.setDate(5, mysqlDate);
                psmt.setString(6, job);
                psmt.setDouble(7, salary);
                psmt.setDouble(8, comissionPct);
                psmt.setInt(9, manager);
                psmt.setInt(10, department);
                psmt.setInt(11, employee.getEmployeeId());
                psmt.execute();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Employee employee) {
        try {
            PreparedStatement psmt = connection.prepareStatement("DELETE FROM employee WHERE id=?");
            psmt.setInt(1, employee.getEmployeeId());
            psmt.execute();
            employees.remove(employee);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private int generateId() {
        String query = "SELECT id FROM employee ORDER BY id DESC LIMIT 1";
        try {
            ResultSet rs = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 100;
    }

}
