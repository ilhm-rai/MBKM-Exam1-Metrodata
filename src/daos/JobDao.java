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
import models.Job;

/**
 *
 * @author RAI
 */
public class JobDao implements Dao<Job> {

    private final Connection connection;
    private List<Job> jobs = new ArrayList<>();

    public JobDao(Connection connection) {
        this.connection = connection;
        
        String query = "SELECT * FROM job";
        try {
            ResultSet rs = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (rs.next()) {
                jobs.add(new Job(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<Job> get(long id) {
        return Optional.ofNullable(jobs.get((int) id));
    }

    @Override
    public List<Job> getAll() {
        return jobs;
    }

    @Override
    public void save(Job job) {
        try {
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO job(id, title, min_salary, max_salary) VALUES(?,?,?,?)");
            psmt.setString(1, job.getJobId());
            psmt.setString(2, job.getTitle());
            psmt.setDouble(3, job.getMinSalary());
            psmt.setDouble(4, job.getMaxSalary());
            psmt.execute();
            jobs.add(job);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Job job, String[] params) {
        String id = params[0];
        String title = params[1];
        double minSalary = Double.parseDouble(params[2]);
        double maxSalary = Double.parseDouble(params[2]);
        
        job.setJobId(Objects.requireNonNull(
                id, "Job id cannot be null"));
        job.setTitle(Objects.requireNonNull(
                title, "Title cannot be null"));
        job.setMinSalary(Objects.requireNonNull(
                minSalary, "Min salary cannot be null"));
        job.setMaxSalary(Objects.requireNonNull(
                minSalary, "Max salary cannot be null"));
        try {
            PreparedStatement psmt = connection.prepareStatement("UPDATE job SET id=?, title=?, min_salary=?, max_salary=? WHERE id=?");
            psmt.setString(1, id);
            psmt.setString(2, title);
            psmt.setDouble(3, minSalary);
            psmt.setDouble(4, maxSalary);
            psmt.setString(5, job.getJobId());
            psmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Job job) {
        try {
            PreparedStatement psmt = connection.prepareStatement("DELETE FROM job WHERE id=?");
            psmt.setString(1, job.getJobId());
            psmt.execute();
            jobs.remove(job);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
