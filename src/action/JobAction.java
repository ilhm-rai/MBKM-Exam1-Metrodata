/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.JobDao;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import models.Job;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class JobAction implements Action {

    private String jobId, title;
    private double minSalary, maxSalary;
    
    private static Dao<Job> jobDao;

    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public JobAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        jobDao = new JobDao(connection.getConnection());
    }
    
    @Override
    public void show() {
        jobDao.getAll().forEach(j -> System.out.println(
                j.getJobId() + " | " + j.getTitle() + " | " + j.getMinSalary() + " | " + j.getMaxSalary()
        ));
    }

    @Override
    public void insert() {
        System.out.print("Id: ");
        jobId = objStr.nextLine();
        System.out.print("Title: ");
        title = objStr.nextLine();
        System.out.print("Title: ");
        minSalary = obj.nextDouble();
        System.out.print("Title: ");
        maxSalary = obj.nextDouble();
        jobDao.save(new Job(jobId, title, minSalary, maxSalary));
    }

    @Override
    public void update() {
        System.out.print("Update job(Id): ");
        jobId = objStr.nextLine();
        Job job = getJob(jobId);
        System.out.print("Change id: ");
        String changedJobId = objStr.nextLine();
        System.out.print("Title: ");
        title = objStr.nextLine();
        System.out.print("Title: ");
        minSalary = obj.nextDouble();
        System.out.print("Title: ");
        maxSalary = obj.nextDouble();
        jobDao.update(job, new String[] {String.valueOf(changedJobId), title, String.valueOf(minSalary), String.valueOf(maxSalary)});
    }

    @Override
    public void delete() {
        System.out.print("Delete job(Id): ");
        jobId = objStr.nextLine();
        Job job = getJob(jobId);
        jobDao.delete(job);
    }
    
    private static Job getJob(String id) {
        List<Job> countries = jobDao.getAll();
        int jobId = IntStream.range(0, countries.size())
                .filter(i -> countries.get(i).getJobId().equalsIgnoreCase(id))
                .findFirst()
                .getAsInt();
        
        Optional<Job> job = jobDao.get(jobId);
        return job.orElseGet(() -> new Job("n/a", "non-existing job", 0, 0));
    }
    
}
