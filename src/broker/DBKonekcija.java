/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class DBKonekcija {
    
    private List<Connection> konekcije;
    private static DBKonekcija instance;
    
    public static DBKonekcija getInstance() throws SQLException{
    
        if(instance==null){
        instance=new DBKonekcija();}
        return instance;
    
    }
    
    
    private DBKonekcija() throws SQLException{
    
    
        konekcije = new ArrayList<>();
         for (int i = 0; i < 100; i++) {
            try {
                String url = "jdbc:mysql://localhost/prosoft";
                String user = "root";
                String password = "";
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println("Konekcija sa bazom podataka uspesno uspostavljena!");

                //iskljucujemo automatsko potvrdjivanje transakcije nakon svakog upita
                connection.setAutoCommit(false);
                konekcije.add(connection);
            } catch (SQLException ex) {
                System.out.println("Greska! Konekcija sa bazom nije uspesno uspostavljena!\n" + ex.getMessage());
                ex.printStackTrace();
                throw ex;
            }
        }
         
        
    }
    
    public synchronized void push(Connection connection) {
    konekcije.add(connection);
    }
    
     public synchronized Connection pop() throws Exception {
        if (konekcije.isEmpty()) {
            throw new Exception("Nema slobodne konekcije");
        }
        Connection connection = konekcije.get(0);
        konekcije.remove(0);
        return connection;
    }
    
}
