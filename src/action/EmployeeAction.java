/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.EmployeeDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import models.Employee;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class EmployeeAction implements Action {

    private int employeeId, manager, department;
    private String firstName, lastName, email, phoneNumber, job, hireDateStr;
    private Date hireDate;
    private double salary, comissionPct;

    private static Dao<Employee> employeeDao;

    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public EmployeeAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        employeeDao = new EmployeeDao(connection.getConnection());
    }

    @Override
    public void show() {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        employeeDao.getAll().forEach(e -> {
            hireDateStr = DateFor.format(e.getHireDate());
            System.out.println(
                    e.getEmployeeId() + " | " + e.getFirstName() + " | " + e.getLastName() + " | "
                            + e.getEmail() + " | " + e.getPhoneNumber() + " | " + hireDateStr + " | "
                            + e.getJob() + " | " + e.getSalary() + " | " + e.getComissionPct() + " | " + e.getManager() + " | " + e.getDepartment()
            );
        });
    }

    @Override
    public void insert() {
        try {
            System.out.print("First name: ");
            firstName = objStr.nextLine();
            System.out.print("Last name: ");
            lastName = objStr.nextLine();
            System.out.print("Email: ");
            email = objStr.nextLine();
            System.out.print("Phone number: ");
            phoneNumber = objStr.nextLine();
            System.out.print("Hire date(YYYY-MM-DD): ");
            hireDateStr = objStr.nextLine();
            hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(hireDateStr);
            System.out.print("Job id: ");
            job = objStr.nextLine();
            System.out.print("Salary: ");
            salary = obj.nextDouble();
            System.out.print("Comission PCT: ");
            comissionPct = obj.nextDouble();
            System.out.print("Manager: ");
            manager = obj.nextInt();
            System.out.print("Department id: ");
            department = obj.nextInt();
            employeeDao.save(new Employee(firstName, lastName, email, phoneNumber, hireDate, job, salary, comissionPct, manager, department));
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            System.out.print("Update employee(Id): ");
            employeeId = obj.nextInt();
            Employee employee = getEmployee(employeeId);
            System.out.print("Change first name: ");
            firstName = objStr.nextLine();
            System.out.print("Change last name: ");
            lastName = objStr.nextLine();
            System.out.print("Change email: ");
            email = objStr.nextLine();
            System.out.print("Change phone number: ");
            phoneNumber = objStr.nextLine();
            System.out.print("Cahnge hire date(YYYY-MM-DD): ");
            hireDateStr = objStr.nextLine();
            hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(hireDateStr);
            System.out.print("Change job id: ");
            job = objStr.nextLine();
            System.out.print("Change salary: ");
            salary = obj.nextDouble();
            System.out.print("Change comission PCT: ");
            comissionPct = obj.nextDouble();
            System.out.print("Change manager: ");
            manager = obj.nextInt();
            System.out.print("Change department id: ");
            department = obj.nextInt();
            employeeDao.update(employee, new String[]{firstName, lastName, email, phoneNumber, hireDateStr, job, String.valueOf(salary), String.valueOf(comissionPct), String.valueOf(manager), String.valueOf(department)});
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.print("Delete department(Id): ");
        employeeId = obj.nextInt();
        Employee employee = getEmployee(employeeId);
        employeeDao.delete(employee);
    }
    
    private static Employee getEmployee(int id) {
        List<Employee> employees = employeeDao.getAll();
        int countryId = IntStream.range(0, employees.size())
                .filter(i -> employees.get(i).getEmployeeId() == id)
                .findFirst()
                .getAsInt();

        Optional<Employee> employee = employeeDao.get(countryId);
        return employee.orElseGet(() -> new Employee("n/a", "n/a", "n/a", "n/a",new Date(), "n/a", 0, 0, 0, 0));
    }
}
