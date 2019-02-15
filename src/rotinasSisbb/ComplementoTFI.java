/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import dao.Conta99DAO;
import java.util.Date;
import java.util.List;
import entidade.Conta99;
import enumEntidade.ContaEnum;
import javax.swing.JOptionPane;
import rotinasSisbb.extratoRetag.CompeDOC;
import rotinasSisbb.extratoRetag.CompeTED;
import sisbbgeneric.CompeSisbb;
import sisbbgeneric.Janela3270;
import sisbbgeneric.LogarUsuarioSisbb;
import util.Utils;

/**
 *
 * @author f4281065
 */
public class ComplementoTFI {
 
    ContaEnum contaEnum;
    Conta99DAO<Conta99> conta99Dao;

    public ComplementoTFI(ContaEnum contaEnum) {
        this.contaEnum = contaEnum;
        this.conta99Dao = new Conta99DAO<>(contaEnum.getBancoDados(), contaEnum.getTabelaBancoDados());
    }
        
    public void rotinaTFI() throws RoboException, Exception, Throwable {
        
        String dataConferencia = String.valueOf(conta99Dao.confereComplementacao("DATA_MOV"));

        if (Utils.getDataAtualYMD().equals(dataConferencia)) {
            JOptionPane.showMessageDialog(null, "Rotina de complementação cancelada!\n\n"
                    + "A complementação COMPE já foi realizada.");
        } else {

            String dataRecenteRotinaDB = String.valueOf(conta99Dao.buscaDataRecente("DATA_ROTINA")); //ultima entrada data de rotina RETAG no BD
            List<Conta99> listaDataRotina = conta99Dao.dataMovimentacaoRotina(dataRecenteRotinaDB);

            Janela3270 sisbb = new Janela3270();
            sisbb.setTamanho(700, 500);
            LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
            logar.autenticar("COMPE", sisbb);

            CompeSisbb compeSisbb = new CompeSisbb(contaEnum);

            ted(sisbb, listaDataRotina, compeSisbb);

            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardar(1, 3, "TFIM0000");
            sisbb.teclarAguardarTroca("@3");

            doc(sisbb, listaDataRotina, compeSisbb);

            sisbb.rotinaEncerramento();
            JOptionPane.showMessageDialog(null, "Rotina de complementação TFI finalizada");
            
        }

    }
    
    public void preRotina() throws Throwable {

        int k = JOptionPane.showConfirmDialog(null, "A rotina de captura das transações RETAG deverá ser realizada antes da complementação!\n"
                + "Se ela já foi realizada, continue com o procedimento.\n\n"
                + "Gostaria de continuar com a complementação das transações via COMPE?\n\n",
                "Coleta COMPE", JOptionPane.YES_NO_OPTION);

        if (k == JOptionPane.YES_OPTION) {
            rotinaTFI();
        }

    }

    /**
     * Inicio rotina TED referente a data da rotina que está sendo executada
     * 
     * @param sisbb janela sisbb
     * @param listaDataRotina lista das datas que possuem transações
     * @param compeSisbb interação no aplicativo COMPE da janela sisbb
     * @throws RoboException
     * @throws Exception 
     */
    private void ted(Janela3270 sisbb, List<Conta99> listaDataRotina, CompeSisbb compeSisbb) throws RoboException, Exception {

        compeSisbb.posicionarTedDoc(sisbb, "14"); //interação sisbb TED
        //traz lista dos dias que foi movimentado no dia de execução da rotina
        for (Conta99 conta99 : listaDataRotina) {

            Date dataMovimentacao = conta99.getDataMovimentacao();
            String dataMovimentacaoStr = Utils.formataDateSQL(dataMovimentacao);
            String dataMovimentacaoDMY = Utils.formataDataDMY(dataMovimentacaoStr);

            List<Conta99> listaTed = conta99Dao.buscaDocTed("976", dataMovimentacaoStr);  //lista registros TED
       
            if (listaTed.size() > 0) {

                compeSisbb.consultaData(sisbb, dataMovimentacaoDMY);

                CompeTED compeTED = new CompeTED(conta99Dao);

                while (!sisbb.copiar(23, 3, 6).equals("Ultima")) {
                    compeTED.consultaRecebimentoTED(sisbb, listaTed);
                    sisbb.teclarAguardarTroca("@8");
                }

                sisbb.teclarAguardarTroca("@3");
                sisbb.aguardar(1, 3, "TFIM2910");
                sisbb.teclarAguardarTroca("@3");
                sisbb.aguardar(1, 3, "TFIM2900");
                
            }

        }

    }

    /**
     * Inicio rotina DOC referente a data da rotina que está sendo executada
     * 
     * @param sisbb janela sisbb
     * @param listaDataRotina lista das datas que possuem transações
     * @param compeSisbb interação no aplicativo COMPE da janela sisbb
     * @throws RoboException
     * @throws Exception 
     */
    private void doc(Janela3270 sisbb, List<Conta99> listaDataRotina, CompeSisbb compeSisbb) throws RoboException, Exception {
        
        compeSisbb.posicionarTedDoc(sisbb, "07");
        
        for (Conta99 conta99 : listaDataRotina) {

            Date dataMovimentacao = conta99.getDataMovimentacao();
            String dataMovimentacaoStr = Utils.formataDateSQL(dataMovimentacao);
            String dataMovimentacaoDMY = Utils.formataDataDMY(dataMovimentacaoStr);

            List<Conta99> listaDoc = conta99Dao.buscaDocTed("623", dataMovimentacaoStr);  //lista registros DOC

            if (listaDoc.size() > 0) {
                
                compeSisbb.consultaData(sisbb, dataMovimentacaoDMY);

                CompeDOC compeDOC = new CompeDOC(conta99Dao);

                while (!sisbb.copiar(23, 3, 6).equals("Ultima")) {
                    compeDOC.consultaRecebimentoDOC(sisbb, listaDoc);
                    sisbb.teclarAguardarTroca("@8");
                }

                sisbb.teclarAguardarTroca("@3");
                sisbb.aguardar(1, 3, "TFIM2910");
                sisbb.teclarAguardarTroca("@3");
                sisbb.aguardar(1, 3, "TFIM2900");
                
            }

        }
        
    }

}
