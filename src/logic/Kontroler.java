/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import domain.Gost;
import domain.Rezervacija;
import domain.Soba;
import domain.Zaposleni;
import java.util.ArrayList;
import java.util.List;
import server.ClientThread;
import so.DeleteReservation;
import so.Login;
import so.ReadGuest;
import so.ReadReservation;
import so.ReadRoom;
import so.SaveGuest;
import so.SaveReservation;
import so.SaveRoom;
import so.TableGuest;
import so.TableReservation;
import so.TableRoom;
import so.UpdateGuest;
import so.UpdateReservation;
import so.UpdateRoom;

/**
 *
 * @author Korisnik
 */
public class Kontroler {
    
    private List<ClientThread> klijenti = new ArrayList<>();
    
    private static Kontroler instance;
    
    public static Kontroler getInstance(){
    
        if(instance==null){
        
            instance = new Kontroler();
        }
        return instance;
    }
    
    private Kontroler(){}

    public List<ClientThread> getKlijenti() {
        return klijenti;
    }

    public void setKlijenti(List<ClientThread> klijenti) {
        this.klijenti = klijenti;
    }

    public boolean nijeUlogovan(Zaposleni user) {
         for (ClientThread k : klijenti) {
        Zaposleni loggedInUser = k.getLoggedIn();
        
        if (loggedInUser != null && loggedInUser.getUsername().equals(user.getUsername())
                && loggedInUser.getPassword().equals(user.getPassword())) {
            System.out.println("here");
            return false;
        }
    }
    return true;
    }

    public Zaposleni login(Zaposleni user) throws Exception {
        
        Login login = new Login();
        login.execute(user);
        return login.getKorisnik();
    }

    public void createRoom(Soba soba) throws Exception {
        SaveRoom save = new SaveRoom();
        save.execute(soba);
        
    }

    public void createGuest(Gost gost) throws Exception {
        SaveGuest save = new SaveGuest();
        save.execute(gost);
    }

    public List<Soba> tableRoom() throws Exception {
       TableRoom tbl = new TableRoom();
       tbl.execute(null);
       return tbl.getSobe();
    }

    public List<Gost> tableGost() throws Exception {
        TableGuest tbl = new TableGuest();
        tbl.execute(null);
        return tbl.getGosti();
    }

    public List<Soba> readTheRoom(String soba) throws Exception {
        
        ReadRoom read = new ReadRoom();
        read.execute(soba);
        return read.getSobe();
    }

    public List<Gost> readTheGuest(String gost) throws Exception {
        ReadGuest read = new ReadGuest();
        read.execute(gost);
        return read.getGosti();
    }

    public void updateGuest(Gost gost) throws Exception {
        UpdateGuest update = new UpdateGuest();
        update.execute(gost);
    }

    public void updateRoom(Soba soba) throws Exception {
        UpdateRoom update = new UpdateRoom();
        update.execute(soba);
    }

    public void createReservation(Rezervacija rez) throws Exception {
        SaveReservation save = new SaveReservation();
        save.execute(rez);
    }

    public List<Rezervacija> tableRezervacije() throws Exception {
        TableReservation table = new TableReservation();
        table.execute(null);
        return table.getRezervacije();
    }

    public List<Rezervacija> readTheReservation(String rezervacija) throws Exception {
        ReadReservation read = new ReadReservation();
        read.execute(rezervacija);
        return read.getRezervacije();
    }

    public void deleteReservation(Rezervacija rezervacija) throws Exception {
        DeleteReservation delete = new DeleteReservation();
        delete.execute(rezervacija);
    }

    public void updateReservation(Rezervacija rezervacija) throws Exception {
        UpdateReservation update = new UpdateReservation();
        update.execute(rezervacija);
    }

    public void logout(Zaposleni zaposleni) {
        for(ClientThread k : klijenti) {
        
            if(k.getLoggedIn().equals(zaposleni)){
            
                klijenti.remove(k);
                return;
            
            }
            
        }
    }

  
    
   
    
}
