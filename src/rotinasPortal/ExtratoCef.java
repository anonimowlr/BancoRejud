package rotinasPortal;

import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.wait;
import conexao.ConnectionFactory;
import dao.DAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import entidade.Usuario;

public class ExtratoCef extends Thread {

    PreparedStatement stmt;
    Connection con;

   // static String diretorio = "G:\\Publica\\DOCUMENTOS_AUTOMATIZADOS";
    static String diretorio = "C:\\DOCUMENTOS_AUTOMATIZADOS";
    static int m = 0;

    Coletas coletas = new Coletas();
    DAO d = new DAO("rejud");

    @Override
    public void run() {

        try {
            downloadExtrato();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, " Erro na rotina de downloads extratos cef - " + ex);
        }

    }

    public void downloadExtrato() throws Exception {

        String usuario = "LUIZGBUR17";
        String senha = "lljm2807";

        System.setProperty("webdriver.firefox.marionette", "");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        profile.setPreference("browser.download.manager.showwhenStarting", false);
        profile.setPreference("browser.download.dir", diretorio);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, 3000);
        driver.get("https://depositojudicial.caixa.gov.br/sigsj_internet/Autenticacao");

        Boolean b = coletas.isElementPresentXpath(driver, "/html/body/div[@id='global']/div[@id='content']/div[@id='main']/form[@id='form0']/div/div[2]/div[1]/div/ul/li[1]/input[@id='usuarioInternet']");

        nomeElemento = "usuarioInternet";
        coletas.aguardaElementoTelaByID(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, usuario);

        nomeElemento = "senhaInternet";
        coletas.aguardaElementoTelaByID(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, senha);

        nomeElemento = "//*[@id='imagem']/input";
        coletas.clickElementXpath(driver, nomeElemento);

        coletas.aguardaElementoTelaByID("j_id45:filtroView:j_id46:agencia");

        interacao();

    }

    private void interacao() throws SQLException, InterruptedException {

        driver.get("https://depositojudicial.caixa.gov.br/sigsj_internet/Autenticacao");
        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_pog where ((OBS is null or OBS = '') and (FUNCIONARIO_RESPONSAVEL = ? )";
        stmt = con.prepareStatement(query);

        //  stmt.setString(1, matricula);
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_pog where OBS is null or OBS =  '' ";
            stmt = con.prepareStatement(query);

        }

        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.setSize();
            JOptionPane.showMessageDialog(null, " FIM !!!" + "\n" + "Local de armazenamento de extratos :" + diretorio);
            con.close();
            stmt.close();
            rs.close();
            stop();

        }

        do {

            String npj = rs.getString("NPJ");
            String idDepodito = rs.getString("ID_DEPOSITO");
            String agenciaDepositaria = rs.getString("AGENCIA_DEPOSITARIA");
            String contaDepositaria = rs.getString("CONTA_DEPOSITARIA");
            String variacaoConta = rs.getString("VARIACAO_CONTA_DEPOSITARIA");
            String modalidadedeposito = rs.getString("MODALIDADE_DEPOSITO");

            //consulta por ID
            /*coletas.clickElementXpath(driver, "/html/body/div[@id='global']/div[@id='content']/div[@id='main']/span[@id='j_id51']/form[@id='j_id51:filtroView:j_id52']/div[@id='subMain']/span[@id='j_id51:filtroView:j_id52:dinamico']/div/fieldset[1]/ul/li/div/table[@id='j_id51:filtroView:j_id52:icPadraoPesquisa']/tbody/tr[2]/td/span/span/img");
          coletas.procuraElementoPorId(driver, "j_id51:filtroView:j_id52:idDeposito", idDepodito);
             */
            coletas.procuraElementoPorId(driver, "j_id45:filtroView:j_id46:agencia", agenciaDepositaria);
            coletas.pausar(1000);
            coletas.procuraElementoPorId(driver, "j_id45:filtroView:j_id46:operacaoTrue", modalidadedeposito);
            coletas.procuraElementoPorId(driver, "j_id45:filtroView:j_id46:conta", contaDepositaria);
            coletas.procuraElementoPorId(driver, "j_id45:filtroView:j_id46:dv", variacaoConta);

            Boolean n = coletas.isElementPresentID(driver, "j_id51:filtroView:j_id52:modalEmail:_painelEmailContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("j_id51:filtroView:j_id52:modalEmail:_painelEmailContentDiv");

                if (!msgfim.equals("")) {
                    coletas.clickElementId(driver, "j_id51:filtroView:j_id52:modalEmail:_fecharButtonEmail");
                    int codigo = rs.getInt("CODIGO");
                    d.editarGenerico("tb_pog", "OBS", msgfim, codigo);
                    con.close();
                    stmt.close();
                    rs.close();
                    interacao();
                }
            }

            coletas.clickElementId(driver, "j_id51:filtroView:j_id52:btConsultaConta");

            n = coletas.isElementPresentID(driver, "modal:waitContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                while (!msgfim.equals("")) {
                    msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                }
            }

            n = coletas.isElementPresentID(driver, "j_id45:filtroView:j_id46:modalEmail:_painelEmailHeader");

            if (n) {
                String msgfim = coletas.lerValorElementoID("j_id45:filtroView:j_id46:modalEmail:_painelEmailContentDiv");

                if (!msgfim.equals("")) {
                    coletas.clickElementId(driver, "modal:_fecharButtonDocumento");
                   int codigo = rs.getInt("CODIGO");
                    d.editarGenerico("tb_pog", "OBS", msgfim, codigo);
                    con.close();
                    stmt.close();
                    rs.close();
                    interacao();
                }
            }

            coletas.aguardaElementoTelaByID("j_id45:filtroView:j_id46:btConsultaConta");
            coletas.clickElementId(driver, "j_id45:filtroView:j_id46:btConsultaConta");
            

            n = coletas.isElementPresentID(driver, "modal:waitContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                while (!msgfim.equals("")) {
                    msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                }
            }
            
            coletas.aguardaElementoTelaByID("j_id45:filtroView:j_id194:listaView:listaContaView:listaMovimentos:0:j_id366");
            
            coletas.clickElementXpath(driver, ".//*[@id='j_id45:filtroView:j_id194:listaView:j_id219:0:j_id280:0:extrato']/img");
            

            n = coletas.isElementPresentID(driver, "modal:waitContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                while (!msgfim.equals("")) {
                    msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                }
            }

            
            coletas.aguardaElementoTelaByID("j_id45:filtroView:j_id1415:dtInicialInputDate");
           
            coletas.clickElementId(driver, "j_id45:filtroView:j_id1415:j_id1433");
            
             n = coletas.isElementPresentID(driver, "modal:waitContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                while (!msgfim.equals("")) {
                    msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                }
            }
            
            
            
            coletas.clickElementXpath(driver, ".//*[@id='j_id45:filtroView:j_id1415:btnVisualizar']/img");

            
            
            
            
            
            n = coletas.isElementPresentID(driver, "modal:waitContentDiv");

            if (n) {
                String msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                while (!msgfim.equals("")) {
                    msgfim = coletas.lerValorElementoID("modal:waitContentDiv");

                }
            }

            n = coletas.isElementPresentID(driver, "modal:waitHeader");

            coletas.aguardaElementoTelaByName("j_id45:filtroView:j_id1415:_btnPRINT_Relatorio");
            coletas.clickElementXpath(driver, ".//*[@id='j_id45:filtroView:j_id1415:j_id1520']/img");

            
            String pasta = npj ;

            criarDiretorioMacro(pasta);
            

             int codigo = rs.getInt("CODIGO");
                    d.editarGenerico("tb_pog", "OBS", "Download efetuado", codigo);
                    con.close();
                    stmt.close();
                    rs.close();
            

            interacao();

        } while (rs.next());

        {
        }

    }

    public void criarDiretorioMacro(String variavel) {
        try {
            File dir = new File(diretorio + "\\" + variavel);
            dir.mkdir();
            Thread.sleep(2000);
            moverArquivos(variavel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o diretorio");
            System.out.println(ex);
        }
    }

    public void moverArquivos(String variavel) throws IOException, InterruptedException {

        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];

            Boolean b = arquivos.getName().endsWith(".pdf");
            if (b) {
                String nomeArquivo = arquivos.getName();
                String dstPath = diretorio + "\\" + variavel;
                Files.move(arquivos.toPath(), Paths.get(dstPath, nomeArquivo), REPLACE_EXISTING);
                Thread.sleep(1000);
            }

        }

    }

}
