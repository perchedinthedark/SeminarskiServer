/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.Soba;

/**
 *
 * @author Korisnik
 */
public class SaveRoom extends AbstractSO{
    
    
    public SaveRoom() throws Exception {
    super();
}

    @Override
    protected void executeOperation(Object object) throws Exception {
        broker.add((Soba)object);
    }

    @Override
    protected void validate(Object object) throws Exception {
         if (!(object instanceof Soba)) {
            throw new Exception("Object is not valid");
        }
    }
    
    
    
}
