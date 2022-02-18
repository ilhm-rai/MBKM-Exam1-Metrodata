package action;

import daos.Dao;
import daos.RegionDao;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import models.Region;
import tools.DbConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RAI
 */
public class Main {

    private static Action tableAction;

    public static void main(String[] args) {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        boolean stop = false;
        boolean stopMethod = false;

        Scanner obj = new Scanner(System.in);

        while (!stop) {
            System.out.println("Choose table menu");
            System.out.println("(1) Region\n"
                    + "(2) Country\n"
                    + "(3) Location\n"
                    + "(4) Department\n"
                    + "(5) Job\n"
                    + "(6) Employee\n"
                    + "(7) Exit");

            int table = obj.nextInt();

            switch (table) {
                case 1:
                    tableAction = new RegionAction();
                    break;
                case 7: {
                    stop = true;
                    break;
                }
                default:
                    System.out.println("Method doesn't exist!");
                    break;
            }

            while (!stopMethod) {
                System.out.println("Choose method menu");
                System.out.println("(1) Show\n"
                        + "(2) Insert\n"
                        + "(3) Update\n"
                        + "(4) Delete\n"
                        + "(5) Change table\n"
                        + "(6) Exit");

                int method = obj.nextInt();

                switch (method) {
                    case 1: {
                        tableAction.show();
                        break;
                    }
                    case 2: {
                        tableAction.insert();
                        break;
                    }
                    case 3: {
                        tableAction.update();
                        break;
                    }
                    case 4: {
                        tableAction.delete();
                        break;
                    }
                    case 5: {
                        stopMethod = true;
                        break;
                    }
                    case 6: {
                        stop = true;
                        stopMethod = true;
                        break;
                    }
                    default: {
                        System.out.println("Method doesn't exist!");
                        break;
                    }
                }
            }
        }
    }
}
