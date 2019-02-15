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

public class IncluirBancoBeneficiarioTarifa extends Thread {
    static int ultimoRegistro;
    static String msgfin;

    Coletas coletas = new Coletas();
    DAO<String> d = new DAO<>("rejud");
    Connection con = null;

    public IncluirBancoBeneficiarioTarifa() {
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

    private void interacao() throws SQLException {

        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        SolicitacaoLevantamento s = new SolicitacaoLevantamento();
        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_tarifa where (OBS_INCLUIR_BANCO_BENEFICIARIO is null or OBS_INCLUIR_BANCO_BENEFICIARIO='');";

        stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.pausar(5000);
            stmt.close();
            con.close();
            rs.close();
            if (user.getMatricula().equals("F5078775") || user.getMatricula().equals("f4555872") || user.getMatricula().equals("F3161139") || user.getMatricula().equals("f8783781")) {
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
            
            String bancoBeneficiario = rs.getString("BANCO_BENEFICIARIO");
            String cnpj = "";
                    
                    if(bancoBeneficiario.equals("CAIXA ECONOMICA FEDERAL")){
                        cnpj = "00360305000104";
                    }else if(bancoBeneficiario.equals("BANESTES")) {
                        cnpj = "28127603000178";
                    } else if(bancoBeneficiario.equals("BANRISUL")) {
                        
                         cnpj = "92702067000196";
                        
                    }
        
            
            
            
            

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
                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", n, codigo);
                    interacao();
                }
            }

            if (coletas.isElementPresentID(driver, "pesquisarProcessoForm:j_id572")) {
                String msgfim = coletas.lerValorElementoID("pesquisarProcessoForm:j_id572");
                if (!msgfim.equals("")) {

                    if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", msgfim, codigo);
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
                d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", "Não aparece o lapis para clicar e a situação do NPJ é :" + situacao, codigo);
                interacao();
            }

            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", n, codigo);
                    interacao();
                }
            }
            
            
            
            
            
            
            
            if (coletas.isElementPresentID(driver, "pesquisarProcessoForm:btEnviarDoc")) {
                coletas.clickElementId(driver, "pesquisarProcessoForm:btEnviarDoc");
               

            }
            
            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", n, codigo);
                    interacao();
                }
            }
            
            
            
            
            
            coletas.aguardaElementoTelaByName("j_id741");

            coletas.clickElementName(driver, "j_id741");
            coletas.aguardaElementoTelaByName("partesPrincipaisForm:j_id809");
            coletas.clickElementName(driver, "partesPrincipaisForm:j_id809");
            coletas.aguardaElementoTelaByID("terceirosForm:Decorate:TerceiroDecorate:pesquisarPessoaDecorate:j_id825");
            coletas.clickElementId(driver, "terceirosForm:Decorate:TerceiroDecorate:pesquisarPessoaDecorate:j_id825");
            coletas.aguardaElementoTelaByID("includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput");
            coletas.procuraElementoPorId(driver, "includePopup:pesquisarPessoaForm:cpfcnpjDecorate:cpfcnpjInput", cnpj);
            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:chkPesquisaMCI");
            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:pesquisarPessoa");

            if (coletas.isElementPresentID(driver, "includePopup:pesquisarPessoaForm:j_id1486")) {
                coletas.pausar(2000);
                String sit = coletas.lerValorElementoID("includePopup:pesquisarPessoaForm:j_id1486");
                if (!sit.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    incluirNaoListado(bancoBeneficiario,cnpj);
                    if(!msgfin.equals("")){
                        d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", msgfin, codigo);
                        interacao();    
                    }
                }

            }

            coletas.pausar(1000);
            if (coletas.isElementPresentID(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1554")) {
                String nome = coletas.lerValorElementoID("includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1554");

            } else {
                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", "CPF/CNPJ informado não encontrou pessoa vinculada", codigo);
                interacao();
            }

            coletas.clickElementId(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:j_id1554");
            
            coletas.pausar(1000);
            
            coletas.isVisibleById(driver, "includePopup:pesquisarPessoaForm:dataTablepessoa:0:detalhar");
               
            coletas.pausar(1000);
            
            
            coletas.selecionarElementoID("terceirosForm:Decorate:TerceiroDecorate:comboTipoEnvolvimentoDecorate:comboTipoEnvolvimentoListMenu", 5);
            
            
            
            
            
            coletas.aguardaElementoTelaByID("terceirosForm:Decorate:TerceiroDecorate:comboTipoEnvolvimentoDecorate:j_id839");
            coletas.clickElementId(driver, "terceirosForm:Decorate:TerceiroDecorate:comboTipoEnvolvimentoDecorate:j_id839");
            coletas.pausar(2000);
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", n, codigo);
                    interacao();
                }
            }
            
            
           
            
            
            
