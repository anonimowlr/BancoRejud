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
public class CompeTED {
    
    private boolean ehNumero;
    
    Conta99DAO<Conta99> conta99Dao;

    public CompeTED(Conta99DAO<Conta99> conta99Dao) {
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
    public void consultaRecebimentoTED(Janela3270 sisbb, List<Conta99> listaConta99) throws RoboException {

        for (int i = 6; i < 21; i++) {
            
            if (sisbb.copiar(i, 19, 10).equals("")) {
                return;
            }
            
            String documento = sisbb.copiar(i, 19, 10);
            documento = documento.replace(".", "");
                  
            for (Conta99 conta99 : listaConta99) {
                String docBD = String.valueOf(conta99.getNumeroDoc());
                
                if (docBD.equals(documento)) {                                  //Comparar documento sisbb com documento BD
                    sisbb.posicionar(i, 3);
                    sisbb.teclar("X");
                    sisbb.teclarAguardarTroca("@E");
                    sisbb.aguardar(1, 3, "TFIM2912");

                    BigDecimal valorSisbb = Utils.converterStringParaBigDecimal(sisbb.copiar(6, 53, 16).trim());
                    BigDecimal valorBD = Utils.converterDobleParaBigDecimal(conta99.getValor());

                    if (valorBD.equals(valorSisbb)) {  
                        
                        sisbb.teclarAguardarTroca("@12");
                        sisbb.aguardar(6, 24, "TFIMS195");
                        sisbb.posicionar(9, 25);
                        sisbb.teclar("X");
                        sisbb.teclarAguardarTroca("@E");
                        sisbb.aguardar(1, 3, "STRN4199");

                        do {
                            gravarDadosTED(sisbb, conta99);
                            sisbb.teclarAguardarTroca("@8");
                        } while (!sisbb.copiar(23, 3, 6).equals("Ultima"));
                        
                    }   

                } 

            }

            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardar(1, 3, "TFIM2912");
            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardar(1, 3, "TFIM2911");

        }
       
    }    

    /**
     * Verifica a posição onde se encontra os dados de origem, remetente, histórico
     * @param sisbb janela sisbb
     * @param conta99 registro BD - objeto com os dados do TED
     * @throws RoboException 
     */
    private void gravarDadosTED(Janela3270 sisbb, Conta99 conta99) throws RoboException {
        
        for (int z = 12; z < 22; z++) {                                         //verificar todas as linhas

            String codPosicao = sisbb.copiar(z, 6, 3);

            try {
                int valor = Integer.parseInt(codPosicao);
                ehNumero = true;
            } catch (NumberFormatException e) {
                ehNumero = false;
            }

            if (ehNumero) {

                int idConta99 = conta99.getCodigoConta99();
                
                switch (codPosicao) {
                    case "373": //Conta Origem
                        String contaDebitada = sisbb.copiar(z, 41, 40).trim();      
                        contaDebitada = Utils.tratarVariavel(contaDebitada);                        
                        conta99Dao.updateConta99("CONTA_ORIGEM", contaDebitada, idConta99);                        
                        break;
                        
                    case "421": //Nome cliente debitada
                    case "382": //Nome Remetente TED
                        String nomeRemetente = sisbb.copiar(z, 41, 40);
                        conta99Dao.updateConta99("NOME_REMETENTE", nomeRemetente, idConta99);                        
                        break;
                        
                    case "691": //Histórico
                    case "690": //Histórico
                        String historioOrigem = historicoTED(sisbb, z);
                        conta99Dao.updateConta99("HISTORICO_TED", historioOrigem, idConta99);
                        break;
                        
                }

            }

        }
        
    }

    /**
     * Grava o histórico do TED no Banco de Dados quando é dividada em duas
     * páginas
     *
     * @param sisbb janela sisbb
     * @param z linha do extrato com informações do TED
     * @throws RoboException
     */
    private String historicoTED(Janela3270 sisbb, int z) throws RoboException {

        String historicoOrigem = sisbb.copiar(z, 41, 40) + "-";                       //1 linha 1 pag
        
        switch (z) {
            case 18:
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, 1, 3);    //+3 linhas 1 pag
                sisbb.teclarAguardarTroca("@8");                
                historicoOrigem = historicoOrigem.concat(sisbb.copiar(12, 41, 40) + "-");
                sisbb.teclarAguardarTroca("@7");
                break;
            case 19:
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, 1, 2);    //+2 linhas 1 pag                
                sisbb.teclarAguardarTroca("@8");                
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, -7, -6);  //+2 linhas 2 pag 
                sisbb.teclarAguardarTroca("@7");
                break;
            case 20:
                historicoOrigem = historicoOrigem.concat(sisbb.copiar(z + 1, 41, 40) + "-"); //+1 linha 1 pag 
                sisbb.teclarAguardarTroca("@8");
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, -8, -6);  //+3 linhas 2 pag
                sisbb.teclarAguardarTroca("@7");
                break;
            case 21:
                sisbb.teclarAguardarTroca("@8"); 
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, -9, -6);  //+4 linhas 2 pag
                sisbb.teclarAguardarTroca("@7");
                break;
            default:
                historicoOrigem = lacoHist(sisbb, z, historicoOrigem, 1, 4);    //+4 linhas 1 pag
        }
        
        return historicoOrigem;

    }

    /**
     * Laço FOR para captura do histórico do TED
     * 
     * @param sisbb janela sisbb
     * @param z linha do extrato com informações do TED
     * @param historicoOrigem dados capturados do histórico
     * @param inicio linha do inicio do laço do histórico; positivo para
     * primeira página, negativo para segunda página
     * @param fim linha final do laço (a diferença com o inicio é a quantidade
     * de linhas capturadas)
     * @return retorna String capturado
     * @throws RoboException
     */
    private String lacoHist(Janela3270 sisbb, int z, String historicoOrigem, int inicio, int fim) throws RoboException {
        for (int y = inicio; y <= fim; y++) {
            historicoOrigem = historicoOrigem.concat(sisbb.copiar(z + y, 41, 40) + "-");
        }
        return historicoOrigem;
    }
    
}
