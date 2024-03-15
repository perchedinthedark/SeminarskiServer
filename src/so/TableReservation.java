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
public class TableReservation extends AbstractSO{
    
    private List<Rezervacija> rezervacije = new ArrayList<>();
    
    

    public TableReservation() throws Exception {
        super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        rezervacije = broker.getAllReservations();
    }

    @Override
    protected void validate(Object object) throws Exception {
        
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    
    
    
}
