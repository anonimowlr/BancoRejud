package rotinasPortal;

import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.valorElemento;
import static rotinasPortal.Coletas.driver;
import conexao.ConnectionFactory;
import dao.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import entidade.SolicitacaoLevantamento;
import entidade.Usuario;

public class IncluirBeneficiario extends Thread {
    static int ultimoRegistro;
    static String msgfin;
//    private String bloqProcessoCpf;
//    private int contaTempoBloq;

    Coletas coletas = new Coletas();
    DAO<String> d = new DAO<>("rejud");

  public IncluirBeneficiario() {
    }

    /**
     *
     */
    @Override
    public void run() {

        coletas.autenticarUsuario();
        try {
            interacao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    @SuppressWarnings("ConvertToTryWithResources")
    private void interacao() throws SQLException {

        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        SolicitacaoLevantamento s = new SolicitacaoLevantamento();
        PreparedStatement stmt;
        Connection con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_solicitacao_levantamento "
                + "where ((OBS_INCLUIR_PARTE is null or OBS_INCLUIR_PARTE='') "
                +         "AND (OBS <> '' or OBS <> null) "
                +         "AND (CPF_BENEFICIARIO <> '' OR CPF_BENEFICIARIO <> null))"
                + "order by CODIGO desc;";
        stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.pausar(10000);
            stmt.close();
            con.close();
            rs.close();
            if (user.getGrupoUsuario().equals("ADMINISTRADOR") || user.getGrupoUsuario().equals("OPERADOR")) {
                coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/pesquisar");
                interacao();
            } else {
                coletas.setSize();
                JOptionPane.showMessageDialog(null, "Fim da rotina!!");
                driver.close();
            }

        }

        do {
            driver.manage().window().maximize();
            
            String beneficiario = rs.getString("BENEFICIARIO");
            String cpf = rs.getString("CPF_BENEFICIARIO");
            String cpfTratado = coletas.tratarVariavel(cpf);

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/pesquisar");
            coletas.aguardaElementoTelaByID("pesquisarProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput");
            nomeElemento = "";
            valorElemento = "";

//----------------------------------------------------------NPJ-------------------------------------------------------    
            nomeElemento = "pesquisarProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString(); //Primeira parte do NPJ
            coletas.procuraElementoPorName(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString(); //Segunda parte do NPJ
            coletas.procuraElementoPorName(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.procuraElementoPorName(driver, nomeElemento, valorElemento);
            coletas.clickElementId(driver, "pesquisarProcessoForm:btPesquisar");

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento","OBS_INCLUIR_PARTE", n, codigo);
                    interacao();
                }
            }

            if (coletas.isElementPresentID(driver, "pesquisarProcessoForm:j_id581")) {
                String msgfim = coletas.lerValorElementoID("pesquisarProcessoForm:j_id581");
                if (!msgfim.equals("")) {

                    if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_solicitacao_levantamento","OBS_INCLUIR_PARTE", msgfim, codigo);
                        interacao();
                    }

                }

            }
            String situacao = coletas.lerValorElementoID("pesquisarProcessoForm:dataTabletableProcessos:0:colSituacaoDecorate:colSituacaoOutput");

            if (coletas.isElementPresentID(driver, "pesquisarProcessoForm:dataTabletableProcessos:0:selecionar")) {
                coletas.clickElementId(driver, "pesquisarProcessoForm:dataTabletableProcessos:0:selecionar");
                coletas.aguardaElementoTelaByID("pesquisarProcessoForm:btEnviarDoc");
            } else {
                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_solicitacao_levantamento","OBS_INCLUIR_PARTE", "Não aparece o lapis para clicar e a situação do NPJ é :" + situacao, codigo);
                interacao();
            }

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", n, codigo);
                    interacao();
                }
            }

