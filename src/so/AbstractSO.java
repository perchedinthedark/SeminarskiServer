/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import broker.DBBroker;
import broker.DBKonekcija;
import java.sql.SQLException;

/**
 *
 * @author student2
 */
public abstract class AbstractSO {

    protected DBBroker broker;

    public AbstractSO() throws Exception {
        broker = new DBBroker(DBKonekcija.getInstance().pop());
    }

    public void execute(Object object) throws Exception {
        try {
            validate(object);
            executeOperation(object);
            commit();
        } catch (Exception ex) {
            rollback();
            throw ex;
        }
    }

    private void commit() throws SQLException {
        broker.getKonekcija().commit();
    }

    private void rollback() throws SQLException {
        broker.getKonekcija().rollback();
    }

    protected abstract void executeOperation(Object object) throws Exception;

    protected abstract void validate(Object object) throws Exception;
}
