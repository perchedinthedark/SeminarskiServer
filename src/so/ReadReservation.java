/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.DomainObject;
import domain.Rezervacija;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class ReadReservation extends AbstractSO{
    
    private Rezervacija rez = new Rezervacija();
     private List<Rezervacija> rezervacije = new ArrayList<>();
     
     
    
    

    public ReadReservation() throws Exception {
        super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
      rezervacije = broker.searchReservationsByColumn("datumOdjave", (String) object);
    }

    @Override
    protected void validate(Object object) throws Exception {
        
    }

    public Rezervacija getRez() {
        return rez;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    
    
    
}
