/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tpi.prog2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ezequiel Taboada
 */
public class ConexionDB {
 private static final String URL = "jdbc:mysql://localhost:3306/food_store_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private ConexionDB() {        
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
