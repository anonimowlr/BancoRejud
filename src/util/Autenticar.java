/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JOptionPane;
import entidade.Usuario;

/**
 *
 * @author f4281065
 */
public class Autenticar {

    Usuario user = new Usuario();
    

    public void autenticaUser() {
        
        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário autenticado!!");
            System.exit(0);
        }

        if (!user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            JOptionPane.showMessageDialog(null, "Usuário sem Perfil de acesso para esta rotina!!");
            System.exit(0);
        }

    }
    
    

    
}
