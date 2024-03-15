/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Kontroler;

/**
 *
 * @author Korisnik
 */
public class ServerStart extends Thread{
    
    ServerSocket ss;
    private static boolean kraj = false;

    @Override
    public void run() {
        
        try {
            ss = new ServerSocket(9000);
            System.out.println("SOKET OTVOREN");
            
            while(!kraj){
            
                Socket soket = ss.accept();
                System.out.println("KLIJENT POVEZAN");
                
                ClientThread nit = new ClientThread(soket, this);
                Kontroler.getInstance().getKlijenti().add(nit);
                nit.start();
            
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
