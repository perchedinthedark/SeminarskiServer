/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker;

/**
 *
 * @author Korisnik
 */

import domain.DomainObject;
import domain.Gost;
import domain.Rezervacija;
import domain.Soba;
import domain.Zaposleni;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import so.Login;


public class DBBroker {

    private final Connection konekcija;
    
    public DBBroker(Connection connection) {
        this.konekcija = connection;
    }

    public Connection getKonekcija() {
        return konekcija;
    }

    public Zaposleni getUser(Zaposleni zaposleni) throws Exception {
        
        String query = "SELECT id, ime, prezime, korime, lozinka FROM zaposleni WHERE korime=? AND lozinka=?";
        
        PreparedStatement ps = konekcija.prepareStatement(query);
        ps.setString(1, zaposleni.getUsername());
        ps.setString(2, zaposleni.getPassword());
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        
            zaposleni.setId(rs.getLong("id"));
            zaposleni.setIme(rs.getString("ime"));
            zaposleni.setPrezime(rs.getString("prezime"));
            
        }
        else{
            zaposleni = null;
                    }
        
        rs.close();
        ps.close();
        
           return zaposleni;
        }
        
      public void add(DomainObject domainObject) throws SQLException {
        try {
//            String query = "INSERT INTO person (firstname, lastname, birthday, city, gender, married) VALUES (?,?,?,?,?,?)";
            String query = "INSERT INTO " + domainObject.getTableName()
                    + " (" + domainObject.getColumnsForInsert() + ") VALUES " + domainObject.getParamsForInsert();

            System.out.println("Upit: " + query);

            //Pravljenje objekta koji je odgovoran za izvrsavanje upita
            PreparedStatement statement = konekcija.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            //postavljanje vrednosti parametara
            domainObject.setParamsForInsert(statement, domainObject);

            //izvsi upit
            int result = statement.executeUpdate();
            //System.out.println("Result = " + result);
            System.out.println("Objekat uspesno dodat u bazu!");

            //pristup generisanom kljucu
            if (domainObject.containsAutoIncrementPK()) {
                ResultSet rsID = statement.getGeneratedKeys();
                if (rsID.next()) {
                    //person.setPersonID(rsID.getLong(1));
                    domainObject.setAutoIncrementPrimaryKey(rsID.getLong(1));
                }
                rsID.close();
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Neuspesno dodavanje objekta u bazu!\n" + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }
      
      public DomainObject read(DomainObject domainObject) throws SQLException {
    DomainObject resultObject = null;
    try {
        String query = "SELECT * FROM " + domainObject.getTableName() + " WHERE id=" + domainObject.getId();
        
        PreparedStatement statement = konekcija.prepareStatement(query);
        
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            resultObject = domainObject.readResultSet(resultSet);
            System.out.println("Objekat uspesno procitan iz baze!");
        } else {
            System.out.println("Objekat nije pronadjen.");
        }
        
        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        System.out.println("Neuspesno citanje objekta iz baze!\n" + ex.getMessage());
        ex.printStackTrace();
        throw ex;
    }
    return resultObject;
}
      
    public <T extends DomainObject> List<T> searchByColumn(T sampleDomainObject, String columnName, String searchString) throws SQLException {
    List<T> results = new ArrayList<>();
    String query = "SELECT * FROM " + sampleDomainObject.getTableName() + " WHERE " + columnName + " LIKE ?";

    try (PreparedStatement statement = konekcija.prepareStatement(query)) {
        statement.setString(1, "%" + searchString + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            T object = (T)sampleDomainObject.readResultSet(resultSet);
            results.add(object);
        }

        resultSet.close();
    } catch (SQLException ex) {
        System.out.println("Failed to search for objects in the database.\n" + ex.getMessage());
        ex.printStackTrace();
        throw ex;
    }
    return results;
}
    
    public List<Rezervacija> searchReservationsByColumn(String columnName, String searchString) throws SQLException {
    List<Rezervacija> results = new ArrayList<>();
    String query = "SELECT * FROM rezervacija WHERE " + columnName + " LIKE ?";

    try (PreparedStatement statement = konekcija.prepareStatement(query)) {
        statement.setString(1, "%" + searchString + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Rezervacija rezervacija = new Rezervacija();
            // Assuming you have a method in Rezervacija to populate fields from ResultSet
            rezervacija.setId(resultSet.getLong("id"));
            
            // Now fetch and attach the related objects
            Gost gost = getGostById(resultSet.getLong("gostId"));
            Soba soba = getSobaById(resultSet.getLong("sobaId"));
            Zaposleni zaposleni = getZaposleniById(resultSet.getLong("zaposleniId"));

            rezervacija.setGost(gost);
            rezervacija.setSoba(soba);
            rezervacija.setZaposleni(zaposleni);
            rezervacija.setDatumPrijave(resultSet.getDate("datumPrijave"));
            rezervacija.setDatumOdjave(resultSet.getDate("datumOdjave"));
            rezervacija.setUkupnaCena(resultSet.getDouble("ukupnaCena"));

            results.add(rezervacija);
        }
    } catch (SQLException ex) {
        System.out.println("Failed to search for reservations in the database.\n" + ex.getMessage());
        ex.printStackTrace();
        throw ex;
    }
    return results;
}
      
      public void update(DomainObject domainObject) throws SQLException {
    try {
        String query = "UPDATE " + domainObject.getTableName()
                + " SET " + domainObject.getColumnsForUpdate()
                + " WHERE id=?";

        PreparedStatement statement = konekcija.prepareStatement(query);

      
        int index = domainObject.setParamsForUpdate(statement, domainObject);
        statement.setLong(index, domainObject.getId()); 

        int result = statement.executeUpdate();
        System.out.println("Objekat uspesno azuriran u bazi!");

        statement.close();
    } catch (SQLException ex) {
        System.out.println("Neuspesno azuriranje objekta u bazi!\n" + ex.getMessage());
        ex.printStackTrace();
        throw ex;
    }
}
      
      public void delete(DomainObject domainObject) throws SQLException {
    try {
        String query = "DELETE FROM " + domainObject.getTableName() + " WHERE id=?";

        PreparedStatement statement = konekcija.prepareStatement(query);
        statement.setLong(1, domainObject.getId()); 

        int result = statement.executeUpdate();
        System.out.println("Objekat uspesno obrisan iz baze!");

        statement.close();
    } catch (SQLException ex) {
        System.out.println("Neuspesno brisanje objekta iz baze!\n" + ex.getMessage());
        ex.printStackTrace();
        throw ex;
    }
}
      
      
    public List<Soba> getAllRooms() throws SQLException {
    List<Soba> rooms = new ArrayList<>();
    String sql = "SELECT * FROM soba;";
    try (
         PreparedStatement statement = konekcija.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Soba soba = new Soba();
          
            soba.setId(resultSet.getLong("id"));
            soba.setTip(resultSet.getString("tip"));
            soba.setBrojKreveta(resultSet.getLong("brojKreveta"));
            soba.setDostupna(resultSet.getBoolean("dostupna"));
            soba.setCena(resultSet.getDouble("cena"));
            soba.setPogled(resultSet.getString("pogled"));
            rooms.add(soba);
        }
    }
    return rooms;
}
    
    public List<Gost> getAllGuests() throws SQLException {
    List<Gost> guests = new ArrayList<>();
    String sql = "SELECT * FROM gost;";
    try (
         PreparedStatement statement = konekcija.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Gost gost = new Gost();
            // Populate gost object
            gost.setId(resultSet.getLong("id"));
            gost.setIme(resultSet.getString("ime"));
            gost.setPrezime(resultSet.getString("prezime"));
            gost.setKontakt(resultSet.getString("kontakt"));
            gost.setPopust(resultSet.getDouble("popust"));
            guests.add(gost);
        }
    }
    return guests;
}
    
    public List<Rezervacija> getAllReservations() throws SQLException {
      
    List<Rezervacija> reservations = new ArrayList<>();
    String sql = "SELECT * FROM rezervacija;";
    try (
         PreparedStatement statement = konekcija.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Rezervacija rezervacija = new Rezervacija();
            
            rezervacija.setId(resultSet.getLong("id"));

            
            Gost gost = getGostById(resultSet.getLong("gostId"));
            Soba soba = getSobaById(resultSet.getLong("sobaId"));
            Zaposleni zaposleni = getZaposleniById(resultSet.getLong("zaposleniId"));

            rezervacija.setGost(gost);
            rezervacija.setSoba(soba);
            rezervacija.setZaposleni(zaposleni);
            rezervacija.setDatumPrijave(resultSet.getDate("datumPrijave"));
            rezervacija.setDatumOdjave(resultSet.getDate("datumOdjave"));
            rezervacija.setUkupnaCena(resultSet.getDouble("ukupnaCena"));

            reservations.add(rezervacija);
        }
    }
    return reservations;
}

    private Gost getGostById(long aLong) throws SQLException {
    Gost gost = new Gost();
    gost.setId(aLong); 
    return (Gost) read(gost);
    }

    private Soba getSobaById(long aLong) throws SQLException {
             Soba soba = new Soba();
             soba.setId(aLong); 
             return (Soba) read(soba);
    }

    private Zaposleni getZaposleniById(long aLong) throws SQLException {
      Zaposleni zaposleni = new Zaposleni();
      zaposleni.setId(aLong); 
      return (Zaposleni) read(zaposleni);
    }
    
     

    }



    
    
    

