package rotinasPortal;

import dao.ResgateDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import entidade.Resgate;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;
import util.Utils;

public class VerificarSituacaoResgateBaseCpj extends Thread {

    Integer qtdreg = 0;

    List<Resgate> lista = null;
    Usuario user = new Usuario();
    ResgateDAO<Resgate> resgateDAO = new ResgateDAO<>("rejud");

    Coletas coletas = new Coletas();

    public VerificarSituacaoResgateBaseCpj() {
    }

    @Override
    @SuppressWarnings("CallToThreadStopSuspendOrResumeManager")
    public void run() {

        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado");
            this.stop();
        }

        try {
            createList();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar registros para sincronização da base cpj_cadastro");
        }
        lerLista();
        coletas.setSize();
        JOptionPane.showMessageDialog(null, "fim de rotina de sincronização cpj");
        coletas.encerraPortal(driver);

    }

    public void createList() throws SQLException {

        lista = resgateDAO.buscarParaSincronizar();
        if (lista.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há registros a sincronizar");
        } else {
            coletas.autenticarUsuario();
        }

    }

    public void lerLista() {

        int n = 0;
        qtdreg = lista.size();

        for (Resgate s : lista) {

            BigDecimal valorRegateBanco = s.getIDValor();
            String contaDepositaria = s.getContaDepositaria();
            String oficioBanco = s.getSolicOrdem();
            String npj = s.getNpj();
            int codigo = s.getCod();
            int numSolicitacao = s.getIDPortal();

            sincronizarSituacaoResgate(contaDepositaria, npj, valorRegateBanco, oficioBanco, numSolicitacao, codigo);

            n++;

        }

    }

    private void sincronizarSituacaoResgate(String contaDepositaria, String npj, BigDecimal valorRegateBanco, String oficioBanco, int numSolicitacao, int codigo) {

        Resgate resgate = new Resgate();// instacia o objeto
        resgate.setCod(codigo);
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/deposito/levantamento/popupDetalharSolicitacaoLevantamento.seam?cid=1592810&idObj=" + numSolicitacao);

        String npjTexto = coletas.lerValorElementoID("detalharsolicitacaoLevantamentoDetalheForm:bbjurDepDecorate:bbjurDepOutput");
        String npjTratado = Utils.tratarNpjColetaSequencial(npjTexto);

        if (!npj.equals(npjTratado)) {
            return;
        }

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

        if (coletas.isElementPresentXpath(driver, ".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:numAgenciaDepositarioDecorate:j_id468']")) {
            resgate.setDeposAgencia(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:numAgenciaDepositarioDecorate:j_id468']"));

        }

        if (coletas.isElementPresentXpath(driver, ".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:Decorate:Output']")) {
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

        // COLETA DADOS DA DESTINAÇÃO -- QUANDO REGISTRO NA SITUAÇÃO CONCLUÍDO
        if (resgate.getIDSituação().equals("Concluida")) {
         coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/deposito/levantamento/popupDetalharSolicitacaoLevantamento.seam?cid=1592810&idObj=" + numSolicitacao);
         coletas.aguardaElementoTelaByID("detalharsolicitacaoLevantamentoDetalheForm:bbjurDepDecorate:bbjurDepOutput");
         
          if(coletas.isElementPresentID(driver, "detalharsolicitacaoLevantamentoDetalheForm:dataTabletableDest:0:nomePessoaDecorate:nomePessoaOutput")){
              
          resgate.setEspecificacao(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:dataTabletableDest:0:nomePessoaDecorate:nomePessoaOutput']"));
          resgate.setEnvolvido(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:dataTabletableDest:0:tipoPessoaDecorate']/span/div[1]"));
          resgate.setValorDestinado(Utils.converterDobleParaBigDecimal(coletas.lerValorElementoByXpth(".//*[@id='detalharsolicitacaoLevantamentoDetalheForm:dataTabletableDest:0:valorCustoDecorate:valorCustoOutput']"))); 
          }
          
          
          
         
        }

        
        try {
            resgate.setDataSincronizacao(Utils.getDataAtualFormatoMysql());
        } catch (Exception ex) {
            JOptionPane.showConfirmDialog(null, "Não foi possível apurara a data atual para gravação na sincronização.rotina proseguirá sem a data deste registro");
        }
        
        
        resgateDAO.atualizaRegistroResgate(resgate);

    }

}
