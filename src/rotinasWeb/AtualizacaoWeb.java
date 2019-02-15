
package rotinasWeb;

import dao.AtualizacaoWebDAO;
import java.util.List;
import javax.swing.JLabel;
import entidade.Desconciliacao;

/**
 *
 * @author f4281065
 */
public class AtualizacaoWeb {
        
    AtualizacaoWebDAO webDAO;
    
    /**
     * Inicia o construtor com o nome do Banco de Dados a ser conectado
     * 
     * @param bancoDados 
     */
    public AtualizacaoWeb(String bancoDados) {
        this.webDAO = new AtualizacaoWebDAO(bancoDados);
    }
    
    
    /**
     * Sequencia de comandos de inclusão e atualização das desconciliações
     * 
     * @param txtArea JLabel da Form informando o andamento dos procedimentos
     */
    public void atualizacaoDiaria(JLabel txtArea) {
                 
        webDAO.apagaTabelaTemp("tb_tmp_incremento");
        webDAO.apagaTabelaTemp("tb_tmp_concluidos");
        webDAO.apagaTabelaTemp("tb_falso_regularizado");
        
        txtArea.setText("Criando tabela temporária incremento dos novos registros...");
        webDAO.criarIncremento();        
        webDAO.inserirIncremento();
        
        txtArea.setText("Criando tabela temporária dos registros concluídos...");
        webDAO.criarConcluidos();
        
        txtArea.setText("Criando tabela temporária dos falsos regularizados...");
        webDAO.criarFalsoRegularizado();
        
        txtArea.setText("Atualizando registros falsos regularizados...");
        List<Desconciliacao> falsosRegularizados = webDAO.pesquisaFalsoRegularizado();
        
        for (Desconciliacao falsoReg : falsosRegularizados) {            
            int idDesc = falsoReg.getCodigoDesconciliacao();            
            webDAO.upFalsoRegularizado(idDesc);            
        }
        
        txtArea.setText("Atualizando registros regularizados...");
        List<Desconciliacao> Regularizados = webDAO.pesquisaRegularizados();
        
        for (Desconciliacao regularizado : Regularizados) {
            int idReg = regularizado.getCodigoDesconciliacao();
            webDAO.upRegularizado(idReg);
        }

        txtArea.setText("Atualizando desconciliados...");
        webDAO.inserirHistorico();
        
        webDAO.upAvocado();
        webDAO.upInedito();
        webDAO.upDiasConci();
        webDAO.upPrimeiroTrat();
        webDAO.upDiasConciRegular();
        webDAO.upPrazo();
        
    }
    
    public void atualizacaoOnline() {
        
        List<Desconciliacao> contabilizacoesIguais = webDAO.pesquisaContabDevIguais("OBS_CONTABILIZACAO");
        
        for (Desconciliacao contabIgual : contabilizacoesIguais) {
            int idCont = contabIgual.getCodigoDesconciliacao();
            webDAO.upContabIguais(idCont);
        }
        
        webDAO.upPrimeiroTrat();
        webDAO.upDiasConciRegular();
        webDAO.upPrazo();
        
        List<Desconciliacao> devolucoesIguais = webDAO.pesquisaContabDevIguais("OBS_DEVOLUCAO");
        
        for (Desconciliacao devolucaoIgual : devolucoesIguais) {
            int idDev = devolucaoIgual.getCodigoDesconciliacao();
            webDAO.upDevolucaoIgual(idDev);
        }
        
        webDAO.upPrimeiroTratPend();
        webDAO.upPrazoRetorno();
        
    }
    
}