            if (coletas.isElementPresentID(driver, "pesquisarProcessoForm:btEnviarDoc")) {
                coletas.clickElementId(driver, "pesquisarProcessoForm:btEnviarDoc");
    
                coletas.pausar(500);                
                

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {     //ver msg bloqueio simultaneo
                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");
                                        
                    
                    //espera 10 minutos caso o npj esteja bloqueado
/*                    if(n.contains("Processo bloqueado")) {
                      
                        String npj = rs.getString("NPJ");
                        
                        List<ProcessoBloqueio> listaBloqueios = new ArrayList<>();
                        listaBloqueios = d.buscaBloqueios(npj);

                        if (listaBloqueios.isEmpty()) {
                            d.insereBloqueado(npj);
                        } else {
                            for (ProcessoBloqueio objBloqueado : listaBloqueios) {
                                String npjBloqueado = objBloqueado.getNpj();
                                if (npjBloqueado.equals(npj)) {
//                                    Date date = objBloqueado.getDataHora();
                                    
                                    java.util.Date date = null;
                                    Timestamp timestamp = (Timestamp) objBloqueado.getDataHora();
                                    if (timestamp != null) {
                                        date = new java.util.Date(timestamp.getTime());
                                    }
                                    
                                    
                                    List<ProcessoBloqueio> blocIncluso = d.buscaBloqueios10(10, date);
                                    if (blocIncluso.isEmpty()) {
                                        return;
                                    }
                                }
                            }
                        }
                        //enviar para BD o npj, horario;
                        //buscar BD, comparar horarios, se + 10 min continua, senao return                     
                    }
*/                   
                    
                    
                    if (!n.equals("")) {
                        con.close();
                        rs.close();
                        stmt.close();
                        interacao();
                    }
                }
                 
                coletas.aguardaElementoTelaByName("j_id767");

            }

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", n, codigo);
                    interacao();
                }
            }

            coletas.aguardaElementoTelaByName("j_id767");//741

            coletas.clickElementName(driver, "j_id767");
            coletas.aguardaElementoTelaByName("partesPrincipaisForm:j_id833");//803
            coletas.clickElementName(driver, "partesPrincipaisForm:j_id833");
            coletas.aguardaElementoTelaByID("adversasForm:ParteAdversaDecorate:pesquisarPessoaDecorate:j_id855");//825
            coletas.clickElementId(driver, "adversasForm:ParteAdversaDecorate:pesquisarPessoaDecorate:j_id855");
            coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput");
            coletas.procuraElementoPorId(driver, "includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput", cpfTratado);
            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:chkPesquisaMCI");
            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:pesquisarPessoa");

            if (coletas.isElementPresentID(driver, "includePopup:pesquisarPessoaForm:matriculaDecorate:j_id1522")) {
                coletas.pausar(1000);
                String sit = coletas.lerValorElementoID("includePopup:pesquisarPessoaForm:matriculaDecorate:j_id1522");
                if (!sit.equals("")) { 
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    incluirNaoListado(beneficiario,cpf);
                    if(!msgfin.equals("")){
                        d.editar("tb_solicitacao_levantamento","OBS_INCLUIR_PARTE", msgfin, codigo);
                        interacao();    
                    }
                }

            }

            coletas.pausar(1000);
            //Se localizou beneficiário
            if (coletas.isElementPresentID(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1580")) { //1574
                String nome = coletas.lerValorElementoID("includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1580");
                coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar");
                
            } else {
                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", "CPF/CNPJ informado não encontrou pessoa vinculada", codigo);
                interacao();
            }

//            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1580");//1574            
//            coletas.pausar(1000);
//            coletas.isVisibleById(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar");
               
            coletas.pausar(1000);
            
            coletas.aguardaElementoTelaByID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:0:j_id879");//877 f4281065
            coletas.clickElementId(driver, "adversasForm:ParteAdversaDecorate:j_id858");//828
            coletas.pausar(1000);

            if (coletas.isElementPresentID(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:0:j_id879")) {
                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", n, codigo);
                    interacao();
                }
            }

            int n = 0;
            if (coletas.isElementPresentID(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:9:comboTipoRelProcesso")) {
                n = 100;

            } else {
                n = 10;
            }

            for (int i = 20; i >= 1; i--) {////*[@id="adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:1:comboTipoRelProcesso"]
                if (coletas.isElementPresentXpath(driver, ".//*[@id='adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:j_id880_table']/tbody/tr/td[" + i + "]")) {//872
                    coletas.clickElementXpath(driver, ".//*[@id='adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:j_id880_table']/tbody/tr/td[" + i + "]");//872
                    break;
                }
            }

            for (int i = 0; i <= n; i++) {

                if (coletas.isElementPresentID(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id879")) {//877 f42
                    ultimoRegistro = n - i;
                    String nome = coletas.lerValorElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id879");//877 f42
                    Boolean b = coletas.compararNomes(beneficiario, nome);

                    if (b) {

                        coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":comboTipoRelProcesso", 9);
                        coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":comboTipoRelBanco", 3);
                        coletas.clickElementName(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id892");//890
                        break;

                    } else {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", "Nome do beneficiario diverge do informado", codigo);
                        interacao();

                    }

                }

            }


          for(int i = 0; i<= ultimoRegistro;i++){
              String preenchimentoAnterior = "";
              
              if (coletas.isVisibleByIdByLeitura(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso")){
   
                    preenchimentoAnterior = coletas.lerValorElementoSelectID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso");

                } else {
                    break;
                }
              
                if (preenchimentoAnterior.equals("Selecione")) {
                    coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso", 9);
                }

            }

            for (int i = 0; i <= ultimoRegistro; i++) {
                String preenchimentoAnterior = "";

                if (coletas.isVisibleByIdByLeitura(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco")) {

                    preenchimentoAnterior = coletas.lerValorElementoSelectID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco");

                } else {
                    break;
                }

                if (preenchimentoAnterior.equals("Selecione")) {
                    coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco", 3);
                }

            }

            coletas.clickElementId(driver, "adversasForm:btSalvar");

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String msgfin = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!msgfin.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento", "OBS_INCLUIR_PARTE", msgfin, codigo);
                    interacao();
                }
            }

            rs.close();
            con.close();
            stmt.close();
            interacao();

        } while (rs.next());

        rs.close();
        con.close();
        stmt.close();
    }

    private void incluirNaoListado(String beneficiario, String cpf) {
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/pessoa/pesquisar");
        coletas.aguardaElementoTelaByID("consultarPessoaForm:cpfCnpjDecorate:cpfCnpjInput");
        coletas.procuraElementoPorId(driver, "consultarPessoaForm:cpfCnpjDecorate:cpfCnpjInput", cpf);
        coletas.aguardaElementoTelaByID("consultarPessoaForm:chkParteNome");
        coletas.clickElementId(driver, "consultarPessoaForm:chkParteNome");
        coletas.clickElementId(driver, "consultarPessoaForm:chkRegistroInativo");
        coletas.clickElementId(driver, "consultarPessoaForm:j_id296");
        coletas.clickElementId(driver, "consultarPessoaForm:consultar");
        

        String n = coletas.lerValorElementoID("modaldivMessagesGlobal");
        
        if(!n.equals("")){
            msgfin = n;
            return;
    
        }

        String sit = coletas.lerValorElementoID("consultarPessoaForm:j_id317");

        if (!sit.equals("")) {
            incluirPessoa(beneficiario, cpf);
            msgfin = "CPF/CNPJ informado não encontrou pessoa vinculada ";
        }

    }

    private void incluirPessoa(String beneficiario, String cpf) {
        coletas.clickElementId(driver, "j_id74");
        coletas.aguardaElementoTelaByID("incluirPessoaForm:Decorate:ListBox");
        coletas.selecionarElementoID("incluirPessoaForm:Decorate:ListBox", 2);
        coletas.aguardaElementoTelaByID("incluirPessoaForm:cpfPssDecorate:cpfPssInput");
        coletas.procuraElementoPorId(driver, "incluirPessoaForm:cpfPssDecorate:cpfPssInput", cpf);
        coletas.pausar(1000);
        coletas.clickElementName(driver, "incluirPessoaForm:j_id150");
        coletas.pausar(1000);

        if (coletas.isElementPresentName(driver, "incluirPessoaForm:j_id733")) {
            coletas.clickElementName(driver, "incluirPessoaForm:j_id733");
        } 

    }
    
}
