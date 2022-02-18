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
import models.Country;

/**
 *
 * @author RAI
 */
public class CountryDao implements Dao<Country> {

    private final Connection connection;
    private List<Country> countries = new ArrayList<>();

    public CountryDao(Connection connection) {
        this.connection = connection;
        String query = "SELECT * FROM country";
        try {
            ResultSet resultSet = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (resultSet.next()) {
                countries.add(new Country(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(countries.get((int) --id));
    }
    
    @Override
    public List getAll() {
        return countries;
    }

    @Override
    public void save(Country country) {
        try {
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO country(id, name, region) VALUES(?,?,?)");
            psmt.setString(1, country.getCountryId());
            psmt.setString(2, country.getName());
            psmt.setInt(3, country.getRegion());
            psmt.execute();
            countries.add(country);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Country country, String[] params) {
        String id = params[0];
        String name = params[1];
        int region = Integer.parseInt(params[2]);
        
        country.setCountryId(Objects.requireNonNull(
                id, "Country id cannot be null"));
        country.setName(Objects.requireNonNull(
                name, "Country name cannot be null"));
        country.setRegion(Objects.requireNonNull(
                region, "0"));
        try {
            PreparedStatement psmt = connection.prepareStatement("UPDATE country SET id=?, name=?, region=? WHERE id=?");
            psmt.setString(1, id);
            psmt.setString(2, name);
            psmt.setInt(3, region);
            psmt.setString(4, country.getCountryId());
            psmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Country country) {
        try {
            PreparedStatement psmt = connection.prepareStatement("DELETE FROM country WHERE id=?");
            psmt.setString(1, country.getCountryId());
            psmt.execute();
            countries.remove(country);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
