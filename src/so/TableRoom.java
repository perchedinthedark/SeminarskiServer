/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.Soba;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class TableRoom extends AbstractSO{
    
    private List<Soba> sobe = new ArrayList<>();
    
    
    
    public TableRoom() throws Exception{
    super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        sobe = broker.getAllRooms();
    }

    @Override
    protected void validate(Object object) throws Exception {
        
    }

    public List<Soba> getSobe() {
        return sobe;
    }

   
    
}
