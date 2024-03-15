/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.DomainObject;
import domain.Soba;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class ReadRoom extends AbstractSO{
    
   private List<Soba> sobe = new ArrayList<>();
   Soba soba = new Soba();
    
    
    
    public ReadRoom() throws Exception{
    super();}

    @Override
    protected void executeOperation(Object object) throws Exception {
        sobe = broker.searchByColumn(soba, "tip", (String) object);
    }

    @Override
    protected void validate(Object object) throws Exception {
      /*  if (!(object instanceof Soba)) {
            throw new Exception("Object is not valid");
        }*/
    }

    public Soba getSoba() {
        return soba;
    }

    public List<Soba> getSobe() {
        return sobe;
    }
    
    


    
    
}
