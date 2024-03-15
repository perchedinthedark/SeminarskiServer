/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.DomainObject;
import domain.Rezervacija;

/**
 *
 * @author Korisnik
 */
public class UpdateReservation extends AbstractSO{

    public UpdateReservation() throws Exception {
        super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.update((DomainObject) object);
    }

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Rezervacija)) {
            throw new Exception("Object is not valid");
        }
    }
    
    
    
}
