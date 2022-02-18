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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import models.Region;

/**
 *
 * @author RAI
 */
public class RegionDao implements Dao<Region> {

    private final Connection connection;
    private List<Region> regions = new ArrayList<>();

    public RegionDao(Connection connection) {
        this.connection = connection;

        String query = "SELECT * FROM region";
        try {
            ResultSet resultSet = this.connection
                    .prepareStatement(query)
                    .executeQuery();
            while (resultSet.next()) {
                regions.add(new Region(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Region> get(long id) {
        return Optional.ofNullable(regions.get((int) --id));
    }

    @Override
    public List<Region> getAll() {
        return regions;
    }

    @Override
    public void save(Region region) {
        int id = 0;
        try {
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO region(name, count) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, region.getRegionName());
            psmt.setInt(2, region.getCount());
            
            int affectedRows = psmt.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet rs = psmt.getGeneratedKeys()) {
                    if(rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            
            region.setRegionId(id);
            regions.add(region);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Region region, String[] params) {
        String name = params[0];
        int count = Integer.parseInt(params[1]);
        region.setRegionName(Objects.requireNonNull(
                name, "Region name cannot be null"));
        region.setCount(Objects.requireNonNull(
                count, "0"));
        try {
            PreparedStatement psmt = connection.prepareStatement("UPDATE region SET name=?, count=? WHERE id=?");
            psmt.setString(1, name);
            psmt.setInt(2, count);
            psmt.setInt(3, region.getRegionId());
            psmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Region region) {
        regions.remove(region);
    }

}
