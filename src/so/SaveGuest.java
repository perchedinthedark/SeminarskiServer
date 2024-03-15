/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.Gost;
import domain.Soba;

/**
 *
 * @author Korisnik
 */
public class SaveGuest extends AbstractSO{
    
    public SaveGuest() throws Exception {
    super();
}

     @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((Gost)object);
    }

    @Override
    protected void validate(Object object) throws Exception {
         if (!(object instanceof Gost)) {
            throw new Exception("Object is not valid");
        }
    }
}
