package com.example.demo;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Database {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet result = null;
    public Database(){
        openConnection();
    }
    public void openConnection(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://root:sdbYUGACuTRFNNkEMmSZUNMHeoaleEyl@shinkansen.proxy.rlwy.net:55957/railway");
            String sql = """
                CREATE TABLE IF NOT EXISTS url (
                    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    pack VARCHAR(20),
                    link TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """;
            stmt = conn.prepareStatement(sql);
            stmt.execute();
        }
        catch (SQLException e) {
            System.out.println("Can't connect");
            System.exit(0);
        }
    }
    public boolean keyExists(String key){
        try {
            stmt = conn.prepareStatement("SELECT pack from url WHERE pack = ?");
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            boolean found = rs.next();
            rs.close();
            stmt.close();
            return found;
        }
        catch (SQLException e) {
            System.out.println("Can't check if key exists");
            System.exit(0);
        }
        return false;
    }
    public String[] urlExists(String url){
        try {
            stmt = conn.prepareStatement("SELECT pack from url WHERE link = ?");
            stmt.setString(1, url);
            System.out.println(stmt.toString());
            result = stmt.executeQuery();
            if (result.next()){
                String a = result.getString("pack");
                conn.close();
                result.close();
                stmt.close();
                return new String[] {"true", a};
            }
            result.close();
            stmt.close();
            return new String[]{"false", "nothing"};
        }
        catch (SQLException e) {
            System.out.println("Can't check if url exists");
            System.exit(0);
        }
        return new String[]{"false", "nothing"};
    }
    public void insertValues(String key, String url){
        try {
            stmt = conn.prepareStatement("INSERT INTO url (pack, link) VALUES (?, ?)");
            stmt.setString(1, key);
            stmt.setString(2, url);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e){
            System.out.println("Can't insert values");
            System.exit(0);
        }
    }
    public void closeConnection(){
        try {
            if (result != null) result.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection");
        }
    }
}
