/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasSisbb.extratoRetag;

import br.com.bb.jibm3270.RoboException;
import dao.Conta99DAO;
import java.math.BigDecimal;
import java.util.List;
import entidade.Conta99;
import sisbbgeneric.Janela3270;
import util.Utils;

/**
 *
 * @author f4281065
 */
public class CompeDOC {
 
    Conta99DAO<Conta99> conta99Dao;

    public CompeDOC(Conta99DAO<Conta99> conta99Dao) {
        this.conta99Dao = conta99Dao;
    }
       

    /**
     * Percorrer as linhas - TFI - Recebendo - Consulta Sintetica - Tela sisbb
     * TFIM2911. Comparar e complementar com os registros coletados pelo RETAG
     *
     * @param sisbb instancia sisbb
     * @param listaConta99 lista coletada do BD das movimentações do dia
     * @throws br.com.bb.jibm3270.RoboException
     */
    public void consultaRecebimentoDOC(Janela3270 sisbb, List<Conta99> listaConta99) throws RoboException {

        for (int i = 6; i < 21; i++) {

            if (sisbb.copiar(i, 19, 10).equals("")) {
                return;
            }

            String documento = sisbb.copiar(i, 20, 9);
            documento = documento.replace(".", "");

            for (Conta99 conta99 : listaConta99) {
                String docBD = String.valueOf(conta99.getNumeroDoc());
                
                if (docBD.equals(documento)) {                                  //Comparar documento sisbb com documento BD
                    sisbb.posicionar(i, 3);
                    sisbb.teclar("X");
                    sisbb.teclarAguardarTroca("@E");
                    sisbb.aguardarInd(1, 3, "TFIM2912");

                    BigDecimal valorSisbb = Utils.converterStringParaBigDecimal(sisbb.copiar(6, 53, 16).trim());
                    BigDecimal valorBD = Utils.converterDobleParaBigDecimal(conta99.getValor());

                    if (valorBD.equals(valorSisbb)) {                            //+ comparar valores sisbb e BD
                        
                        String nomeRemetente = sisbb.copiar(19, 35, 46);

                        int idConta99 = conta99.getCodigoConta99();
                        conta99Dao.updateConta99("NOME_REMETENTE", nomeRemetente, idConta99);
                        
                    }

                }

            }

            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardarInd(1, 3, "TFIM2911");

        }

        sisbb.teclarAguardarTroca("@3");
        sisbb.aguardar(1, 3, "TFIM2910");
        sisbb.teclarAguardarTroca("@3");
        sisbb.aguardar(1, 3, "TFIM2900");
        
    }    
    
}
