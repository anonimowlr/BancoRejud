/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisbbgeneric;

import br.com.bb.jibm3270.RoboException;
import enumEntidade.ContaEnum;

/**
 *
 * @author f4281065
 */
public class CompeSisbb {
    
    ContaEnum contaEnum;

    public CompeSisbb(ContaEnum contaEnum) {
        this.contaEnum = contaEnum;
    }
        
    
    /**
     * Autenticação do usuário no SISBB para acessar aplicativo COMPE
     *
     * @param sisbb janela sisbb
     * @throws RoboException
     */
    public void autenticarCompe(Janela3270 sisbb) throws RoboException {

        sisbb.teclarAguardarTroca("@3");
        sisbb.aguardarInd(1, 2, "BB30");
        sisbb.posicionar(15, 14);
        sisbb.teclar("COMPE");
        sisbb.posicionar(16, 14);

    }

    /**
     * Interação posicionamento janela sisbb para TED ou DOC
     * 
     * @param sisbb janela sisbb
     * @param opcao 07 para DOC ou 14 para TED
     * @throws RoboException 
     */
    public void posicionarTedDoc(Janela3270 sisbb, String opcao) throws RoboException {
        
        sisbb.aguardarInd(1, 3, "CELM800A");
        sisbb.posicionar(21, 35);
        sisbb.teclar(opcao);
        sisbb.teclarAguardarTroca("@E");

        if (sisbb.copiar(1, 3, 7).equals("CELMMSG")) {
            sisbb.teclarAguardarTroca("@3");
        }

        sisbb.aguardarInd(1, 3, "TFIM0000");
        sisbb.posicionar(21, 20);
        sisbb.teclar("29");
        sisbb.teclarAguardarTroca("@E");
        
    }

    /**
     * Interação com janela SISBB pelo aplicativo COMPE para captura de dados de
     * TED e/ou DOC das transações realizadas pelo RETAG
     *
     * @param sisbb janela sisbb
     * @param data data da consulta ddMMyyyy
     * @throws RoboException 
     */
    public void consultaData(Janela3270 sisbb, String data) throws RoboException {

        sisbb.aguardarInd(1, 3, "TFIM2900");
        sisbb.posicionar(13, 24);
        sisbb.teclar("10");
        sisbb.posicionar(15, 24);
        sisbb.teclar(contaEnum.getNumeroAgencia());
        sisbb.posicionar(16, 24);
        sisbb.teclar(contaEnum.getNumeroConta());

        sisbb.posicionar(17, 24);
        sisbb.teclar(data);
        sisbb.posicionar(18, 24);
        sisbb.teclar(data);

        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(1, 3, "TFIM2910");

        for (int i = 7; i < 13; i++) {
            if (sisbb.copiar(i, 6, 10).equals("Processado")) {
                sisbb.posicionar(i, 3);
                sisbb.teclar("X");
                sisbb.teclarAguardarTroca("@E");
            }
        }
        sisbb.aguardar(1, 3, "TFIM2911");
        
    }
    
}
