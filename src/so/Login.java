/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domain.Zaposleni;

/**
 *
 * @author Korisnik
 */
public class Login extends AbstractSO{
    
    private Zaposleni korisnik;

    public Zaposleni getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Zaposleni korisnik) {
        this.korisnik = korisnik;
    }
    
    
    
    public Login() throws Exception{
    super();
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        korisnik = broker.getUser((Zaposleni)object);
      
    }

    @Override
    protected void validate(Object object) throws Exception {
        if (!(object instanceof Zaposleni)) {
            throw new Exception("Object is not valid");
        }
    }
    
    
    
}
