/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.Gost;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class TableGuest extends AbstractSO{
    
    private List<Gost> gosti = new ArrayList<>();
    
    
    
    public TableGuest() throws Exception{
    super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        gosti = broker.getAllGuests();
    }

    @Override
    protected void validate(Object object) throws Exception {
    }

    public List<Gost> getGosti() {
        return gosti;
    }

  
}
