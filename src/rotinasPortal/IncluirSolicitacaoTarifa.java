package rotinasPortal;

import util.Utils;
import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.valorElemento;
import conexao.ConnectionFactory;
import dao.DAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import entidade.Usuario;

public class IncluirSolicitacaoTarifa extends Thread {

    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    public IncluirSolicitacaoTarifa() {

    }

    @Override
    public void run() {

         coletas.autenticarUsuario();
        try {
            interacao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }

    private void interacao() throws SQLException {
        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_tarifa where ((OBS_INCLUIR_TARIFA_PORTAL is null or OBS_INCLUIR_TARIFA_PORTAL = '') and (FUNCIONARIO_RESPONSAVEL = ? ))";

        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_tarifa where (OBS_INCLUIR_TARIFA_PORTAL is null or OBS_INCLUIR_TARIFA_PORTAL = '')";
            stmt = con.prepareStatement(query);

        }

        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.setSize();
            coletas.encerraPortal(driver);
            JOptionPane.showMessageDialog(null, " Fim de Rotina!!");
            con.close();
            stmt.close();
            rs.close();
            stop();

        }

        do {
            
            
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            String bancoBeneficiario = rs.getString("BANCO_BENEFICIARIO");
            String valorTarifa = rs.getString("VALOR_TARIFA");
            String cnpj= null;
            
            
            BigDecimal valorBanco = coletas.tratarNumero(valorTarifa);
            String valorTarifaTratado = valorBanco.toString();
            
            
            
            int tamanhoValorTarifa = valorTarifa.length();
            
            if (tamanhoValorTarifa == 1){
                
                valorTarifa = valorTarifa + ",00";
                
            }
            String numeroDocumento = rs.getString("NUMERO_DOCUMENTO");
            String dataDocumento = rs.getString("DATA_DOCUMENTO");
            String dataTratada = Utils.formatDataTexto(dataDocumento);
            
            String enderecoDocumento = rs.getString("ENDERECO_DOCUMENTO");
            
            

            nomeElemento = "";
            valorElemento = "";

            driver.manage().window().maximize();

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/custos/pesquisarCustos");
            coletas.aguardaElementoTelaByID("custoForm:j_id75");
            coletas.clickElementId(driver, "custoForm:j_id75");

            nomeElemento = "incluirCustoProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "incluirCustoProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";

           
            

            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            //coletas.pausar(1000);

            nomeElemento = "incluirCustoProcessoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            coletas.aguardaElementoTelaByID("incluirCustoProcessoForm:btSalvar");

            coletas.clickElementId(driver, "incluirCustoProcessoForm:btSalvar");

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL", n, codigo);
                    interacao();
                }
            }

            coletas.aguardaElementoTelaByID("incluirCustoProcessoForm:btSalvar");
            coletas.clickElementId(driver, "incluirCustoProcessoForm:btSalvar");
            driver.switchTo().alert().accept();
             coletas.pausar(1000);
            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL", n, codigo);
                    interacao();
                }
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            coletas.pausar(200);

            coletas.aguardaElementoTelaByID("incluirCustoProcessoForm:categoriaCustoDecorate:categoriaCustoListMenu");
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:categoriaCustoDecorate:categoriaCustoListMenu", "Despesas Administrativas Juridicas");

            coletas.clickElementId(driver, "incluirCustoProcessoForm:tipoCustoDecorate:tipoCustoListMenu");

            coletas.pausar(200);
            coletas.aguardaElementoTelaByID("incluirCustoProcessoForm:valorPagoDecorate:valorPagoInput");
            coletas.clickElementId(driver, "incluirCustoProcessoForm:valorPagoDecorate:valorPagoInput");
            coletas.pausar(1000);

            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:tipoCustoDecorate:tipoCustoListMenu", "Tarifa DOC/TED - Levantamento de Depositos outros Bancos ");

           
            
            
            if (bancoBeneficiario.equals("BANRISUL")) {
                cnpj = "92702067000196";
                incluirPessoa(bancoBeneficiario, cnpj);
                //coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:selectPessoaEnvolvidaDecorate:selectPessoaEnvolvidaListMenu", "BANCO DO ESTADO DO RIO GRANDE DO SUL S.A. ");
                
            } else if(bancoBeneficiario.equals("CAIXA ECONOMICA FEDERAL")){
               
                cnpj = "00360305000104 ";
                 incluirPessoa(bancoBeneficiario, cnpj);
                //coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:selectPessoaEnvolvidaDecorate:selectPessoaEnvolvidaListMenu", "CAIXA ECONOMICA FEDERAL");
                
            } else if (bancoBeneficiario.equals("BANESE")){
                cnpj = "13009717003404";
                 incluirPessoa(bancoBeneficiario, cnpj);
                
            } else if (bancoBeneficiario.equals("BANESTES")){
                cnpj = "28127603000178";
                 incluirPessoa(bancoBeneficiario, cnpj);
                
            }
            

            
            
            
             coletas.pausar(1000);
            
         
              
        
            
             coletas.pausar(1000);
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:valorPagoDecorate:valorPagoInput", valorTarifaTratado);
            
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:numeroDocumentoDecorate:numeroDocumentoInput", numeroDocumento);
            coletas.pausar(1000);
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:cmbTipoDocumento", "DJ DOC/ALVAR  JUDICIAL");
            
            
            coletas.clickElementId(driver, "incluirCustoProcessoForm:dataDocumentoDecorate:dataDocumentoInputInputDate");
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:dataDocumentoDecorate:dataDocumentoInputInputDate", dataTratada);
            coletas.procuraElementoPorId(driver, "incluirCustoProcessoForm:textoJustificativaDecorate:textoJustificativaInput", "Trata-se de Tarifa DOC/TED ref. a levt de depósito judicial em favor do BB  efetuado em outros Bancos cfe IN 869-2  item 2.4.5");
            
            coletas.clickElementId(driver, "incluirCustoProcessoForm:btSalvar");
             driver.switchTo().alert().accept();
             coletas.pausar(2000);
            
            
            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL", n, codigo);
                    interacao();
                }
            }
            
           
            coletas.aguardaElementoTelaByID("incluirDocumentoCustoProcessoForm:botaoNao");
            
            coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:botaoNao");
            
