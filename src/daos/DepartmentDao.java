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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import models.Department;

/**
 *
 * @author RAI
 */
public class DepartmentDao implements Dao<Department> {

    private final Connection connection;
    private List<Department> departments = new ArrayList<>();

    public DepartmentDao(Connection connection) {
        this.connection = connection;

        String query = "SELECT * FROM department";
        try {
            ResultSet rs = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (rs.next()) {
                departments.add(new Department(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Department> get(long id) {
        return Optional.ofNullable(departments.get((int) id));
    }

    @Override
    public List<Department> getAll() {
        return departments;
    }

    @Override
    public void save(Department department) {
        try {
            int id = this.generateId();
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO country(id, name, manager, location) VALUES(?,?,?,?)");
            psmt.setInt(1, id);
            psmt.setString(2, department.getDepartmentName());
            psmt.setInt(3, department.getManager());
            psmt.setInt(3, department.getLocation());
            psmt.execute();
            department.setDepartmentId(id);
            departments.add(department);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Department department, String[] params) {
        String name = params[0];
        int manager = Integer.parseInt(params[1]);
        int location = Integer.parseInt(params[2]);

        department.setDepartmentName(Objects.requireNonNull(
                name, "Country name cannot be null"));
        department.setManager(Objects.requireNonNull(
                manager, "Manager cannot be null"));
        department.setLocation(Objects.requireNonNull(
                location, "Location cannot be null"));

        try {
            PreparedStatement psmt = connection.prepareStatement("UPDATE department SET name=?, manager=?, location=? WHERE id=?");
            psmt.setString(1, name);
            psmt.setInt(2, manager);
            psmt.setInt(3, location);
            psmt.setInt(4, department.getDepartmentId());
            psmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Department department) {
        try {
            PreparedStatement psmt = connection.prepareStatement("DELETE FROM department WHERE id=?");
            psmt.setInt(1, department.getDepartmentId());
            psmt.execute();
            departments.remove(department);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int generateId() {
        String query = "SELECT id FROM department ORDER BY id DESC LIMIT 1";
        try {
            ResultSet resultSet = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1) + 10;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 10;
    }

}
