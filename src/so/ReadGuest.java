/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.DomainObject;
import domain.Gost;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class ReadGuest extends AbstractSO{
    
    private Gost gost = new Gost();
    
     private List<Gost> gosti = new ArrayList<>();
     
     
    
    
    public ReadGuest() throws Exception{
    
        super();
    
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        gosti = broker.searchByColumn(gost, "ime", (String)object);
    }

    @Override
    protected void validate(Object object) throws Exception {
       /*  if (!(object instanceof Gost)) {
            throw new Exception("Object is not valid");
        }*/
    }

    public Gost getGost() {
        return gost;
    }

    public List<Gost> getGosti() {
        return gosti;
    }
    
}
