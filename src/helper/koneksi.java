/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class koneksi {
    static final String DB_URL = "jdbc:mysql://localhost/spkkualitasplastik";
    static final String DB_USER  = "root";
    static final String DB_PASS = "";
    
    private Connection connection;
    
    public  Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Berhasil koneksi");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            System.out.println("Berhasil Koneksi Database");
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
}
