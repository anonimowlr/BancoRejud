/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasPortal;


import util.Utils;
import dao.ResgateDAO;
import javax.swing.JOptionPane;
import entidade.Resgate;
import static rotinasPortal.Coletas.driver;



/**
 *
 * @author f5078775
 */
public class ColetaResgateDepJudicial extends Thread {
    
    ResgateDAO<Resgate> resgateDAO = new ResgateDAO<>("rejud");
    
    Coletas coletas = new Coletas();
   
   
    @Override
    public void run() {
      
        
        coletas.autenticarUsuario();
        
        calculaSequencial();
        
        
    }
    
    public void calculaSequencial(){ // aqui fazer metodo no DAO para contar próximo a ser coletado.
       int numSolicitacao = resgateDAO.proximoSequencial();
       //int numSolicitacao = 524933;
        coletarDados(numSolicitacao);
    }
    
    
   
   public void coletarDados(int numSolicitacao){
       
      Boolean existeDados = true; 
      
      while(existeDados){ 
      Resgate resgate = new Resgate();    
      coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/deposito/levantamento/popupDetalharSolicitacaoLevantamento.seam?cid=1592810&idObj=" + numSolicitacao);
      
      String npjTexto = coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:bbjurDepDecorate:bbjurDepOutput");
      String npjTratado = Utils.tratarNpjColetaSequencial(npjTexto);
      
      
      // dados do processo
      
      resgate.setIdNpj(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:bbjurDepDecorate:bbjurDepOutput"));
      resgate.setIDPortal(numSolicitacao);
      resgate.setNpj(npjTratado);
     
      resgate.setProcesAutor(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:autorDepDecorate:autorDepOutput"));
      resgate.setProcesReu(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:reuDepDecorate:reuDepOutput"));
      resgate.setProcesProcesso(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:numeroProcessoDepDecorate:numeroProcessoDepOutput"));
      resgate.setProcesVara(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:juizoVaraDepDecorate:juizoVaraDepOutput"));
      resgate.setProcesComarca(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:comarcaDepDecorate:comarcaDepOutput"));
      resgate.setProcesTpacao(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:tipoAcaoDepDecorate:tipoAcaoDepOutput"));
      resgate.setProcesNatureza(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:naturezaDepDecorate:naturezaDepOutput"));
      resgate.setProcesUja(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:dependenciaDepDecorate:j_id211"));
      resgate.setProcesAdvogadobb(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:advogadoBBDepDecorate:advogadoBBDepOutput"));
      
      
      
      
      // dados do depósito
      
      resgate.setDeposSituacao(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:situacaoDepositoDecorate:situacaoDepositoOutput"));
      resgate.setDeposModalidade(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:modalidadeDepositoDecorate:modalidadeDepositoOutput"));
      resgate.setDeposDepositante(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:depositanteDepositoDecorate:depositanteDepositoOutput"));
      resgate.setContabDependencia(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:depContabilDepositoDecorate:depContabilDepositoOutput"));
      resgate.setContabValororigem(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorOriginalDepositoDecorate:valorOriginalDepositoOutput")));
      
       try {
              resgate.setContabInicio(Utils.formataData(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:dataDepositoDecorate:dataDepositoOutput")));
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(null, "Erro ao salvar data  inicio saldo contábil");
          }
      
      
      resgate.setContabSaldo(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorSaldoDepositoDecorate:valorSaldoDepositoOutput")));
      
      
      
      
      
      
      // dados do depositário
      
      resgate.setDeposBanco(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:bancoDepositarioDecorate:bancoDepositarioOutput"));
      
      if(coletas.isElementPresentXpath(driver, ".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:numAgenciaDepositarioDecorate:j_id468']")){
      resgate.setDeposAgencia(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:numAgenciaDepositarioDecorate:j_id468']"));
          
      }
      
      if(coletas.isElementPresentXpath(driver, ".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:Decorate:Output']")){
      resgate.setDeposAgencia(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:Decorate:Output']"));
          
      }
      
      resgate.setDeposDeposito(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:numContaDepositarioDecorate:numContaDepositarioOutput"));
      resgate.setContaDepositaria(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:numContaDepositarioDecorate:numContaDepositarioOutput"));
      resgate.setIDDeposito(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:numContaDepositarioDecorate:numContaDepositarioOutput"));
      
     
         
      
      // dados da solicitação
      
      resgate.setIDValor(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorSolicitacaoDecorate:valorSolicitacaoOutput")));
      resgate.setSolicValor(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorSolicitacaoDecorate:valorSolicitacaoOutput")));
      resgate.setSolicOrdem(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:numOficioSolicitacaoDecorate:numOficioSolicitacaoOutput"));
          try {
              resgate.setSolicData(Utils.formataData(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:dataOficioSolicitacaoDecorate:dataOficioSolicitacaoOutput")));
          } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "Erro ao salvar data  da solicitação");
          }
     
      
      
          try {
              resgate.setSolicValidade(Utils.formataData(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:dataValidadeOrdemDecorate:dataValidadeOrdemOutput")));
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(null, "Erro ao salvar data  de validade da ordem da solicitação");
          }
      
      resgate.setSolicPrefixo(Utils.convertStringToInt(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:ujSolicitanteDecorate:j_id682")));
          
          
      
      
      // dados da liberação
      
      resgate.setLiberValor(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorSolicitLiberacaoDecorate:valorSolicitLiberacaoOutput")));
      resgate.setLiberValor(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:valorSolicitLiberacaoDecorate:valorSolicitLiberacaoOutput")));
      resgate.setLiberOperadora(Utils.convertStringToInt(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:depOperadoraDecorate:j_id837")));
      resgate.setLiberPrefixo(Utils.convertStringToInt(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:depLiberadoraDecorate:j_id869")));
      resgate.setLiberTipo(coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:tipoResgateDecorate:tipoResgateOutput"));
      
      
      
     
     
                //  dados de matéria e assunto
          coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/processo/consultar/relatorioCompleto.seam?idProcessoPrincipal=" + resgate.getNpj() + "");

          coletas.aguardaElementoTelaByID("detalharProcessoForm:j_id202");
          // coletas.pausar(1000);
          coletas.clickElementId(driver, "detalharProcessoForm:j_id202");
          //coletas.pausar(1000);

          coletas.aguardaElementoTelaByID("detalharProcessoForm:reuDecorate:reuOutput");

          if (!coletas.isElementPresentID(driver, "detalharProcessoForm:assuntoDecorate:assuntoOutput")) {

              return;
          }

          String assunto = coletas.lerValorElementoID("detalharProcessoForm:assuntoDecorate:assuntoOutput");

          String materia = coletas.lerValorElementoID("detalharProcessoForm:materiaDecorate:materiaOutput");

          resgate.setRelcompletoassunto(assunto);
          resgate.setRelCompletoMateria(materia);

             // coleta da situação
           coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/deposito/levantamento/popupHistoricoSolicitacaoLevantamento.seam?cid=1713556&idObj=" + numSolicitacao);
           int reg = 0;
           while (coletas.isElementPresentID(driver, "historicoSolicitacaoLevantamentoForm:j_id37:" + reg + ":estadoDecorate:estadoOutput")) {

               resgate.setIDSituação(coletas.lerValorElementoID("historicoSolicitacaoLevantamentoForm:j_id37:" + reg + ":estadoDecorate:estadoOutput"));
               resgate.setFunciSituacao(coletas.lerValorElementoID("historicoSolicitacaoLevantamentoForm:j_id37:" + reg + ":respoDecorate:respoOutput"));

               try {
                   resgate.setIDSituacaoData(Utils.formataData(coletas.lerValorElementoID("historicoSolicitacaoLevantamentoForm:j_id37:" + reg + ":hotra")));
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(null, "Erro ao salvar data  da situação");
               }
               reg++;
           }

           resgateDAO.inserirResgate(resgate);

           numSolicitacao++;
       }

   }

}