//            //coletas.pausar(2000);
//            
//            
//           int n=0; 
//           if (coletas.isElementPresentID(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:9:comboTipoRelProcesso")){
//               
//                n = 100;
//                
//               
//           }  else {
//               n=10;
//           }
//            
//           for(int i = 20; i>=1;i--){
//               if(coletas.isElementPresentXpath(driver, ".//*[@id='adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:j_id866_table']/tbody/tr/td[" + i + "]")){
//                coletas.clickElementXpath(driver, ".//*[@id='adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:j_id866_table']/tbody/tr/td[" + i + "]");
//                break;
//               }
//            }
//            
//          for(int i = 0;i<=n;i++){
//              
//              if(coletas.isElementPresentID(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id841")){
//                ultimoRegistro = n - i;
//                String nome = coletas.lerValorElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id841");
//                Boolean b =  coletas.compararNomes(bancoBeneficiario, nome);
//               
//                    if(b){
//                       
//                        coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":comboTipoRelProcesso", 9);
//                        coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":comboTipoRelBanco", 3);
//                        coletas.clickElementName(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (n - i) + ":j_id854");
//                        break;
//                       
//                        
//                    } else {
//                    int codigo = rs.getInt("CODIGO");
//                    con.close();
//                    rs.close();
//                    stmt.close();
//                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", "Nome do beneficiario diverge do informado", codigo);
//                    interacao();
//                        
//                    }
//                
//                
//              }
//              
//          }
//            
//          
//          for(int i = 0; i<= ultimoRegistro;i++){
//              String preenchimentoAnterior = "";
//              
//              if (coletas.isVisibleByIdByLeitura(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso")){
//            
//            
//              preenchimentoAnterior = coletas.lerValorElementoSelectID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso");
//            
//              
//              } else{
//              break;
//              }
//                if(preenchimentoAnterior.equals("Selecione")){
//                coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelProcesso", 9);
//                } 
//              
//          }
//          
//          
//          
//          
//            
//          
//          for(int i = 0; i<= ultimoRegistro;i++){
//               String preenchimentoAnterior = "";
//               
//               if (coletas.isVisibleByIdByLeitura(driver, "adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco")){
//                   
//                   preenchimentoAnterior = coletas.lerValorElementoSelectID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco");
//                   
//                   
//              } else{
//              break;
//              }
//              
//              
//              
//                if(preenchimentoAnterior.equals("Selecione")){
//                coletas.selecionarElementoID("adversasForm:ParteAdversaDecorate:dataTablePartesAdversas:" + (ultimoRegistro - i) + ":comboTipoRelBanco", 3);
//                }
//              
//          }
//          
//          
//          
          
        coletas.clickElementId(driver, "terceirosForm:btSalvar");
            
            
            
        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

               String   msgfin = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!msgfin.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa","OBS_INCLUIR_BANCO_BENEFICIARIO", msgfin, codigo);
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

        String sit = coletas.lerValorElementoID("consultarPessoaForm:j_id307");
        
        
        if(!sit.equals("")){
            
            incluirPessoa(beneficiario, cpf);
            msgfin = "CPF/CNPJ informado não encontrou pessoa vinculada ";
            return;
            
        }

            
                
        
        
        
        
    }

    private void incluirPessoa(String beneficiario, String cpf) {
        coletas.clickElementId(driver, "j_id56");
        coletas.aguardaElementoTelaByID("incluirPessoaForm:Decorate:ListBox");
        coletas.selecionarElementoID("incluirPessoaForm:Decorate:ListBox", 2);
        coletas.aguardaElementoTelaByID("incluirPessoaForm:cpfPssDecorate:cpfPssInput");
        coletas.procuraElementoPorId(driver, "incluirPessoaForm:cpfPssDecorate:cpfPssInput", cpf);
        coletas.pausar(1000);
        coletas.clickElementName(driver, "incluirPessoaForm:j_id141");
        coletas.pausar(1000);
        
        
        if (coletas.isElementPresentName(driver, "incluirPessoaForm:j_id723")){
            coletas.clickElementName(driver, "incluirPessoaForm:j_id723");
            
            return;
        } else{
            return;
        }
        
        
        
        
        
        
    }
}
