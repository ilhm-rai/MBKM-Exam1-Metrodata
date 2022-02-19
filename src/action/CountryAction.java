/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import daos.Dao;
import daos.CountryDao;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import models.Country;
import tools.DbConnection;

/**
 *
 * @author RAI
 */
public class CountryAction implements Action {

    private static String id, name;
    private static int region;

    private static Dao<Country> countryDao;

    private static final Scanner obj = new Scanner(System.in);
    private static final Scanner objStr = new Scanner(System.in);

    public CountryAction() {
        DbConnection connection = new DbConnection();
        System.out.println(connection.getConnection());

        countryDao = new CountryDao(connection.getConnection());
    }

    @Override
    public void show() {
        countryDao.getAll().forEach(country -> System.out.println(
                country.getCountryId() + " | " + country.getName() + " | " + country.getRegion()
        ));
    }

    @Override
    public void insert() {
        System.out.print("Id: ");
        id = objStr.nextLine();
        System.out.print("Country name: ");
        name = objStr.nextLine();
        System.out.print("Region id: ");
        region = obj.nextInt();
        countryDao.save(new Country(id, name, region));
    }

    @Override
    public void update() {
        System.out.print("Update country(Id): ");
        id = objStr.nextLine();
        Country country = getCountry(id);
        System.out.println(country.getCountryId() + " | " + country.getName() + " | " + country.getRegion());
        System.out.print("Change id: ");
        String changedId = objStr.nextLine();
        System.out.print("Change country name: ");
        name = objStr.nextLine();
        System.out.print("Change region id: ");
        region = obj.nextInt();
        countryDao.update(country, new String[] {changedId, name, String.valueOf(region)});
    }

    @Override
    public void delete() {
        System.out.print("Delete country(Id): ");
        id = objStr.nextLine();
        Country country = getCountry(id);
        countryDao.delete(country);
    }

    private static Country getCountry(String id) {
        List<Country> countries = countryDao.getAll();
        int countryId = IntStream.range(0, countries.size())
                .filter(i -> countries.get(i).getCountryId().equalsIgnoreCase(id))
                .findFirst()
                .getAsInt();
        
        Optional<Country> country = countryDao.get(countryId);
        return country.orElseGet(() -> new Country("ne","non-existing country", 0));
    }
}
