/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisbbgeneric;

import entidade.Usuario;

/**
 *
 * @author suporte
 */
public class LogarUsuarioSisbb {
    
    public void autenticar(String aplicativoSisbb,Janela3270 sisbb) throws Throwable {
        
        Usuario user = new Usuario();
        
        sisbb.aguardarInd(20, 39, "SISBB");
        sisbb.teclarAguardarTroca("@E");

        //sisbb.aguardar(1, 19, "SISBB");
        if(sisbb.copiar(1, 3, 5).equals("P0130")) {  
           sisbb.teclarAguardarTroca("@3");
        }

        if(sisbb.copiar(1, 3, 5).equals("P0013")) {  
           sisbb.teclarAguardarTroca("@E");
        }

        sisbb.aguardarInd(1, 16, "Sistema");
        sisbb.posicionar(13, 21);
        sisbb.teclar(user.getMatricula());
        sisbb.posicionar(14, 21);

        sisbb.aguardarInd(1, 2, "CIC");

        sisbb.posicionar(21, 19);
        sisbb.teclar("50");
        sisbb.teclarAguardarTroca("@E");

        sisbb.aguardarInd(15, 2, "Aplicativo");
        sisbb.posicionar(15, 14);
        sisbb.teclar(aplicativoSisbb);
        sisbb.teclarAguardarTroca("@E");

    }
    
}
