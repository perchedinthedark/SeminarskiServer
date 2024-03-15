/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import communication.Odgovor;
import communication.Operacija;
import communication.Receiver;
import communication.Sender;
import communication.Zahtev;
import domain.Gost;
import domain.Rezervacija;
import domain.Soba;
import domain.Zaposleni;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Kontroler;

/**
 *
 * @author Korisnik
 */
public class ClientThread extends Thread{
    
    Socket soket;
    ServerStart ss;
    Receiver receiver;
    Sender sender;
    private Zaposleni loggedIn;
    

    ClientThread(Socket soket, ServerStart aThis) {
        this.soket = soket;
        this.ss = aThis;
        receiver = new Receiver(soket);
        sender = new Sender(soket);
    }

    @Override
    public void run() {
        
        while (true) {            
            
            
            try {
                Zahtev zahtev = (Zahtev) receiver.primiOdgovor();
                Odgovor odgovor = new Odgovor();
                
                switch(zahtev.getOperacija()){
                
                    case LOGIN:{
                    
                        Zaposleni user = (Zaposleni) zahtev.getArgument();
                  
                        if(Kontroler.getInstance().nijeUlogovan(user)){
                        loggedIn = user;
                        Zaposleni ulogovani = Kontroler.getInstance().login(user);
                        odgovor.setRezultat(ulogovani);
                        }
                        else{
                        Zaposleni duplikat = new Zaposleni();
                        duplikat.setIme("duplikat");
                        odgovor.setRezultat(duplikat);
                        }
                        odgovor.setOperacija(Operacija.LOGIN);
                        sender.posalji(odgovor);
                    
                    
                    break;
                    }
                    
                    case C_SOBA:{
                    
                        Soba soba = (Soba) zahtev.getArgument();
                        if(soba.getTip().isEmpty()|| soba.getPogled().isEmpty() || soba.getCena() < 0 || soba.getBrojKreveta() < 0 || 
                                soba.getBrojKreveta()>8){
                          odgovor.setRezultat(null);
                        }
                        else{
                         Kontroler.getInstance().createRoom(soba);
                         odgovor.setRezultat(soba);
                        }
                       
                        odgovor.setOperacija(Operacija.C_SOBA);
                        sender.posalji(odgovor);
                    
                    break;
                    }
                    
                    case C_GOST:{
                    
                        Gost gost = (Gost) zahtev.getArgument();
                        if(gost.getIme().isEmpty() || gost.getPrezime().isEmpty() ||
                                gost.getKontakt().isEmpty() || gost.getPopust() < 0 || gost.getPopust()>1){
                        odgovor.setRezultat(null);
                        }
                        else{
                        Kontroler.getInstance().createGuest(gost);
                        odgovor.setRezultat(gost);
                        }
                        odgovor.setOperacija(Operacija.C_GOST);
                        sender.posalji(odgovor);
                    
                    break;
                    }
                    
                    case TABLE_SOBA:{
                    
                        List<Soba> sobe = Kontroler.getInstance().tableRoom();
                        odgovor.setOperacija(Operacija.TABLE_SOBA);
                        odgovor.setRezultat(sobe);
                        sender.posalji(odgovor);
                        break;
                    }
                    
                    case TABLE_GOST:{
                    
                    List<Gost> gosti = Kontroler.getInstance().tableGost();
                    odgovor.setOperacija(Operacija.TABLE_GOST);
                        odgovor.setRezultat(gosti);
                        sender.posalji(odgovor);
                        break;
                    
                    
                    }
                    
                    case R_SOBA:{ 
                    
                    String filter = (String) zahtev.getArgument();
                    List<Soba> sobe = (List<Soba>) Kontroler.getInstance().readTheRoom(filter);
                    odgovor.setOperacija(Operacija.R_SOBA);
                    odgovor.setRezultat(sobe);
                    sender.posalji(odgovor);
                    break;
                    
                    }
                    
                    case R_GOST:{ 
                    
                    String filter = (String) zahtev.getArgument();
                    List<Gost> gosti = (List<Gost>) Kontroler.getInstance().readTheGuest(filter);
                    odgovor.setOperacija(Operacija.R_GOST);
                    odgovor.setRezultat(gosti);
                    sender.posalji(odgovor);
                    break;
                    
                    }
                    
                    case U_GOST:{
                    
                    Gost gost = (Gost) zahtev.getArgument();
                     if(gost.getIme().isEmpty() || gost.getPrezime().isEmpty() ||
                                gost.getKontakt().isEmpty() || gost.getPopust() < 0 || gost.getPopust()>1){
                        odgovor.setRezultat(null);
                        }
                        else{
                        Kontroler.getInstance().updateGuest(gost);
                        odgovor.setRezultat(gost);
                        }
                        odgovor.setOperacija(Operacija.U_GOST);
                        sender.posalji(odgovor);
                    break;
                    
                    }
                    
                    case U_SOBA:{
                    
                        Soba soba = (Soba) zahtev.getArgument();
                        if(soba.getTip().isEmpty()|| soba.getPogled().isEmpty() || soba.getCena() < 0 || soba.getBrojKreveta() < 0 || 
                                soba.getBrojKreveta()>8){
                          odgovor.setRezultat(null);
                        }
                        else{
                        Kontroler.getInstance().updateRoom(soba);
                        odgovor.setRezultat(soba);}
                        odgovor.setOperacija(Operacija.U_SOBA);
                        sender.posalji(odgovor);
                        break;
                    
                    }
                    
                    case BOX_SOBA:{
                    
                        List<Soba> sobe = Kontroler.getInstance().tableRoom();
                        odgovor.setOperacija(Operacija.BOX_SOBA);
                        odgovor.setRezultat(sobe);
                        sender.posalji(odgovor);
                        break;
                    
                    
                    }
                    
                    case BOX_GOST:{
                    
                    List<Gost> gosti = Kontroler.getInstance().tableGost();
                    odgovor.setOperacija(Operacija.BOX_GOST);
                    odgovor.setRezultat(gosti);
                    sender.posalji(odgovor);
                    break;
                    
                    }
                    
                    case C_REZEERVACIJA:{
                    
                        Rezervacija rez = (Rezervacija) zahtev.getArgument();
                        if(rez.getDatumOdjave().before(rez.getDatumPrijave())){
                        odgovor.setRezultat(null);
                        }
                        else{
                        Kontroler.getInstance().createReservation(rez);
                        odgovor.setRezultat(rez);
                        }
                        odgovor.setOperacija(Operacija.C_REZEERVACIJA);
                        sender.posalji(odgovor);
                        break;
                    
                    
                    }
                    
                    case TABLE_REZERVACIJA:{
                        
                       
                        List<Rezervacija> rezervacije = Kontroler.getInstance().tableRezervacije();
                        odgovor.setOperacija(Operacija.TABLE_REZERVACIJA);
                        odgovor.setRezultat(rezervacije);
                        sender.posalji(odgovor);
                        break;
                        
                    }
                    
                    case R_REZERVACIJA:{
                    
                    String filter = (String) zahtev.getArgument();
                    List<Rezervacija> rezervacije = (List<Rezervacija>) Kontroler.getInstance().readTheReservation(filter);
                    odgovor.setOperacija(Operacija.R_REZERVACIJA);
                    odgovor.setRezultat(rezervacije);
                    sender.posalji(odgovor);
                    break;
                    
                    
                    }
                    
                    case D_REZERVACIJA:{
                    
                    Rezervacija rezervacija =  (Rezervacija) zahtev.getArgument();
                    Kontroler.getInstance().deleteReservation(rezervacija);
                    odgovor.setRezultat(null);
                    odgovor.setOperacija(Operacija.D_REZERVACIJA);
                        sender.posalji(odgovor);
                    break;
                    }
                
                    case U_REZERVACIJA:{
                    Rezervacija rezervacija =  (Rezervacija) zahtev.getArgument();
                    if(rezervacija.getDatumOdjave().before(rezervacija.getDatumPrijave())){
                        odgovor.setRezultat(null);
                        }
                        else{
                        Kontroler.getInstance().updateReservation(rezervacija);
                        odgovor.setRezultat(rezervacija);
                        }
                        odgovor.setOperacija(Operacija.U_REZERVACIJA);
                        sender.posalji(odgovor);
                        break;
                    
                    
                    }
                    
                    case LOGOUT:{
                    Zaposleni zaposleni = (Zaposleni) zahtev.getArgument();
                    Kontroler.getInstance().logout(zaposleni);
                    odgovor.setOperacija(Operacija.LOGOUT);
                    odgovor.setRezultat(null);
                    sender.posalji(odgovor);
                    break;
                    }
                
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        }
        
    }

    public Zaposleni getLoggedIn() {
        return loggedIn;
    }
    
    
}
