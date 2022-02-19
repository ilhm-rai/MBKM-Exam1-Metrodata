/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.RegionDao;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import models.Region;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class RegionAction implements Action {

    private static long id;
    private static int count;
    private static String name;

    private static Dao<Region> regionDao;

    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public RegionAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        regionDao = new RegionDao(connection.getConnection());
    }

    @Override
    public void show() {
        regionDao.getAll().forEach(region -> System.out.println(
                region.getRegionId() + " | " + region.getRegionName() + " | " + region.getCount()
        ));
    }

    @Override
    public void insert() {
        System.out.print("Region name: ");
        name = objStr.nextLine();
        System.out.print("Count: ");
        count = obj.nextInt();
        regionDao.save(new Region(name, count));
    }

    @Override
    public void update() {
        System.out.print("Update region(Id): ");
        id = obj.nextLong();
        Region region = getRegion(id);
        System.out.println(
                region.getRegionId() + " | " + region.getRegionName() + " | " + region.getCount()
        );
        System.out.print("Change name: ");
        name = objStr.nextLine();
        System.out.print("Change count: ");
        count = obj.nextInt();
        regionDao.update(region, new String[]{name, String.valueOf(count)});
    }

    @Override
    public void delete() {
        System.out.print("Delete region(Id): ");
        id = obj.nextLong();
        Region region = getRegion(id);
        regionDao.delete(region);
    }
    
    private static Region getRegion(long id) {
        List<Region> regions = regionDao.getAll();
        int countryId = IntStream.range(0, regions.size())
                .filter(i -> regions.get(i).getRegionId() == id)
                .findFirst()
                .getAsInt();
        
        Optional<Region> region = regionDao.get(countryId);
        return region.orElseGet(() -> new Region("non-existing region", 0));
    }
}
