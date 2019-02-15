package rotinasPortal;

import dao.EfetivacaoDAO;
import util.Utils;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import entidade.Efetivacao;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;

public class ComplementarDepositoEfetivacao extends Thread {
    List<Efetivacao> lista = null;
    Usuario user = new Usuario();
    EfetivacaoDAO<Efetivacao> efetivacaoDAO = new EfetivacaoDAO<>("rejud");
    Coletas coletas = new Coletas();

    public ComplementarDepositoEfetivacao() {
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
            lista = efetivacaoDAO.buscar();
            if (lista.size() < 1) {
                JOptionPane.showMessageDialog(null, "Não há registros de efetivação de depósitos  a complementar");
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

        for (Efetivacao s : lista) {
            Integer codigo = s.getCodigo();
            String npj = s.getNpj();
            BigDecimal valorEfetivacao = s.getValorEfetivacao();
            Integer variacaoNpj = s.getVariacaoNpj();
            String contaDepositaria = s.getContaDepositaria();
            String bancoDepositario = s.getBancoDepositario();
            String agenciaDepositaria = s.getAgenciaDepositaria().toString();

            efetivarComplementacaoDeposito(codigo, npj, variacaoNpj, contaDepositaria, agenciaDepositaria, valorEfetivacao, bancoDepositario);
            n++;
        }
    }
    
    private void efetivarComplementacaoDeposito(Integer codigo, String npj, Integer variacaoNpj, String contaDepositaria, String agenciaDepositaria, BigDecimal valorEfetivacao, String bancoDepositario) {
        int tamanhoNpj = npj.length();
        if (contaDepositaria == null) {
            efetivacaoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "conta invalida", codigo);
            return;
        }

        driver.manage().window().maximize();
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/deposito/pesquisar");
        coletas.aguardaElementoTelaByID("pesquisarDepositoForm:linkMostrarPesquisa");
        coletas.clickElementId(driver, "pesquisarDepositoForm:linkMostrarPesquisa");

//        coletas.clickElementId(driver, "pesquisarDepositoForm:contaDepositoDecorate:contaDepositoInput");
//        coletas.procuraElementoPorId(driver, "pesquisarDepositoForm:contaDepositoDecorate:contaDepositoInput", contaDepositaria);


        coletas.procuraElementoPorId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput", npj.subSequence(0, 4).toString());
        coletas.procuraElementoPorId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput", npj.subSequence(4, tamanhoNpj).toString());
        coletas.procuraElementoPorId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput", variacaoNpj.toString());


        coletas.clickElementId(driver, "pesquisarDepositoForm:btPesquisar");
        coletas.pausar(500);
        
        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", n, codigo);
                return;
            }
        }

        // ler registros na pagina
        Integer qtdreg = null;
        if (coletas.isElementPresentID(driver, "pesquisarDepositoForm:j_id660")) {

            String n = coletas.lerValorElementoID("pesquisarDepositoForm:j_id660");

            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
        } else {

            efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", "nenhum registro encontrado", codigo);
            return;
        }

        for (int reg = 0; reg <= qtdreg; reg++) {

            if (reg == 10 || reg == 20 || reg == 30 || reg == 40 || reg == 50 || reg == 50) {

                coletas.clickElementXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[3]/div/div/div[2]/table/tbody/tr[2]/td/table/tfoot/tr/td/div/table/tbody/tr/td[6]");
                coletas.pausar(2000);
            }

            if (reg == qtdreg) {

                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", "nao localizada a conta para complementar", codigo);
                return;

            }

            String numeroContaPortal = coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id719");

            numeroContaPortal = Utils.separaAgenciaDeConta(numeroContaPortal);

            BigDecimal saldoPortal = Utils.converterStringParaBigDecimal(coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id761"));

            String npjPortal = Utils.tratarVariavel(coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":bbJurValor").subSequence(0, 12).toString());
            npjPortal = Utils.tratarVariavel(coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":bbJurValor").subSequence(0, 12).toString());

            if ((npj.equals(npjPortal)) && (valorEfetivacao.equals(saldoPortal))) {

                coletas.pausar(1000);

                if (reg <= 9) {
                    coletas.clickElementXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[3]/div/div/div[2]/table/tbody/tr[2]/td/table/tbody/tr[" + (reg + 1) + "]/td[1]/input");
                    coletas.clickElementXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[3]/div/div/div[2]/table/tbody/tr[2]/td/table/tbody/tr[" + (reg + 1) + "]/td[1]/input");

                } else {
                    efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", "Efetuar a complementação manualmente. impossível complementar automatizado", codigo);
                    return;
                }
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", n, codigo);
                        return;
                    }
                }

                coletas.aguardaElementoTelaByID("pesquisarDepositoForm:btAlterarModalidade");

                while (coletas.isElementPresentID(driver, "pesquisarDepositoForm:btAlterarModalidade")) {
                    coletas.clickElementName(driver, "pesquisarDepositoForm:btAlterarModalidade");
                }

                // nesta estapa capturamos alguns dados do processo em variáveis para gravação na tabela de efetivação
                String situacaoDeposito = coletas.lerValorElementoID("ajustarSaldoForm:situacaoDecorate:situacaoOutput");
                
                if (situacaoDeposito.equals("DEPOSITO CONCLUIDO - COMPLEMENTO REALIZADO")) {
                    efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "SITUACAO_DEPOSITO", situacaoDeposito, codigo);
                    efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", "Operação realizada com sucesso.", codigo);
                    return;
                }

                String processo = coletas.lerValorElementoID("ajustarSaldoForm:numeroProcessoDepDecorate:numeroProcessoDepOutput");
                String modalidade = coletas.lerValorElementoID("ajustarSaldoForm:modalidadeDecorate:modalidadeOutput");
                String natureza = coletas.lerValorElementoID("ajustarSaldoForm:depNaturezaDecorate:depNaturezaOutput");
                String valorInicial = coletas.lerValorElementoID("ajustarSaldoForm:depSaldoAntDecorate:depSaldoAntOutput");
                String saldoAtual = coletas.lerValorElementoID("ajustarSaldoForm:depSaldoAtualDecorate:depSaldoAtualOutput");
                String dataInicial = coletas.lerValorElementoID("ajustarSaldoForm:dtInicialDepositoDecorate:dtInicialDepositoOutput");
                String autor = coletas.lerValorElementoID("ajustarSaldoForm:autorDepDecorate:autorDepOutput");
                String reu = coletas.lerValorElementoID("ajustarSaldoForm:reuDepDecorate:reuDepOutput");

                // GRAVA DIRETO NO BANCO AS VARIAVES SEM INSTANCIAR OBJETO
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "PROCESSO", processo, codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "MODALIDADE", modalidade, codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "NATUREZA", natureza, codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "VALOR_INICIAL", Utils.tratarVariavel(valorInicial), codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "SALDO_ATUAL", Utils.tratarVariavel(saldoAtual), codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "DATA_INICIAL", Utils.format(dataInicial), codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "AUTOR", autor, codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "REU", reu, codigo);
                efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "SITUACAO_DEPOSITO", situacaoDeposito, codigo);

                coletas.aguardaElementoTelaByID("ajustarSaldoForm:pesquisarAgenciaDecorate:j_id425"); //agencia depositaria

                //obs: selecionar banco antes da agencia nos casos BANPARA 
                // ajustei para todos os bancos --- f5078775
                if (bancoDepositario.equals("BANPARA")) {
                    coletas.zoonIn();
                    coletas.selecionarElementoName("ajustarSaldoForm:j_id393", 5); //byindex, ver por option
                     coletas.zoonOut();
                
                }else if(bancoDepositario.contains("CAIXA ECONOMICA FEDERAL")){
                    coletas.zoonIn();
                    coletas.selecionarElementoName("ajustarSaldoForm:j_id393", 9); //byindex, ver por option
                    coletas.zoonOut();
                }else if(bancoDepositario.contains("BANRISUL")){
                    coletas.zoonIn();
                    coletas.selecionarElementoName("ajustarSaldoForm:j_id393", 6); //byindex, ver por option
                    coletas.zoonOut();
                }

                coletas.aguardaElementoTelaByID("ajustarSaldoForm:pesquisarAgenciaDecorate:pesquisarAgenciaDecorate:pesquisarAgenciaInput");
                coletas.clickElementId(driver, "ajustarSaldoForm:pesquisarAgenciaDecorate:j_id425");
                coletas.pausar(1000);

                coletas.procuraElementoPorId(driver, "includePopup:pesquisarAgenciaForm:codigoAgenciaDecorate:codigoAgenciaInput", agenciaDepositaria);
                coletas.pausar(1000);

                coletas.aguardaElementoTelaByID("includePopup:pesquisarAgenciaForm:pesquisarAgencia");
                coletas.clickElementId(driver, "includePopup:pesquisarAgenciaForm:pesquisarAgencia");
                coletas.pausar(1000);

                if (coletas.isElementPresentID(driver, "includePopup:pesquisarAgenciaForm:AgenciasdivMessagesGlobal")) {
                    coletas.pausar(1000);
                    String msgfim = coletas.lerValorElementoID("includePopup:pesquisarAgenciaForm:AgenciasdivMessagesGlobal");
                    if (!msgfim.equals("")) {

                        efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", msgfim + "- Portal nao retornou a agencia depositaria", codigo);
                        return;
                    }

                }

                coletas.aguardaElementoTelaByID("includePopup:pesquisarAgenciaForm:dataTablePopupAgencias:0:selecionar");
                coletas.clickElementId(driver, "includePopup:pesquisarAgenciaForm:dataTablePopupAgencias:0:selecionar");

                coletas.pausar(1000);

                coletas.clickElementId(driver, "ajustarSaldoForm:ctDepositariaDecorate:ctDepositariaInput");
                coletas.clickElementId(driver, "ajustarSaldoForm:ctDepositariaDecorate:ctDepositariaInput");

                String contaDepositariaPortal = coletas.lerValorInputID("ajustarSaldoForm:ctDepositariaDecorate:ctDepositariaInput");

                if (contaDepositariaPortal.equals("0")) {
                    coletas.procuraElementoPorId(driver, "ajustarSaldoForm:ctDepositariaDecorate:ctDepositariaInput", Utils.tratarVariavel(contaDepositaria));
                    coletas.pausar(1000);
                }

                coletas.clickElementId(driver, "ajustarSaldoForm:btSalvar");
                driver.switchTo().alert().accept();

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "OBS", n, codigo);

                        if (n.equals("Operação realizada com sucesso.")) {
                            efetivacaoDAO.editarGenerico("tb_efetivacao_dep", "SITUACAO_DEPOSITO", "DEPOSITO CONCLUIDO - COMPLEMENTO REALIZADO", codigo);
                        }
                        return;
                    }
                    
                }

            }
            
        }

    }

}
