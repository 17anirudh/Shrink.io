package com.example.demo;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

@Component
public class Database {
    public void printSQLExceptionErroStatement(){
        System.out.println("Database connection not established");
    }
    public Connection openConnection(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/uri", "root", "root@2004");
            return conn;
        }
        catch (SQLTimeoutException e)
        {
            System.out.println("Timeout, please wait....");
            System.exit(0);
        } 
        catch (SQLException e) {
            printSQLExceptionErroStatement();
            System.exit(0);
        }
        return null;
    }
    public void insertValues(String key, String url){
        try {
            Connection conn = openConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO url (pack, link) VALUES (?, ?)");
            statement.setString(1, key);
            statement.setString(2, url);
            statement.executeUpdate();
            conn.close();
            statement.close();
        }
        catch (SQLException e){
            printSQLExceptionErroStatement();
            System.exit(0);
        }
    }
    public boolean keyExists(String key){
        try {
            Connection conn = openConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT pack from url WHERE pack = ?");
            statement.setString(1, key);
            ResultSet rs = statement.executeQuery();
            boolean found = rs.next();
            if (found) {
                conn.close();
                statement.close();
                rs.close();
                return true;
            }
            statement.close();
            rs.close();
            return false;
        }
        catch (SQLException e) {
            printSQLExceptionErroStatement();
            System.exit(0);
        }
        return false;
    }
    public String[] urlExists(String url){
        try {
            Connection conn = openConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT pack from url WHERE link = ?");
            statement.setString(1, url);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                conn.close();
                statement.close();
                rs.close();
                return new String[] {"true", rs.getString("pack")};
            }
            conn.close();
            statement.close();
            rs.close();
            return new String[]{"false", "nothing"};
        }
        catch (SQLException e) {
            printSQLExceptionErroStatement();
            System.exit(0);
        }
        return new String[]{"false", "nothing"};
    }
    public void showTable(){
        try {
            Connection conn = openConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM url;");
            ResultSet output = statement.executeQuery();
            while(output.next()){
                System.out.println(output.getString("pack") + "\t" + output.getString("link"));
            }
            conn.close();
            statement.close();
            output.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
}
