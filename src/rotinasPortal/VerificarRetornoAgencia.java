package rotinasPortal;

import util.Utils;
import dao.DesconciliacaoDAO;
import java.util.List;

import javax.swing.JOptionPane;
import entidade.Desconciliacao;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.valorElemento;

public class VerificarRetornoAgencia extends Thread {
    
     Integer qtdreg = 0;

    List<Desconciliacao> lista = null;
    Usuario user = new Usuario();
    DesconciliacaoDAO<Desconciliacao> desconciliacaoDAO = new DesconciliacaoDAO<>("rejud");

    Coletas coletas = new Coletas();

    public VerificarRetornoAgencia() {
    }

    @Override
    public void run() {

        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado");
            this.stop();
        }

        createList();
        lerLista();
        coletas.setSize();
        JOptionPane.showMessageDialog(null, "fim de rotina");
        coletas.encerraPortal(driver);

    }

    public void createList() {

        lista = desconciliacaoDAO.buscarRetornoAgencia();
        if (lista.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há registros a complementar");
        } else {
            coletas.autenticarUsuario();
        }

    }

    public void lerLista() {

        int n = 0;
        int qtdreg = lista.size();

        for (Desconciliacao s : lista) {

            Integer codigo = s.getCodigoDesconciliacao();
            String npj = s.getNpj();

            Integer variacaoNpj = s.getVariacaoNpj();
            String contaDepositaria = s.getContaDepositaria();
            String tratamento = s.getNomeTratamento();

            //coletaDadosPortal(codigo, npj, variacaoNpj, contaDepositaria);
            //coletaDadosNpj(codigo, npj, variacaoNpj, contaDepositaria);
            verificaRetorno(codigo, npj, variacaoNpj, contaDepositaria, tratamento);
            n++;

        }

    }

    private void verificaRetorno(Integer codigo, String npj, Integer variacaoNpj, String contaDepositaria, String tratamento) {

        driver.manage().window().maximize();
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/levantamento/solicitacao/pesquisar");
        coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:linkDados");

        nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
        valorElemento = npj.subSequence(0, 4).toString();
        coletas.aguardaElementoTelaByName(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(npj);
           valorElemento =npj.subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            //coletas.pausar(1000);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento =variacaoNpj.toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

           

            

            coletas.aguardaElementoTelaByName("pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);

      
          

           if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                   return;
                }
            }
                
    
          if(coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id436")){
           String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id436");
           
            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
                        if (qtdreg>10){
                            
                             Boolean b = coletas.isElementPresentXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[4]/table/tbody/tr[1]/td/div[2]/div/div[5]/a");

                                if (b) {
                                    coletas.clickElementXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[4]/table/tbody/tr[1]/td/div[2]/div/div[5]/a");
                    coletas.pausar(2000);
                    coletas.aguardaElementoTelaByID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:10:j_id669");

                }

            }

        } else {
            return;
        }

        for (int reg = 0; reg < qtdreg; reg++) {
          
              String contaDepositariaPOrtal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":contaDepositoDecorate:contaDepositoOutput");
              String situacao = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id601");
              String valorResgate = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":valorDecorate:valorOutput");
              
              
                            if(tratamento == null){
                               return; 
                            }
              
                            
              
                        if(contaDepositaria.equals(contaDepositariaPOrtal) && situacao.contains("Aguardando Desti") && tratamento.contains("RETORNO")){
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "SITUACAO", "RETORNADO AGENCIA", codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "OBS_LIVRE", "Atenção!!! resgate valor:" + valorResgate + "  " + " aguardando destinação levantamento DJO" , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "NOME_TRATAMENTO", "" , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "DATA_SITUACAO", Utils.getDataHoraAtualMysql() , codigo);
                         
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "DIAS_DESCONCILIADO", "0" , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "DATA_RETORNO_AGENCIA", Utils.getDataHoraAtualMysql() , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "TRATADO_PRAZO", null , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "FUNCIONARIO_RESPONSAVEL_SITUACAO", null , codigo);
                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "FUNCIONARIO_ATUAL", null , codigo);
                             
                        } 
                        
                        
                        
                        
//                          if(contaDepositaria.equals(contaDepositariaPOrtal) && situacao.contains("Aguardando Documento") && tratamento.contains("RETORNO") && coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta")){
//                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "SITUACAO", "RETORNADO AGENCIA", codigo);
//                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "OBS_LIVRE", "Atenção!!! resgate valor:" + valorResgate + "  " + " aguardando documento porém redisponibilizado pela Diris com alegação de alvará eletrônico" , codigo);
//                          desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "NOME_TRATAMENTO", "" , codigo);
//                            
//                        } 
//                        
        }

    }

}
