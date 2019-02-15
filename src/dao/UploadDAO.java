/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import entidade.UploadOficio;
import util.EntityManagerUtil;

/**
 *
 * @author f5078775
 */
public class UploadDAO implements  Serializable{
    
    
    
    
    public void salvar(UploadOficio uploadOficio){
        
        
        EntityManager em = EntityManagerUtil.getEntityManager();
        
        try{
              em.getTransaction().begin();
              em.persist(uploadOficio);
              em.getTransaction().commit();
            
        }catch(Exception e ){
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Erro ao salvar  nome de arquivo no banco arquivo" + e);
        }
      
        
        
    }
    
    
    
    
}
