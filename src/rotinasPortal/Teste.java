/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasPortal;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import entidade.SolicitacaoLevantamento;



public class Teste extends Thread{

    @Override
    public void run() {
        for(int i = 0; i<10;i++){
            System.out.println("Teste Thread");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
   
    
    
  
}
