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
import models.Location;

/**
 *
 * @author RAI
 */
public class LocationDao implements Dao<Location> {

    private final Connection connection;
    private List<Location> locations = new ArrayList<>();

    public LocationDao(Connection connection) {
        this.connection = connection;

        String query = "SELECT * FROM department";
        try {
            ResultSet rs = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (rs.next()) {
                locations.add(new Location(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Location> get(long id) {
        return Optional.ofNullable(locations.get((int) id));
    }

    @Override
    public List<Location> getAll() {
        return locations;
    }

    @Override
    public void save(Location location) {
        try {
            int id = this.generateId();
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO location(id, street_address, postal_code, city, state_province, country) VALUES(?,?,?,?,?,?)");
            psmt.setInt(1, id);
            psmt.setString(2, location.getStreetAddress());
            psmt.setString(3, location.getPostalCode());
            psmt.setString(4, location.getCity());
            psmt.setString(5, location.getStateProvince());
            psmt.setString(6, location.getCountry());
            psmt.execute();
            location.setLocationId(id);
            locations.add(location);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Location location, String[] params) {
        String streetAddress = params[0],
                postalCode = params[1],
                city = params[2],
                stateProvince = params[3],
                country = params[4];
        
        location.setStreetAddress(Objects.requireNonNull(
                streetAddress, "Street address cannot be null"));
        location.setPostalCode(Objects.requireNonNull(
                postalCode, "Postal code cannot be null"));
        location.setCity(Objects.requireNonNull(
                city, "City cannot be null"));
        location.setStateProvince(Objects.requireNonNull(
                stateProvince, "State province cannot be null"));
        location.setCountry(Objects.requireNonNull(
                country, "Country cannot be null"));

        try {
            PreparedStatement psmt = connection.prepareStatement("UPDATE location SET street_address=?, postal_code=?, city=?, state_province=?, country=? WHERE id=?");
            psmt.setString(1, location.getStreetAddress());
            psmt.setString(2, location.getPostalCode());
            psmt.setString(3, location.getCity());
            psmt.setString(4, location.getStateProvince());
            psmt.setString(5, location.getCountry());
            psmt.setInt(6, location.getLocationId());
            psmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Location location) {
        try {
            PreparedStatement psmt = connection.prepareStatement("DELETE FROM location WHERE id=?");
            psmt.setInt(1, location.getLocationId());
            psmt.execute();
            locations.remove(location);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int generateId() {
        String query = "SELECT id FROM location ORDER BY id DESC LIMIT 1";
        try {
            ResultSet resultSet = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1) + 100;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 1000;
    }

}
