/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Local;
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@Local
public interface RowBuilderBeanLocal {
    String createRow(Client c);
}
