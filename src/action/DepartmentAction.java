/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.DepartmentDao;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import models.Department;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class DepartmentAction implements Action {

    private static int id, manager, location;
    private static String name;

    private static Dao<Department> departmentDao;

    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public DepartmentAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        departmentDao = new DepartmentDao(connection.getConnection());
    }

    @Override
    public void show() {
        departmentDao.getAll().forEach(d -> System.out.println(
                d.getDepartmentId() + " | " + d.getDepartmentName() + " | " + d.getManager() + " | " + d.getLocation()
        ));
    }

    @Override
    public void insert() {
        System.out.print("Department name: ");
        name = objStr.nextLine();
        System.out.print("Manager: ");
        manager = obj.nextInt();
        System.out.print("Location id: ");
        location = obj.nextInt();
        departmentDao.save(new Department(name, manager, location));
    }

    @Override
    public void update() {
        System.out.println("Update department(Id): ");
        id = obj.nextInt();
        Department department = getDepartment(id);
        System.out.println(
                department.getDepartmentId() + " | " + department.getDepartmentName() + " | " + department.getManager() + " | " + department.getLocation()
        );
        System.out.print("Change department name: ");
        name = objStr.nextLine();
        System.out.print("Change manager: ");
        manager = obj.nextInt();
        System.out.print("Change location: ");
        location = obj.nextInt();
        departmentDao.update(department, new String[] {name, String.valueOf(manager), String.valueOf(location)});
    }

    @Override
    public void delete() {
        System.out.print("Delete department(Id): ");
        id = obj.nextInt();
        Department department = getDepartment(id);
        departmentDao.delete(department);
    }

    private static Department getDepartment(int id) {
        List<Department> departments = departmentDao.getAll();
        int countryId = IntStream.range(0, departments.size())
                .filter(i -> departments.get(i).getDepartmentId() == id)
                .findFirst()
                .getAsInt();

        Optional<Department> department = departmentDao.get(countryId);
        return department.orElseGet(() -> new Department("non-existing department", 0, 0));
    }

}
