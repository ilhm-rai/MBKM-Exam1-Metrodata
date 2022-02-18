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

    private static long id;
    private static int count;
    private static String name;
    
    private static Dao<Region> regionDao;
    
    public static void main(String[] args) {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        regionDao = new RegionDao(connection.getConnection());

        boolean stop = false;
        
        Scanner obj = new Scanner(System.in);
        Scanner objStr = new Scanner(System.in);

        while (!stop) {
            System.out.println("Choose method");
            System.out.println("(1) Show\n"
                    + "(2) Insert\n"
                    + "(3) Update\n"
                    + "(4) Delete\n"
                    + "(5) Change table\n"
                    + "(6) Exit");

            int method = obj.nextInt();

            switch (method) {
                case 1: {
                    regionDao.getAll().forEach(region -> System.out.println(
                        region.getRegionId() + " | " + region.getRegionName() + " | " + region.getCount()
                    ));
                    break;
                }
                case 2: {
                    name = objStr.nextLine();
                    count = obj.nextInt();
                    regionDao.save(new Region(name, count));
                    break;
                }
                case 3: {
                    id = obj.nextLong();
                    Region region = getRegion(id);
                    name = objStr.nextLine();
                    count = obj.nextInt();
                    regionDao.update(region, new String[]{name, String.valueOf(count)});
                    break;
                }
                case 4: {
                    id = obj.nextLong();
                    Region region = getRegion(id);
                    regionDao.delete(region);
                    break;
                }
                case 6: {
                    stop = true;
                    break;
                }
            }
        }
    }
    
    private static Region getRegion(long id) {
        Optional<Region> region = regionDao.get(id);
        return region.orElseGet(() -> new Region("non-existing region", 0));
    }
}
