/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.LocationDao;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import models.Location;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class LocationAction implements Action {
    
    private static int id;
    private static String streetAddress, postalCode, city, stateProvince, country;
    
    private static Dao<Location> locationDao;
    
    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public LocationAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());
        
        locationDao = new LocationDao(connection.getConnection());
    }
    
    
    @Override
    public void show() {
        locationDao.getAll().forEach(l -> System.out.println(
                l.getLocationId() + " | " + l.getStreetAddress() + " | " + l.getPostalCode() + " | " 
                        + l.getCity() + " | " +  l.getStateProvince() + " | " + l.getCountry()
        ));
    }

    @Override
    public void insert() {
        System.out.print("Street address: ");
        streetAddress = objStr.nextLine();
        System.out.print("Postal code: ");
        postalCode = objStr.nextLine();
        System.out.print("City: ");
        city = objStr.nextLine();
        System.out.print("State province: ");
        stateProvince = objStr.nextLine();
        System.out.print("Country: ");
        country = objStr.nextLine();
        locationDao.save(new Location(streetAddress, postalCode, city, stateProvince, country));
    }

    @Override
    public void update() {
        System.out.print("Update location(Id): ");
        id = obj.nextInt();
        Location location = getLocation(id);
        System.out.print("Change street address: ");
        streetAddress = objStr.nextLine();
        System.out.print("Change postal code: ");
        postalCode = objStr.nextLine();
        System.out.print("Change city: ");
        city = objStr.nextLine();
        System.out.print("Change state province: ");
        stateProvince = objStr.nextLine();
        System.out.print("Change country: ");
        country = objStr.nextLine();
        locationDao.update(location, new String[] {streetAddress, postalCode, city, stateProvince, country});
    }

    @Override
    public void delete() {
        System.out.print("Delete country(Id): ");
        id = obj.nextInt();
        Location location = getLocation(id);
        locationDao.delete(location);
    }
    
    private static Location getLocation(long id) {
        List<Location> locations = locationDao.getAll();
        int locationId = IntStream.range(0, locations.size())
                .filter(i -> locations.get(i).getLocationId() == id)
                .findFirst()
                .getAsInt();
        
        Optional<Location> location = locationDao.get(locationId);
        return location.orElseGet(() -> new Location("n/a", "n/a", "n/a", "n/a", "n/a"));
    }
    
}
