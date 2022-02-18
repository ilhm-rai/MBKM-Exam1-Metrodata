/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RAI
 */
public class DbConnection {
    private Connection con;
    
    public Connection getConnection() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hr?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return con;
    }
}