//            //coletas.procuraElementoPorId(driver, "incluirDocumentoCustoProcessoForm:uploadArquivo", enderecoDocumento);
//            coletas.aguardaElementoTelaByID("incluirDocumentoCustoProcessoForm:btSalvar");
//            coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:btSalvar");
            
            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL", n, codigo);
                    interacao();
                }
            }
            
//            coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:btConcluir");
//            coletas.pausar(1000);
            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL", "Operação realizada com sucesso.. providenciar a analise liberação", codigo);
                    interacao();
                } else{
                    
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_INCLUIR_TARIFA_PORTAL",n, codigo);
                    interacao();
                }
            }
            
            
            
            
            
        } while (rs.next());

        {
        }

    }
    
    
    
    
    public void incluirPessoa(String bancoBeneficiario, String cnpj){
        
        
        
        
        Boolean b = coletas.isElementPresentID(driver, "incluirCustoProcessoForm:j_id409");
        if (b){
        coletas.clickElementId(driver, "incluirCustoProcessoForm:j_id409");
        coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput");
        coletas.procuraElementoPorId(driver, "includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput", cnpj);
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:chkPesquisaMCI");
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:pesquisarPessoa");
        coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1518");
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1518");
        } else{
        coletas.clickElementId(driver, "incluirCustoProcessoForm:j_id400");
        coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput");
        coletas.procuraElementoPorId(driver, "includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput", cnpj);
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:chkPesquisaMCI");
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:pesquisarPessoa");
        
        
        
        if(coletas.isVisibleById(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1504")){   
        coletas.pausar(1000);
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1504");
            
        }
        
        
        
        
        
        
        
        
        if(coletas.isVisibleById(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar")){
        
        coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar");
        
        coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar");
       
        } 
       
            
        
        
        }
    }

}
