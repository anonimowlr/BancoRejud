package rotinasPortal;

import util.Utils;
import dao.IneditoDAO;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import entidade.SolicitacaoInedito;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;

public class ComplementarDeposito extends Thread {
    List<SolicitacaoInedito> lista = null;
    Usuario user = new Usuario();
    IneditoDAO<SolicitacaoInedito> ineditoDAO = new IneditoDAO<>("rejud");
    Coletas coletas = new Coletas();

    public ComplementarDeposito() {
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

        try {
            lista = ineditoDAO.listaIneditosParaComplementar();
            if (lista.size() < 1) {
                JOptionPane.showMessageDialog(null, "Não há registros a complementar");
            } else {
                coletas.autenticarUsuario();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void lerLista() {

        int n = 0;
        int qtdreg = lista.size();

        for (SolicitacaoInedito s : lista) {

            Integer codigo = s.getCodigo();
            String npj = s.getNpj();

            Integer variacaoNpj = s.getVariacaoNpj();
            String contaDepositaria = s.getContaDepositaria();
            String agenciaDetentora = s.getDependenciaDtra().toString();

            efetivarComplementacaoDeposito(codigo, npj, variacaoNpj, contaDepositaria, agenciaDetentora);
            n++;

        }

    }
    
    
    
    
    

    private void efetivarComplementacaoDeposito(Integer codigo, String npj, Integer variacaoNpj, String contaDepositaria, String agenciaDetentora) {
        if (contaDepositaria == null) {
            ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "conta invalida", codigo);
            return;
        }
        
        driver.manage().window().maximize();
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/deposito/pesquisar");
        coletas.aguardaElementoTelaByID("pesquisarDepositoForm:linkMostrarPesquisa");
        coletas.clickElementId(driver, "pesquisarDepositoForm:linkMostrarPesquisa");
        coletas.clickElementId(driver, "pesquisarDepositoForm:contaDepositoDecorate:contaDepositoInput");
        coletas.procuraElementoPorId(driver, "pesquisarDepositoForm:contaDepositoDecorate:contaDepositoInput", contaDepositaria);
        
        coletas.clickElementId(driver, "pesquisarDepositoForm:btPesquisar");

        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", n, codigo);
                return;
            }
        }

        // ler gegistros na pagina
        Integer qtdreg = null;
        
//                                              pesquisarDepositoForm:j_id632
        if (coletas.isElementPresentID(driver, "pesquisarDepositoForm:j_id660")) { //registros encontrados

            String n = coletas.lerValorElementoID("pesquisarDepositoForm:j_id660"); //632

            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
        } else {

            ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "nenhum registro encontrado", codigo);
            return;
        }


        for (int reg = 0; reg <= qtdreg; reg++) {
            
            
              if (reg == 10 || reg == 20 || reg == 30 || reg == 40 || reg == 50 || reg == 50 ){
                    
                   coletas.clickElementXpath(driver, ".//*[@id='pesquisarDepositoForm:dataTabletableDepositoConglomerado:ds_table']/tbody/tr/td[6]");
                    
                }   
            
            
            
            
            

            if (reg == qtdreg) {

                ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "nao localizada a conta para complementar", codigo);
                return;

            }

            String numeroContaPortal = coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id719"); //691

            numeroContaPortal = Utils.separaAgenciaDeConta(numeroContaPortal);

            String saldoPortal = coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id761"); //733

            if (numeroContaPortal.contains(contaDepositaria.subSequence(1, 8))) {

                saldoPortal = Utils.tratarVariavel(saldoPortal);
                ineditoDAO.editarGenerico("tb_inedito", "SALDO_PORTAL", saldoPortal, codigo);

                
                coletas.clickElementId(driver, "radioDep");
               

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", n, codigo);
                        return;
                    }
                }

            } else{
                 ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "Numero da conta informada difere da conta no Portal", codigo);
                        return;
                
            }

            coletas.aguardaElementoTelaByID("pesquisarDepositoForm:btAlterarModalidade");
            
            while(coletas.isElementPresentID(driver, "pesquisarDepositoForm:btAlterarModalidade")){
                coletas.clickElementName(driver, "pesquisarDepositoForm:btAlterarModalidade");
            }
            
            
            //verificar se ocorreu erro F6765257
            if (driver.getPageSource().toLowerCase().contains("houve um problema no processamento de sua")){
                //deu erro, pular esta conta, vamos para a proxima... F6765257
                return;
            }
            
            coletas.aguardaElementoTelaByID("ajustarSaldoForm:pesquisarAgenciaDecorate:j_id425");
            
            //coletas.aguardaElementoTelaByID("ajustarSaldoForm:pesquisarAgenciaDecorate:pesquisarAgenciaDecorate:pesquisarAgenciaInput");
            coletas.clickElementId(driver, "ajustarSaldoForm:pesquisarAgenciaDecorate:j_id425");
            coletas.pausar(1000);
            coletas.procuraElementoPorId(driver, "includePopup:pesquisarAgenciaForm:codigoAgenciaDecorate:codigoAgenciaInput", agenciaDetentora);
            coletas.pausar(1000);
            coletas.aguardaElementoTelaByID("includePopup:pesquisarAgenciaForm:pesquisarAgencia");
            coletas.clickElementId(driver, "includePopup:pesquisarAgenciaForm:pesquisarAgencia");
            coletas.pausar(1000);
            
            if (coletas.isElementPresentID(driver, "includePopup:pesquisarAgenciaForm:AgenciasdivMessagesGlobal")) {
               coletas.pausar(1000);
                String msgfim = coletas.lerValorElementoID("includePopup:pesquisarAgenciaForm:AgenciasdivMessagesGlobal");
                if (!msgfim.equals("")) {
                    ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", msgfim, codigo);
                    return;
                }
            }

            coletas.aguardaElementoTelaByID("includePopup:pesquisarAgenciaForm:dataTablePopupAgencias:0:selecionar");
            coletas.clickElementId(driver, "includePopup:pesquisarAgenciaForm:dataTablePopupAgencias:0:selecionar");
            coletas.pausar(1000);
            coletas.clickElementId(driver, "ajustarSaldoForm:btSalvar");
            driver.switchTo().alert().accept();

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {
                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");
                if (!n.equals("")) {
                    ineditoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", n, codigo);
                    return;
                }
            }
        }
    }
}
